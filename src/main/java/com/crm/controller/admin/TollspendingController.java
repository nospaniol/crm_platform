package com.crm.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.crm.generic.Nospaniol;
import static com.crm.generic.Nospaniol.LOG;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.SpendingResponse;
import com.crm.model.Tollspending;
import com.crm.model.Transaction;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("tollspending")
public class TollspendingController extends Nospaniol {

    @RequestMapping(value = {"monthly"}, method = RequestMethod.GET)
    public String viewClients(Model mv, Pageable pageable) {
        mv.addAttribute("institution", institutionName());
        Page<ClientProfile> pages = this.clientProfileService.findAll(pageable);
        mv.addAttribute("number", pages.getNumber());
        mv.addAttribute("totalPages", pages.getTotalPages());
        mv.addAttribute("totalElements", pages.getTotalElements());
        mv.addAttribute("size", pages.getSize());
        mv.addAttribute("data", pages.getContent());
        return "tollspendings";
    }

    @RequestMapping("/departments/{id}")
    public ModelAndView viewDepartments(@PathVariable("id") Long id, @PageableDefault(value = 10) Pageable pageable, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Administrator privileges only!");
            return mv;
        }
        ClientProfile profile = this.clientProfileService.findByClientProfileId(id);
        if (profile == null) {
            redir.addFlashAttribute("Client profile not found");
            redir.addFlashAttribute("fail");
            mv.setViewName("tolldepartmentspendings");
            return mv;
        } else {
            mv.addObject("department", new Department());
            mv.addObject("topic", "DEPARTMENTAL SPENDING FOR " + profile.getCompanyName());
            mv.addObject("clientId", profile.getClientProfileId());
            List<Department> pages = this.departmentService.findByClientProfile(profile);
            mv.addObject("data", pages);
            mv.addObject("institution", institutionName());
            mv.setViewName("tolldepartmentspendings");
            return mv;
        }
    }

    @RequestMapping("/expenditure/{id}")
    public ModelAndView viewExpenditure(@PathVariable("id") Long id, @PageableDefault(value = 10) Pageable pageable, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Administrator privileges only!");
            return mv;
        }
        ClientProfile profile = this.clientProfileService.findByClientProfileId(id);
        if (profile == null) {
            redir.addFlashAttribute("Client not found");
            redir.addFlashAttribute("fail");
            mv.setViewName("departmenttollspending");
            return mv;
        } else {
            LocalDate today = LocalDate.now();
            double total = 0.00;
            String month = today.getMonth().toString();
            int year = today.getYear();
            Aggregation aggregation = newAggregation(
                    match(Criteria.where("clientProfile").is(profile).and("postedMonth").is(month).and("postedYear").is(year).and("transactionDispute").is(false)),
                    group("vehicle").sum("amount").as("amount").addToSet("vehicle").as("vehicle")
            );
            AggregationResults<Tollspending> groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
            List<Tollspending> spendings = groupResults.getMappedResults();
            mv.addObject("department", new Department());
            mv.addObject("topic", today.getMonth().toString() + " / " + String.valueOf(today.getYear()) + " CLIENT SPENDING FOR " + profile.getCompanyName());
            mv.addObject("clientId", profile.getClientProfileId());
            mv.addObject("total", this.getTwoDecimal(total));
            mv.addObject("innovative_title", "License Plates");
            mv.addObject("allSpendings", spendings);
            mv.addObject("institution", institutionName());
            mv.setViewName("spending");
            return mv;
        }
    }

    @RequestMapping("/department/expenditure/{id}")
    public ModelAndView viewDepartmentExpenditure(@PathVariable("id") Long id, @PageableDefault(value = 10) Pageable pageable, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Administrator privileges only!");
            return mv;
        }
        Department department = this.departmentService.findByDepartmentId(id);
        if (department == null) {
            redir.addFlashAttribute("Client profile not found");
            redir.addFlashAttribute("fail");
            mv.setViewName("departmenttollspending");
            return mv;
        } else {
            LocalDate today = LocalDate.now();
            String month = today.getMonth().toString();
            int yrs = today.getYear();
            mv.addObject("department", new Department());
            mv.addObject("topic", today.getMonth().toString() + " / " + String.valueOf(today.getYear()) + " DEPARTMENT SPENDING FOR " + department.getDepartmentName());
            mv.addObject("departmentId", department.getDepartmentId());
            List<String> existingFilter = new ArrayList<>();
            double total = 0.00;
            Aggregation aggregation = newAggregation(
                    match(Criteria.where("department").is(department).and("postedMonth").is(month).and("postedYear").is(yrs).and("transactionDispute").is(false)),
                    group("vehicle").sum("amount").as("amount").addToSet("vehicle").as("vehicle")
            );
            AggregationResults<Tollspending> groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
            List<Tollspending> spendings = groupResults.getMappedResults();

            mv.addObject("total", this.getTwoDecimal(total));
            mv.addObject("innovative_title", "License Plates");
            mv.addObject("allSpendings", spendings);
            mv.addObject("institution", institutionName());
            mv.setViewName("departmentspending");
            return mv;
        }
    }

    @RequestMapping(path = "/filter/monthly/by/{clientId}/{month}/{year}/{filter}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchClientMonthly(@PathVariable("clientId") Long clientId, @PathVariable("month") String month, @PathVariable("year") String year, @PathVariable("filter") String filter) {
        SpendingResponse result = new SpendingResponse();
        LOG.info("Monthly client request processing..");
        ClientProfile profile = this.clientProfileService.findByClientProfileId(clientId);
        if (profile == null) {
            result.setMessage("Client not found");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            int yrs = Integer.valueOf(year);
            result.setPageTitle(month + " / " + String.valueOf(year) + " CLIENT SPENDING FOR " + profile.getCompanyName());
            List<String> existingFilter = new ArrayList<>();
            List<Tollspending> spendings = new ArrayList<>();
            double total = 0.00;
            switch (filter) {
                case "licensePlate":
                    result.setTableTitle("License Plate");
                    Aggregation aggregation = newAggregation(
                            match(Criteria.where("clientProfile").is(profile).and("postedMonth").is(month).and("postedYear").is(yrs).and("transactionDispute").is(false)),
                            group("vehicle").sum("amount").as("amount").addToSet("vehicle").as("vehicle")
                    );
                    AggregationResults<Tollspending> groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
                    spendings = groupResults.getMappedResults();
                    break;
                case "exitLane":
                    result.setTableTitle("Exit Lane");
                    aggregation = newAggregation(
                            match(Criteria.where("clientProfile").is(profile).and("postedMonth").is(month).and("postedYear").is(yrs).and("transactionDispute").is(false)),
                            group("exitLane").sum("amount").as("amount").addToSet("exitLane").as("title")
                    );
                    groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
                    spendings = groupResults.getMappedResults();
                    break;
                case "exitLocation":
                    result.setTableTitle("Location");
                    aggregation = newAggregation(
                            match(Criteria.where("clientProfile").is(profile).and("postedMonth").is(month).and("postedYear").is(yrs).and("transactionDispute").is(false)),
                            group("exitLocation").sum("amount").as("amount").addToSet("exitLocation").as("title")
                    );
                    groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
                    spendings = groupResults.getMappedResults();
                    break;
                case "exitDateTime":
                    result.setTableTitle("Date");
                    aggregation = newAggregation(
                            match(Criteria.where("clientProfile").is(profile).and("postedMonth").is(month).and("postedYear").is(yrs).and("transactionDispute").is(false)),
                            group("exitDateTime").sum("amount").as("amount").addToSet("exitDateTime").as("title")
                    );
                    groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
                    spendings = groupResults.getMappedResults();
                    break;
                case "agency":
                    result.setTableTitle("Agency");
                    aggregation = newAggregation(
                            match(Criteria.where("clientProfile").is(profile).and("postedMonth").is(month).and("postedYear").is(yrs).and("transactionDispute").is(false)),
                            group("agency").sum("amount").as("amount").addToSet("agency").as("title")
                    );
                    groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
                    spendings = groupResults.getMappedResults();
                    break;
                case "state":
                    result.setTableTitle("State");
                    aggregation = newAggregation(
                            match(Criteria.where("clientProfile").is(profile).and("postedMonth").is(month).and("postedYear").is(yrs).and("transactionDispute").is(false)),
                            group("state").sum("amount").as("amount").addToSet("state").as("title")
                    );
                    groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
                    spendings = groupResults.getMappedResults();
                    break;
                default:
                    LOG.info("NO FILTER WAS FOUND");
                    break;
            }
            result.setTitle("success");
            result.setResults(spendings);
            return ResponseEntity.ok(result);
        }
    }

    @RequestMapping(path = "/filter/department/monthly/by/{departmentId}/{month}/{year}/{filter}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchClientDepartmentMonthly(@PathVariable("departmentId") Long departmentId, @PathVariable("month") String month, @PathVariable("year") String year, @PathVariable("filter") String filter) {
        SpendingResponse result = new SpendingResponse();
        LOG.info("Monthly department request processing..");
        Department department = this.departmentService.findByDepartmentId(departmentId);
        if (department == null) {
            result.setMessage("Department not found");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            int yrs = Integer.valueOf(year);
            result.setPageTitle(month + " / " + String.valueOf(year) + " DEPARTMENT SPENDING FOR " + department.getDepartmentName());
            List<String> existingFilter = new ArrayList<>();
            List<Tollspending> spendings = new ArrayList<>();
            double total = 0.00;
            switch (filter) {
                case "licensePlate":
                    result.setTableTitle("License Plate");
                    Aggregation aggregation = newAggregation(
                            match(Criteria.where("department").is(department).and("postedMonth").is(month).and("postedYear").is(yrs).and("transactionDispute").is(false)),
                            group("vehicle").sum("amount").as("amount").addToSet("vehicle").as("vehicle")
                    );
                    AggregationResults<Tollspending> groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
                    spendings = groupResults.getMappedResults();
                    break;
                case "exitLane":
                    result.setTableTitle("Exit Lane");
                    aggregation = newAggregation(
                            match(Criteria.where("department").is(department).and("postedMonth").is(month).and("postedYear").is(yrs).and("transactionDispute").is(false)),
                            group("exitLane").sum("amount").as("amount").addToSet("exitLane").as("title")
                    );
                    groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
                    spendings = groupResults.getMappedResults();
                    break;
                case "exitLocation":
                    result.setTableTitle("Location");
                    aggregation = newAggregation(
                            match(Criteria.where("department").is(department).and("postedMonth").is(month).and("postedYear").is(yrs).and("transactionDispute").is(false)),
                            group("exitLocation").sum("amount").as("amount").addToSet("exitLocation").as("title")
                    );
                    groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
                    spendings = groupResults.getMappedResults();
                    break;
                case "exitDateTime":
                    result.setTableTitle("Date");
                    aggregation = newAggregation(
                            match(Criteria.where("department").is(department).and("postedMonth").is(month).and("postedYear").is(yrs).and("transactionDispute").is(false)),
                            group("exitDateTime").sum("amount").as("amount").addToSet("exitDateTime").as("title")
                    );
                    groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
                    spendings = groupResults.getMappedResults();
                    break;
                case "agency":
                    result.setTableTitle("Agency");
                    aggregation = newAggregation(
                            match(Criteria.where("department").is(department).and("postedMonth").is(month).and("postedYear").is(yrs).and("transactionDispute").is(false)),
                            group("agency").sum("amount").as("amount").addToSet("agency").as("title")
                    );
                    groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
                    spendings = groupResults.getMappedResults();
                    break;
                case "state":
                    result.setTableTitle("State");
                    aggregation = newAggregation(
                            match(Criteria.where("department").is(department).and("postedMonth").is(month).and("postedYear").is(yrs).and("transactionDispute").is(false)),
                            group("state").sum("amount").as("amount").addToSet("state").as("title")
                    );
                    groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
                    spendings = groupResults.getMappedResults();
                    break;
                default:
                    LOG.info("NO FILTER WAS FOUND");
                    break;
            }
            result.setTotal(String.valueOf(total));
            result.setTitle("success");
            result.setResults(spendings);
            return ResponseEntity.ok(result);
        }
    }

    /*
    
    
    ANNUAL SPENDINGS
    
     */
    @RequestMapping(value = {"annually"}, method = RequestMethod.GET)
    public String viewAnnualClients(Model mv, Pageable pageable) {
        mv.addAttribute("institution", institutionName());
        Page<ClientProfile> pages = this.clientProfileService.findAll(pageable);
        mv.addAttribute("number", pages.getNumber());
        mv.addAttribute("totalPages", pages.getTotalPages());
        mv.addAttribute("totalElements", pages.getTotalElements());
        mv.addAttribute("size", pages.getSize());
        mv.addAttribute("data", pages.getContent());
        return "annualtollspendings";
    }

    @RequestMapping("/annual/departments/{id}")
    public ModelAndView viewAnnualDepartments(@PathVariable("id") Long id, @PageableDefault(value = 10) Pageable pageable, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        ClientProfile profile = this.clientProfileService.findByClientProfileId(id);
        if (profile == null) {
            redir.addFlashAttribute("Department not found");
            redir.addFlashAttribute("fail");
            mv.setViewName("annualtolldepartmentspendings");
            return mv;
        } else {
            mv.addObject("department", new Department());
            mv.addObject("topic", "DEPARTMENTAL SPENDING FOR " + profile.getCompanyName());
            mv.addObject("clientId", profile.getClientProfileId());
            List<Department> pages = this.departmentService.findByClientProfile(profile);
            mv.addObject("data", pages);
            mv.addObject("institution", institutionName());
            mv.setViewName("annualtolldepartmentspendings");
            return mv;
        }
    }

    @RequestMapping("/annual/expenditure/{id}")
    public ModelAndView viewAnnualExpenditure(@PathVariable("id") Long id, @PageableDefault(value = 10) Pageable pageable, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        ClientProfile profile = this.clientProfileService.findByClientProfileId(id);
        if (profile == null) {
            redir.addFlashAttribute("Client profile not found");
            redir.addFlashAttribute("fail");
            mv.setViewName("annualspending");
            return mv;
        } else {
            LocalDate today = LocalDate.now();
            mv.addObject("department", new Department());
            mv.addObject("topic", String.valueOf(today.getYear()) + " CLIENT SPENDING FOR " + profile.getCompanyName());
            mv.addObject("clientId", profile.getClientProfileId());
            List<String> existingFilter = new ArrayList<>();
            double total = 0.00;
            String month = today.getMonth().toString();
            int year = today.getYear();
            Aggregation aggregation = newAggregation(
                    match(Criteria.where("clientProfile").is(profile).and("postedYear").is(year).and("transactionDispute").is(false)),
                    group("vehicle").sum("amount").as("amount").addToSet("vehicle").as("vehicle")
            );
            AggregationResults<Tollspending> groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
            List<Tollspending> spendings = groupResults.getMappedResults();
            mv.addObject("total", this.getTwoDecimal(total));
            mv.addObject("innovative_title", "License Plates");
            mv.addObject("allSpendings", spendings);
            mv.addObject("institution", institutionName());
            mv.setViewName("annualspending");
            return mv;
        }
    }

    @RequestMapping("/annual/department/expenditure/{id}")
    public ModelAndView viewAnnualDepartmentExpenditure(@PathVariable("id") Long id, @PageableDefault(value = 10) Pageable pageable, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        Department department = this.departmentService.findByDepartmentId(id);
        if (department == null) {
            redir.addFlashAttribute("Department not found");
            redir.addFlashAttribute("fail");
            mv.setViewName("annualdepartmentspending");
            return mv;
        } else {
            LocalDate today = LocalDate.now();
            mv.addObject("department", new Department());
            mv.addObject("topic", today.getMonth().toString() + " / " + String.valueOf(today.getYear()) + " DEPARTMENT SPENDING FOR " + department.getDepartmentName());
            mv.addObject("departmentId", department.getDepartmentId());
            int year = today.getYear();
            Aggregation aggregation = newAggregation(
                    match(Criteria.where("department").is(department).and("postedYear").is(year).and("transactionDispute").is(false)),
                    group("vehicle").sum("amount").as("amount").addToSet("vehicle").as("vehicle")
            );
            AggregationResults<Tollspending> groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
            List<Tollspending> spendings = groupResults.getMappedResults();
            //mv.addObject("total", this.getTwoDecimal(total));
            mv.addObject("innovative_title", "License Plates");
            mv.addObject("allSpendings", spendings);
            mv.addObject("institution", institutionName());
            mv.setViewName("annualdepartmentspending");
            return mv;
        }
    }

    @RequestMapping(path = "/filter/annual/view/by/{clientId}/{year}/{filter}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchClientAnnually(@PathVariable("clientId") Long clientId, @PathVariable("year") String year, @PathVariable("filter") String filter) {
        SpendingResponse result = new SpendingResponse();
        LOG.info("Annual client request processing..");
        ClientProfile profile = this.clientProfileService.findByClientProfileId(clientId);
        if (profile == null) {
            result.setMessage("Client profile not found");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            int yrs = Integer.valueOf(year);
            result.setPageTitle(String.valueOf(year) + " CLIENT SPENDING FOR " + profile.getCompanyName());
            List<String> existingFilter = new ArrayList<>();
            List<Tollspending> spendings = new ArrayList<>();
            double total = 0.00;
            switch (filter) {
                case "licensePlate":
                    result.setTableTitle("License Plate");
                    Aggregation aggregation = newAggregation(
                            match(Criteria.where("clientProfile").is(profile).and("postedYear").is(yrs).and("transactionDispute").is(false)),
                            group("vehicle").sum("amount").as("amount").addToSet("vehicle").as("vehicle")
                    );
                    AggregationResults<Tollspending> groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
                    spendings = groupResults.getMappedResults();
                    break;
                case "exitLane":
                    result.setTableTitle("Exit Lane");
                    aggregation = newAggregation(
                            match(Criteria.where("clientProfile").is(profile).and("postedYear").is(yrs).and("transactionDispute").is(false)),
                            group("exitLane").sum("amount").as("amount").addToSet("exitLane").as("title")
                    );
                    groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
                    spendings = groupResults.getMappedResults();
                    break;
                case "exitLocation":
                    result.setTableTitle("Location");
                    aggregation = newAggregation(
                            match(Criteria.where("clientProfile").is(profile).and("postedYear").is(yrs).and("transactionDispute").is(false)),
                            group("exitLocation").sum("amount").as("amount").addToSet("exitLocation").as("title")
                    );
                    groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
                    spendings = groupResults.getMappedResults();
                    break;
                case "exitDateTime":
                    result.setTableTitle("Date");
                    aggregation = newAggregation(
                            match(Criteria.where("clientProfile").is(profile).and("postedYear").is(yrs).and("transactionDispute").is(false)),
                            group("exitDateTime").sum("amount").as("amount").addToSet("exitDateTime").as("title")
                    );
                    groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
                    spendings = groupResults.getMappedResults();
                    break;
                case "agency":
                    result.setTableTitle("Agency");
                    aggregation = newAggregation(
                            match(Criteria.where("clientProfile").is(profile).and("postedYear").is(yrs).and("transactionDispute").is(false)),
                            group("agency").sum("amount").as("amount").addToSet("agency").as("title")
                    );
                    groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
                    spendings = groupResults.getMappedResults();
                    break;
                case "state":
                    result.setTableTitle("State");
                    aggregation = newAggregation(
                            match(Criteria.where("clientProfile").is(profile).and("postedYear").is(yrs).and("transactionDispute").is(false)),
                            group("state").sum("amount").as("amount").addToSet("state").as("title")
                    );
                    groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
                    spendings = groupResults.getMappedResults();
                    break;
                default:
                    LOG.info("NO FILTER WAS FOUND");
                    break;
            }
            result.setTotal(String.valueOf(total));
            result.setTitle("success");
            result.setResults(spendings);
            return ResponseEntity.ok(result);
        }
    }

    @RequestMapping(path = "/filter/annual/view/department/by/{departmentId}/{year}/{filter}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchClientDepartmentAnnually(@PathVariable("departmentId") Long departmentId, @PathVariable("year") String year, @PathVariable("filter") String filter) {
        SpendingResponse result = new SpendingResponse();
        LOG.info("Annual department request processing..");
        Department department = this.departmentService.findByDepartmentId(departmentId);
        if (department == null) {
            result.setMessage("Department not found");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            int yrs = Integer.valueOf(year);
            result.setPageTitle(String.valueOf(year) + " DEPARTMENT SPENDING FOR " + department.getDepartmentName());
            List<String> existingFilter = new ArrayList<>();
            List<Tollspending> spendings = new ArrayList<>();
            double total = 0.00;
            switch (filter) {
                case "licensePlate":
                    result.setTableTitle("License Plate");
                    Aggregation aggregation = newAggregation(
                            match(Criteria.where("department").is(department).and("postedYear").is(yrs).and("transactionDispute").is(false)),
                            group("vehicle").sum("amount").as("amount").addToSet("vehicle").as("vehicle")
                    );
                    AggregationResults<Tollspending> groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
                    spendings = groupResults.getMappedResults();
                    break;
                case "exitLane":
                    result.setTableTitle("Exit Lane");
                    aggregation = newAggregation(
                            match(Criteria.where("department").is(department).and("postedYear").is(yrs).and("transactionDispute").is(false)),
                            group("exitLane").sum("amount").as("amount").addToSet("exitLane").as("title")
                    );
                    groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
                    spendings = groupResults.getMappedResults();
                    break;
                case "exitLocation":
                    result.setTableTitle("Location");
                    aggregation = newAggregation(
                            match(Criteria.where("department").is(department).and("postedYear").is(yrs).and("transactionDispute").is(false)),
                            group("exitLocation").sum("amount").as("amount").addToSet("exitLocation").as("title")
                    );
                    groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
                    spendings = groupResults.getMappedResults();
                    break;
                case "exitDateTime":
                    result.setTableTitle("Date");
                    aggregation = newAggregation(
                            match(Criteria.where("department").is(department).and("postedYear").is(yrs).and("transactionDispute").is(false)),
                            group("exitDateTime").sum("amount").as("amount").addToSet("exitDateTime").as("title")
                    );
                    groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
                    spendings = groupResults.getMappedResults();
                    break;
                case "agency":
                    result.setTableTitle("Agency");
                    aggregation = newAggregation(
                            match(Criteria.where("department").is(department).and("postedYear").is(yrs).and("transactionDispute").is(false)),
                            group("agency").sum("amount").as("amount").addToSet("agency").as("title")
                    );
                    groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
                    spendings = groupResults.getMappedResults();
                    break;
                case "state":
                    result.setTableTitle("State");
                    aggregation = newAggregation(
                            match(Criteria.where("department").is(department).and("postedYear").is(yrs).and("transactionDispute").is(false)),
                            group("state").sum("amount").as("amount").addToSet("state").as("title")
                    );
                    groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspending.class);
                    spendings = groupResults.getMappedResults();
                    break;
                default:
                    LOG.info("NO FILTER WAS FOUND");
                    break;
            }
            result.setTotal(String.valueOf(total));
            result.setTitle("success");
            result.setResults(spendings);
            return ResponseEntity.ok(result);
        }
    }

}
