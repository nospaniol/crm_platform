//package com.crm.controller.admin;
//
//import com.crm.generic.Nospaniol;
//import com.crm.model.Citation;
//import com.crm.model.CitationType;
//import com.crm.model.ClientProfile;
//import com.crm.model.Department;
//import com.crm.model.JobResponse;
//import com.crm.model.Search;
//import com.crm.model.Tollspendings;
//import com.crm.model.Transaction;
//import com.crm.model.User;
//import java.time.LocalDate;
//import java.util.Base64;
//import java.util.List;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.mongodb.core.aggregation.Aggregation;
//import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
//import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
//import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
//import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
//import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
//import org.springframework.data.mongodb.core.aggregation.AggregationResults;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
///**
// *
// * @author nospaniol
// */
//@Controller
//@RequestMapping("innovative_refresh")
//public class DataRefresh extends Nospaniol {
//        
//    void dashboardClientInfo(ModelAndView mv, String clientId) {
//        ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
//        if (profile == null) {
//        } else {
//            LocalDate today = LocalDate.now();
//            setClientCitations(profile, mv);
//            mv.addObject("menutype", "header");
//            mv.addObject("spendingAmount", this.getTwoDecimal(this.getTollSpending(profile)));
//            mv.addObject("activeVehicleCount", this.vehicleService.countByClientProfile(profile));
//            List<Department> departments = this.departmentService.findByClientProfile(profile);
//            if (departments == null || departments.isEmpty()) {
//                mv.addObject("totalSavings", this.savingService.findByClientProfile(profile).getAmount());
//            } else {
//                Double account_balance = 0.00;
//                for (Department department : departments) {
//                    account_balance = account_balance + this.departmentSavingService.findByDepartment(department).getAmount();
//                }
//                mv.addObject("totalSavings", account_balance);
//            }
//            mv.addObject("transactionCount", this.transactionService.countByClientProfileAndTransactionMonthAndTransactionYear(profile, today.getMonth().toString(), today.getYear()));;
//            mv.addObject("todayToll", getDailyClientTollSpending(profile));
//            mv.addObject("spendings", highClientSpenders(profile));
//            mv.addObject("disputedTransactionCount", this.transactionDisputeService.countByClientProfile(profile));
//        }
//    }
//
//    void dashboardDepartmentInfo(ModelAndView mv, String clientId) {
//        Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
//        if (department == null) {
//        } else {
//            LocalDate today = LocalDate.now();
//            setDepartmentCitations(department, mv);
//            mv.addObject("spendingAmount", this.getTwoDecimal(this.getDepartmentTollSpending(department)));
//            mv.addObject("activeVehicleCount", this.vehicleService.countByDepartmentAndVehicleStatus(department, "active"));
//            mv.addObject("totalSavings", this.departmentSavingService.findByDepartment(department).getAmount());
//            mv.addObject("transactionCount", this.transactionService.countByDepartmentAndTransactionMonthAndTransactionYear(department, today.getMonth().toString(), today.getYear()));
//            mv.addObject("todayToll", getDailyDepartmentTollSpending(department));
//            mv.addObject("spendings", this.highDepartmentSpenders(department));
//            mv.addObject("disputedTransactionCount", this.transactionDisputeService.countByDepartment(department));
//        }
//    }
//
//    @RequestMapping(path = "/change_mode/parent/", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseEntity<?> changeParentMode(Model model, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
//        JobResponse result = new JobResponse();
//        String ns_mail = (String) session.getAttribute("ns_mail_address");
//        if (ns_mail == null || ns_mail.isEmpty()) {
//            result.setMessage("Invalid session detected, please login again!");
//            result.setTitle("fail");
//            return ResponseEntity.ok(result);
//        }
//        session.setAttribute("department_mode", "NO");
//        session.setAttribute("department_id", null);
//        result.setTitle("success");
//        return ResponseEntity.ok(result);
//    }
//
//    @RequestMapping(path = "/change_mode/department/", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseEntity<?> changeMode(@ModelAttribute("search") Search search, Model model, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
//        JobResponse result = new JobResponse();
//        String ns_mail = (String) session.getAttribute("ns_mail_address");
//        if (ns_mail == null || ns_mail.isEmpty()) {
//            result.setMessage("Invalid session detected, please login again!");
//            result.setTitle("fail");
//            return ResponseEntity.ok(result);
//        }
//        String client = search.getSearchItem().trim();
//        if (client.equals("ALL")) {
//            session.setAttribute("department_mode", "NO");
//            session.setAttribute("department_id", null);
//            result.setTitle("success");
//            return ResponseEntity.ok(result);
//        }
//        Department department = this.departmentService.findByDepartmentName(client);
//        if (department == null) {
//            result.setMessage("Department : '" + search.getSearchItem() + "' not found in the system");
//            result.setTitle("fail");
//            return ResponseEntity.ok(result);
//        } else {
//            session.setAttribute("department_mode", "YES");
//            session.setAttribute("department_id", String.valueOf(department.getDepartmentId()));
//            result.setTitle("success");
//        }
//        return ResponseEntity.ok(result);
//    }
//
//    @RequestMapping("/profile")
//    public ModelAndView viewProfile(HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
//        ModelAndView mv = new ModelAndView();
//        mv.addObject("menutype", "header");
//        String clientId = (String) session.getAttribute("NsId");
//        if (clientId == null || clientId.isEmpty()) {
//            mv.setViewName("user-logout");
//            mv.addObject("messageType", "success");
//            mv.addObject("message", "Session expired, please login again");
//            return mv;
//        }
//        Long user_id = (Long) session.getAttribute("ns_user_id");
//        User user = this.userService.findByUserId(user_id);
//        mv.addObject("user", user);
//        String user_type = (String) session.getAttribute("ns_user");
//        switch (user_type) {
//            case "CLIENT":
//                String departmentMode = (String) session.getAttribute("department_mode");
//                String departmentId = (String) session.getAttribute("department_id");
//                if (departmentMode != null && departmentMode.equals("YES")) {
//                    Department department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
//                    mv.addObject("institution", departmentName(departmentId));
//                    departmentLogo(mv, departmentId);
//                   session.setAttribute("companyLogo",Base64.getEncoder().encodeToString(department.getDepartmentLogo().getData()));
//                    mv.addObject("department", department);
//                } else {
//                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
//                    mv.addObject("companyLogo", Base64.getEncoder().encodeToString(profile.getCompanyLogo().getData()));
//                    mv.addObject("institution", clientName(clientId));
//                    clientLogo(mv, clientId);
//                    mv.addObject("clientProfile", profile);
//                }
//                break;
//
//            case "DEPARTMENT":
//                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
//               session.setAttribute("companyLogo",Base64.getEncoder().encodeToString(department.getDepartmentLogo().getData()));
//                mv.addObject("institution", departmentName(clientId));
//                departmentLogo(mv, clientId);
//                mv.addObject("department", department);
//                break;
//            default:
//                LOG.info("USER ACCOUNT UNKNOWN");
//        }
//
//        mv.setViewName("myprofile");
//        return mv;
//    }
//
//    @RequestMapping("/view/department")
//    public ModelAndView viewDepartments(HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
//        ModelAndView mv = new ModelAndView();
//        mv.addObject("menutype", "header");
//        String clientId = (String) session.getAttribute("NsId");
//        if (clientId == null || clientId.isEmpty()) {
//            mv.setViewName("user-logout");
//            mv.addObject("messageType", "success");
//            mv.addObject("message", "Session expired, please login again");
//            return mv;
//        }
//        ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
//        mv.addObject("allDepartments", this.departmentService.findByClientProfile(profile));
//        mv.addObject("institution", clientName(clientId));
//        clientLogo(mv, clientId);
//        mv.setViewName("viewdepartment");
//        return mv;
//    }
//
//    Double getDepartmentTollSpending(Department department) {
//        double total = 0.00;
//        LocalDate today = LocalDate.now();
//        String month = today.getMonth().toString();
//        for (Transaction transaction : this.transactionService.findByDepartmentAndTransactionMonthAndTransactionYearAndTransactionDispute(department, month, today.getYear(), false)) {
//            total = total + transaction.getAmount();
//        }
//        return total;
//    }
//
//    Double getTollSpending(ClientProfile profile) {
//        double total = 0.00;
//        LocalDate today = LocalDate.now();
//        String month = today.getMonth().toString();
//        for (Transaction transaction : this.transactionService.findByClientProfileAndTransactionMonthAndTransactionYearAndTransactionDispute(profile, month, today.getYear(), false)) {
//            total = total + transaction.getAmount();
//        }
//        return total;
//    }
//
//    Double getDailyDepartmentTollSpending(Department department) {
//        double total = 0.00;
//        LocalDate today = LocalDate.now();
//        for (Transaction transaction : this.transactionService.findByDepartmentAndTransactionDateOrderByTransactionIdDesc(department, this.getConvertedDate(String.valueOf(today)))) {
//            Query query = new Query();
//            query.addCriteria(Criteria.where("transactionDate").is(transaction.getTransactionDate()));
//            query.addCriteria(Criteria.where("department").is(transaction.getDepartment()));
//            query.addCriteria(Criteria.where("transactionDispute").is(false));
//            List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class);
//            for (Transaction trans : transactionReport) {
//                total = total + trans.getAmount();
//            }
//        }
//        return total;
//    }
//
//    void setClientCitations(ClientProfile profile, ModelAndView mv) {
//        LocalDate today = LocalDate.now();
//        String month = today.getMonth().toString();
//        int yrs = today.getYear();
//        double total = 0.00;
//        CitationType type = this.citationTypeService.findByCitationTypeName("SPEED TICKET");
//        for (Citation citation : this.citationService.findByClientProfileAndCitationMonthAndCitationYearAndCitationTypeOrderByCitationIdDesc(profile, month, yrs, type)) {
//            total = total + citation.getCitationAmount();
//            LOG.info("Speed : " + String.valueOf(citation.getCitationAmount()));
//        }
//        mv.addObject("speedTicketCount", this.citationService.countByClientProfileAndCitationMonthAndCitationYearAndCitationType(profile, month, yrs, type));
//        mv.addObject("speedTicketAmount", total);
//        total = 0.00;
//        type = this.citationTypeService.findByCitationTypeName("PARKING");
//        for (Citation citation : this.citationService.findByClientProfileAndCitationMonthAndCitationYearAndCitationTypeOrderByCitationIdDesc(profile, month, yrs, type)) {
//            total = total + citation.getCitationAmount();
//        }
//        mv.addObject("parkingCount", this.citationService.countByClientProfileAndCitationMonthAndCitationYearAndCitationType(profile, month, yrs, type));
//        mv.addObject("parkingAmount", total);
//        total = 0.00;
//        type = this.citationTypeService.findByCitationTypeName("RED LIGHT");
//        for (Citation citation : this.citationService.findByClientProfileAndCitationMonthAndCitationYearAndCitationTypeOrderByCitationIdDesc(profile, month, yrs, type)) {
//            total = total + citation.getCitationAmount();
//        }
//        mv.addObject("redLightAmount", total);
//        mv.addObject("redLightCount", this.citationService.countByClientProfileAndCitationMonthAndCitationYearAndCitationType(profile, month, yrs, type));
//    }
//
//    void setDepartmentCitations(Department department, ModelAndView mv
//    ) {
//        LocalDate today = LocalDate.now();
//        String month = today.getMonth().toString();
//        int yrs = today.getYear();
//        double total = 0.00;
//        CitationType type = this.citationTypeService.findByCitationTypeName("SPEED TICKET");
//        for (Citation citation : this.citationService.findByDepartmentAndCitationMonthAndCitationYearAndCitationTypeOrderByCitationIdDesc(department, month, yrs, type)) {
//            total = total + citation.getCitationAmount();
//        }
//        mv.addObject("speedTicketAmount", total);
//        total = 0.00;
//        type = this.citationTypeService.findByCitationTypeName("PARKING");
//        for (Citation citation : this.citationService.findByDepartmentAndCitationMonthAndCitationYearAndCitationTypeOrderByCitationIdDesc(department, month, yrs, type)) {
//            total = total + citation.getCitationAmount();
//        }
//        mv.addObject("parkingAmount", total);
//        total = 0.00;
//        type = this.citationTypeService.findByCitationTypeName("RED LIGHT");
//        for (Citation citation : this.citationService.findByDepartmentAndCitationMonthAndCitationYearAndCitationTypeOrderByCitationIdDesc(department, month, yrs, type)) {
//            total = total + citation.getCitationAmount();
//        }
//        mv.addObject("redLightAmount", total);
//        mv.addObject("invoiceCount", this.invoiceService.countByDepartmentAndInvoiceMonthAndInvoiceYearAndInvoiceStat(department, month, yrs, "OPEN"));
//    }
//
//    Double getDailyClientTollSpending(ClientProfile profile
//    ) {
//        LocalDate today = LocalDate.now();
//        double total = 0.00;
//        for (Transaction transaction : this.transactionService.findByClientProfileAndTransactionDateOrderByTransactionIdDesc(profile, this.getConvertedDate(String.valueOf(today)))) {
//            Query query = new Query();
//            query.addCriteria(Criteria.where("transactionDate").is(transaction.getTransactionDate()));
//            query.addCriteria(Criteria.where("clientProfile").is(transaction.getClientProfile()));
//            query.addCriteria(Criteria.where("transactionDispute").is(false));
//            List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class);
//            for (Transaction trans : transactionReport) {
//                total = total + trans.getAmount();
//            }
//
//        }
//        return total;
//    }
//
//    @RequestMapping(path = "/load/departments/", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseEntity<?> loadDepartments(Model model, HttpSession session,
//            HttpServletRequest req, RedirectAttributes redir
//    ) {
//        JobResponse result = new JobResponse();
//        String clientId = (String) session.getAttribute("NsId");
//        if (clientId == null || clientId.isEmpty()) {
//            result.setMessage("Invalid session detected, please login again!");
//            result.setTitle("fail");
//            return ResponseEntity.ok(result);
//        }
//
//        String user_type = (String) session.getAttribute("ns_user");
//        switch (user_type) {
//            case "CLIENT":
//                ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
//                if (profile == null) {
//                    result.setMessage("Client ID : '" + clientId + "' not found in the system");
//                    result.setTitle("fail");
//                    return ResponseEntity.ok(result);
//                }
//                List<Department> departments = this.departmentService.findByClientProfile(profile);
//                if (departments == null || departments.isEmpty()) {
//                    result.setResults(departments);
//                    result.setMessage("Client : " + profile.getCompanyName() + " has no departments!");
//                    result.setTitle("fail");
//                    return ResponseEntity.ok(result);
//                }
//                result.setMessage("Available departments found");
//                result.setTitle("success");
//                result.setResults(departments);
//                break;
//            case "DEPARTMENT":
//                result.setResults(null);
//                result.setMessage("This is a department!");
//                result.setTitle("fail");
//                break;
//            default:
//                LOG.info("USER ACCOUNT UNKNOWN");
//        }
//        return ResponseEntity.ok(result);
//    }
//
//    List<Tollspendings> highClientSpenders(ClientProfile profile) {
//        LocalDate today = LocalDate.now();
//        String month = today.getMonth().toString();
//        int year = today.getYear();
//        Aggregation aggregation = newAggregation(
//                match(Criteria.where("transactionYear").is(year).and("transactionMonth").is(month).and("clientProfile").is(profile)),
//                group("vehicle").sum("amount").as("total").addToSet("vehicle").as("vehicle"),
//                sort(Sort.by("total").descending()),
//                limit(5)
//        );
//
//        AggregationResults<Tollspendings> groupResults = mongoTemplate.aggregate(
//                aggregation, Transaction.class, Tollspendings.class);
//
//        List<Tollspendings> expenditureReport = groupResults.getMappedResults();
//        return expenditureReport;
//    }
//
//    List<Tollspendings> highDepartmentSpenders(Department department) {
//        LocalDate today = LocalDate.now();
//        String month = today.getMonth().toString();
//        int year = today.getYear();
//        Aggregation aggregation = newAggregation(
//                match(Criteria.where("transactionYear").is(year).and("transactionMonth").is(month).and("department").is(department)),
//                group("vehicle").sum("amount").as("total").addToSet("vehicle").as("vehicle"),
//                sort(Sort.by("total").descending()),
//                limit(5)
//        );
//
//        AggregationResults<Tollspendings> groupResults = mongoTemplate.aggregate(
//                aggregation, Transaction.class, Tollspendings.class);
//
//        List<Tollspendings> expenditureReport = groupResults.getMappedResults();
//        return expenditureReport;
//    }   
//}
