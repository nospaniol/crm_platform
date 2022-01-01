package com.crm.controller.admin;

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
import com.crm.model.State;
import com.crm.model.StoreLocation;
import com.crm.model.TagType;
import com.crm.model.TransactionUpload;
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
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.apache.poi.ss.usermodel.Cell;
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

@Controller
@RequestMapping("vehicle")
public class VehicleController extends Nospaniol {

    @RequestMapping(value = {"view_file"}, method = RequestMethod.GET)
    public ModelAndView viewFile(@PageableDefault(value = 50) Pageable pageable, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
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
        mv.addObject("vehicle", new VehicleUpload());
        mv.addObject("institution", institutionName());
        Page<VehicleFile> pages = this.vehicleFileService.findAllByOrderByVehicleFileIdDesc(pageable);
        mv.addObject("number", pages.getNumber());
        mv.addObject("totalPages", pages.getTotalPages());
        mv.addObject("totalElements", pages.getTotalElements());
        mv.addObject("size", pages.getSize());
        mv.addObject("data", pages.getContent());
        mv.setViewName("vehicleuploads");
        return mv;
    }

    @RequestMapping("/download/upload/{id}")
    public void downloadUpload(@PathVariable("id") Long id, HttpServletResponse response, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        try {
            String ns_mail = (String) session.getAttribute("ns_mail_address");
            if (ns_mail == null || ns_mail.isEmpty()) {
                return;
            }
            String logged_user = (String) session.getAttribute("ns_user");
            if (logged_user == null || logged_user.equals("CLIENT")) {
                return;
            }
            VehicleFile download = this.vehicleFileService.findByVehicleFileId(id);
            if (download != null) {
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename=vehicleupload.xlsx");
                if (download.getVehicles() == null) {
                    LOG.info("This file does not exist for file id :: ".toUpperCase() + download.getVehicleName());
                } else {
                    LOG.info("This file found for file id :: ".toUpperCase() + download.getVehicleName());
                    ByteArrayInputStream stream = new ByteArrayInputStream(download.getVehicles().getData());
                    IOUtils.copy(stream, response.getOutputStream());
                }
            } else {
                LOG.info("This file does not exist with id:: ".toUpperCase() + String.valueOf(id));
                return;
            }
        } catch (IOException ex) {
            LOG.error(ex.toString());
            return;
        }
    }

    @RequestMapping(value = {"delete_batch"}, method = RequestMethod.GET)
    public ModelAndView addTransaction(RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
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
        mv.addObject("transaction", new TransactionUpload());
        mv.setViewName("vehicledelete");
        loadCombos(mv);
        return mv;
    }

    @RequestMapping(value = "delete/{batch}", method = RequestMethod.POST)
    public ModelAndView uploadVeh(@ModelAttribute("transaction") TransactionUpload p, MultipartFile file, RedirectAttributes redir, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        int counter = 0;
        int header = 0;
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        mv.setViewName("redirect:/vehicle/delete_batch");
        loadCombos(mv);
        if (p.getClient().isEmpty()) {
            redir.addFlashAttribute("messageType", "fail");
            redir.addFlashAttribute("message", "Fill all required fields!");
            redir.addFlashAttribute("transaction", p);
            return mv;
        }
        ClientProfile profile = this.clientProfileService.findByCompanyName(p.getClient());
        if (profile == null) {
            redir.addFlashAttribute("messageType", "fail");
            redir.addFlashAttribute("message", "Client : '" + p.getClient() + "' not found in the system");
            redir.addFlashAttribute("transaction", p);
            return mv;
        }
        List<Vehicle> vehicles = this.vehicleService.findByClientProfileOrderByVehicleIdDesc(profile);
        vehicleService.deleteAll(vehicles);
        LOG.info("Processed batch upload successfully ".toUpperCase());
        redir.addFlashAttribute("messageType", "success");
        redir.addFlashAttribute("message", "Successfully updated");
        return mv;
    }

    @RequestMapping(value = {"view_vehicle"}, method = RequestMethod.GET)
    public ModelAndView viewVehicles(@PageableDefault(value = 100) Pageable pageable, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
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
        mv.addObject("vehicle", new VehicleUpload());
        mv.addObject("institution", institutionName());
        mv.addObject("topic", "Recently added vehicles");
        Page<Vehicle> pages = this.vehicleService.findAll(pageable);
        mv.addObject("number", pages.getNumber());
        mv.addObject("totalPages", pages.getTotalPages());
        mv.addObject("totalElements", pages.getTotalElements());
        mv.addObject("size", pages.getSize());
        mv.addObject("data", pages.getContent());
        mv.setViewName("viewvehicles");
        return mv;
    }

