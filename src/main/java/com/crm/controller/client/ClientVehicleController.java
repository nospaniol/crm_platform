package com.crm.controller.client;

import com.crm.enums.ExportReportType;
import com.crm.generic.ExcelVehicleExporter;
import com.crm.generic.Nospaniol;
import static com.crm.generic.Nospaniol.LOG;
import com.crm.model.Axle;
import com.crm.model.Cancel;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.Vehicle;
import com.crm.model.JobResponse;
import com.crm.model.Search;
import com.crm.model.StoreLocation;
import com.crm.model.TagType;
import com.crm.model.Transaction;
import com.crm.model.VehicleFile;
import com.crm.model.VehicleForm;
import com.crm.model.VehicleUpload;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;

@Controller
@RequestMapping("client_vehicle")
public class ClientVehicleController extends Nospaniol {

    @RequestMapping(value = {"view_vehicle"}, method = RequestMethod.GET)
    public ModelAndView viewVehicles(@PageableDefault(value = 100) Pageable pageable, RedirectAttributes redir, HttpServletRequest req, HttpSession session, HttpServletResponse response) {
        // response.addHeader("Cache-Control", "public,immutable,max-age=600, must-revalidate, no-transform");
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "vehicle");
        String clientId = (String) req.getSession().getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }

        mv.setViewName("clientviewvehicles");
        loadCombos(mv);
        mv.addObject("topic", "Recently added vehicles".toUpperCase());
        mv.addObject("vehicle", new VehicleUpload());
        String user_type = (String) req.getSession().getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                String departmentMode = (String) session.getAttribute("department_mode");
                String departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                    Page<Vehicle> pages = this.vehicleService.findByDepartmentAndVinNotAndStateNot(pageable, department, null, null);
                    mv.addObject("number", pages.getNumber());
                    mv.addObject("totalPages", pages.getTotalPages());
                    mv.addObject("totalElements", pages.getTotalElements());
                    mv.addObject("size", pages.getSize());
                    List<Vehicle> vehicles = pages.getContent();
                    List<Vehicle> finalVehicles = new ArrayList<>();
                    finalVehicles.addAll(vehicles);
                    finalVehicles.addAll(vehicles);
                    mv.addObject("data", finalVehicles);
                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    Query query = new Query();
                    query.addCriteria(Criteria.where("clientProfile").is(profile));
                    query.addCriteria(Criteria.where("state").ne(null));
                    query.with(pageable);
                    //query.with(Sort.by("vehicleId").descending());
                    List<Vehicle> vehicleReport = mongoTemplate.find(query, Vehicle.class);
                    long page_count = this.vehicleService.countByClientProfileAndVinNotAndStateNot(profile, null, null);
                    List<Vehicle> finalVehicles = new ArrayList<>();
                    finalVehicles.addAll(vehicleReport);
                    mv.addObject("totalPages", page_count / 100);
                    mv.addObject("size", 100);
                    mv.addObject("data", finalVehicles);
                }
                break;

            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                Page<Vehicle> pages = this.vehicleService.findByDepartmentAndVinNotAndStateNot(pageable, department, "NEW", null);
                mv.addObject("number", pages.getNumber());
                mv.addObject("totalPages", pages.getTotalPages());
                mv.addObject("totalElements", pages.getTotalElements());
                mv.addObject("size", pages.getSize());
                List<Vehicle> vehicles = pages.getContent();
                List<Vehicle> finalVehicles = new ArrayList<>();
                finalVehicles.addAll(vehicles);
                finalVehicles.addAll(vehicles);
                mv.addObject("data", finalVehicles);
                break;
            default:
                LOG.info("USER ACCOUNT UNKNOWN");
        }

        return mv;
    }

    @RequestMapping(path = {"/load/printed/info/"}, produces = {"application/json; charset=UTF-8"}, method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<?> printedInfo(HttpSession session) {
        ClientProfile profile;
        String departmentMode, departmentId;
        Department department;
        List<Vehicle> vehicles = null;
        double total;
        JobResponse result = new JobResponse();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }

        int year = Integer.valueOf((String) session.getAttribute("year_mode"));
        String month = (String) session.getAttribute("month_mode");
        LOG.info("DATES USED" + String.valueOf(year) + month);
        session.setAttribute("transaction_start_date", null);
        session.setAttribute("transaction_end_date", null);
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                departmentMode = (String) session.getAttribute("department_mode");
                departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department1 = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                    Query query = new Query();
                    query.addCriteria(Criteria.where("department").is(department1));
                    query.addCriteria(Criteria.where("state").ne(null));
                    vehicles = mongoTemplate.find(query, Vehicle.class);

                } else {
                    profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    Query query = new Query();
                    query.addCriteria(Criteria.where("clientProfile").is(profile));
                    query.addCriteria(Criteria.where("state").ne(null));
                    query.with(Sort.by("vehicleId").descending());
                    vehicles = mongoTemplate.find(query, Vehicle.class);
                }
                break;
            case "DEPARTMENT":
                department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                Query query = new Query();
                query.addCriteria(Criteria.where("department").is(department));
                query.addCriteria(Criteria.where("state").ne(null));
                vehicles = mongoTemplate.find(query, Vehicle.class);
                break;
        }

        result.setMessage("VEHICLE LIST");
        result.setResults(vehicles);
        LOG.info("returning search results");
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = {"/count/printing/"}, produces = {"application/json; charset=UTF-8"}, method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<?> countPrinting(@ModelAttribute("search") Search search, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        long vehicle_counter = countPrintingInfo(session);
        LOG.info("TRANSACTIONS COUNT :: " + String.valueOf(vehicle_counter));
        if (vehicle_counter == 0) {
            result.setTotal(Double.valueOf(vehicle_counter));
            result.setMessage("No records found with the specified criteria!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);

        }
        if (vehicle_counter > 30000) {
            result.setTotal(Double.valueOf(vehicle_counter));
            result.setMessage("Please select records less than 30,000 using the date range, records found are : " + String.valueOf(vehicle_counter));
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            result.setTotal(Double.valueOf(vehicle_counter));
            result.setMessage("successful records found : " + String.valueOf(vehicle_counter));
            result.setTitle("success");
            return ResponseEntity.ok(result);
        }
    }

    public long countPrintingInfo(HttpSession session) {
        ClientProfile profile;
        String departmentMode, departmentId;
        Department department;
        long vehicles = 0;
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            return vehicles;
        }

        int year = Integer.valueOf((String) session.getAttribute("year_mode"));
        String month = (String) session.getAttribute("month_mode");
        LOG.info("DATES USED" + String.valueOf(year) + month);
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                departmentMode = (String) session.getAttribute("department_mode");
                departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                    vehicles = this.vehicleService.countByDepartmentAndVinNotAndStateNot(department, null, null);

                } else {
                    profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    vehicles = this.vehicleService.countByClientProfileAndVinNotAndStateNot(profile, null, null);;
                }
                break;
            case "DEPARTMENT":
                department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                vehicles = this.vehicleService.countByDepartmentAndVinNotAndStateNot(department, null, null);
                break;
        }
        return vehicles;
    }

    @RequestMapping(path = {"/search/vehicle/"}, produces = {"application/json; charset=UTF-8"}, method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<?> searchVehicle(@ModelAttribute("search") Search search, HttpSession session,
            HttpServletRequest req, RedirectAttributes redir
    ) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        String searchItem = search.getSearchItem().trim();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        ClientProfile profile = null;
        Department department = null;
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                String departmentMode = (String) session.getAttribute("department_mode");
                String departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                    profile = department.getClientProfile();
                    List<Vehicle> tolls = this.vehicleService.findByClientProfileAndTollTagIdLike(profile, searchItem);
                    if (tolls == null || tolls.isEmpty()) {
                        List<Vehicle> plates = this.vehicleService.findByClientProfileAndLicensePlateLike(profile, searchItem);
                        if (plates == null || plates.isEmpty()) {
                            result.setMessage("<b>" + search.getSearchItem() + "</b> not found in the system");
                            result.setTitle("fail");
                            return ResponseEntity.ok(result);
                        } else {
                            result.setMessage("");
                            result.setTitle("success");
                            result.setResults(plates);
                            return ResponseEntity.ok(result);
                        }
                    } else {
                        result.setMessage("");
                        result.setTitle("success");
                        result.setResults(tolls);
                        return ResponseEntity.ok(result);
                    }
                } else {
                    profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    List<Vehicle> tolls = this.vehicleService.findByClientProfileAndTollTagIdLike(profile, searchItem);
                    if (tolls == null || tolls.isEmpty()) {
                        List<Vehicle> plates = this.vehicleService.findByClientProfileAndLicensePlateLike(profile, searchItem);
                        if (plates == null || plates.isEmpty()) {
                            List<Vehicle> vins = this.vehicleService.findByClientProfileAndVinLike(profile, searchItem);
                            if (vins == null || vins.isEmpty()) {
                                result.setMessage("" + search.getSearchItem() + " not found in the system");
                                result.setTitle("fail");
                                return ResponseEntity.ok(result);
                            } else {
                                result.setMessage("");
                                result.setTitle("success");
                                result.setResults(vins);
                                return ResponseEntity.ok(result);
                            }
                        } else {
                            result.setMessage("");
                            result.setTitle("success");
                            result.setResults(plates);
                            return ResponseEntity.ok(result);
                        }
                    } else {
                        result.setMessage("");
                        result.setTitle("success");
                        result.setResults(tolls);
                        return ResponseEntity.ok(result);
                    }
                }
            case "DEPARTMENT":
                department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                profile = department.getClientProfile();
                List<Vehicle> tolls = this.vehicleService.findByClientProfileAndTollTagIdLike(profile, searchItem);
                if (tolls == null || tolls.isEmpty()) {
                    List<Vehicle> plates = this.vehicleService.findByClientProfileAndLicensePlateLike(profile, searchItem);
                    if (plates == null || plates.isEmpty()) {
                        result.setMessage("" + search.getSearchItem() + " not found in the system");
                        result.setTitle("fail");
                        return ResponseEntity.ok(result);
                    } else {
                        result.setMessage("");
                        result.setTitle("success");
                        result.setResults(plates);
                        return ResponseEntity.ok(result);
                    }
                } else {
                    result.setMessage("");
                    result.setTitle("success");
                    result.setResults(tolls);
                    return ResponseEntity.ok(result);
                }
            default:
                LOG.info("USER ACCOUNT UNKNOWN");
        }
        return ResponseEntity.ok(result);
    }

    void loadCombos(ModelAndView mv
    ) {
        mv.addObject("states", this.getUsStates());
        mv.addObject("tagTypes", this.tagTypeService.findAll());
        mv.addObject("axles", this.axleService.findAll());
    }

    @RequestMapping(value = "register/{save}", method = RequestMethod.POST)
    public ModelAndView registerVehicle(@ModelAttribute("vehicle") VehicleForm p, MultipartFile file,
            RedirectAttributes redir, HttpServletRequest req,
            HttpSession session
    ) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "vehicle");

        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        loadCombos(mv);
        ClientProfile profile = null;
        Department department = null;
        mv.addObject("vehicle", new VehicleUpload());
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                String departmentMode = (String) session.getAttribute("department_mode");
                String departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                    profile = department.getClientProfile();
                } else {
                    profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    mv.addObject("departments", this.departmentService.countByClientProfile(profile));

                    //mv.addObject("institution", profile.getCompanyName());
                    Map< String, String> departments = new HashMap<>();
                    departments.put("", "Select a department");
                    this.departmentService.findByClientProfile(profile).forEach((departmentss) -> {
                        departments.put(departmentss.getDepartmentName(), departmentss.getDepartmentName());
                    });
                    mv.addObject("departmentMap", departments);
                    mv.addObject("vehicle", new VehicleUpload());
                }
                break;
            case "DEPARTMENT":
                department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));

                profile = department.getClientProfile();

                mv.addObject("vehicle", new VehicleUpload());
                break;
            default:
                LOG.info("USER ACCOUNT UNKNOWN");
        }

        mv.setViewName("clientaddvehicle");
        if (p.getLicensePlate().isEmpty()) {
            mv.addObject("messageType", "fail");
            mv.addObject("message", "License plate not found!");
            mv.addObject("vehicle", p);
            return mv;
        }
        String plate = p.getLicensePlate().toUpperCase();
        String tolltag = p.getTollTagId().toUpperCase();
        Vehicle vehicle = this.vehicleService.findByVehicleId(p.getVehicleId());
        if (vehicle == null) {
            LOG.info(":::NEW VEHICLE ADDITION:::");
            vehicle = new Vehicle();
            if (this.vehicleService.findByLicensePlate(plate) != null) {
                mv.addObject("messageType", "fail");
                mv.addObject("message", "This license plate has already been registered!");
                mv.addObject("vehicle", p);
                return mv;
            }
            vehicle.setVehicleId(seqGeneratorService.generateSequence(Vehicle.SEQUENCE_NAME));
            p.setTollTagId(tolltag);
            p.setLicensePlate(plate);
            vehicle.setUpdatedDate(this.getCurrentDate());
            vehicle.setCreationDate(this.getCurrentDate());
            vehicle.setCreationTime(this.getCurrentTimestamp());
            vehicle.setUpdatedTime(this.getCurrentTimestamp());
        } else {
            vehicle.setUpdatedDate(this.getCurrentDate());
            vehicle.setUpdatedTime(this.getCurrentTimestamp());
        }
        vehicle.setColor(p.getColor());
        vehicle.setMake(p.getMake());
        vehicle.setModel(p.getModel());
        if (!p.getStartDate().isEmpty()) {
            vehicle.setStartDate(this.getConvertedDate(p.getStartDate()));
        }
        if (!p.getEndDate().isEmpty()) {
            vehicle.setEndDate(this.getConvertedDate(p.getEndDate()));
        }
        if (p.getState().isEmpty() || p.getState().contains("Select")) {
        } else {
            LOG.info("STATE:::" + p.getState());
            vehicle.setState(p.getState());
        }
        if (p.getDepartment() != null) {
            if (!p.getDepartment().isEmpty()) {
                LOG.info("DEPARTMENT:::" + p.getDepartment());
                department = this.departmentService.findByDepartmentName(p.getDepartment());
                if (department != null) {
                    LOG.info("DEPARTMENT::: found");
                    vehicle.setDepartment(department);
                }
            }
        }
        vehicle.setType(p.getType());
        vehicle.setLicensePlate(plate);
        vehicle.setTollTagId(tolltag);
        vehicle.setClientProfile(profile);
        vehicle.setVehicleComment(p.getVehicleComment());
        vehicle.setVehicleStatus(p.getVehicleStatus());
        if (p.getAxle() != null && !p.getAxle().isEmpty()) {
            LOG.info("AXLES:::" + p.getAxle());
            Axle axle = this.axleService.findByAxleName(p.getAxle().toUpperCase());
            if (axle == null) {
                LOG.info("AXLES:::" + p.getAxle());
            } else {
                LOG.info("AXLES::: NOT FOUND" + axle.getAxleId());
            }
            vehicle.setAxle(axle);
        }
        vehicle.setStatus(true);
        Vehicle newVehicle = this.vehicleService.save(vehicle);
        if (newVehicle != null) {
            mv.addObject("messageType", "success");
            mv.addObject("message", "Successfully updated");
        } else {
            mv.addObject("messageType", "fail");
            mv.addObject("message", "Failed to update");
        }
        mv.addObject("vehicle", new VehicleForm());
        return mv;
    }

    @RequestMapping(value = "/file/upload")
    public ModelAndView uploadVehicles(RedirectAttributes redir, HttpServletRequest req,
            HttpSession session
    ) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "vehicle");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        loadCombos(mv);
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                String departmentMode = (String) session.getAttribute("department_mode");
                String departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                    mv.addObject("vehicle", new VehicleUpload());
                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    mv.addObject("departments", this.departmentService.countByClientProfile(profile));
                    Map< String, String> departments = new HashMap<>();
                    departments.put("", "Select a department");
                    this.departmentService.findByClientProfile(profile).forEach((department) -> {
                        departments.put(department.getDepartmentName(), department.getDepartmentName());
                    });
                    mv.addObject("departmentMap", departments);
                    mv.addObject("vehicle", new VehicleUpload());
                }
                break;
            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                mv.addObject("vehicle", new VehicleUpload());
                break;
            default:
                LOG.info("USER ACCOUNT UNKNOWN");
        }
        mv.setViewName("clientvehicleupload");
        return mv;
    }

    @RequestMapping(value = "upload/file/{save}", method = RequestMethod.POST)
    public ModelAndView uploadVeh(@ModelAttribute("vehicle") VehicleUpload p, MultipartFile file,
            RedirectAttributes redir, HttpServletRequest req,
            HttpSession session
    ) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "vehicle");
        int counter = 0;
        int header = 0;
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        mv.setViewName("clientvehicleupload");
        loadCombos(mv);
        ClientProfile profile = new ClientProfile();
        Department department = new Department();
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                String departmentMode = (String) session.getAttribute("department_mode");
                String departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));

                } else {
                    department = null;
                    profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    if (profile == null) {
                        mv.addObject("messageType", "fail");
                        mv.addObject("message", "Client : '" + p.getClient() + "' not found in the system");
                        mv.addObject("vehicle", p);
                        return mv;
                    }
                    Map< String, String> departmen = new HashMap<>();
                    departmen.put("", "Select a department");
                    this.departmentService.findByClientProfile(profile).forEach((dep) -> {
                        departmen.put(dep.getDepartmentName(), dep.getDepartmentName());
                    });
                    mv.addObject("departmentMap", departmen);
                    List<Department> departments = this.departmentService.findByClientProfile(profile);
                    if (departments == null || departments.isEmpty()) {

                    } else {
                        if (p.getDepartment() == null || p.getDepartment().isEmpty()) {
                            mv.addObject("messageType", "fail");
                            mv.addObject("message", "Select a department for '" + p.getClient() + "' before proceeding!");
                            mv.addObject("vehicle", p);
                            return mv;
                        }
                        department = this.departmentService.findByDepartmentNameAndClientProfile(p.getDepartment(), profile);
                    }
                }
                break;
            case "DEPARTMENT":
                department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                profile = department.getClientProfile();

                break;
            default:
                LOG.info("USER ACCOUNT UNKNOWN");
        }

        try {

            String type = p.getType();
            Binary logo = new Binary(BsonBinarySubType.BINARY, p.getVehicles().getBytes());
            if (logo.length() <= 0) {
                mv.addObject("messageType", "fail");
                mv.addObject("message", "Select a file first!");
                mv.addObject("vehicle", p);
                return mv;
            }
            FileOutputStream fileOuputStream = null;
            LOG.info("\n Import Excel Invoked \n".toUpperCase());
            String path = System.getProperty("java.io.tmpdir");
            fileOuputStream = new FileOutputStream(path.substring(0, path.length() - 1) + p.getVehicles().getOriginalFilename());
            fileOuputStream.write(p.getVehicles().getBytes());
            String fileLocation = path.substring(0, path.length() - 1) + p.getVehicles().getOriginalFilename();
            LOG.info("\n File Name :: ".toUpperCase() + fileLocation + "\n");
            LOG.info("Reading information from excel file  ".toUpperCase() + fileLocation);

            LOG.info("\n File Name :: ".toUpperCase() + p.getVehicles().getOriginalFilename() + "\n");
            LOG.info("Reading information from excel file  ".toUpperCase() + fileLocation);
            VehicleFile vehicleFile = new VehicleFile();
            vehicleFile.setVehicleFileId(seqGeneratorService.generateSequence(VehicleFile.SEQUENCE_NAME));
            vehicleFile.setClientProfile(profile);
            vehicleFile.setVehicleName(p.getVehicles().getOriginalFilename());
            if (department != null) {
                vehicleFile.setDepartment(department);
            }
            vehicleFile.setVehicles(logo);
            vehicleFile.setUpdatedDate(this.getCurrentDate());
            vehicleFile.setCreationDate(this.getCurrentDate());
            vehicleFile.setCreationTime(this.getCurrentTimestamp());
            vehicleFile.setUpdatedTime(this.getCurrentTimestamp());
            LOG.info(vehicleFile.toString());

            VehicleFile newVehicleFile = this.vehicleFileService.save(vehicleFile);
            if (newVehicleFile == null) {
                LOG.info("Batch file uploaded failed to save ".toUpperCase());
                redir.addFlashAttribute("messageType", "fail");
                redir.addFlashAttribute("message", "Batch file failed to save, please try again!");
                return mv;
            }

            File myFile = new File(fileLocation);
            FileInputStream fis = new FileInputStream(myFile);
            // Finds the workbook instance for XLSX file
            XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
            // Return first sheet from the XLSX workbook
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);
            // Traversing over each row of XLSX file
            LOG.info("ROW COUNT FOR FILE NAME :" + fileLocation + "    :::::  " + String.valueOf(mySheet.getLastRowNum()));

            LOG.info("Number of rows found : " + String.valueOf(mySheet.getLastRowNum()));
            for (int i = 0; i <= mySheet.getLastRowNum(); i++) {
                Vehicle vehicle = new Vehicle();
                /*
                                
                    LOOPING THROUGH ROWS
                                
                                
                 */
                XSSFRow row = ((XSSFRow) mySheet.getRow(i));
                if (i > 0) {
                    counter = 0;
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        counter = counter + 1;
                        Cell cell = row.getCell(j);
                        // LOG.info("Looping through cell :: ".toUpperCase() + String.valueOf(counter));
                        loadVehicles(profile, department, type, vehicle, cell, counter);
                    }
                    LOG.info("Processing info for plate number : " + vehicle.getLicensePlate());
                    vehicle.setVehicleFile(newVehicleFile);
                    vehicle.setStore(profile.getCompanyName() + " STORE");
                    vehicle.setClientProfile(profile);
                    if (department != null) {
                        vehicle.setDepartment(department);
                    }
                    vehicle.setType(type);
                    vehicle.setVehicleStatus("active");
                    vehicle.setStatus(true);
                    vehicle.setUpdatedDate(this.getCurrentDate());
                    vehicle.setCreationDate(this.getCurrentDate());
                    vehicle.setCreationTime(this.getCurrentTimestamp());
                    vehicle.setUpdatedTime(this.getCurrentTimestamp());;
                    //Vehicle toll = null;
                    Vehicle plate = null;

                    if (vehicle.getLicensePlate().isEmpty() || vehicle.getLicensePlate() == null) {
                        List<Vehicle> tolltag = this.vehicleService.findByTollTagId(vehicle.getTollTagId());
                        if (tolltag == null || tolltag.isEmpty()) {
                            tolltag = this.vehicleService.findByTollTagId("DNT." + vehicle.getTollTagId());
                            if (tolltag == null || tolltag.isEmpty()) {
                                tolltag = this.vehicleService.findByTollTagId("0" + vehicle.getTollTagId());
                                if (tolltag == null || tolltag.isEmpty()) {
                                    plate = null;
                                } else {
                                    plate = tolltag.get(0);
                                }
                            } else {
                                plate = tolltag.get(0);
                            }
                        } else {
                            plate = tolltag.get(0);
                        }
                    } else {
                        List<Vehicle> vehicles = this.vehicleService.findByLicensePlateOrderByVehicleIdDesc(vehicle.getLicensePlate());
                        if (vehicles == null || vehicles.isEmpty()) {

                        } else {
                            plate = vehicles.get(0);
                        }
                    }

                    if (plate != null) {
                        vehicle.setVehicleId(plate.getVehicleId());
                    } else {
                        vehicle.setVehicleId(seqGeneratorService.generateSequence(Vehicle.SEQUENCE_NAME));
                    }

                    LOG.info("Processing row number :: ".toUpperCase() + String.valueOf(i));
                    LOG.info("Saving plate..");
                    Vehicle newVehicle = this.vehicleService.save(vehicle);
                    LOG.info("done!");
                }

            }
            LOG.info("Processed batch upload successfully ".toUpperCase());
            mv.addObject("messageType", "success");
            mv.addObject("message", "Successfully updated");
            return mv;
        } catch (IOException ex) {
            mv.setViewName("redirect:/client_vehicle/file/upload");
            mv.addObject("messageType", "fail");
            mv.addObject("message", "Failed : " + ex);
            return mv;
        }
    }

    void loadVehicles(ClientProfile profile, Department department,
            String type, Vehicle vehicle,
            Cell cell, int counter
    ) {
        String value = "";
        java.util.Date tranDate = new java.util.Date();
        java.util.Date tranDateTime = new java.util.Date();
        //   LOG.info("Cell counter number : " + String.valueOf(counter));
        if (cell == null) {
            value = "";
        } else {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        LOG.info("DATE GENERATED VALUE: ".toUpperCase());
                        tranDateTime = cell.getDateCellValue();
                        tranDate = cell.getDateCellValue();
                        LocalDateTime convertDate = this.convertToLocalDateTimeViaMilisecond(tranDate);
                        LocalDate date = convertDate.toLocalDate();
                        tranDate = this.getConvertedDate(String.valueOf(date));
                    } else {
                        value = String.valueOf(new BigDecimal(cell.toString()).toPlainString());
                        LOG.info("NUMERIC VALUE FOUND:::   ".toUpperCase() + value);
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    if (cell.getBooleanCellValue()) {
                        value = "true";
                    } else {
                        value = "false";
                    }
                    break;
                default:
                    value = "NA";
            }
        }
        switch (counter) {
            case 1:
                LOG.info(value.toUpperCase());
                vehicle.setLicensePlate(value.toUpperCase());
                break;
            case 2:
                StoreLocation location = this.storeLocationService.findByStoreLocationName(value);
                vehicle.setStoreLocation(location);
                vehicle.setState(value);
                break;
            case 3:
                vehicle.setYear(value);
                break;
            case 4:
                vehicle.setColor(value);
                break;
            case 5:
                vehicle.setMake(value);
                break;
            case 6:
                vehicle.setUnit(value);
                break;
            case 7:
                vehicle.setModel(value);
                break;
            case 8:
                vehicle.setTollTagId(value);
                break;
            case 9:
                Axle axle = this.axleService.findByAxleName(value.toUpperCase());
                vehicle.setAxle(axle);
                break;
            case 10:
                TagType tagType = this.tagTypeService.findByTagTypeName(value);
                vehicle.setTagType(tagType);
                break;
            case 11:
                vehicle.setVin(value);
                break;
            case 12:
                LOG.info("Start Date".toUpperCase() + String.valueOf(tranDate));
                vehicle.setStartDate(tranDate);
                break;
            case 13:
                vehicle.setEndDate(tranDate);
                break;
            default:
        }
    }

    @RequestMapping("/delete/{id}")
    public ModelAndView viewVehicles(@PathVariable("id") Long id, HttpSession session,
            HttpServletRequest req, RedirectAttributes redir
    ) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "vehicle");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        mv.setViewName("viewvehicles");
        Vehicle vehicle = this.vehicleService.findByVehicleId(id);
        if (vehicle == null) {

        } else {
            this.vehicleService.delete(vehicle);
            mv.addObject("message", "Vehicle deleted!");
            mv.addObject("messageType", "success");
        }
        loadCombos(mv);
        mv.addObject("vehicle", new VehicleUpload());
        mv.addObject("topic", "Client Data");
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                String departmentMode = (String) session.getAttribute("department_mode");
                String departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));

                    ClientProfile profile = department.getClientProfile();
                    //mv.addObject("institution", profile.getCompanyName());

                    List<Vehicle> vehicles = this.vehicleService.findByClientProfileAndDepartmentOrderByVehicleIdDesc(profile, department);
                    if (vehicles == null || vehicles.isEmpty()) {
                        mv.addObject("messageType", "No data found");
                        mv.addObject("messageType", "fail");
                        return mv;
                    } else {
                        mv.addObject("messageType", "Department information found");
                        mv.addObject("messageType", "success");
                        mv.addObject("allVehicles", vehicles);
                    }
                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));

                    if (profile == null) {
                        mv.addObject("messageType", "fail");
                        mv.addObject("message", "Client not found");
                        return mv;
                    }
                    //mv.addObject("institution", profile.getCompanyName());

                    List<Vehicle> vehicles = this.vehicleService.findByClientProfileOrderByVehicleIdDesc(profile);
                    if (vehicles == null || vehicles.isEmpty()) {
                        mv.addObject("messageType", "No data found");
                        mv.addObject("messageType", "fail");
                        return mv;
                    } else {
                        mv.addObject("messageType", "Client information found");
                        mv.addObject("messageType", "success");
                        mv.addObject("allVehicles", vehicles);
                    }
                }
                break;
            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                if (department == null) {
                    mv.addObject("messageType", "fail");
                    mv.addObject("message", "Department not found");
                    return mv;
                }

                ClientProfile profile = department.getClientProfile();
                List<Vehicle> vehicles = this.vehicleService.findByClientProfileAndDepartmentOrderByVehicleIdDesc(profile, department);
                if (vehicles == null || vehicles.isEmpty()) {
                    mv.addObject("messageType", "No data found");
                    mv.addObject("messageType", "fail");
                    return mv;
                } else {
                    mv.addObject("messageType", "Department information found");
                    mv.addObject("messageType", "success");
                    mv.addObject("allVehicles", vehicles);
                }
                break;
            default:
                LOG.info("USER ACCOUNT UNKNOWN");
        }

        return mv;
    }

    @RequestMapping(path = "account/{delete-account}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> deleteAccount(@ModelAttribute("cancel") Cancel p, Model model,
            HttpSession session, HttpServletRequest req,
            RedirectAttributes redir
    ) {
        JobResponse result = new JobResponse();
        Vehicle vehicle = this.vehicleService.findByVehicleId(p.getCancelItem());
        if (vehicle == null) {
            result.setMessage("Vehicle does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            this.vehicleService.delete(vehicle);
            result.setMessage("Vehicle deleted!");
            result.setTitle("success");
            return ResponseEntity.ok(result);
        }
    }

    @RequestMapping("/edit/vehicle/{id}")
    public ModelAndView editVehicle(@PathVariable("id") Long id, HttpSession session,
            HttpServletRequest req, RedirectAttributes redir
    ) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "vehicle");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        loadCombos(mv);
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                String departmentMode = (String) session.getAttribute("department_mode");
                String departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));

                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));

                    Map< String, String> departments = new HashMap<>();
                    departments.put("", "Select a department");
                    this.departmentService.findByClientProfile(profile).forEach((department) -> {
                        departments.put(department.getDepartmentName(), department.getDepartmentName());
                    });
                    mv.addObject("departmentMap", departments);
                    //mv.addObject("institution", profile.getCompanyName());

                }
                break;
            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));

                break;
            default:
                LOG.info("USER ACCOUNT UNKNOWN");
        }
        Vehicle vehicle = this.vehicleService.findByVehicleId(id);
        if (vehicle == null) {
            redir.addFlashAttribute("Vehicle does not exist");
            redir.addFlashAttribute("fail");
            mv.setViewName("redirect:/client_vehicle/view_vehicle/");
            return mv;
        } else {
            VehicleForm prof = new VehicleForm();
            prof.setVehicleId(vehicle.getVehicleId());
            if (vehicle.getClientProfile() != null) {
                prof.setClient(vehicle.getClientProfile().getCompanyName());
            }
            if (vehicle.getLicensePlate() != null) {
                prof.setLicensePlate(vehicle.getLicensePlate());
            }
            if (vehicle.getColor() != null) {
                prof.setColor(vehicle.getColor());
            }
            if (vehicle.getMake() != null) {
                prof.setMake(vehicle.getMake());
            }
            if (vehicle.getModel() != null) {
                prof.setModel(vehicle.getModel());
            }
            if (vehicle.getStartDate() != null) {
                prof.setStartDate(String.valueOf(vehicle.getStartDate()));
            }
            if (vehicle.getEndDate() != null) {
                prof.setStartDate(String.valueOf(vehicle.getEndDate()));
            }
            if (vehicle.getAxle() != null) {
                prof.setAxle(vehicle.getAxle().getAxleName());
            }
            if (vehicle.getTagType() != null) {
                prof.setTagType(vehicle.getTagType().getTagTypeName());
            }
            if (vehicle.getTollTagId() != null) {
                prof.setTollTagId(vehicle.getTollTagId());
            }
            if (vehicle.getUnit() != null) {
                prof.setUnit(vehicle.getUnit());
            }
            if (vehicle.getYear() != null) {
                prof.setYear(vehicle.getYear());
            }
            if (vehicle.getVin() != null) {
                prof.setVin(vehicle.getVin());
            }
            if (vehicle.getState() != null) {
                prof.setState(vehicle.getStore());
            }
            if (vehicle.getVehicleStatus() != null) {
                prof.setVehicleStatus(vehicle.getVehicleStatus());
            }
            if (vehicle.getVehicleComment() != null) {
                prof.setVehicleComment(vehicle.getVehicleComment());
            }
            mv.addObject("vehicle", prof);
            mv.setViewName("clientaddvehicle");
            return mv;
        }
    }

    @RequestMapping("/register/new/vehicle/")
    public ModelAndView registerVehicle(HttpSession session, HttpServletRequest req,
            RedirectAttributes redir
    ) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "vehicle");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        loadCombos(mv);
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                String departmentMode = (String) session.getAttribute("department_mode");
                String departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));

                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    if (profile.getCompanyName().contains("PROTECH")) {
                        mv.addObject("transponders", protechTransponders());
                    }
                }
                break;
            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                break;
            default:
                LOG.info("USER ACCOUNT UNKNOWN");
        }

        VehicleForm prof = new VehicleForm();
        mv.addObject("vehicle", prof);
        mv.setViewName("clientaddvehicle");
        return mv;

    }

    @RequestMapping("/view/vehicle/info/{id}")
    public ModelAndView viewVehicle(@PathVariable("id") Long id, HttpSession session,
            HttpServletRequest req, RedirectAttributes redir
    ) {
        ModelAndView mv = new ModelAndView();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }

        ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
        //mv.addObject("institution", profile.getCompanyName());

        mv.addObject("menutype", "vehicle");
        Vehicle vehicle = this.vehicleService.findByVehicleId(id);
        if (vehicle == null) {
            redir.addFlashAttribute("message", "No vehicle added");
            redir.addFlashAttribute("messageType", "fail");
            mv.addObject("vehicle", vehicle);
            mv.setViewName("client_view_vehicle_info");
            return mv;
        } else {
            mv.addObject("vehicle", vehicle);
            mv.setViewName("client_view_vehicle_info");
            return mv;
        }
    }

    @RequestMapping("/search/{id}")
    public ModelAndView searchCar(@PathVariable("plate") String plate, HttpSession session,
            HttpServletRequest req, RedirectAttributes redir
    ) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "vehicle");
        loadCombos(mv);
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
        //mv.addObject("institution", profile.getCompanyName());

        Vehicle vehicle = this.vehicleService.findByLicensePlate(plate);
        if (vehicle == null) {
            redir.addFlashAttribute("message", "No vehicle added");
            redir.addFlashAttribute("messageType", "fail");
            mv.setViewName("redirect:/client_dash/view_vehicle/");
            return mv;
        } else {
            //mv.addObject("institution", profile.getCompanyName());

            mv.addObject("vehicle", vehicle);
            mv.setViewName("client_view_vehicle");
            return mv;
        }
    }

    @RequestMapping(path = "/load/combo/by/client", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> loadClientCombo(HttpSession session
    ) {
        JobResponse result = new JobResponse();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
        List<Department> departments = this.departmentService.findByClientProfile(profile);
        if (departments == null || departments.isEmpty()) {
            List<Vehicle> vehicles = this.vehicleService.findByClientProfileOrderByVehicleIdDesc(profile);
            if (vehicles == null || vehicles.isEmpty()) {
                result.setMessage(profile.getCompanyName() + " data was not found");
                result.setTitle("fail");
                return ResponseEntity.ok(result);
            } else {
                result.setTitle("success");
                result.setMessage("Data found");
                result.setResults(vehicles);
            }
        } else {

            result.setMessage("departments found");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = "/load/by/client", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchClient(HttpSession session
    ) {
        JobResponse result = new JobResponse();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
        if (profile == null) {
            result.setMessage("Department does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        List<Vehicle> vehicles = this.vehicleService.findByClientProfileOrderByVehicleIdDesc(profile);
        if (vehicles == null || vehicles.isEmpty()) {
            result.setMessage(profile.getCompanyName() + " data was not found");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            result.setTitle("success");
            result.setMessage("Data found");
            result.setResults(vehicles);
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = "/load/by/client/department", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchClientDepartment(HttpSession session,
            @RequestParam String departmentName
    ) {
        JobResponse result = new JobResponse();
        String department = departmentName;
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
        if (profile == null) {
            result.setMessage("Department does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        Department dep = this.departmentService.findByDepartmentNameAndClientProfile(department, profile);
        if (dep == null) {
            result.setMessage("Department does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        List<Vehicle> vehicles = this.vehicleService.findByClientProfileAndDepartmentOrderByVehicleIdDesc(profile, dep);
        if (vehicles == null || vehicles.isEmpty()) {
            result.setMessage(dep.getDepartmentName() + " data was not found");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            result.setTitle("success");
            result.setMessage("Department data found");
            result.setResults(vehicles);
        }
        return ResponseEntity.ok(result);
    }

    JasperPrint printVehicles(List<Vehicle> vehicleList
    ) {
        try {
            JRBeanCollectionDataSource vehicleDataSource = new JRBeanCollectionDataSource(vehicleList);
            Map<String, Object> parameters = new HashMap();
            parameters.put("VehicleDataSource", vehicleDataSource);
            ClassPathResource fStream = new ClassPathResource("reportsDesign/vehicles.jrxml");
            InputStream inputStream = fStream.getInputStream();
            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return jasperPrint;
        } catch (IOException | JRException ex) {
            LOG.info(ex.toString().toUpperCase());
            return null;
        }

    }

    @GetMapping(value = "/vehiclePdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePDFReport(HttpSession session) {
        List<Vehicle> vehicleList = new ArrayList<>();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            return null;
        }
        vehicleList = loadVehicles(session);
        try {
            if (printVehicles(vehicleList) != null) {
                ByteArrayResource byteArrayResource = reportService.generateDataSourceReport(vehicleList, ExportReportType.PDF, printVehicles(vehicleList));
                ByteArrayInputStream bis = (ByteArrayInputStream) byteArrayResource.getInputStream();
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Disposition", "inline; filename=innovative-vehicles.pdf");
                return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bis));
            } else {
                LOG.info("NO DATA WAS PROCESSED");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (IOException ex) {
            LOG.error(ex.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/excel/vehicles.xlsx")
    public void generateExcelReport(HttpServletResponse response, HttpSession session) throws IOException {
        List<Vehicle> vehicleList = new ArrayList<>();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            return;
        }
        vehicleList = loadVehicles(session);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=innovative-vehicles.xlsx");
        ByteArrayInputStream stream = ExcelVehicleExporter.exportListToExcelFile(vehicleList);
        IOUtils.copy(stream, response.getOutputStream());
    }

    private List<Vehicle> loadVehicles(HttpSession session) {
        ClientProfile profile;
        String departmentMode, departmentId;
        Department department;
        List<Vehicle> vehicles = null;
        double total;
        JobResponse result = new JobResponse();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            return vehicles;
        }

        int year = Integer.valueOf((String) session.getAttribute("year_mode"));
        String month = (String) session.getAttribute("month_mode");
        LOG.info("DATES USED" + String.valueOf(year) + month);
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                departmentMode = (String) session.getAttribute("department_mode");
                departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department1 = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                    Query query = new Query();
                    query.addCriteria(Criteria.where("department").is(department1));
                    query.addCriteria(Criteria.where("state").ne(null));
                    vehicles = mongoTemplate.find(query, Vehicle.class);

                } else {
                    profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    Query query = new Query();
                    query.addCriteria(Criteria.where("clientProfile").is(profile));
                    query.addCriteria(Criteria.where("state").ne(null));
                    query.with(Sort.by("vehicleId").descending());
                    vehicles = mongoTemplate.find(query, Vehicle.class);
                }
                break;
            case "DEPARTMENT":
                department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                Query query = new Query();
                query.addCriteria(Criteria.where("department").is(department));
                query.addCriteria(Criteria.where("state").ne(null));
                vehicles = mongoTemplate.find(query, Vehicle.class);
                break;
        }
        LOG.info("returning search results");
        return vehicles;

    }

    public List<String> protechTransponders() {
        List<String> transponderList = new ArrayList<>();
        transponderList.add("DNT.12032859");
        transponderList.add("DNT.12032873");
        transponderList.add("DNT.12032874");
        transponderList.add("DNT.12032875");
        transponderList.add("DNT.12032876");
        transponderList.add("DNT.12032877");
        transponderList.add("DNT.12032878");
        transponderList.add("DNT.12032879");
        transponderList.add("DNT.12032880");
        transponderList.add("DNT.12032881");
        transponderList.add("DNT.12032883");
        transponderList.add("DNT.12032884");
        transponderList.add("DNT.12032885");
        transponderList.add("DNT.12032886");
        transponderList.add("DNT.12032887");
        transponderList.add("DNT.12032889");
        transponderList.add("DNT.12032890");
        transponderList.add("DNT.12032891");
        transponderList.add("DNT.12032892");
        transponderList.add("DNT.12032893");
        transponderList.add("DNT.12032894");
        transponderList.add("DNT.12032895");
        transponderList.add("DNT.12032896");
        transponderList.add("DNT.12032897");
        transponderList.add("DNT.12032898");
        transponderList.add("DNT.12032899");
        transponderList.add("DNT.12032900");
        transponderList.add("DNT.12032901");
        transponderList.add("DNT.12032902");
        transponderList.add("DNT.12032903");
        transponderList.add("DNT.12032904");
        transponderList.add("DNT.12032905");
        transponderList.add("DNT.12032906");
        transponderList.add("DNT.12032907");
        transponderList.add("DNT.12032908");
        transponderList.add("DNT.12032909");
        transponderList.add("DNT.12032910");
        transponderList.add("DNT.12032912");
        transponderList.add("DNT.12032913");
        transponderList.add("DNT.12032914");
        transponderList.add("DNT.12032915");
        transponderList.add("DNT.12032916");
        transponderList.add("DNT.12032917");
        transponderList.add("DNT.12032918");
        transponderList.add("DNT.12032919");
        transponderList.add("DNT.12032920");
        transponderList.add("DNT.12032921");
        transponderList.add("DNT.12032922");
        transponderList.add("DNT.12032923");
        transponderList.add("DNT.12032926");
        transponderList.add("DNT.12034685");
        transponderList.add("DNT.12034686");
        transponderList.add("DNT.12034687");
        transponderList.add("DNT.12034688");
        transponderList.add("DNT.12034689");
        transponderList.add("DNT.12034690");
        transponderList.add("DNT.12034692");
        transponderList.add("DNT.12034693");
        transponderList.add("DNT.12034694");
        transponderList.add("DNT.12032928");
        transponderList.add("DNT.12032929");
        transponderList.add("DNT.12032930");
        transponderList.add("DNT.12032931");
        transponderList.add("DNT.12032932");
        transponderList.add("DNT.12032933");
        transponderList.add("DNT.12032934");
        transponderList.add("DNT.12032935");
        transponderList.add("DNT.12032936");
        transponderList.add("DNT.12032937");
        transponderList.add("DNT.12032938");
        transponderList.add("DNT.12032939");
        transponderList.add("DNT.12032940");
        transponderList.add("DNT.12134031");
        transponderList.add("DNT.12134032");
        transponderList.add("DNT.12134033");
        transponderList.add("DNT.12134034");
        transponderList.add("DNT.12824857");
        transponderList.add("DNT.12853363");
        transponderList.add("DNT.12905384");
        transponderList.add("DNT.12959276");
        transponderList.add("DNT.15111364");
        transponderList.add("DNT.15652097");
        transponderList.add("DNT.15851886");
        transponderList.add("001-00070367");
        transponderList.add("001-00070368");
        transponderList.add("001-00070369");
        transponderList.add("001-00070370");
        transponderList.add("001-00070371");
        transponderList.add("001-00070372");
        transponderList.add("001-00070373");
        transponderList.add("001-00070374");
        transponderList.add("001-00070413");
        transponderList.add("001-00070414");
        transponderList.add("001-00070415");
        transponderList.add("001-00070416");
        transponderList.add("001-00070417");
        transponderList.add("5667380");
        transponderList.add("5667381");
        transponderList.add("5667382");
        transponderList.add("5667383");
        transponderList.add("5667384");
        transponderList.add("5667385");
        transponderList.add("5667386");
        transponderList.add("5667387");
        transponderList.add("5667388");
        transponderList.add("5674911");
        transponderList.add("5687512");
        transponderList.add("03301163772");
        transponderList.add("03301163773");
        transponderList.add("03301163774");
        transponderList.add("03301163775");
        transponderList.add("03301163776");
        transponderList.add("03301163777");
        transponderList.add("03301163778");
        transponderList.add("03301163779");
        transponderList.add("03301163780");
        transponderList.add("03301163781");
        transponderList.add("03301163782");
        transponderList.add("03301163783");
        transponderList.add("03301163784");
        transponderList.add("03301163785");
        transponderList.add("03301163786");
        transponderList.add("03301163787");
        transponderList.add("03301163788");
        transponderList.add("03301163789");
        transponderList.add("03301163790");
        transponderList.add("03301170102");
        transponderList.add("03301170103");
        transponderList.add("03301170104");
        transponderList.add("03301170105");
        transponderList.add("03301170106");
        transponderList.add("03301170107");
        transponderList.add("03301170108");
        transponderList.add("03301170109");
        transponderList.add("03301170110");
        transponderList.add("03301170111");
        transponderList.add("03301170122");
        transponderList.add("03301170123");
        transponderList.add("03301170124");
        transponderList.add("03301170125");
        transponderList.add("03301170126");
        transponderList.add("03301170127");
        transponderList.add("03301170128");
        transponderList.add("03301170129");
        transponderList.add("03301170130");
        transponderList.add("03301170131");
        transponderList.add("03301170132");
        transponderList.add("03301170133");
        transponderList.add("03301170134");
        transponderList.add("03301170135");
        transponderList.add("03301170136");
        transponderList.add("03301170137");
        transponderList.add("03301170174");
        transponderList.add("03301170175");
        transponderList.add("03301170176");
        transponderList.add("03301170138");
        transponderList.add("03301170139");
        transponderList.add("03301170140");
        transponderList.add("03301170141");
        transponderList.add("03301170142");
        transponderList.add("03301170143");
        transponderList.add("03301170144");
        transponderList.add("03301170145");
        transponderList.add("03301170146");
        transponderList.add("03301170147");
        transponderList.add("03301170148");
        transponderList.add("03301170149");
        transponderList.add("03301170150");
        transponderList.add("03301170151");
        transponderList.add("03301170152");
        transponderList.add("03301170153");
        transponderList.add("03301170154");
        transponderList.add("03301170155");
        transponderList.add("03301170156");
        transponderList.add("03301170157");
        transponderList.add("03301170158");
        transponderList.add("03301170159");
        transponderList.add("03301170110");
        transponderList.add("03301170161");
        transponderList.add("03301170162");
        transponderList.add("03301170163");
        transponderList.add("03301170164");
        transponderList.add("03301170165");
        transponderList.add("03301170166");
        transponderList.add("03301170167");
        transponderList.add("03301170168");
        transponderList.add("03301170169");
        transponderList.add("03301170170");
        transponderList.add("03301170171");
        transponderList.add("03301170172");
        transponderList.add("03301170173");
        transponderList.add("3809005");
        transponderList.add("3809013");
        transponderList.add("010300035679475");
        transponderList.add("010300035679509");
        transponderList.add("010300035679517");
        transponderList.add("010300035679525");
        transponderList.add("010300035679533");
        transponderList.add("010300035679541");
        transponderList.add("010300035679558");
        transponderList.add("010300035679566");
        transponderList.add("010300035679574");
        transponderList.add("010300035679582");
        transponderList.add("010300035679590");
        transponderList.add("010300035679108");
        transponderList.add("010300035679616");
        transponderList.add("010300035679624");
        transponderList.add("010300035679632");
        transponderList.add("010300035679640");
        transponderList.add("010300035679657");
        transponderList.add("010300035679665");
        transponderList.add("010300035679673");
        transponderList.add("010300035679681");
        transponderList.add("010300035679699");
        transponderList.add("010300035679707");
        transponderList.add("010300035679715");
        transponderList.add("010300035679723");
        transponderList.add("010300035679731");
        transponderList.add("010300035679749");
        transponderList.add("010300035679756");
        transponderList.add("010300035679764");
        transponderList.add("010300035679772");
        transponderList.add("010300035679780");
        transponderList.add("010300031030462");
        transponderList.add("010300031030470");
        transponderList.add("010300031030488");
        transponderList.add("010300031030496");
        transponderList.add("010300031030512");
        transponderList.add("0006822849");
        transponderList.add("0006822850");
        transponderList.add("0006822851");
        transponderList.add("0006822852");
        transponderList.add("0006822853");
        transponderList.add("0006822854");
        transponderList.add("0006822855");
        transponderList.add("10300036113904");
        transponderList.add("10300036114100");
        transponderList.add("10300036115305");
        transponderList.add("10300036115503");
        transponderList.add("10300036115701");
        transponderList.add("10300036116071");
        transponderList.add("10300036116279");
        transponderList.add("10300036116477");
        transponderList.add("DNT.12034691");
        transponderList.add("DNT.12032925");
        transponderList.add("DNT.12032924");
        transponderList.add("DNT.12032928");
        transponderList.add("DNT.12032929");
        transponderList.add("DNT.12032930");
        transponderList.add("DNT.12032937");
        return transponderList;
    }

}
