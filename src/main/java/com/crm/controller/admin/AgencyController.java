package com.crm.controller.admin;

import com.crm.generic.Nospaniol;
import com.crm.model.Cancel;
import com.crm.model.Agency;
import com.crm.model.JobResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("agency")
public class AgencyController extends Nospaniol {

    @RequestMapping(value = {"add_agency"}, method = RequestMethod.GET)
    public ModelAndView addClients(RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
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
        mv.addObject("agency", new Agency());
        mv.addObject("allAgencys", this.agencyService.findAll());
        mv.addObject("institution", institutionName());
        mv.setViewName("addagency");
        return mv;
    }

    @RequestMapping(value = "register/{save}", method = RequestMethod.POST)
    public ModelAndView registerAgency(@ModelAttribute("agency") Agency p, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        mv.setViewName("redirect:/agency/add_agency");
        if (p.getAgencyName().isEmpty()) {
            redir.addFlashAttribute("messageType", "fail");
            redir.addFlashAttribute("message", "Please enter the  agency name!");
            redir.addFlashAttribute("agency", p);
            return mv;
        }
        Agency agency = this.agencyService.findByAgencyId(p.getAgencyId());
        if (agency == null) {
            if (this.agencyService.findByAgencyName(p.getAgencyName().toUpperCase()) != null) {
                redir.addFlashAttribute("messageType", "fail");
                redir.addFlashAttribute("message", "This  agency has already been registered!");
                redir.addFlashAttribute("agency", p);
                return mv;
            }
            agency = new Agency();
            agency.setAgencyId(seqGeneratorService.generateSequence(Agency.SEQUENCE_NAME));
            agency.setUpdatedDate(this.getCurrentDate());
            agency.setCreationDate(this.getCurrentDate());
            agency.setCreationTime(this.getCurrentTimestamp());
            agency.setUpdatedTime(this.getCurrentTimestamp());;
        } else {
            agency.setUpdatedDate(this.getCurrentDate());
            agency.setUpdatedTime(this.getCurrentTimestamp());
        }
        agency.setAgencyName(p.getAgencyName().toUpperCase());
        Agency prof = this.agencyService.save(agency);
        mv.addObject("institution", institutionName());
        redir.addFlashAttribute("messageType", "success");
        redir.addFlashAttribute("message", "Successfully updated");
        return mv;

    }

    @RequestMapping(path = "delete/{id}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> deleteAccount(@PathVariable("id") Long id, HttpSession session) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        Agency agency = this.agencyService.findByAgencyId(id);
        if (agency == null) {
            result.setMessage("Agency not found!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            this.agencyService.delete(agency);
            result.setMessage("Agency deleted!");
            result.setTitle("success");
            return ResponseEntity.ok(result);
        }
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
        Agency agency = this.agencyService.findByAgencyId(p.getCancelItem());
        if (agency == null) {
            result.setMessage(" agency not found!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            this.agencyService.delete(agency);
            result.setMessage(" agency deleted!");
            result.setTitle("success");
            return ResponseEntity.ok(result);
        }
    }

    @RequestMapping("edit/{id}")
    public ModelAndView editAccount(@PathVariable("id") Long id, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        Agency agency = this.agencyService.findByAgencyId(id);
        if (agency == null) {
            redir.addFlashAttribute(" agency does not exist");
            redir.addFlashAttribute("fail");
            mv.setViewName("addagency");
            return mv;
        } else {
            Agency prof = new Agency();
            prof.setAgencyName(agency.getAgencyName());
            prof.setAgencyId(agency.getAgencyId());
            prof.setAgencyId(agency.getAgencyId());
            mv.addObject("allAgencys", this.agencyService.findAll());
            mv.addObject("agency", prof);
            mv.addObject("institution", institutionName());
            mv.setViewName("addagency");
            return mv;
        }
    }
}
