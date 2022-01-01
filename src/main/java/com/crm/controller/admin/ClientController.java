package com.crm.controller.admin;

import com.crm.generic.Nospaniol;
import com.crm.model.Account;
import com.crm.model.Authority;
import com.crm.model.Cancel;
import com.crm.model.ClientProfile;
import com.crm.model.ClientUser;
import com.crm.model.Department;
import com.crm.model.DepartmentForm;
import com.crm.model.PhoneToken;
import com.crm.model.User;
import com.crm.model.EmailToken;
import com.crm.model.JobResponse;
import com.crm.model.Profile;
import com.crm.model.Saving;
import com.crm.model.Search;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("client")
public class ClientController extends Nospaniol {

    @RequestMapping("/new/user/{id}")
    public ModelAndView newUser(@PathVariable("id") Long id, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        ClientProfile profile = this.clientProfileService.findByClientProfileId(id);
        if (profile == null) {
            redir.addFlashAttribute("Client account does not exist");
            redir.addFlashAttribute("fail");
            mv.setViewName("redirect:/administrator/view_client/");
            return mv;
        } else {
            mv.addObject("data", this.userService.findByClientProfile(profile));
            mv.addObject("clientProfileId", profile.getClientProfileId());
            mv.addObject("profile", profile);
            mv.addObject("user", new ClientUser());
            mv.addObject("institution", institutionName());
            mv.setViewName("addclientuser");
            return mv;
        }
    }

