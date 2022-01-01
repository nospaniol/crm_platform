package com.crm.controller;

import com.crm.generic.Nospaniol;
import com.crm.model.Citation;
import com.crm.model.CitationType;
import com.crm.model.ClientProfile;
import com.crm.model.DailySpending;
import com.crm.model.DashboardResponse;
import com.crm.model.Department;
import com.crm.model.DepartmentSaving;
import com.crm.model.EmailToken;
import com.crm.model.GenericResponse;
import com.crm.model.JobResponse;
import com.crm.model.LoginResponse;
import com.crm.model.Search;
import com.crm.model.Tollspendings;
import com.crm.model.Transaction;
import com.crm.model.User;
import com.crm.model.Vehicle;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"innovative"})
public class InnovativeRestController extends Nospaniol {

    @RequestMapping(path = {"/authenticate/by/{email}/{pass}"}, produces = {"application/json; charset=UTF-8"}, method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<?> validateUser(@PageableDefault(100) Pageable pageable, @PathVariable("email") String email, @PathVariable("pass") String pass) throws JsonProcessingException {
        LoginResponse result = new LoginResponse();
        EmailToken etoken = this.emailTokenService.findByEmailAddress(email.toLowerCase());
        User checkUser = this.userService.findByEmailToken(etoken);
        if (checkUser == null) {
            result.setMessage(email.toUpperCase() + " does not exist!");
            result.setTitle("fail");
            GenericResponse generic = new GenericResponse();
            generic.setResult(result.toString());
            return ResponseEntity.ok(generic);
        }
        if (checkUser.getPassword().equals(getHashed(pass))) {
            result.setUserId(String.valueOf(checkUser.getUserId()));
            result.setPhone(checkUser.getPhone());
            result.setEmail(checkUser.getEmail());
            result.setFirstName(checkUser.getFirstName());
            result.setLastName(checkUser.getLastName());
            result.setMiddleName(checkUser.getMiddleName());
            result.setRole(checkUser.getAuthority().getRole());
            result.setMessage("Login is successful, Welcome to Innovative Toll Solutions!");
            result.setTitle("success");
            Department department = checkUser.getDepartment();
            if (department != null) {
                result.setDepartment(department);
                result.setLoggedUser("DEPARTMENT");
                result.setDepartmentMode("YES");
                DashboardResponse response = getDashboard(pageable, "DEPARTMENT", department.getClientProfile(), department, "YES");
                result.setDashboardResponse(response);
                result.setClientId(String.valueOf(department.getDepartmentId()));
                result.setParentClientId(String.valueOf(department.getDepartmentId()));
            } else {
                ClientProfile profile = checkUser.getClientProfile();
                if (profile != null) {
                    DashboardResponse response = getDashboard(pageable, "CLIENT", profile, department, "NO");
                    result.setDashboardResponse(response);
                    result.setLoggedUser("CLIENT");
                    result.setDepartmentMode("NO");
                    result.setClientProfile(profile);
                    result.setClientId(email);
                    List<Department> departments = this.departmentService.findByClientProfile(profile);
                    if (departments != null && !departments.isEmpty()) {
                        result.setDepartments(departments);
                    }
                }
            }
            GenericResponse generic = new GenericResponse();
            generic.setResult(result.toString());
            return ResponseEntity.ok(generic);
        }
        result.setMessage("Password is incorrect!");
        result.setTitle("failed_pass");
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = {"/available/check_availability"}, produces = {"application/json; charset=UTF-8"}, method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<?> searchUsername(@ModelAttribute("search") Search search) {
        JobResponse result = new JobResponse();
        String username = search.getSearchItem();
        User user = this.userService.findByEmailToken(this.emailTokenService.findByEmailAddress(username));
        if (user == null) {
            result.setTitle("available");
            result.setMessage("Good news, the username is available!");
        } else {
            result.setTitle("booked");
            result.setMessage("Sorry, The username is already taken");
        }
        return ResponseEntity.ok(result);
    }

    private DashboardResponse getDashboard(@PageableDefault(100) Pageable pageable, String userType, ClientProfile profile, Department department, String departmentMode) {
        DashboardResponse result = new DashboardResponse();
        LocalDate today = LocalDate.now();
        String month = today.getMonth().toString();
        int year = today.getYear();
        switch (userType) {
            case "CLIENT":
                if (departmentMode != null && departmentMode.equals("YES")) {
                    dashboardDepartmentInfo(pageable, result, department, month, year);
                } else {
                    dashboardClientInfo(pageable, result, profile, month, year);
                }
                result.setMessage("Welcome to Innovative Toll Solutions!");
                result.setTitle("success");
                return result;
            case "DEPARTMENT":
                dashboardDepartmentInfo(pageable, result, department, month, year);
                result.setMessage("Welcome to Innovative Toll Solutions!");
                result.setTitle("success");
                return result;
        }
        LOG.info("USER ACCOUNT UNKNOWN");
        result.setMessage("Welcome to Innovative Toll Solutions!");
        result.setTitle("success");
        return result;
    }

    private DashboardResponse getTotalDashboard(@PageableDefault(100) Pageable pageable, String userType, ClientProfile profile, Department department, String departmentMode) {
        DashboardResponse result = new DashboardResponse();
        LocalDate today = LocalDate.now();
        String month = today.getMonth().toString();
        int year = today.getYear();
        switch (userType) {
            case "CLIENT":
                if (departmentMode != null && departmentMode.equals("YES")) {
                    dashboardDepartmentInfo(pageable, result, department, month, year);
                } else {
                    dashboardClientInfo(pageable, result, profile, month, year);
                }
                result.setMessage("Welcome to Innovative Toll Solutions!");
                result.setTitle("success");
                return result;
            case "DEPARTMENT":
                dashboardDepartmentInfo(pageable, result, department, month, year);
                result.setMessage("Welcome to Innovative Toll Solutions!");
                result.setTitle("success");
                return result;
        }
        LOG.info("USER ACCOUNT UNKNOWN");
        result.setMessage("Welcome to Innovative Toll Solutions!");
        result.setTitle("success");
        return result;
    }

    @RequestMapping(path = {"/dashboard/by/{userType}/{departmentMode}/{clientId}/{departmentId}/{email}/{month}/{year}"}, produces = {"application/json; charset=UTF-8"}, method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<?> loadDashboard(@PageableDefault(100) Pageable pageable, @PathVariable("userType") String userType, @PathVariable("departmentMode") String departmentMode, @PathVariable("clientId") Long clientId, @PathVariable("departmentId") Long departmentId, @PathVariable("email") String email, @PathVariable("month") String month, @PathVariable("year") int year) {
        String str;
        Department department;
        DashboardResponse result = new DashboardResponse();
        EmailToken etoken = this.emailTokenService.findByEmailAddress(email.toLowerCase());
        User checkUser = this.userService.findByEmailToken(etoken);
        if (checkUser == null) {
            result.setMessage(email.toUpperCase() + " does not exist!");
            result.setTitle("fail");
            GenericResponse genericResponse = new GenericResponse();
            genericResponse.setResult(result.toString());
            return ResponseEntity.ok(genericResponse);
        }
        GenericResponse generic = new GenericResponse();
        LocalDate today = LocalDate.now();
        ClientProfile profile = this.clientProfileService.findByClientProfileId(clientId);
        switch (userType) {
            case "CLIENT":
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department1 = this.departmentService.findByDepartmentId(departmentId);
                    dashboardDepartmentInfo(pageable, result, department1, month, year);
                } else {
                    dashboardClientInfo(pageable, result, profile, month, year);
                }
                result.setMessage("Welcome to Innovative Toll Solutions!");
                result.setTitle("success");
                generic.setResult(result.toString());
                return ResponseEntity.ok(generic);
            case "DEPARTMENT":
                department = this.departmentService.findByDepartmentId(departmentId);
                dashboardDepartmentInfo(pageable, result, department, month, year);
                result.setMessage("Welcome to Innovative Toll Solutions!");
                result.setTitle("success");
                generic = new GenericResponse();
                generic.setResult(result.toString());
                return ResponseEntity.ok(generic);
        }
        LOG.info("USER ACCOUNT UNKNOWN");
        result.setMessage("Welcome to Innovative Toll Solutions!");
        result.setTitle("success");
        generic.setResult(result.toString());
        return ResponseEntity.ok(generic);
    }

    @RequestMapping(path = {"/dashboard/transaction/by/{userType}/{departmentMode}/{clientId}/{departmentId}/{email}/{month}/{year}"}, produces = {"application/json; charset=UTF-8"}, method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<?> loadTransactionDashboard(@PageableDefault(100) Pageable pageable, @PathVariable("userType") String userType, @PathVariable("departmentMode") String departmentMode, @PathVariable("clientId") Long clientId, @PathVariable("departmentId") Long departmentId, @PathVariable("email") String email, @PathVariable("month") String month, @PathVariable("year") int year) {
        String str;
        Department department;
        Page<Transaction> pages;
        List<Transaction> transactions;
        DashboardResponse result = new DashboardResponse();
        EmailToken etoken = this.emailTokenService.findByEmailAddress(email.toLowerCase());
        User checkUser = this.userService.findByEmailToken(etoken);
        if (checkUser == null) {
            result.setMessage(email.toUpperCase() + " does not exist!");
            result.setTitle("fail");
            GenericResponse genericResponse = new GenericResponse();
            genericResponse.setResult(result.toString());
            return ResponseEntity.ok(genericResponse);
        }

        GenericResponse generic = new GenericResponse();
        LocalDate today = LocalDate.now();
        ClientProfile profile = this.clientProfileService.findByClientProfileId(clientId);
        switch (userType) {
            case "CLIENT":
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department1 = this.departmentService.findByDepartmentId(departmentId);
                    long count = this.transactionService.countByDepartmentAndTransactionMonthAndTransactionYear(department1, today.getMonth().toString(), today.getYear());
                    Page<Transaction> page = this.transactionService.findByClientProfileAndDepartmentAndTransactionMonthAndTransactionYearOrderByTransactionIdDesc(pageable, profile, department1, month, year);
                    List<Transaction> list = page.getContent();
                    result.setTransactions(list);
                } else {
                    Page<Transaction> page = this.transactionService.findByClientProfileAndTransactionMonthAndTransactionYearOrderByTransactionIdDesc(pageable, profile, month, year);
                    List<Transaction> list = page.getContent();
                    result.setTransactions(list);
                }
                result.setMessage("Transaction loaded successfuly!");
                result.setTitle("success");
                generic.setResult(result.toString());
                return ResponseEntity.ok(generic);
            case "DEPARTMENT":
                department = this.departmentService.findByDepartmentId(departmentId);
                pages = this.transactionService.findByClientProfileAndDepartmentAndTransactionMonthAndTransactionYearOrderByTransactionIdDesc(pageable, profile, department, month, year);
                transactions = pages.getContent();
                result.setTransactions(transactions);
                result.setMessage("Transaction loaded successfuly!");
                result.setTitle("success");
                generic = new GenericResponse();
                generic.setResult(result.toString());
                return ResponseEntity.ok(generic);
        }
        LOG.info("USER ACCOUNT UNKNOWN");
        result.setMessage("Transaction loaded successfuly!");
        result.setTitle("success");
        generic.setResult(result.toString());
        return ResponseEntity.ok(generic);
    }

    @RequestMapping(path = {"/dashboard/vehicle/by/{userType}/{departmentMode}/{clientId}/{departmentId}/{email}/{month}/{year}"}, produces = {"application/json; charset=UTF-8"}, method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<?> loadVehicleDashboard(@PathVariable("userType") String userType, @PathVariable("departmentMode") String departmentMode, @PathVariable("clientId") Long clientId, @PathVariable("departmentId") Long departmentId, @PathVariable("email") String email, @PathVariable("month") String month, @PathVariable("year") int year) {
        String str;
        Department department;
        List<Vehicle> vehicles;
        DashboardResponse result = new DashboardResponse();
        EmailToken etoken = this.emailTokenService.findByEmailAddress(email.toLowerCase());
        User checkUser = this.userService.findByEmailToken(etoken);
        if (checkUser == null) {
            result.setMessage(email.toUpperCase() + " does not exist!");
            result.setTitle("fail");
            GenericResponse genericResponse = new GenericResponse();
            genericResponse.setResult(result.toString());
            return ResponseEntity.ok(genericResponse);
        }

        GenericResponse generic = new GenericResponse();
        LocalDate today = LocalDate.now();
        ClientProfile profile = this.clientProfileService.findByClientProfileId(clientId);
        switch (userType) {
            case "CLIENT":
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department1 = this.departmentService.findByDepartmentId(departmentId);
                    List<Vehicle> list = this.vehicleService.findByClientProfileAndDepartmentOrderByVehicleIdDesc(department1.getClientProfile(), department1);
                    result.setVehicles(list);
                } else {
                    List<Vehicle> list = this.vehicleService.findByClientProfileOrderByVehicleIdDesc(profile);
                    result.setVehicles(list);
                }
                result.setMessage("Transaction loaded successfuly!");
                result.setTitle("success");
                generic = new GenericResponse();
                generic.setResult(result.toString());
                return ResponseEntity.ok(generic);
            case "DEPARTMENT":
                department = this.departmentService.findByDepartmentId(departmentId);
                vehicles = this.vehicleService.findByClientProfileAndDepartmentOrderByVehicleIdDesc(department.getClientProfile(), department);
                result.setVehicles(vehicles);
                result.setMessage("Transaction loaded successfuly!");
                result.setTitle("success");
                generic = new GenericResponse();
                generic.setResult(result.toString());
                return ResponseEntity.ok(generic);
        }
        LOG.info("USER ACCOUNT UNKNOWN");
        result.setMessage("Transaction loaded successfuly!");
        result.setTitle("success");
        generic.setResult(result.toString());
        return ResponseEntity.ok(generic);
    }

    @RequestMapping(path = {"/transaction/range/by/{userType}/{departmentMode}/{clientId}/{departmentId}/{email}/{startDate}/{endDate}"}, produces = {"application/json; charset=UTF-8"}, method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<?> searchDate(@PathVariable("userType") String user_type, @PathVariable("departmentMode") String departmentMode, @PathVariable("clientId") Long clientId, @PathVariable("departmentId") Long departmentId, @PathVariable("email") String email, @PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) {
        String str;
        ClientProfile profile;
        Department department;
        List<Transaction> transactions;
        double total;
        Aggregation aggregation;
        AggregationResults<Tollspendings> groupResults;
        List<Tollspendings> expenditureReport;
        DashboardResponse result = new DashboardResponse();
        EmailToken etoken = this.emailTokenService.findByEmailAddress(email.toLowerCase());
        User checkUser = this.userService.findByEmailToken(etoken);
        if (checkUser == null) {
            result.setMessage(email.toUpperCase() + " does not exist!");
            result.setTitle("fail");
            GenericResponse genericResponse = new GenericResponse();
            genericResponse.setResult(result.toString());
            return ResponseEntity.ok(genericResponse);
        }
        LocalDate today = LocalDate.now();
        Date end_Date = getConvertedDate(endDate);
        Date start_Date = getConvertedDate(startDate);
        GenericResponse generic = new GenericResponse();
        switch (user_type) {
            case "CLIENT":
                profile = this.clientProfileService.findByClientProfileId(clientId);
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department1 = this.departmentService.findByDepartmentId(departmentId);
                    List<Transaction> list = getDepartmentTransactionRange(department1, start_Date, end_Date);
                    if (list == null || list.isEmpty()) {
                        result.setMessage("No transactions found for " + String.valueOf(department1.getDepartmentName()));
                        result.setTitle("fail");
                        GenericResponse genericResponse = new GenericResponse();
                        genericResponse.setResult(result.toString());
                        return ResponseEntity.ok(genericResponse);
                    }
                    result.setTitle("success");
                    result.setMessage("Transactions data found");
                    result.setTransactions(list);
                    double d = 0.0D;
                    Aggregation aggregation1 = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("department").is(department1).and("transactionDispute").is(false).and("transactionDate").gte(startDate).lte(endDate)),
                        (AggregationOperation) Aggregation.group(new String[]{"clientProfile"}).sum("amount").as("total")});
                    AggregationResults<Tollspendings> aggregationResults = this.mongoTemplate.aggregate(aggregation1, Transaction.class, Tollspendings.class);
                    List<Tollspendings> list1 = aggregationResults.getMappedResults();
                    if (list1 != null && !list1.isEmpty()) {
                        Tollspendings toll = list1.get(0);
                        d = toll.getTotal();
                    }
                    result.setTransactionTotal(d);
                } else {
                    List<Transaction> list = getCLientProfileTransactionRange(profile, start_Date, end_Date);
                    if (list == null || list.isEmpty()) {
                        result.setMessage("No transactions uploaded for " + String.valueOf(profile.getCompanyName()));
                        result.setTitle("fail");
                        GenericResponse genericResponse1 = new GenericResponse();
                        genericResponse1.setResult(result.toString());
                        return ResponseEntity.ok(genericResponse1);
                    }
                    result.setTitle("success");
                    double d = 0.0D;
                    Aggregation aggregation1 = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("clientProfile").is(profile).and("transactionDispute").is(false).and("transactionDate").gte(startDate).lte(endDate)),
                        (AggregationOperation) Aggregation.group(new String[]{"clientProfile"}).sum("amount").as("total")});
                    AggregationResults<Tollspendings> aggregationResults = this.mongoTemplate.aggregate(aggregation1, Transaction.class, Tollspendings.class);
                    List<Tollspendings> list1 = aggregationResults.getMappedResults();
                    if (list1 != null && !list1.isEmpty()) {
                        Tollspendings toll = list1.get(0);
                        d = toll.getTotal();
                    }
                    result.setTransactionTotal(d);
                    result.setMessage("Transactions data found");
                    result.setTransactions(list);
                    GenericResponse genericResponse = new GenericResponse();
                    genericResponse.setResult(result.toString());
                    return ResponseEntity.ok(genericResponse);
                }
                LOG.info("returning search results");
                generic.setResult(result.toString());
                return ResponseEntity.ok(generic);
            case "DEPARTMENT":
                department = this.departmentService.findByDepartmentId(departmentId);
                transactions = getDepartmentTransactionRange(department, start_Date, end_Date);
                if (transactions == null || transactions.isEmpty()) {
                    result.setMessage("No transactions uploaded on " + String.valueOf(department.getDepartmentName()));
                    result.setTitle("fail");
                    GenericResponse genericResponse = new GenericResponse();
                    genericResponse.setResult(result.toString());
                    return ResponseEntity.ok(genericResponse);
                }
                result.setTitle("success");
                LOG.info("transactions range data found");
                result.setMessage("Transactions data found");
                total = 0.0D;
                aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("department").is(department).and("transactionDispute").is(false).and("transactionDate").gte(startDate).lte(endDate)), (AggregationOperation) Aggregation.group(new String[]{"clientProfile"}).sum("amount").as("total")});
                groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, Tollspendings.class);
                expenditureReport = groupResults.getMappedResults();
                if (expenditureReport != null && !expenditureReport.isEmpty()) {
                    Tollspendings toll = expenditureReport.get(0);
                    total = toll.getTotal();
                }
                result.setTransactionTotal(total);
                result.setTransactions(transactions);
                LOG.info("returning search results");
                generic = new GenericResponse();
                generic.setResult(result.toString());
                return ResponseEntity.ok(generic);
        }
        result.setMessage("USER ACCOUNT UNKNOWN");
        LOG.info("USER ACCOUNT UNKNOWN");
        LOG.info("returning search results");
        generic.setResult(result.toString());
        return ResponseEntity.ok(generic);
    }

    private List<Transaction> getCLientProfileTransactionRange(ClientProfile profile, Date startDate, Date endDate) {
        Query query = new Query();
        query.addCriteria((CriteriaDefinition) Criteria.where("clientProfile").is(profile));
        LOG.info(startDate.toString());
        query.addCriteria((CriteriaDefinition) Criteria.where("transactionDate").gte(startDate).lte(endDate));
        List<Transaction> transactions = this.mongoTemplate.find(query, Transaction.class);
        return transactions;
    }

    private List<Transaction> getDepartmentTransactionRange(Department department, Date startDate, Date endDate) {
        Query query = new Query();
        query.addCriteria((CriteriaDefinition) Criteria.where("department").is(department));
        query.addCriteria((CriteriaDefinition) Criteria.where("transactionDate").gte(startDate).lte(endDate));
        List<Transaction> transactions = this.mongoTemplate.find(query, Transaction.class);
        return transactions;
    }

    void dashboardClientInfo(@PageableDefault(100) Pageable pageable, DashboardResponse mv, ClientProfile profile, String month, int year) {
        if (profile != null) {
            LocalDate today = LocalDate.now();
            setClientCitations(profile, mv);
            calculateAgencySpending(profile, mv);
            calculateDailyFigures(profile, mv);
            calculateMonthlyFigures(profile, mv);
            mv.setSpendingAmount(getTwoDecimal(getClientTollSpending(profile)));
            mv.setVehicleCount(this.vehicleService.countByClientProfile(profile));
            clientSavings(profile, mv);
            mv.setTransactionCount(this.transactionService.countByClientProfileAndTransactionMonthAndTransactionYear(profile, today.getMonth().toString(), today.getYear()));
            mv.setTollSpendings(highClientSpenders(profile));
            mv.setDisputedTransactionCount(this.transactionDisputeService.countByClientProfile(profile));
        }
    }

    void dashboardDepartmentInfo(@PageableDefault(100) Pageable pageable, DashboardResponse mv, Department department, String month, int year) {
        if (department != null) {
            LocalDate today = LocalDate.now();
            setDepartmentCitations(department, mv);
            calculateAgencySpending(department, mv);
            calculateDailyFigures(department, mv);
            calculateMonthlyFigures(department, mv);
            mv.setTotalSavings(this.departmentSavingService.findByDepartment(department).getAmount());
            mv.setSpendingAmount(getTwoDecimal(getDepartmentTollSpending(department)));
            mv.setVehicleCount(this.vehicleService.countByDepartmentAndVehicleStatus(department, "active"));
            mv.setSpendingAmount(this.departmentSavingService.findByDepartment(department).getAmount());
            mv.setTransactionCount(this.transactionService.countByDepartmentAndPostedMonthAndPostedYear(department, today.getMonth().toString(), today.getYear()));
            mv.setTollSpendings(highDepartmentSpenders(department));
            mv.setDisputedTransactionCount(this.transactionDisputeService.countByDepartment(department));
        }
    }

    void dashboardTotalClientInfo(@PageableDefault(100) Pageable pageable, DashboardResponse mv, ClientProfile profile, String month, int year) {
        if (profile != null) {
            LocalDate today = LocalDate.now();
            setClientCitations(profile, mv);
            calculateAgencySpending(profile, mv);
            calculateDailyFigures(profile, mv);
            calculateMonthlyFigures(profile, mv);
            mv.setSpendingAmount(getTwoDecimal(getClientTollSpending(profile)));
            mv.setVehicleCount(this.vehicleService.countByClientProfile(profile));
            clientSavings(profile, mv);
            mv.setTransactionCount(this.transactionService.countByClientProfileAndTransactionMonthAndTransactionYear(profile, today.getMonth().toString(), today.getYear()));
            mv.setTollSpendings(highClientSpenders(profile));
            mv.setDisputedTransactionCount(this.transactionDisputeService.countByClientProfile(profile));
            Page<Transaction> pages = this.transactionService.findByClientProfileAndTransactionMonthAndTransactionYearOrderByTransactionIdDesc(pageable, profile, month, year);
            List<Transaction> transactions = pages.getContent();
            mv.setTransactions(transactions);
        }
    }

    void dashboardTotalDepartmentInfo(@PageableDefault(100) Pageable pageable, DashboardResponse mv, Department department, String month, int year) {
        if (department != null) {
            LocalDate today = LocalDate.now();
            setDepartmentCitations(department, mv);
            calculateAgencySpending(department, mv);
            calculateDailyFigures(department, mv);
            calculateMonthlyFigures(department, mv);
            mv.setTotalSavings(this.departmentSavingService.findByDepartment(department).getAmount());
            mv.setSpendingAmount(getTwoDecimal(getDepartmentTollSpending(department)));
            mv.setVehicleCount(this.vehicleService.countByDepartmentAndVehicleStatus(department, "active"));
            mv.setSpendingAmount(this.departmentSavingService.findByDepartment(department).getAmount());
            mv.setTransactionCount(this.transactionService.countByDepartmentAndPostedMonthAndPostedYear(department, today.getMonth().toString(), today.getYear()));
            mv.setTollSpendings(highDepartmentSpenders(department));
            mv.setDisputedTransactionCount(this.transactionDisputeService.countByDepartment(department));
            Page<Transaction> pages = this.transactionService.findByClientProfileAndDepartmentAndTransactionMonthAndTransactionYearOrderByTransactionIdDesc(pageable, department.getClientProfile(), department, month, year);
            List<Transaction> transactions = pages.getContent();
            mv.setTransactions(transactions);
        }
    }

    void clientSavings(ClientProfile profile, DashboardResponse mv) {
        List<Department> departments = this.departmentService.findByClientProfile(profile);
        if (departments == null || departments.isEmpty()) {
            mv.setTotalSavings(this.savingService.findByClientProfile(profile).getAmount());
        } else {
            double total = 0.0D;
            LocalDate today = LocalDate.now();
            int year = today.getYear();
            String month = today.getMonth().toString();
            Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("clientProfile").is(profile)),
                (AggregationOperation) Aggregation.group(new String[]{"clientProfile"}).sum("amount").as("total")});
            AggregationResults<Tollspendings> groupResults = this.mongoTemplate.aggregate(aggregation, DepartmentSaving.class, Tollspendings.class);
            List<Tollspendings> expenditureReport = groupResults.getMappedResults();
            if (expenditureReport != null && !expenditureReport.isEmpty()) {
                Tollspendings toll = expenditureReport.get(0);
                total = toll.getTotal();
            }
            mv.setTotalSavings(total);
        }
    }

    Double getDepartmentTollSpending(Department department) {
        double total = 0.0D;
        LocalDate today = LocalDate.now();
        String month = today.getMonth().toString();
        int year = today.getYear();
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("postedYear").is(Integer.valueOf(year)).and("postedMonth").is(month).and("department").is(department).and("transactionDispute").is(Boolean.valueOf(false))),
            (AggregationOperation) Aggregation.group(new String[]{"postedMonth"}).sum("amount").as("total")});
        AggregationResults<Tollspendings> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, Tollspendings.class);
        List<Tollspendings> expenditureReport = groupResults.getMappedResults();
        if (expenditureReport != null && !expenditureReport.isEmpty()) {
            Tollspendings toll = expenditureReport.get(0);
            total = toll.getTotal();
        }
        return total;
    }

    Double getClientTollSpending(ClientProfile profile) {
        double total = 0.0D;
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        String month = today.getMonth().toString();
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("postedYear").is(year).and("postedMonth").is(month).and("clientProfile").is(profile).and("transactionDispute").is(false)),
            (AggregationOperation) Aggregation.group(new String[]{"postedMonth"}).sum("amount").as("total")});
        AggregationResults<Tollspendings> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, Tollspendings.class);
        List<Tollspendings> expenditureReport = groupResults.getMappedResults();
        if (expenditureReport != null && !expenditureReport.isEmpty()) {
            Tollspendings toll = expenditureReport.get(0);
            total = toll.getTotal();
        }
        return total;
    }

    void setClientCitations(ClientProfile profile, DashboardResponse mv) {
        LocalDate today = LocalDate.now();
        String month = today.getMonth().toString();
        int year = today.getYear();
        double total = 0.0D;
        CitationType type = this.citationTypeService.findByCitationTypeName("SPEED TICKET");
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("citationYear").is(year).and("citationMonth").is(month).and("clientProfile").is(profile).and("citationType").is(type)),
            (AggregationOperation) Aggregation.group(new String[]{"citationMonth"}).sum("citationAmount").as("total")});
        AggregationResults<Tollspendings> groupResults = this.mongoTemplate.aggregate(aggregation, Citation.class, Tollspendings.class);
        List<Tollspendings> expenditureReport = groupResults.getMappedResults();
        if (expenditureReport != null && !expenditureReport.isEmpty()) {
            Tollspendings toll = expenditureReport.get(0);
            total = toll.getTotal();
        }
        mv.setSpeedTicketCount(this.citationService.countByClientProfileAndCitationMonthAndCitationYearAndCitationType(profile, month, year, type));
        mv.setSpeedTicketAmount(total);
        total = 0.0D;
        type = this.citationTypeService.findByCitationTypeName("PARKING");
        aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("citationYear").is(year).and("citationMonth").is(month).and("clientProfile").is(profile).and("citationType").is(type)),
            (AggregationOperation) Aggregation.group(new String[]{"citationMonth"}).sum("citationAmount").as("total")});
        groupResults = this.mongoTemplate.aggregate(aggregation, Citation.class, Tollspendings.class);
        expenditureReport = groupResults.getMappedResults();
        if (expenditureReport != null && !expenditureReport.isEmpty()) {
            Tollspendings toll = expenditureReport.get(0);
            total = toll.getTotal();
        }
        mv.setParkingCount(this.citationService.countByClientProfileAndCitationMonthAndCitationYearAndCitationType(profile, month, year, type));
        mv.setParkingAmount(total);
        total = 0.0D;
        type = this.citationTypeService.findByCitationTypeName("RED LIGHT");
        aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("citationYear").is(year).and("citationMonth").is(month).and("clientProfile").is(profile).and("citationType").is(type)),
            (AggregationOperation) Aggregation.group(new String[]{"citationMonth"}).sum("citationAmount").as("total")});
        groupResults = this.mongoTemplate.aggregate(aggregation, Citation.class, Tollspendings.class);
        expenditureReport = groupResults.getMappedResults();
        if (expenditureReport != null && !expenditureReport.isEmpty()) {
            Tollspendings toll = expenditureReport.get(0);
            total = toll.getTotal();
        }
        mv.setRedLightAmount(total);
        mv.setRedLightCount(this.citationService.countByClientProfileAndCitationMonthAndCitationYearAndCitationType(profile, month, year, type));
    }

    void setDepartmentCitations(Department department, DashboardResponse mv) {
        LocalDate today = LocalDate.now();
        String month = today.getMonth().toString();
        int year = today.getYear();
        double total = 0.0D;
        CitationType type = this.citationTypeService.findByCitationTypeName("SPEED TICKET");
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("citationYear").is(year).and("citationMonth").is(month).and("department").is(department).and("citationType").is(type)),
            (AggregationOperation) Aggregation.group(new String[]{"citationMonth"}).sum("citationAmount").as("total")});
        AggregationResults<Tollspendings> groupResults = this.mongoTemplate.aggregate(aggregation, Citation.class, Tollspendings.class);
        List<Tollspendings> expenditureReport = groupResults.getMappedResults();
        if (expenditureReport != null && !expenditureReport.isEmpty()) {
            Tollspendings toll = expenditureReport.get(0);
            total = toll.getTotal();
        }
        mv.setSpeedTicketCount(this.citationService.countByDepartmentAndCitationMonthAndCitationYearAndCitationType(department, month, year, type));
        mv.setSpeedTicketAmount(total);
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
        mv.setParkingCount(this.citationService.countByDepartmentAndCitationMonthAndCitationYearAndCitationType(department, month, year, type));
        mv.setParkingAmount(total);
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
        mv.setRedLightAmount(total);
        mv.setRedLightCount(this.citationService.countByDepartmentAndCitationMonthAndCitationYearAndCitationType(department, month, year, type));
    }

    List<Tollspendings> highClientSpenders(ClientProfile profile) {
        LocalDate today = LocalDate.now();
        String month = today.getMonth().toString();
        int year = today.getYear();
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("postedYear").is(year).and("postedMonth").is(month).and("clientProfile").is(profile)),
            (AggregationOperation) Aggregation.group(new String[]{"vehicle"}).sum("amount").as("total").addToSet("vehicle").as("vehicle")});
        AggregationResults<Tollspendings> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, Tollspendings.class);
        List<Tollspendings> expenditureReport = groupResults.getMappedResults();
        return expenditureReport;
    }

    List<Tollspendings> highDepartmentSpenders(Department department) {
        LocalDate today = LocalDate.now();
        String month = today.getMonth().toString();
        int year = today.getYear();
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("postedYear").is(year).and("postedMonth").is(month).and("department").is(department)),
            (AggregationOperation) Aggregation.group(new String[]{"vehicle"}).sum("amount").as("total").addToSet("vehicle").as("vehicle")});
        AggregationResults<Tollspendings> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, Tollspendings.class);
        List<Tollspendings> expenditureReport = groupResults.getMappedResults();
        return expenditureReport;
    }

    void calculateAgencySpending(ClientProfile profile, DashboardResponse mv) {
        List<DailySpending> spendings = getAgencyClientTollSpending(profile);
        mv.setAgencySpendings(spendings);
    }

    void calculateAgencySpending(Department department, DashboardResponse mv) {
        List<DailySpending> spendings = getAgencyDepartmentTollSpending(department);
        mv.setAgencySpendings(spendings);
    }

    List<DailySpending> getAgencyDepartmentTollSpending(Department department) {
        LocalDate today = LocalDate.now();
        String month = today.getMonth().toString();
        int year = today.getYear();
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("postedYear").is(year).and("postedMonth").is(month).and("department").is(department)),
            (AggregationOperation) Aggregation.group(new String[]{"agency"}).sum("amount").as("tollAmount").addToSet("agency").as("agency")});
        AggregationResults<DailySpending> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, DailySpending.class);
        List<DailySpending> spendings = groupResults.getMappedResults();
        return spendings;
    }

    List<DailySpending> getAgencyClientTollSpending(ClientProfile profile) {
        LocalDate today = LocalDate.now();
        String month = today.getMonth().toString();
        int year = today.getYear();
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("postedYear").is(year).and("postedMonth").is(month).and("clientProfile").is(profile)),
            (AggregationOperation) Aggregation.group(new String[]{"agency"}).sum("amount").as("tollAmount").addToSet("agency").as("agency")});
        AggregationResults<DailySpending> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, DailySpending.class);
        List<DailySpending> spendings = groupResults.getMappedResults();
        return spendings;
    }

    void calculateMonthlyFigures(ClientProfile profile, DashboardResponse mv) {
        List<DailySpending> spendings = getMonthlyClientTollSpending(profile);
        mv.setMonthlySpendings(spendings);
    }

    void calculateMonthlyFigures(Department department, DashboardResponse mv) {
        List<DailySpending> spendings = getMonthlyDepartmentTollSpending(department);
        mv.setMonthlySpendings(spendings);
    }

    List<DailySpending> getMonthlyDepartmentTollSpending(Department department) {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("postedYear").is(year).and("department").is(department)),
            (AggregationOperation) Aggregation.group(new String[]{"postedMonth"}).sum("amount").as("tollAmount").addToSet("postedMonth").as("month")});
        AggregationResults<DailySpending> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, DailySpending.class);
        List<DailySpending> spendings = groupResults.getMappedResults();
        return spendings;
    }

    List<DailySpending> getMonthlyClientTollSpending(ClientProfile profile) {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("postedYear").is(year).and("clientProfile").is(profile)),
            (AggregationOperation) Aggregation.group(new String[]{"postedMonth"}).sum("amount").as("tollAmount").addToSet("postedMonth").as("month")});
        AggregationResults<DailySpending> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, DailySpending.class);
        List<DailySpending> spendings = groupResults.getMappedResults();
        return spendings;
    }

    void calculateDailyFigures(ClientProfile profile, DashboardResponse mv) {
        List<DailySpending> spendings = getDailiesClientTollSpending(profile);
        mv.setDailiesSpendings(spendings);
    }

    void calculateDailyFigures(Department department, DashboardResponse mv) {
        List<DailySpending> spendings = getDailiesDepartmentTollSpending(department);
        mv.setDailiesSpendings(spendings);
    }

    List<DailySpending> getDailiesDepartmentTollSpending(Department department) {
        LocalDate today = LocalDate.now();
        String month = today.getMonth().toString();
        int year = today.getYear();
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("postedYear").is(year).and("postedMonth").is(month).and("department").is(department)),
            (AggregationOperation) Aggregation.group(new String[]{"postedDate"}).sum("amount").as("tollAmount").addToSet("postedDate").as("tollDate"),
            (AggregationOperation) Aggregation.sort(Sort.Direction.ASC, new String[]{Aggregation.previousOperation(), "postedDate"})});
        AggregationResults<DailySpending> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, DailySpending.class);
        List<DailySpending> spendings = groupResults.getMappedResults();
        return spendings;
    }

    List<DailySpending> getDailiesClientTollSpending(ClientProfile profile) {
        LocalDate today = LocalDate.now();
        String month = today.getMonth().toString();
        int year = today.getYear();
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("postedYear").is(year).and("postedMonth").is(month).and("clientProfile").is(profile)),
            (AggregationOperation) Aggregation.group(new String[]{"postedDate"}).sum("amount").as("tollAmount").addToSet("postedDate").as("tollDate"),
            (AggregationOperation) Aggregation.sort(Sort.Direction.ASC, new String[]{Aggregation.previousOperation(), "postedDate"})});
        AggregationResults<DailySpending> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, DailySpending.class);
        List<DailySpending> spendings = groupResults.getMappedResults();
        return spendings;
    }

    @RequestMapping(path = {"change/password/{currentPass}/{newPass}/{confirmPass}"}, produces = {"application/json; charset=UTF-8"}, method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<?> changePassword(HttpSession session, @PathVariable("currentPass") String currentPass, @PathVariable("newPass") String newPass, @PathVariable("confirmPass") String confirmPass) {
        JobResponse result = new JobResponse();
        if (currentPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            result.setMessage("Fill all required fileds!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Session expired, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        EmailToken etoken = this.emailTokenService.findByEmailAddress(ns_mail.toLowerCase());
        User user = this.userService.findByEmailToken(etoken);
        if (user == null) {
            result.setMessage("User account does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        if (user.getPassword().equals(getHashed(currentPass))) {
            if (newPass.equals(confirmPass)) {
                user.setPassword(getHashed(newPass));
                this.userService.save(user);
                result.setMessage("Password modified successfuly!");
                result.setTitle("success");
            } else {
                result.setMessage("New passwords does not match!");
                result.setTitle("fail");
            }
        } else {
            result.setMessage("The current password entered is invalid!");
            result.setTitle("fail");
        }
        return ResponseEntity.ok(result);
    }

}
