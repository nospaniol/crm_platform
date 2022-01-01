package com.crm.controller.admin;

import com.crm.generic.Nospaniol;
import com.crm.model.Cancel;
import com.crm.model.Citation;
import com.crm.model.CitationDispute;
import com.crm.model.CitationForm;
import com.crm.model.CitationType;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.User;
import com.crm.model.JobResponse;
import com.crm.model.Search;
import com.crm.model.State;
import com.crm.model.TransactionUpload;
import com.crm.model.Vehicle;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
@RequestMapping("citation")
public class CitationController extends Nospaniol {

    void loadCombos(ModelAndView mv) {
        mv.addObject("institution", institutionName());
        Map< String, String> clients = new HashMap<>();
        clients.put("", "select a client");
        this.clientProfileService.findAll().forEach((data) -> {
            clients.put(data.getCompanyName(), data.getCompanyName());
        });
        mv.addObject("clientMap", clients);

        Map< String, String> types = new HashMap<>();
        types.put("", "citation type");
        this.citationTypeService.findAll().forEach((data) -> {
            types.put(data.getCitationTypeName(), data.getCitationTypeName());
        });
        mv.addObject("typeMap", types);

        Map< String, String> states = new HashMap<>();
        states.put("", "select state");
        this.stateService.findAll().forEach((data) -> {
            states.put(data.getStateName(), data.getStateName());
        });
        mv.addObject("stateMap", states);

    }

    @RequestMapping(value = "save/{saveInfo}", method = RequestMethod.POST)
    public ModelAndView registerCitation(@ModelAttribute("citation") CitationForm p, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
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
        mv.setViewName("addcitation");
        if (p.getClient().isEmpty() || p.getLicensePlate().isEmpty() || p.getLicensePlateState().isEmpty() || p.getViolationNumber().isEmpty() || p.getViolationState().isEmpty() || p.getPayableTo().isEmpty() || p.getPaidDate().isEmpty() || p.getPaidAmount().isEmpty() || p.getCitationAmount().isEmpty() || p.getCitationStatus().isEmpty() || p.getCitationType().isEmpty() || p.getCitationDate().isEmpty()) {
            mv.addObject("messageType", "fail");
            mv.addObject("message", "Fill all required fields!");
            mv.addObject("citation", p);
            return mv;
        }
        ClientProfile profile = this.clientProfileService.findByCompanyName(p.getClient());
        if (profile == null) {
            mv.addObject("messageType", "fail");
            mv.addObject("message", "Select a client first!");
            mv.addObject("citation", p);
            return mv;
        }
        Department dep = null;
        List<Department> departments = this.departmentService.findByClientProfile(profile);
        if (departments == null || departments.isEmpty()) {

        } else {
            if (p.getDepartment().isEmpty()) {
                mv.addObject("messageType", "fail");
                mv.addObject("message", "Select a department!");
                mv.addObject("citation", p);
                return mv;
            }
            dep = this.departmentService.findByDepartmentNameAndClientProfile(p.getDepartment(), profile);
        }

        Vehicle veh = this.vehicleService.findByLicensePlate(p.getLicensePlate());
        State vehicleState = this.stateService.findByStateName(p.getLicensePlateState());
        State violationState = this.stateService.findByStateName(p.getViolationState());
        CitationType type = this.citationTypeService.findByCitationTypeName(p.getCitationType());

        Citation citation = this.citationService.findByCitationId(p.getCitationId());
        if (citation == null) {
            citation = new Citation();
            citation.setCitationId(seqGeneratorService.generateSequence(Citation.SEQUENCE_NAME));
            citation.setCreationDate(this.getCurrentDate());
            citation.setCreationTime(this.getCurrentTimestamp());
        }
        LocalDate citDate = LocalDate.parse(p.getCitationDate());
        citation.setCitationMonth(citDate.getMonth().toString());
        citation.setCitationYear(citDate.getYear());
        citation.setCitationAmount(Double.NaN);
        citation.setClientProfile(profile);
        citation.setDepartment(dep);
        citation.setVehicle(veh);
        citation.setViolationState(violationState);
        citation.setLicensePlateState(vehicleState);
        citation.setCitationAmount(Double.parseDouble(p.getCitationAmount()));
        citation.setCitationDate(this.getConvertedDate(p.getCitationDate()));
        citation.setCitationStatus(p.getCitationStatus());
        citation.setCitationType(type);
        citation.setPayableTo(p.getPayableTo());
        citation.setPaidDate(this.getConvertedDate(p.getPaidDate()));
        citation.setFeeAmount(Double.parseDouble(p.getFeeAmount()));
        citation.setViolationNumber(p.getViolationNumber());
        citation.setPaidAmount(Double.parseDouble(p.getPaidAmount()));
        citation.setUpdatedDate(this.getCurrentDate());
        citation.setUpdatedTime(this.getCurrentTimestamp());
        this.citationService.save(citation);
        mv.addObject("institution", institutionName());
        redir.addFlashAttribute("messageType", "success");
        redir.addFlashAttribute("message", "Successfully updated");
        mv.setViewName("redirect:/citation/view/" + citation.getCitationId());
        return mv;
    }

