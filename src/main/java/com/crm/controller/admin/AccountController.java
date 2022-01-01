package com.crm.controller.admin;

import com.crm.generic.Nospaniol;
import com.crm.model.Cancel;
import com.crm.model.ClientProfile;
import com.crm.model.Account;
import com.crm.model.JobResponse;
import com.crm.model.AccountUpload;
import com.crm.model.Department;
import com.crm.model.DepartmentAccount;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("account")
public class AccountController extends Nospaniol {

    int counter = 0;
    int header = 0;

    @RequestMapping(value = {"view_accounts"}, method = RequestMethod.GET)
    public ModelAndView viewAccounts(@PageableDefault(value = 10) Pageable pageable, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
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
        this.clientProfileService.findAll().stream().filter((profile) -> (this.accountService.findByClientProfile(profile) == null)).map((profile) -> {
            Account account = new Account();
            account.setAccountId(seqGeneratorService.generateSequence(Account.SEQUENCE_NAME));
            account.setClientProfile(profile);
            account.setStatus(true);
            account.setUpdatedDate(this.getCurrentDate());
            account.setCreationDate(this.getCurrentDate());
            account.setCreationTime(this.getCurrentTimestamp());
            account.setUpdatedTime(this.getCurrentTimestamp());
            return account;
        }).map((account) -> {
            account.setAmount(0.00);
            return account;
        }).forEachOrdered((account) -> {
            this.accountService.save(account);
        });

        mv.addObject("topic", "CLIENT ACCOUNTS");
        mv.addObject("institution", institutionName());
        Page<Account> pages = this.accountService.findAll(pageable);
        mv.addObject("number", pages.getNumber());
        mv.addObject("totalPages", pages.getTotalPages());
        mv.addObject("totalElements", pages.getTotalElements());
        mv.addObject("size", pages.getSize());
        mv.addObject("data", pages.getContent());
        LOG.info("\n Fetching  accounts \n".toUpperCase());
        mv.addObject("account", new AccountUpload());
        mv.setViewName("viewaccounts");
        return mv;
    }

    @RequestMapping("/view/department/{id}")
    public ModelAndView editAccount(@PathVariable("id") Long id, @PageableDefault(value = 10) Pageable pageable, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
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
        ClientProfile profile = this.clientProfileService.findByClientProfileId(id);
        if (profile == null) {
            redir.addFlashAttribute("Client profile not found");
            redir.addFlashAttribute("fail");
            mv.setViewName("viewdepartmentaccount");
            return mv;
        } else {
            List<Department> departments = this.departmentService.findByClientProfile(profile);
            if (departments == null || departments.isEmpty()) {

            } else {
                for (Department department : departments) {
                    DepartmentAccount account = this.departmentAccountService.findByClientProfileAndDepartment(profile, department);
                    if (account == null) {
                        account = new DepartmentAccount();
                        account.setClientProfile(profile);
                        account.setDepartment(department);
                        account.setAccountId(seqGeneratorService.generateSequence(Account.SEQUENCE_NAME));
                        account.setAmount(0.00);
                        account.setStatus(true);
                        account.setUpdatedDate(this.getCurrentDate());
                        account.setCreationDate(this.getCurrentDate());
                        account.setCreationTime(this.getCurrentTimestamp());
                        account.setUpdatedTime(this.getCurrentTimestamp());
                        this.departmentAccountService.save(account);
                    }
                }
            }
            mv.addObject("account", new AccountUpload());
            mv.addObject("topic", "DEPARTMENTAL ACCOUNTS FOR " + profile.getCompanyName());
            mv.addObject("clientId", profile.getClientProfileId());
            Page<DepartmentAccount> pages = this.departmentAccountService.findByClientProfile(pageable, profile);
            mv.addObject("number", pages.getNumber());
            mv.addObject("totalPages", pages.getTotalPages());
            mv.addObject("totalElements", pages.getTotalElements());
            mv.addObject("size", pages.getSize());
            mv.addObject("data", pages.getContent());
            mv.addObject("institution", institutionName());
            mv.setViewName("viewdepartmentaccount");
            return mv;
        }
    }

