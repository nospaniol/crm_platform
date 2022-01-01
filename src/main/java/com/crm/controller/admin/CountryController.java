package com.crm.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.crm.generic.Nospaniol;
import com.crm.model.Country;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("country")
public class CountryController extends Nospaniol {

    @RequestMapping("/manage")
    public ModelAndView updateCountry(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        mv.addObject("country", new Country());
        mv.addObject("listCountries", this.countryService.findAll());
        mv.addObject("institution", institutionName());
        mv.setViewName("country");
        return mv;
    }

    @RequestMapping(value = "/{country-add}", method = RequestMethod.POST)
    public ModelAndView country(@ModelAttribute("country") Country p, HttpServletRequest req, RedirectAttributes redir, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        Country country = this.countryService.findByCountryName(p.getCountryName());
        if (country != null) {
            p.setCountryId(country.getCountryId());
        }
        p.setStatus(true);
        // p.setUser(getUser());
        Country newCountry = this.countryService.save(p);
        redir.addFlashAttribute("countryMessage", "Saved successful!");
        mv.setViewName("redirect:/country/manage");
        return mv;
    }

    @RequestMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        Country country = this.countryService.findByCountryId(id);
        this.countryService.delete(country);
        mv.addObject("institution", institutionName());
        redir.addFlashAttribute("countryMessage", "Deleted Successfully!");
        mv.setViewName("redirect:/country/manage");
        return mv;
    }

}
