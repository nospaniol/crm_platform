package com.crm.controller.client;

import com.crm.enums.ExportReportType;
import com.crm.generic.ExcelTransactionExporter;
import com.crm.generic.Nospaniol;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.JobResponse;
import com.crm.model.Search;
import com.crm.model.Tollspendings;
import com.crm.model.Transaction;
import com.crm.model.TransactionDispute;
import com.crm.model.TransactionUpload;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({"client_transaction"})
public class ClientTransactionController extends Nospaniol {

    int counter = 0;

    int header = 0;

    Double sumAmount = 0.0;

    String sclient;

    String sdepartment;

    Page<Transaction> pages = null;

    private void getTimeStamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp);
        //LOG.info("*****************" + timestamp.toString());
    }

    @RequestMapping(value = {"view_transaction"}, method = {RequestMethod.GET})
    public ModelAndView viewTransactions(@PageableDefault(100) Pageable pageable, RedirectAttributes redir, HttpServletRequest req, HttpSession session, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        String clientId = (String) req.getSession().getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        mv.setViewName("clientviewtransactions");
        String departmentMode, departmentId;
        Department department;
        Page<Transaction> pages;
        List<Transaction> trans;
        double total;
        Aggregation aggregation;
        AggregationResults<Tollspendings> groupResults;
        List<Tollspendings> expenditureReport;
        mv.addObject("menutype", "transaction");
        loadCombos(mv);
        int year = Integer.valueOf((String) req.getSession().getAttribute("year_mode"));
        String month = (String) req.getSession().getAttribute("month_mode");
        //LOG.info("DATES USED" + String.valueOf(year) + month);
        session.setAttribute("transaction_start_date", null);
        session.setAttribute("transaction_end_date", null);
        String user_type = (String) req.getSession().getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                departmentMode = (String) session.getAttribute("department_mode");
                departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department1 = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                    Page<Transaction> page = this.transactionService.findByDepartmentAndPostedMonthAndPostedYearOrderByTransactionIdDesc(pageable, department1, month, year);
                    mv.addObject("number", page.getNumber());
                    mv.addObject("totalPages",countPrintingInfo(session)/100);
                    mv.addObject("totalElements", page.getTotalElements());
                    mv.addObject("size", page.getSize());
                    List<Transaction> list1 = page.getContent();
                    mv.addObject("allTransactions", list1);
                    double d = 0.0D;
                    Aggregation aggregation1 = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("department").is(department1).and("transactionDispute").is(false).and("transactionMonth").is(month).and("transactionYear").is(year)),
                        (AggregationOperation) Aggregation.group(new String[]{"department"}).sum("amount").as("total")});
                    AggregationResults<Tollspendings> aggregationResults = this.mongoTemplate.aggregate(aggregation1, Transaction.class, Tollspendings.class);
                    List<Tollspendings> list = aggregationResults.getMappedResults();
                    if (list != null && !list.isEmpty()) {
                        Tollspendings toll = list.get(0);
                        d = toll.getTotal();
                    }
                    mv.addObject("totalAmount", getTwoDecimal(d));
                } else {
                    getTimeStamp();
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    Page<Transaction> page = this.transactionService.findByClientProfileAndPostedMonthAndPostedYearOrderByTransactionIdDesc(pageable, profile, month, year);
                    mv.addObject("number", page.getNumber());
                    mv.addObject("totalPages", page.getTotalPages()-1);
                    mv.addObject("totalElements", page.getTotalElements());
                    mv.addObject("size", page.getSize());
                    double d = 0.0D;
                    getTimeStamp();
                    Aggregation aggregation1 = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("clientProfile").is(profile).and("transactionDispute").is(false).and("transactionMonth").is(month).and("transactionYear").is(year)),
                        (AggregationOperation) Aggregation.group(new String[]{"clientProfile"}).sum("amount").as("total")});
                    AggregationResults<Tollspendings> aggregationResults = this.mongoTemplate.aggregate(aggregation1, Transaction.class, Tollspendings.class);
                    List<Tollspendings> list = aggregationResults.getMappedResults();
                    if (list != null && !list.isEmpty()) {
                        Tollspendings toll = list.get(0);
                        d = toll.getTotal();
                    }
                    mv.addObject("totalAmount", getTwoDecimal(d));
                    List<Transaction> list1 = page.getContent();
                    mv.addObject("allTransactions", list1);
                }
                getTimeStamp();
                mv.addObject("transaction", new TransactionUpload());
                mv.addObject("topic", "Recently added transactions");
                break;
            case "DEPARTMENT":
                department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                pages = this.transactionService.findByDepartmentAndPostedMonthAndPostedYearOrderByTransactionIdDesc(pageable, department, month, year);
                mv.addObject("number", pages.getNumber());
                mv.addObject("totalPages", pages.getTotalPages());
                mv.addObject("totalElements", pages.getTotalElements());
                mv.addObject("size", pages.getSize());
                mv.addObject("allTransactions", pages.getContent());
                trans = pages.getContent();
                total = 0.0D;
                aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("department").is(department).and("transactionDispute").is(false).and("transactionMonth").is(month).and("transactionYear").is(year)), (AggregationOperation) Aggregation.group(new String[]{"department"}).sum("amount").as("total")});
                groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, Tollspendings.class);
                expenditureReport = groupResults.getMappedResults();
                if (expenditureReport != null && !expenditureReport.isEmpty()) {
                    Tollspendings toll = expenditureReport.get(0);
                    total = toll.getTotal();
                }
                mv.addObject("totalAmount", getTwoDecimal(total));
                mv.addObject("transaction", new TransactionUpload());
                break;
        }
        getTimeStamp();
        mv.addObject("transaction", new TransactionUpload());
        return mv;
    }

    @RequestMapping(value = {"disputes"}, method = {RequestMethod.GET})
    public ModelAndView viewDisputes(@PageableDefault(100) Pageable pageable, RedirectAttributes redir, HttpServletRequest req, HttpSession session, HttpServletResponse response) {
        // response.addHeader("Cache-Control", "max-age=600, must-revalidate, no-transform");
        String departmentMode, departmentId;
        Department department;
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "transaction");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        mv.setViewName("clientviewtransactiondisputes");
        session.setAttribute("transaction_start_date", "NONE");
        session.setAttribute("transaction_end_date", "NONE");
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                departmentMode = (String) session.getAttribute("department_mode");
                departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department1 = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));

                    mv.addObject("allDisputes", this.transactionDisputeService.findByDepartmentAndDisputeStatusOrderByDisputeIdDesc(department1, "ACTIVE"));
                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    mv.addObject("allDisputes", this.transactionDisputeService.findByClientProfileAndDisputeStatusOrderByDisputeIdDesc(profile, "ACTIVE"));
                }
                loadCombos(mv);
                return mv;
            case "DEPARTMENT":
                department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));

                mv.addObject("allDisputes", this.transactionDisputeService.findByDepartmentAndDisputeStatusOrderByDisputeIdDesc(department, "ACTIVE"));
                loadCombos(mv);
                return mv;
        }
        //LOG.info("USER ACCOUNT UNKNOWN");
        loadCombos(mv);
        return mv;
    }

    @RequestMapping(value = {"search_transaction"})
    public ModelAndView searchTransactions(@PageableDefault(100) Pageable pageable, RedirectAttributes redir, HttpServletRequest req, HttpSession session, HttpServletResponse response) {
            String departmentMode, departmentId;
        Department department;
        List<Transaction> trans;
        double total;
        Aggregation aggregation;
        AggregationResults<Tollspendings> groupResults;
        List<Tollspendings> expenditureReport;
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "transaction");
        loadCombos(mv);
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }

        int year = Integer.valueOf((String) req.getSession().getAttribute("year_mode"));
        String month = (String) req.getSession().getAttribute("month_mode");
        //LOG.info("DATES USED" + String.valueOf(year) + month);
        this.getTimeStamp();
        mv.setViewName("clientsearchtransactions");
        String startDate = (String) session.getAttribute("transaction_start_date");
        String endDate = (String) session.getAttribute("transaction_end_date");
        Date end_Date = getConvertedDate(endDate);
        LOG.info(end_Date.toString());
        end_Date.setHours(00);
        end_Date.setMinutes(00);
        end_Date.setSeconds(01);
        Date start_Date = getConvertedDate(startDate);
        LOG.info(start_Date.toString());
        start_Date.setHours(00);
        start_Date.setMinutes(00);
        start_Date.setSeconds(01);
        String user_type = (String) session.getAttribute("ns_user");
        mv.addObject("transaction", new TransactionUpload());
        mv.addObject("topic", "SEARCH RESULTS " + startDate + "  ::  " + endDate);
        //LOG.info(startDate);
        //LOG.info(endDate);
        switch (user_type) {
            case "CLIENT":
                departmentMode = (String) session.getAttribute("department_mode");
                departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department1 = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                    Query query = new Query();
                    query.addCriteria(Criteria.where("department").is(department1));
                    query.addCriteria(Criteria.where("postedDate").gte(start_Date).lte(end_Date));
                    query.with(pageable);
                    List<Transaction> finalReport = mongoTemplate.find(query, Transaction.class);
                    long page_count = this.transactionService.countByDepartmentAndPostedDateBetween(department1, start_Date, end_Date);
                    List<Transaction> finalTransactions = new ArrayList<>();
                    finalTransactions.addAll(finalReport);
                    mv.addObject("totalPages", page_count / 100);
                    mv.addObject("size", 100);
                    mv.addObject("allTransactions", finalTransactions);
                    Aggregation aggregation1 = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("department").is(department1).and("transactionDispute").is(false).and("postedDate").gte(start_Date).lte(end_Date)),
                        (AggregationOperation) Aggregation.group(new String[]{"department"}).sum("amount").as("total")});
                    AggregationResults<Tollspendings> aggregationResults = this.mongoTemplate.aggregate(aggregation1, Transaction.class, Tollspendings.class);
                    List<Tollspendings> list1 = aggregationResults.getMappedResults();
                    double d = 0.0;
                    if (list1 != null && !list1.isEmpty()) {
                        Tollspendings toll = list1.get(0);
                        d = toll.getTotal();
                    }
                    mv.addObject("totalAmount", getTwoDecimal(d));
                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    Query query = new Query();
                    query.addCriteria(Criteria.where("clientProfile").is(profile));
                    query.addCriteria(Criteria.where("postedDate").gte(start_Date).lte(end_Date));
                    query.with(pageable);
                    List<Transaction> finalReport = mongoTemplate.find(query, Transaction.class);
                    long page_count = this.transactionService.countByClientProfileAndPostedDateBetween(profile, start_Date, end_Date);
                    List<Transaction> finalTransactions = new ArrayList<>();
                    finalTransactions.addAll(finalReport);
                    mv.addObject("totalPages", page_count / 100);
                    mv.addObject("size", 100);
                    mv.addObject("allTransactions", finalTransactions);
                    double d = 0.0D;
                    Aggregation aggregation1 = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("clientProfile").is(profile).and("transactionDispute").is(false).and("postedDate").gte(start_Date).lte(end_Date)),
                        (AggregationOperation) Aggregation.group(new String[]{"clientProfile"}).sum("amount").as("total")});
                    AggregationResults<Tollspendings> aggregationResults = this.mongoTemplate.aggregate(aggregation1, Transaction.class, Tollspendings.class);
                    List<Tollspendings> list1 = aggregationResults.getMappedResults();
                    if (list1 != null && !list1.isEmpty()) {
                        Tollspendings toll = list1.get(0);
                        d = toll.getTotal();
                    }
                    mv.addObject("totalAmount", getTwoDecimal(d));
                }
                break;
            case "DEPARTMENT":
                department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                Query query = new Query();
                query.addCriteria(Criteria.where("department").is(department));
                query.addCriteria(Criteria.where("postedDate").gte(start_Date).lte(end_Date));
                query.with(pageable);
                List<Transaction> finalReport = mongoTemplate.find(query, Transaction.class);
                long page_count = this.transactionService.countByDepartmentAndPostedDateBetween(department, start_Date, end_Date);
                List<Transaction> finalTransactions = new ArrayList<>();
                finalTransactions.addAll(finalReport);
                mv.addObject("totalPages", page_count / 100);
                mv.addObject("size", 100);
                mv.addObject("allTransactions", finalTransactions);
                total = 0.0D;
                aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("department").is(department).and("transactionDispute").is(false).and("postedDate").gte(start_Date).lte(end_Date)), (AggregationOperation) Aggregation.group(new String[]{"department"}).sum("amount").as("total")});
                groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, Tollspendings.class);
                expenditureReport = groupResults.getMappedResults();
                if (expenditureReport != null && !expenditureReport.isEmpty()) {
                    Tollspendings toll = expenditureReport.get(0);
                    total = toll.getTotal();
                }
                mv.addObject("totalAmount", getTwoDecimal(total));
                break;
        }
        //LOG.info("Transaction Processed" + String.valueOf(year) + month);
        this.getTimeStamp();
        return mv;
    }

    @RequestMapping(path = {"/change_mode/dates/"}, produces = {"application/json; charset=UTF-8"}, method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<?> changeMode(@ModelAttribute("search") Search search, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        String startDate = search.getSearchItem().trim();
        String endDate = search.getAdditionalField().trim();
        session.setAttribute("transaction_start_date", startDate);
        session.setAttribute("transaction_end_date", endDate);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = {"/count/printing/"}, produces = {"application/json; charset=UTF-8"}, method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<?> countPrinting(@ModelAttribute("search") Search search, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        long vehicle_counter = countPrintingInfo(session);
        LOG.info("TRANSACTIONS COUNT :: "+String.valueOf(vehicle_counter));
        if (vehicle_counter == 0) {
            result.setTotal(Double.valueOf(vehicle_counter));
            result.setMessage("No records found with the specified criteria!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);

        }
        if (vehicle_counter > 30000) {
            result.setTotal(Double.valueOf(vehicle_counter));
            result.setMessage("Please select records less than 30,000 using the date range, records found are : " + String.valueOf(vehicle_counter));
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            result.setTotal(Double.valueOf(vehicle_counter));
            result.setMessage("successful records found : " + String.valueOf(vehicle_counter));
            result.setTitle("success");
            return ResponseEntity.ok(result);
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
        this.getTimeStamp();
        String startDate = (String) session.getAttribute("transaction_start_date");
        String endDate = (String) session.getAttribute("transaction_end_date");
        if (startDate == null || endDate == null) {
            int year = Integer.valueOf((String) session.getAttribute("year_mode"));
            String month = (String) session.getAttribute("month_mode");
            session.setAttribute("transaction_start_date", null);
            session.setAttribute("transaction_end_date", null);
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
        } else {
            Date end_Date = getConvertedDate(endDate);
            Date start_Date = getConvertedDate(startDate);
            String user_type = (String) session.getAttribute("ns_user");
            switch (user_type) {
                case "CLIENT":
                    departmentMode = (String) session.getAttribute("department_mode");
                    departmentId = (String) session.getAttribute("department_id");
                    if (departmentMode != null && departmentMode.equals("YES")) {
                        Department department1 = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                        transactions = this.transactionService.countByDepartmentAndPostedDateBetween(department1, start_Date, end_Date);
                    } else {
                        profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                        transactions = this.transactionService.countByClientProfileAndPostedDateBetween(profile, start_Date, end_Date);
                    }
                    return transactions;
                case "DEPARTMENT":
                    department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                    transactions = this.transactionService.countByDepartmentAndPostedDateBetween(department, start_Date, end_Date);
                    return transactions;
            }
        }
        this.getTimeStamp();
        return transactions;
    }

    @RequestMapping(path = {"/load/printed/info/"}, produces = {"application/json; charset=UTF-8"}, method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<?> printedInfo(HttpSession session) {
        ClientProfile profile;
        String departmentMode, departmentId;
        Department department;
        List<Transaction> transactions = null;
        double total;
        Aggregation aggregation;
        AggregationResults<Tollspendings> groupResults;
        List<Tollspendings> expenditureReport;
        JobResponse result = new JobResponse();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        //LOG.info("PRINT DATES USED");
        this.getTimeStamp();
        String startDate = (String) session.getAttribute("transaction_start_date");
        String endDate = (String) session.getAttribute("transaction_end_date");
        if (startDate == null || endDate == null) {
            int year = Integer.valueOf((String) session.getAttribute("year_mode"));
            String month = (String) session.getAttribute("month_mode");
            session.setAttribute("transaction_start_date", null);
            session.setAttribute("transaction_end_date", null);
            String user_type = (String) session.getAttribute("ns_user");
            switch (user_type) {
                case "CLIENT":
                    departmentMode = (String) session.getAttribute("department_mode");
                    departmentId = (String) session.getAttribute("department_id");
                    if (departmentMode != null && departmentMode.equals("YES")) {
                        Department department1 = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                        transactions = this.transactionService.findByDepartmentAndPostedMonthAndPostedYearOrderByTransactionIdDesc(department1, month, year);
                        double d = 0.0D;
                        Aggregation aggregation1 = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("department").is(department1).and("transactionDispute").is(false).and("transactionMonth").is(month).and("transactionYear").is(year)),
                            (AggregationOperation) Aggregation.group(new String[]{"department"}).sum("amount").as("total")});
                        AggregationResults<Tollspendings> aggregationResults = this.mongoTemplate.aggregate(aggregation1, Transaction.class, Tollspendings.class);
                        List<Tollspendings> list = aggregationResults.getMappedResults();
                        if (list != null && !list.isEmpty()) {
                            Tollspendings toll = list.get(0);
                            d = toll.getTotal();
                        }
                    } else {
                        getTimeStamp();
                        profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                        transactions = this.transactionService.findByClientProfileAndPostedMonthAndPostedYearOrderByTransactionIdDesc(profile, month, year);
                        double d = 0.0D;
                        getTimeStamp();
                        Aggregation aggregation1 = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("clientProfile").is(profile).and("transactionDispute").is(false).and("transactionMonth").is(month).and("transactionYear").is(year)),
                            (AggregationOperation) Aggregation.group(new String[]{"clientProfile"}).sum("amount").as("total")});
                        AggregationResults<Tollspendings> aggregationResults = this.mongoTemplate.aggregate(aggregation1, Transaction.class, Tollspendings.class);
                        List<Tollspendings> list = aggregationResults.getMappedResults();
                        if (list != null && !list.isEmpty()) {
                            Tollspendings toll = list.get(0);
                            d = toll.getTotal();
                        }
                    }
                    break;
                case "DEPARTMENT":
                    department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                    transactions = this.transactionService.findByDepartmentAndPostedMonthAndPostedYearOrderByTransactionIdDesc(department, month, year);
                    total = 0.0D;
                    aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("department").is(department).and("transactionDispute").is(false).and("transactionMonth").is(month).and("transactionYear").is(year)), (AggregationOperation) Aggregation.group(new String[]{"department"}).sum("amount").as("total")});
                    groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, Tollspendings.class);
                    expenditureReport = groupResults.getMappedResults();
                    if (expenditureReport != null && !expenditureReport.isEmpty()) {
                        Tollspendings toll = expenditureReport.get(0);
                        total = toll.getTotal();
                    }
                    result.setTotal(total);
                    break;
            }
        } else {
            Date end_Date = getConvertedDate(endDate);
            Date start_Date = getConvertedDate(startDate);
            String user_type = (String) session.getAttribute("ns_user");
            switch (user_type) {
                case "CLIENT":
                    profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    departmentMode = (String) session.getAttribute("department_mode");
                    departmentId = (String) session.getAttribute("department_id");
                    if (departmentMode != null && departmentMode.equals("YES")) {
                        Department department1 = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                        List<Transaction> list = getDepartmentTransactionRange(department1, start_Date, end_Date);
                        if (list == null || list.isEmpty()) {
                            result.setMessage("No transactions uploaded on " + String.valueOf(department1.getDepartmentName()));
                            result.setTitle("fail");
                            return ResponseEntity.ok(result);
                        }
                        result.setTitle("success");
                        //LOG.info("transactions range data found");
                        result.setMessage("Transactions data found");
                        result.setResults(list);
                        double d = 0.0D;
                        Aggregation aggregation1 = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("department").is(department1).and("transactionDispute").is(Boolean.valueOf(false)).and("transactionDate").gte(startDate).lte(endDate)),
                            (AggregationOperation) Aggregation.group(new String[]{"clientProfile"}).sum("amount").as("total")});
                        AggregationResults<Tollspendings> aggregationResults = this.mongoTemplate.aggregate(aggregation1, Transaction.class, Tollspendings.class);
                        List<Tollspendings> list1 = aggregationResults.getMappedResults();
                        if (list1 != null && !list1.isEmpty()) {
                            Tollspendings toll = list1.get(0);
                            d = toll.getTotal();
                        }
                        result.setTotal(d);
                        //LOG.info("transactions fetched successfully");
                    } else {
                        List<Transaction> list = getCLientProfileTransactionRange(profile, start_Date, end_Date);
                        if (list == null || list.isEmpty()) {
                            result.setMessage("No transactions uploaded on " + String.valueOf(profile.getCompanyName()));
                            result.setTitle("fail");
                            return ResponseEntity.ok(result);
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
                        result.setTotal(d);
                        //LOG.info("transactions range data found");
                        result.setMessage("Transactions data found");
                        result.setResults(list);
                    }
                    //LOG.info("returning search results");
                    return ResponseEntity.ok(result);
                case "DEPARTMENT":
                    department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                    transactions = getDepartmentTransactionRange(department, start_Date, end_Date);
                    if (transactions == null || transactions.isEmpty()) {
                        result.setMessage("No transactions uploaded on " + String.valueOf(department.getDepartmentName()));
                        result.setTitle("fail");
                        return ResponseEntity.ok(result);
                    }
                    result.setTitle("success");
                    //LOG.info("transactions range data found");
                    result.setMessage("Transactions data found");
                    total = 0.0D;
                    aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("department").is(department).and("transactionDispute").is(false).and("transactionDate").gte(startDate).lte(endDate)), (AggregationOperation) Aggregation.group(new String[]{"clientProfile"}).sum("amount").as("total")});
                    groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, Tollspendings.class);
                    expenditureReport = groupResults.getMappedResults();
                    if (expenditureReport != null && !expenditureReport.isEmpty()) {
                        Tollspendings toll = expenditureReport.get(0);
                        total = toll.getTotal();
                    }
                    result.setTotal(total);
                    result.setResults(transactions);
                    //LOG.info("returning search results");
                    return ResponseEntity.ok(result);
            }
        }
        result.setMessage("TRANSACTION LIST");
        result.setResults(transactions);
        //LOG.info("returning search results".toUpperCase());
        this.getTimeStamp();
        return ResponseEntity.ok(result);
    }

    private List<Transaction> getCLientProfileTransactionRange(ClientProfile profile, Date startDate, Date endDate) {
        Query query = new Query();
        query.addCriteria((CriteriaDefinition) Criteria.where("clientProfile").is(profile));
        query.addCriteria((CriteriaDefinition) Criteria.where("postedDate").gte(startDate).lte(endDate));
        List<Transaction> transactions = this.mongoTemplate.find(query, Transaction.class);
        return transactions;
    }

    private List<Transaction> getDepartmentTransactionRange(Department department, Date startDate, Date endDate) {
        Query query = new Query();
        query.addCriteria((CriteriaDefinition) Criteria.where("department").is(department));
        query.addCriteria((CriteriaDefinition) Criteria.where("postedDate").gte(startDate).lte(endDate));
        List<Transaction> transactions = this.mongoTemplate.find(query, Transaction.class);
        return transactions;
    }

    @RequestMapping(path = {"/post/dispute/{transactionId}/{dispute}"}, produces = {"application/json; charset=UTF-8"}, method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<?> postDisputes(HttpSession session, @PathVariable("transactionId") Long transactionId, @PathVariable("dispute") String dispute) {
        JobResponse result = new JobResponse();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        Transaction transaction = this.transactionService.findByTransactionId(transactionId);
        if (transaction == null) {
            result.setTitle("fail");
            //LOG.info("invalid transaction");
            result.setMessage("Failed , invalid transaction!");
            return ResponseEntity.ok(result);
        }
        TransactionDispute transactionDispute = this.transactionDisputeService.findByTransaction(transaction);
        if (transactionDispute == null) {
            transactionDispute = new TransactionDispute();
            transactionDispute.setDisputeId(this.seqGeneratorService.generateSequence("transaction_dispute_sequence"));
            transactionDispute.setTransaction(transaction);
            transactionDispute.setDisputeStatus("ACTIVE");
            transactionDispute.setDispute(dispute);
            transactionDispute.setClientProfile(transaction.getClientProfile());
            transactionDispute.setDepartment(transaction.getDepartment());
            transactionDispute.setStatus(true);
            transactionDispute.setUpdatedDate(getCurrentDate());
            transactionDispute.setCreationDate(getCurrentDate());
            transactionDispute.setCreationTime(getCurrentTimestamp());
            transactionDispute.setUpdatedTime(getCurrentTimestamp());
            transaction.setTransactionDispute(true);
            this.transactionDisputeService.save(transactionDispute);
            this.transactionService.save(transaction);
            //LOG.info("transaction dispute submitted successfully");
            result.setTitle("success");
            result.setMessage("Transaction dispute submitted successfully");
        } else {
            result.setTitle("fail");
            //LOG.info("There is an existing dispute related to this transaction");
            result.setMessage("Failed , The dispute already exists!");
        }
        return ResponseEntity.ok(result);
    }

    void loadCombos(ModelAndView mv) {
        List<ClientProfile> clientList = this.clientProfileService.findAll();
        mv.addObject("clientList", clientList);
        mv.addObject("clientProfile", new ClientProfile());
    }

    JasperPrint printTransactions(List<Transaction> transactionList) {
        try {
            JRBeanCollectionDataSource transactionDataSource = new JRBeanCollectionDataSource(transactionList);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("TransactionDataSource", transactionDataSource);
            ClassPathResource fStream = new ClassPathResource("reportsDesign/transactions.jrxml");
            InputStream inputStream = fStream.getInputStream();
            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, (JRDataSource) new JREmptyDataSource());
            return jasperPrint;
        } catch (IOException | net.sf.jasperreports.engine.JRException ex) {
            //LOG.info(ex.toString().toUpperCase());
            return null;
        }
    }

    public List<Transaction> loadPrintingInfo(HttpSession session) {
        ClientProfile profile;
        String departmentMode, departmentId;
        Department department;
        List<Transaction> transactions = null;
        double total;
        Aggregation aggregation;
        AggregationResults<Tollspendings> groupResults;
        List<Tollspendings> expenditureReport;
        JobResponse result = new JobResponse();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return null;
        }
        //LOG.info("PRINT DATES USED");
        this.getTimeStamp();
        String startDate = (String) session.getAttribute("transaction_start_date");
        String endDate = (String) session.getAttribute("transaction_end_date");
        if (startDate == null || endDate == null) {
            int year = Integer.valueOf((String) session.getAttribute("year_mode"));
            String month = (String) session.getAttribute("month_mode");
            session.setAttribute("transaction_start_date", null);
            session.setAttribute("transaction_end_date", null);
            String user_type = (String) session.getAttribute("ns_user");
            switch (user_type) {
                case "CLIENT":
                    departmentMode = (String) session.getAttribute("department_mode");
                    departmentId = (String) session.getAttribute("department_id");
                    if (departmentMode != null && departmentMode.equals("YES")) {
                        Department department1 = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                        transactions = this.transactionService.findByDepartmentAndPostedMonthAndPostedYearOrderByTransactionIdDesc(department1, month, year);
                        double d = 0.0D;
                        Aggregation aggregation1 = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("department").is(department1).and("transactionDispute").is(false).and("transactionMonth").is(month).and("transactionYear").is(year)),
                            (AggregationOperation) Aggregation.group(new String[]{"department"}).sum("amount").as("total")});
                        AggregationResults<Tollspendings> aggregationResults = this.mongoTemplate.aggregate(aggregation1, Transaction.class, Tollspendings.class);
                        List<Tollspendings> list = aggregationResults.getMappedResults();
                        if (list != null && !list.isEmpty()) {
                            Tollspendings toll = list.get(0);
                            d = toll.getTotal();
                        }
                    } else {
                        getTimeStamp();
                        profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                        transactions = this.transactionService.findByClientProfileAndPostedMonthAndPostedYearOrderByTransactionIdDesc(profile, month, year);
                        double d = 0.0D;
                        /*         
                        Pageable pageable = PageRequest.of(0,1000);
                        Query query = new Query();
                        query.addCriteria(Criteria.where("clientProfile").is(profile));
                        query.addCriteria(Criteria.where("postedMonth").is(month));
                        query.addCriteria(Criteria.where("postedYear").is(year));
                        query.with(pageable);
                        transactions = mongoTemplate.find(query, Transaction.class);
                         */
                        getTimeStamp();
                        Aggregation aggregation1 = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("clientProfile").is(profile).and("transactionDispute").is(false).and("transactionMonth").is(month).and("transactionYear").is(year)),
                            (AggregationOperation) Aggregation.group(new String[]{"clientProfile"}).sum("amount").as("total")});
                        AggregationResults<Tollspendings> aggregationResults = this.mongoTemplate.aggregate(aggregation1, Transaction.class, Tollspendings.class);
                        List<Tollspendings> list = aggregationResults.getMappedResults();
                        if (list != null && !list.isEmpty()) {
                            Tollspendings toll = list.get(0);
                            d = toll.getTotal();
                        }
                    }
                    break;
                case "DEPARTMENT":
                    department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                    transactions = this.transactionService.findByDepartmentAndPostedMonthAndPostedYearOrderByTransactionIdDesc(department, month, year);
                    total = 0.0D;
                    aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("department").is(department).and("transactionDispute").is(false).and("transactionMonth").is(month).and("transactionYear").is(year)), (AggregationOperation) Aggregation.group(new String[]{"department"}).sum("amount").as("total")});
                    groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, Tollspendings.class);
                    expenditureReport = groupResults.getMappedResults();
                    if (expenditureReport != null && !expenditureReport.isEmpty()) {
                        Tollspendings toll = expenditureReport.get(0);
                        total = toll.getTotal();
                    }

                    break;
            }
        } else {
            Date end_Date = getConvertedDate(endDate);
            Date start_Date = getConvertedDate(startDate);
            String user_type = (String) session.getAttribute("ns_user");
            switch (user_type) {
                case "CLIENT":
                    profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    departmentMode = (String) session.getAttribute("department_mode");
                    departmentId = (String) session.getAttribute("department_id");
                    if (departmentMode != null && departmentMode.equals("YES")) {
                        Department department1 = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                        transactions = getDepartmentTransactionRange(department1, start_Date, end_Date);
                        if (transactions == null || transactions.isEmpty()) {
                            result.setMessage("No transactions uploaded on " + String.valueOf(department1.getDepartmentName()));
                            result.setTitle("fail");
                            return transactions;
                        }
                        result.setTitle("success");
                        //LOG.info("transactions range data found");
                        result.setMessage("Transactions data found");
                        result.setResults(transactions);
                        double d = 0.0D;
                        Aggregation aggregation1 = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("department").is(department1).and("transactionDispute").is(Boolean.valueOf(false)).and("transactionDate").gte(startDate).lte(endDate)),
                            (AggregationOperation) Aggregation.group(new String[]{"clientProfile"}).sum("amount").as("total")});
                        AggregationResults<Tollspendings> aggregationResults = this.mongoTemplate.aggregate(aggregation1, Transaction.class, Tollspendings.class);
                        List<Tollspendings> list1 = aggregationResults.getMappedResults();
                        if (list1 != null && !list1.isEmpty()) {
                            Tollspendings toll = list1.get(0);
                            d = toll.getTotal();
                        }
                        result.setTotal(d);
                        //LOG.info("transactions fetched successfully");
                    } else {
                        transactions = getCLientProfileTransactionRange(profile, start_Date, end_Date);
                        if (transactions == null || transactions.isEmpty()) {
                            return transactions;
                        }
                        double d = 0.0D;
                        Aggregation aggregation1 = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("clientProfile").is(profile).and("transactionDispute").is(false).and("transactionDate").gte(startDate).lte(endDate)),
                            (AggregationOperation) Aggregation.group(new String[]{"clientProfile"}).sum("amount").as("total")});
                        AggregationResults<Tollspendings> aggregationResults = this.mongoTemplate.aggregate(aggregation1, Transaction.class, Tollspendings.class);
                        List<Tollspendings> list1 = aggregationResults.getMappedResults();
                        if (list1 != null && !list1.isEmpty()) {
                            Tollspendings toll = list1.get(0);
                            d = toll.getTotal();
                        }
                    }
                    //LOG.info("returning search results");
                    return transactions;
                case "DEPARTMENT":
                    department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                    transactions = getDepartmentTransactionRange(department, start_Date, end_Date);
                    if (transactions == null || transactions.isEmpty()) {
                        return transactions;
                    }
                    result.setTitle("success");
                    //LOG.info("transactions range data found");
                    result.setMessage("Transactions data found");
                    total = 0.0D;
                    aggregation = Aggregation.newAggregation(new AggregationOperation[]{(AggregationOperation) Aggregation.match(Criteria.where("department").is(department).and("transactionDispute").is(false).and("transactionDate").gte(startDate).lte(endDate)), (AggregationOperation) Aggregation.group(new String[]{"clientProfile"}).sum("amount").as("total")});
                    groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, Tollspendings.class);
                    expenditureReport = groupResults.getMappedResults();
                    if (expenditureReport != null && !expenditureReport.isEmpty()) {
                        Tollspendings toll = expenditureReport.get(0);
                        total = toll.getTotal();
                    }
                    //LOG.info("returning search results");
                    return transactions;
            }
        }
        result.setMessage("TRANSACTION LIST");
        result.setResults(transactions);
        //LOG.info("returning search results".toUpperCase());
        this.getTimeStamp();
        return transactions;
    }

    @GetMapping(value = {"/transactionPdf"}, produces = {"application/pdf"})
    public ResponseEntity<InputStreamResource> generatePDFReport(HttpSession session) {
        String departmentMode, departmentId;
        ClientProfile profile;
        Department department;
        List<Transaction> transactionList = new ArrayList<>();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            return null;
        }
        transactionList = loadPrintingInfo(session);
        //LOG.info("Detailed transaction for generating report");
        try {
            if (printTransactions(transactionList) != null) {
                ByteArrayResource byteArrayResource = this.reportService.generateDataSourceReport(transactionList, ExportReportType.PDF, printTransactions(transactionList));
                ByteArrayInputStream bis = (ByteArrayInputStream) byteArrayResource.getInputStream();
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Disposition", "inline; filename=transactions.pdf");
                return ((ResponseEntity.BodyBuilder) ResponseEntity.ok().headers(headers)).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bis));
            }
            //LOG.info("NO DATA WAS PROCESSED");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (IOException ex) {
            //LOG.error(ex.toString());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping({"/excel/transactions.xlsx"})
    public void generateExcelReport(HttpServletResponse response, HttpSession session) throws IOException {
        String departmentMode, departmentId;
        ClientProfile profile;
        Department department;
        List<Transaction> transactionList = new ArrayList<>();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            return;
        }
        transactionList = loadPrintingInfo(session);
        //LOG.info("Detailed transaction for generating report with data source");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=transactions.xlsx");
        ByteArrayInputStream stream = ExcelTransactionExporter.exportListToExcelFile(transactionList);
        IOUtils.copy(stream, (OutputStream) response.getOutputStream());
    }

    @GetMapping(value = {"/search/transactionPdf"}, produces = {"application/pdf"})
    public ResponseEntity<InputStreamResource> generateSearchPDFReport(HttpSession session) {
        List<Transaction> transactionList = new ArrayList<>();
        //LOG.info("***** PRINTING PDF ******");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            return null;
        }
        transactionList = loadPrintingInfo(session);
        //LOG.info("Detailed transaction for generating report with data source: {}");
        try {
            if (printTransactions(transactionList) != null) {
                ByteArrayResource byteArrayResource = this.reportService.generateDataSourceReport(transactionList, ExportReportType.PDF, printTransactions(transactionList));
                ByteArrayInputStream bis = (ByteArrayInputStream) byteArrayResource.getInputStream();
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Disposition", "inline; filename=transactions.pdf");
                return ((ResponseEntity.BodyBuilder) ResponseEntity.ok().headers(headers)).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bis));
            }
            //LOG.info("NO DATA WAS PROCESSED");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (IOException ex) {
            //LOG.error(ex.toString());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping({"/excel/search/transactions.xlsx"})
    public void generateSearchExcelReport(HttpServletResponse response, HttpSession session) throws IOException {
        List<Transaction> transactionList = new ArrayList<>();
        //LOG.info("***** PRINTING PDF ******");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            return;
        }
        transactionList = loadPrintingInfo(session);
        //LOG.info("Detailed transaction for generating report with data source: {}");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=transactions.xlsx");
        ByteArrayInputStream stream = ExcelTransactionExporter.exportListToExcelFile(transactionList);
        IOUtils.copy(stream, (OutputStream) response.getOutputStream());
    }

    @RequestMapping(path = {"/close/dispute/{disputeId}"}, produces = {"application/json; charset=UTF-8"}, method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<?> closeDisputes(HttpSession session, @PathVariable("disputeId") Long disputeId) {
        String departmentMode, departmentId;
        Department department;
        JobResponse result = new JobResponse();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        TransactionDispute transactionDispute = this.transactionDisputeService.findByDisputeId(disputeId);
        if (transactionDispute == null) {
            result.setMessage("Invalid dispute, please check and try again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                departmentMode = (String) session.getAttribute("department_mode");
                departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department1 = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                    if (department1.getDepartmentName().equals(transactionDispute.getDepartment().getDepartmentName())) {
                        Transaction transaction = transactionDispute.getTransaction();
                        if (transaction != null) {
                            transaction.setTransactionDispute(false);
                            this.transactionService.save(transaction);
                        }
                        this.transactionDisputeService.delete(transactionDispute);
                        result.setTitle("success");
                        result.setMessage("Dispute closed successfully!");
                    } else {
                        result.setMessage("You can only cancel your disputes!");
                        result.setTitle("fail");
                    }
                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    if (profile.getCompanyName().equals(transactionDispute.getClientProfile().getCompanyName())) {
                        Transaction transaction = transactionDispute.getTransaction();
                        if (transaction != null) {
                            transaction.setTransactionDispute(false);
                            this.transactionService.save(transaction);
                        }
                        this.transactionDisputeService.delete(transactionDispute);
                        result.setTitle("success");
                        result.setMessage("Dispute closed successfully!");
                    } else {
                        result.setMessage("You can only cancel your disputes!");
                        result.setTitle("fail");
                    }
                }
                return ResponseEntity.ok(result);
            case "DEPARTMENT":
                department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                if (department.getDepartmentName().equals(transactionDispute.getDepartment().getDepartmentName())) {
                    Transaction transaction = transactionDispute.getTransaction();
                    if (transaction != null) {
                        transaction.setTransactionDispute(false);
                        this.transactionService.save(transaction);
                    }
                    this.transactionDisputeService.delete(transactionDispute);
                    result.setTitle("success");
                    result.setMessage("Dispute closed successfully!");
                    break;
                }
                result.setMessage("You can only cancel your disputes!");
                result.setTitle("fail");
                break;
        }
        //LOG.info("USER ACCOUNT UNKNOWN");
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = {"/load/disputes"}, produces = {"application/json; charset=UTF-8"}, method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<?> loadDisputes(HttpSession session) {
        ClientProfile profile;
        String departmentMode, departmentId;
        Department department;
        List<TransactionDispute> transactions;
        JobResponse result = new JobResponse();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                departmentMode = (String) session.getAttribute("department_mode");
                departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department1 = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                    List<TransactionDispute> list = this.transactionDisputeService.findByDepartmentAndDisputeStatusOrderByDisputeIdDesc(department1, "ACTIVE");
                    if (list == null || list.isEmpty()) {
                        result.setMessage("No disputes found!");
                        result.setTitle("fail");
                        return ResponseEntity.ok(result);
                    }
                    result.setTitle("success");
                    result.setMessage("Disputes found!");
                    result.setResults(list);
                } else {
                    List<TransactionDispute> list = this.transactionDisputeService.findByClientProfileAndDisputeStatusOrderByDisputeIdDesc(profile, "ACTIVE");
                    if (list == null || list.isEmpty()) {
                        result.setMessage("No disputes found!");
                        result.setTitle("fail");
                        return ResponseEntity.ok(result);
                    }
                    result.setTitle("success");
                    result.setMessage("Disputes found!");
                    result.setResults(list);
                }
                //LOG.info("returning search results");
                return ResponseEntity.ok(result);
            case "DEPARTMENT":
                department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                transactions = this.transactionDisputeService.findByDepartmentAndDisputeStatusOrderByDisputeIdDesc(department, "ACTIVE");
                if (transactions == null || transactions.isEmpty()) {
                    result.setMessage("No disputes found!");
                    result.setTitle("fail");
                    return ResponseEntity.ok(result);
                }
                result.setTitle("success");
                result.setMessage("Disputes found!");
                result.setResults(transactions);
                //LOG.info("returning search results");
                return ResponseEntity.ok(result);
        }
        result.setMessage("USER ACCOUNT UNKNOWN");
        //LOG.info("USER ACCOUNT UNKNOWN");
        //LOG.info("returning search results");
        return ResponseEntity.ok(result);
    }

}
