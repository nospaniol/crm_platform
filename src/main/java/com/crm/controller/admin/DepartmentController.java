package com.crm.controller.admin;

import com.crm.generic.Nospaniol;
import static com.crm.generic.Nospaniol.LOG;
import com.crm.model.Authority;
import com.crm.model.Cancel;
import com.crm.model.ClientProfile;
import com.crm.model.ClientUser;
import com.crm.model.Department;
import com.crm.model.DepartmentAccount;
import com.crm.model.DepartmentForm;
import com.crm.model.DepartmentSaving;
import com.crm.model.EmailToken;
import com.crm.model.JobResponse;
import com.crm.model.PhoneToken;
import com.crm.model.Saving;
import com.crm.model.User;
import java.io.IOException;
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
@RequestMapping("department")
public class DepartmentController extends Nospaniol {

    @RequestMapping(value = "register/{saveProfile}", method = RequestMethod.POST)
    public ModelAndView registerDepartment(@ModelAttribute("department") DepartmentForm p, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        try {
            mv.setViewName("redirect:/client/view/department/" + p.getClientProfileId());
            ClientProfile profile = this.clientProfileService.findByClientProfileId(p.getClientProfileId());
            if (profile == null) {
                mv.addObject("messageType", "fail");
                mv.addObject("message", "No client was selected when opening this page, open this page from client profile page!");
                mv.addObject("departmentForm", p);
                return mv;
            }
            if (p.getDepartmentName().isEmpty()) {
                mv.addObject("messageType", "fail");
                mv.addObject("message", "Please enter the department name!");
                mv.addObject("departmentForm", p);
                return mv;
            }
            Department department = this.departmentService.findByDepartmentId(p.getDepartmentId());
            if (department == null) {
                if (this.departmentService.findByDepartmentNameAndClientProfile(p.getDepartmentName(), profile) != null) {
                    mv.addObject("messageType", "fail");
                    mv.addObject("message", "This department has already been registered!");
                    mv.addObject("profile", p);
                    return mv;
                }
                Binary logo = new Binary(BsonBinarySubType.BINARY, p.getDepartmentLogo().getBytes());
                if (logo.length() <= 0) {
                    mv.addObject("messageType", "fail");
                    mv.addObject("message", "Select a logo please!");
                    mv.addObject("departmentForm", p);
                    return mv;
                }
                if (this.getFileExtension(p.getDepartmentLogo()).equals("png") || this.getFileExtension(p.getDepartmentLogo()).equals("jpg") || this.getFileExtension(p.getDepartmentLogo()).equals("jpeg")) {

                } else {
                    mv.addObject("messageType", "fail");
                    mv.addObject("message", "Please ensure the logo is a image with an extension either '*.png, *.jpg ,*.jpeg'!");
                    mv.addObject("departmentForm", p);
                    return mv;
                }
                department = new Department();
                department.setDepartmentId(seqGeneratorService.generateSequence(Department.SEQUENCE_NAME));
                department.setDepartmentLogo(new Binary(BsonBinarySubType.BINARY, p.getDepartmentLogo().getBytes()));
            } else {
                Binary logo = new Binary(BsonBinarySubType.BINARY, p.getDepartmentLogo().getBytes());
                if (logo.length() <= 0) {

                } else {
                    if (this.getFileExtension(p.getDepartmentLogo()).equals("png") || this.getFileExtension(p.getDepartmentLogo()).equals("jpg") || this.getFileExtension(p.getDepartmentLogo()).equals("jpeg")) {
                        department.setDepartmentLogo(new Binary(BsonBinarySubType.BINARY, p.getDepartmentLogo().getBytes()));
                    }
                }
            }
            department.setDepartmentName(p.getDepartmentName().toUpperCase());
            department.setClientProfile(profile);
            Department prof = this.departmentService.save(department);
            DepartmentAccount account = this.departmentAccountService.findByClientProfileAndDepartment(profile, prof);
            if (account == null) {
                account = new DepartmentAccount();
                account.setAccountId(seqGeneratorService.generateSequence(DepartmentAccount.SEQUENCE_NAME));
                account.setClientProfile(profile);
                account.setDepartment(prof);
                account.setAmount(0.00);
                account.setStatus(true);
                account.setUpdatedDate(this.getCurrentDate());
                account.setCreationDate(this.getCurrentDate());
                account.setCreationTime(this.getCurrentTimestamp());
                account.setUpdatedTime(this.getCurrentTimestamp());
                this.departmentAccountService.save(account);

                DepartmentSaving saving = new DepartmentSaving();
                saving.setSavingId(seqGeneratorService.generateSequence(DepartmentSaving.SEQUENCE_NAME));
                saving.setDepartment(prof);
                saving.setClientProfile(prof.getClientProfile());
                saving.setAmount(0.00);
                saving.setStatus(true);
                saving.setUpdatedDate(this.getCurrentDate());
                saving.setCreationDate(this.getCurrentDate());
                saving.setCreationTime(this.getCurrentTimestamp());
                saving.setUpdatedTime(this.getCurrentTimestamp());
                this.departmentSavingService.save(saving);
            }
            mv.addObject("institution", institutionName());
            redir.addFlashAttribute("messageType", "success");
            redir.addFlashAttribute("message", "Successfully updated");
            mv.setViewName("redirect:/client/view/department/" + profile.getClientProfileId());
            return mv;
        } catch (IOException ex) {
            redir.addFlashAttribute("messageType", "fail");
            redir.addFlashAttribute("message", "Failed : " + ex);
            mv.setViewName("redirect:/client/view/department/" + p.getClientProfileId());
            return mv;
        }
    }

