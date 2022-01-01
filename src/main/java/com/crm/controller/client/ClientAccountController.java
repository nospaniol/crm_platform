package com.crm.controller.client;

import com.crm.generic.Nospaniol;
import com.crm.model.ClientProfile;
import com.crm.model.Account;
import com.crm.model.JobResponse;
import com.crm.model.AccountUpload;
import com.crm.model.Department;
import com.crm.model.DepartmentAccount;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("client_account")
public class ClientAccountController extends Nospaniol {

    int counter = 0;
    int header = 0;

    @RequestMapping(value = {"view_accounts"}, method = RequestMethod.GET)
    public ModelAndView viewAccounts(RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "account");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                String departmentMode = (String) session.getAttribute("department_mode");
                String departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));

                    mv.addObject("topic", department.getDepartmentName() + " ACCOUNT");
                    DepartmentAccount depAccount = this.departmentAccountService.findByDepartment(department);
                    mv.addObject("account", depAccount);
                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    mv.addObject("topic", profile.getCompanyName() + " ACCOUNTS");
                    //mv.addObject("institution", profile.getCompanyName());

                    Account account = this.accountService.findByClientProfile(profile);
                    mv.addObject("account", account);
                }
                break;
            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));

                mv.addObject("topic", department.getDepartmentName() + " ACCOUNT");

                DepartmentAccount depAccount = this.departmentAccountService.findByDepartment(department);
                mv.addObject("account", depAccount);
                break;
            default:
                LOG.info("USER ACCOUNT UNKNOWN");
        }

        mv.setViewName("clientviewaccounts");
        return mv;
    }

    @RequestMapping("/view/department/")
    public ModelAndView editAccount(@PageableDefault(value = 10) Pageable pageable, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "account");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
        if (profile == null) {
            redir.addFlashAttribute("Client profile not found");
            redir.addFlashAttribute("fail");
            mv.setViewName("clientviewdepartmentaccount");
            return mv;
        } else {
            //mv.addObject("institution", profile.getCompanyName());

            mv.addObject("account", new AccountUpload());
            mv.addObject("topic", "DEPARTMENTAL ACCOUNTS FOR " + profile.getCompanyName());
            mv.addObject("clientId", profile.getClientProfileId());
            Page<DepartmentAccount> pages = this.departmentAccountService.findByClientProfile(pageable, profile);
            mv.addObject("number", pages.getNumber());
            mv.addObject("totalPages", pages.getTotalPages());
            mv.addObject("totalElements", pages.getTotalElements());
            mv.addObject("size", pages.getSize());
            mv.addObject("data", pages.getContent());
            mv.setViewName("clientviewdepartmentaccount");
            return mv;
        }
    }

    @RequestMapping(path = "/load/by/client", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchClient(@PageableDefault(value = 10) Pageable pageable, @RequestParam String clientName) {
        JobResponse result = new JobResponse();
        String client = clientName;
        ClientProfile profile = this.clientProfileService.findByCompanyName(client);
        if (profile == null) {
            result.setMessage("Client  " + clientName + " does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }

        List<Account> accounts = new ArrayList<>();
        accounts.add(this.accountService.findByClientProfile(profile));
        if (accounts.isEmpty()) {
            result.setMessage("Client : " + clientName + " has no accounts");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            result.setTitle("success");
            result.setMessage("Client : " + clientName + " data found");
            result.setResults(accounts);
        }
        return ResponseEntity.ok(result);
    }

}
