package com.crm.controller.admin;

import com.crm.generic.Nospaniol;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.ClientUser;
import com.crm.model.SubDepartment;
import com.crm.model.DepartmentForm;
import com.crm.model.JobResponse;
import com.crm.model.User;
import java.io.IOException;
import java.util.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("sub_department")
public class SubDepartmentController extends Nospaniol {

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
        session.setAttribute("department_id", id);
        mv.addObject("depart", "sub_department");
        Department profile = this.departmentService.findByDepartmentId(id);
        if (profile == null) {
            redir.addFlashAttribute("message", "No existing client was selected when opening this page, open this page from client profile page!");
            redir.addFlashAttribute("messageType", "fail");
            mv.setViewName("adddepartment");
            return mv;
        } else {
            mv.addObject("profile", profile.getClientProfile());
            mv.addObject("department", profile);
            mv.addObject("departmentLogo", Base64.getEncoder().encodeToString(profile.getDepartmentLogo().getData()));
            mv.addObject("institution", institutionName());
            DepartmentForm form = new DepartmentForm();
            form.setClientProfileId(id);
            mv.addObject("departmentForm", form);
            mv.addObject("allDepartments", this.subDepartmentService.findByDepartment(profile));
            mv.setViewName("adddepartment");
            return mv;
        }
    }

    @RequestMapping(value = "register/{saveProfile}", method = RequestMethod.POST)
    public ModelAndView registerSubDepartment(@ModelAttribute("department") DepartmentForm p, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        mv.addObject("depart", "sub_department");
        try {

            Long id = (Long) session.getAttribute("department_id");
            LOG.info(id.toString() + " DEPARTMENT");
            mv.setViewName("redirect:/sub_department/view/department/" + id.toString());
            Department profile = this.departmentService.findByDepartmentId(id);
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
            SubDepartment department = this.subDepartmentService.findByDepartmentId(p.getDepartmentId());
            if (department == null) {
                if (this.subDepartmentService.findByDepartmentNameAndDepartment(p.getDepartmentName(), profile) != null) {
                    mv.addObject("messageType", "fail");
                    mv.addObject("message", "This sub department has already been registered!");
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
                department = new SubDepartment();
                department.setDepartmentId(seqGeneratorService.generateSequence(SubDepartment.SEQUENCE_NAME));
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
            department.setDepartment(profile);
            SubDepartment prof = this.subDepartmentService.save(department);

            mv.addObject("institution", institutionName());
            redir.addFlashAttribute("messageType", "success");
            redir.addFlashAttribute("message", "Successfully updated");
            mv.setViewName("redirect:/sub_department/view/department/" + profile.getDepartmentId());
            return mv;
        } catch (IOException ex) {
            Long id = (Long) session.getAttribute("department_id");
            mv.setViewName("redirect:/sub_department/view/department/" + id.toString());
            redir.addFlashAttribute("messageType", "fail");
            redir.addFlashAttribute("message", "Failed : " + ex);
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
        SubDepartment department = this.subDepartmentService.findByDepartmentId(id);
        if (department == null) {
            result.setMessage("SubDepartment not found!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            this.subDepartmentService.delete(department);
            result.setMessage("SubDepartment deleted!");
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
        SubDepartment department = this.subDepartmentService.findByDepartmentId(id);
        if (department == null) {
            redir.addFlashAttribute("Client account does not exist");
            redir.addFlashAttribute("fail");
            mv.setViewName("redirect:/administrator/view_client/");
            return mv;
        } else {
            //mv.addObject("data", this.userService.findByDepartment(department));
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
        Department profile = this.departmentService.findByDepartmentId(id);
        if (profile == null) {
            result.setMessage("User profile not found!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            result.setMessage("Data found!");
            result.setTitle("success");
            result.setResults(this.userService.findByDepartment(profile));
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
        SubDepartment department = this.subDepartmentService.findByDepartmentId(id);
        if (department == null) {
            redir.addFlashAttribute("SubDepartment does not exist");
            redir.addFlashAttribute("fail");
            mv.setViewName("adddepartment");
            return mv;
        } else {
            mv.addObject("profile", department.getDepartment());
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
            ClientUser newUser = new ClientUser();
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

}
