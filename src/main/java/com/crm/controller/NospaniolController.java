package com.crm.controller;

import com.crm.generic.Nospaniol;
import static com.crm.generic.Nospaniol.LOG;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class NospaniolController extends Nospaniol {

    @RequestMapping
    public String home(Model mv, HttpSession session, RedirectAttributes redir, HttpServletRequest req) {
        try {
            req.logout();
        } catch (ServletException ex) {
            LOG.info(ex.toString());
        }
        LOG.info("SESSION DESTROYED : " + session.getId());
        session.invalidate();
        redir.getFlashAttributes().clear();
        session = null;
        mv.addAttribute("institution", institutionName());
        return "user-logout";
    }

    @RequestMapping("/cache.manifest")
    public ModelAndView cacheManifest(RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        try {
            req.logout();
        } catch (ServletException ex) {
            LOG.info(ex.toString());
        }
        String userMail = (String) session.getAttribute("ns_mail_address");
        session.invalidate();
        redir.getFlashAttributes().clear();
        session = null;
        mv.addObject("institution", institutionName());
        mv.setViewName("cache.manifest");
        return mv;
    }

    @RequestMapping("/login")
    public ModelAndView login(RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        try {
            req.logout();
        } catch (ServletException ex) {
            LOG.info(ex.toString());
        }
        String userMail = (String) session.getAttribute("ns_mail_address");
        session.invalidate();
        redir.getFlashAttributes().clear();
        session = null;
        mv.addObject("institution", institutionName());
        mv.setViewName("welcome");
        return mv;
    }

    @RequestMapping("/notfound")
    public ModelAndView errorPage() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", "error occured, the support team have been notified, please try again later!");
        mv.addObject("institution", institutionName());
        mv.setViewName("error");
        return mv;
    }

    @RequestMapping("/unauthorised")
    public ModelAndView unAuthorisedPage() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", "unauthorised access");
        mv.addObject("institution", institutionName());
        mv.setViewName("unauthorised");
        return mv;
    }

}
