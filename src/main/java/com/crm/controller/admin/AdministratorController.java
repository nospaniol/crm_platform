package com.crm.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.crm.generic.Nospaniol;
import com.crm.model.Application;
import com.crm.model.ClientProfile;
import com.crm.model.DashboardResponse;
import com.crm.model.Department;
import com.crm.model.DepartmentAccount;
import com.crm.model.DepartmentSaving;
import com.crm.model.Profile;
import com.crm.model.Tollspendings;
import com.crm.model.Transaction;
import com.crm.model.Vehicle;
import com.crm.model.VehicleFile;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("administrator")
public class AdministratorController extends Nospaniol {

    @RequestMapping(value = {"dashboard-redirect"}, method = RequestMethod.GET)
    public ModelAndView dashboard_redirector(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin-redirect");
        return mv;
    }

    @RequestMapping(value = {"dashboard"}, method = RequestMethod.GET)
    public String dashboard_panel(Model mv, HttpSession session) {
        LOG.info((String) session.getId());
        mv.addAttribute("institution", institutionName());
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.addAttribute("message", "Invalid session detected, please login again!");
            mv.addAttribute("messageType", "fail");
            return "user-logout";
        }
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            mv.addAttribute("messageType", "success");
            mv.addAttribute("message", "Administrator privileges only!");
            return "user-logout";
        }
        mv.addAttribute("vehicleCount", this.vehicleService.countAll());
        mv.addAttribute("clientCount", this.clientProfileService.countAll());
        mv.addAttribute("tollCount", this.getTwoDecimal(this.getTollSpending()));
        mv.addAttribute("transactionCount", this.transactionService.countByPostedDate(this.getCurrentDate()));
        return "admindashboard";

    }

    @RequestMapping(path = "/load/dashboard", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> dashboardDetails(HttpSession session) {
        DashboardResponse result = new DashboardResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        result.setVehicleCount(this.vehicleService.countAll());
        result.setClientCount(this.clientProfileService.countAll());
        result.setSpendingAmount(this.getTwoDecimal(this.getTollSpending()));
        result.setTransactionCount(this.transactionService.countByPostedDate(this.getCurrentDate()));
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = {"crmsynchroniser"}, method = RequestMethod.GET)
    public String crmsynchroniser(Model mv, HttpSession session) {
        mv.addAttribute("profile", new Profile());
        mv.addAttribute("institution", institutionName());
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.addAttribute("message", "Invalid session detected, please login again!");
            mv.addAttribute("messageType", "fail");
            return "user-logout";
        }
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            mv.addAttribute("messageType", "success");
            mv.addAttribute("message", "Administrator privileges only!");
            return "user-logout";
        }
        updateDepartment442AndOtherInShermco();
        return "addclient";
    }

    public void updateDepartment442AndOtherInShermco() {
        ClientProfile profile = this.clientProfileService.findByCompanyName("SHERMCO INDUSTRIES");
        Department department82 = this.departmentService.findByDepartmentName("DEPT.82");
        Department department442 = this.departmentService.findByDepartmentName("DEPT.442");
        Department departmentOther = this.departmentService.findByDepartmentName("OTHERS");

        DepartmentSaving saving = this.departmentSavingService.findByDepartment(department442);
        if (saving != null) {
            LOG.info("\n DELETING SAVING ACCOUNT FOR DEPARTMENT :: " + profile.getCompanyName() + "  :: " + department442.getDepartmentName());
            this.departmentSavingService.delete(saving);
            LOG.info("\n ************  DATA UPDATED *********** ".toUpperCase());
        }
        saving = this.departmentSavingService.findByDepartment(departmentOther);
        if (saving != null) {
            LOG.info("\n DELETING SAVING ACCOUNT FOR DEPARTMENT :: " + profile.getCompanyName() + "  :: " + departmentOther.getDepartmentName());
            this.departmentSavingService.delete(saving);
            LOG.info("\n ************  DATA UPDATED *********** ".toUpperCase());
        }

        DepartmentAccount account = this.departmentAccountService.findByDepartment(department442);
        if (account != null) {
            LOG.info("\n DELETING ACCOUNT FOR DEPARTMENT :: " + profile.getCompanyName() + "  :: " + department442.getDepartmentName());
            this.departmentAccountService.delete(account);
            LOG.info("\n ************  DATA UPDATED *********** ".toUpperCase());
        }
        account = this.departmentAccountService.findByDepartment(departmentOther);
        if (account != null) {
            LOG.info("\n DELETING ACCOUNT FOR DEPARTMENT :: " + profile.getCompanyName() + "  :: " + departmentOther.getDepartmentName());
            this.departmentAccountService.delete(account);
            LOG.info("\n ************  DATA UPDATED *********** ".toUpperCase());
        }

        for (Vehicle vehicle : this.vehicleService.findByClientProfileAndDepartmentOrderByVehicleIdDesc(profile, department442)) {
            LOG.info("\n UPDATING VEHICLE PLATE ::: ".toUpperCase() + vehicle.getLicensePlate() + "  FOR :: " + profile.getCompanyName() + "  :: " + department442.getDepartmentName());
            vehicle.setDepartment(department82);
            vehicle.setClientProfile(profile);
            this.vehicleService.save(vehicle);
            LOG.info("\n ************  DATA UPDATED *********** ".toUpperCase());
        }
        for (Vehicle vehicle : this.vehicleService.findByClientProfileAndDepartmentOrderByVehicleIdDesc(profile, departmentOther)) {
            LOG.info("\n UPDATING VEHICLE PLATE ::: ".toUpperCase() + vehicle.getLicensePlate() + "  FOR :: " + profile.getCompanyName() + "  :: " + departmentOther.getDepartmentName());
            vehicle.setDepartment(department82);
            vehicle.setClientProfile(profile);
            this.vehicleService.save(vehicle);
            LOG.info("\n ************  DATA UPDATED *********** ".toUpperCase());
        }
        for (VehicleFile vehicle : this.vehicleFileService.findByClientProfileAndDepartment(profile, departmentOther)) {
            LOG.info("\n UPDATING VEHICLE FILES ::: ".toUpperCase() + "  FOR :: " + profile.getCompanyName() + "  :: " + departmentOther.getDepartmentName());
            vehicle.setDepartment(department82);
            this.vehicleFileService.save(vehicle);
            LOG.info("\n ************  DATA UPDATED *********** ".toUpperCase());
        }
        for (VehicleFile vehicle : this.vehicleFileService.findByClientProfileAndDepartment(profile, departmentOther)) {
            LOG.info("\n UPDATING VEHICLE FILES ::: ".toUpperCase() + "  FOR :: " + profile.getCompanyName() + "  :: " + departmentOther.getDepartmentName());
            vehicle.setDepartment(department82);
            this.vehicleFileService.save(vehicle);
            LOG.info("\n ************  DATA UPDATED *********** ".toUpperCase());
        }
    }

    @RequestMapping(value = {"add_client"}, method = RequestMethod.GET)
    public String addClients(Model mv, HttpSession session) {
        mv.addAttribute("profile", new Profile());
        mv.addAttribute("institution", institutionName());
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.addAttribute("message", "Invalid session detected, please login again!");
            mv.addAttribute("messageType", "fail");
            return "user-logout";
        }
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            mv.addAttribute("messageType", "success");
            mv.addAttribute("message", "Administrator privileges only!");
            return "user-logout";
        }
        return "addclient";
    }

    @RequestMapping(value = {"view_applicants"}, method = RequestMethod.GET)
    public String viewApplicants(Model mv, Pageable pageable, HttpSession session) {
        mv.addAttribute("institution", institutionName());
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.addAttribute("message", "Invalid session detected, please login again!");
            mv.addAttribute("messageType", "fail");
            return "user-logout";
        }
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            mv.addAttribute("messageType", "success");
            mv.addAttribute("message", "Administrator privileges only!");
            return "user-logout";
        }
        List<Application> pages = this.applicationService.findAll();
        mv.addAttribute("data", pages);
        return "viewapplicants";
    }

    @RequestMapping(value = {"view_client"}, method = RequestMethod.GET)
    public String viewClients(Model mv, Pageable pageable, HttpSession session) {
        mv.addAttribute("institution", institutionName());
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.addAttribute("message", "Invalid session detected, please login again!");
            mv.addAttribute("messageType", "fail");
            return "user-logout";
        }
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            mv.addAttribute("messageType", "success");
            mv.addAttribute("message", "Administrator privileges only!");
            return "user-logout";
        }
        List<ClientProfile> pages = this.clientProfileService.findAll();
        mv.addAttribute("data", pages);
        return "viewclients";
    }

    @RequestMapping(value = {"add_staff"}, method = RequestMethod.GET)
    public String addUsers(Model mv, HttpSession session) {
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.addAttribute("message", "Invalid session detected, please login again!");
            mv.addAttribute("messageType", "fail");
            return "user-logout";
        }
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            mv.addAttribute("messageType", "success");
            mv.addAttribute("message", "Administrator privileges only!");
            return "user-logout";
        }
        mv.addAttribute("institution", institutionName());
        return "addstaff";
    }

    @RequestMapping(value = {"view_staff"}, method = RequestMethod.GET)
    public String viewUsers(Model mv, HttpSession session) {
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.addAttribute("message", "Invalid session detected, please login again!");
            mv.addAttribute("messageType", "fail");
            return "user-logout";
        }
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            mv.addAttribute("messageType", "success");
            mv.addAttribute("message", "Administrator privileges only!");
            return "user-logout";
        }
        mv.addAttribute("institution", institutionName());
        mv.addAttribute("allUsers", this.userService.findByAuthority(this.authorityService.findByRole("STAFF")));
        return "viewstaff";
    }

    @RequestMapping(value = {"config-view"}, method = RequestMethod.GET)
    public String configView(Model mv) {
        mv.addAttribute("institution", institutionName());
        return "config-view";
    }

    @RequestMapping(value = {"config-modify"}, method = RequestMethod.GET)
    public String configModify(Model mv) {
        mv.addAttribute("institution", institutionName());
        return "config-add";
    }

    @RequestMapping(value = {"console"}, method = RequestMethod.GET)
    public String consoleWindow(Model mv) {
        mv.addAttribute("institution", institutionName());
        return "console";
    }

    @RequestMapping(value = {"system"}, method = RequestMethod.GET)
    public String systems(Model mv) {
        mv.addAttribute("institution", institutionName());
        mv.addAttribute("listCompanys", this.companyService.findAll());
        mv.addAttribute("listNationalities", this.nationalityService.findAll());
        return "system";
    }

    @RequestMapping(value = {"logout"}, method = RequestMethod.GET)
    public String logout(Model mv) {
        return "admin-logout";
    }

    Double getTollSpending() {
        double total = 0.00;
        LocalDate today = LocalDate.now();
        String month = today.getMonth().toString();
        int year = today.getYear();
        Aggregation aggregation = newAggregation(
                match(Criteria.where("transactionYear").is(year).and("transactionMonth").is(month).and("transactionDispute").is(false)),
                group("transactionMonth").sum("amount").as("total")
        );
        AggregationResults<Tollspendings> groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspendings.class);
        List<Tollspendings> expenditureReport = groupResults.getMappedResults();
        if (expenditureReport == null || expenditureReport.isEmpty()) {
        } else {
            Tollspendings toll = expenditureReport.get(0);
            total = toll.getTotal();
        }
        return total;
    }

}
