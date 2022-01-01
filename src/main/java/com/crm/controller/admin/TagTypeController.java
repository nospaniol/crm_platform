package com.crm.controller.admin;

import com.crm.controller.*;
import com.crm.generic.Nospaniol;
import com.crm.model.Cancel;
import com.crm.model.TagType;
import com.crm.model.JobResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("tagtype")
public class TagTypeController extends Nospaniol {

    @RequestMapping(value = {"add_tagtype"}, method = RequestMethod.GET)
    public String addClients(Model mv, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            mv.addAttribute("messageType", "success");
            mv.addAttribute("message", "Administrator privileges only!");
            return "user-logout";
        }
        mv.addAttribute("tagType", new TagType());
        mv.addAttribute("allTagTypes", this.tagTypeService.findAll());
        mv.addAttribute("institution", institutionName());
        return "addtagtype";
    }

    @RequestMapping(value = "register/{save}", method = RequestMethod.POST)
    public ModelAndView registerTagType(@ModelAttribute("tagType") TagType p, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Administrator privileges only!");
            return mv;
        }
        mv.setViewName("redirect:/tagtype/add_tagtype");
        if (p.getTagTypeName().isEmpty()) {
            mv.addObject("messageType", "fail");
            mv.addObject("message", "Please enter the tagType name!");
            mv.addObject("tagType", p);
            return mv;
        }
        TagType tagType = this.tagTypeService.findByTagTypeId(p.getTagTypeId());
        if (tagType == null) {
            if (this.tagTypeService.findByTagTypeName(p.getTagTypeName()) != null) {
                mv.addObject("messageType", "fail");
                mv.addObject("message", "This tagType has already been registered!");
                mv.addObject("profile", p);
                return mv;
            }
            tagType = new TagType();
            tagType.setTagTypeId(seqGeneratorService.generateSequence(TagType.SEQUENCE_NAME));
            tagType.setUpdatedDate(this.getCurrentDate());
            tagType.setCreationDate(this.getCurrentDate());
            tagType.setCreationTime(this.getCurrentTimestamp());
            tagType.setUpdatedTime(this.getCurrentTimestamp());;
        } else {
            tagType.setUpdatedDate(this.getCurrentDate());
            tagType.setUpdatedTime(this.getCurrentTimestamp());
        }
        tagType.setTagTypeName(p.getTagTypeName().toUpperCase());
        TagType prof = this.tagTypeService.save(tagType);
        mv.addObject("institution", institutionName());
        redir.addFlashAttribute("messageType", "success");
        redir.addFlashAttribute("message", "Successfully updated");
        return mv;

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
        TagType tagType = this.tagTypeService.findByTagTypeId(p.getCancelItem());
        if (tagType == null) {
            result.setMessage("Tag type not found!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            this.tagTypeService.delete(tagType);
            result.setMessage("Tag type deleted!");
            result.setTitle("success");
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
        TagType tagType = this.tagTypeService.findByTagTypeId(id);
        if (tagType == null) {
            redir.addFlashAttribute("TagType does not exist");
            redir.addFlashAttribute("fail");
            mv.setViewName("addtagtype");
            return mv;
        } else {
            TagType prof = new TagType();
            prof.setTagTypeName(tagType.getTagTypeName());
            prof.setTagTypeId(tagType.getTagTypeId());
            prof.setTagTypeId(tagType.getTagTypeId());
            mv.addObject("allTagTypes", this.tagTypeService.findAll());
            mv.addObject("tagType", prof);
            mv.addObject("institution", institutionName());
            mv.setViewName("addtagtype");
            return mv;
        }
    }
}
