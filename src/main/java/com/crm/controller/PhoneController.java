package com.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.crm.generic.Nospaniol;
import com.crm.model.User;
import com.crm.model.EmailToken;
import com.crm.model.PhoneToken;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("phone")
public class PhoneController extends Nospaniol {

    @PostMapping(value = "/{validate}")
    public ModelAndView validatePhone(@ModelAttribute("phoneToken") PhoneToken p, HttpServletRequest req, RedirectAttributes redir, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        String ns_otp = (String) session.getAttribute("otp");
        String ns_eotp = (String) session.getAttribute("eotp");
        redir.addFlashAttribute("eotp", ns_otp);
        redir.addFlashAttribute("otp", ns_eotp);
        PhoneToken token = phoneTokenService.findByPhoneNumber(p.getPhoneNumber());
        User user = this.userService.findByPhoneToken(token);
        EmailToken etoken = user.getEmailToken();
        if (token.getPhoneOtp().equals(this.getHashed(p.getPhoneOtp()))) {
            token.setStatus(true);
            phoneTokenService.update(token);
            redir.addFlashAttribute("loggedUser", p.getPhoneNumber());
            redir.addFlashAttribute("loggedMail", user.getEmail());
            String otp = this.generateOtp();
            boolean sendPhoneStatus = true;
            if (!token.isStatus()) {
                redir.addFlashAttribute("phoneVerificationStatus", "phoneNotVerified");
                token.setPhoneOtp(this.getHashed(otp));
                token.setStatus(false);
                user.setPhoneToken(this.phoneTokenService.save(token));
                userService.save(user);
                sendPhoneStatus = this.sendText(user.getPhone(), "Hi " + user.getFirstName() + " , your innovative OTP : " + otp);
                redir.addFlashAttribute("otp", otp);
                redir.addFlashAttribute("phoneMessage", "Please verify contacts!");
            } else {
                redir.addFlashAttribute("phoneVerificationStatus", "phoneVerified");
            }
            if (!etoken.isStatus()) {
                redir.addFlashAttribute("emailVerificationStatus", "emailNotVerified");
            } else {
                redir.addFlashAttribute("emailVerificationStatus", "emailVerified");
            }
        } else {
            redir.addFlashAttribute("phoneMessage", "Wrong OTP added!");
            redir.addFlashAttribute("phoneToken", new PhoneToken());
        }
        mv.setViewName("redirect:/User/verify");
        return mv;
    }

}
