package com.crm.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.crm.generic.Nospaniol;
import com.crm.model.Nationality;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("nationality_admin")
public class NationalityController extends Nospaniol {

    @RequestMapping(value = "/{nationality-add}", method = RequestMethod.POST)
    public ModelAndView nationality(HttpSession session, @ModelAttribute("nationality") Nationality p, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        Nationality nationality = this.nationalityService.findByNation(p.getNation());
        p.setStatus(true);
        //p.setUser(getUser());
        if (nationality != null) {
            p.setNationalityId(nationality.getNationalityId());
        }
        Nationality newNationality = this.nationalityService.save(p);
        redir.addFlashAttribute("nationalityMessage ", "Saved successful!");
        redir.addFlashAttribute("nationality", newNationality);
        mv.setViewName("redirect:/administrator/system");
        redir.addFlashAttribute("listCompanys", this.companyService.findAll());
        redir.addFlashAttribute("listNationalitys", this.nationalityService.findAll());
        return mv;
    }

    @RequestMapping("/remove/{id}")
    public ModelAndView remove(HttpSession session, @PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        mv.setViewName("nationalitys");
        this.nationalityService.delete(id);
        mv.addObject("institution", institutionName());
        mv.addObject("listCompanys", this.companyService.findAll());
        mv.addObject("listNationalitys", this.nationalityService.findAll());
        mv.addObject("nationalityMessage", "Deleted Successfully!");
        mv.setViewName("redirect:/administrator/system");
        return mv;
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("nationality", this.nationalityService.find(id));
        return "redirect:/administrator/system";
    }
}
