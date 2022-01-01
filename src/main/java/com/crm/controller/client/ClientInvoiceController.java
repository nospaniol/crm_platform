package com.crm.controller.client;

import com.crm.generic.Nospaniol;
import com.crm.model.Invoice;
import com.crm.model.InvoiceForm;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.JobResponse;
import com.crm.model.TransactionUpload;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("client_invoice")
public class ClientInvoiceController extends Nospaniol {

    @RequestMapping("/view_invoice")
    public ModelAndView viewInvoices(@PageableDefault(value = 100) Pageable pageable, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "invoice");
        mv.addObject("transaction", new TransactionUpload());
        mv.addObject("institution", institutionName());
        mv.addObject("topic", "Recently added invoices");
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

                    mv.addObject("allInvoices", this.invoiceService.findByDepartmentOrderByInvoiceIdDesc(department));
                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    if (profile == null) {
                        mv.addObject("message", "Client profile not found");
                        mv.addObject("messageType", "fail");
                        mv.setViewName("clientspending");
                        return mv;
                    }
                    //mv.addObject("institution", profile.getCompanyName());

                    mv.addObject("allInvoices", this.invoiceService.findByClientProfileOrderByInvoiceIdDesc(profile));
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

                mv.addObject("allInvoices", this.invoiceService.findByDepartmentOrderByInvoiceIdDesc(department));
                break;
            default:
                LOG.info("USER ACCOUNT UNKNOWN");
        }
        mv.setViewName("clientviewinvoices");
        return mv;
    }

    @RequestMapping("/monthly_invoice")
    public ModelAndView monthlyInvoices(@PageableDefault(value = 100) Pageable pageable, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "invoice");
        LocalDate today = LocalDate.now();
        String month = today.getMonth().toString();
        int year = Integer.valueOf((String) session.getAttribute("year_mode"));
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

                    mv.addObject("topic", department.getDepartmentName() + " Invoices");
                    Page<Invoice> pages = this.invoiceService.findByDepartmentOrderByInvoiceIdDesc(pageable, department);
                    mv.addObject("number", pages.getNumber());
                    mv.addObject("totalPages", pages.getTotalPages());
                    mv.addObject("totalElements", pages.getTotalElements());
                    mv.addObject("size", pages.getSize());
                    mv.addObject("allInvoices", pages.getContent());
                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    if (profile == null) {
                        mv.addObject("message", "Client profile not found");
                        mv.addObject("messageType", "fail");
                        mv.setViewName("clientspending");
                        return mv;
                    }

                    //mv.addObject("institution", profile.getCompanyName());
                    mv.addObject("topic", profile.getCompanyName() + " " + month + " / " + String.valueOf(year) + " Invoices");
                    Page<Invoice> pages = this.invoiceService.findByClientProfileOrderByInvoiceIdDesc(pageable, profile);
                    mv.addObject("number", pages.getNumber());
                    mv.addObject("totalPages", pages.getTotalPages());
                    mv.addObject("totalElements", pages.getTotalElements());
                    mv.addObject("size", pages.getSize());
                    mv.addObject("allInvoices", pages.getContent());
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

                mv.addObject("topic", department.getDepartmentName() + " " + month + " / " + String.valueOf(year) + " Invoices");

                Page<Invoice> pages = this.invoiceService.findByDepartmentOrderByInvoiceIdDesc(pageable, department);
                mv.addObject("number", pages.getNumber());
                mv.addObject("totalPages", pages.getTotalPages());
                mv.addObject("totalElements", pages.getTotalElements());
                mv.addObject("size", pages.getSize());
                mv.addObject("allInvoices", pages.getContent());
                break;
            default:
                LOG.info("USER ACCOUNT UNKNOWN");
        }
        mv.setViewName("clientmonthlyinvoices");
        return mv;
    }

    @RequestMapping(value = "/all/added/invoices", method = RequestMethod.GET)
    public ModelAndView allInvoices(@PageableDefault(value = 10) Pageable pageable, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "invoice");
        mv.addObject("invoice", new InvoiceForm());
        mv.addObject("topic", "All invoices");
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

                    Page<Invoice> pages = this.invoiceService.findByDepartmentOrderByInvoiceIdDesc(pageable, department);
                    mv.addObject("number", pages.getNumber());
                    mv.addObject("totalPages", pages.getTotalPages());
                    mv.addObject("totalElements", pages.getTotalElements());
                    mv.addObject("size", pages.getSize());
                    mv.addObject("allInvoices", pages.getContent());
                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    if (profile == null) {
                        mv.addObject("message", "Client not found");
                        mv.addObject("messageType", "fail");
                        mv.setViewName("clientspending");
                        return mv;
                    }

                    //mv.addObject("institution", profile.getCompanyName());
                    Page<Invoice> pages = this.invoiceService.findByClientProfile(pageable, profile);
                    mv.addObject("number", pages.getNumber());
                    mv.addObject("totalPages", pages.getTotalPages());
                    mv.addObject("totalElements", pages.getTotalElements());
                    mv.addObject("size", pages.getSize());
                    mv.addObject("allInvoices", pages.getContent());
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

                Page<Invoice> pages = this.invoiceService.findByDepartmentOrderByInvoiceIdDesc(pageable, department);
                mv.addObject("number", pages.getNumber());
                mv.addObject("totalPages", pages.getTotalPages());
                mv.addObject("totalElements", pages.getTotalElements());
                mv.addObject("size", pages.getSize());
                mv.addObject("allInvoices", pages.getContent());
                break;
            default:
                LOG.info("USER ACCOUNT UNKNOWN");
        }
        mv.setViewName("allclientinvoices");
        return mv;
    }

    @RequestMapping("/view/{id}")
    public ModelAndView viewProfile(@PathVariable("id") Long id, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "invoice");
        Invoice invoice = this.invoiceService.findByInvoiceId(id);
        if (invoice == null) {
            redir.addFlashAttribute("message", "No invoice found");
            redir.addFlashAttribute("messageType", "fail");
            mv.setViewName("redirect:/invoice/new_invoice/");
            return mv;
        } else {
            ClientProfile profile = invoice.getClientProfile();

            if (invoice.getPdfFile() != null) {
                mv.addObject("invoicePdf", Base64.getEncoder().encodeToString(invoice.getPdfFile().getData()));
            }
            if (invoice.getExcelFile() != null) {
                mv.addObject("invoiceExcel", Base64.getEncoder().encodeToString(invoice.getExcelFile().getData()));
            }
            mv.addObject("institution", institutionName());
            mv.addObject("invoice", invoice);
            mv.setViewName("clientinvoice");
            return mv;
        }
    }

    @RequestMapping(path = "/load/by/client", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchClient(HttpSession session) {
        JobResponse result = new JobResponse();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
        if (profile == null) {
            result.setMessage("Client does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        List<Invoice> invoices = this.invoiceService.findByClientProfileOrderByInvoiceIdDesc(profile);
        if (invoices == null || invoices.isEmpty()) {
            result.setMessage("Client : " + profile.getCompanyName() + " has no invoices");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            result.setTitle("success");
            result.setMessage("Client : " + profile.getCompanyName() + " data found");
            result.setResults(invoices);
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = "/load/by/client/department", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchClientDepartment(@RequestParam String departmentName, HttpSession session) {
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
                String departmentMode = (String) session.getAttribute("department_mode");
                String departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                    List<Invoice> invoices = this.invoiceService.findByDepartmentOrderByInvoiceIdDesc(department);
                    if (invoices == null || invoices.isEmpty()) {
                        result.setMessage("No department data found ");
                        result.setTitle("fail");
                        return ResponseEntity.ok(result);
                    } else {
                        result.setTitle("success");
                        result.setMessage("Department data found");
                        result.setResults(invoices);
                    }
                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    if (profile == null) {
                        result.setMessage("client invoice does not exist");
                        result.setTitle("fail");
                        return ResponseEntity.ok(result);
                    }
                    Department department = this.departmentService.findByDepartmentName(departmentName);
                    List<Invoice> invoices = this.invoiceService.findByDepartmentOrderByInvoiceIdDesc(department);
                    if (invoices == null || invoices.isEmpty()) {
                        result.setMessage("No department data found ");
                        result.setTitle("fail");
                        return ResponseEntity.ok(result);
                    } else {
                        result.setTitle("success");
                        result.setMessage("Department data found");
                        result.setResults(invoices);
                    }
                }
                break;
            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                List<Invoice> invoices = this.invoiceService.findByDepartmentOrderByInvoiceIdDesc(department);
                if (invoices == null || invoices.isEmpty()) {
                    result.setMessage("No department data found ");
                    result.setTitle("fail");
                    return ResponseEntity.ok(result);
                } else {
                    result.setTitle("success");
                    result.setMessage("Department data found");
                    result.setResults(invoices);
                }
                break;
            default:
                LOG.info("USER ACCOUNT UNKNOWN");
        }

        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = "/filter/monthly/by/{month}/{year}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchClientDepartmentMonthly(HttpSession session, @PathVariable("month") String month, @PathVariable("year") String year) {
        JobResponse result = new JobResponse();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        int yrs = Integer.valueOf(year);
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                String departmentMode = (String) session.getAttribute("department_mode");
                String departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                    List<Invoice> invoices = this.invoiceService.findByDepartmentAndInvoiceMonthAndInvoiceYearOrderByInvoiceIdDesc(department, month, yrs);
                    if (invoices == null || invoices.isEmpty()) {
                        result.setMessage("No data found ");
                        result.setTitle("fail");
                        return ResponseEntity.ok(result);
                    } else {
                        result.setTitle("success");
                        result.setMessage("Data found");
                        result.setResults(invoices);
                    }
                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    if (profile == null) {
                        result.setMessage("client invoice does not exist");
                        result.setTitle("fail");
                        return ResponseEntity.ok(result);
                    }
                    List<Invoice> invoices = this.invoiceService.findByClientProfileAndInvoiceMonthAndInvoiceYearOrderByInvoiceIdDesc(profile, month, yrs);
                    if (invoices == null || invoices.isEmpty()) {
                        result.setMessage("No data found ");
                        result.setTitle("fail");
                        return ResponseEntity.ok(result);
                    } else {
                        result.setTitle("success");
                        result.setMessage("Data found");
                        result.setResults(invoices);
                    }
                }
                break;
            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                List<Invoice> invoices = this.invoiceService.findByDepartmentAndInvoiceMonthAndInvoiceYearOrderByInvoiceIdDesc(department, month, yrs);
                if (invoices == null || invoices.isEmpty()) {
                    result.setMessage("No data found ");
                    result.setTitle("fail");
                    return ResponseEntity.ok(result);
                } else {
                    result.setTitle("success");
                    result.setMessage("Data found");
                    result.setResults(invoices);
                }
                break;
            default:
                LOG.info("USER ACCOUNT UNKNOWN");
        }
        return ResponseEntity.ok(result);
    }

}
