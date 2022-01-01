package com.crm.controller.admin;

import com.crm.enums.ExportReportType;
import com.crm.generic.ExcelTransactionExporter;
import com.crm.generic.Nospaniol;
import com.crm.model.Account;
import com.crm.model.Agency;
import com.crm.model.Cancel;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.DepartmentAccount;
import com.crm.model.Transaction;
import com.crm.model.JobResponse;
import com.crm.model.State;
import com.crm.model.Tollspendings;
import com.crm.model.TransactionDispute;
import com.crm.model.TransactionFile;
import com.crm.model.TransactionUpload;
import com.crm.model.Vehicle;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("transaction")
public class TransactionController extends Nospaniol {

    @RequestMapping(value = {"delete_batch"}, method = RequestMethod.GET)
    public ModelAndView deleteBat(RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
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
        mv.addObject("transaction", new TransactionUpload());
        mv.setViewName("transactiondelete");
        loadCombos(mv);
        return mv;
    }

    @RequestMapping(value = "delete/{batch}", method = RequestMethod.POST)
    public ModelAndView deleteProces(@ModelAttribute("transaction") TransactionUpload p, MultipartFile file, RedirectAttributes redir, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        int counter = 0;
        int header = 0;
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        mv.setViewName("redirect:/transaction/delete_batch");
        loadCombos(mv);
        if (p.getClient().isEmpty() || p.getDateType().isEmpty() || p.getTransactionDate().isEmpty()) {
            redir.addFlashAttribute("messageType", "fail");
            redir.addFlashAttribute("message", "Fill all required fields!");
            redir.addFlashAttribute("transaction", p);
            return mv;
        }
        int year = Integer.valueOf(p.getTransactionDate());
        String month = p.getDateType();
        ClientProfile profile = this.clientProfileService.findByCompanyName(p.getClient());
        if (profile == null) {
            redir.addFlashAttribute("messageType", "fail");
            redir.addFlashAttribute("message", "Client : '" + p.getClient() + "' not found in the system");
            redir.addFlashAttribute("transaction", p);
            return mv;
        }
        List<Transaction> transactions = this.transactionService.findByClientProfileAndPostedMonthAndPostedYearOrderByTransactionIdDesc(profile, month, year);
        transactionService.deleteAll(transactions);
        LOG.info("Processed batch upload successfully ".toUpperCase());
        redir.addFlashAttribute("messageType", "success");
        redir.addFlashAttribute("message", "Successfully updated");
        return mv;
    }

    @RequestMapping(value = {"view_file"}, method = RequestMethod.GET)
    public ModelAndView viewFile(@PageableDefault(value = 50) Pageable pageable, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
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
        loadCombos(mv);
        mv.addObject("transaction", new TransactionUpload());
        mv.addObject("institution", institutionName());
        Page<TransactionFile> pages = this.transactionFileService.findAllByOrderByTransactionFileIdDesc(pageable);
        mv.addObject("number", pages.getNumber());
        mv.addObject("totalPages", pages.getTotalPages());
        mv.addObject("totalElements", pages.getTotalElements());
        mv.addObject("size", pages.getSize());
        mv.addObject("data", pages.getContent());
        mv.setViewName("transactionuploads");
        return mv;
    }

    @RequestMapping("/download/upload/{id}")
    public void downloadUpload(@PathVariable("id") Long id, HttpServletResponse response, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        try {
            String ns_mail = (String) session.getAttribute("ns_mail_address");
            if (ns_mail == null || ns_mail.isEmpty()) {
                return;
            }
            String logged_user = (String) session.getAttribute("ns_user");
            if (logged_user == null || logged_user.equals("CLIENT")) {
                return;
            }
            TransactionFile download = this.transactionFileService.findByTransactionFileId(id);
            if (download != null) {
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename=transactionupload.xlsx");
                if (download.getTransactions() == null) {
                    LOG.info("This file does not exist for file id :: ".toUpperCase() + download.getTransactionName());
                } else {
                    LOG.info("This file found for file id :: ".toUpperCase() + download.getTransactionName());
                    ByteArrayInputStream stream = new ByteArrayInputStream(download.getTransactions().getData());
                    IOUtils.copy(stream, response.getOutputStream());
                }
            } else {
                LOG.info("This file does not exist with id:: ".toUpperCase() + String.valueOf(id));
                return;
            }
        } catch (IOException ex) {
            LOG.error(ex.toString());
            return;
        }
    }

