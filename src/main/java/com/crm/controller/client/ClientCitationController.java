package com.crm.controller.client;

import com.crm.generic.Nospaniol;
import com.crm.model.Citation;
import com.crm.model.CitationDispute;
import com.crm.model.CitationForm;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.JobResponse;
import com.crm.model.TransactionUpload;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("client_citation")
public class ClientCitationController extends Nospaniol {

    void loadCombos(ModelAndView mv) {
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

    @RequestMapping(value = {"disputes"}, method = RequestMethod.GET)
    public ModelAndView viewDisputes(@PageableDefault(value = 100) Pageable pageable, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        mv.addObject("menutype", "citation");
        session.setAttribute("citation_start_date", "NONE");
        session.setAttribute("citation_end_date", "NONE");
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                String departmentMode = (String) session.getAttribute("department_mode");
                String departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));

                    mv.addObject("allDisputes", this.citationDisputeService.findByDepartmentAndDisputeStatusOrderByDisputeIdDesc(department, "ACTIVE"));
                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    //mv.addObject("institution", profile.getCompanyName());

                    mv.addObject("allDisputes", this.citationDisputeService.findByClientProfileAndDisputeStatusOrderByDisputeIdDesc(profile, "ACTIVE"));
                }
                break;
            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));

                mv.addObject("allDisputes", this.citationDisputeService.findByDepartmentAndDisputeStatusOrderByDisputeIdDesc(department, "ACTIVE"));
                break;
            default:
                LOG.info("USER ACCOUNT UNKNOWN");
        }
        loadCombos(mv);
        mv.setViewName("clientviewcitationdisputes");
        return mv;
    }

    @RequestMapping("/view_citations")
    public ModelAndView viewCitations(HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        loadCombos(mv);
        mv.addObject("menutype", "citation");
        mv.addObject("transaction", new TransactionUpload());
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        mv.addObject("topic", "Recently added citations");
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                String departmentMode = (String) session.getAttribute("department_mode");
                String departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));

                    mv.addObject("allCitations", this.citationService.findTop10ByDepartmentOrderByCitationIdDesc(department));
                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    //mv.addObject("institution", profile.getCompanyName());

                    mv.addObject("allCitations", this.citationService.findTop10ByClientProfileOrderByCitationIdDesc(profile));
                }
                break;
            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));

                mv.addObject("allCitations", this.citationService.findTop10ByDepartmentOrderByCitationIdDesc(department));
                break;
            default:
                LOG.info("USER ACCOUNT UNKNOWN");
        }

        mv.setViewName("clientviewcitations");
        return mv;
    }

    @RequestMapping(value = "/all/added/citations", method = RequestMethod.GET)
    public ModelAndView allCitations(@PageableDefault(value = 10) Pageable pageable, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        loadCombos(mv);
        mv.addObject("menutype", "citation");
        mv.addObject("citation", new CitationForm());
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        mv.addObject("topic", "All citations");
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                String departmentMode = (String) session.getAttribute("department_mode");
                String departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));

                    mv.addObject("allCitations", this.citationService.findByDepartmentOrderByCitationIdDesc(department));
                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    //mv.addObject("institution", profile.getCompanyName());

                    mv.addObject("allCitations", this.citationService.findByClientProfileOrderByCitationIdDesc(profile));
                }
                break;
            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));

                mv.addObject("allCitations", this.citationService.findByDepartmentOrderByCitationIdDesc(department));
                break;
            default:
                LOG.info("USER ACCOUNT UNKNOWN");
        }
        mv.setViewName("allclientcitations");
        return mv;
    }

    @RequestMapping("/view/{id}")
    public ModelAndView viewProfile(@PathVariable("id") Long id, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        mv.addObject("menutype", "citation");
        ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
        //mv.addObject("institution", profile.getCompanyName());

        Citation citation = this.citationService.findByCitationId(id);
        if (citation == null) {
            redir.addFlashAttribute("message", "No citation found");
            redir.addFlashAttribute("messageType", "fail");
            mv.setViewName("redirect:/citation/new_citation/");
            return mv;
        } else {

            //mv.addObject("institution", profile.getCompanyName());
            mv.addObject("citation", citation);
            mv.setViewName("clientcitation");
            return mv;
        }
    }

    @RequestMapping(path = "/load/by/client/department", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchClientDepartment(HttpSession session, @RequestParam String departmentName) {
        JobResponse result = new JobResponse();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            result.setMessage("Invalid session!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
        if (profile == null) {
            result.setMessage("client citation does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        Department dep = this.departmentService.findByDepartmentNameAndClientProfile(departmentName, profile);
        if (dep == null) {
            result.setMessage("department citation does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        List<Citation> citations = this.citationService.findByDepartment(dep);
        if (citations == null || citations.isEmpty()) {
            result.setMessage("No department data found");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            result.setTitle("success");
            result.setMessage("Department data found");
            result.setResults(citations);
        }
        return ResponseEntity.ok(result);
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

        mv.addObject("menutype", "citation");
        loadCombos(mv);
        mv.addObject("institution", institutionName());
        mv.addObject("topic", "Recently added citations");
        mv.addObject("allDisputes", this.citationDisputeService.findByDisputeStatusOrderByDisputeIdDesc("ACTIVE"));
        mv.setViewName("viewcitationdisputes");
        return mv;
    }

    @RequestMapping(path = "/post/dispute/{citationId}/{dispute}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> postDisputes(HttpSession session, @PathVariable("citationId") Long citationId, @PathVariable("dispute") String dispute) {
        JobResponse result = new JobResponse();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        Citation citation = this.citationService.findByCitationId(citationId);
        if (citation == null) {
            result.setTitle("fail");
            LOG.info("invalid citation");
            result.setMessage("Failed , invalid citation!");
            return ResponseEntity.ok(result);
        }
        CitationDispute citationDispute = this.citationDisputeService.findByCitation(citation);
        if (citationDispute == null) {
            citationDispute = new CitationDispute();
            citationDispute.setDisputeId(seqGeneratorService.generateSequence(citationDispute.SEQUENCE_NAME));
            citationDispute.setCitation(citation);
            citationDispute.setDisputeStatus("ACTIVE");
            citationDispute.setDispute(dispute);
            citationDispute.setClientProfile(citation.getClientProfile());
            citationDispute.setDepartment(citation.getDepartment());
            citationDispute.setStatus(true);
            citationDispute.setUpdatedDate(this.getCurrentDate());
            citationDispute.setCreationDate(this.getCurrentDate());
            citationDispute.setCreationTime(this.getCurrentTimestamp());
            citationDispute.setUpdatedTime(this.getCurrentTimestamp());
            citation.setCitationDispute(true);
            citationDisputeService.save(citationDispute);
            this.citationService.save(citation);
            LOG.info("citation dispute submitted successfully");
            result.setTitle("success");
            result.setMessage("Citation dispute submitted successfully");
        } else {
            result.setTitle("fail");
            LOG.info("There is an existing dispute related to this citation");
            result.setMessage("Failed , The dispute already exists!");
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = "/load/by/range/{startDate}/{endDate}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchDate(HttpSession session, @PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) {
        JobResponse result = new JobResponse();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        Date end_Date = this.getConvertedDate(endDate);
        Date start_Date = this.getConvertedDate(startDate);
        session.setAttribute("citation_start_date", startDate);
        session.setAttribute("citation_end_date", endDate);
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                String departmentMode = (String) session.getAttribute("department_mode");
                String departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                    List<Citation> citations = this.getDepartmentCitationRange(department, start_Date, end_Date);
                    if (citations == null || citations.isEmpty()) {
                        result.setMessage("No citations uploaded on " + String.valueOf(department.getDepartmentName()));
                        result.setTitle("fail");
                        return ResponseEntity.ok(result);
                    } else {
                        result.setTitle("success");
                        LOG.info("citations range data found");
                        result.setMessage("Citations data found");
                        result.setResults(citations);
                        LOG.info("citations fetched successfully");
                    }
                } else {
                    List<Citation> citations = this.getCLientProfileCitationRange(profile, start_Date, end_Date);
                    if (citations == null || citations.isEmpty()) {
                        result.setMessage("No citations uploaded on " + String.valueOf(profile.getCompanyName()));
                        result.setTitle("fail");
                        return ResponseEntity.ok(result);
                    } else {
                        result.setTitle("success");
                        LOG.info("citations range data found");
                        result.setMessage("Citations data found");
                        result.setResults(citations);
                    }
                }
                break;
            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                List<Citation> citations = this.getDepartmentCitationRange(department, start_Date, end_Date);
                if (citations == null || citations.isEmpty()) {
                    result.setMessage("No citations uploaded on " + String.valueOf(department.getDepartmentName()));
                    result.setTitle("fail");
                    return ResponseEntity.ok(result);
                } else {
                    result.setTitle("success");
                    LOG.info("citations range data found");
                    result.setMessage("Citations data found");
                    result.setResults(citations);
                }
                break;
            default:
                result.setMessage("USER ACCOUNT UNKNOWN");
                LOG.info("USER ACCOUNT UNKNOWN");
        }
        LOG.info("returning search results");
        return ResponseEntity.ok(result);
    }

    private List<Citation> getCLientProfileCitationRange(ClientProfile profile, Date startDate, Date endDate) {
        Query query = new Query();
        query.addCriteria(Criteria.where("clientProfile").is(profile));
        query.addCriteria(Criteria.where("citationDate").gte(startDate).lte(endDate));
        List<Citation> citations = mongoTemplate.find(query, Citation.class);
        return citations;
    }

    private List<Citation> getDepartmentCitationRange(Department department, Date startDate, Date endDate) {
        Query query = new Query();
        query.addCriteria(Criteria.where("department").is(department));
        query.addCriteria(Criteria.where("citationDate").gte(startDate).lte(endDate));
        List<Citation> citations = mongoTemplate.find(query, Citation.class);
        return citations;
    }

    @RequestMapping(path = "/close/dispute/{disputeId}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> closeDisputes(HttpSession session, @PathVariable("disputeId") Long disputeId) {
        JobResponse result = new JobResponse();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
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
            String user_type = (String) session.getAttribute("ns_user");
            switch (user_type) {
                case "CLIENT":
                    String departmentMode = (String) session.getAttribute("department_mode");
                    String departmentId = (String) session.getAttribute("department_id");
                    if (departmentMode != null && departmentMode.equals("YES")) {
                        Department department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                        if (department.getDepartmentName().equals(citationDispute.getDepartment().getDepartmentName())) {
                            Citation citation = citationDispute.getCitation();
                            if (citation != null) {
                                citation.setCitationDispute(false);
                                this.citationService.save(citation);
                            }
                            this.citationDisputeService.delete(citationDispute);
                            result.setTitle("success");
                            result.setMessage("Dispute closed successfully!");
                        } else {
                            result.setMessage("You can only cancel your disputes!");
                            result.setTitle("fail");
                        }
                    } else {
                        ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                        if (profile.getCompanyName().equals(citationDispute.getClientProfile().getCompanyName())) {
                            Citation citation = citationDispute.getCitation();
                            if (citation != null) {
                                citation.setCitationDispute(false);
                                this.citationService.save(citation);
                            }
                            this.citationDisputeService.delete(citationDispute);
                            result.setTitle("success");
                            result.setMessage("Dispute closed successfully!");
                        } else {
                            result.setMessage("You can only cancel your disputes!");
                            result.setTitle("fail");
                        }
                    }
                    break;
                case "DEPARTMENT":
                    Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                    if (department.getDepartmentName().equals(citationDispute.getDepartment().getDepartmentName())) {
                        Citation citation = citationDispute.getCitation();
                        if (citation != null) {
                            citation.setCitationDispute(false);
                            this.citationService.save(citation);
                        }
                        this.citationDisputeService.delete(citationDispute);
                        result.setTitle("success");
                        result.setMessage("Dispute closed successfully!");
                    } else {
                        result.setMessage("You can only cancel your disputes!");
                        result.setTitle("fail");
                    }
                default:
                    LOG.info("USER ACCOUNT UNKNOWN");
            }
            return ResponseEntity.ok(result);
        }
    }

}