    @RequestMapping("/new_citation")
    public ModelAndView newCitation(HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
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
        this.loadCombos(mv);
        mv.addObject("institution", institutionName());
        mv.addObject("citation", new CitationForm());
        mv.setViewName("addcitation");
        return mv;
    }

    @RequestMapping("/view_citations")
    public ModelAndView viewCitations(HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
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
        loadCombos(mv);
        mv.addObject("transaction", new TransactionUpload());
        mv.addObject("institution", institutionName());
        mv.addObject("topic", "Recently added citations");
        mv.addObject("allCitations", this.citationService.findTop10ByOrderByCitationIdDesc());
        mv.setViewName("viewcitations");
        return mv;
    }

    @RequestMapping(value = {"active_disputes"}, method = RequestMethod.GET)
    public ModelAndView viewDisputes(RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
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
        loadCombos(mv);
        mv.addObject("institution", institutionName());
        mv.addObject("topic", "Recently added citations");
        mv.addObject("allDisputes", this.citationDisputeService.findByDisputeStatusOrderByDisputeIdDesc("ACTIVE"));
        mv.setViewName("viewcitationdisputes");
        return mv;
    }

    @RequestMapping(value = "/all/added/citations", method = RequestMethod.GET)
    public ModelAndView allCitations(@PageableDefault(value = 10) Pageable pageable, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        loadCombos(mv);
        mv.addObject("citation", new CitationForm());
        mv.addObject("institution", institutionName());
        mv.addObject("topic", "All citations");
        mv.addObject("allCitations", this.citationService.findTop10ByOrderByCitationIdDesc());
        mv.setViewName("allcitations");
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
        Citation citation = this.citationService.findByCitationId(p.getCancelItem());
        if (citation == null) {
            result.setMessage("Citation does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            this.citationService.delete(citation);
            result.setMessage("citation deleted!");
            result.setTitle("success");
            return ResponseEntity.ok(result);
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
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Administrator privileges only!");
            return mv;
        }
        Citation citation = this.citationService.findByCitationId(id);
        if (citation == null) {
            redir.addFlashAttribute("message", "No citation found");
            redir.addFlashAttribute("messageType", "fail");
            mv.setViewName("redirect:/citation/new_citation/");
            return mv;
        } else {
            ClientProfile profile = citation.getClientProfile();
            mv.addObject("companyLogo", Base64.getEncoder().encodeToString(profile.getCompanyLogo().getData()));
            mv.addObject("institution", institutionName());
            mv.addObject("citation", citation);
            mv.setViewName("citation");
            return mv;
        }
    }

    @RequestMapping(path = "/load/by/client", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchClient(HttpSession session, @PageableDefault(value = 10) Pageable pageable, @RequestParam String clientName) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
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
        List<Citation> citations = this.citationService.findByClientProfileOrderByCitationIdDesc(profile);
        if (citations == null || citations.isEmpty()) {
            result.setMessage("Client : " + clientName + " has no citations");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            result.setTitle("success");
            result.setMessage("Client : " + clientName + " data found");
            result.setResults(citations);
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = "/load/by/client/department", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchClientDepartment(HttpSession session, @PageableDefault(value = 10) Pageable pageable, @RequestParam String clientName, @RequestParam String departmentName) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        String client = clientName;
        String department = departmentName;
        ClientProfile profile = this.clientProfileService.findByCompanyName(client);
        if (profile == null) {
            result.setMessage("client citation does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        Department dep = this.departmentService.findByDepartmentNameAndClientProfile(department, profile);
        if (dep == null) {
            result.setMessage("department citation does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        List<Citation> citations = this.citationService.findByDepartmentOrderByCitationIdDesc(dep);
        if (citations == null || citations.isEmpty()) {
            result.setMessage("No department data found today");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            result.setTitle("success");
            result.setMessage("Department data found");
            result.setResults(citations);
        }
        return ResponseEntity.ok(result);
    }
    //*********DISPUTES

    @RequestMapping(path = "/reverse/dispute/{disputeId}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> reverseDisputeCitation(HttpSession session, @PathVariable("disputeId") Long disputeId) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        CitationDispute citationDispute = this.citationDisputeService.findByDisputeId(disputeId);
        if (citationDispute == null) {
            result.setMessage("Invalid dispute, please check and try again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            Citation citation = citationDispute.getCitation();
            this.citationService.delete(citation);
            this.citationDisputeService.delete(citationDispute);
            result.setTitle("success");
            result.setMessage("Citation reversal completed successfully!");
            return ResponseEntity.ok(result);
        }
    }

    @RequestMapping(path = "/close/dispute/{disputeId}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> closeDisputes(HttpSession session, @PathVariable("disputeId") Long disputeId) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        CitationDispute citationDispute = this.citationDisputeService.findByDisputeId(disputeId);
        if (citationDispute == null) {
            result.setMessage("Invalid dispute, please check and try again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            Citation citation = citationDispute.getCitation();
            citation.setCitationDispute(false);
            this.citationService.save(citation);
            this.citationDisputeService.delete(citationDispute);
            result.setTitle("success");
            result.setMessage("Citation reversal completed successfully!");
            return ResponseEntity.ok(result);
        }
    }

}