    @RequestMapping(value = {"add_vehicle"}, method = RequestMethod.GET)
    public ModelAndView addClients(RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
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
        mv.addObject("vehicle", new VehicleForm());
        mv.setViewName("addvehicle");
        loadCombos(mv);
        return mv;
    }

    void loadCombos(ModelAndView mv) {
        mv.addObject("institution", institutionName());
        List<ClientProfile> clientList = this.clientProfileService.findAll();
        mv.addObject("clientList", clientList);
        mv.addObject("clientProfile", new ClientProfile());

        List<State> stateList = this.stateService.findAll();
        mv.addObject("stateList", stateList);

        List<StoreLocation> storeLocationList = this.storeLocationService.findAll();
        mv.addObject("storeLocationList", storeLocationList);

        List<TagType> tagTypeList = this.tagTypeService.findAll();
        mv.addObject("tagTypeList", tagTypeList);

        List<Axle> axleList = this.axleService.findAll();
        mv.addObject("axleList", axleList);

        List<String> types = new ArrayList<>();
        types.add("DEMO");
        types.add("RENTAL");
        mv.addObject("types", types);

    }

    @RequestMapping(value = "register/{save}", method = RequestMethod.POST)
    public ModelAndView registerVehicle(@ModelAttribute("vehicle") VehicleForm p, MultipartFile file, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        loadCombos(mv);
        mv.setViewName("addvehicle");
        if (p.getVehicleStatus().isEmpty() || p.getAxle().isEmpty() || p.getColor().isEmpty() || p.getLicensePlate().isEmpty() || p.getMake().isEmpty() || p.getModel().isEmpty() || p.getStartDate().isEmpty() || p.getState().isEmpty() || p.getTagType().isEmpty() || p.getTollTagId().isEmpty()) {
            mv.addObject("messageType", "fail");
            mv.addObject("message", "Fill all required fields!");
            mv.addObject("vehicle", p);
            return mv;
        }
        String plate = p.getLicensePlate().toUpperCase();
        String tolltag = p.getTollTagId().toUpperCase();
        Vehicle vehicle = this.vehicleService.findByVehicleId(p.getVehicleId());
        if (vehicle == null) {
            if (this.vehicleService.findByLicensePlate(plate) != null) {
                mv.addObject("messageType", "fail");
                mv.addObject("message", "This license plate has already been registered!");
                mv.addObject("vehicle", p);
                return mv;
            }
            if (this.vehicleService.findByTollTagId(tolltag) != null) {
                mv.addObject("messageType", "fail");
                mv.addObject("message", "This toll tag has already been registered!");
                mv.addObject("vehicle", p);
                return mv;
            }
            vehicle = new Vehicle();
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
        vehicle.setStartDate(this.getConvertedDate(p.getStartDate()));
        vehicle.setEndDate(this.getConvertedDate(p.getEndDate()));
        vehicle.setState(p.getState());
        vehicle.setType(p.getType());
        vehicle.setLicensePlate(plate);
        vehicle.setTollTagId(tolltag);
        vehicle.setVehicleComment(p.getVehicleComment());
        vehicle.setVehicleStatus(p.getVehicleStatus());
        ClientProfile profile = this.clientProfileService.findByCompanyName(p.getClient());
        vehicle.setClientProfile(profile);
        Axle axle = this.axleService.findByAxleName(p.getAxle().toUpperCase());
        vehicle.setAxle(axle);
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
        mv.addObject("institution", institutionName());
        loadCombos(mv);
        return mv;
    }

    @RequestMapping(value = "/file/upload")
    public ModelAndView uploadVehicles(RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        LOG.info("\n Opening vehicle upload page \n".toUpperCase());
        loadCombos(mv);
        mv.addObject("vehicle", new VehicleUpload());
        mv.setViewName("vehicleupload");
        return mv;
    }

    @RequestMapping(value = "upload/file/{save}", method = RequestMethod.POST)
    public ModelAndView uploadVeh(@ModelAttribute("vehicle") VehicleUpload p, MultipartFile file, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        mv.setViewName("vehicleupload");
        loadCombos(mv);
        try {
            if (p.getClient().isEmpty()) {
                mv.addObject("messageType", "fail");
                mv.addObject("message", "Fill all required fields!");
                mv.addObject("vehicle", p);
                return mv;
            }
            ClientProfile profile = this.clientProfileService.findByCompanyName(p.getClient());
            if (profile == null) {
                mv.addObject("messageType", "fail");
                mv.addObject("message", "Client : '" + p.getClient() + "' not found in the system");
                mv.addObject("vehicle", p);
                return mv;
            }
            Department department = null;
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
            String type = p.getType();
            Binary logo = new Binary(BsonBinarySubType.BINARY, p.getVehicles().getBytes());
            if (logo.length() <= 0) {
                mv.addObject("messageType", "fail");
                mv.addObject("message", "Select a file first!");
                mv.addObject("vehicle", p);
                return mv;
            }
            int counter = 0;
            int header = 0;

            FileOutputStream fileOuputStream = null;
            LOG.info("\n Import Excel Invoked \n".toUpperCase());
            String path = System.getProperty("java.io.tmpdir");
            fileOuputStream = new FileOutputStream(path.substring(0, path.length() - 1) + p.getVehicles().getOriginalFilename());
            fileOuputStream.write(p.getVehicles().getBytes());
            String fileLocation = path.substring(0, path.length() - 1) + p.getVehicles().getOriginalFilename();
            LOG.info("\n File Name :: ".toUpperCase() + p.getVehicles().getOriginalFilename() + "\n");
            LOG.info("Reading information from excel file  ".toUpperCase() + fileLocation);
            VehicleFile vehicleFile = new VehicleFile();
            vehicleFile.setClientProfile(profile);
            vehicleFile.setVehicleName(p.getVehicles().getOriginalFilename());
            if (department != null) {
                vehicleFile.setDepartment(department);
            }
            vehicleFile.setVehicles(logo);
            vehicleFile.setVehicleFileId(seqGeneratorService.generateSequence(VehicleFile.SEQUENCE_NAME));
            vehicleFile.setUpdatedDate(this.getCurrentDate());
            vehicleFile.setCreationDate(this.getCurrentDate());
            vehicleFile.setCreationTime(this.getCurrentTimestamp());
            vehicleFile.setUpdatedTime(this.getCurrentTimestamp());
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
                        loadVehicles(vehicle, cell, counter);
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
                    Vehicle vin = null;
                    vehicle.setVehicleId(seqGeneratorService.generateSequence(Vehicle.SEQUENCE_NAME));

//                    if (vehicle.getVin() == null) {
//                        LOG.info("Vin value is null");
//                    } else {
//                        if (vehicle.getVin().isEmpty()) {
//                            LOG.info("Vin is empty");
//                        } else {
//                            vin = this.vehicleService.findByVin(vehicle.getVin());
//                        }
//                    }
                    if (vehicle.getLicensePlate() == null) {
                        LOG.info("Plate value is null");
                    } else {
                        if (vehicle.getLicensePlate().isEmpty()) {
                            LOG.info("Plate is empty");
                        } else {
                            plate = this.vehicleService.findByLicensePlate(vehicle.getLicensePlate());
                        }
                    }
//                    if (vin != null) {
//                        LOG.info("Vin found, updating existing vehicle");
//                        vehicle.setVehicleId(vin.getVehicleId());
//                    }
                    if (plate != null) {
                        LOG.info("Plate found, updating existing vehicle");
                        vehicle.setVehicleId(plate.getVehicleId());
                    }

                    LOG.info("Processing row number :: ".toUpperCase() + String.valueOf(i));
                    LOG.info("Saving plate..");
                    Vehicle newVehicle = this.vehicleService.save(vehicle);
                    LOG.info("done!");
                }

            }
            LOG.info("Processed batch upload successfully ".toUpperCase());
            redir.addFlashAttribute("messageType", "success");
            redir.addFlashAttribute("message", "Successfully updated");
            mv.setViewName("redirect:/vehicle/file/upload");
            return mv;
        } catch (IOException ex) {
            mv.setViewName("redirect:/vehicle/file/upload");
            redir.addFlashAttribute("messageType", "fail");
            redir.addFlashAttribute("message", "Failed : " + ex);
            return mv;
        }
    }

    void loadVehicles(Vehicle vehicle, Cell cell, int counter) {
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
                    value = "";
            }
        }
        switch (counter) {
            case 1:
                if (cell != null) {
                    if (value.toUpperCase().trim().isEmpty()) {
                        vehicle.setLicensePlate(null);
                    } else {
                        vehicle.setLicensePlate(value.toUpperCase().trim());
                    }
                }
                break;
            case 2:
                if (cell == null) {
                    vehicle.setState("");
                    vehicle.setStoreLocation(null);
                } else {
                    StoreLocation location = this.storeLocationService.findByStoreLocationName(value);
                    vehicle.setStoreLocation(location);
                    vehicle.setState(value);
                }
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
                if (cell == null) {
                    vehicle.setTollTagId("");
                } else {
                    vehicle.setTollTagId(value);
                }
                break;
            case 9:
                if (cell == null) {
                    vehicle.setAxle(null);
                } else {
                    Axle axle = this.axleService.findByAxleName(value.toUpperCase());
                    vehicle.setAxle(axle);
                }
                break;
            case 10:
                if (cell == null) {
                    vehicle.setTagType(null);
                } else {
                    TagType tagType = this.tagTypeService.findByTagTypeName(value);
                    vehicle.setTagType(tagType);
                }
                break;
            case 11:
                vehicle.setVin(value);
                break;
            case 12:
                vehicle.setStartDate(tranDate);
                break;
            case 13:
                vehicle.setEndDate(tranDate);
                break;
            default:
        }
    }

    @RequestMapping("/delete/{id}")
    public ModelAndView deleteVehicles(@PathVariable("id") Long id, HttpSession session,
            HttpServletRequest req, RedirectAttributes redir
    ) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
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
        mv.addObject("institution", institutionName());
        mv.addObject("topic", "Departmental Data");
        List<Vehicle> vehicles = this.vehicleService.findAll();
        if (vehicles == null || vehicles.isEmpty()) {
            mv.addObject("messageType", "No data found");
            mv.addObject("messageType", "fail");
            return mv;
        } else {
            mv.addObject("messageType", "Client information found");
            mv.addObject("messageType", "success");
            mv.addObject("allVehicles", vehicles);
        }
        return mv;
    }

    @RequestMapping("/reverse/upload/{id}")
    @ResponseBody
    public ResponseEntity<?> reverseUpload(@PathVariable("id") Long id, HttpSession session
    ) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        VehicleFile vehicleFile = this.vehicleFileService.findByVehicleFileId(id);
        if (vehicleFile == null) {
            result.setMessage("Vehicle upload does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        } else {
            List<Vehicle> vehicles = this.vehicleService.findByVehicleFile(vehicleFile);
            for (Vehicle vehicle : vehicles) {
                this.vehicleService.delete(vehicle);
            }
            this.vehicleFileService.delete(vehicleFile);
            LOG.info("Rervesal done for :: ".toUpperCase() + vehicleFile.getVehicleName() + " (" + String.valueOf(vehicleFile.getVehicleFileId()) + ")");
            result.setMessage("Reversal complete!");
            result.setTitle("success");
            return ResponseEntity.ok(result);
        }
    }

    @RequestMapping("/delete/{client}/{department}/{id}")
    public ModelAndView deleteVehicles(@PathVariable("client") String client,
            @PathVariable("department") String department,
            @PathVariable("id") Long id, HttpSession session,
            HttpServletRequest req, RedirectAttributes redir
    ) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        mv.setViewName("viewvehicles");
        mv.addObject("clientName", client);
        mv.addObject("departmentName", department);
        ClientProfile profile = this.clientProfileService.findByCompanyName(client);
        if (profile == null) {
            mv.addObject("messageType", "fail");
            mv.addObject("message", "Client not found");
            return mv;
        }
        Department dep = this.departmentService.findByDepartmentNameAndClientProfile(department, profile);
        if (dep == null) {
            mv.addObject("messageType", "fail");
            mv.addObject("message", "Department not found");
            return mv;
        }
        Vehicle vehicle = this.vehicleService.findByVehicleId(id);
        if (vehicle == null) {

        } else {
            this.vehicleService.delete(vehicle);
            mv.addObject("message", "Vehicle deleted!");
            mv.addObject("messageType", "success");
        }

        loadCombos(mv);

        mv.addObject("vehicle", new VehicleUpload());
        mv.addObject("institution", institutionName());
        mv.addObject("topic", "Departmental Data");
        List<Vehicle> vehicles = this.vehicleService.findByClientProfileAndDepartmentOrderByVehicleIdDesc(profile, dep);
        if (vehicles == null || vehicles.isEmpty()) {
            mv.addObject("messageType", "No data found");
            mv.addObject("messageType", "fail");
            return mv;
        } else {
            mv.addObject("messageType", "Client information found");
            mv.addObject("messageType", "success");
            mv.addObject("allVehicles", vehicles);
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
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
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
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        Vehicle vehicle = this.vehicleService.findByVehicleId(id);
        if (vehicle == null) {
            redir.addFlashAttribute("Vehicle does not exist");
            redir.addFlashAttribute("fail");
            mv.setViewName("redirect:/vehicle/view_vehicle/");
            return mv;
        } else {
            loadCombos(mv);
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
            if (vehicle.getStore() != null) {
                prof.setState(vehicle.getState());
            }

            if (vehicle.getVehicleStatus() != null) {
                prof.setVehicleStatus(vehicle.getVehicleStatus());
            }
            if (vehicle.getVehicleComment() != null) {
                prof.setVehicleComment(vehicle.getVehicleComment());
            }
            mv.addObject("vehicle", prof);
            mv.setViewName("addvehicle");
            return mv;
        }
    }

    @RequestMapping("/view/vehicle/info/{id}")
    public ModelAndView viewVehicle(@PathVariable("id") Long id, HttpSession session, HttpServletRequest req, RedirectAttributes redir
    ) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        Vehicle vehicle = this.vehicleService.findByVehicleId(id);
        if (vehicle == null) {
            redir.addFlashAttribute("message", "No vehicle added");
            redir.addFlashAttribute("messageType", "fail");
            mv.addObject("vehicle", vehicle);
            mv.setViewName("view_vehicle_info");
            return mv;
        } else {
            mv.addObject("vehicle", vehicle);
            mv.setViewName("view_vehicle_info");
            return mv;
        }
    }

    @RequestMapping("/search/{id}")
    public ModelAndView searchCar(@PathVariable("plate") String plate, HttpSession session,
            HttpServletRequest req, RedirectAttributes redir
    ) {
        ModelAndView mv = new ModelAndView();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        loadCombos(mv);
        Vehicle vehicle = this.vehicleService.findByLicensePlate(plate);
        if (vehicle == null) {
            redir.addFlashAttribute("message", "No vehicle added");
            redir.addFlashAttribute("messageType", "fail");
            mv.setViewName("redirect:/administrator/view_vehicle/");
            return mv;
        } else {
            mv.addObject("institution", institutionName());
            mv.addObject("vehicle", vehicle);
            mv.setViewName("view_vehicle");
            return mv;
        }
    }

    @RequestMapping(path = "/load/combo/by/client", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> loadClientCombo(HttpSession session,
            @RequestParam String clientName
    ) {
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
            result.setMessage("Client does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
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
    public ResponseEntity<?> searchClient(HttpSession session,
            @RequestParam String clientName
    ) {
        JobResponse result = new JobResponse();
        String ns_mail = (String) session.getAttribute("ns_mail_address");
        if (ns_mail == null || ns_mail.isEmpty()) {
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        String client = clientName;
        LOG.info("Searching of :: ".toUpperCase() + client);
        ClientProfile profile = this.clientProfileService.findByCompanyName(client);
        if (profile == null) {
            result.setMessage("Client does not exist");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        List<Vehicle> vehicles = this.vehicleService.findByClientProfileOrderByVehicleIdDesc(profile);
        if (vehicles == null || vehicles.isEmpty()) {
            result.setMessage(profile.getCompanyName() + " data not found");
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
            @RequestParam String clientName,
            @RequestParam String departmentName
    ) {
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
    public ResponseEntity<InputStreamResource> generatePDFReport(HttpSession session
    ) {
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList = this.vehicleService.findAll();
        LOG.info("Detailed vehicle for generating report with data source: {}", vehicleList);
        try {
            if (printVehicles(vehicleList) != null) {
                ByteArrayResource byteArrayResource = reportService.generateDataSourceReport(vehicleList, ExportReportType.PDF, printVehicles(vehicleList));
                ByteArrayInputStream bis = (ByteArrayInputStream) byteArrayResource.getInputStream();
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Disposition", "inline; filename=vehicles.pdf");
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
        vehicleList = this.vehicleService.findAll();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=vehicles.xlsx");
        ByteArrayInputStream stream = ExcelVehicleExporter.exportListToExcelFile(vehicleList);
        IOUtils.copy(stream, response.getOutputStream());
    }

}
