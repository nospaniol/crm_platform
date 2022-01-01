package com.crm.controller.admin;

import com.crm.controller.*;
import com.crm.generic.Nospaniol;
import com.crm.model.Cancel;
import com.crm.model.StoreLocation;
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
@RequestMapping("store")
public class StoreLocationController extends Nospaniol {

    @RequestMapping(value = {"add_location"}, method = RequestMethod.GET)
    public String addClients(Model mv, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            mv.addAttribute("messageType", "success");
            mv.addAttribute("message", "Administrator privileges only!");
            return "user-logout";
        }
        mv.addAttribute("storeLocation", new StoreLocation());
        mv.addAttribute("allStoreLocations", this.storeLocationService.findAll());
        mv.addAttribute("institution", institutionName());
        return "addstorelocation";
    }

    @RequestMapping(value = "register/{save}", method = RequestMethod.POST)
    public ModelAndView registerStoreLocation(@ModelAttribute("storeLocation") StoreLocation p, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
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
        mv.setViewName("redirect:/store/add_location");
        if (p.getStoreLocationName().isEmpty()) {
            redir.addFlashAttribute("messageType", "fail");
            redir.addFlashAttribute("message", "Please enter the store location name!");
            redir.addFlashAttribute("storeLocation", p);
            return mv;
        }
        StoreLocation storeLocation = this.storeLocationService.findByStoreLocationId(p.getStoreLocationId());
        if (storeLocation == null) {
            if (this.storeLocationService.findByStoreLocationName(p.getStoreLocationName().toUpperCase()) != null) {
                redir.addFlashAttribute("messageType", "fail");
                redir.addFlashAttribute("message", "This store location has already been registered!");
                redir.addFlashAttribute("storeLocation", p);
                return mv;
            }
            storeLocation = new StoreLocation();
            storeLocation.setStoreLocationId(seqGeneratorService.generateSequence(StoreLocation.SEQUENCE_NAME));
            storeLocation.setUpdatedDate(this.getCurrentDate());
            storeLocation.setCreationDate(this.getCurrentDate());
            storeLocation.setCreationTime(this.getCurrentTimestamp());
            storeLocation.setUpdatedTime(this.getCurrentTimestamp());;
        } else {
            storeLocation.setUpdatedDate(this.getCurrentDate());
            storeLocation.setUpdatedTime(this.getCurrentTimestamp());
        }
        storeLocation.setStoreLocationName(p.getStoreLocationName().toUpperCase());
        StoreLocation prof = this.storeLocationService.save(storeLocation);
        mv.addObject("institution", institutionName());
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
        StoreLocation storeLocation = this.storeLocationService.findByStoreLocationId(p.getCancelItem());
        if (storeLocation == null) {
            result.setMessage("Store location not found!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            this.storeLocationService.delete(storeLocation);
            result.setMessage("Store location deleted!");
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
        StoreLocation storeLocation = this.storeLocationService.findByStoreLocationId(id);
        if (storeLocation == null) {
            redir.addFlashAttribute("Store location does not exist");
            redir.addFlashAttribute("fail");
            mv.setViewName("addstorelocation");
            return mv;
        } else {
            StoreLocation prof = new StoreLocation();
            prof.setStoreLocationName(storeLocation.getStoreLocationName());
            prof.setStoreLocationId(storeLocation.getStoreLocationId());
            prof.setStoreLocationId(storeLocation.getStoreLocationId());
            mv.addObject("allStoreLocations", this.storeLocationService.findAll());
            mv.addObject("storeLocation", prof);
            mv.addObject("institution", institutionName());
            mv.setViewName("addstorelocation");
            return mv;
        }
    }
}
