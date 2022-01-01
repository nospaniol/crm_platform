package com.crm.controller;

import com.crm.generic.Nospaniol;
import com.crm.model.Application;
import com.crm.model.Cancel;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.User;
import com.crm.model.EmailToken;
import com.crm.model.JobResponse;
import com.crm.model.Search;
import java.util.Base64;
import java.util.List;
import javax.servlet.ServletException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("user")
public class UserController extends Nospaniol {

    @RequestMapping(value = "/signUp/{addUser}", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@ModelAttribute("application") Application p, RedirectAttributes redir, HttpServletRequest req) {
        JobResponse result = new JobResponse();
        if (p == null) {
            result.setMessage("Fill all required fields!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            Application application = applicationService.findByCompanyName(p.getCompanyName().toUpperCase());
            if (application != null) {
                result.setMessage("Company application is already registered!");
                result.setTitle("fail");
                return ResponseEntity.ok(result);
            }
            application = new Application();
            application.setApplicationId(seqGeneratorService.generateSequence(Application.SEQUENCE_NAME));
            application.setFirstName(p.getFirstName());
            application.setLastName(p.getLastName());
            application.setStatus(false);
            application.setPhoneNumber(p.getPhoneNumber());
            application.setEmailAddress(p.getEmailAddress().toLowerCase());
            application.setCompanyName(p.getCompanyName().toUpperCase());
            application.setCompanyTitle(p.getCompanyTitle());
            this.applicationService.save(application);
            result.setTitle("success");
            result.setMessage("Thank you for registering. We will get back to you to finish setting up your account.");
            return ResponseEntity.ok(result);
        }
    }

    @PostMapping(value = "/{validate}")
    public ModelAndView loginUser(@ModelAttribute("user") User p, HttpServletRequest req, RedirectAttributes redir, HttpServletResponse response) {
       // response.addHeader("Cache-Control", "max-age=600, must-revalidate, no-transform");
        ModelAndView mv = new ModelAndView();
        getUser();
        LOG.info("Processing '" + p.getEmailAddress().toUpperCase() + "' login!");
        mv.setViewName("welcome");
        EmailToken etoken = this.emailTokenService.findByEmailAddress(p.getEmailAddress().toLowerCase());
        User checkUser = userService.findByEmailToken(etoken);
        if (checkUser == null) {
            LOG.info("Email address '" + p.getEmailAddress().toUpperCase() + "' does not exist!");
            mv.addObject("message", "Email address '" + p.getEmailAddress().toUpperCase() + "' does not exist!");
            mv.addObject("messageType", "fail");
            mv.addObject("user", new User());
            return mv;
        } else {
            String pass = p.getPassword();
            if (checkUser.getPassword().equals(this.getHashed(pass))) {
                String phone = checkUser.getPhoneToken().getPhoneNumber();
                String email = checkUser.getEmailToken().getEmailAddress();
                redir.addFlashAttribute("firstName", checkUser.getFirstName());
                redir.addFlashAttribute("middleName", checkUser.getMiddleName());
                redir.addFlashAttribute("lastName", checkUser.getLastName());
                redir.addFlashAttribute("ns_user", checkUser.getEmail());
                redir.addFlashAttribute("loggedNumber", phone);
                redir.addFlashAttribute("loggedMail", email);
                req.setAttribute("loggedMail", email);
                redir.addFlashAttribute("ns_mail_address", email);
                redir.addFlashAttribute("ns_phone_number", phone);
                redir.addFlashAttribute("user_id", checkUser.getUserId());
                switch (checkUser.getAuthority().getRole()) {
                    case "ADMINISTRATOR":
                        LOG.info("ADMINISTRATOR LOGIN********");
                        redir.addFlashAttribute("logged_user", "ADMINISTRATOR");
                        redir.addFlashAttribute("client_id", String.valueOf(checkUser.getUserId()));
                        mv.setViewName("redirect:/administrator/dashboard-redirect");
                        break;
                    case "STAFF":
                        LOG.info("STAFF LOGIN********");
                        redir.addFlashAttribute("logged_user", "STAFF");
                        redir.addFlashAttribute("client_id", String.valueOf(checkUser.getUserId()));
                        mv.setViewName("redirect:/administrator/dashboard-redirect");
                        break;
                    case "CLIENT":
                        LOG.info("CLIENT LOGIN********");
                        ClientProfile profile = checkUser.getClientProfile();
                        if (profile != null) {
                            List<Department> departments = this.departmentService.findByClientProfile(profile);
                            if (departments == null || departments.isEmpty()) {
                            } else {
                                redir.addFlashAttribute("departments", departments);
                            }
                            redir.addFlashAttribute("institution", profile.getCompanyName());
                            redir.addFlashAttribute("parent_institution", profile.getCompanyName());
                            redir.addFlashAttribute("companyLogo", Base64.getEncoder().encodeToString(profile.getCompanyLogo().getData()));
                            redir.addFlashAttribute("account_type", profile.getAccountType());
                            redir.addFlashAttribute("logged_company_name", profile.getCompanyName());
                            redir.addFlashAttribute("logged_user", "CLIENT");
                            redir.addFlashAttribute("client_id", String.valueOf(profile.getClientProfileId()));
                            redir.addFlashAttribute("parent_client_id", String.valueOf(profile.getClientProfileId()));
                            mv.setViewName("redirect:/client_dash/dashboard-redirect");
                            return mv;
                        }
                        Department department = checkUser.getDepartment();
                        if (department != null) {
                            redir.addFlashAttribute("institution", department.getDepartmentName());
                            redir.addFlashAttribute("parent_institution", department.getClientProfile().getCompanyName());
                            redir.addFlashAttribute("logged_user", "DEPARTMENT");
                            redir.addFlashAttribute("department_id", String.valueOf(department.getDepartmentId()));
                            redir.addFlashAttribute("client_id", String.valueOf(department.getDepartmentId()));
                            redir.addFlashAttribute("parent_client_id", String.valueOf(department.getDepartmentId()));
                            mv.setViewName("redirect:/client_dash/dashboard-redirect");
                            return mv;
                        } else {
                            mv.addObject("message", "Sorry, your user account is being worked on by the administrator, please contact for more info!");
                            mv.addObject("messageType", "fail");
                            mv.addObject("user", new User());
                        }
                        break;
                    default:
                        mv.addObject("message", "Sorry, the user has not been assigned any page, please contact the administrator!");
                        mv.addObject("messageType", "fail");
                        mv.addObject("user", new User());
                        return mv;
                }

                return mv;
            } else {
                LOG.info("Incorrect password entered");
                mv.addObject("message", "Incorrect password !");
                mv.addObject("messageTypw", "fail");
                mv.addObject("user", new User());
                return mv;
            }
        }
    }

    @RequestMapping(value = {"logout"}, method = RequestMethod.GET)
    public String logout(Model mv, HttpSession session, RedirectAttributes redir, HttpServletRequest req, HttpServletResponse response) {
       // response.addHeader("Cache-Control", "max-age=0, must-revalidate, no-transform");
        try {
            req.logout();
        } catch (ServletException ex) {
            LOG.info(ex.toString());
        }
        response.reset();
        String userMail = (String) session.getAttribute("ns_mail_address");
        LOG.info("LOGING OUT : " + userMail);
        LOG.info("SESSION DESTROYED : " + session.getId());
        session.invalidate();
        redir.getFlashAttributes().clear();
        session = null;
        mv.addAttribute("institution", institutionName());
        return "user-logout";
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

    @RequestMapping(path = "client/account/{delete-account}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> deleteClientAccount(@ModelAttribute("cancel") Cancel p, Model model, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        JobResponse result = new JobResponse();
        User user = this.userService.findByUserId(p.getCancelItem());
        if (user == null) {
            result.setMessage("User account does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            ClientProfile profile = this.clientProfileService.findByClientProfileId(p.getClientId());
            if (profile == null) {
                result.setMessage("Client account does not exist");
                result.setTitle("fail");
                return ResponseEntity.ok(result);
            }
            if (user.getEmailToken() != null) {
                this.emailTokenService.delete(user.getEmailToken());
            }
            if (user.getPhoneToken() != null) {
                this.phoneTokenService.delete(user.getPhoneToken());
            }
            this.userService.delete(user);
            result.setMessage("User account deleted!");
            result.setTitle("success");
            result.setResults(this.userService.findByClientProfile(profile));
            return ResponseEntity.ok(result);
        }
    }

    @RequestMapping(path = "change/password/{currentPass}/{newPass}/{confirmPass}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> changePassword(HttpSession session, @PathVariable("currentPass") String currentPass, @PathVariable("newPass") String newPass, @PathVariable("confirmPass") String confirmPass) {
        JobResponse result = new JobResponse();
        if (currentPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            result.setMessage("Fill all required fileds!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Session expired, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        EmailToken etoken = this.emailTokenService.findByEmailAddress(ns_mail.toLowerCase());
        User user = userService.findByEmailToken(etoken);
        if (user == null) {
            result.setMessage("User account does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            if (user.getPassword().equals(this.getHashed(currentPass))) {
                if (newPass.equals(confirmPass)) {
                    user.setPassword(this.getHashed(newPass));
                    this.userService.save(user);
                    result.setMessage("Password modified successfuly!");
                    result.setTitle("success");
                } else {
                    result.setMessage("New passwords does not match!");
                    result.setTitle("fail");
                }
            } else {
                result.setMessage("The current password entered is invalid!");
                result.setTitle("fail");
            }
            return ResponseEntity.ok(result);
        }
    }

}