    void loadCombos(ModelAndView mv) {
        mv.addObject("institution", institutionName());
        Map< String, String> clients = new HashMap<>();
        clients.put("", "Select a client");
        this.clientProfileService.findAll().forEach((profile) -> {
            clients.put(profile.getCompanyName(), profile.getCompanyName());
        });
        mv.addObject("clientMap", clients);

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
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            result.setMessage("Administrator privileges only!!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        Account account = this.accountService.findByAccountId(p.getCancelItem());
        if (account == null) {
            result.setMessage("Account does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            this.accountService.delete(account);
            result.setMessage("Account deleted!");
            result.setTitle("success");
            return ResponseEntity.ok(result);
        }
    }

    @RequestMapping(path = "/load/by/client", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchClient(@PageableDefault(value = 10) Pageable pageable, HttpSession session, @RequestParam String clientName) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            result.setMessage("Administrator privileges only!!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
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

    @RequestMapping(path = "/deposit/{amount}/{accountId}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> depositAccount(HttpSession session, @PathVariable Double amount, @PathVariable Long accountId) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        ClientProfile profile = this.clientProfileService.findByClientProfileId(accountId);
        if (profile == null) {
            result.setMessage("Client does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        Account account = this.accountService.findByClientProfile(profile);
        if (account == null) {
            account = new Account();
            account.setAccountId(seqGeneratorService.generateSequence(Account.SEQUENCE_NAME));
            account.setClientProfile(profile);
            account.setAmount(this.getTwoDecimal(amount));
            account.setStatus(true);
            account.setUpdatedDate(this.getCurrentDate());
            account.setCreationDate(this.getCurrentDate());
            account.setCreationTime(this.getCurrentTimestamp());
            account.setUpdatedTime(this.getCurrentTimestamp());
        } else {
            Double currentAmount = this.getTwoDecimal(account.getAmount() + amount);
            account.setAmount(currentAmount);
            account.setUpdatedDate(this.getCurrentDate());
            account.setUpdatedTime(this.getCurrentTimestamp());
        }
        this.accountService.save(account);
        result.setTitle("success");
        result.setMessage("Account updated successfully");
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = "/department/deposit/{amount}/{accountId}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> depositDepartmentAccount(HttpSession session, @PathVariable Double amount, @PathVariable Long accountId) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            result.setMessage("Administrator privileges only!!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }

        DepartmentAccount account = this.departmentAccountService.findByAccountId(accountId);
        Double currentAmount = this.getTwoDecimal(account.getAmount() + amount);
        account.setAmount(currentAmount);
        account.setUpdatedDate(this.getCurrentDate());
        account.setUpdatedTime(this.getCurrentTimestamp());
        this.departmentAccountService.save(account);
        result.setTitle("success");
        result.setMessage("Account updated successfully");
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = "/credit/{amount}/{accountId}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> creditAccount(HttpSession session, @PathVariable Double amount, @PathVariable Long accountId) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            result.setMessage("Administrator privileges only!!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        Account account = this.accountService.findByAccountId(accountId);
        Double currentAmount = this.getTwoDecimal(account.getAmount() - amount);
        account.setAmount(currentAmount);
        account.setUpdatedDate(this.getCurrentDate());
        account.setUpdatedTime(this.getCurrentTimestamp());
        this.accountService.save(account);
        result.setTitle("success");
        result.setMessage("Account updated successfully");
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = "/department/credit/{amount}/{accountId}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> creditDepartmentAccount(HttpSession session, @PathVariable Double amount, @PathVariable Long accountId) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            result.setMessage("Administrator privileges only!!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        DepartmentAccount account = this.departmentAccountService.findByAccountId(accountId);
        Double currentAmount = this.getTwoDecimal(account.getAmount() - amount);
        account.setAmount(currentAmount);
        account.setUpdatedDate(this.getCurrentDate());
        account.setUpdatedTime(this.getCurrentTimestamp());
        this.departmentAccountService.save(account);
        result.setTitle("success");
        result.setMessage("Account updated successfully");
        return ResponseEntity.ok(result);
    }

}
