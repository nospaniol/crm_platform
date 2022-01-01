package com.crm.controller.admin;

import com.crm.generic.Nospaniol;
import com.crm.model.Cancel;
import com.crm.model.Invoice;
import com.crm.model.InvoiceForm;
import com.crm.model.FeeType;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.JobResponse;
import com.crm.model.State;
import com.crm.model.TransactionUpload;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("invoice")
public class InvoiceController extends Nospaniol {

    void loadCombos(ModelAndView mv) {
        mv.addObject("institution", institutionName());
        List<ClientProfile> clientList = this.clientProfileService.findAll();
        mv.addObject("clientList", clientList);
        List<FeeType> feeList = this.feeTypeService.findAll();
        mv.addObject("feeList", feeList);
        List<State> stateList = this.stateService.findAll();
        mv.addObject("stateList", stateList);
    }

    @RequestMapping(value = "save/{saveInfo}", method = RequestMethod.POST)
    public ModelAndView registerInfo(@ModelAttribute("invoice") InvoiceForm p, RedirectAttributes redir, HttpServletRequest req, HttpSession session) throws IOException {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("addinvoice");
        LOG.info("Saving invoice info".toUpperCase());
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
        if (p.getClient().isEmpty() || p.getInvoiceAmount().isEmpty() || p.getInvoiceStat().isEmpty() || p.getFeeType().isEmpty() || p.getInvoiceDate().isEmpty()) {
            mv.addObject("messageType", "fail");
            mv.addObject("message", "Fill all required fields!");
            mv.addObject("invoice", p);
            return mv;
        }

        ClientProfile profile = this.clientProfileService.findByCompanyName(p.getClient());
        if (profile == null) {
            mv.addObject("messageType", "fail");
            mv.addObject("message", "Select a client first!");
            mv.addObject("invoice", p);
            return mv;
        }
        Department dep = null;
        List<Department> departments = this.departmentService.findByClientProfile(profile);
        if (departments == null || departments.isEmpty()) {

        } else {
            if (p.getDepartment().isEmpty()) {
                mv.addObject("messageType", "fail");
                mv.addObject("message", "Select a department!");
                mv.addObject("invoice", p);
                return mv;
            }
            dep = this.departmentService.findByDepartmentNameAndClientProfile(p.getDepartment(), profile);

        }
        FeeType type = this.feeTypeService.findByFeeTypeName(p.getFeeType());

        Binary pdfType = new Binary(BsonBinarySubType.BINARY, p.getPdfFile().getBytes());
        Binary excelType = new Binary(BsonBinarySubType.BINARY, p.getExcelFile().getBytes());
        Invoice invoice = this.invoiceService.findByInvoiceId(p.getInvoiceId());
        if (invoice == null) {
            invoice = new Invoice();
            invoice.setInvoiceId(seqGeneratorService.generateSequence(Invoice.SEQUENCE_NAME));
            invoice.setCreationDate(this.getCurrentDate());
            invoice.setCreationTime(this.getCurrentTimestamp());
            if (pdfType.length() <= 0 || excelType.length() <= 0) {
                mv.addObject("messageType", "fail");
                mv.addObject("message", "Select atleast one invoice attachment!");
                mv.addObject("profile", p);
                return mv;
            }
        } else {
            if (pdfType.length() <= 0 || excelType.length() <= 0) {

            } else {
                if (pdfType.length() <= 0) {
                    pdfType = invoice.getPdfFile();
                } else {
                    pdfType = new Binary(BsonBinarySubType.BINARY, p.getPdfFile().getBytes());
                }
                if (excelType.length() <= 0) {
                    excelType = invoice.getExcelFile();
                } else {
                    excelType = new Binary(BsonBinarySubType.BINARY, p.getExcelFile().getBytes());
                }
            }
        }
        LocalDate invDate = LocalDate.parse(p.getInvoiceDate());
        invoice.setInvoiceMonth(invDate.getMonth().toString());
        invoice.setInvoiceYear(invDate.getYear());

        if (!p.getPaidDate().isEmpty()) {
            LocalDate payDate = LocalDate.parse(p.getPaidDate());
            invoice.setPaidMonth(payDate.getMonth().toString());
            invoice.setPaidYear(payDate.getYear());
        }
        invoice.setPdfFile(pdfType);
        invoice.setExcelFile(excelType);
        invoice.setClientProfile(profile);
        invoice.setDepartment(dep);
        invoice.setInvoiceAmount(Double.parseDouble(p.getInvoiceAmount()));
        invoice.setInvoiceDate(this.getConvertedDate(p.getInvoiceDate()));
        invoice.setInvoiceStat(p.getInvoiceStat());
        invoice.setFeeType(type);
        if (!p.getPaidDate().isEmpty()) {
            invoice.setPaidDate(this.getConvertedDate(p.getPaidDate()));
        }
        if (!p.getFeeAmount().isEmpty()) {
            invoice.setFeeAmount(Double.parseDouble(p.getFeeAmount()));
        }
        if (!p.getTollAmount().isEmpty()) {
            invoice.setTollAmount(Double.parseDouble(p.getTollAmount()));
        }
        if (!p.getTotalPaid().isEmpty()) {
            invoice.setTotalPaid(Double.parseDouble(p.getTotalPaid()));
        }
        invoice.setUpdatedDate(this.getCurrentDate());
        invoice.setUpdatedTime(this.getCurrentTimestamp());
        this.invoiceService.save(invoice);
        mv.addObject("institution", institutionName());
        mv.addObject("messageType", "success");
        mv.addObject("message", "Successfully updated");
        mv.setViewName("addinvoice");
        return mv;
    }

