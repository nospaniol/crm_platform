package com.crm.controller.admin;

import com.crm.generic.Nospaniol;
import com.crm.model.Cancel;
import com.crm.model.ClientProfile;
import com.crm.model.Saving;
import com.crm.model.JobResponse;
import com.crm.model.SavingUpload;
import com.crm.model.Department;
import com.crm.model.DepartmentSaving;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
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
@RequestMapping("saving")
public class SavingController extends Nospaniol {

    int counter = 0;
    int header = 0;

    @RequestMapping(value = {"view_savings"}, method = RequestMethod.GET)
    public ModelAndView viewSavings(@PageableDefault(value = 10) Pageable pageable, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
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
        this.clientProfileService.findAll().stream().filter((profile) -> (this.savingService.findByClientProfile(profile) == null)).map((profile) -> {
            Saving saving = new Saving();
            saving.setSavingId(seqGeneratorService.generateSequence(Saving.SEQUENCE_NAME));
            saving.setClientProfile(profile);
            saving.setStatus(true);
            saving.setUpdatedDate(this.getCurrentDate());
            saving.setCreationDate(this.getCurrentDate());
            saving.setCreationTime(this.getCurrentTimestamp());
            saving.setUpdatedTime(this.getCurrentTimestamp());
            return saving;
        }).map((saving) -> {
            saving.setAmount(0.00);
            return saving;
        }).forEachOrdered((saving) -> {
            this.savingService.save(saving);
        });

        mv.addObject("topic", "CLIENT ACCOUNTS");
        mv.addObject("institution", institutionName());
        Page<Saving> pages = this.savingService.findAll(pageable);
        mv.addObject("number", pages.getNumber());
        mv.addObject("totalPages", pages.getTotalPages());
        mv.addObject("totalElements", pages.getTotalElements());
        mv.addObject("size", pages.getSize());
        mv.addObject("data", pages.getContent());
        LOG.info("\n Fetching  savings \n".toUpperCase());
        mv.addObject("saving", new SavingUpload());
        mv.setViewName("viewsavings");
        return mv;
    }

