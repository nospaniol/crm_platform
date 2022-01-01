package com.crm.controller.client;

import com.crm.generic.Nospaniol;
import static com.crm.generic.Nospaniol.LOG;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.JobResponse;
import com.crm.model.OrderAssetFile;
import com.crm.model.OrderShippingFile;
import com.crm.model.Fullfillment;
import com.crm.model.TransactionFile;
import com.crm.model.TransponderOrder;
import com.crm.model.TransponderOrderUpload;
import com.crm.model.VehicleFile;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({"client_transponder"})
public class ClientTransponderController extends Nospaniol {

    void loadCombos(ModelAndView mv) {
        mv.addObject("states", this.getUsStates());
    }

    @RequestMapping("/view_order")
    public ModelAndView viewOrders(@PageableDefault(100) Pageable pageable, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        redir.addFlashAttribute("menutype", "transponder_order");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            redir.addFlashAttribute("messageType", "success");
            redir.addFlashAttribute("message", "Session expired, please login again");
            return mv;
        }
        String departmentMode, departmentId;
        Department department;
        loadCombos(mv);
        mv.setViewName("clientvieworder");
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                departmentMode = (String) session.getAttribute("department_mode");
                departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department1 = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                    mv.addObject("data", this.transponderOrderService.findByDepartmentOrderByOrderIdDesc(department1));
                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    mv.addObject("data", this.transponderOrderService.findByClientProfileOrderByOrderIdDesc(profile));
                }
                break;
            case "DEPARTMENT":
                department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                mv.addObject("data", this.transponderOrderService.findByDepartmentOrderByOrderIdDesc(department));
                break;
        }
        //LOG.info("ORDER TRANSPONDER VIEWING");
        redir.addFlashAttribute("topic", "Recently placed orders");
        return mv;
    }

    @RequestMapping("/view_saved_orders")
    public ModelAndView viewSavedOrders(@PageableDefault(100) Pageable pageable, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        redir.addFlashAttribute("menutype", "transponder_order");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            redir.addFlashAttribute("messageType", "success");
            redir.addFlashAttribute("message", "Session expired, please login again");
            return mv;
        }
        String departmentMode, departmentId;
        Department department;
        loadCombos(mv);
        mv.setViewName("clientsavedorder");
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                departmentMode = (String) session.getAttribute("department_mode");
                departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department1 = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                    mv.addObject("data", this.fullfillmentService.findByDepartmentOrderByOrderIdDesc(department1));
                } else {
                    ClientProfile profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    mv.addObject("data", this.fullfillmentService.findByClientProfileOrderByOrderIdDesc(profile));
                }
                break;
            case "DEPARTMENT":
                department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                mv.addObject("data", this.fullfillmentService.findByDepartmentOrderByOrderIdDesc(department));
                break;
        }
        //LOG.info("ORDER TRANSPONDER VIEWING");
        redir.addFlashAttribute("topic", "Recently placed orders");
        return mv;
    }

    @RequestMapping("/view/info/{id}")
    public ModelAndView viewTransponderOrder(@PathVariable("id") Long id, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        redir.addFlashAttribute("menutype", "transponder_order");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        //LOG.info("ORDER INFO LOAD");
        mv.setViewName("clientorderinfo");
        TransponderOrder transponderOrder = null;
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
                    transponderOrder = this.transponderOrderService.findByClientProfileAndOrderId(profile, id);
                }
                break;
            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                break;
            default:
            //LOG.info("USER ACCOUNT UNKNOWN");
        }

        //LOG.info("ORDER INFO PROCESSED");
        if (transponderOrder == null) {
            mv.addObject("Transponder order does not exist");
            mv.addObject("fail");
            mv.setViewName("redirect:/client_transponder/view_transponder/");
            return mv;
        } else {
            mv.addObject("transponder", transponderOrder);
            return mv;
        }
    }

    @RequestMapping("/cancel/{id}")
    public ModelAndView cancelTransponderOrder(@PathVariable("id") Long id, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        redir.addFlashAttribute("menutype", "transponder_order");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        mv.setViewName("clientorderinfo");
        TransponderOrder transponderOrder = null;
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
                    TransponderOrder transponder = this.transponderOrderService.findByClientProfileAndOrderId(profile, id);
                    if (transponder != null) {
                        if (transponder.getOrderStatus().equals("processing")) {
                            transponder.setOrderStatus("cancelled");
                            transponderOrder = this.transponderOrderService.save(transponder);
                            mv.addObject("transponder", transponderOrder);
                            mv.addObject("message", "Order cancelled successfully!");
                            mv.addObject("messageType", "success");
                        } else {
                            mv.addObject("transponder", transponder);
                            mv.addObject("message", "You can only cancel an order while in processing, please contact us!");
                            mv.addObject("messageType", "fail");
                        }
                    } else {
                        mv.addObject("transponder", transponder);
                        mv.addObject("message", "This transponder was not found!");
                        mv.addObject("messageType", "fail");
                    }
                }
                break;
            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                break;
            default:
            //LOG.info("USER ACCOUNT UNKNOWN");
        }

        return mv;

    }

    @RequestMapping("/view/saved/info/{id}")
    public ModelAndView viewSavedTransponderOrder(@PathVariable("id") Long id, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        redir.addFlashAttribute("menutype", "transponder_order");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        //LOG.info("ORDER INFO LOAD");
        mv.setViewName("clientsavedorderinfo");
        Fullfillment transponderOrder = null;
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
                    transponderOrder = this.fullfillmentService.findByClientProfileAndOrderId(profile, id);
                }
                break;
            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                break;
            default:
            //LOG.info("USER ACCOUNT UNKNOWN");
        }

        //LOG.info("ORDER INFO PROCESSED");
        if (transponderOrder == null) {
            redir.addFlashAttribute("message", "Transponder order does not exist");
            redir.addFlashAttribute("messageType", "fail");
            mv.setViewName("redirect:/client_transponder/view_saved_orders/");
            return mv;
        } else {
            //LOG.info(transponderOrder.toString());
            mv.addObject("transponder", transponderOrder);
            return mv;
        }
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editTransponderOrder(@PathVariable("id") Long id, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        redir.addFlashAttribute("menutype", "transponder_order");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            redir.addFlashAttribute("messageType", "success");
            redir.addFlashAttribute("message", "Session expired, please login again");
            return mv;
        }
        loadCombos(mv);
        TransponderOrder transponderOrder = null;
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                String departmentMode = (String) session.getAttribute("department_mode");
                String departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));

                } else {
                    ClientProfile transponderOrderUploadile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    transponderOrder = this.transponderOrderService.findByClientProfileAndOrderId(transponderOrderUploadile, id);
                }
                break;
            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                break;
            default:
            //LOG.info("USER ACCOUNT UNKNOWN");
        }
        if (transponderOrder == null) {
            redir.addFlashAttribute("Transponder order does not exist");
            redir.addFlashAttribute("fail");
            mv.setViewName("redirect:/client_transponder/view_order/");
            return mv;
        } else {

            TransponderOrderUpload transponderOrderUpload = new TransponderOrderUpload();
            transponderOrderUpload.setOrderId(transponderOrder.getOrderId());
            transponderOrderUpload.setTransponderQuantity(transponderOrder.getTransponderQuantity());

            if (transponderOrder.getExtraVelcro() != 0) {
                transponderOrderUpload.setExtraVelcro(transponderOrder.getExtraVelcro());
            }
            if (transponderOrder.getLicensePlate() != null) {
                transponderOrderUpload.setLicensePlate(transponderOrder.getLicensePlate());
            }
            if (transponderOrder.getState() != null) {
                transponderOrderUpload.setState(transponderOrder.getState());
            }
            if (transponderOrder.getDepartment() != null) {
                transponderOrderUpload.setDepartment(transponderOrder.getDepartment().getDepartmentName());
            }
            if (transponderOrder.getDomicileTerminal() != null) {
                transponderOrderUpload.setDomicileTerminal(transponderOrder.getDomicileTerminal());
            }
            if (transponderOrder.getInstructions() != null) {
                transponderOrderUpload.setInstructions(transponderOrder.getInstructions());
            }
            if (transponderOrder.getShippingAddress() != null) {
                transponderOrderUpload.setShippingAddress(transponderOrder.getShippingAddress());
            }
            if (transponderOrder.getCustomerVehicleId() != null) {
                transponderOrderUpload.setCustomerVehicleId(transponderOrder.getCustomerVehicleId());
            }
            if (transponderOrder.getAssetName() != null) {
                transponderOrderUpload.setAssetName(transponderOrder.getAssetName());
            }
            mv.addObject("transponders", transponderOrderUpload);
            mv.setViewName("clientmakeorder");
            return mv;
        }
    }

    @RequestMapping("/complete/{id}")
    public ModelAndView completeTransponderOrder(@PathVariable("id") Long id, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        redir.addFlashAttribute("menutype", "transponder_order");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            redir.addFlashAttribute("messageType", "success");
            redir.addFlashAttribute("message", "Session expired, please login again");
            return mv;
        }
        loadCombos(mv);
        TransponderOrder transponderOrder = null;
        Fullfillment fullfillment = null;
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                String departmentMode = (String) session.getAttribute("department_mode");
                String departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));

                } else {
                    ClientProfile transponderOrderUploadile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    fullfillment = this.fullfillmentService.findByClientProfileAndOrderId(transponderOrderUploadile, id);
                    if (fullfillment != null) {
                        transponderOrder = new TransponderOrder();
                        transponderOrder.setTransponderQuantity(fullfillment.getTransponderQuantity());
                        transponderOrder.setDomicileTerminal(fullfillment.getDomicileTerminal());
                        transponderOrder.setExtraVelcro(fullfillment.getExtraVelcro());
                        transponderOrder.setInstructions(fullfillment.getInstructions());
                        transponderOrder.setLicensePlate(fullfillment.getLicensePlate());
                        transponderOrder.setShippingAddress(fullfillment.getShippingAddress());
                        transponderOrder.setCustomerVehicleId(fullfillment.getCustomerVehicleId());
                    }
                }
                break;
            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                break;
            default:
            //LOG.info("USER ACCOUNT UNKNOWN");
        }

        TransponderOrderUpload transponderOrderUpload = new TransponderOrderUpload();
        if (transponderOrder == null) {
            mv.addObject("transponders", transponderOrderUpload);
            mv.setViewName("clientmakeorder");
            return mv;
        } else {

            transponderOrderUpload = new TransponderOrderUpload();
            //transponderOrderUpload.setOrderId(transponderOrder.getOrderId());

            transponderOrderUpload.setTransponderQuantity(transponderOrder.getTransponderQuantity());

            if (transponderOrder.getExtraVelcro() != 0) {
                transponderOrderUpload.setExtraVelcro(transponderOrder.getExtraVelcro());
            }
            if (transponderOrder.getLicensePlate() != null) {
                transponderOrderUpload.setLicensePlate(transponderOrder.getLicensePlate());
            }
            if (transponderOrder.getState() != null) {
                transponderOrderUpload.setState(transponderOrder.getState());
            }
            if (transponderOrder.getDepartment() != null) {
                transponderOrderUpload.setDepartment(transponderOrder.getDepartment().getDepartmentName());
            }
            if (transponderOrder.getDomicileTerminal() != null) {
                transponderOrderUpload.setDomicileTerminal(transponderOrder.getDomicileTerminal());
            }
            if (transponderOrder.getInstructions() != null) {
                transponderOrderUpload.setInstructions(transponderOrder.getInstructions());
            }
            if (transponderOrder.getShippingAddress() != null) {
                transponderOrderUpload.setShippingAddress(transponderOrder.getShippingAddress());
            }
            if (transponderOrder.getCustomerVehicleId() != null) {
                transponderOrderUpload.setCustomerVehicleId(transponderOrder.getCustomerVehicleId());
            }
            if (transponderOrder.getAssetName() != null) {
                transponderOrderUpload.setAssetName(transponderOrder.getAssetName());
            }
            if (fullfillment != null) {
                this.fullfillmentService.delete(fullfillment);
            }
            mv.addObject("transponders", transponderOrderUpload);
            mv.setViewName("clientmakeorder");
            return mv;
        }
    }

    @RequestMapping("/download/asset_file/{id}")
    public void downloadAssetFile(@PathVariable("id") Long id, HttpServletResponse response, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        try {
            String ns_mail = (String) session.getAttribute("ns_mail_address");
            if (ns_mail == null || ns_mail.isEmpty()) {
                return;
            }
            String logged_user = (String) session.getAttribute("ns_user");
            if (logged_user == null || logged_user.equals("CLIENT")) {
                return;
            }
            OrderAssetFile download = this.orderAssetFileService.findByOrderFileId(id);
            if (download != null) {
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename=transactionupload.xlsx");
                if (download.getOrders() == null) {
                    //LOG.info("This file does not exist for file id :: ".toUpperCase() + download.getOrderName());
                } else {
                    //LOG.info("This file found for file id :: ".toUpperCase() + download.getOrderName());
                    ByteArrayInputStream stream = new ByteArrayInputStream(download.getOrders().getData());
                    IOUtils.copy(stream, response.getOutputStream());
                }
            } else {
                //LOG.info("This file does not exist with id:: ".toUpperCase() + String.valueOf(id));
            }
        } catch (IOException ex) {
            LOG.error(ex.toString());
        }
    }

    @RequestMapping("/download/shipping_file/{id}")
    public void downloadShippingFile(@PathVariable("id") Long id, HttpServletResponse response, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        try {
            String ns_mail = (String) session.getAttribute("ns_mail_address");
            if (ns_mail == null || ns_mail.isEmpty()) {
                return;
            }
            String logged_user = (String) session.getAttribute("ns_user");
            if (logged_user == null || logged_user.equals("CLIENT")) {
                return;
            }
            OrderShippingFile download = this.orderShippingFileService.findByOrderFileId(id);
            if (download != null) {
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename=transactionupload.xlsx");
                if (download.getOrders() == null) {
                    //LOG.info("This file does not exist for file id :: ".toUpperCase() + download.getOrderName());
                } else {
                    //LOG.info("This file found for file id :: ".toUpperCase() + download.getOrderName());
                    ByteArrayInputStream stream = new ByteArrayInputStream(download.getOrders().getData());
                    IOUtils.copy(stream, response.getOutputStream());
                }
            } else {
                //LOG.info("This file does not exist with id:: ".toUpperCase() + String.valueOf(id));
            }
        } catch (IOException ex) {
            LOG.error(ex.toString());
        }
    }

    @RequestMapping("/delete/saved/{id}")
    public ModelAndView deleteSavedTransponderOrder(@PathVariable("id") Long id, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        redir.addFlashAttribute("menutype", "transponder_order");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            redir.addFlashAttribute("messageType", "success");
            redir.addFlashAttribute("message", "Session expired, please login again");
            return mv;
        }
        loadCombos(mv);
        TransponderOrder transponderOrder = null;
        Fullfillment fullfillment = null;
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                String departmentMode = (String) session.getAttribute("department_mode");
                String departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    Department department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));

                } else {
                    ClientProfile transponderOrderUploadile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    fullfillment = this.fullfillmentService.findByClientProfileAndOrderId(transponderOrderUploadile, id);
                    if (fullfillment != null) {
                        this.fullfillmentService.delete(fullfillment);
                    }
                }
                break;
            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                break;
            default:
                //LOG.info("USER ACCOUNT UNKNOWN");
                break;
        }
        redir.addFlashAttribute("message", "Saved fullfillment removed!");
        redir.addFlashAttribute("messageType", "fail");
        mv.setViewName("redirect:/client_transponder/view_saved_orders/");
        return mv;
    }

    @RequestMapping("/make_order")
    public ModelAndView registerTransponderOrder(HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        redir.addFlashAttribute("menutype", "transponder_order");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            redir.addFlashAttribute("messageType", "success");
            redir.addFlashAttribute("message", "Session expired, please login again");
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

                }
                break;
            case "DEPARTMENT":
                Department department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));

                break;
            default:
            //LOG.info("USER ACCOUNT UNKNOWN");
        }

        TransponderOrderUpload transponderOrderUpload = new TransponderOrderUpload();
        mv.addObject("transponders", transponderOrderUpload);
        mv.setViewName("clientmakeorder");
        return mv;

    }

    @RequestMapping(value = "upload/file/{save}", method = RequestMethod.POST)
    public ModelAndView uploadTransponder(@ModelAttribute("transponders") TransponderOrderUpload Order, MultipartFile file, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
        //LOG.info("ORDER HAS BEEN PLACED");
        ModelAndView mv = new ModelAndView();
        redir.addFlashAttribute("menutype", "transponder_order");
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            mv.setViewName("user-logout");
            redir.addFlashAttribute("messageType", "success");
            redir.addFlashAttribute("message", "Session expired, please login again");
            return mv;
        }
        mv.setViewName("clientmakeorder");
        mv.addObject("transponder", Order);
        loadCombos(mv);
        ClientProfile profile = new ClientProfile();
        Department department = new Department();
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                String departmentMode = (String) session.getAttribute("department_mode");
                String departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    if (Order.getDepartment() == null || Order.getDepartment().isEmpty()) {
                        department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                    } else {
                        department = this.departmentService.findByDepartmentName(Order.getDepartment());
                    }
                } else {
                    department = null;
                    profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                    if (profile == null) {
                        redir.addFlashAttribute("messageType", "fail");
                        redir.addFlashAttribute("message", "Client not found in the system");
                        return mv;
                    }
                }
                break;
            case "DEPARTMENT":
                department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                profile = department.getClientProfile();

                break;
            default:
            //LOG.info("USER ACCOUNT UNKNOWN");
        }
        TransponderOrder order = new TransponderOrder();
        if (Order.getOrderId() == null || Order.getOrderId() == 0) {
            order.setOrderId(seqGeneratorService.generateSequence(TransponderOrder.SEQUENCE_NAME));
        } else {
            order.setOrderId(Order.getOrderId());
        }

        try {

            FileOutputStream fileOuputStream = null;
            Binary logo = new Binary(BsonBinarySubType.BINARY, Order.getShippingFile().getBytes());
            if (logo.length() <= 0) {

            } else {

                //LOG.info("\n Import Excel Invoked \n".toUpperCase());
                String path = System.getProperty("java.io.tmpdir");
                fileOuputStream = new FileOutputStream(path.substring(0, path.length() - 1) + Order.getShippingFile().getOriginalFilename());
                fileOuputStream.write(Order.getShippingFile().getBytes());
                String fileLocation = path.substring(0, path.length() - 1) + Order.getShippingFile().getOriginalFilename();
                //LOG.info("\n File Name :: ".toUpperCase() + fileLocation + "\n");
                //LOG.info("Reading information from excel file  ".toUpperCase() + fileLocation);
                OrderShippingFile shippingFile = new OrderShippingFile();
                shippingFile.setOrderFileId(seqGeneratorService.generateSequence(VehicleFile.SEQUENCE_NAME));
                shippingFile.setClientProfile(profile);
                shippingFile.setOrderName(Order.getShippingFile().getOriginalFilename());
                if (department != null) {
                    shippingFile.setDepartment(department);
                }
                shippingFile.setOrders(logo);
                shippingFile.setUpdatedDate(this.getCurrentDate());
                shippingFile.setCreationDate(this.getCurrentDate());
                shippingFile.setCreationTime(this.getCurrentTimestamp());
                shippingFile.setUpdatedTime(this.getCurrentTimestamp());
                OrderShippingFile newShippingFile = this.orderShippingFileService.save(shippingFile);
                if (newShippingFile == null) {
                    mv.addObject("messageType", "fail");
                    mv.addObject("message", "Shipping file uploaded failed to save, please try again!");
                    return mv;
                }

                order.setOrderShippingFile(newShippingFile);
            }

            if (Order.getTransponderQuantity() == 0) {
                mv.addObject("messageType", "fail");
                mv.addObject("message", "Fill all required fields!");
                return mv;
            }

            if (profile.getCompanyName().equals("AMAZON")) {
                logo = new Binary(BsonBinarySubType.BINARY, Order.getAssetFile().getBytes());
                if (logo.length() <= 0) {
                } else {
                    fileOuputStream = null;
                    //LOG.info("\n Import Excel Invoked \n".toUpperCase());
                    String path = System.getProperty("java.io.tmpdir");
                    fileOuputStream = new FileOutputStream(path.substring(0, path.length() - 1) + Order.getAssetFile().getOriginalFilename());
                    fileOuputStream.write(Order.getAssetFile().getBytes());
                    String fileLocation = path.substring(0, path.length() - 1) + Order.getAssetFile().getOriginalFilename();
                    OrderAssetFile assetFile = new OrderAssetFile();
                    assetFile.setOrderFileId(seqGeneratorService.generateSequence(OrderAssetFile.SEQUENCE_NAME));
                    assetFile.setClientProfile(profile);
                    assetFile.setOrderName(Order.getAssetFile().getOriginalFilename());
                    if (department != null) {
                        assetFile.setDepartment(department);
                    }
                    assetFile.setOrders(logo);
                    assetFile.setUpdatedDate(this.getCurrentDate());
                    assetFile.setCreationDate(this.getCurrentDate());
                    assetFile.setCreationTime(this.getCurrentTimestamp());
                    assetFile.setUpdatedTime(this.getCurrentTimestamp());
                    //LOG.info(assetFile.toString());

                    OrderAssetFile newAssetFile = this.orderAssetFileService.save(assetFile);
                    if (newAssetFile == null) {
                        //LOG.info("Asset file uploaded failed to save ".toUpperCase());
                        mv.addObject("messageType", "fail");
                        mv.addObject("message", "Asset file uploaded failed to save, please try again!");
                        return mv;
                    }
                    order.setOrderAssetFile(newAssetFile);
                }

                if (Order.getAssetName().isEmpty()) {
                    //LOG.info("Failed to fill all fields ".toUpperCase());
                    mv.addObject("messageType", "fail");
                    mv.addObject("message", "Fill all required fields!");
                    return mv;
                }

                order.setAssetName(Order.getAssetName());
                if (!Order.getDomicileTerminal().isEmpty()) {
                    order.setDomicileTerminal(Order.getDomicileTerminal());
                }
            }

            if (profile.getCompanyName().equals("PROTECH AS")) {
                if (department == null || Order.getLicensePlate().isEmpty() || Order.getCustomerVehicleId().isEmpty() || Order.getState().isEmpty()) {
                    //LOG.info("Failed to fill all fields ".toUpperCase());
                    redir.addFlashAttribute("messageType", "fail");
                    redir.addFlashAttribute("message", "Fill all required fields!");
                    return mv;
                }
                order.setLicensePlate(Order.getLicensePlate());
                order.setDepartment(department);
                order.setState(Order.getState());
                order.setCustomerVehicleId(Order.getCustomerVehicleId());
            }
            order.setClientProfile(profile);
            order.setTransponderQuantity(Order.getTransponderQuantity());
            order.setShippingAddress(Order.getShippingAddress());
            if (Order.getExtraVelcro() != 0) {
                order.setExtraVelcro(Order.getExtraVelcro());
            }
            order.setOrderStatus("processing");
            order.setStatus(true);
            order.setUpdatedDate(this.getCurrentDate());
            order.setCreationDate(this.getCurrentDate());
            order.setCreationTime(this.getCurrentTimestamp());
            order.setUpdatedTime(this.getCurrentTimestamp());
            //LOG.info("Saving order...");
            TransponderOrder newOrder = this.transponderOrderService.save(order);
            TransponderOrderUpload transponderOrderUpload = new TransponderOrderUpload();
            mv.addObject("transponders", transponderOrderUpload);
            //LOG.info("Processed order successfully ".toUpperCase());
            mv.addObject("messageType", "success");
            mv.addObject("message", "Order placed successfully");
            return mv;
        } catch (IOException ex) {
            mv.setViewName("redirect:/client_transponder/make_order");
            redir.addFlashAttribute("messageType", "fail");
            redir.addFlashAttribute("message", "Failed : " + ex);
            return mv;
        }
    }

    @RequestMapping(path = {"/save/order/"}, produces = {"application/json; charset=UTF-8"}, method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<?> savingOrder(@ModelAttribute("fullfillment") Fullfillment transponder, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        JobResponse result = new JobResponse();
        String clientId = (String) session.getAttribute("NsId");
        if (clientId == null || clientId.isEmpty()) {
            //LOG.info("Invalid session detected, please login again!");
            result.setMessage("Invalid session detected, please login again!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
        //LOG.info("Saving transonder order!");
        ClientProfile profile = null;
        Department department = null;
        String user_type = (String) session.getAttribute("ns_user");
        switch (user_type) {
            case "CLIENT":
                String departmentMode = (String) session.getAttribute("department_mode");
                String departmentId = (String) session.getAttribute("department_id");
                if (departmentMode != null && departmentMode.equals("YES")) {
                    department = this.departmentService.findByDepartmentId(Long.valueOf(departmentId));
                } else {
                    profile = this.clientProfileService.findByClientProfileId(Long.valueOf(clientId));
                }
                break;
            case "DEPARTMENT":
                department = this.departmentService.findByDepartmentId(Long.valueOf(clientId));
                break;
            default:
            //LOG.info("USER ACCOUNT UNKNOWN");
        }
        transponder.setOrderId(seqGeneratorService.generateSequence(Fullfillment.SEQUENCE_NAME));
        transponder.setClientProfile(profile);
        Fullfillment order = this.fullfillmentService.save(transponder);
        if (order != null) {
            //LOG.info("Saved successfully!");
            result.setMessage("Saved successfully!");
            result.setTitle("success");
            return ResponseEntity.ok(result);
        } else {
            //LOG.info("Failed to save!");
            result.setMessage("Failed to save, please try again later!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }
    }

}