    @RequestMapping("/view/client/user/{id}")
    public ResponseEntity<?> viewUser(@PathVariable("id") Long id, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        ClientProfile profile = this.clientProfileService.findByClientProfileId(id);
        if (profile == null) {
            result.setMessage("User profile not found!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            result.setMessage("Data found!");
            result.setTitle("success");
            result.setResults(this.userService.findByClientProfile(profile));
            return ResponseEntity.ok(result);
        }
    }

    @RequestMapping("/edit/user/{id}")
    public ModelAndView editUser(@PathVariable("id") Long id, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        User user = this.userService.findByUserId(id);
        if (user == null) {
            redir.addFlashAttribute("Client account does not exist");
            redir.addFlashAttribute("fail");
            mv.setViewName("redirect:/administrator/view_client/");
            return mv;
        } else {
            mv.addObject("data", this.userService.findByClientProfile(user.getClientProfile()));
            mv.addObject("clientProfileId", user.getClientProfile().getClientProfileId());
            ClientUser newUser = new ClientUser();
            newUser.setClientProfileId(user.getClientProfile().getClientProfileId());
            newUser.setUserId(id);
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setDesignation(user.getDesignation());
            if (user.getEmailToken() != null) {
                newUser.setEmailAddress(user.getEmailToken().getEmailAddress());
            }
            if (user.getPhoneToken() != null) {
                newUser.setPhoneNumber(user.getPhoneToken().getPhoneNumber());
            }
            mv.addObject("user", newUser);
            mv.addObject("institution", institutionName());
            mv.setViewName("addclientuser");
            return mv;
        }
    }

    @RequestMapping(value = "profile/{saveProfile}", method = RequestMethod.POST)
    public ModelAndView registerClientProfile(@ModelAttribute("profile") Profile p, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        try {
            mv.setViewName("addclient");
            if (p.getPostalAddress().isEmpty() || p.getCompanyEmailAddress().isEmpty() || p.getCompanyPhoneNumber().isEmpty() || p.getCategory().isEmpty() || p.getAccountType().isEmpty()) {
                mv.addObject("messageType", "fail");
                mv.addObject("message", "Fill all required fields!");
                mv.addObject("profile", p);
                return mv;
            }

            ClientProfile profile = this.clientProfileService.findByClientProfileId(p.getClientProfileId());
            if (profile == null) {
                Binary logo = new Binary(BsonBinarySubType.BINARY, p.getCompanyLogo().getBytes());
                if (logo.length() <= 0) {
                    mv.addObject("messageType", "fail");
                    mv.addObject("message", "Select a logo please!");
                    mv.addObject("profile", p);
                    return mv;
                }

                if (this.getFileExtension(p.getCompanyLogo()).equals("png") || this.getFileExtension(p.getCompanyLogo()).equals("jpg") || this.getFileExtension(p.getCompanyLogo()).equals("jpeg")) {

                } else {
                    mv.addObject("messageType", "fail");
                    mv.addObject("message", "Please ensure the logo is a image with an extension either '*.png, *.jpg ,*.jpeg'!");
                    mv.addObject("profile", p);
                    return mv;
                }

                if (this.clientProfileService.findByCompanyName(p.getCompanyName()) != null) {
                    mv.addObject("messageType", "fail");
                    mv.addObject("message", "This company name has already been registered!");
                    mv.addObject("profile", p);
                    return mv;
                }
                profile = new ClientProfile();
                profile.setClientProfileId(seqGeneratorService.generateSequence(ClientProfile.SEQUENCE_NAME));
                profile.setCompanyLogo(new Binary(BsonBinarySubType.BINARY, p.getCompanyLogo().getBytes()));

            } else {
                Binary logo = new Binary(BsonBinarySubType.BINARY, p.getCompanyLogo().getBytes());
                if (logo.length() <= 0) {

                } else {
                    if (this.getFileExtension(p.getCompanyLogo()).equals("png") || this.getFileExtension(p.getCompanyLogo()).equals("jpg") || this.getFileExtension(p.getCompanyLogo()).equals("jpeg")) {
                        profile.setCompanyLogo(new Binary(BsonBinarySubType.BINARY, p.getCompanyLogo().getBytes()));
                    }
                }
            }

            profile.setCompanyName(p.getCompanyName().toUpperCase());
            profile.setCompanyEmailAddress(p.getCompanyEmailAddress());
            profile.setCompanyPhoneNumber(p.getCompanyPhoneNumber());
            profile.setPostalAddress(p.getPostalAddress());
            profile.setCategory(p.getCategory());
            profile.setAccountType(p.getAccountType());
            profile.setStatus(true);
            profile.setUpdatedDate(this.getCurrentDate());
            profile.setCreationDate(this.getCurrentDate());
            profile.setCreationTime(this.getCurrentTimestamp());
            profile.setUpdatedTime(this.getCurrentTimestamp());
            ClientProfile prof = this.clientProfileService.save(profile);

            /*
            
            create new account
            
             */
            Account account = this.accountService.findByClientProfile(prof);
            if (account == null) {
                account = new Account();
                account.setAccountId(seqGeneratorService.generateSequence(Account.SEQUENCE_NAME));
                account.setClientProfile(prof);
                account.setAmount(0.00);
                account.setStatus(true);
                account.setUpdatedDate(this.getCurrentDate());
                account.setCreationDate(this.getCurrentDate());
                account.setCreationTime(this.getCurrentTimestamp());
                account.setUpdatedTime(this.getCurrentTimestamp());
                this.accountService.save(account);
                Saving saving = new Saving();
                saving.setSavingId(seqGeneratorService.generateSequence(Saving.SEQUENCE_NAME));
                saving.setClientProfile(prof);
                saving.setAmount(0.00);
                saving.setStatus(true);
                saving.setUpdatedDate(this.getCurrentDate());
                saving.setCreationDate(this.getCurrentDate());
                saving.setCreationTime(this.getCurrentTimestamp());
                saving.setUpdatedTime(this.getCurrentTimestamp());
                this.savingService.save(saving);
            }
            mv.addObject("institution", institutionName());
            redir.addFlashAttribute("messageType", "success");
            redir.addFlashAttribute("message", "Successfully updated");
            mv.setViewName("redirect:/client/view/" + prof.getClientProfileId());
            return mv;
        } catch (IOException ex) {
            redir.addFlashAttribute("messageType", "fail");
            redir.addFlashAttribute("message", "Failed : " + ex);
            mv.setViewName("redirect:/administrator/add_client");
            return mv;
        }
    }

    @RequestMapping(value = "register/new/{user}", method = RequestMethod.POST)
    public ResponseEntity<?> addClientUsers(@ModelAttribute("user") ClientUser p, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        if (p.getFirstName().isEmpty() || p.getLastName().isEmpty() || p.getFirstName().isEmpty() || p.getLastName().isEmpty() || p.getPhoneNumber().isEmpty() || p.getEmailAddress().isEmpty() || p.getDesignation().isEmpty()) {
            result.setMessage(" fill all required fields!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        if (p.getPassword().isEmpty() || p.getRpassword().isEmpty()) {
            result.setMessage(" Enter the passwords!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        ClientProfile profile = this.clientProfileService.findByClientProfileId(p.getClientProfileId());
        if (profile == null) {
            result.setMessage(" Client id " + String.valueOf(p.getClientProfileId()) + " not found!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        /*
            
            update user account
            
         */
        String otp = this.generateOtp();
        String eotp = this.generateOtp();
        User user = null;
        User idUser = this.userService.findByUserId(p.getUserId());
        if (idUser != null) {
            user = idUser;
        }
        EmailToken emailTok = this.emailTokenService.findByEmailAddress(p.getEmailAddress().toLowerCase());
        User emailUser = this.userService.findByEmailToken(emailTok);
        if (emailUser != null) {
            user = emailUser;
        }
        if (user == null) {
            /*
                
                User does not exist
                
             */
            User newUser = new User();
            LOG.info("THE USER WAS NOT FOUND");
            PhoneToken phone = this.phoneTokenService.findByPhoneNumber(p.getPhoneNumber());
            if (phone == null) {
                PhoneToken token = new PhoneToken();
                token.setPhoneNumber(p.getPhoneNumber());
                token.setPhoneOtp(this.getHashed(otp));
                token.setStatus(false);
                token.setPhoneTokenId(seqGeneratorService.generateSequence(PhoneToken.SEQUENCE_NAME));
                phone = this.phoneTokenService.save(token);
            }
            EmailToken email = this.emailTokenService.findByEmailAddress(p.getEmailAddress().toLowerCase());
            if (email == null) {
                EmailToken etoken = new EmailToken();
                etoken.setEmailAddress(p.getEmailAddress().toLowerCase());
                etoken.setEmailOtp(this.getHashed(eotp));
                etoken.setStatus(false);
                etoken.setEmailTokenId(seqGeneratorService.generateSequence(EmailToken.SEQUENCE_NAME));
                email = this.emailTokenService.save(etoken);
            }
            newUser.setUserId(seqGeneratorService.generateSequence(User.SEQUENCE_NAME));
            String pass = this.getHashed(p.getPassword());
            newUser.setPassword(pass);
            newUser.setDesignation(p.getDesignation());
            newUser.setFirstName(p.getFirstName());
            newUser.setLastName(p.getLastName());
            newUser.setStatus(true);
            newUser.setPhoneToken(phone);
            newUser.setEmailToken(email);
            Authority authority = authorityService.findByRole("CLIENT");
            newUser.setAuthority(authority);
            newUser.setClientProfile(profile);
            newUser.setGroupStatus(false);
            newUser.setDepartmentStatus(false);
            newUser.setAdministrativeStatus(true);
            User newUserAccount = userService.save(newUser);
        } else {
            LOG.info("THE USER WAS FOUND");
            PhoneToken phone = this.phoneTokenService.findByPhoneNumber(p.getPhoneNumber());
            if (phone == null) {
                PhoneToken token = user.getPhoneToken();
                if (token == null) {
                    token = new PhoneToken();
                    token.setPhoneTokenId(seqGeneratorService.generateSequence(PhoneToken.SEQUENCE_NAME));
                }
                token.setPhoneNumber(p.getPhoneNumber());
                token.setPhoneOtp(this.getHashed(otp));
                token.setStatus(false);
                phone = this.phoneTokenService.save(token);
            }
            EmailToken email = this.emailTokenService.findByEmailAddress(p.getEmailAddress().toLowerCase());
            if (email == null) {
                EmailToken etoken = user.getEmailToken();
                if (etoken == null) {
                    etoken = new EmailToken();
                    etoken.setEmailTokenId(seqGeneratorService.generateSequence(EmailToken.SEQUENCE_NAME));
                }
                etoken.setEmailAddress(p.getEmailAddress().toLowerCase());
                etoken.setEmailOtp(this.getHashed(eotp));
                etoken.setStatus(false);
                email = this.emailTokenService.save(etoken);
            }
            String pass = this.getHashed(p.getPassword());
            user.setPassword(pass);
            user.setDesignation(p.getDesignation());
            user.setFirstName(p.getFirstName());
            user.setLastName(p.getLastName());
            user.setStatus(true);
            user.setPhoneToken(phone);
            user.setEmailToken(email);
            Authority authority = authorityService.findByRole("CLIENT");
            user.setAuthority(authority);
            user.setClientProfile(profile);
            user.setGroupStatus(false);
            user.setDepartmentStatus(false);
            user.setAdministrativeStatus(true);
            User userAccount = userService.save(user);
        }
        result.setResults(this.userService.findByClientProfile(profile));
        result.setMessage(" user account created successfully!");
        result.setTitle("success");
        return ResponseEntity.ok(result);

    }

    @RequestMapping("/view_profile")
    public ModelAndView viewProfile(HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        String doc_mail = (String) session.getAttribute("ns_mail_address");
        User user = this.userService.findByEmailToken(this.emailTokenService.findByEmailAddress(doc_mail));
        ClientProfile profile = user.getClientProfile();
        if (profile == null) {
            profile = new ClientProfile();
        }
        mv.addObject("institution", institutionName());
        redir.addFlashAttribute("clientProfile", profile);
        mv.setViewName("view_user_profile");
        return mv;
    }

    @RequestMapping(path = "/available/check_availability", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchUsername(HttpSession session, @ModelAttribute("search") Search search) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
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
        ClientProfile profile = this.clientProfileService.findByClientProfileId(p.getCancelItem());
        if (profile == null) {
            result.setMessage("Client account does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            List<Department> departments = this.departmentService.findByClientProfile(profile);
            if (departments == null || departments.isEmpty()) {

            } else {
                this.departmentService.deleteAll(departments);
            }
            List<User> users = this.userService.findByClientProfile(profile);
            this.userService.deleteAll(users);
            this.clientProfileService.delete(profile);
            result.setMessage("Client account deleted!");
            result.setTitle("success");
            return ResponseEntity.ok(result);
        }
    }

    @RequestMapping("/profile/edit/{id}")
    public ModelAndView editProfile(@PathVariable("id") Long id, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        ClientProfile profile = this.clientProfileService.findByClientProfileId(id);
        if (profile == null) {
            redir.addFlashAttribute("User account does not exist");
            redir.addFlashAttribute("fail");
            mv.setViewName("redirect:/administrator/view_client/");
            return mv;
        } else {
            Profile prof = new Profile();
            prof.setCompanyName(profile.getCompanyName());
            prof.setCompanyEmailAddress(profile.getCompanyEmailAddress());
            prof.setCompanyPhoneNumber(profile.getCompanyPhoneNumber());
            prof.setPostalAddress(profile.getPostalAddress());
            prof.setCategory(profile.getCategory());
            prof.setAccountType(profile.getAccountType());
            prof.setClientProfileId(profile.getClientProfileId());
            mv.addObject("profile", prof);
            mv.addObject("institution", institutionName());
            mv.setViewName("addclient");
            return mv;
        }
    }

    @RequestMapping("/view/{id}")
    public ModelAndView viewProfile(@PathVariable("id") Long id, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        ClientProfile profile = this.clientProfileService.findByClientProfileId(id);
        if (profile == null) {
            redir.addFlashAttribute("message", "No profile added");
            redir.addFlashAttribute("messageType", "fail");
            mv.setViewName("redirect:/administrator/view_client/");
            return mv;
        } else {
            mv.addObject("companyLogo", Base64.getEncoder().encodeToString(profile.getCompanyLogo().getData()));
            mv.addObject("institution", institutionName());
            mv.addObject("clientProfile", profile);
            mv.setViewName("clientprofile");
            return mv;
        }
    }

    @RequestMapping("/view/department/{id}")
    public ModelAndView viewDepartments(@PathVariable("id") Long id, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        ClientProfile profile = this.clientProfileService.findByClientProfileId(id);
        if (profile == null) {
            redir.addFlashAttribute("message", "No existing client was selected when opening this page, open this page from client profile page!");
            redir.addFlashAttribute("messageType", "fail");
            mv.setViewName("adddepartment");
            return mv;
        } else {
            mv.addObject("profile", profile);
            mv.addObject("departmentLogo", Base64.getEncoder().encodeToString(profile.getCompanyLogo().getData()));
            mv.addObject("institution", institutionName());
            DepartmentForm form = new DepartmentForm();
            form.setClientProfileId(id);
            mv.addObject("departmentForm", form);
            mv.addObject("allDepartments", this.departmentService.findByClientProfile(profile));
            mv.setViewName("adddepartment");
            return mv;
        }
    }

    @RequestMapping("/new/group/{id}")
    public ModelAndView newGroup(@PathVariable("id") Long id, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        ClientProfile profile = this.clientProfileService.findByClientProfileId(id);
        if (profile == null) {
            redir.addFlashAttribute("message", "No existing client was selected when opening this page, open this page from client profile page!");
            redir.addFlashAttribute("messageType", "fail");
            mv.setViewName("adddepartment");
            return mv;
        } else {
            mv.addObject("companyName", profile.getCompanyName());
            mv.addObject("departmentLogo", Base64.getEncoder().encodeToString(profile.getCompanyLogo().getData()));
            mv.addObject("institution", institutionName());
            DepartmentForm form = new DepartmentForm();
            form.setClientProfileId(id);
            mv.addObject("departmentForm", form);
            mv.addObject("allDepartments", this.departmentService.findByClientProfile(profile));
            mv.setViewName("addgroup");
            return mv;
        }
    }

    @RequestMapping(value = "update/{user_account}", method = RequestMethod.POST)
    public ModelAndView updateUserAccount(@ModelAttribute("user") User p, HttpServletRequest req, RedirectAttributes redir, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        String otp = this.generateOtp();
        String eotp = this.generateOtp();
        User currentUser = this.userService.findByUserId(p.getUserId());
        PhoneToken ptoken = currentUser.getPhoneToken();
        EmailToken eetoken = currentUser.getEmailToken();
        if (ptoken == null) {
            redir.addFlashAttribute("message", "Phone token retrieved is null");
            mv.setViewName("user-edit-account");
            return mv;
        }
        if (eetoken == null) {
            redir.addFlashAttribute("message", "Email token retrieved is null");
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
        User newUser = this.userService.save(p);
        redir.addFlashAttribute("message", "Updated!");
        mv.setViewName("redirect:/administrator/view_staff");
        return mv;
    }

    @RequestMapping(path = "/load/departments/", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> loadDepartments(@ModelAttribute("search") Search search, Model model, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        String client = search.getSearchItem().trim();
        ClientProfile profile = this.clientProfileService.findByCompanyName(client);
        if (profile == null) {
            result.setMessage("Client : '" + search.getSearchItem() + "' not found in the system");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        List<Department> departments = this.departmentService.findByClientProfile(profile);
        if (departments == null || departments.isEmpty()) {
            result.setResults(departments);
            result.setMessage("Client : " + profile.getCompanyName() + " has no departments!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        result.setMessage("Available departments found");
        result.setTitle("success");
        result.setResults(departments);
        return ResponseEntity.ok(result);
    }

}
