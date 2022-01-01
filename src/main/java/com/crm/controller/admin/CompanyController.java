package com.crm.controller.admin;

import com.crm.controller.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.crm.generic.Nospaniol;
import com.crm.model.Company;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("company_admin")
public class CompanyController extends Nospaniol {

    @RequestMapping(value = "/{company-add}", method = RequestMethod.POST)
    public ModelAndView company(HttpSession session, @ModelAttribute("company") Company p, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        Company company = this.companyService.findByCompanyId(Long.valueOf(1));
        if (company == null) {
            company = new Company();
            company.setCompanyId(Long.valueOf(1));
            company.setCompanyName(p.getCompanyName());
            company.setCompanyMotto(p.getCompanyMotto());
            company.setCompanyLogo(p.getCompanyLogo());
            company.setStatus(true);
            //company.setUser(getUser());
            Company newCompany = this.companyService.save(company);
            redir.addFlashAttribute("companyMessage", "Saved successful!");
            redir.addFlashAttribute("company", newCompany);
            mv.setViewName("redirect:/administrator/system");
        } else {
            company.setCompanyName(p.getCompanyName());
            company.setCompanyMotto(p.getCompanyMotto());
            company.setCompanyLogo(p.getCompanyLogo());
            company.setStatus(true);
            //company.setUser(getUser());
            Company newCompany = this.companyService.save(company);
            redir.addFlashAttribute("companyMessage", "Saved successful!");
            redir.addFlashAttribute("company", newCompany);
            mv.setViewName("redirect:/administrator/system");
        }
        mv.addObject("institution", institutionName());
        redir.addFlashAttribute("listCompanys", this.companyService.findAll());
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
        mv.setViewName("companys");
        this.companyService.delete(id);
        mv.addObject("institution", institutionName());
        mv.addObject("listCompanys", this.companyService.findAll());
        mv.addObject("companyMessage", "Deleted Successfully!");
        mv.setViewName("redirect:/administrator/system");
        return mv;
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("company", this.companyService.find(id));
        return "redirect:/administrator/system";
    }
}
