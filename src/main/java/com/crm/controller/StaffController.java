package com.crm.controller;

import com.crm.generic.Nospaniol;
import com.crm.model.Authority;
import com.crm.model.PhoneToken;
import com.crm.model.User;
import com.crm.model.EmailToken;
import com.crm.model.JobResponse;
import com.crm.model.Search;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("staff")
public class StaffController extends Nospaniol {

    @RequestMapping(value = "/register/{addUser}", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@ModelAttribute("user") User p, RedirectAttributes redir, HttpServletRequest req) {
        JobResponse result = new JobResponse();
        if (p == null) {
            result.setMessage("Fill all required fields!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            User doc = this.userService.findByEmailToken(this.emailTokenService.findByEmailAddress(p.getEmailAddress().toLowerCase()));
            User dc = this.userService.findByPhoneToken(this.phoneTokenService.findByPhoneNumber(p.getPhoneNumber()));
            if (doc != null) {
                result.setMessage("Email address :: " + p.getEmailAddress().toLowerCase() + " :: is already registered!");
                result.setTitle("fail");
                return ResponseEntity.ok(result);
            }
            if (dc != null) {
                result.setMessage("Phone number :: " + p.getPhoneNumber() + " ::  is already registered!");
                result.setTitle("fail");
                return ResponseEntity.ok(result);
            }
            p.setUserId(seqGeneratorService.generateSequence(User.SEQUENCE_NAME));
            p.setStatus(true);
            String pass = this.getHashed(p.getPassword());
            p.setPassword(pass);
            String otp = this.generateOtp();
            String eotp = this.generateOtp();
            PhoneToken token = new PhoneToken();
            token.setPhoneNumber(p.getPhoneNumber());
            token.setPhoneOtp(this.getHashed(otp));
            token.setStatus(true);
            token.setPhoneTokenId(seqGeneratorService.generateSequence(PhoneToken.SEQUENCE_NAME));
            PhoneToken phone = this.phoneTokenService.save(token);
            EmailToken etoken = new EmailToken();
            etoken.setEmailAddress(p.getEmailAddress().toLowerCase());
            etoken.setEmailOtp(this.getHashed(eotp));
            etoken.setStatus(true);
            etoken.setEmailTokenId(seqGeneratorService.generateSequence(EmailToken.SEQUENCE_NAME));
            EmailToken email = this.emailTokenService.save(etoken);
            p.setPhoneToken(phone);
            p.setEmailToken(email);
            Authority authority = authorityService.findByRole("STAFF");
            p.setAuthority(authority);
            User user = userService.save(p);
            result.setRole(user.getAuthority().getRole());
            result.setTitle("success");
            result.setMessage("Registration successful.");
            return ResponseEntity.ok(result);
        }
    }

    @RequestMapping(path = "/available/check_availability", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchUsername(@ModelAttribute("search") Search search) {
        JobResponse result = new JobResponse();
        String username = search.getSearchItem();
        User user = this.userService.findByEmailToken(this.emailTokenService.findByEmailAddress(username));
        if (user == null) {
            result.setTitle("available");
            result.setMessage("Good news, the username is available!");
        } else {
            result.setTitle("booked");
            result.setMessage("Sorry, The username is already taken");
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView remove(@PathVariable("id") Long id, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        User user = this.userService.findByUserId(id);
        if (user == null) {
            mv.addObject("User account does not exist");
            mv.addObject("fail");
            mv.setViewName("view_staff");
            return mv;
        } else {
            mv.addObject("institution", institutionName());
            mv.addObject("user", user);
            mv.setViewName("user-edit-account");
            return mv;
        }
    }

    @RequestMapping(value = "update/{user_account}", method = RequestMethod.POST)
    public ModelAndView updateUserAccount(@ModelAttribute("user") User p, HttpServletRequest req, RedirectAttributes redir, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        String otp = this.generateOtp();
        String eotp = this.generateOtp();
        User currentUser = this.userService.findByUserId(p.getUserId());
        PhoneToken ptoken = currentUser.getPhoneToken();
        EmailToken eetoken = currentUser.getEmailToken();
        if (ptoken == null) {
            mv.addObject("message", "Phone token retrieved is null");
            mv.setViewName("user-edit-account");
            return mv;
        }
        if (eetoken == null) {
            mv.addObject("message", "Email token retrieved is null");
            mv.setViewName("user-edit-account");
            return mv;
        }
        if (!ptoken.getPhoneNumber().equals(p.getPhoneToken().getPhoneNumber())) {
            PhoneToken token = new PhoneToken();
            token.setPhoneNumber(p.getPhoneToken().getPhoneNumber());
            token.setPhoneOtp(this.getHashed(otp));
            token.setStatus(true);
            token.setPhoneTokenId(ptoken.getPhoneTokenId());
            PhoneToken phone = this.phoneTokenService.save(token);
            p.setPhoneToken(phone);
        } else {
            p.setPhoneToken(ptoken);
        }
        if (!eetoken.getEmailAddress().equals(p.getEmailToken().getEmailAddress())) {
            EmailToken etoken = new EmailToken();
            etoken.setEmailAddress(p.getEmailToken().getEmailAddress().toLowerCase());
            etoken.setEmailOtp(this.getHashed(eotp));
            etoken.setStatus(true);
            etoken.setEmailTokenId(eetoken.getEmailTokenId());
            EmailToken email = this.emailTokenService.save(etoken);
            p.setEmailToken(email);
        } else {
            p.setEmailToken(eetoken);
        }
        Authority authority = this.authorityService.findByRole("STAFF");
        p.setAuthority(authority);
        this.userService.save(p);
        redir.addFlashAttribute("message", "Updated!");
        mv.setViewName("redirect:/administrator/view_staff");
        return mv;
    }
}
