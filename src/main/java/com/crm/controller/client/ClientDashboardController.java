package com.crm.controller.client;

import com.crm.generic.Nospaniol;
import com.crm.model.AgencySpending;
import com.crm.model.Citation;
import com.crm.model.CitationType;
import com.crm.model.ClientProfile;
import com.crm.model.DailySpending;
import com.crm.model.Department;
import com.crm.model.JobResponse;
import com.crm.model.MonthlySpending;
import com.crm.model.Search;
import com.crm.model.Tollspendings;
import com.crm.model.Transaction;
import com.crm.model.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({"client_dash"})
public class ClientDashboardController extends Nospaniol {

    @RequestMapping(value = {"dashboard-redirect"}, method = {RequestMethod.GET})
    public ModelAndView dashboard_redirector(HttpSession session, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "header");
        mv.setViewName("client-redirect");
        return mv;
    }

    @RequestMapping(value = {"dashboard"}, method = {RequestMethod.GET})
    public ModelAndView dashboard_panel(RedirectAttributes redir, HttpServletRequest req, HttpSession session, HttpServletResponse response) {
        //response.addHeader("Cache-Control", "public,immutable,max-age=600, must-revalidate, no-transform");
        String departmentMode, departmentId;
        Department department;
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "header");
        session.setAttribute("totalPages", null);
        String clientId = (String) req.getSession().getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        LocalDate today = LocalDate.now();
        String month = (String) session.getAttribute("month_mode");
        if (month == null || month.isEmpty()) {
            month = today.getMonth().toString();
        }
        mv.addObject("nsmonth", month);
        mv.addObject("nsyear", (String) session.getAttribute("year_mode"));
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                departmentMode = (String) session.getAttribute("department_mode");
                session.setAttribute("departments", this.departmentService.findByClientProfile(profile));
                departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department1 = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                    dashboardDepartmentInfo(session, mv, department1);
                } else {
                    dashboardClientInfo(session, mv, profile);
                }
                mv.setViewName("clientdashboard");
                break;
            case "DEPARTMENT":
                department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                session.setAttribute("departments", this.subDepartmentService.findByDepartment(department));
                dashboardDepartmentInfo(session, mv, department);
                LOG.info(" PAGE LOAD UP " + LocalDateTime.now().toString());
                mv.setViewName("clientdashboard");
                break;
        }
        LOG.info(" PAGE LOAD UP " + LocalDateTime.now().toString());
        mv.setViewName("clientdashboard");
        return mv;
    }

    void dashboardClientInfo(HttpSession session, ModelAndView mv, ClientProfile profile) {
        LocalDate today = LocalDate.now();
        int year = Integer.valueOf((String) session.getAttribute("year_mode"));
        String month = (String) session.getAttribute("month_mode");
        if (month == null || month.isEmpty()) {
            month = today.getMonth().toString();
        }
        if (profile != null) {
            //mv.addObject("institution", profile.getCompanyName());
            setClientCitations(session, profile, mv);
            calculateAgencySpending(session, profile, mv);
            calculateDailyFigures(session, profile, mv);
            calculateMonthlyFigures(profile, mv, session);
            mv.addObject("menutype", "header");
            Double mtd = getTwoDecimal(getClientTollSpending(session, profile));
            mv.addObject("spendingAmount", mtd);
            mv.addObject("totalSavings", getTwoDecimal(mtd * 0.185));
            mv.addObject("activeVehicleCount", this.vehicleService.countByClientProfileAndVinNotAndStateNot(profile, null, null));
            mv.addObject("transactionCount", countPrintingInfo(session));
            mv.addObject("spendings", highClientSpenders(session, profile));
            mv.addObject("disputedTransactionCount", this.transactionDisputeService.countByClientProfile(profile));
        }
    }

    void dashboardDepartmentInfo(HttpSession session, ModelAndView mv, Department department) {
        if (department != null) {
            LocalDate today = LocalDate.now();
            int year = Integer.valueOf((String) session.getAttribute("year_mode"));
            String month = (String) session.getAttribute("month_mode");
            if (month == null || month.isEmpty()) {
                month = today.getMonth().toString();
            }

            setDepartmentCitations(session, department, mv);
            calculateAgencySpending(session, department, mv);
            calculateDailyFigures(session, department, mv);
            calculateMonthlyFigures(department, mv, session);
            Double mtd = getTwoDecimal(getDepartmentTollSpending(session, department));
            mv.addObject("spendingAmount", mtd);
            mv.addObject("totalSavings", getTwoDecimal(mtd * 0.185));
            mv.addObject("activeVehicleCount", this.vehicleService.countByDepartmentAndVinNotAndStateNot(department, null, null));
            mv.addObject("transactionCount", countPrintingInfo(session));
            mv.addObject("spendings", highDepartmentSpenders(session, department));
            mv.addObject("disputedTransactionCount", this.transactionDisputeService.countByDepartment(department));
        }
    }

    public long countPrintingInfo(HttpSession session) {
        ClientProfile profile;
        String departmentMode, departmentId;
        Department department;
        long transactions = 0;
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            return transactions;
        }
        int year = Integer.valueOf((String) session.getAttribute("year_mode"));
        String month = (String) session.getAttribute("month_mode");
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                departmentMode = (String) session.getAttribute("department_mode");
                departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department1 = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                    transactions = this.transactionService.countByDepartmentAndPostedMonthAndPostedYearOrderByTransactionIdDesc(department1, month, year);
                } else {
                    profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    transactions = this.transactionService.countByClientProfileAndPostedMonthAndPostedYearOrderByTransactionIdDesc(profile, month, year);
                }
                break;
            case "DEPARTMENT":
                department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                transactions = this.transactionService.countByDepartmentAndPostedMonthAndPostedYearOrderByTransactionIdDesc(department, month, year);
                break;
        }
        return transactions;
    }

    @RequestMapping(path = {"/change_mode/parent/"}, produces = {"application/json; charset=UTF-8"}, method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<?> changeParentMode(HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        result.setTitle("success");
        String clientId = (String) session.getAttribute("NsId");
        ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
        session.setAttribute("institution", profile.getCompanyName());
        session.setAttribute("parent_institution", profile.getCompanyName());
        session.setAttribute("companyLogo", Base64.getEncoder().encodeToString(profile.getCompanyLogo().getData()));
        session.setAttribute("departments", this.departmentService.findByClientProfile(profile));
        session.setAttribute("department_mode", "NO");
        session.setAttribute("department_id", null);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = {"/change_mode/department/"}, produces = {"application/json; charset=UTF-8"}, method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<?> changeMode(@ModelAttribute("search") Search search, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        String client = search.getSearchItem().trim();
        if (client.equals("ALL")) {
            session.setAttribute("department_mode", "NO");
            session.setAttribute("department_id", null);
            return ResponseEntity.ok(result);
        }
        Department department = this.departmentService.findByDepartmentName(client);
        if (department == null) {
            result.setMessage("Department : '" + search.getSearchItem() + "' not found in the system");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        session.setAttribute("institution", department.getDepartmentName());
        session.setAttribute("parent_institution", department.getClientProfile().getCompanyName());
        session.setAttribute("companyLogo", Base64.getEncoder().encodeToString(department.getDepartmentLogo().getData()));
        session.setAttribute("departments", this.subDepartmentService.findByDepartment(department));
        session.setAttribute("department_mode", "YES");
        session.setAttribute("department_id", String.valueOf(department.getDepartmentId()));
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = {"/change_mode/month/"}, produces = {"application/json; charset=UTF-8"}, method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<?> changeMonthMode(@ModelAttribute("search") Search search, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        result.setTitle("success");
        String month = search.getSearchItem().trim();
        session.setAttribute("month_mode", month);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = {"/change_mode/year/"}, produces = {"application/json; charset=UTF-8"}, method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<?> changeYearMode(@ModelAttribute("search") Search search, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        result.setTitle("success");
        String year = search.getSearchItem().trim();
        session.setAttribute("year_mode", year);
        return ResponseEntity.ok(result);
    }

    @RequestMapping({"/profile"})
    public ModelAndView viewProfile(HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        String departmentMode, departmentId;
        Department department;
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "header");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        Long user_id = (Long) session.getAttribute("ns_user_id");
        User user = this.userService.findByUserId(user_id);
        mv.addObject("user", user);
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                departmentMode = (String) session.getAttribute("department_mode");
                departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department1 = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));

                    mv.addObject("department", department1);
                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));

                    session.setAttribute("companyLogo", Base64.getEncoder().encodeToString(profile.getCompanyLogo().getData()));
                    //mv.addObject("institution", profile.getCompanyName());
                    mv.addObject("clientProfile", profile);
                }
                mv.setViewName("myprofile");
                return mv;
            case "DEPARTMENT":
                department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));

                session.setAttribute("companyLogo", Base64.getEncoder().encodeToString(department.getDepartmentLogo().getData()));

                mv.addObject("department", department);
                mv.setViewName("myprofile");
                return mv;
        }
        LOG.info("USER ACCOUNT UNKNOWN");
        mv.setViewName("myprofile");
        return mv;
    }

    @RequestMapping({"/view/department"})
    public ModelAndView viewDepartments(HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "header");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));

        mv.addObject("allDepartments", this.departmentService.findByClientProfile(profile));
        //mv.addObject("institution", profile.getCompanyName());
        session.setAttribute("companyLogo", Base64.getEncoder().encodeToString(profile.getCompanyLogo().getData()));
        mv.setViewName("viewdepartment");
        return mv;
    }

    Double getDepartmentTollSpending(HttpSession session, Department department) {
        double total = 0.0D;
        LocalDate today = LocalDate.now();
        String month = (String) session.getAttribute("month_mode");
        if (month == null || month.isEmpty()) {
            month = today.getMonth().toString();
        }
        int year = Integer.valueOf((String) session.getAttribute("year_mode"));
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("postedYear").is(Integer.valueOf(year)).and("postedMonth").is(month).and("department").is(department).and("transactionDispute").is(Boolean.valueOf(false))),
            (AggregationOperation) Aggregation.group(new String[]{"postedMonth"}).sum("amount").as("total")});
        AggregationResults<Tollspendings> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, Tollspendings.class);
        List<Tollspendings> expenditureReport = groupResults.getMappedResults();
        if (expenditureReport != null && !expenditureReport.isEmpty()) {
            Tollspendings toll = expenditureReport.get(0);
            total = toll.getTotal();
        }
        return Double.valueOf(total);
    }

    Double getClientTollSpending(HttpSession session, ClientProfile profile) {
        double total = 0.0D;
        LocalDate today = LocalDate.now();
        int year = Integer.valueOf((String) session.getAttribute("year_mode"));
        String month = (String) session.getAttribute("month_mode");
        if (month == null || month.isEmpty()) {
            month = today.getMonth().toString();
        }
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("postedYear").is(Integer.valueOf(year)).and("postedMonth").is(month).and("clientProfile").is(profile).and("transactionDispute").is(false)),
            (AggregationOperation) Aggregation.group(new String[]{"postedMonth"}).sum("amount").as("total")});
        AggregationResults<Tollspendings> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, Tollspendings.class);
        List<Tollspendings> expenditureReport = groupResults.getMappedResults();
        if (expenditureReport != null && !expenditureReport.isEmpty()) {
            Tollspendings toll = expenditureReport.get(0);
            total = toll.getTotal();
        }
        return total;
    }

    void setClientCitations(HttpSession session, ClientProfile profile, ModelAndView mv) {
        LocalDate today = LocalDate.now();
        String month = (String) session.getAttribute("month_mode");
        if (month == null || month.isEmpty()) {
            month = today.getMonth().toString();
        }
        int year = Integer.valueOf((String) session.getAttribute("year_mode"));
        double total = 0.0D;
        CitationType type = this.citationTypeService.findByCitationTypeName("SPEED TICKET");
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("citationYear").is(Integer.valueOf(year)).and("citationMonth").is(month).and("clientProfile").is(profile).and("citationType").is(type)),
            (AggregationOperation) Aggregation.group(new String[]{"citationMonth"}).sum("citationAmount").as("total")});
        AggregationResults<Tollspendings> groupResults = this.mongoTemplate.aggregate(aggregation, Citation.class, Tollspendings.class);
        List<Tollspendings> expenditureReport = groupResults.getMappedResults();
        if (expenditureReport != null && !expenditureReport.isEmpty()) {
            Tollspendings toll = expenditureReport.get(0);
            total = toll.getTotal();
        }
        mv.addObject("speedTicketCount", Long.valueOf(this.citationService.countByClientProfileAndCitationMonthAndCitationYearAndCitationType(profile, month, year, type)));
        mv.addObject("speedTicketAmount", Double.valueOf(total));
        total = 0.0D;
        type = this.citationTypeService.findByCitationTypeName("PARKING");
        aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("citationYear").is(Integer.valueOf(year)).and("citationMonth").is(month).and("clientProfile").is(profile).and("citationType").is(type)),
            (AggregationOperation) Aggregation.group(new String[]{"citationMonth"}).sum("citationAmount").as("total")});
        groupResults = this.mongoTemplate.aggregate(aggregation, Citation.class, Tollspendings.class);
        expenditureReport = groupResults.getMappedResults();
        if (expenditureReport != null && !expenditureReport.isEmpty()) {
            Tollspendings toll = expenditureReport.get(0);
            total = toll.getTotal();
        }
        mv.addObject("parkingCount", this.citationService.countByClientProfileAndCitationMonthAndCitationYearAndCitationType(profile, month, year, type));
        mv.addObject("parkingAmount", total);
        total = 0.0D;
        type = this.citationTypeService.findByCitationTypeName("RED LIGHT");
        aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("citationYear").is(Integer.valueOf(year)).and("citationMonth").is(month).and("clientProfile").is(profile).and("citationType").is(type)),
            (AggregationOperation) Aggregation.group(new String[]{"citationMonth"}).sum("citationAmount").as("total")});
        groupResults = this.mongoTemplate.aggregate(aggregation, Citation.class, Tollspendings.class);
        expenditureReport = groupResults.getMappedResults();
        if (expenditureReport != null && !expenditureReport.isEmpty()) {
            Tollspendings toll = expenditureReport.get(0);
            total = toll.getTotal();
        }
        mv.addObject("redLightAmount", total);
        mv.addObject("redLightCount", this.citationService.countByClientProfileAndCitationMonthAndCitationYearAndCitationType(profile, month, year, type));
    }

    void setDepartmentCitations(HttpSession session, Department department, ModelAndView mv) {
        LocalDate today = LocalDate.now();
        String month = (String) session.getAttribute("month_mode");
        if (month == null || month.isEmpty()) {
            month = today.getMonth().toString();
        }
        int year = Integer.valueOf((String) session.getAttribute("year_mode"));
        double total = 0.0D;
        CitationType type = this.citationTypeService.findByCitationTypeName("SPEED TICKET");
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("citationYear").is(Integer.valueOf(year)).and("citationMonth").is(month).and("department").is(department).and("citationType").is(type)),
            (AggregationOperation) Aggregation.group(new String[]{"citationMonth"}).sum("citationAmount").as("total")});
        AggregationResults<Tollspendings> groupResults = this.mongoTemplate.aggregate(aggregation, Citation.class, Tollspendings.class);
        List<Tollspendings> expenditureReport = groupResults.getMappedResults();
        if (expenditureReport != null && !expenditureReport.isEmpty()) {
            Tollspendings toll = expenditureReport.get(0);
            total = toll.getTotal();
        }
        mv.addObject("speedTicketCount", this.citationService.countByDepartmentAndCitationMonthAndCitationYearAndCitationType(department, month, year, type));
        mv.addObject("speedTicketAmount", total);
        total = 0.0D;
        type = this.citationTypeService.findByCitationTypeName("PARKING");
        aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("citationYear").is(year).and("citationMonth").is(month).and("department").is(department).and("citationType").is(type)),
            (AggregationOperation) Aggregation.group(new String[]{"citationMonth"}).sum("citationAmount").as("total")});
        groupResults = this.mongoTemplate.aggregate(aggregation, Citation.class, Tollspendings.class);
        expenditureReport = groupResults.getMappedResults();
        if (expenditureReport != null && !expenditureReport.isEmpty()) {
            Tollspendings toll = expenditureReport.get(0);
            total = toll.getTotal();
        }
        mv.addObject("parkingCount", this.citationService.countByDepartmentAndCitationMonthAndCitationYearAndCitationType(department, month, year, type));
        mv.addObject("parkingAmount", total);
        total = 0.0D;
        type = this.citationTypeService.findByCitationTypeName("RED LIGHT");
        aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("citationYear").is(year).and("citationMonth").is(month).and("department").is(department).and("citationType").is(type)),
            (AggregationOperation) Aggregation.group(new String[]{"citationMonth"}).sum("citationAmount").as("total")});
        groupResults = this.mongoTemplate.aggregate(aggregation, Citation.class, Tollspendings.class);
        expenditureReport = groupResults.getMappedResults();
        if (expenditureReport != null && !expenditureReport.isEmpty()) {
            Tollspendings toll = expenditureReport.get(0);
            total = toll.getTotal();
        }
        mv.addObject("redLightAmount", total);
        mv.addObject("redLightCount", this.citationService.countByDepartmentAndCitationMonthAndCitationYearAndCitationType(department, month, year, type));
    }

    class Sortbyroll implements Comparator<Tollspendings> {
        // Used for sorting in ascending order of 
        // roll number 

        public int compare(Tollspendings a, Tollspendings b) {
            return (int) (a.getTollTotal() - b.getTollTotal());
        }
    }

    List<Tollspendings> highClientSpenders(HttpSession session, ClientProfile profile) {
        LocalDate today = LocalDate.now();
        String month = (String) session.getAttribute("month_mode");
        if (month == null || month.isEmpty()) {
            month = today.getMonth().toString();
        }
        // AggregationOperation sort = Aggregation.sort(Sort.Direction.DESC, "total");
        int year = Integer.valueOf((String) session.getAttribute("year_mode"));
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("postedYear").is(year).and("postedMonth").is(month).and("clientProfile").is(profile)),
            (AggregationOperation) Aggregation.group(new String[]{"vehicle"}).sum("amount").as("total").addToSet("vehicle").as("vehicle"),
            (AggregationOperation) Aggregation.sort(Sort.Direction.DESC, "total")});
        AggregationResults<Tollspendings> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, Tollspendings.class);
        List<Tollspendings> expenditureReport = groupResults.getMappedResults();
        List<Tollspendings> modifiableList = new ArrayList<>(expenditureReport);
        Collections.sort(modifiableList);
        return expenditureReport;
    }
    Comparator<Tollspendings> comparator = (Tollspendings str1, Tollspendings str2) -> (int) (str1.getTollTotal() - str2.getTollTotal());

    List<Tollspendings> highDepartmentSpenders(HttpSession session, Department department) {
        LocalDate today = LocalDate.now();
        String month = (String) session.getAttribute("month_mode");
        if (month == null || month.isEmpty()) {
            month = today.getMonth().toString();
        }
        int year = Integer.valueOf((String) session.getAttribute("year_mode"));
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("postedYear").is(year).and("postedMonth").is(month).and("department").is(department)),
            (AggregationOperation) Aggregation.group(new String[]{"vehicle"}).sum("amount").as("total").addToSet("vehicle").as("vehicle"),
            (AggregationOperation) Aggregation.sort(Sort.Direction.DESC, "total")});
        AggregationResults<Tollspendings> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, Tollspendings.class);
        List<Tollspendings> expenditureReport = groupResults.getMappedResults();
        return expenditureReport;
    }

    void calculateAgencySpending(HttpSession session, ClientProfile profile, ModelAndView mv) {
        List<AgencySpending> spendings = getAgencyClientTollSpending(session, profile);
        mv.addObject("agencies", spendings);
        mv.addObject("agency_names", getAgenciesClient(session, profile));
    }

    void calculateAgencySpending(HttpSession session, Department department, ModelAndView mv) {
        List<AgencySpending> spendings = getAgencyDepartmentTollSpending(session, department);
        mv.addObject("agencies", spendings);
    }

    List<String> getAgenciesClient(HttpSession session, Department department) {
        LocalDate today = LocalDate.now();
        String month = (String) session.getAttribute("month_mode");
        if (month == null || month.isEmpty()) {
            month = today.getMonth().toString();
        }
        int year = Integer.valueOf((String) session.getAttribute("year_mode"));
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("postedYear").is(year).and("postedMonth").is(month).and("department").is(department)),
            (AggregationOperation) Aggregation.group(new String[]{"agency"}).addToSet("agency").as("agency")});
        AggregationResults<String> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, String.class);
        List<String> spendings = groupResults.getMappedResults();
        return spendings;
    }

    List<AgencySpending> getAgencyDepartmentTollSpending(HttpSession session, Department department) {
        LocalDate today = LocalDate.now();
        String month = (String) session.getAttribute("month_mode");
        if (month == null || month.isEmpty()) {
            month = today.getMonth().toString();
        }
        int year = Integer.valueOf((String) session.getAttribute("year_mode"));
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("postedYear").is(year).and("postedMonth").is(month).and("department").is(department)),
            (AggregationOperation) Aggregation.group(new String[]{"agency"}).sum("amount").as("tollAmount").addToSet("agency").as("agency")});
        AggregationResults<AgencySpending> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, AgencySpending.class);
        List<AgencySpending> spendings = groupResults.getMappedResults();
        return spendings;
    }

    List<String> getAgenciesClient(HttpSession session, ClientProfile profile) {
        LocalDate today = LocalDate.now();
        String month = (String) session.getAttribute("month_mode");
        if (month == null || month.isEmpty()) {
            month = today.getMonth().toString();
        }
        int year = Integer.valueOf((String) session.getAttribute("year_mode"));
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("postedYear").is(year).and("postedMonth").is(month).and("clientProfile").is(profile)),
            (AggregationOperation) Aggregation.group(new String[]{"agency"}).addToSet("agency").as("agency")});
        AggregationResults<String> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, String.class);
        List<String> spendings = groupResults.getMappedResults();
        return spendings;
    }

    List<AgencySpending> getAgencyClientTollSpending(HttpSession session, ClientProfile profile) {
        LocalDate today = LocalDate.now();
        String month = (String) session.getAttribute("month_mode");
        if (month == null || month.isEmpty()) {
            month = today.getMonth().toString();
        }
        int year = Integer.valueOf((String) session.getAttribute("year_mode"));
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("postedYear").is(year).and("postedMonth").is(month).and("clientProfile").is(profile)),
            (AggregationOperation) Aggregation.group(new String[]{"agency"}).sum("amount").as("tollAmount").addToSet("agency").as("agency")});
        AggregationResults<AgencySpending> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, AgencySpending.class);
        List<AgencySpending> spendings = groupResults.getMappedResults();
        return spendings;
    }

    void calculateMonthlyFigures(ClientProfile profile, ModelAndView mv, HttpSession session) {
        List<MonthlySpending> spendings = getMonthlyClientTollSpending(profile, session);
        mv.addObject("monthlies", spendings);
    }

    void calculateMonthlyFigures(Department department, ModelAndView mv, HttpSession session) {
        List<MonthlySpending> spendings = getMonthlyDepartmentTollSpending(department, session);
        mv.addObject("monthlies", spendings);
    }

    List<MonthlySpending> getMonthlyDepartmentTollSpending(Department department, HttpSession session) {
        int year = Integer.valueOf((String) session.getAttribute("year_mode"));
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("postedYear").is(Integer.valueOf(year)).and("department").is(department)),
            (AggregationOperation) Aggregation.group(new String[]{"postedMonth"}).sum("amount").as("tollAmount").addToSet("postedMonth").as("month")});
        AggregationResults<MonthlySpending> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, MonthlySpending.class);
        List<MonthlySpending> spendings = groupResults.getMappedResults();
        return spendings;
    }

    List<MonthlySpending> getMonthlyClientTollSpending(ClientProfile profile, HttpSession session) {
        LocalDate today = LocalDate.now();
        int year = Integer.valueOf((String) session.getAttribute("year_mode"));
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("postedYear").is(Integer.valueOf(year)).and("clientProfile").is(profile)),
            (AggregationOperation) Aggregation.group(new String[]{"postedMonth"}).sum("amount").as("tollAmount").addToSet("postedMonth").as("month")});
        AggregationResults<MonthlySpending> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, MonthlySpending.class);
        List<MonthlySpending> spendings = groupResults.getMappedResults();
        return spendings;
    }

    void calculateDailyFigures(HttpSession session, ClientProfile profile, ModelAndView mv) {
        List<DailySpending> spendings = getDailiesClientTollSpending(session, profile);
        mv.addObject("dailies", spendings);
    }

    void calculateDailyFigures(HttpSession session, Department department, ModelAndView mv) {
        List<DailySpending> spendings = getDailiesDepartmentTollSpending(session, department);
        mv.addObject("dailies", spendings);
    }

    List<DailySpending> getDailiesDepartmentTollSpending(HttpSession session, Department department) {
        LocalDate today = LocalDate.now();
        String month = (String) session.getAttribute("month_mode");
        if (month == null || month.isEmpty()) {
            month = today.getMonth().toString();
        }
        int year = Integer.valueOf((String) session.getAttribute("year_mode"));
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("postedYear").is(year).and("postedMonth").is(month).and("department").is(department)),
            (AggregationOperation) Aggregation.group(new String[]{"postedDate"}).sum("amount").as("tollAmount").addToSet("postedDate").as("tollDate"),
            (AggregationOperation) Aggregation.sort(Sort.Direction.ASC, new String[]{Aggregation.previousOperation(), "postedDate"})});
        AggregationResults<DailySpending> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, DailySpending.class);
        List<DailySpending> spendings = groupResults.getMappedResults();
        return spendings;
    }

    List<DailySpending> getDailiesClientTollSpending(HttpSession session, ClientProfile profile) {
        LocalDate today = LocalDate.now();
        String month = (String) session.getAttribute("month_mode");
        if (month == null || month.isEmpty()) {
            month = today.getMonth().toString();
        }
        int year = Integer.valueOf((String) session.getAttribute("year_mode"));
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("postedYear").is(year).and("postedMonth").is(month).and("clientProfile").is(profile)),
            (AggregationOperation) Aggregation.group(new String[]{"postedDate"}).sum("amount").as("tollAmount").addToSet("postedDate").as("tollDate"),
            (AggregationOperation) Aggregation.sort(Sort.Direction.ASC, new String[]{Aggregation.previousOperation(), "postedDate"})});
        AggregationResults<DailySpending> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, DailySpending.class);
        List<DailySpending> spendings = groupResults.getMappedResults();
        return spendings;
    }

    /*
    
    
    CUSTOM FOR PROTECH
    
    
     */
    @RequestMapping(value = {"dashboard/caliber"}, method = {RequestMethod.GET})
    public ModelAndView calib_dashboard_panel(RedirectAttributes redir, HttpServletRequest req, HttpSession session, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "header");
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.addObject("message", "Invalid session detected, please login again!");
            mv.addObject("messageType", "fail");
            mv.setViewName("user-logout");
            return mv;
        }
        ClientProfile profile = this.clientProfileService.findByCompanyName("CALIBER AUTO GLASS");
        session.setAttribute("institution", profile.getCompanyName());
        session.setAttribute("parent_institution", profile.getCompanyName());
        session.setAttribute("companyLogo", Base64.getEncoder().encodeToString(profile.getCompanyLogo().getData()));
        session.setAttribute("NsId", String.valueOf(profile.getClientProfileId()));
        session.setAttribute("client_id", String.valueOf(profile.getClientProfileId()));
        mv.setViewName("redirect:/client_dash/dashboard");
        return mv;
    }

    @RequestMapping(value = {"dashboard/protech"}, method = {RequestMethod.GET})
    public ModelAndView prot_dashboard_panel(RedirectAttributes redir, HttpServletRequest req, HttpSession session, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.addObject("message", "Invalid session detected, please login again!");
            mv.addObject("messageType", "fail");
            mv.setViewName("user-logout");
            return mv;
        }
        ClientProfile profile = this.clientProfileService.findByCompanyName("PROTECH AS");
        session.setAttribute("NsId", String.valueOf(profile.getClientProfileId()));
        session.setAttribute("institution", profile.getCompanyName());
        session.setAttribute("parent_institution", profile.getCompanyName());
        session.setAttribute("companyLogo", Base64.getEncoder().encodeToString(profile.getCompanyLogo().getData()));
        session.setAttribute("client_id", String.valueOf(profile.getClientProfileId()));
        mv.setViewName("redirect:/client_dash/dashboard");
        return mv;
    }

    /**
     *
     *
     * END OF PROTECH CUSTOM
     *
     */
}
