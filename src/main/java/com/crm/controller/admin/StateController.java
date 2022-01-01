package com.crm.controller.admin;

import com.crm.controller.*;
import com.crm.generic.Nospaniol;
import com.crm.model.State;
import com.crm.model.Cancel;
import com.crm.model.State;
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
@RequestMapping("state")
public class StateController extends Nospaniol {

    @RequestMapping(value = {"add_state"}, method = RequestMethod.GET)
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
        mv.addObject("state", new State());
        mv.addObject("allStates", this.stateService.findAll());
        mv.addObject("institution", institutionName());
        mv.setViewName("addstate");
        return mv;
    }

    @RequestMapping(value = "register/{save}", method = RequestMethod.POST)
    public ModelAndView registerState(@ModelAttribute("state") State p, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        mv.setViewName("redirect:/state/add_state");
        if (p.getStateName().isEmpty()) {
            redir.addFlashAttribute("messageType", "fail");
            redir.addFlashAttribute("message", "Please enter the  state name!");
            redir.addFlashAttribute("state", p);
            return mv;
        }
        State state = this.stateService.findByStateId(p.getStateId());
        if (state == null) {
            if (this.stateService.findByStateName(p.getStateName().toUpperCase()) != null) {
                redir.addFlashAttribute("messageType", "fail");
                redir.addFlashAttribute("message", "This  state has already been registered!");
                redir.addFlashAttribute("state", p);
                return mv;
            }
            state = new State();
            state.setStateId(seqGeneratorService.generateSequence(State.SEQUENCE_NAME));
            state.setUpdatedDate(this.getCurrentDate());
            state.setCreationDate(this.getCurrentDate());
            state.setCreationTime(this.getCurrentTimestamp());
            state.setUpdatedTime(this.getCurrentTimestamp());;
        } else {
            state.setUpdatedDate(this.getCurrentDate());
            state.setUpdatedTime(this.getCurrentTimestamp());
        }
        state.setStateName(p.getStateName().toUpperCase());
        State prof = this.stateService.save(state);
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
        State state = this.stateService.findByStateId(id);
        if (state == null) {
            result.setMessage("State not found!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            this.stateService.delete(state);
            result.setMessage("State deleted!");
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
        State state = this.stateService.findByStateId(p.getCancelItem());
        if (state == null) {
            result.setMessage(" state not found!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            this.stateService.delete(state);
            result.setMessage(" state deleted!");
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
        State state = this.stateService.findByStateId(id);
        if (state == null) {
            redir.addFlashAttribute(" state does not exist");
            redir.addFlashAttribute("fail");
            mv.setViewName("addstate");
            return mv;
        } else {
            State prof = new State();
            prof.setStateName(state.getStateName());
            prof.setStateId(state.getStateId());
            prof.setStateId(state.getStateId());
            mv.addObject("allStates", this.stateService.findAll());
            mv.addObject("state", prof);
            mv.addObject("institution", institutionName());
            mv.setViewName("addstate");
            return mv;
        }
    }
}
