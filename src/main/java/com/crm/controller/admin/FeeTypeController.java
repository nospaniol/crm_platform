package com.crm.controller.admin;

import com.crm.generic.Nospaniol;
import com.crm.model.Cancel;
import com.crm.model.FeeType;
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
@RequestMapping("feetype")
public class FeeTypeController extends Nospaniol {

    @RequestMapping(value = {"add_feetype"}, method = RequestMethod.GET)
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
        mv.addObject("feeType", new FeeType());
        mv.addObject("allFeeTypes", this.feeTypeService.findAll());
        mv.addObject("institution", institutionName());
        mv.setViewName("addfeetype");
        return mv;
    }

    @RequestMapping(value = "register/{save}", method = RequestMethod.POST)
    public ModelAndView registerFeeType(@ModelAttribute("feeType") FeeType p, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        mv.setViewName("redirect:/feetype/add_feetype");
        if (p.getFeeTypeName().isEmpty()) {
            redir.addFlashAttribute("messageType", "fail");
            redir.addFlashAttribute("message", "Please enter the  feeType name!");
            redir.addFlashAttribute("feeType", p);
            return mv;
        }
        FeeType feeType = this.feeTypeService.findByFeeTypeId(p.getFeeTypeId());
        if (feeType == null) {
            if (this.feeTypeService.findByFeeTypeName(p.getFeeTypeName().toUpperCase()) != null) {
                redir.addFlashAttribute("messageType", "fail");
                redir.addFlashAttribute("message", "This  fee type has already been registered!");
                redir.addFlashAttribute("feeType", p);
                return mv;
            }
            feeType = new FeeType();
            feeType.setFeeTypeId(seqGeneratorService.generateSequence(FeeType.SEQUENCE_NAME));
            feeType.setUpdatedDate(this.getCurrentDate());
            feeType.setCreationDate(this.getCurrentDate());
            feeType.setCreationTime(this.getCurrentTimestamp());
            feeType.setUpdatedTime(this.getCurrentTimestamp());;
        } else {
            feeType.setUpdatedDate(this.getCurrentDate());
            feeType.setUpdatedTime(this.getCurrentTimestamp());
        }
        feeType.setFeeTypeName(p.getFeeTypeName().toUpperCase());
        FeeType prof = this.feeTypeService.save(feeType);
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
        FeeType feeType = this.feeTypeService.findByFeeTypeId(id);
        if (feeType == null) {
            result.setMessage("Fee type not found!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            this.feeTypeService.delete(feeType);
            result.setMessage("Fee type deleted!");
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
        FeeType feeType = this.feeTypeService.findByFeeTypeId(p.getCancelItem());
        if (feeType == null) {
            result.setMessage(" feeType not found!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            this.feeTypeService.delete(feeType);
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
        FeeType feeType = this.feeTypeService.findByFeeTypeId(id);
        if (feeType == null) {
            redir.addFlashAttribute(" feeType does not exist");
            redir.addFlashAttribute("fail");
            mv.setViewName("addfeetype");
            return mv;
        } else {
            FeeType prof = new FeeType();
            prof.setFeeTypeName(feeType.getFeeTypeName());
            prof.setFeeTypeId(feeType.getFeeTypeId());
            prof.setFeeTypeId(feeType.getFeeTypeId());
            mv.addObject("allFeeTypes", this.feeTypeService.findAll());
            mv.addObject("feeType", prof);
            mv.addObject("institution", institutionName());
            mv.setViewName("addfeetype");
            return mv;
        }
    }
}