    @RequestMapping("/view/department/{id}")
    public ModelAndView editSaving(@PathVariable("id") Long id, @PageableDefault(value = 10) Pageable pageable, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
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
            mv.setViewName("viewdepartmentsaving");
            return mv;
        } else {
            List<Department> departments = this.departmentService.findByClientProfile(profile);
            if (departments == null || departments.isEmpty()) {
                LOG.info("NO DEPARTMENTS FOUND : ");
            } else {
                for (Department department : departments) {
                    DepartmentSaving saving = this.departmentSavingService.findByDepartment(department);
                    if (saving == null) {
                        saving = new DepartmentSaving();
                        saving.setClientProfile(profile);
                        saving.setDepartment(department);
                        saving.setSavingId(seqGeneratorService.generateSequence(DepartmentSaving.SEQUENCE_NAME));
                        saving.setAmount(0.00);
                        saving.setStatus(true);
                        saving.setUpdatedDate(this.getCurrentDate());
                        saving.setCreationDate(this.getCurrentDate());
                        saving.setCreationTime(this.getCurrentTimestamp());
                        saving.setUpdatedTime(this.getCurrentTimestamp());
                        this.departmentSavingService.save(saving);
                    }
                }
            }
            mv.addObject("saving", new SavingUpload());
            mv.addObject("topic", "DEPARTMENTAL ACCOUNTS FOR " + profile.getCompanyName());
            mv.addObject("clientId", profile.getClientProfileId());
            Page<DepartmentSaving> pages = this.departmentSavingService.findByClientProfile(pageable, profile);
            mv.addObject("number", pages.getNumber());
            mv.addObject("totalPages", pages.getTotalPages());
            mv.addObject("totalElements", pages.getTotalElements());
            mv.addObject("size", pages.getSize());
            mv.addObject("data", pages.getContent());
            mv.addObject("institution", institutionName());
            mv.setViewName("viewdepartmentsaving");
            return mv;
        }
    }

    void loadCombos(ModelAndView mv) {
        mv.addObject("institution", institutionName());
        Map< String, String> clients = new HashMap<>();
        clients.put("", "Select a client");
        this.clientProfileService.findAll().forEach((profile) -> {
            clients.put(profile.getCompanyName(), profile.getCompanyName());
        });
        mv.addObject("clientMap", clients);

    }

    @RequestMapping(path = "saving/{delete-saving}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> deleteSaving(@ModelAttribute("cancel") Cancel p, Model model, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            result.setMessage("Administrator privileges only!!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        Saving saving = this.savingService.findBySavingId(p.getCancelItem());
        if (saving == null) {
            result.setMessage("Saving does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            this.savingService.delete(saving);
            result.setMessage("Saving deleted!");
            result.setTitle("success");
            return ResponseEntity.ok(result);
        }
    }

    @RequestMapping(path = "/load/by/client", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchClient(@PageableDefault(value = 10) Pageable pageable, HttpSession session, @RequestParam String clientName) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            result.setMessage("Administrator privileges only!!");
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

        List<Saving> savings = new ArrayList<>();
        savings.add(this.savingService.findByClientProfile(profile));
        if (savings.isEmpty()) {
            result.setMessage("Client : " + clientName + " has no savings");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            result.setTitle("success");
            result.setMessage("Client : " + clientName + " data found");
            result.setResults(savings);
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = "/deposit/{amount}/{savingId}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> depositSaving(HttpSession session, @PathVariable Double amount, @PathVariable Long savingId) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            result.setMessage("Administrator privileges only!!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        ClientProfile profile = this.clientProfileService.findByClientProfileId(savingId);
        if (profile == null) {
            result.setMessage("Client does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        Saving saving = this.savingService.findByClientProfile(profile);
        if (saving == null) {
            saving = new Saving();
            saving.setSavingId(seqGeneratorService.generateSequence(Saving.SEQUENCE_NAME));
            saving.setClientProfile(profile);
            saving.setAmount(this.getTwoDecimal(amount));
            saving.setStatus(true);
            saving.setUpdatedDate(this.getCurrentDate());
            saving.setCreationDate(this.getCurrentDate());
            saving.setCreationTime(this.getCurrentTimestamp());
            saving.setUpdatedTime(this.getCurrentTimestamp());
        } else {
            Double currentAmount = this.getTwoDecimal(saving.getAmount() + amount);
            saving.setAmount(currentAmount);
            saving.setUpdatedDate(this.getCurrentDate());
            saving.setUpdatedTime(this.getCurrentTimestamp());
        }
        this.savingService.save(saving);
        result.setTitle("success");
        result.setMessage("Saving updated successfully");
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = "/department/deposit/{amount}/{savingId}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> depositDepartmentSaving(HttpSession session, @PathVariable Double amount, @PathVariable Long savingId) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }

        DepartmentSaving saving = this.departmentSavingService.findBySavingId(savingId);
        Double currentAmount = this.getTwoDecimal(saving.getAmount() + amount);
        saving.setAmount(currentAmount);
        saving.setUpdatedDate(this.getCurrentDate());
        saving.setUpdatedTime(this.getCurrentTimestamp());
        this.departmentSavingService.save(saving);
        result.setTitle("success");
        result.setMessage("Saving updated successfully");
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = "/credit/{amount}/{savingId}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> creditSaving(HttpSession session, @PathVariable Double amount, @PathVariable Long savingId) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            result.setMessage("Administrator privileges only!!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        Saving saving = this.savingService.findBySavingId(savingId);
        Double currentAmount = this.getTwoDecimal(saving.getAmount() - amount);
        saving.setAmount(currentAmount);
        saving.setUpdatedDate(this.getCurrentDate());
        saving.setUpdatedTime(this.getCurrentTimestamp());
        this.savingService.save(saving);
        result.setTitle("success");
        result.setMessage("Saving updated successfully");
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = "/department/credit/{amount}/{savingId}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> creditDepartmentSaving(HttpSession session, @PathVariable Double amount, @PathVariable Long savingId) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            result.setMessage("Administrator privileges only!!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        DepartmentSaving saving = this.departmentSavingService.findBySavingId(savingId);
        Double currentAmount = this.getTwoDecimal(saving.getAmount() - amount);
        saving.setAmount(currentAmount);
        saving.setUpdatedDate(this.getCurrentDate());
        saving.setUpdatedTime(this.getCurrentTimestamp());
        this.departmentSavingService.save(saving);
        result.setTitle("success");
        result.setMessage("Saving updated successfully");
        return ResponseEntity.ok(result);
    }

}