    @RequestMapping(path = "delete/{id}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> deleteAccount(@PathVariable("id") Long id, HttpSession session) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        Department department = this.departmentService.findByDepartmentId(id);
        if (department == null) {
            result.setMessage("Department not found!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            this.departmentService.delete(department);
            result.setMessage("Department deleted!");
            result.setTitle("success");
            return ResponseEntity.ok(result);
        }
    }

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
        Department department = this.departmentService.findByDepartmentId(id);
        if (department == null) {
            redir.addFlashAttribute("Client account does not exist");
            redir.addFlashAttribute("fail");
            mv.setViewName("redirect:/administrator/view_client/");
            return mv;
        } else {
            mv.addObject("data", this.userService.findByDepartment(department));
            mv.addObject("departmentId", department.getDepartmentId());
            mv.addObject("department", department);
            mv.addObject("user", new ClientUser());
            mv.addObject("institution", institutionName());
            mv.setViewName("adddepartmentuser");
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
        Department department = this.departmentService.findByDepartmentId(id);
        if (department == null) {
            redir.addFlashAttribute("Department does not exist");
            redir.addFlashAttribute("fail");
            mv.setViewName("adddepartment");
            return mv;
        } else {
            mv.addObject("profile", department.getClientProfile());
            DepartmentForm prof = new DepartmentForm();
            prof.setDepartmentName(department.getDepartmentName());
            prof.setDepartmentId(department.getDepartmentId());
            mv.addObject("departmentForm", prof);
            mv.addObject("institution", institutionName());
            mv.setViewName("adddepartment");
            return mv;
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
            mv.addObject("data", this.userService.findByDepartment(user.getDepartment()));
            mv.addObject("departmentId", user.getDepartment().getDepartmentId());
            mv.addObject("department", user.getDepartment());
            mv.addObject("profile", user.getDepartment().getClientProfile());
            ClientUser newUser = new ClientUser();
            newUser.setDepartmentId(user.getDepartment().getDepartmentId());
            newUser.setUserId(id);
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setDesignation(user.getDesignation());
            newUser.setEmailAddress(user.getEmailToken().getEmailAddress());
            newUser.setPhoneNumber(user.getPhoneToken().getPhoneNumber());
            mv.addObject("user", newUser);
            mv.addObject("institution", institutionName());
            mv.setViewName("adddepartmentuser");
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
        Department department = this.departmentService.findByDepartmentId(p.getDepartmentId());
        if (department == null) {
            result.setMessage(" Client id " + String.valueOf(p.getDepartmentId()) + " not found!");
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
            newUser.setDepartment(department);
            newUser.setGroupStatus(false);
            newUser.setDepartmentStatus(true);
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
            user.setDepartment(department);
            user.setGroupStatus(false);
            user.setDepartmentStatus(true);
            user.setAdministrativeStatus(true);
            User userAccount = userService.save(user);
        }
        result.setResults(this.userService.findByDepartment(department));
        result.setMessage(" user account created successfully!");
        result.setTitle("success");
        return ResponseEntity.ok(result);

    }

}
