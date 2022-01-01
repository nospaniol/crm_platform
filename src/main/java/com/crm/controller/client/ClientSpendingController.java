package com.crm.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.crm.generic.Nospaniol;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.SpendingResponse;
import com.crm.model.Tollspending;
import com.crm.model.Transaction;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("client_spending")
public class ClientSpendingController extends Nospaniol {

    @RequestMapping("/departments/")
    public ModelAndView viewDepartments(HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "spending");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
        if (profile == null) {
            redir.addFlashAttribute("Client profile not found");
            redir.addFlashAttribute("fail");
            mv.setViewName("clienttolldepartmentspendings");
            return mv;
        } else {
            //mv.addObject("institution", profile.getCompanyName());

            mv.addObject("department", new Department());
            mv.addObject("topic", "DEPARTMENTAL SPENDING FOR " + profile.getCompanyName());
            mv.addObject("clientId", profile.getClientProfileId());
            List<Department> pages = this.departmentService.findByClientProfile(profile);
            mv.addObject("data", pages);
            mv.setViewName("clienttolldepartmentspendings");
            return mv;
        }
    }

    @RequestMapping("/expenditure")
    public ModelAndView viewExpenditure(HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "spending");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                String departmentMode = (String) session.getAttribute("department_mode");
                String departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));

                    LocalDate today = LocalDate.now();
                    mv.addObject("department", new Department());
                    mv.addObject("topic", today.getMonth().toString() + " / " + String.valueOf(today.getYear()) + " CLIENT SPENDING FOR " + department.getDepartmentName());
                    mv.addObject("clientId", department.getDepartmentId());
                    List<String> existingFilter = new ArrayList<>();
                    List<Tollspending> spendings = new ArrayList<>();
                    double total = 0.00;
                    for (Transaction transaction : this.transactionService.findByDepartmentAndTransactionMonthAndTransactionYear(department, today.getMonth().toString(), today.getYear())) {
                        double sum = 0.00;
                        if (!existingFilter.contains(transaction.getVehicle().getLicensePlate())) {
                            //LOG.info("PROCESSING FOR PLATE     ::::::: " + transaction.getVehicle().getLicensePlate());
                            existingFilter.add(transaction.getVehicle().getLicensePlate());
                            Query query = new Query();
                            query.addCriteria(Criteria.where("vehicle").is(transaction.getVehicle()));
                            query.addCriteria(Criteria.where("transactionMonth").is(transaction.getTransactionMonth()));
                            query.addCriteria(Criteria.where("transactionYear").is(transaction.getTransactionYear()));
                            query.addCriteria(Criteria.where("clientProfile").is(transaction.getClientProfile()));
                            List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class);
                            for (Transaction trans : transactionReport) {
                                sum = sum + trans.getAmount();
                            }
                            //LOG.info("PLATE     ::::::: " + transaction.getVehicle().getLicensePlate() + "  :::::::::   " + sum);
                            Tollspending spending = new Tollspending();
                            total = total + sum;
                            spending.setAmount(this.getTwoDecimal(sum));
                            spending.setTitle(transaction.getVehicle().getLicensePlate());
                            spendings.add(spending);
                        }

                    }
                    mv.addObject("total", this.getTwoDecimal(total));
                    mv.addObject("innovative_title", "License Plates");
                    mv.addObject("allSpendings", spendings);
                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    if (profile == null) {
                        mv.addObject("message", "Client profile not found");
                        mv.addObject("messageType", "fail");
                        mv.setViewName("clientspending");
                        return mv;
                    } else {
                        //mv.addObject("institution", profile.getCompanyName());

                        LocalDate today = LocalDate.now();
                        mv.addObject("department", new Department());
                        mv.addObject("topic", today.getMonth().toString() + " / " + String.valueOf(today.getYear()) + " CLIENT SPENDING FOR " + profile.getCompanyName());
                        mv.addObject("clientId", profile.getClientProfileId());
                        List<String> existingFilter = new ArrayList<>();
                        List<Tollspending> spendings = new ArrayList<>();
                        double total = 0.00;
                        for (Transaction transaction : this.transactionService.findByClientProfileAndTransactionMonthAndTransactionYear(profile, today.getMonth().toString(), today.getYear())) {
                            double sum = 0.00;
                            if (!existingFilter.contains(transaction.getVehicle().getLicensePlate())) {
                                existingFilter.add(transaction.getVehicle().getLicensePlate());
                                Query query = new Query();
                                query.addCriteria(Criteria.where("vehicle").is(transaction.getVehicle()));
                                query.addCriteria(Criteria.where("transactionMonth").is(transaction.getTransactionMonth()));
                                query.addCriteria(Criteria.where("transactionYear").is(transaction.getTransactionYear()));
                                query.addCriteria(Criteria.where("clientProfile").is(transaction.getClientProfile()));
                                List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class);
                                for (Transaction trans : transactionReport) {
                                    sum = sum + trans.getAmount();
                                }
                                Tollspending spending = new Tollspending();
                                total = total + sum;
                                spending.setAmount(this.getTwoDecimal(sum));
                                spending.setTitle(transaction.getVehicle().getLicensePlate());
                                spendings.add(spending);
                            }
                        }
                        mv.addObject("total", this.getTwoDecimal(total));
                        mv.addObject("innovative_title", "License Plates");
                        mv.addObject("allSpendings", spendings);
                    }
                }
                break;
            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                if (department == null) {
                    mv.addObject("message", "Department not found");
                    mv.addObject("messageType", "fail");
                    mv.setViewName("clientspending");
                    return mv;
                } else {

                    LocalDate today = LocalDate.now();
                    mv.addObject("department", new Department());
                    mv.addObject("topic", today.getMonth().toString() + " / " + String.valueOf(today.getYear()) + " CLIENT SPENDING FOR " + department.getDepartmentName());
                    mv.addObject("clientId", department.getDepartmentId());
                    List<String> existingFilter = new ArrayList<>();
                    List<Tollspending> spendings = new ArrayList<>();
                    double total = 0.00;
                    for (Transaction transaction : this.transactionService.findByDepartmentAndTransactionMonthAndTransactionYear(department, today.getMonth().toString(), today.getYear())) {
                        double sum = 0.00;
                        if (!existingFilter.contains(transaction.getVehicle().getLicensePlate())) {
                            //LOG.info("PROCESSING FOR PLATE     ::::::: " + transaction.getVehicle().getLicensePlate());
                            existingFilter.add(transaction.getVehicle().getLicensePlate());
                            Query query = new Query();
                            query.addCriteria(Criteria.where("vehicle").is(transaction.getVehicle()));
                            query.addCriteria(Criteria.where("transactionMonth").is(transaction.getTransactionMonth()));
                            query.addCriteria(Criteria.where("transactionYear").is(transaction.getTransactionYear()));
                            query.addCriteria(Criteria.where("clientProfile").is(transaction.getClientProfile()));
                            List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class);
                            for (Transaction trans : transactionReport) {
                                sum = sum + trans.getAmount();
                            }
                            //LOG.info("PLATE     ::::::: " + transaction.getVehicle().getLicensePlate() + "  :::::::::   " + sum);
                            Tollspending spending = new Tollspending();
                            total = total + sum;
                            spending.setAmount(this.getTwoDecimal(sum));
                            spending.setTitle(transaction.getVehicle().getLicensePlate());
                            spendings.add(spending);
                        }

                    }
                    mv.addObject("total", this.getTwoDecimal(total));
                    mv.addObject("innovative_title", "License Plates");
                    mv.addObject("allSpendings", spendings);

                }
                break;
            default:
                LOG.info("USER ACCOUNT UNKNOWN");
        }
        mv.setViewName("clientspending");
        return mv;
    }

    @RequestMapping("/department/expenditure/{id}")
    public ModelAndView viewDepartmentExpenditure(@PathVariable("id") Long id, @PageableDefault(value = 10) Pageable pageable, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "spending");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                String departmentMode = (String) session.getAttribute("department_mode");
                String departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));

                    LocalDate today = LocalDate.now();
                    mv.addObject("department", new Department());
                    mv.addObject("topic", today.getMonth().toString() + " / " + String.valueOf(today.getYear()) + " DEPARTMENT SPENDING FOR " + department.getDepartmentName());
                    mv.addObject("departmentId", department.getDepartmentId());
                    List<String> existingFilter = new ArrayList<>();
                    List<Tollspending> spendings = new ArrayList<>();
                    double total = 0.00;
                    for (Transaction transaction : this.transactionService.findByDepartmentAndTransactionMonthAndTransactionYear(department, today.getMonth().toString(), today.getYear())) {
                        double sum = 0.00;
                        if (!existingFilter.contains(transaction.getVehicle().getLicensePlate())) {
                            //LOG.info("PROCESSING FOR PLATE     ::::::: " + transaction.getVehicle().getLicensePlate());
                            existingFilter.add(transaction.getVehicle().getLicensePlate());
                            Query query = new Query();
                            query.addCriteria(Criteria.where("vehicle").is(transaction.getVehicle()));
                            query.addCriteria(Criteria.where("transactionMonth").is(transaction.getTransactionMonth()));
                            query.addCriteria(Criteria.where("transactionYear").is(transaction.getTransactionYear()));
                            query.addCriteria(Criteria.where("department").is(transaction.getDepartment()));
                            List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class);
                            for (Transaction trans : transactionReport) {
                                sum = sum + trans.getAmount();
                            }
                            Tollspending spending = new Tollspending();
                            total = total + sum;
                            spending.setAmount(this.getTwoDecimal(sum));
                            spending.setTitle(transaction.getVehicle().getLicensePlate());
                            spendings.add(spending);
                        }

                    }
                    mv.addObject("total", this.getTwoDecimal(total));
                    mv.addObject("innovative_title", "License Plates");
                    mv.addObject("allSpendings", spendings);
                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    if (profile == null) {
                        mv.addObject("message", "Client profile not found");
                        mv.addObject("messageType", "fail");
                        mv.setViewName("clientdepartmentspending");
                        return mv;
                    } else {
                        //mv.addObject("institution", profile.getCompanyName());

                        Department department = this.departmentService.findByDepartmentId(id);
                        if (department == null) {
                            redir.addFlashAttribute("Department not found");
                            redir.addFlashAttribute("fail");
                            mv.setViewName("clientdepartmenttollspending");
                            LocalDate today = LocalDate.now();
                            mv.addObject("department", new Department());
                            mv.addObject("topic", today.getMonth().toString() + " / " + String.valueOf(today.getYear()) + " DEPARTMENT SPENDING FOR " + department.getDepartmentName());
                            mv.addObject("departmentId", department.getDepartmentId());
                            List<String> existingFilter = new ArrayList<>();
                            List<Tollspending> spendings = new ArrayList<>();
                            double total = 0.00;
                            for (Transaction transaction : this.transactionService.findByDepartmentAndTransactionMonthAndTransactionYear(department, today.getMonth().toString(), today.getYear())) {
                                double sum = 0.00;
                                if (!existingFilter.contains(transaction.getVehicle().getLicensePlate())) {
                                    //LOG.info("PROCESSING FOR PLATE     ::::::: " + transaction.getVehicle().getLicensePlate());
                                    existingFilter.add(transaction.getVehicle().getLicensePlate());
                                    Query query = new Query();
                                    query.addCriteria(Criteria.where("vehicle").is(transaction.getVehicle()));
                                    query.addCriteria(Criteria.where("transactionMonth").is(transaction.getTransactionMonth()));
                                    query.addCriteria(Criteria.where("transactionYear").is(transaction.getTransactionYear()));
                                    query.addCriteria(Criteria.where("department").is(transaction.getDepartment()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class);
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    total = total + sum;
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getVehicle().getLicensePlate());
                                    spendings.add(spending);
                                }

                            }
                            mv.addObject("total", this.getTwoDecimal(total));
                            mv.addObject("innovative_title", "License Plates");
                            mv.addObject("allSpendings", spendings);
                        } else {
                            LocalDate today = LocalDate.now();
                            mv.addObject("department", new Department());
                            mv.addObject("topic", today.getMonth().toString() + " / " + String.valueOf(today.getYear()) + " DEPARTMENT SPENDING FOR " + department.getDepartmentName());
                            mv.addObject("departmentId", department.getDepartmentId());
                            List<String> existingFilter = new ArrayList<>();
                            List<Tollspending> spendings = new ArrayList<>();
                            double total = 0.00;
                            for (Transaction transaction : this.transactionService.findByDepartmentAndTransactionMonthAndTransactionYear(department, today.getMonth().toString(), today.getYear())) {
                                double sum = 0.00;
                                if (!existingFilter.contains(transaction.getVehicle().getLicensePlate())) {
                                    //LOG.info("PROCESSING FOR PLATE     ::::::: " + transaction.getVehicle().getLicensePlate());
                                    existingFilter.add(transaction.getVehicle().getLicensePlate());
                                    Query query = new Query();
                                    query.addCriteria(Criteria.where("vehicle").is(transaction.getVehicle()));
                                    query.addCriteria(Criteria.where("transactionMonth").is(transaction.getTransactionMonth()));
                                    query.addCriteria(Criteria.where("transactionYear").is(transaction.getTransactionYear()));
                                    query.addCriteria(Criteria.where("department").is(transaction.getDepartment()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class);
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    total = total + sum;
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getVehicle().getLicensePlate());
                                    spendings.add(spending);
                                }

                            }
                            mv.addObject("total", this.getTwoDecimal(total));
                            mv.addObject("innovative_title", "License Plates");
                            mv.addObject("allSpendings", spendings);
                        }
                    }
                }
                break;
            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                if (department == null) {
                    mv.addObject("message", "Department not found");
                    mv.addObject("messageType", "fail");
                    return mv;
                } else {

                    LocalDate today = LocalDate.now();

                    mv.addObject("department", new Department());
                    mv.addObject("topic", today.getMonth().toString() + " / " + String.valueOf(today.getYear()) + " DEPARTMENT SPENDING FOR " + department.getDepartmentName());
                    mv.addObject("departmentId", department.getDepartmentId());
                    List<String> existingFilter = new ArrayList<>();
                    List<Tollspending> spendings = new ArrayList<>();
                    double total = 0.00;
                    for (Transaction transaction : this.transactionService.findByDepartmentAndTransactionMonthAndTransactionYear(department, today.getMonth().toString(), today.getYear())) {
                        double sum = 0.00;
                        if (!existingFilter.contains(transaction.getVehicle().getLicensePlate())) {
                            //LOG.info("PROCESSING FOR PLATE     ::::::: " + transaction.getVehicle().getLicensePlate());
                            existingFilter.add(transaction.getVehicle().getLicensePlate());
                            Query query = new Query();
                            query.addCriteria(Criteria.where("vehicle").is(transaction.getVehicle()));
                            query.addCriteria(Criteria.where("transactionMonth").is(transaction.getTransactionMonth()));
                            query.addCriteria(Criteria.where("transactionYear").is(transaction.getTransactionYear()));
                            query.addCriteria(Criteria.where("department").is(transaction.getDepartment()));
                            List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class);
                            for (Transaction trans : transactionReport) {
                                sum = sum + trans.getAmount();
                            }
                            Tollspending spending = new Tollspending();
                            total = total + sum;
                            spending.setAmount(this.getTwoDecimal(sum));
                            spending.setTitle(transaction.getVehicle().getLicensePlate());
                            spendings.add(spending);
                        }

                    }
                    mv.addObject("total", this.getTwoDecimal(total));
                    mv.addObject("innovative_title", "License Plates");
                    mv.addObject("allSpendings", spendings);

                }
                break;
            default:
                LOG.info("USER ACCOUNT UNKNOWN");
        }
        mv.setViewName("clientdepartmentspending");
        return mv;
    }

    @RequestMapping(path = "/filter/monthly/by/{month}/{year}/{filter}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchClientMonthly(HttpSession session, @PathVariable("month") String month, @PathVariable("year") String year, @PathVariable("filter") String filter) {
        SpendingResponse result = new SpendingResponse();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                String departmentMode = (String) session.getAttribute("department_mode");
                String departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                    int yrs = Integer.valueOf(year);
                    result.setPageTitle(month + " / " + String.valueOf(year) + " CLIENT SPENDING FOR " + department.getDepartmentName());
                    List<String> existingFilter = new ArrayList<>();
                    List<Tollspending> spendings = new ArrayList<>();
                    double total = 0.00;
                    for (Transaction transaction : this.transactionService.findByDepartmentAndTransactionMonthAndTransactionYear(department, month, yrs)) {
                        double sum = 0.00;
                        Query query = new Query();
                        query.addCriteria(Criteria.where("transactionMonth").is(transaction.getTransactionMonth()));
                        query.addCriteria(Criteria.where("transactionYear").is(transaction.getTransactionYear()));
                        query.addCriteria(Criteria.where("department").is(transaction.getDepartment()));
                        query.addCriteria(Criteria.where("transactionDispute").is(false));
                        switch (filter) {
                            case "licensePlate":
                                result.setTableTitle("License Plate");
                                if (!existingFilter.contains(transaction.getVehicle().getLicensePlate())) {
                                    existingFilter.add(transaction.getVehicle().getLicensePlate());
                                    query.addCriteria(Criteria.where("vehicle").is(transaction.getVehicle()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getVehicle().getLicensePlate());
                                    spendings.add(spending);
                                }
                                break;
                            case "exitLane":
                                result.setTableTitle("Exit Lane");
                                if (!existingFilter.contains(transaction.getExitLane())) {
                                    existingFilter.add(transaction.getExitLane());
                                    query.addCriteria(Criteria.where("exitLane").is(transaction.getExitLane()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getExitLane());
                                    spendings.add(spending);
                                }
                                break;
                            case "exitLocation":
                                result.setTableTitle("Location");
                                if (!existingFilter.contains(transaction.getExitLocation())) {
                                    existingFilter.add(transaction.getExitLocation());
                                    query.addCriteria(Criteria.where("exitLocation").is(transaction.getExitLocation()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getExitLocation());
                                    spendings.add(spending);
                                }
                                break;
                            case "exitDateTime":
                                result.setTableTitle("Date");
                                if (!existingFilter.contains(String.valueOf(transaction.getTransactionDate()))) {
                                    existingFilter.add(String.valueOf(transaction.getTransactionDate()));
                                    query.addCriteria(Criteria.where("transactionDate").is(transaction.getTransactionDate()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(String.valueOf(transaction.getTransactionDate()));
                                    spendings.add(spending);
                                }
                                break;
                            case "agency":
                                result.setTableTitle("Agency");
                                if (!existingFilter.contains(transaction.getAgency())) {
                                    existingFilter.add(transaction.getAgency());
                                    query.addCriteria(Criteria.where("agency").is(transaction.getAgency()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getAgency());
                                    spendings.add(spending);
                                }
                                break;
                            case "state":
                                result.setTableTitle("State");
                                if (!existingFilter.contains(transaction.getState())) {
                                    existingFilter.add(transaction.getState());
                                    query.addCriteria(Criteria.where("state").is(transaction.getState()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getState());
                                    spendings.add(spending);
                                }
                                break;
                            default:
                                LOG.info("NO FILTER WAS FOUND");
                                break;
                        }
                        total = total + sum;
                    }
                    result.setTotal(String.valueOf(total));
                    result.setTitle("success");
                    result.setResults(spendings);
                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
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
                        for (Transaction transaction : this.transactionService.findByClientProfileAndTransactionMonthAndTransactionYear(profile, month, yrs)) {
                            double sum = 0.00;
                            Query query = new Query();
                            query.addCriteria(Criteria.where("transactionMonth").is(transaction.getTransactionMonth()));
                            query.addCriteria(Criteria.where("transactionYear").is(transaction.getTransactionYear()));
                            query.addCriteria(Criteria.where("clientProfile").is(transaction.getClientProfile()));
                            query.addCriteria(Criteria.where("transactionDispute").is(false));
                            switch (filter) {
                                case "licensePlate":
                                    result.setTableTitle("License Plate");
                                    if (!existingFilter.contains(transaction.getVehicle().getLicensePlate())) {
                                        existingFilter.add(transaction.getVehicle().getLicensePlate());
                                        query.addCriteria(Criteria.where("vehicle").is(transaction.getVehicle()));
                                        List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                        );
                                        for (Transaction trans : transactionReport) {
                                            sum = sum + trans.getAmount();
                                        }
                                        Tollspending spending = new Tollspending();
                                        spending.setAmount(this.getTwoDecimal(sum));
                                        spending.setTitle(transaction.getVehicle().getLicensePlate());
                                        spendings.add(spending);
                                    }
                                    break;
                                case "exitLane":
                                    result.setTableTitle("Exit Lane");
                                    if (!existingFilter.contains(transaction.getExitLane())) {
                                        existingFilter.add(transaction.getExitLane());
                                        query.addCriteria(Criteria.where("exitLane").is(transaction.getExitLane()));
                                        List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                        );
                                        for (Transaction trans : transactionReport) {
                                            sum = sum + trans.getAmount();
                                        }
                                        Tollspending spending = new Tollspending();
                                        spending.setAmount(this.getTwoDecimal(sum));
                                        spending.setTitle(transaction.getExitLane());
                                        spendings.add(spending);
                                    }
                                    break;
                                case "exitLocation":
                                    result.setTableTitle("Location");
                                    if (!existingFilter.contains(transaction.getExitLocation())) {
                                        existingFilter.add(transaction.getExitLocation());
                                        query.addCriteria(Criteria.where("exitLocation").is(transaction.getExitLocation()));
                                        List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                        );
                                        for (Transaction trans : transactionReport) {
                                            sum = sum + trans.getAmount();
                                        }
                                        Tollspending spending = new Tollspending();
                                        spending.setAmount(this.getTwoDecimal(sum));
                                        spending.setTitle(transaction.getExitLocation());
                                        spendings.add(spending);
                                    }
                                    break;
                                case "exitDateTime":
                                    result.setTableTitle("Date");
                                    if (!existingFilter.contains(String.valueOf(transaction.getTransactionDate()))) {
                                        existingFilter.add(String.valueOf(transaction.getTransactionDate()));
                                        query.addCriteria(Criteria.where("transactionDate").is(transaction.getTransactionDate()));
                                        List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                        );
                                        for (Transaction trans : transactionReport) {
                                            sum = sum + trans.getAmount();
                                        }
                                        Tollspending spending = new Tollspending();
                                        spending.setAmount(this.getTwoDecimal(sum));
                                        spending.setTitle(String.valueOf(transaction.getTransactionDate()));
                                        spendings.add(spending);
                                    }
                                    break;
                                case "agency":
                                    result.setTableTitle("Agency");
                                    if (!existingFilter.contains(transaction.getAgency())) {
                                        existingFilter.add(transaction.getAgency());
                                        query.addCriteria(Criteria.where("agency").is(transaction.getAgency()));
                                        List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                        );
                                        for (Transaction trans : transactionReport) {
                                            sum = sum + trans.getAmount();
                                        }
                                        Tollspending spending = new Tollspending();
                                        spending.setAmount(this.getTwoDecimal(sum));
                                        spending.setTitle(transaction.getAgency());
                                        spendings.add(spending);
                                    }
                                    break;
                                case "state":
                                    result.setTableTitle("State");
                                    if (!existingFilter.contains(transaction.getState())) {
                                        existingFilter.add(transaction.getState());
                                        query.addCriteria(Criteria.where("state").is(transaction.getState()));
                                        List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                        );
                                        for (Transaction trans : transactionReport) {
                                            sum = sum + trans.getAmount();
                                        }
                                        Tollspending spending = new Tollspending();
                                        spending.setAmount(this.getTwoDecimal(sum));
                                        spending.setTitle(transaction.getState());
                                        spendings.add(spending);
                                    }
                                    break;
                                default:
                                    LOG.info("NO FILTER WAS FOUND");
                                    break;
                            }
                            total = total + sum;
                        }
                        result.setTotal(String.valueOf(total));
                        result.setTitle("success");
                        result.setResults(spendings);
                    }
                }
                break;
            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                if (department == null) {
                    result.setMessage("Department not found");
                    result.setTitle("fail");
                    return ResponseEntity.ok(result);
                } else {
                    int yrs = Integer.valueOf(year);
                    result.setPageTitle(month + " / " + String.valueOf(year) + " CLIENT SPENDING FOR " + department.getDepartmentName());
                    List<String> existingFilter = new ArrayList<>();
                    List<Tollspending> spendings = new ArrayList<>();
                    double total = 0.00;
                    for (Transaction transaction : this.transactionService.findByDepartmentAndTransactionMonthAndTransactionYear(department, month, yrs)) {
                        double sum = 0.00;
                        Query query = new Query();
                        query.addCriteria(Criteria.where("transactionMonth").is(transaction.getTransactionMonth()));
                        query.addCriteria(Criteria.where("transactionYear").is(transaction.getTransactionYear()));
                        query.addCriteria(Criteria.where("department").is(transaction.getDepartment()));
                        query.addCriteria(Criteria.where("transactionDispute").is(false));
                        switch (filter) {
                            case "licensePlate":
                                result.setTableTitle("License Plate");
                                if (!existingFilter.contains(transaction.getVehicle().getLicensePlate())) {
                                    existingFilter.add(transaction.getVehicle().getLicensePlate());
                                    query.addCriteria(Criteria.where("vehicle").is(transaction.getVehicle()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getVehicle().getLicensePlate());
                                    spendings.add(spending);
                                }
                                break;
                            case "exitLane":
                                result.setTableTitle("Exit Lane");
                                if (!existingFilter.contains(transaction.getExitLane())) {
                                    existingFilter.add(transaction.getExitLane());
                                    query.addCriteria(Criteria.where("exitLane").is(transaction.getExitLane()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getExitLane());
                                    spendings.add(spending);
                                }
                                break;
                            case "exitLocation":
                                result.setTableTitle("Location");
                                if (!existingFilter.contains(transaction.getExitLocation())) {
                                    existingFilter.add(transaction.getExitLocation());
                                    query.addCriteria(Criteria.where("exitLocation").is(transaction.getExitLocation()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getExitLocation());
                                    spendings.add(spending);
                                }
                                break;
                            case "exitDateTime":
                                result.setTableTitle("Date");
                                if (!existingFilter.contains(String.valueOf(transaction.getTransactionDate()))) {
                                    existingFilter.add(String.valueOf(transaction.getTransactionDate()));
                                    query.addCriteria(Criteria.where("transactionDate").is(transaction.getTransactionDate()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(String.valueOf(transaction.getTransactionDate()));
                                    spendings.add(spending);
                                }
                                break;
                            case "agency":
                                result.setTableTitle("Agency");
                                if (!existingFilter.contains(transaction.getAgency())) {
                                    existingFilter.add(transaction.getAgency());
                                    query.addCriteria(Criteria.where("agency").is(transaction.getAgency()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getAgency());
                                    spendings.add(spending);
                                }
                                break;
                            case "state":
                                result.setTableTitle("State");
                                if (!existingFilter.contains(transaction.getState())) {
                                    existingFilter.add(transaction.getState());
                                    query.addCriteria(Criteria.where("state").is(transaction.getState()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getState());
                                    spendings.add(spending);
                                }
                                break;
                            default:
                                LOG.info("NO FILTER WAS FOUND");
                                break;
                        }
                        total = total + sum;
                    }
                    result.setTotal(String.valueOf(total));
                    result.setTitle("success");
                    result.setResults(spendings);
                }
                break;
            default:
                LOG.info("USER ACCOUNT UNKNOWN");
        }

        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = "/filter/department/monthly/by/{departmentId}/{month}/{year}/{filter}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchClientDepartmentMonthly(@PathVariable("departmentId") Long departmentId, @PathVariable("month") String month, @PathVariable("year") String year, @PathVariable("filter") String filter, HttpSession session) {
        SpendingResponse result = new SpendingResponse();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        List<String> existingFilter = new ArrayList<>();
        List<Tollspending> spendings = new ArrayList<>();
        double total = 0.00;
        int yrs = Integer.valueOf(year);
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                String departmentMode = (String) session.getAttribute("department_mode");
                departmentId = Long.valueOf((String) session.getAttribute("department_id"));
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                    if (department == null) {
                        result.setMessage("Department not found");
                        result.setTitle("fail");
                        return ResponseEntity.ok(result);
                    }
                    result.setPageTitle(month + " / " + String.valueOf(year) + " DEPARTMENT SPENDING FOR " + department.getDepartmentName());
                    for (Transaction transaction : this.transactionService.findByDepartmentAndTransactionMonthAndTransactionYear(department, month, yrs)) {
                        double sum = 0.00;
                        Query query = new Query();
                        query.addCriteria(Criteria.where("transactionMonth").is(transaction.getTransactionMonth()));
                        query.addCriteria(Criteria.where("transactionYear").is(transaction.getTransactionYear()));
                        query.addCriteria(Criteria.where("department").is(transaction.getDepartment()));
                        query.addCriteria(Criteria.where("transactionDispute").is(false));
                        switch (filter) {
                            case "licensePlate":
                                result.setTableTitle("License Plate");
                                if (!existingFilter.contains(transaction.getVehicle().getLicensePlate())) {
                                    existingFilter.add(transaction.getVehicle().getLicensePlate());
                                    query.addCriteria(Criteria.where("vehicle").is(transaction.getVehicle()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getVehicle().getLicensePlate());
                                    spendings.add(spending);
                                }
                                break;
                            case "exitLane":
                                result.setTableTitle("Exit Lane");
                                if (!existingFilter.contains(transaction.getExitLane())) {
                                    existingFilter.add(transaction.getExitLane());
                                    query.addCriteria(Criteria.where("exitLane").is(transaction.getExitLane()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getExitLane());
                                    spendings.add(spending);
                                }
                                break;
                            case "exitLocation":
                                result.setTableTitle("Location");
                                if (!existingFilter.contains(transaction.getExitLocation())) {
                                    existingFilter.add(transaction.getExitLocation());
                                    query.addCriteria(Criteria.where("exitLocation").is(transaction.getExitLocation()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getExitLocation());
                                    spendings.add(spending);
                                }
                                break;
                            case "exitDateTime":
                                result.setTableTitle("Date");
                                if (!existingFilter.contains(String.valueOf(transaction.getTransactionDate()))) {
                                    existingFilter.add(String.valueOf(transaction.getTransactionDate()));
                                    query.addCriteria(Criteria.where("transactionDate").is(transaction.getTransactionDate()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(String.valueOf(transaction.getTransactionDate()));
                                    spendings.add(spending);
                                }
                                break;
                            case "agency":
                                result.setTableTitle("Agency");
                                if (!existingFilter.contains(transaction.getAgency())) {
                                    existingFilter.add(transaction.getAgency());
                                    query.addCriteria(Criteria.where("agency").is(transaction.getAgency()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getAgency());
                                    spendings.add(spending);
                                }
                                break;
                            case "state":
                                result.setTableTitle("State");
                                if (!existingFilter.contains(transaction.getState())) {
                                    existingFilter.add(transaction.getState());
                                    query.addCriteria(Criteria.where("state").is(transaction.getState()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getState());
                                    spendings.add(spending);
                                }
                                break;
                            default:
                                LOG.info("NO FILTER WAS FOUND");
                                break;
                        }
                        total = total + sum;
                    }
                    result.setTotal(String.valueOf(total));
                    result.setTitle("success");
                    result.setResults(spendings);
                }
                break;

            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                if (department == null) {
                    result.setMessage("Department not found");
                    result.setTitle("fail");
                    return ResponseEntity.ok(result);
                }
                result.setPageTitle(month + " / " + String.valueOf(year) + " CLIENT SPENDING FOR " + department.getDepartmentName());
                for (Transaction transaction : this.transactionService.findByDepartmentAndTransactionMonthAndTransactionYear(department, month, yrs)) {
                    double sum = 0.00;
                    Query query = new Query();
                    query.addCriteria(Criteria.where("transactionMonth").is(transaction.getTransactionMonth()));
                    query.addCriteria(Criteria.where("transactionYear").is(transaction.getTransactionYear()));
                    query.addCriteria(Criteria.where("department").is(transaction.getDepartment()));
                    query.addCriteria(Criteria.where("transactionDispute").is(false));
                    switch (filter) {
                        case "licensePlate":
                            result.setTableTitle("License Plate");
                            if (!existingFilter.contains(transaction.getVehicle().getLicensePlate())) {
                                existingFilter.add(transaction.getVehicle().getLicensePlate());
                                query.addCriteria(Criteria.where("vehicle").is(transaction.getVehicle()));
                                List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                );
                                for (Transaction trans : transactionReport) {
                                    sum = sum + trans.getAmount();
                                }
                                Tollspending spending = new Tollspending();
                                spending.setAmount(this.getTwoDecimal(sum));
                                spending.setTitle(transaction.getVehicle().getLicensePlate());
                                spendings.add(spending);
                            }
                            break;
                        case "exitLane":
                            result.setTableTitle("Exit Lane");
                            if (!existingFilter.contains(transaction.getExitLane())) {
                                existingFilter.add(transaction.getExitLane());
                                query.addCriteria(Criteria.where("exitLane").is(transaction.getExitLane()));
                                List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                );
                                for (Transaction trans : transactionReport) {
                                    sum = sum + trans.getAmount();
                                }
                                Tollspending spending = new Tollspending();
                                spending.setAmount(this.getTwoDecimal(sum));
                                spending.setTitle(transaction.getExitLane());
                                spendings.add(spending);
                            }
                            break;
                        case "exitLocation":
                            result.setTableTitle("Location");
                            if (!existingFilter.contains(transaction.getExitLocation())) {
                                existingFilter.add(transaction.getExitLocation());
                                query.addCriteria(Criteria.where("exitLocation").is(transaction.getExitLocation()));
                                List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                );
                                for (Transaction trans : transactionReport) {
                                    sum = sum + trans.getAmount();
                                }
                                Tollspending spending = new Tollspending();
                                spending.setAmount(this.getTwoDecimal(sum));
                                spending.setTitle(transaction.getExitLocation());
                                spendings.add(spending);
                            }
                            break;
                        case "exitDateTime":
                            result.setTableTitle("Date");
                            if (!existingFilter.contains(String.valueOf(transaction.getTransactionDate()))) {
                                existingFilter.add(String.valueOf(transaction.getTransactionDate()));
                                query.addCriteria(Criteria.where("transactionDate").is(transaction.getTransactionDate()));
                                List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                );
                                for (Transaction trans : transactionReport) {
                                    sum = sum + trans.getAmount();
                                }
                                Tollspending spending = new Tollspending();
                                spending.setAmount(this.getTwoDecimal(sum));
                                spending.setTitle(String.valueOf(transaction.getTransactionDate()));
                                spendings.add(spending);
                            }
                            break;
                        case "agency":
                            result.setTableTitle("Agency");
                            if (!existingFilter.contains(transaction.getAgency())) {
                                existingFilter.add(transaction.getAgency());
                                query.addCriteria(Criteria.where("agency").is(transaction.getAgency()));
                                List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                );
                                for (Transaction trans : transactionReport) {
                                    sum = sum + trans.getAmount();
                                }
                                Tollspending spending = new Tollspending();
                                spending.setAmount(this.getTwoDecimal(sum));
                                spending.setTitle(transaction.getAgency());
                                spendings.add(spending);
                            }
                            break;
                        case "state":
                            result.setTableTitle("State");
                            if (!existingFilter.contains(transaction.getState())) {
                                existingFilter.add(transaction.getState());
                                query.addCriteria(Criteria.where("state").is(transaction.getState()));
                                List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                );
                                for (Transaction trans : transactionReport) {
                                    sum = sum + trans.getAmount();
                                }
                                Tollspending spending = new Tollspending();
                                spending.setAmount(this.getTwoDecimal(sum));
                                spending.setTitle(transaction.getState());
                                spendings.add(spending);
                            }
                            break;
                        default:
                            LOG.info("NO FILTER WAS FOUND");
                            break;
                    }
                    total = total + sum;
                }
                result.setTotal(String.valueOf(total));
                result.setTitle("success");
                result.setResults(spendings);
                break;
            default:
                LOG.info("USER ACCOUNT UNKNOWN");
        }

        return ResponseEntity.ok(result);
    }

    /*
    
    
    ANNUAL SPENDINGS
    
     */
    @RequestMapping("/annual/expenditure/")
    public ModelAndView viewAnnualExpenditure(HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "spending");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.addObject("message", "Invalid session detected, please login again!");
            mv.addObject("messageType", "fail");
            mv.setViewName("clientspending");
            return mv;
        }
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                String departmentMode = (String) session.getAttribute("department_mode");
                String departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));

                    LocalDate today = LocalDate.now();
                    mv.addObject("department", new Department());
                    mv.addObject("topic", String.valueOf(today.getYear()) + " CLIENT SPENDING FOR " + department.getDepartmentName());
                    mv.addObject("clientId", department.getDepartmentId());
                    List<String> existingFilter = new ArrayList<>();
                    List<Tollspending> spendings = new ArrayList<>();
                    double total = 0.00;
                    for (Transaction transaction : this.transactionService.findByDepartmentAndTransactionYear(department, today.getYear())) {
                        double sum = 0.00;
                        if (!existingFilter.contains(transaction.getVehicle().getLicensePlate())) {
                            //LOG.info("PROCESSING FOR PLATE     ::::::: " + transaction.getVehicle().getLicensePlate());
                            existingFilter.add(transaction.getVehicle().getLicensePlate());
                            Query query = new Query();
                            query.addCriteria(Criteria.where("vehicle").is(transaction.getVehicle()));
                            query.addCriteria(Criteria.where("transactionYear").is(transaction.getTransactionYear()));
                            query.addCriteria(Criteria.where("department").is(transaction.getDepartment()));
                            query.addCriteria(Criteria.where("transactionDispute").is(false));
                            List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                            );
                            for (Transaction trans : transactionReport) {
                                sum = sum + trans.getAmount();
                            }
                            //LOG.info("PLATE     ::::::: " + transaction.getVehicle().getLicensePlate() + "  :::::::::   " + sum);
                            Tollspending spending = new Tollspending();
                            total = total + sum;
                            spending.setAmount(this.getTwoDecimal(sum));

                            spending.setTitle(transaction.getVehicle().getLicensePlate());
                            spendings.add(spending);
                        }

                    }
                    mv.addObject("total", this.getTwoDecimal(total));
                    mv.addObject("innovative_title", "License Plates");
                    mv.addObject("allSpendings", spendings);
                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    if (profile == null) {
                        redir.addFlashAttribute("Client profile not found");
                        redir.addFlashAttribute("fail");
                        mv.setViewName("clientannualspending");
                        return mv;
                    } else {
                        //mv.addObject("institution", profile.getCompanyName());

                        LocalDate today = LocalDate.now();
                        mv.addObject("department", new Department());
                        mv.addObject("topic", String.valueOf(today.getYear()) + " CLIENT SPENDING FOR " + profile.getCompanyName());
                        mv.addObject("clientId", profile.getClientProfileId());
                        List<String> existingFilter = new ArrayList<>();
                        List<Tollspending> spendings = new ArrayList<>();
                        double total = 0.00;
                        for (Transaction transaction : this.transactionService.findByClientProfileAndTransactionYear(profile, today.getYear())) {
                            double sum = 0.00;
                            if (!existingFilter.contains(transaction.getVehicle().getLicensePlate())) {
                                //LOG.info("PROCESSING FOR PLATE     ::::::: " + transaction.getVehicle().getLicensePlate());
                                existingFilter.add(transaction.getVehicle().getLicensePlate());
                                Query query = new Query();
                                query.addCriteria(Criteria.where("vehicle").is(transaction.getVehicle()));
                                query.addCriteria(Criteria.where("transactionDispute").is(false));
                                query.addCriteria(Criteria.where("transactionYear").is(transaction.getTransactionYear()));
                                query.addCriteria(Criteria.where("clientProfile").is(transaction.getClientProfile()));
                                List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                );
                                for (Transaction trans : transactionReport) {
                                    sum = sum + trans.getAmount();
                                }
                                //LOG.info("PLATE     ::::::: " + transaction.getVehicle().getLicensePlate() + "  :::::::::   " + sum);
                                Tollspending spending = new Tollspending();
                                total = total + sum;
                                spending.setAmount(this.getTwoDecimal(sum));
                                spending.setTitle(transaction.getVehicle().getLicensePlate());
                                spendings.add(spending);
                            }

                        }
                        mv.addObject("total", this.getTwoDecimal(total));
                        mv.addObject("innovative_title", "License Plates");
                        mv.addObject("allSpendings", spendings);
                    }
                }
                break;
            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                if (department == null) {
                    mv.addObject("message", "Department not found");
                    mv.addObject("messageType", "fail");
                    return mv;
                }

                LocalDate today = LocalDate.now();
                mv.addObject("department", new Department());
                mv.addObject("topic", String.valueOf(today.getYear()) + " CLIENT SPENDING FOR " + department.getDepartmentName());
                mv.addObject("clientId", department.getDepartmentId());
                List<String> existingFilter = new ArrayList<>();
                List<Tollspending> spendings = new ArrayList<>();
                double total = 0.00;
                for (Transaction transaction : this.transactionService.findByDepartmentAndTransactionYear(department, today.getYear())) {
                    double sum = 0.00;
                    if (!existingFilter.contains(transaction.getVehicle().getLicensePlate())) {
                        //LOG.info("PROCESSING FOR PLATE     ::::::: " + transaction.getVehicle().getLicensePlate());
                        existingFilter.add(transaction.getVehicle().getLicensePlate());
                        Query query = new Query();
                        query.addCriteria(Criteria.where("vehicle").is(transaction.getVehicle()));
                        query.addCriteria(Criteria.where("transactionYear").is(transaction.getTransactionYear()));
                        query.addCriteria(Criteria.where("department").is(transaction.getDepartment()));
                        query.addCriteria(Criteria.where("transactionDispute").is(false));
                        List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                        );
                        for (Transaction trans : transactionReport) {
                            sum = sum + trans.getAmount();
                        }
                        //LOG.info("PLATE     ::::::: " + transaction.getVehicle().getLicensePlate() + "  :::::::::   " + sum);
                        Tollspending spending = new Tollspending();
                        total = total + sum;
                        spending.setAmount(this.getTwoDecimal(sum));

                        spending.setTitle(transaction.getVehicle().getLicensePlate());
                        spendings.add(spending);
                    }

                }
                mv.addObject("total", this.getTwoDecimal(total));
                mv.addObject("innovative_title", "License Plates");
                mv.addObject("allSpendings", spendings);
                break;
            default:
                LOG.info("USER ACCOUNT UNKNOWN");
        }
        mv.setViewName("clientannualspending");
        return mv;
    }

    @GetMapping("/annual/departments/")
    public ModelAndView viewAnnualDepartments(HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "spending");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
        if (profile == null) {
            redir.addFlashAttribute("Department not found");
            redir.addFlashAttribute("fail");
            mv.setViewName("clientannualtolldepartmentspendings");
            return mv;
        } else {
            mv.addObject("department", new Department());
            mv.addObject("topic", "DEPARTMENTAL SPENDING FOR " + profile.getCompanyName());
            mv.addObject("clientId", profile.getClientProfileId());
            List<Department> pages = this.departmentService.findByClientProfile(profile);
            mv.addObject("data", pages);

            //mv.addObject("institution", profile.getCompanyName());
            //mv.addObject("institution", profile.getCompanyName());
            //mv.addObject("institution", profile.getCompanyName());
            mv.setViewName("clientannualtolldepartmentspendings");
            return mv;
        }
    }

    @RequestMapping("/annual/department/expenditure/{id}")
    public ModelAndView viewAnnualDepartmentExpenditure(@PathVariable("id") Long id, @PageableDefault(value = 10) Pageable pageable, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "spending");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }

        LocalDate today = LocalDate.now();
        List<String> existingFilter = new ArrayList<>();
        List<Tollspending> spendings = new ArrayList<>();
        double total = 0.00;
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                String departmentMode = (String) session.getAttribute("department_mode");
                String departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));

                    mv.addObject("department", new Department());
                    mv.addObject("topic", today.getMonth().toString() + " / " + String.valueOf(today.getYear()) + " DEPARTMENT SPENDING FOR " + department.getDepartmentName());
                    mv.addObject("departmentId", department.getDepartmentId());
                    for (Transaction transaction : this.transactionService.findByDepartmentAndTransactionYear(department, today.getYear())) {
                        double sum = 0.00;
                        if (!existingFilter.contains(transaction.getVehicle().getLicensePlate())) {
                            //LOG.info("PROCESSING FOR PLATE     ::::::: " + transaction.getVehicle().getLicensePlate());
                            existingFilter.add(transaction.getVehicle().getLicensePlate());
                            Query query = new Query();
                            query.addCriteria(Criteria.where("vehicle").is(transaction.getVehicle()));
                            query.addCriteria(Criteria.where("transactionYear").is(transaction.getTransactionYear()));
                            query.addCriteria(Criteria.where("department").is(transaction.getDepartment()));
                            query.addCriteria(Criteria.where("transactionDispute").is(false));
                            List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                            );
                            for (Transaction trans : transactionReport) {
                                sum = sum + trans.getAmount();
                            }
                            //LOG.info("PLATE     ::::::: " + transaction.getVehicle().getLicensePlate() + "  :::::::::   " + sum);
                            Tollspending spending = new Tollspending();
                            total = total + sum;
                            spending.setAmount(this.getTwoDecimal(sum));
                            //spending.setDepartment(department);
                            spending.setTitle(transaction.getVehicle().getLicensePlate());
                            spendings.add(spending);
                        }

                    }
                    mv.addObject("total", this.getTwoDecimal(total));
                    mv.addObject("innovative_title", "License Plates");
                    mv.addObject("allSpendings", spendings);
                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    if (profile == null) {
                        mv.addObject("message", "Client profile not found");
                        mv.addObject("messageType", "fail");
                        mv.setViewName("clientspending");
                        return mv;
                    }
                    //mv.addObject("institution", profile.getCompanyName());

                    Department department = this.departmentService.findByDepartmentId(id);
                    if (department == null) {
                        redir.addFlashAttribute("Department not found");
                        redir.addFlashAttribute("fail");
                        mv.setViewName("clientannualdepartmentspending");
                        return mv;
                    }
                    mv.addObject("department", new Department());
                    mv.addObject("topic", today.getMonth().toString() + " / " + String.valueOf(today.getYear()) + " DEPARTMENT SPENDING FOR " + department.getDepartmentName());
                    mv.addObject("departmentId", department.getDepartmentId());
                    for (Transaction transaction : this.transactionService.findByDepartmentAndTransactionYear(department, today.getYear())) {
                        double sum = 0.00;
                        if (!existingFilter.contains(transaction.getVehicle().getLicensePlate())) {
                            //LOG.info("PROCESSING FOR PLATE     ::::::: " + transaction.getVehicle().getLicensePlate());
                            existingFilter.add(transaction.getVehicle().getLicensePlate());
                            Query query = new Query();
                            query.addCriteria(Criteria.where("vehicle").is(transaction.getVehicle()));
                            query.addCriteria(Criteria.where("transactionYear").is(transaction.getTransactionYear()));
                            query.addCriteria(Criteria.where("department").is(transaction.getDepartment()));
                            query.addCriteria(Criteria.where("transactionDispute").is(false));
                            List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                            );
                            for (Transaction trans : transactionReport) {
                                sum = sum + trans.getAmount();
                            }
                            //LOG.info("PLATE     ::::::: " + transaction.getVehicle().getLicensePlate() + "  :::::::::   " + sum);
                            Tollspending spending = new Tollspending();
                            total = total + sum;
                            spending.setAmount(this.getTwoDecimal(sum));
                            //spending.setDepartment(department);
                            spending.setTitle(transaction.getVehicle().getLicensePlate());
                            spendings.add(spending);
                        }

                    }
                    mv.addObject("total", this.getTwoDecimal(total));
                    mv.addObject("innovative_title", "License Plates");
                    mv.addObject("allSpendings", spendings);
                }
                break;

            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                if (department == null) {
                    mv.addObject("message", "Department not found");
                    mv.addObject("messageType", "fail");
                    mv.setViewName("clientspending");
                    return mv;
                }

                mv.addObject("department", new Department());
                mv.addObject("topic", today.getMonth().toString() + " / " + String.valueOf(today.getYear()) + " DEPARTMENT SPENDING FOR " + department.getDepartmentName());
                mv.addObject("departmentId", department.getDepartmentId());
                for (Transaction transaction : this.transactionService.findByDepartmentAndTransactionYear(department, today.getYear())) {
                    double sum = 0.00;
                    if (!existingFilter.contains(transaction.getVehicle().getLicensePlate())) {
                        //LOG.info("PROCESSING FOR PLATE     ::::::: " + transaction.getVehicle().getLicensePlate());
                        existingFilter.add(transaction.getVehicle().getLicensePlate());
                        Query query = new Query();
                        query.addCriteria(Criteria.where("vehicle").is(transaction.getVehicle()));
                        query.addCriteria(Criteria.where("transactionYear").is(transaction.getTransactionYear()));
                        query.addCriteria(Criteria.where("department").is(transaction.getDepartment()));
                        query.addCriteria(Criteria.where("transactionDispute").is(false));
                        List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                        );
                        for (Transaction trans : transactionReport) {
                            sum = sum + trans.getAmount();
                        }
                        //LOG.info("PLATE     ::::::: " + transaction.getVehicle().getLicensePlate() + "  :::::::::   " + sum);
                        Tollspending spending = new Tollspending();
                        total = total + sum;
                        spending.setAmount(this.getTwoDecimal(sum));
                        //spending.setDepartment(department);
                        spending.setTitle(transaction.getVehicle().getLicensePlate());
                        spendings.add(spending);
                    }

                }
                mv.addObject("total", this.getTwoDecimal(total));
                mv.addObject("innovative_title", "License Plates");
                mv.addObject("allSpendings", spendings);
                break;
            default:
                LOG.info("USER ACCOUNT UNKNOWN");
        }

        mv.setViewName("clientannualdepartmentspending");
        return mv;
    }

    @RequestMapping(path = "/filter/annual/view/by/{year}/{filter}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchClientAnnually(HttpSession session, @PathVariable("year") String year, @PathVariable("filter") String filter) {
        SpendingResponse result = new SpendingResponse();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }

        int yrs = Integer.valueOf(year);
        List<String> existingFilter = new ArrayList<>();
        List<Tollspending> spendings = new ArrayList<>();
        double total = 0.00;

        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                String departmentMode = (String) session.getAttribute("department_mode");
                String departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                    result.setPageTitle(String.valueOf(year) + " CLIENT SPENDING FOR " + department.getDepartmentName());
                    for (Transaction transaction : this.transactionService.findByDepartmentAndTransactionYear(department, yrs)) {
                        double sum = 0.00;
                        Query query = new Query();
                        query.addCriteria(Criteria.where("transactionYear").is(transaction.getTransactionYear()));
                        query.addCriteria(Criteria.where("department").is(transaction.getDepartment()));
                        query.addCriteria(Criteria.where("transactionDispute").is(false));
                        switch (filter) {
                            case "licensePlate":
                                result.setTableTitle("License Plate");
                                if (!existingFilter.contains(transaction.getVehicle().getLicensePlate())) {
                                    existingFilter.add(transaction.getVehicle().getLicensePlate());
                                    query.addCriteria(Criteria.where("vehicle").is(transaction.getVehicle()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getVehicle().getLicensePlate());
                                    spendings.add(spending);
                                }
                                break;
                            case "exitLane":
                                result.setTableTitle("Exit Lane");
                                if (!existingFilter.contains(transaction.getExitLane())) {
                                    existingFilter.add(transaction.getExitLane());
                                    query.addCriteria(Criteria.where("exitLane").is(transaction.getExitLane()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));

                                    spending.setTitle(transaction.getExitLane());
                                    spendings.add(spending);
                                }
                                break;
                            case "exitLocation":
                                result.setTableTitle("Location");
                                if (!existingFilter.contains(transaction.getExitLocation())) {
                                    existingFilter.add(transaction.getExitLocation());
                                    query.addCriteria(Criteria.where("exitLocation").is(transaction.getExitLocation()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));

                                    spending.setTitle(transaction.getExitLocation());
                                    spendings.add(spending);
                                }
                                break;
                            case "exitDateTime":
                                result.setTableTitle("Date");
                                if (!existingFilter.contains(String.valueOf(transaction.getTransactionDate()))) {
                                    existingFilter.add(String.valueOf(transaction.getTransactionDate()));
                                    query.addCriteria(Criteria.where("transactionDate").is(transaction.getTransactionDate()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));

                                    spending.setTitle(String.valueOf(transaction.getTransactionDate()));
                                    spendings.add(spending);
                                }
                                break;
                            case "agency":
                                result.setTableTitle("Agency");
                                if (!existingFilter.contains(transaction.getAgency())) {
                                    existingFilter.add(transaction.getAgency());
                                    query.addCriteria(Criteria.where("agency").is(transaction.getAgency()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getAgency());
                                    spendings.add(spending);
                                }
                                break;
                            case "state":
                                result.setTableTitle("State");
                                if (!existingFilter.contains(transaction.getState())) {
                                    existingFilter.add(transaction.getState());
                                    query.addCriteria(Criteria.where("state").is(transaction.getState()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getState());
                                    spendings.add(spending);
                                }
                        }
                    }
                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    if (profile == null) {
                        result.setMessage("Client profile not found");
                        result.setTitle("fail");
                        return ResponseEntity.ok(result);
                    }
                    result.setPageTitle(String.valueOf(year) + " CLIENT SPENDING FOR " + profile.getCompanyName());
                    for (Transaction transaction : this.transactionService.findByClientProfileAndTransactionYear(profile, yrs)) {
                        double sum = 0.00;
                        Query query = new Query();
                        query.addCriteria(Criteria.where("transactionYear").is(transaction.getTransactionYear()));
                        query.addCriteria(Criteria.where("clientProfile").is(transaction.getClientProfile()));
                        query.addCriteria(Criteria.where("transactionDispute").is(false));
                        switch (filter) {
                            case "licensePlate":
                                result.setTableTitle("License Plate");
                                if (!existingFilter.contains(transaction.getVehicle().getLicensePlate())) {
                                    existingFilter.add(transaction.getVehicle().getLicensePlate());
                                    query.addCriteria(Criteria.where("vehicle").is(transaction.getVehicle()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getVehicle().getLicensePlate());
                                    spendings.add(spending);
                                }
                                break;
                            case "exitLane":
                                result.setTableTitle("Exit Lane");
                                if (!existingFilter.contains(transaction.getExitLane())) {
                                    existingFilter.add(transaction.getExitLane());
                                    query.addCriteria(Criteria.where("exitLane").is(transaction.getExitLane()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));

                                    spending.setTitle(transaction.getExitLane());
                                    spendings.add(spending);
                                }
                                break;
                            case "exitLocation":
                                result.setTableTitle("Location");
                                if (!existingFilter.contains(transaction.getExitLocation())) {
                                    existingFilter.add(transaction.getExitLocation());
                                    query.addCriteria(Criteria.where("exitLocation").is(transaction.getExitLocation()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));

                                    spending.setTitle(transaction.getExitLocation());
                                    spendings.add(spending);
                                }
                                break;
                            case "exitDateTime":
                                result.setTableTitle("Date");
                                if (!existingFilter.contains(String.valueOf(transaction.getTransactionDate()))) {
                                    existingFilter.add(String.valueOf(transaction.getTransactionDate()));
                                    query.addCriteria(Criteria.where("transactionDate").is(transaction.getTransactionDate()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));

                                    spending.setTitle(String.valueOf(transaction.getTransactionDate()));
                                    spendings.add(spending);
                                }
                                break;
                            case "agency":
                                result.setTableTitle("Agency");
                                if (!existingFilter.contains(transaction.getAgency())) {
                                    existingFilter.add(transaction.getAgency());
                                    query.addCriteria(Criteria.where("agency").is(transaction.getAgency()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getAgency());
                                    spendings.add(spending);
                                }
                                break;
                            case "state":
                                result.setTableTitle("State");
                                if (!existingFilter.contains(transaction.getState())) {
                                    existingFilter.add(transaction.getState());
                                    query.addCriteria(Criteria.where("state").is(transaction.getState()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getState());
                                    spendings.add(spending);
                                }
                                break;
                            default:
                                LOG.info("NO FILTER WAS FOUND");
                                break;
                        }
                        total = total + sum;
                    }
                    result.setTotal(String.valueOf(total));
                    result.setTitle("success");
                    result.setResults(spendings);
                }
                break;

            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                if (department == null) {
                    result.setMessage("Department not found");
                    result.setTitle("fail");
                    return ResponseEntity.ok(result);
                }
                result.setPageTitle(String.valueOf(year) + " CLIENT SPENDING FOR " + department.getDepartmentName());
                for (Transaction transaction : this.transactionService.findByDepartmentAndTransactionYear(department, yrs)) {
                    double sum = 0.00;
                    Query query = new Query();
                    query.addCriteria(Criteria.where("transactionYear").is(transaction.getTransactionYear()));
                    query.addCriteria(Criteria.where("department").is(transaction.getDepartment()));
                    query.addCriteria(Criteria.where("transactionDispute").is(false));
                    switch (filter) {
                        case "licensePlate":
                            result.setTableTitle("License Plate");
                            if (!existingFilter.contains(transaction.getVehicle().getLicensePlate())) {
                                existingFilter.add(transaction.getVehicle().getLicensePlate());
                                query.addCriteria(Criteria.where("vehicle").is(transaction.getVehicle()));
                                List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                );
                                for (Transaction trans : transactionReport) {
                                    sum = sum + trans.getAmount();
                                }
                                Tollspending spending = new Tollspending();
                                spending.setAmount(this.getTwoDecimal(sum));
                                spending.setTitle(transaction.getVehicle().getLicensePlate());
                                spendings.add(spending);
                            }
                            break;
                        case "exitLane":
                            result.setTableTitle("Exit Lane");
                            if (!existingFilter.contains(transaction.getExitLane())) {
                                existingFilter.add(transaction.getExitLane());
                                query.addCriteria(Criteria.where("exitLane").is(transaction.getExitLane()));
                                List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                );
                                for (Transaction trans : transactionReport) {
                                    sum = sum + trans.getAmount();
                                }
                                Tollspending spending = new Tollspending();
                                spending.setAmount(this.getTwoDecimal(sum));

                                spending.setTitle(transaction.getExitLane());
                                spendings.add(spending);
                            }
                            break;
                        case "exitLocation":
                            result.setTableTitle("Location");
                            if (!existingFilter.contains(transaction.getExitLocation())) {
                                existingFilter.add(transaction.getExitLocation());
                                query.addCriteria(Criteria.where("exitLocation").is(transaction.getExitLocation()));
                                List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                );
                                for (Transaction trans : transactionReport) {
                                    sum = sum + trans.getAmount();
                                }
                                Tollspending spending = new Tollspending();
                                spending.setAmount(this.getTwoDecimal(sum));

                                spending.setTitle(transaction.getExitLocation());
                                spendings.add(spending);
                            }
                            break;
                        case "exitDateTime":
                            result.setTableTitle("Date");
                            if (!existingFilter.contains(String.valueOf(transaction.getTransactionDate()))) {
                                existingFilter.add(String.valueOf(transaction.getTransactionDate()));
                                query.addCriteria(Criteria.where("transactionDate").is(transaction.getTransactionDate()));
                                List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                );
                                for (Transaction trans : transactionReport) {
                                    sum = sum + trans.getAmount();
                                }
                                Tollspending spending = new Tollspending();
                                spending.setAmount(this.getTwoDecimal(sum));

                                spending.setTitle(String.valueOf(transaction.getTransactionDate()));
                                spendings.add(spending);
                            }
                            break;
                        case "agency":
                            result.setTableTitle("Agency");
                            if (!existingFilter.contains(transaction.getAgency())) {
                                existingFilter.add(transaction.getAgency());
                                query.addCriteria(Criteria.where("agency").is(transaction.getAgency()));
                                List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                );
                                for (Transaction trans : transactionReport) {
                                    sum = sum + trans.getAmount();
                                }
                                Tollspending spending = new Tollspending();
                                spending.setAmount(this.getTwoDecimal(sum));
                                spending.setTitle(transaction.getAgency());
                                spendings.add(spending);
                            }
                            break;
                        case "state":
                            result.setTableTitle("State");
                            if (!existingFilter.contains(transaction.getState())) {
                                existingFilter.add(transaction.getState());
                                query.addCriteria(Criteria.where("state").is(transaction.getState()));
                                List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                );
                                for (Transaction trans : transactionReport) {
                                    sum = sum + trans.getAmount();
                                }
                                Tollspending spending = new Tollspending();
                                spending.setAmount(this.getTwoDecimal(sum));
                                spending.setTitle(transaction.getState());
                                spendings.add(spending);
                            }
                            break;
                        default:
                            LOG.info("NO FILTER WAS FOUND");
                            break;
                    }
                    total = total + sum;
                }
                result.setTotal(String.valueOf(total));
                result.setTitle("success");
                result.setResults(spendings);
                break;
            default:
                LOG.info("USER ACCOUNT UNKNOWN");
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = "/filter/annual/view/department/by/{departmentId}/{year}/{filter}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchClientDepartmentAnnually(HttpSession session, @PathVariable("departmentId") Long departmentId, @PathVariable("year") String year, @PathVariable("filter") String filter) {
        SpendingResponse result = new SpendingResponse();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        int yrs = Integer.valueOf(year);
        List<String> existingFilter = new ArrayList<>();
        List<Tollspending> spendings = new ArrayList<>();
        double total = 0.00;
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                String departmentMode = (String) session.getAttribute("department_mode");
                departmentId = Long.valueOf((String) session.getAttribute("department_id"));
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                    result.setPageTitle(String.valueOf(year) + " DEPARTMENT SPENDING FOR " + department.getDepartmentName());
                    for (Transaction transaction : this.transactionService.findByDepartmentAndTransactionYear(department, yrs)) {
                        double sum = 0.00;
                        Query query = new Query();
                        query.addCriteria(Criteria.where("transactionYear").is(transaction.getTransactionYear()));
                        query.addCriteria(Criteria.where("department").is(transaction.getDepartment()));
                        query.addCriteria(Criteria.where("transactionDispute").is(false));
                        switch (filter) {
                            case "licensePlate":
                                result.setTableTitle("License Plate");
                                if (!existingFilter.contains(transaction.getVehicle().getLicensePlate())) {
                                    existingFilter.add(transaction.getVehicle().getLicensePlate());
                                    query.addCriteria(Criteria.where("vehicle").is(transaction.getVehicle()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getVehicle().getLicensePlate());
                                    spendings.add(spending);
                                }
                                break;
                            case "exitLane":
                                result.setTableTitle("Exit Lane");
                                if (!existingFilter.contains(transaction.getExitLane())) {
                                    existingFilter.add(transaction.getExitLane());
                                    query.addCriteria(Criteria.where("exitLane").is(transaction.getExitLane()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));

                                    spending.setTitle(transaction.getExitLane());
                                    spendings.add(spending);
                                }
                                break;
                            case "exitLocation":
                                result.setTableTitle("Location");
                                if (!existingFilter.contains(transaction.getExitLocation())) {
                                    existingFilter.add(transaction.getExitLocation());
                                    query.addCriteria(Criteria.where("exitLocation").is(transaction.getExitLocation()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getExitLocation());
                                    spendings.add(spending);
                                }
                                break;
                            case "exitDateTime":
                                result.setTableTitle("Date");
                                if (!existingFilter.contains(String.valueOf(transaction.getTransactionDate()))) {
                                    existingFilter.add(String.valueOf(transaction.getTransactionDate()));
                                    query.addCriteria(Criteria.where("transactionDate").is(transaction.getTransactionDate()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(String.valueOf(transaction.getTransactionDate()));
                                    spendings.add(spending);
                                }
                                break;
                            case "agency":
                                result.setTableTitle("Agency");
                                if (!existingFilter.contains(transaction.getAgency())) {
                                    existingFilter.add(transaction.getAgency());
                                    query.addCriteria(Criteria.where("agency").is(transaction.getAgency()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getAgency());
                                    spendings.add(spending);
                                }
                                break;
                            case "state":
                                result.setTableTitle("State");
                                if (!existingFilter.contains(transaction.getState())) {
                                    existingFilter.add(transaction.getState());
                                    query.addCriteria(Criteria.where("state").is(transaction.getState()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getState());
                                    spendings.add(spending);
                                }
                                break;
                            default:
                                LOG.info("NO FILTER WAS FOUND");
                                break;
                        }
                        total = total + sum;
                    }
                    result.setTotal(String.valueOf(total));
                    result.setTitle("success");
                    result.setResults(spendings);
                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    if (profile == null) {
                        result.setMessage("Client profile not found");
                        result.setTitle("fail");
                        return ResponseEntity.ok(result);
                    }
                    Department department = this.departmentService.findByDepartmentId(departmentId);
                    if (department == null) {
                        result.setMessage("Department not found");
                        result.setTitle("fail");
                        return ResponseEntity.ok(result);
                    }
                    result.setPageTitle(String.valueOf(year) + " DEPARTMENT SPENDING FOR " + department.getDepartmentName());
                    for (Transaction transaction : this.transactionService.findByDepartmentAndTransactionYear(department, yrs)) {
                        double sum = 0.00;
                        Query query = new Query();
                        query.addCriteria(Criteria.where("transactionYear").is(transaction.getTransactionYear()));
                        query.addCriteria(Criteria.where("department").is(transaction.getDepartment()));
                        query.addCriteria(Criteria.where("transactionDispute").is(false));
                        switch (filter) {
                            case "licensePlate":
                                result.setTableTitle("License Plate");
                                if (!existingFilter.contains(transaction.getVehicle().getLicensePlate())) {
                                    existingFilter.add(transaction.getVehicle().getLicensePlate());
                                    query.addCriteria(Criteria.where("vehicle").is(transaction.getVehicle()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getVehicle().getLicensePlate());
                                    spendings.add(spending);
                                }
                                break;
                            case "exitLane":
                                result.setTableTitle("Exit Lane");
                                if (!existingFilter.contains(transaction.getExitLane())) {
                                    existingFilter.add(transaction.getExitLane());
                                    query.addCriteria(Criteria.where("exitLane").is(transaction.getExitLane()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));

                                    spending.setTitle(transaction.getExitLane());
                                    spendings.add(spending);
                                }
                                break;
                            case "exitLocation":
                                result.setTableTitle("Location");
                                if (!existingFilter.contains(transaction.getExitLocation())) {
                                    existingFilter.add(transaction.getExitLocation());
                                    query.addCriteria(Criteria.where("exitLocation").is(transaction.getExitLocation()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getExitLocation());
                                    spendings.add(spending);
                                }
                                break;
                            case "exitDateTime":
                                result.setTableTitle("Date");
                                if (!existingFilter.contains(String.valueOf(transaction.getTransactionDate()))) {
                                    existingFilter.add(String.valueOf(transaction.getTransactionDate()));
                                    query.addCriteria(Criteria.where("transactionDate").is(transaction.getTransactionDate()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(String.valueOf(transaction.getTransactionDate()));
                                    spendings.add(spending);
                                }
                                break;
                            case "agency":
                                result.setTableTitle("Agency");
                                if (!existingFilter.contains(transaction.getAgency())) {
                                    existingFilter.add(transaction.getAgency());
                                    query.addCriteria(Criteria.where("agency").is(transaction.getAgency()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getAgency());
                                    spendings.add(spending);
                                }
                                break;
                            case "state":
                                result.setTableTitle("State");
                                if (!existingFilter.contains(transaction.getState())) {
                                    existingFilter.add(transaction.getState());
                                    query.addCriteria(Criteria.where("state").is(transaction.getState()));
                                    List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                    );
                                    for (Transaction trans : transactionReport) {
                                        sum = sum + trans.getAmount();
                                    }
                                    Tollspending spending = new Tollspending();
                                    spending.setAmount(this.getTwoDecimal(sum));
                                    spending.setTitle(transaction.getState());
                                    spendings.add(spending);
                                }
                                break;
                            default:
                                LOG.info("NO FILTER WAS FOUND");
                                break;
                        }
                        total = total + sum;
                    }
                    result.setTotal(String.valueOf(total));
                    result.setTitle("success");
                    result.setResults(spendings);
                }
                break;

            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                if (department == null) {
                    result.setMessage("Department not found");
                    result.setTitle("fail");
                    return ResponseEntity.ok(result);
                }
                result.setPageTitle(String.valueOf(year) + " DEPARTMENT SPENDING FOR " + department.getDepartmentName());
                for (Transaction transaction : this.transactionService.findByDepartmentAndTransactionYear(department, yrs)) {
                    double sum = 0.00;
                    Query query = new Query();
                    query.addCriteria(Criteria.where("transactionYear").is(transaction.getTransactionYear()));
                    query.addCriteria(Criteria.where("department").is(transaction.getDepartment()));
                    query.addCriteria(Criteria.where("transactionDispute").is(false));
                    switch (filter) {
                        case "licensePlate":
                            result.setTableTitle("License Plate");
                            if (!existingFilter.contains(transaction.getVehicle().getLicensePlate())) {
                                existingFilter.add(transaction.getVehicle().getLicensePlate());
                                query.addCriteria(Criteria.where("vehicle").is(transaction.getVehicle()));
                                List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                );
                                for (Transaction trans : transactionReport) {
                                    sum = sum + trans.getAmount();
                                }
                                Tollspending spending = new Tollspending();
                                spending.setAmount(this.getTwoDecimal(sum));
                                spending.setTitle(transaction.getVehicle().getLicensePlate());
                                spendings.add(spending);
                            }
                            break;
                        case "exitLane":
                            result.setTableTitle("Exit Lane");
                            if (!existingFilter.contains(transaction.getExitLane())) {
                                existingFilter.add(transaction.getExitLane());
                                query.addCriteria(Criteria.where("exitLane").is(transaction.getExitLane()));
                                List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                );
                                for (Transaction trans : transactionReport) {
                                    sum = sum + trans.getAmount();
                                }
                                Tollspending spending = new Tollspending();
                                spending.setAmount(this.getTwoDecimal(sum));

                                spending.setTitle(transaction.getExitLane());
                                spendings.add(spending);
                            }
                            break;
                        case "exitLocation":
                            result.setTableTitle("Location");
                            if (!existingFilter.contains(transaction.getExitLocation())) {
                                existingFilter.add(transaction.getExitLocation());
                                query.addCriteria(Criteria.where("exitLocation").is(transaction.getExitLocation()));
                                List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                );
                                for (Transaction trans : transactionReport) {
                                    sum = sum + trans.getAmount();
                                }
                                Tollspending spending = new Tollspending();
                                spending.setAmount(this.getTwoDecimal(sum));
                                spending.setTitle(transaction.getExitLocation());
                                spendings.add(spending);
                            }
                            break;
                        case "exitDateTime":
                            result.setTableTitle("Date");
                            if (!existingFilter.contains(String.valueOf(transaction.getTransactionDate()))) {
                                existingFilter.add(String.valueOf(transaction.getTransactionDate()));
                                query.addCriteria(Criteria.where("transactionDate").is(transaction.getTransactionDate()));
                                List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                );
                                for (Transaction trans : transactionReport) {
                                    sum = sum + trans.getAmount();
                                }
                                Tollspending spending = new Tollspending();
                                spending.setAmount(this.getTwoDecimal(sum));
                                spending.setTitle(String.valueOf(transaction.getTransactionDate()));
                                spendings.add(spending);
                            }
                            break;
                        case "agency":
                            result.setTableTitle("Agency");
                            if (!existingFilter.contains(transaction.getAgency())) {
                                existingFilter.add(transaction.getAgency());
                                query.addCriteria(Criteria.where("agency").is(transaction.getAgency()));
                                List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                );
                                for (Transaction trans : transactionReport) {
                                    sum = sum + trans.getAmount();
                                }
                                Tollspending spending = new Tollspending();
                                spending.setAmount(this.getTwoDecimal(sum));
                                spending.setTitle(transaction.getAgency());
                                spendings.add(spending);
                            }
                            break;
                        case "state":
                            result.setTableTitle("State");
                            if (!existingFilter.contains(transaction.getState())) {
                                existingFilter.add(transaction.getState());
                                query.addCriteria(Criteria.where("state").is(transaction.getState()));
                                List<Transaction> transactionReport = mongoTemplate.find(query, Transaction.class
                                );
                                for (Transaction trans : transactionReport) {
                                    sum = sum + trans.getAmount();
                                }
                                Tollspending spending = new Tollspending();
                                spending.setAmount(this.getTwoDecimal(sum));
                                spending.setTitle(transaction.getState());
                                spendings.add(spending);
                            }
                            break;
                        default:
                            LOG.info("NO FILTER WAS FOUND");
                            break;
                    }
                    total = total + sum;
                }
                result.setTotal(String.valueOf(total));
                result.setTitle("success");
                result.setResults(spendings);
                break;

            default:
                LOG.info("USER ACCOUNT UNKNOWN");
        }

        return ResponseEntity.ok(result);
    }

}