    @RequestMapping("/new_invoice")
    public ModelAndView newInvoice(HttpSession session, HttpServletRequest req,
            RedirectAttributes redir
    ) {
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
        this.loadCombos(mv);
        mv.addObject("institution", institutionName());
        mv.addObject("invoice", new InvoiceForm());
        mv.setViewName("addinvoice");
        return mv;
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editInvoice(@PathVariable("id") Long id, HttpSession session,
            HttpServletRequest req, RedirectAttributes redir
    ) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("invoice");
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

        mv.addObject("institution", institutionName());
        Invoice invoice = this.invoiceService.findByInvoiceId(id);
        if (invoice == null) {
            mv.addObject("invoice", invoice);
            return mv;
        } else {
            mv.setViewName("addinvoice");
            this.loadCombos(mv);
            InvoiceForm form = new InvoiceForm();
            form.setClient(invoice.getClientProfile().getCompanyName());
            if (invoice.getDepartment() != null) {
                form.setDepartment(invoice.getDepartment().getDepartmentName());
            }
            if (invoice.getFeeAmount() != null) {
                form.setFeeAmount(invoice.getFeeAmount().toString());
            }
            if (invoice.getInvoiceAmount() != null) {
                form.setInvoiceAmount(invoice.getInvoiceAmount().toString());
            }
            if (invoice.getTollAmount() != null) {
                form.setTollAmount(invoice.getTollAmount().toString());
            }
            if (invoice.getTotalPaid() != null) {
                form.setTotalPaid(invoice.getTotalPaid().toString());
            }
            if (invoice.getInvoiceDate() != null) {
                form.setInvoiceDate(invoice.getInvoiceDate().toString());
            }
            if (invoice.getPaidDate() != null) {
                form.setPaidDate(invoice.getPaidDate().toString());
            }
            if (invoice.getFeeType() != null) {
                form.setFeeType(invoice.getFeeType().getFeeTypeName());
            }
            if (invoice.getInvoiceStat() != null) {
                form.setInvoiceStat(invoice.getInvoiceStat());
            }
            if (invoice.getInvoiceId() != null) {
                form.setInvoiceId(invoice.getInvoiceId());
            }
            mv.addObject("invoice", form);
            return mv;
        }
    }

    @RequestMapping("/view_invoice")
    public ModelAndView viewInvoices(HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
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
        mv.addObject("topic", "Recently added invoices");
        mv.addObject("allInvoices", this.invoiceService.findTop10ByOrderByInvoiceIdDesc());
        mv.setViewName("viewinvoices");
        return mv;
    }

    @RequestMapping("/monthly_invoice")
    public ModelAndView monthlyInvoices(HttpSession session, HttpServletRequest req,
            RedirectAttributes redir
    ) {
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
        LocalDate today = LocalDate.now();
        String month = today.getMonth().toString();
        int year = today.getYear();
        mv.addObject("transaction", new TransactionUpload());
        mv.addObject("institution", institutionName());
        mv.addObject("topic", month + " / " + String.valueOf(year) + " Invoices");
        mv.addObject("allInvoices", this.invoiceService.findByInvoiceMonthAndInvoiceYearOrderByInvoiceIdDesc(month, year));
        mv.setViewName("monthlyinvoices");
        return mv;
    }

