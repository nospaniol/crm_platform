package com.crm.controller.admin;

import com.crm.generic.Nospaniol;
import com.crm.model.Cancel;
import com.crm.model.Axle;
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
@RequestMapping("axle")
public class AxleController extends Nospaniol {

    @RequestMapping(value = {"add_axle"}, method = RequestMethod.GET)
    public String addClients(Model mv, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
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
        mv.addAttribute("axle", new Axle());
        mv.addAttribute("allAxles", this.axleService.findAll());
        mv.addAttribute("institution", institutionName());
        return "addaxle";
    }

    @RequestMapping(value = "register/{save}", method = RequestMethod.POST)
    public ModelAndView registerAxle(@ModelAttribute("axle") Axle p, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
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
        mv.setViewName("redirect:/axle/add_axle");
        if (p.getAxleName().isEmpty()) {
            redir.addFlashAttribute("messageType", "fail");
            redir.addFlashAttribute("message", "Please enter the axle name!");
            redir.addFlashAttribute("axle", p);
            return mv;
        }
        Axle axle = this.axleService.findByAxleId(p.getAxleId());
        if (axle == null) {
            if (this.axleService.findByAxleName(p.getAxleName().toUpperCase()) != null) {
                redir.addFlashAttribute("messageType", "fail");
                redir.addFlashAttribute("message", "This axle has already been registered!");
                redir.addFlashAttribute("axle", p);
                return mv;
            }
            axle = new Axle();
            axle.setAxleId(seqGeneratorService.generateSequence(Axle.SEQUENCE_NAME));
            axle.setUpdatedDate(this.getCurrentDate());
            axle.setCreationDate(this.getCurrentDate());
            axle.setCreationTime(this.getCurrentTimestamp());
            axle.setUpdatedTime(this.getCurrentTimestamp());
        } else {
            axle.setUpdatedDate(this.getCurrentDate());
            axle.setUpdatedTime(this.getCurrentTimestamp());
        }
        axle.setAxleName(p.getAxleName().toUpperCase());
        Axle newAxle = this.axleService.save(axle);
        redir.addFlashAttribute("institution", institutionName());
        redir.addFlashAttribute("messageType", "success");
        redir.addFlashAttribute("message", "Successfully updated");
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
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            result.setMessage("Administrator privileges only!!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        Axle axle = this.axleService.findByAxleId(p.getCancelItem());
        if (axle == null) {
            result.setMessage("Axle not found!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            this.axleService.delete(axle);
            result.setMessage("Axle deleted!");
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
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Administrator privileges only!");
            return mv;
        }
        Axle axle = this.axleService.findByAxleId(id);
        if (axle == null) {
            redir.addFlashAttribute("Axle does not exist");
            redir.addFlashAttribute("fail");
            mv.setViewName("addaxle");
            return mv;
        } else {
            Axle prof = new Axle();
            prof.setAxleName(axle.getAxleName());
            prof.setAxleId(axle.getAxleId());
            prof.setAxleId(axle.getAxleId());
            mv.addObject("allAxles", this.axleService.findAll());
            mv.addObject("axle", prof);
            mv.addObject("institution", institutionName());
            mv.setViewName("addaxle");
            return mv;
        }
    }
}
