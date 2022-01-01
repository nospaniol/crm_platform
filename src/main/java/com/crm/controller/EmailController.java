package com.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.crm.generic.Nospaniol;
import com.crm.model.User;
import com.crm.model.PhoneToken;
import com.crm.model.EmailToken;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("email")
public class EmailController extends Nospaniol {

    @PostMapping(value = "/{validate}")
    public ModelAndView validateEmail(@ModelAttribute("emailToken") EmailToken p, HttpServletRequest req, RedirectAttributes redir, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        String ns_otp = (String) session.getAttribute("otp");
        String ns_eotp = (String) session.getAttribute("eotp");
        redir.addFlashAttribute("eotp", ns_otp);
        redir.addFlashAttribute("otp", ns_eotp);
        EmailToken token = emailTokenService.findByEmailAddress(p.getEmailAddress().toLowerCase());
        User user = this.userService.findByEmailToken(token);
        PhoneToken etoken = user.getPhoneToken();
        if (token.getEmailOtp().equals(this.getHashed(p.getEmailOtp()))) {
            token.setStatus(true);
            emailTokenService.update(token);
            redir.addFlashAttribute("loggedMail", p.getEmailAddress().toLowerCase());
            redir.addFlashAttribute("loggedNumber", user.getPhone());
            String otp = this.generateOtp();
            boolean sendEmailStatus = true;
            if (!token.isStatus()) {
                redir.addFlashAttribute("emailVerificationStatus", "emailNotVerified");
                token.setEmailOtp(this.getHashed(otp));
                token.setStatus(false);
                user.setEmailToken(this.emailTokenService.save(token));
                userService.save(user);
                sendEmailStatus = this.sendText(user.getEmail(), "Hi " + user.getFirstName() + " , your innovative OTP : " + otp);
                redir.addFlashAttribute("eotp", otp);
                redir.addFlashAttribute("emailMessage", "Please verify contacts!");
            } else {
                redir.addFlashAttribute("emailVerificationStatus", "emailVerified");
            }
            if (!etoken.isStatus()) {
                redir.addFlashAttribute("phoneVerificationStatus", "phoneNotVerified");
            } else {
                redir.addFlashAttribute("phoneVerificationStatus", "phoneVerified");
            }
        } else {
            redir.addFlashAttribute("emailMessage", "Wrong OTP added!");
            redir.addFlashAttribute("emailToken", new EmailToken());
        }
        mv.setViewName("redirect:/User/verify");
        return mv;
    }

}
