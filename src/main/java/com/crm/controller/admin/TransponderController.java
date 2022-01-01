package com.crm.controller.admin;

import com.crm.generic.Nospaniol;
import static com.crm.generic.Nospaniol.LOG;
import com.crm.model.TransponderOrder;
import com.crm.model.TransponderOrderUpload;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({"transponder"})
public class TransponderController extends Nospaniol {

  

    @RequestMapping("/manage")
    public ModelAndView viewOrders(@PageableDefault(100) Pageable pageable, RedirectAttributes redir, HttpServletRequest req, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "transponder_order");
       String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        mv.setViewName("adminvieworder");
        String user_type = (String) session.getAttribute("ns_user");
        mv.addObject("data", this.transponderOrderService.findAllByOrderIdDesc());
        mv.addObject("topic", "Recently placed orders");
        return mv;
    }

    @RequestMapping("/processing/{id}")
    public ModelAndView processingTransponderOrder(@PathVariable("id") Long id, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "transponder_order");
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        LOG.info("ORDER INFO LOAD");
        mv.setViewName("adminorderinfo");
        TransponderOrder transponderOrder = this.transponderOrderService.findByOrderId(id);
        LOG.info("ORDER INFO PROCESSED");
        if (transponderOrder == null) {
            redir.addFlashAttribute("Transponder order does not exist");
            redir.addFlashAttribute("fail");
            mv.setViewName("redirect:/transponder/view_transponder/");
            return mv;
        } else {
            transponderOrder.setOrderStatus("processing");
            TransponderOrder transponder = this.transponderOrderService.save(transponderOrder);
            mv.addObject("transponder", transponder);
            return mv;
        }
    }

    @RequestMapping("/shipping/{id}")
    public ModelAndView shippingTransponderOrder(@PathVariable("id") Long id, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "transponder_order");
         String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        LOG.info("ORDER INFO LOAD");
        mv.setViewName("adminorderinfo");
        TransponderOrder transponderOrder = this.transponderOrderService.findByOrderId(id);
        LOG.info("ORDER INFO PROCESSED");
        if (transponderOrder == null) {
            redir.addFlashAttribute("Transponder order does not exist");
            redir.addFlashAttribute("fail");
            mv.setViewName("redirect:/transponder/view_transponder/");
            return mv;
        } else {
            transponderOrder.setOrderStatus("shipping");
            TransponderOrder transponder = this.transponderOrderService.save(transponderOrder);
            mv.addObject("transponder", transponder);
            return mv;
        }
    }

    @RequestMapping("/shipped/{id}")
    public ModelAndView shippedTransponderOrder(@PathVariable("id") Long id, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "transponder_order");
        String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        LOG.info("ORDER INFO LOAD");
        mv.setViewName("adminorderinfo");
        TransponderOrder transponderOrder = this.transponderOrderService.findByOrderId(id);
        LOG.info("ORDER INFO PROCESSED");
        if (transponderOrder == null) {
            redir.addFlashAttribute("Transponder order does not exist");
            redir.addFlashAttribute("fail");
            mv.setViewName("redirect:/transponder/view_transponder/");
            return mv;
        } else {
            transponderOrder.setOrderStatus("shipped");
            TransponderOrder transponder = this.transponderOrderService.save(transponderOrder);
            mv.addObject("transponder", transponder);
            return mv;
        }
    }

    @RequestMapping("/delivered/{id}")
    public ModelAndView deliveredTransponderOrder(@PathVariable("id") Long id, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "transponder_order");
       String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        LOG.info("ORDER INFO LOAD");
        mv.setViewName("adminorderinfo");
        TransponderOrder transponderOrder = this.transponderOrderService.findByOrderId(id);
        LOG.info("ORDER INFO PROCESSED");
        if (transponderOrder == null) {
            redir.addFlashAttribute("Transponder order does not exist");
            redir.addFlashAttribute("fail");
            mv.setViewName("redirect:/transponder/view_transponder/");
            return mv;
        } else {
            transponderOrder.setOrderStatus("delivered");
            TransponderOrder transponder = this.transponderOrderService.save(transponderOrder);
            mv.addObject("transponder", transponder);
            return mv;
        }
    }

    @RequestMapping("/view/info/{id}")
    public ModelAndView viewTransponderOrder(@PathVariable("id") Long id, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "transponder_order");
         String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        LOG.info("ORDER INFO LOAD");
        mv.setViewName("adminorderinfo");
        TransponderOrder transponderOrder = this.transponderOrderService.findByOrderId(id);
        LOG.info("ORDER INFO PROCESSED");
        if (transponderOrder == null) {
            redir.addFlashAttribute("Transponder order does not exist");
            redir.addFlashAttribute("fail");
            mv.setViewName("redirect:/transponder/view_transponder/");
            return mv;
        } else {
            mv.addObject("transponder", transponderOrder);
            return mv;
        }
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editTransponderOrder(@PathVariable("id") Long id, HttpSession session, HttpServletRequest req, RedirectAttributes redir) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("menutype", "transponder_order");
       String logged_user = (String) session.getAttribute("ns_user");
        if (logged_user == null || logged_user.equals("CLIENT")) {
            mv.setViewName("user-logout");
            mv.addObject("messageType", "success");
            mv.addObject("message", "Session expired, please login again");
            return mv;
        }
        
        TransponderOrder transponderOrder = this.transponderOrderService.findByOrderId(id);

        if (transponderOrder == null) {
            redir.addFlashAttribute("Transponder order does not exist");
            redir.addFlashAttribute("fail");
            mv.setViewName("redirect:/transponder/view_order/");
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
            mv.addObject("transponder", transponderOrderUpload);
            mv.setViewName("clientmakeorder");
            return mv;
        }
    }

}