    @RequestMapping(value = "/all/added/invoices", method = RequestMethod.GET)
    public ModelAndView allInvoices(@PageableDefault(value = 10) Pageable pageable, RedirectAttributes redir,
            HttpServletRequest req, HttpSession session
    ) {
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
        mv.addObject("invoice", new InvoiceForm());
        mv.addObject("institution", institutionName());
        mv.addObject("topic", "All invoices");
        mv.addObject("allInvoices", this.invoiceService.findAll());
        mv.setViewName("allinvoices");
        return mv;
    }

    @RequestMapping(path = "account/{delete-account}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> deleteAccount(@ModelAttribute("cancel") Cancel p, Model model,
            HttpSession session, HttpServletRequest req,
            RedirectAttributes redir
    ) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        Invoice invoice = this.invoiceService.findByInvoiceId(p.getCancelItem());
        if (invoice == null) {
            result.setMessage("Invoice does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            this.invoiceService.delete(invoice);
            result.setMessage("invoice deleted!");
            result.setTitle("success");
            return ResponseEntity.ok(result);
        }
    }

    @RequestMapping("/view/{id}")
    public ModelAndView viewProfile(@PathVariable("id") Long id, HttpSession session,
            HttpServletRequest req, RedirectAttributes redir
    ) {
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
        Invoice invoice = this.invoiceService.findByInvoiceId(id);
        if (invoice == null) {
            redir.addFlashAttribute("message", "No invoice found");
            redir.addFlashAttribute("messageType", "fail");
            mv.setViewName("redirect:/invoice/new_invoice/");
            return mv;
        } else {
            ClientProfile profile = invoice.getClientProfile();
            mv.addObject("companyLogo", Base64.getEncoder().encodeToString(profile.getCompanyLogo().getData()));
            if (invoice.getPdfFile() != null) {
                mv.addObject("invoicePdf", Base64.getEncoder().encodeToString(invoice.getPdfFile().getData()));
            }
            if (invoice.getExcelFile() != null) {
                mv.addObject("invoiceExcel", Base64.getEncoder().encodeToString(invoice.getExcelFile().getData()));
            }
            mv.addObject("institution", institutionName());
            mv.addObject("invoice", invoice);
            mv.setViewName("invoice");
            return mv;
        }
    }

    @RequestMapping(path = "/load/by/client", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchClient(HttpSession session,
            @PageableDefault(value = 10) Pageable pageable,
            @RequestParam String clientName
    ) {
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
        List<Invoice> invoices = this.invoiceService.findByClientProfileOrderByInvoiceIdDesc(profile);
        if (invoices == null || invoices.isEmpty()) {
            result.setMessage("Client : " + clientName + " has no invoices");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            result.setTitle("success");
            result.setMessage("Client : " + clientName + " data found");
            result.setResults(invoices);
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = "/load/by/client/department", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchClientDepartment(HttpSession session,
            @PageableDefault(value = 10) Pageable pageable,
            @RequestParam String clientName,
            @RequestParam String departmentName
    ) {
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
            result.setMessage("client invoice does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        Department dep = this.departmentService.findByDepartmentNameAndClientProfile(department, profile);
        if (dep == null) {
            result.setMessage("department invoice does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        List<Invoice> invoices = this.invoiceService.findByDepartmentOrderByInvoiceIdDesc(dep);
        if (invoices == null || invoices.isEmpty()) {
            result.setMessage("No department data found ");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            result.setTitle("success");
            result.setMessage("Department data found");
            result.setResults(invoices);
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = "/filter/monthly/by/{month}/{year}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchClientDepartmentMonthly(HttpSession session,
            @PathVariable("month") String month,
            @PathVariable("year") String year
    ) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        int yrs = Integer.valueOf(year);
        List<Invoice> invoices = this.invoiceService.findByInvoiceMonthAndInvoiceYearOrderByInvoiceIdDesc(month, yrs);
        if (invoices == null || invoices.isEmpty()) {
            result.setMessage("No data found ");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            result.setTitle("success");
            result.setMessage("Data found");
            result.setResults(invoices);
        }
        return ResponseEntity.ok(result);
    }

}
