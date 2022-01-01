package com.crm.controller.admin;

import com.crm.generic.Nospaniol;
import com.crm.model.Cancel;
import com.crm.model.CitationType;
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
@RequestMapping("citationtype")
public class CitationTypeController extends Nospaniol {

    @RequestMapping(value = {"add_citationtype"}, method = RequestMethod.GET)
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
        mv.addObject("citationType", new CitationType());
        mv.addObject("allCitationTypes", this.citationTypeService.findAll());
        mv.addObject("institution", institutionName());
        mv.setViewName("addcitationtype");
        return mv;
    }

    @RequestMapping(value = "register/{save}", method = RequestMethod.POST)
    public ModelAndView registerCitationType(@ModelAttribute("citationType") CitationType p, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        mv.setViewName("redirect:/citationtype/add_citationtype");
        if (p.getCitationTypeName().isEmpty()) {
            redir.addFlashAttribute("messageType", "fail");
            redir.addFlashAttribute("message", "Please enter the  citationType name!");
            redir.addFlashAttribute("citationType", p);
            return mv;
        }
        CitationType citationType = this.citationTypeService.findByCitationTypeId(p.getCitationTypeId());
        if (citationType == null) {
            if (this.citationTypeService.findByCitationTypeName(p.getCitationTypeName().toUpperCase()) != null) {
                redir.addFlashAttribute("messageType", "fail");
                redir.addFlashAttribute("message", "This  fee type has already been registered!");
                redir.addFlashAttribute("citationType", p);
                return mv;
            }
            citationType = new CitationType();
            citationType.setCitationTypeId(seqGeneratorService.generateSequence(CitationType.SEQUENCE_NAME));
            citationType.setUpdatedDate(this.getCurrentDate());
            citationType.setCreationDate(this.getCurrentDate());
            citationType.setCreationTime(this.getCurrentTimestamp());
            citationType.setUpdatedTime(this.getCurrentTimestamp());;
        } else {
            citationType.setUpdatedDate(this.getCurrentDate());
            citationType.setUpdatedTime(this.getCurrentTimestamp());
        }
        citationType.setCitationTypeName(p.getCitationTypeName().toUpperCase());
        CitationType prof = this.citationTypeService.save(citationType);
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
        CitationType citationType = this.citationTypeService.findByCitationTypeId(id);
        if (citationType == null) {
            result.setMessage("Citation type not found!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            this.citationTypeService.delete(citationType);
            result.setMessage("Citation type deleted!");
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
        CitationType citationType = this.citationTypeService.findByCitationTypeId(p.getCancelItem());
        if (citationType == null) {
            result.setMessage(" citationType not found!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            this.citationTypeService.delete(citationType);
            result.setMessage(" fee type deleted!");
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
        CitationType citationType = this.citationTypeService.findByCitationTypeId(id);
        if (citationType == null) {
            redir.addFlashAttribute(" citationType does not exist");
            redir.addFlashAttribute("fail");
            mv.setViewName("addcitationtype");
            return mv;
        } else {
            CitationType prof = new CitationType();
            prof.setCitationTypeName(citationType.getCitationTypeName());
            prof.setCitationTypeId(citationType.getCitationTypeId());
            prof.setCitationTypeId(citationType.getCitationTypeId());
            mv.addObject("allCitationTypes", this.citationTypeService.findAll());
            mv.addObject("citationType", prof);
            mv.addObject("institution", institutionName());
            mv.setViewName("addcitationtype");
            return mv;
        }
    }
}