    @RequestMapping(value = {"view_today_transaction"}, method = RequestMethod.GET)
    public ModelAndView viewTodaysTransactions(@PageableDefault(value = 100) Pageable pageable, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
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
        loadCombos(mv);
        LocalDate todate = LocalDate.now();
        mv.addObject("transactionDate", todate);
        mv.addObject("transaction", new TransactionUpload());
        mv.addObject("institution", institutionName());
        mv.addObject("topic", String.valueOf(this.getCurrentDate() + "'s Transactions"));
        List<Transaction> pages = this.transactionService.findByPostedDate(this.getCurrentDate());
        mv.addObject("allTransactions", pages);
        double total = 0.00;
        Aggregation aggregation = newAggregation(
                match(Criteria.where("transactionDispute").is(false)),
                group().sum("amount").as("total")
        );
        AggregationResults<Tollspendings> groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspendings.class);
        List<Tollspendings> expenditureReport = groupResults.getMappedResults();
        if (expenditureReport == null || expenditureReport.isEmpty()) {
        } else {
            Tollspendings toll = expenditureReport.get(0);
            total = toll.getTotal();
        }
        mv.addObject("totalAmount", this.getTwoDecimal(total));
        mv.setViewName("todaytransactions");
        return mv;
    }

    @RequestMapping(value = {"view_date_transaction"}, method = RequestMethod.POST)
    public ModelAndView viewDateTransactions(@ModelAttribute("transaction") TransactionUpload p, @PageableDefault(value = 10) Pageable pageable, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
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
        loadCombos(mv);
        mv.addObject("institution", institutionName());
        Date searchDate = getConvertedDate(session, p.getTransactionDate());
        LocalDate todate = LocalDate.parse(p.getTransactionDate());
        mv.addObject("transactionDate", todate);
        mv.addObject("topic", String.valueOf(searchDate + "'s Transactions"));
        Page<Transaction> pages = this.transactionService.findByPostedDate(pageable, searchDate);
        mv.addObject("number", pages.getNumber());
        mv.addObject("totalPages", pages.getTotalPages());
        mv.addObject("totalElements", pages.getTotalElements());
        mv.addObject("size", pages.getSize());
        mv.addObject("data", pages.getContent());
        double total = 0.00;
        Aggregation aggregation = newAggregation(
                match(Criteria.where("postedDate").is(searchDate).and("transactionDispute").is(false)),
                group("postedDate").sum("amount").as("total")
        );
        AggregationResults<Tollspendings> groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspendings.class);
        List<Tollspendings> expenditureReport = groupResults.getMappedResults();
        if (expenditureReport == null || expenditureReport.isEmpty()) {
        } else {
            Tollspendings toll = expenditureReport.get(0);
            total = toll.getTotal();
        }
        mv.addObject("totalAmount", this.getTwoDecimal(total));
        LOG.info("Fetching  transactions ".toUpperCase() + String.valueOf(searchDate));
        mv.addObject("transaction", new TransactionUpload());
        mv.setViewName("todaytransactions");
        return mv;
    }

    @RequestMapping(value = {"view_transaction"}, method = RequestMethod.GET)
    public ModelAndView viewTransactions(@PageableDefault(value = 100) Pageable pageable, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        loadCombos(mv);
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
        session.setAttribute("transaction_start_date", "NONE");
        session.setAttribute("transaction_end_date", "NONE");
        mv.addObject("institution", this.institutionName());
        Page<Transaction> pages = this.transactionService.findAllOrderByTransactionIdDesc(pageable);
        mv.addObject("number", pages.getNumber());
        mv.addObject("totalPages", pages.getTotalPages());
        mv.addObject("totalElements", pages.getTotalElements());
        mv.addObject("size", pages.getSize());
        List<Transaction> trans = pages.getContent();
        double total = 0.00;
        Aggregation aggregation = newAggregation(
                group().sum("amount").as("total")
        );

        AggregationResults<Tollspendings> groupResults = mongoTemplate.aggregate(aggregation, Transaction.class, Tollspendings.class);
        List<Tollspendings> expenditureReport = groupResults.getMappedResults();
        if (expenditureReport == null || expenditureReport.isEmpty()) {
        } else {
            Tollspendings toll = expenditureReport.get(0);
            total = toll.getTotal();
        }
        mv.addObject("totalAmount", this.getTwoDecimal(total));
        mv.addObject("allTransactions", trans);
        mv.addObject("transaction", new TransactionUpload());
        mv.setViewName("viewtransactions");
        return mv;
    }

    @RequestMapping(value = {"active_disputes"}, method = RequestMethod.GET)
    public ModelAndView viewDisputes(@PageableDefault(value = 100) Pageable pageable, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
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
        loadCombos(mv);
        mv.addObject("transaction", new TransactionUpload());
        mv.addObject("institution", institutionName());
        mv.addObject("topic", "Recently added transactions");
        mv.addObject("allDisputes", this.transactionDisputeService.findByDisputeStatusOrderByDisputeIdDesc("ACTIVE"));
        mv.setViewName("viewtransactiondisputes");
        return mv;
    }

    @RequestMapping(value = {"add_transaction"}, method = RequestMethod.GET)
    public ModelAndView addTransaction(RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
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
        mv.addObject("transaction", new TransactionUpload());
        mv.setViewName("transactionupload");
        loadCombos(mv);
        return mv;
    }

    void loadCombos(ModelAndView mv) {
        mv.addObject("institution", institutionName());
        List<ClientProfile> clientList = this.clientProfileService.findAll();
        mv.addObject("clientList", clientList);
        mv.addObject("clientProfile", new ClientProfile());
    }

    Double sum = 0.00;
    int inv_count = 0;

    List<String> missingPlates(List<Transaction> transactions) {
        List<String> plates = new ArrayList<>();
        /*for (Transaction transaction : transactions) {
            if (!transaction.isEmpty()) {
                String value = transaction.getPlate();
                if (value != null && !value.isEmpty()) {
                    Vehicle vehicle = this.vehicleService.findByLicensePlate(value);
                    if (vehicle == null) {
                        if (!plates.contains(value)) {
                            LOG.info("MISSING PLATES :: " + value);
                            plates.add(value);
                        }
                    } else {
                        LOG.info("FOUND MISSING PLATES :: " + value);
                    }
                }
            }
        }
         */
        return plates;
    }

    @RequestMapping(value = "upload/file/{save}", method = RequestMethod.POST)
    public ModelAndView uploadVeh(@ModelAttribute("transaction") TransactionUpload p, MultipartFile file, RedirectAttributes redir, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        int counter = 0;
        int header = 0;
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        mv.setViewName("redirect:/transaction/add_transaction");
        session.setAttribute("date_format", p.getDateType());
        LOG.info("*******************" + p.getDateType());
        loadCombos(mv);
        List<Transaction> transactions = new ArrayList<>();
        try {
            if (p.getClient().isEmpty()) {
                redir.addFlashAttribute("messageType", "fail");
                redir.addFlashAttribute("message", "Fill all required fields!");
                redir.addFlashAttribute("transaction", p);
                return mv;
            }
            ClientProfile profile = this.clientProfileService.findByCompanyName(p.getClient());
            if (profile == null) {
                redir.addFlashAttribute("messageType", "fail");
                redir.addFlashAttribute("message", "Client : '" + p.getClient() + "' not found in the system");
                redir.addFlashAttribute("transaction", p);
                return mv;
            }

            Department department = null;
            if (profile.getCompanyName().equals("CLAY COOLEY LEADERSHIPS")) {
                List<Department> departments = this.departmentService.findByClientProfile(profile);
                if (departments == null || departments.isEmpty()) {

                } else {
                    if (p.getDepartment() == null || p.getDepartment().isEmpty()) {
                        redir.addFlashAttribute("messageType", "fail");
                        redir.addFlashAttribute("message", "Select a department for '" + p.getClient() + "' before proceeding!");
                        redir.addFlashAttribute("transaction", p);
                        return mv;
                    }
                    department = this.departmentService.findByDepartmentNameAndClientProfile(p.getDepartment(), profile);
                }
            }

            Binary logo = new Binary(BsonBinarySubType.BINARY, p.getTransactions().getBytes());
            if (logo.length() <= 0) {
                redir.addFlashAttribute("messageType", "fail");
                redir.addFlashAttribute("message", "Select a file first!");
                redir.addFlashAttribute("transaction", p);
                return mv;
            }

            if (!this.getFileExtension(p.getTransactions()).equals("xlsx")) {
                mv.addObject("messageType", "fail");
                mv.addObject("message", "The file uploaded should be a valid excel file in .xlsx format !".toUpperCase());
                mv.addObject("transaction", p);
                return mv;
            }
            mv.setViewName("redirect:/transaction/add_transaction");
            FileOutputStream fileOuputStream = null;
            String path = System.getProperty("java.io.tmpdir");
            fileOuputStream = new FileOutputStream(path.substring(0, path.length() - 1) + p.getTransactions().getOriginalFilename());
            fileOuputStream.write(p.getTransactions().getBytes());
            String fileLocation = path.substring(0, path.length() - 1) + p.getTransactions().getOriginalFilename();
            LOG.info("Reading information from excel file  ".toUpperCase() + fileLocation);

            double sumAmount = 0.00;
            TransactionFile transactionFile = new TransactionFile();
            transactionFile.setTransactions(logo);
            transactionFile.setTransactionName(p.getTransactions().getOriginalFilename());
            transactionFile.setTransactionFileId(seqGeneratorService.generateSequence(TransactionFile.SEQUENCE_NAME));
            transactionFile.setClientProfile(profile);
            transactionFile.setUpdatedDate(this.getCurrentDate());
            transactionFile.setCreationDate(this.getCurrentDate());
            transactionFile.setCreationTime(this.getCurrentTimestamp());
            transactionFile.setUpdatedTime(this.getCurrentTimestamp());
            TransactionFile newTransactionFile = this.transactionFileService.save(transactionFile);
            if (newTransactionFile == null) {
                LOG.info("Batch file uploaded failed to save ".toUpperCase());
                redir.addFlashAttribute("messageType", "fail");
                redir.addFlashAttribute("message", "Batch file failed to save, please try again!");
                return mv;
            }
            File myFile = new File(fileLocation);
            FileInputStream fis = new FileInputStream(myFile);
            // Finds the workbook instance for XLSX file
            XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
            // Return first sheet from the XLSX workbook
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);
            // Traversing over each row of XLSX file
            LOG.info("ROW COUNT FOR FILE NAME :" + fileLocation + "    :::::  " + String.valueOf(mySheet.getLastRowNum()));
            header = 0;
            for (int i = 0; i <= mySheet.getLastRowNum(); i++) {
                /*
                                
                    LOOPING THROUGH ROWS
                                
                 */
                LOG.info("Processing " + p.getType() + " upload ::: ".toUpperCase());

                XSSFRow row = ((XSSFRow) mySheet.getRow(i));
                if (i > 0) {
                    Transaction transaction = new Transaction();
                    transaction.setClientProfile(profile);
                    transaction.setDepartment(department);
                    counter = 0;
                    /*
                                
                          LOOPING THROUGH CELLS
                                
                                
                     */

                    int cellcount = 0;
                    if (row == null) {
                        transaction.setEmpty(true);
                    } else {
                        for (int j = 0; j < row.getLastCellNum(); j++) {
                            cellcount = j;
                            counter = counter + 1;
                            Cell cell = row.getCell(j);
                            if (cell == null) {
                                transaction.setEmpty(true);
                            } else {
                                if (p.getType().equals("GENERAL")) {
                                    loadDefaultTransactions(profile, department, transaction, cell, counter, session);
                                    if (!transaction.isEmpty()) {
                                        if (transaction.getVehicle() == null) {
                                            if (vehicleService.findByLicensePlateOrderByVehicleIdDesc(transaction.getPlate()) == null || vehicleService.findByLicensePlateOrderByVehicleIdDesc(transaction.getPlate()).isEmpty()) {
                                                Vehicle newVehicle = new Vehicle();
                                                newVehicle.setVehicleId(seqGeneratorService.generateSequence(Vehicle.SEQUENCE_NAME));
                                                newVehicle.setClientProfile(profile);
                                                newVehicle.setDepartment(department);
                                                newVehicle.setColor("NEW");
                                                newVehicle.setVin(null);
                                                newVehicle.setTollTagId("NEW");
                                                newVehicle.setUnit("NEW");
                                                newVehicle.setStartDate(this.getConvertedDate(session, String.valueOf(LocalDate.now())));
                                                newVehicle.setStatus(true);
                                                newVehicle.setVehicleStatus("active");
                                                newVehicle.setLicensePlate(transaction.getPlate());
                                                Vehicle vehicle = this.vehicleService.save(newVehicle);
                                                transaction.setVehicle(vehicle);
                                            }
                                        }
                                    }
                                }
                                if (p.getType().equals("CUSTOM")) {
                                    loadCustomTransactions(profile, transaction, cell, counter, session);
                                    if (!transaction.isEmpty()) {
                                        if (transaction.getVehicle() == null) {
                                            Vehicle newVehicle = new Vehicle();
                                            newVehicle.setVehicleId(seqGeneratorService.generateSequence(Vehicle.SEQUENCE_NAME));
                                            newVehicle.setClientProfile(profile);
                                            newVehicle.setVin(null);
                                            newVehicle.setDepartment(department);
                                            newVehicle.setColor("WHITE");
                                            newVehicle.setStartDate(this.getConvertedDate(session, String.valueOf(LocalDate.now())));
                                            newVehicle.setStatus(true);
                                            newVehicle.setVehicleStatus("start");
                                            newVehicle.setLicensePlate(transaction.getPlate());
                                            Vehicle vehicle = this.vehicleService.save(newVehicle);
                                            transaction.setVehicle(vehicle);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    /*
                                
                          PROCEED SAVING INFO
                                
                                
                     */

                    if (!transaction.isEmpty()) {
                        if (transaction.getVehicle() != null) {
                            department = transaction.getVehicle().getDepartment();
                        } else {
                            department = null;
                        }
                        transactions.add(transaction);
                    }
                }
            }

            if (saveTransaction(redir, mv, profile, transactions, newTransactionFile, sumAmount)) {
                LOG.info("Processed batch upload successfully ".toUpperCase());
                redir.addFlashAttribute("messageType", "success");
                redir.addFlashAttribute("message", "Successfully updated");
            } else {
                redir.addFlashAttribute("message", "Successfully updated");
                LOG.info("Batch file found with missing plates, aborting upload ".toUpperCase());
                //redir.addFlashAttribute("missingplates", missingPlates(transactions));
                redir.addFlashAttribute("messageType", "fail");
                //redir.addFlashAttribute("message", "Batch file found with missing plates, please added them first!");
            }
            return mv;
        } catch (IOException ex) {
            sendMail("noah@cloudsectechnology.com", "Transaction dump failure", ex.toString());
            mv.setViewName("redirect:/transaction/add_transaction");
            redir.addFlashAttribute("messageType", "fail");
            redir.addFlashAttribute("message", "Failed : " + ex);
            return mv;
        }
    }

    boolean saveTransaction(RedirectAttributes redir, ModelAndView mv, ClientProfile profile, List<Transaction> transactionss, TransactionFile newTransactionFile, Double sumAmount) {

        /*
                                
                            PROCESSING ROW COLLECTED
                                
                                
         */
        boolean checkPlate = false;
        for (Transaction transaction : transactionss) {
            if (!transaction.isEmpty()) {
                if (transaction.getVehicle() == null) {
                    checkPlate = true;
                }
            } else {
                transactionss.remove(transaction);
            }
        }
//        if (checkPlate) {
//            return false;
//        }
        int counter = 0;
        boolean plates = true;
        for (Transaction transaction : transactionss) {
            if (transaction.getVehicle() != null) {
                LOG.info("vehicle found, proceeding to add ".toUpperCase());
                if (!transaction.isEmpty()) {
                    LOG.info("saving... ".toUpperCase());
                    newTransactionFile.setDepartment(transaction.getVehicle().getDepartment());
                    this.transactionFileService.save(newTransactionFile);

                    /*
                                
                                SAVING TRANSACTIONS
                                
                                
                     */
                    String modeStatus = transaction.getUploadType();
                    String mode = "NO";
                    LOG.info(" CLIENT :::: ".toUpperCase() + transaction.getClientProfile().getCompanyName());

                    if ("DEFAULT".equals(modeStatus)) {
                        LOG.info(" DEFAULT ".toUpperCase());
                        List<Transaction> avtransactions = this.transactionService.findByExitDateTimeAndExitLocationAndExitLaneAndVehicle(transaction.getExitDateTime(), transaction.getExitLocation(), transaction.getExitLane(), transaction.getVehicle());
                        if (avtransactions == null || avtransactions.isEmpty()) {
                            mode = "YES";
                        } else {
                            mode = "YES";
                            int count = avtransactions.size();
                            if (count > 0) {
                                this.transactionService.deleteAll(avtransactions);
                            }
                        }
                    }
                    if ("CUSTOM".equals(modeStatus)) {
                        LOG.info(" CUSTOM ".toUpperCase());
                        List<Transaction> avtransactions = this.transactionService.findByExitDateTimeAndExitLaneAndVehicle(transaction.getExitDateTime(), transaction.getExitLane(), transaction.getVehicle());
                        if (avtransactions == null || avtransactions.isEmpty()) {
                            mode = "YES";
                        } else {
                            mode = "YES";
                            int count = avtransactions.size();
                            if (count > 0) {
                                this.transactionService.deleteAll(avtransactions);
                            }
                        }
                    }

                    LOG.info(mode);
                    if (mode.equals("YES")) {
                        counter = counter + 1;
                        LOG.info(" RECORD PROCESSING :: ".toUpperCase() + String.valueOf(counter));
                        double transactionAmount = 0.00;
                        if (transaction.getAmount() != null) {
                            transactionAmount = transaction.getAmount();
                            sumAmount = sumAmount + transaction.getAmount();
                        }

                        transaction.setClientProfile(profile);
                        transaction.setDepartment(transaction.getVehicle().getDepartment());
                        transaction.setTransactionId(seqGeneratorService.generateSequence(Transaction.SEQUENCE_NAME));
                        transaction.setTransactionFileId(newTransactionFile.getTransactionFileId());
                        if (transaction.getTransactionDate() != null) {
                            LocalDateTime de = convertToLocalDateTimeViaMilisecond(transaction.getTransactionDate());
                            LocalDate dt = de.toLocalDate();
                            transaction.setTransactionMonth(dt.getMonth().toString());
                            transaction.setTransactionYear(dt.getYear());
                        }
                        if (transaction.getPostedDate() != null) {
                            LocalDateTime dep = convertToLocalDateTimeViaMilisecond(transaction.getPostedDate());
                            LocalDate pdt = dep.toLocalDate();
                            transaction.setPostedMonth(pdt.getMonth().toString());
                            transaction.setPostedYear(pdt.getYear());
                            if (pdt.getMonth().toString().equals("FEBRUARY")) {
                                LOG.info("**********************".toUpperCase());
                                LOG.info("**********************".toUpperCase());
                                LOG.info("**********************".toUpperCase());
                                LOG.info("FEBRUARY TRANSACTIONS".toUpperCase());
                                LOG.info("**********************".toUpperCase());
                                LOG.info("**********************".toUpperCase());
                                LOG.info("**********************".toUpperCase());

                            }
                        }
                        transaction.setStatus(true);
                        transaction.setTransactionDispute(false);
                        transaction.setUpdatedDate(this.getCurrentDate());
                        transaction.setCreationDate(this.getCurrentDate());
                        transaction.setCreationTime(this.getCurrentTimestamp());
                        transaction.setUpdatedTime(this.getCurrentTimestamp());
                        LOG.info("Saving for transaction for plate : " + transaction.getVehicle().getLicensePlate());
                        Transaction new_transaction = this.transactionService.save(transaction);
                        if (new_transaction == null) {
                            LOG.info("**********************".toUpperCase());
                            LOG.info("**********************".toUpperCase());
                            LOG.info("**********************".toUpperCase());
                            LOG.info("Transaction failed to save".toUpperCase());
                            LOG.info("**********************".toUpperCase());
                            LOG.info("**********************".toUpperCase());
                            LOG.info("**********************".toUpperCase());

                        } else {
                            LOG.info("**********************".toUpperCase());
                            LOG.info("**********************".toUpperCase());
                            LOG.info("**********************".toUpperCase());
                            LOG.info("Transaction saved successfully".toUpperCase());
                            LOG.info("**********************".toUpperCase());
                            LOG.info("**********************".toUpperCase());
                            LOG.info("**********************".toUpperCase());
                        }
                        /*
                                
                            TRANSACTION PROCESSED
                                
                                
                         */
                    } else {
                        LOG.info("Transaction for this specific plate : " + transaction.getVehicle().getLicensePlate() + " exists with the same info, so its been skipped".toUpperCase());
                    }
                }
            } else {
                LOG.error("Some vehicles not found... ".toUpperCase());
                plates = false;
            }
        }
        return plates;
    }

    void loadDefaultTransactions(ClientProfile profile, Department department, Transaction transaction, Cell cell, int counter, HttpSession session) {
        /*
                                
                               READ CELL VALUES
                                
                                
         */ LOG.info("COUNTER : ".toUpperCase() + String.valueOf(counter));

        transaction.setUploadType("DEFAULT");
        String value = "";
        Date tranDate = this.getCurrentDate();
        Date tranDateTime = this.getCurrentDate();
        if (cell == null) {
            LOG.info("NULL CELL PROCESSING ".toUpperCase());
            value = "";
        } else {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    LOG.info("STRING CELL PROCESSING ".toUpperCase());
                    value = cell.getStringCellValue();
                    if (counter == 4 || counter == 5) {
                        LOG.info("DATE GENERATED VALUE :: ".toUpperCase() + value);
                        if (this.getConvertedDate(session, value) == null) {
                            LOG.info("DATE VALUE PROCESSING FAILED, SKIPPING TRANSACTIONS".toUpperCase());
                            transaction.setEmpty(true);
                        }
                        tranDate = this.getConvertedDay(session, value);
                        if (tranDate != null) {
                            tranDate.setHours(00);
                            tranDate.setMinutes(00);
                            tranDate.setSeconds(00);
                        }
                        tranDateTime = this.getConvertedDate(session, value);
                        LOG.info("DATE GENERATED VALUE: ".toUpperCase() + value);
                    }
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    LOG.info("NUMERIC CELL PROCESSING ".toUpperCase());
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        LOG.info("DATE GENERATED VALUE: ".toUpperCase());
                        tranDateTime = cell.getDateCellValue();
                        tranDate = cell.getDateCellValue();
                        tranDate.setHours(00);
                        tranDate.setMinutes(00);
                        tranDate.setSeconds(00);
                    } else {
                        value = String.valueOf(new BigDecimal(cell.toString()).toPlainString());
                        if (counter == 1) {
                            value = String.valueOf((long) cell.getNumericCellValue());
                        }
                        if (counter == 8) {
                            value = String.valueOf((double) cell.getNumericCellValue());
                        }
                        LOG.info("NUMERIC VALUE FOUND:::   ".toUpperCase() + value);
                        if (counter == 4 || counter == 5) {
                            LOG.info("DATE VALUE PROCESSING FAILED, SKIPPING TRANSACTIONS".toUpperCase());
                            transaction.setEmpty(true);
                        }
                    }

                    break;
                case Cell.CELL_TYPE_BOOLEAN:

                    LOG.info("BOOLEAN CELL PROCESSING ".toUpperCase());
                    if (cell.getBooleanCellValue()) {
                        value = "true";
                    } else {
                        value = "false";
                    }
                    break;
                default:
                    LOG.info("DEFAULT CELL NOT FOUND ".toUpperCase());
                    if (counter == 1) {
                        value = null;
                    } else {
                        value = "";
                    }
            }

        }

        switch (counter) {
            case 1:
                if (cell != null) {
                    if (value == null || value.isEmpty()) {
                        transaction.setEmpty(true);
                    } else {
                        transaction.setPlate(value);
                        transaction.setEmpty(false);
                        LOG.info("Checking out plate number : ".toUpperCase() + value);
                        List<Vehicle> vehicle = this.vehicleService.findByLicensePlateOrderByVehicleIdDesc(value);
                        if (vehicle == null || vehicle.isEmpty()) {
                            List<Vehicle> tolltag = this.vehicleService.findByTollTagId(value);
                            if (tolltag == null || tolltag.isEmpty()) {
                                tolltag = this.vehicleService.findByTollTagId("DNT." + value);
                                if (tolltag == null || tolltag.isEmpty()) {
                                    tolltag = this.vehicleService.findByTollTagId("0" + value);
                                    if (tolltag == null || tolltag.isEmpty()) {
                                        transaction.setVehicle(null);
                                    } else {
                                        transaction.setVehicle(tolltag.get(0));
                                    }
                                } else {
                                    transaction.setVehicle(tolltag.get(0));
                                }
                            } else {
                                transaction.setVehicle(tolltag.get(0));
                            }
                        } else {
                            transaction.setVehicle(vehicle.get(0));
                        }
                    }
                } else {
                    transaction.setEmpty(true);
                }
                break;
            case 2:
                if (!transaction.isEmpty()) {
                    if (!"NA".equals(value)) {
                        State state = this.stateService.findByStateName(value);
                        if (state == null) {
                            state = new State();
                            state.setStateId(seqGeneratorService.generateSequence(State.SEQUENCE_NAME));
                            state.setStateName(value);
                            this.stateService.save(state);
                        }
                        transaction.setState(value);
                    }
                }
                break;
            case 3:
                if (!transaction.isEmpty()) {
                    if (value == null || value.isEmpty()) {
                    } else {
                        if (!"NA".equals(value)) {
                            Agency agency = this.agencyService.findByAgencyName(value.toUpperCase());
                            if (agency == null) {
                                agency = new Agency();
                                agency.setAgencyId(seqGeneratorService.generateSequence(Agency.SEQUENCE_NAME));
                                agency.setAgencyName(value.toUpperCase());
                                this.agencyService.save(agency);
                            }
                            transaction.setAgency(value.toUpperCase());
                        }
                    }
                }
                break;
            case 4:
                if (!transaction.isEmpty()) {
                    transaction.setTransactionDate(tranDate);
                    transaction.setExitDateTime(tranDateTime);
                }
                break;
            case 5:
                if (!transaction.isEmpty()) {
                    transaction.setPostedDate(tranDate);
                    transaction.setPostedDateTime(tranDateTime);
                }
                break;
            case 6:
                if (!transaction.isEmpty()) {
                    transaction.setExitLane(value);
                }
                break;
            case 7:
                if (!transaction.isEmpty()) {
                    transaction.setExitLocation(value);
                    break;
                }
            case 8:
                if (this.isNumeric(value)) {
                    transaction.setAmount(getTwoDecimal(Double.valueOf(value)));
                } else {
                    transaction.setEmpty(true);
                }
                break;
            default:
                LOG.info("Extra field was found but not assigned ::: ".toUpperCase() + value);
        }
    }

    void loadCustomTransactions(ClientProfile profile, Transaction transaction, Cell cell, int counter, HttpSession session) {
        /*
                                
                               READ CELL VALUES
                                
                                
         */ LOG.info("COUNTER : ".toUpperCase() + String.valueOf(counter));

        transaction.setUploadType("CUSTOM");
        String value = "";
        Date tranDate = this.getCurrentDate();
        Date tranDateTime = this.getCurrentDate();
        if (cell == null) {
            LOG.info("NULL CELL PROCESSING ".toUpperCase());
            value = "";
        } else {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    LOG.info("STRING CELL PROCESSING ".toUpperCase());
                    value = cell.getStringCellValue();
                    if (counter == 9 || counter == 3) {
                        LOG.info("DATE GENERATED VALUE :: ".toUpperCase() + value);
                        if (this.getConvertedDate(session, value) == null) {
                            LOG.info("DATE VALUE PROCESSING FAILED, SKIPPING TRANSACTIONS".toUpperCase());
                            transaction.setEmpty(true);
                        }
                        tranDate = this.getConvertedDay(session, value);
                        if (tranDate != null) {
                            tranDate.setHours(00);
                            tranDate.setMinutes(00);
                            tranDate.setSeconds(00);
                        }
                        tranDateTime = this.getConvertedDate(session, value);
                        if (tranDateTime != null) {
                            LOG.info("DATE GENERATED VALUE: ".toUpperCase() + tranDateTime.toString());
                        }
                    }
                    if (tranDateTime == null || tranDate == null) {
                        LOG.info("DATE VALUE PROCESSING FAILED, SKIPPING TRANSACTIONS".toUpperCase());
                        transaction.setEmpty(true);
                    }
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    LOG.info("NUMERIC CELL PROCESSING ".toUpperCase());
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        LOG.info("DATE GENERATED VALUE: ".toUpperCase());
                        tranDateTime = cell.getDateCellValue();
                        tranDate = cell.getDateCellValue();
                        tranDate.setHours(00);
                        tranDate.setMinutes(00);
                        tranDate.setSeconds(00);
                    } else {
                        value = String.valueOf(new BigDecimal(cell.toString()).toPlainString());
                        if (counter == 1) {
                            value = String.valueOf((long) cell.getNumericCellValue());
                        }
                        if (counter == 8) {
                            value = String.valueOf((double) cell.getNumericCellValue());
                        }
                        LOG.info("NUMERIC VALUE FOUND:::   ".toUpperCase() + value);
                        if (counter == 9 || counter == 3) {
                            LOG.info("DATE VALUE PROCESSING FAILED, SKIPPING TRANSACTIONS".toUpperCase());
                            transaction.setEmpty(true);
                        }
                    }

                    break;
                case Cell.CELL_TYPE_BOOLEAN:

                    LOG.info("BOOLEAN CELL PROCESSING ".toUpperCase());
                    if (cell.getBooleanCellValue()) {
                        value = "true";
                    } else {
                        value = "false";
                    }
                    break;
                default:
                    LOG.info("DEFAULT CELL NOT FOUND ".toUpperCase());
                    if (counter == 1) {
                        value = null;
                    } else {
                        value = "";
                    }
            }

        }

        switch (counter) {
            case 1:
                if (cell != null) {
                    if (value == null || value.isEmpty()) {
                        transaction.setEmpty(true);
                    } else {
                        transaction.setPlate(value);
                        transaction.setEmpty(false);
                        LOG.info("Checking out plate number : ".toUpperCase() + value);
                        List<Vehicle> vehicle = this.vehicleService.findByLicensePlateOrderByVehicleIdDesc(value);
                        if (vehicle == null || vehicle.isEmpty()) {
                            List<Vehicle> tolltag = this.vehicleService.findByTollTagId(value);
                            if (tolltag == null || tolltag.isEmpty()) {
                                tolltag = this.vehicleService.findByTollTagId("DNT." + value);
                                if (tolltag == null || tolltag.isEmpty()) {
                                    tolltag = this.vehicleService.findByTollTagId("0" + value);
                                    if (tolltag == null || tolltag.isEmpty()) {
                                        transaction.setVehicle(null);
                                    } else {
                                        transaction.setVehicle(tolltag.get(0));
                                    }
                                } else {
                                    transaction.setVehicle(tolltag.get(0));
                                }
                            } else {
                                transaction.setVehicle(tolltag.get(0));
                            }
                        } else {
                            transaction.setVehicle(vehicle.get(0));
                        }
                    }
                } else {
                    transaction.setEmpty(true);
                }
                break;
            case 2:
                if (!transaction.isEmpty()) {
                    if (!"NA".equals(value)) {
                        if (this.isNumeric(value)) {

                        } else {
                            State state = this.stateService.findByStateName(value);
                            if (state == null) {
                                state = new State();
                                state.setStateId(seqGeneratorService.generateSequence(State.SEQUENCE_NAME));
                                state.setStateName(value);
                                this.stateService.save(state);
                            }
                            transaction.setState(value);
                        }
                    }
                }
                break;
            case 3:
                if (!transaction.isEmpty()) {
                    transaction.setTransactionDate(tranDate);
                    transaction.setExitDateTime(tranDateTime);
                }
                break;
            case 4:
                if (!transaction.isEmpty()) {
                    if (value == null || value.isEmpty()) {
                    } else {
                        if (!"NA".equals(value)) {
                            if (this.isNumeric(value)) {

                            } else {
                                Agency agency = this.agencyService.findByAgencyName(value);
                                if (agency == null) {
                                    agency = new Agency();
                                    agency.setAgencyId(seqGeneratorService.generateSequence(Agency.SEQUENCE_NAME));
                                    agency.setAgencyName(value);
                                    this.agencyService.save(agency);
                                }
                                transaction.setAgency(value);
                            }
                        }
                    }
                }
                break;
            case 5:
                if (!transaction.isEmpty()) {
                    transaction.setClient(value);
                }
                break;
            case 6:
                if (!transaction.isEmpty()) {
                    transaction.setExitLane(value);
                }
                break;
            case 7:
                if (!transaction.isEmpty()) {
                    transaction.setTransactionClass(value);
                }
                break;
            case 8:
                if (!transaction.isEmpty()) {
                    if (this.isNumeric(value)) {
                        transaction.setAmount(getTwoDecimal(Double.valueOf(value)));
                    } else {
                        transaction.setEmpty(true);
                    }
                }
                break;
            case 9:

                if (!transaction.isEmpty()) {
                    transaction.setPostedDate(tranDate);
                    transaction.setPostedDateTime(tranDateTime);
                }
                break;
            default:
                LOG.info("Counter " + String.valueOf(counter) + " Extra field was found but not assigned ::: ".toUpperCase() + value);
        }
    }

    /**
     * ****
     *
     *
     * Reverse transaction
     *
     * @param session
     * @param transactionId
     * @return
     */
    @RequestMapping(path = "/reverse/transaction/{transactionId}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> reverseTransaction(HttpSession session, @PathVariable("transactionId") String transactionId) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        LOG.info("Reversing transaction...ID :: " + transactionId);
        Transaction transaction = this.transactionService.findByTransactionId(Long.valueOf(transactionId));
        TransactionFile file = this.transactionFileService.findByTransactionFileId(transaction.getTransactionFileId());
        List<Transaction> badTransactions = this.transactionService.findByTransactionFileId(transaction.getTransactionFileId());
        this.transactionService.deleteAll(badTransactions);
        this.transactionFileService.delete(this.transactionFileService.findByTransactionFileId(transaction.getTransactionFileId()));
        result.setTitle("success");
        result.setMessage("Transaction reversal completed successfully!");
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = "/reverse/upload/{transactionId}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> reverseTransactionFile(HttpSession session, @PathVariable("transactionId") String transactionId) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        LOG.info("Reversing transaction file ...ID :: " + transactionId);
        TransactionFile file = this.transactionFileService.findByTransactionFileId(Long.valueOf(transactionId));
        Account account = this.accountService.findByClientProfile(file.getClientProfile());
        List<Department> departments = this.departmentService.findByClientProfile(file.getClientProfile());
        if (departments == null || departments.isEmpty()) {
            double total = 0.00;
            Aggregation aggregation = newAggregation(
                    match(Criteria.where("clientProfile").is(file.getClientProfile()).and("transactionFileId").is(Long.valueOf(transactionId))),
                    group("clientProfile").sum("amount").as("total")
            );
            AggregationResults<Tollspendings> groupResults = mongoTemplate.aggregate(aggregation, Transaction.class,
                    Tollspendings.class
            );
            List<Tollspendings> expenditureReport = groupResults.getMappedResults();
            if (expenditureReport == null || expenditureReport.isEmpty()) {
            } else {
                Tollspendings toll = expenditureReport.get(0);
                total = toll.getTotal();
            }
            account.setAmount(account.getAmount() + total);
            this.accountService.save(account);
        } else {
            departments.stream().map((department) -> this.departmentAccountService.findByClientProfileAndDepartment(file.getClientProfile(), department)).map((depaccount) -> {
                double total = 0.00;
                Aggregation aggregation = newAggregation(
                        match(Criteria.where("department").is(file.getDepartment()).and("transactionFileId").is(Long.valueOf(transactionId))),
                        group("department").sum("amount").as("total")
                );
                AggregationResults<Tollspendings> groupResults = mongoTemplate.aggregate(aggregation, Transaction.class,
                        Tollspendings.class
                );
                List<Tollspendings> expenditureReport = groupResults.getMappedResults();
                if (expenditureReport == null || expenditureReport.isEmpty()) {
                } else {
                    Tollspendings toll = expenditureReport.get(0);
                    total = toll.getTotal();
                }

                depaccount.setAmount(depaccount.getAmount() + total);
                return depaccount;
            }).forEachOrdered((depaccount) -> {
                this.departmentAccountService.save(depaccount);
            });
        }
        List<Transaction> badTransactions = this.transactionService.findByTransactionFileId(Long.valueOf(transactionId));
        this.transactionService.deleteAll(badTransactions);
        this.transactionFileService.delete(this.transactionFileService.findByTransactionFileId(Long.valueOf(transactionId)));
        result.setTitle("success");
        result.setMessage("Transaction reversal completed successfully!");
        return ResponseEntity.ok(result);
    }

    @RequestMapping("/delete/{id}")
    public ModelAndView deleteTransactions(@PathVariable("id") Long id, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        mv.setViewName("viewtransactions");
        Transaction transaction = this.transactionService.findByTransactionId(id);
        if (transaction == null) {

        } else {
            this.transactionService.delete(transaction);
            mv.addObject("message", "Transaction deleted!");
            mv.addObject("messageType", "success");
        }

        loadCombos(mv);
        mv.addObject("transaction", new TransactionUpload());
        mv.addObject("institution", institutionName());
        mv.addObject("topic", "Departmental Data");
        List<Transaction> transactions = this.transactionService.findAll();
        if (transactions == null || transactions.isEmpty()) {
            mv.addObject("messageType", "No data found");
            mv.addObject("messageType", "fail");
            return mv;
        } else {
            mv.addObject("messageType", "Client information found");
            mv.addObject("messageType", "success");
            mv.addObject("allTransactions", transactions);
        }

        return mv;
    }

    @RequestMapping(path = "account/{delete-account}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> deleteAccount(@ModelAttribute("cancel") Cancel p, Model model, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }

        LOG.info("Delete transaction ".toUpperCase());
        Transaction transaction = this.transactionService.findByTransactionId(p.getCancelItem());
        if (transaction == null) {
            result.setMessage("Transaction does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            this.transactionService.delete(transaction);
            result.setMessage("Transaction deleted!");
            result.setTitle("success");
            return ResponseEntity.ok(result);
        }
    }

    @RequestMapping(path = "/load/by/today/{transactionDate}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchDate(HttpSession session, @PathVariable("transactionDate") String transactionDate) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        LOG.info("Querying data for ::  ".toUpperCase() + transactionDate);
        Date searchDate = this.getConvertedDate(session, transactionDate);
        List<Transaction> transactions = this.transactionService.findByPostedDate(searchDate);
        if (transactions == null || transactions.isEmpty()) {
            LOG.info("No transactions uploaded on" + String.valueOf(searchDate));
            result.setMessage("No transactions uploaded on " + String.valueOf(searchDate));
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            result.setTitle("success");
            LOG.info("transactions data found");
            result.setMessage("Transactions data found");
            result.setResults(transactions);
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = "/load/by/range/{startDate}/{endDate}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchDate(HttpSession session, @PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        Date end_Date = this.getConvertedDate(session, endDate);
        Date start_Date = this.getConvertedDate(session, startDate);
        session.setAttribute("transaction_start_date", startDate);
        session.setAttribute("transaction_end_date", endDate);
        List<Transaction> transactions = this.getTransactionRange(start_Date, end_Date);
        if (transactions == null || transactions.isEmpty()) {
            result.setMessage("No transactions uploaded on that range");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            result.setTitle("success");
            LOG.info("transactions range data found");
            result.setMessage("Transactions data found");
            result.setResults(transactions);
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = "/load/by/today/client", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchTodayClient(HttpSession session, @PageableDefault(value = 10) Pageable pageable, @RequestParam String clientName, @RequestParam String transactionDate) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        String client = clientName;
        ClientProfile profile = this.clientProfileService.findByCompanyName(client);
        if (profile == null) {
            result.setMessage("Client  " + clientName + " does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        Date searchDate = this.getConvertedDate(session, transactionDate);
        List<Transaction> transactions = this.transactionService.findByClientProfileAndPostedDateOrderByTransactionIdDesc(profile, searchDate);
        if (transactions == null || transactions.isEmpty()) {
            result.setMessage("Client : " + clientName + " has no transactions");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            result.setTitle("success");
            result.setMessage("Client : " + clientName + " data found");
            result.setResults(transactions);
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = "/load/by/today/client/department", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchTodayClientDepartment(HttpSession session, @PageableDefault(value = 10) Pageable pageable, @RequestParam String clientName, @RequestParam String departmentName, @RequestParam String transactionDate) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        String client = clientName;
        String department = departmentName;
        Date searchDate = this.getConvertedDate(session, transactionDate);
        ClientProfile profile = this.clientProfileService.findByCompanyName(client);
        if (profile == null) {
            result.setMessage(searchDate.toString() + "'s  client transaction does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        Department dep = this.departmentService.findByDepartmentNameAndClientProfile(department, profile);
        if (dep == null) {
            result.setMessage(searchDate.toString() + "'s department transaction does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }

        List<Transaction> transactions = this.transactionService.findByDepartmentAndPostedDate(dep, searchDate);
        if (transactions == null || transactions.isEmpty()) {
            result.setMessage("No department data found on " + searchDate.toString() + "'s");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            result.setTitle("success");
            result.setMessage(searchDate.toString() + "'s Department data found");
            result.setResults(transactions);
            double total = 0.00;
            for (Transaction transaction : transactions) {
                total = total + transaction.getAmount();
            }
            result.setTotal(total);
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = "/load/by/client", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchClient(HttpSession session, @PageableDefault(value = 10) Pageable pageable, @RequestParam String clientName) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        String client = clientName;
        ClientProfile profile = this.clientProfileService.findByCompanyName(client);
        if (profile == null) {
            result.setMessage("Client  " + clientName + " does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        List<Transaction> transactions = this.transactionService.findByClientProfileOrderByTransactionIdDesc(profile);
        if (transactions == null || transactions.isEmpty()) {
            result.setMessage("Client : " + clientName + " has no transactions");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            result.setTitle("success");
            result.setMessage("Client : " + clientName + " data found");
            double total = 0.00;
            for (Transaction transaction : transactions) {
                total = total + transaction.getAmount();
            }
            result.setTotal(total);
            result.setResults(transactions);
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = "/load/by/client/department", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchClientDepartment(HttpSession session, @PageableDefault(value = 10) Pageable pageable, @RequestParam String clientName, @RequestParam String departmentName) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        String client = clientName;
        String department = departmentName;
        ClientProfile profile = this.clientProfileService.findByCompanyName(client);
        if (profile == null) {
            result.setMessage("client transaction does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        Department dep = this.departmentService.findByDepartmentNameAndClientProfile(department, profile);
        if (dep == null) {
            result.setMessage("department transaction does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        List<Transaction> transactions = this.transactionService.findByClientProfileAndDepartmentOrderByTransactionIdDesc(profile, dep);
        if (transactions == null || transactions.isEmpty()) {
            result.setMessage("No department data found today");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            result.setTitle("success");
            result.setMessage("Department data found");
            double total = 0.00;
            for (Transaction transaction : transactions) {
                total = total + transaction.getAmount();
            }
            result.setTotal(total);
            result.setResults(transactions);
        }
        return ResponseEntity.ok(result);
    }

    //*********DISPUTES
    @RequestMapping(path = "/reverse/dispute/{disputeId}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> reverseDisputeTransaction(HttpSession session, @PathVariable("disputeId") Long disputeId) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        TransactionDispute transactionDispute = this.transactionDisputeService.findByDisputeId(disputeId);
        if (transactionDispute == null) {
            result.setMessage("Invalid dispute, please check and try again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            Transaction transaction = transactionDispute.getTransaction();
            List<Transaction> badTransactions = this.transactionService.findByTransactionFileId(transaction.getTransactionFileId());
            this.transactionService.deleteAll(badTransactions);
            this.transactionDisputeService.delete(transactionDispute);
            result.setTitle("success");
            result.setMessage("Transaction reversal completed successfully!");
            return ResponseEntity.ok(result);
        }
    }

    @RequestMapping(path = "/close/dispute/{disputeId}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> closeDisputes(HttpSession session, @PathVariable("disputeId") Long disputeId) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        TransactionDispute transactionDispute = this.transactionDisputeService.findByDisputeId(disputeId);
        if (transactionDispute == null) {
            result.setMessage("Invalid dispute, please check and try again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            Transaction transaction = transactionDispute.getTransaction();
            transaction.setTransactionDispute(false);
            this.transactionService.save(transaction);
            this.transactionDisputeService.delete(transactionDispute);
            result.setTitle("success");
            result.setMessage("Transaction reversal completed successfully!");
            return ResponseEntity.ok(result);
        }
    }

    JasperPrint printTransactions(List<Transaction> transactionList) {
        try {
            JRBeanCollectionDataSource transactionDataSource = new JRBeanCollectionDataSource(transactionList);
            Map<String, Object> parameters = new HashMap();
            parameters.put("TransactionDataSource", transactionDataSource);
            ClassPathResource fStream = new ClassPathResource("reportsDesign/transactions.jrxml");
            InputStream inputStream = fStream.getInputStream();
            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return jasperPrint;
        } catch (IOException | JRException ex) {
            LOG.info(ex.toString().toUpperCase());
            return null;
        }

    }

    @GetMapping(value = "/transactionPdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePDFReport(HttpSession session) {
        LOG.info("Generating pdf...");
        List<Transaction> transactionList = new ArrayList<>();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            return null;
        }
        String startDate = (String) session.getAttribute("transaction_start_date");
        String endDate = (String) session.getAttribute("transaction_end_date");
        if (startDate == null || startDate.isEmpty() || startDate.equals("NONE")) {
            LOG.info("No date range specified");
            transactionList = this.transactionService.findAllByOrderByTransactionIdDesc();
        } else {
            Date end_Date = this.getConvertedDate(session, endDate);
            Date start_Date = this.getConvertedDate(session, startDate);
            transactionList = this.getTransactionRange(start_Date, end_Date);
        }
        LOG.info("Detailed transaction for generating report with data source: {}", transactionList);

        try {
            if (printTransactions(transactionList) != null) {
                ByteArrayResource byteArrayResource = reportService.generateDataSourceReport(transactionList, ExportReportType.PDF, printTransactions(transactionList));
                ByteArrayInputStream bis = (ByteArrayInputStream) byteArrayResource.getInputStream();
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Disposition", "inline; filename=transactions.pdf");
                return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bis));
            } else {
                LOG.info("NO DATA WAS PROCESSED");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (IOException ex) {
            LOG.error(ex.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/excel/transactions.xlsx")
    public void generateExcelReport(HttpServletResponse response, HttpSession session) throws IOException {
        List<Transaction> transactionList = new ArrayList<>();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            return;
        }

        String startDate = (String) session.getAttribute("transaction_start_date");
        String endDate = (String) session.getAttribute("transaction_end_date");
        String user_type = (String) session.getAttribute("ns_user");

        if (startDate == null || startDate.isEmpty() || startDate.equals("NONE")) {
            transactionList = this.transactionService.findAllByOrderByTransactionIdDesc();
        } else {
            Date end_Date = this.getConvertedDate(session, endDate);
            Date start_Date = this.getConvertedDate(session, startDate);
            transactionList = this.getTransactionRange(start_Date, end_Date);
        }
        LOG.info("Detailed transaction for generating report with data source: {}", transactionList);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=transactions.xlsx");
        ByteArrayInputStream stream = ExcelTransactionExporter.exportListToExcelFile(transactionList);
        IOUtils.copy(stream, response.getOutputStream());
    }

    private List<Transaction> getTransactionRange(Date startDate, Date endDate) {
        Query query = new Query();
        query.addCriteria(Criteria.where("transactionDate").gte(startDate).lte(endDate));
        List<Transaction> transactions = mongoTemplate.find(query, Transaction.class
        );
        return transactions;
    }

}
