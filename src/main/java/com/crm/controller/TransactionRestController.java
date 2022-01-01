package com.crm.controller;

import com.crm.generic.Nospaniol;
import static com.crm.generic.Nospaniol.LOG;
import com.crm.model.EmailToken;
import com.crm.model.GenericResponse;
import com.crm.model.LoginResponse;
import com.crm.model.Transaction;
import com.crm.model.User;
import com.crm.model.Vehicle;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.crm.generic.ExcelTransactionExporter;
import com.crm.model.TransactionResponse;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"innovative_transaction"})
public class TransactionRestController extends Nospaniol {

    @GetMapping(path = {"/filter/by/{email}/{licensePlate}/{startDate}/{endDate}/transactions.xlsx"})
    @ResponseBody
    public void filterTransactionDateVehicle(HttpServletResponse response, @PathVariable("email") String demail, @PathVariable("licensePlate") String licensePlate, @PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) throws JsonProcessingException, IOException {
        LoginResponse result = new LoginResponse();
        LOG.info("*****************LICENSE PLATE TRANSACTIONS DATE RANGE*******************");
        String email = this.decrypt(demail, "nospaniolnoah");
        EmailToken etoken = this.emailTokenService.findByEmailAddress(email.toLowerCase());
        User checkUser = this.userService.findByEmailToken(etoken);
        if (checkUser == null) {
            LOG.info("*****************EMAIL DOES NOT EXIST*******************");
            result.setMessage(email.toUpperCase() + " does not exist!");
            result.setTitle("fail");
            GenericResponse generic = new GenericResponse();
            generic.setResult(result.toString());
            return;
        }

        Date start = this.getConvertedDate(startDate);
        Date end = this.getConvertedDate(endDate);
        LOG.info("*****************START DATE RANGE*******************");
        LOG.info(start.toString());
        LOG.info("*****************END DATE RANGE*******************");
        LOG.info(end.toString());
        LOG.info("*****************VEHICLE PLATE*******************");
        LOG.info(licensePlate);
        LOG.info("*****************SEARCHING*******************");

        Vehicle vehicle = this.vehicleService.findByLicensePlate(licensePlate);
        if (vehicle == null) {
            vehicle = this.vehicleService.findByVin(licensePlate);
        }
        if (vehicle != null) {
            List<Transaction> transactionList = getTransactionsByDate(vehicle, start, end);
            LOG.info("Detailed transaction for generating report with data source: {}", transactionList);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=transactions.xlsx");
            ByteArrayInputStream stream = ExcelTransactionExporter.exportListToExcelFile(transactionList);
            IOUtils.copy(stream, (OutputStream) response.getOutputStream());
        } else {
            List<Transaction> transactionList = new ArrayList<>();
            LOG.info("Vehicle was not found", transactionList);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=transactions.xlsx");
            ByteArrayInputStream stream = ExcelTransactionExporter.exportListToExcelFile(transactionList);
            IOUtils.copy(stream, (OutputStream) response.getOutputStream());
        }
    }

    private List<Transaction> getTransactionsByDate(Vehicle vehicle, Date startDate, Date endDate) {
        Aggregation aggregation1 = Aggregation.newAggregation(new AggregationOperation[]{
            (AggregationOperation) Aggregation.match(Criteria.where("transactionDispute").is(false).and("vehicle").is(vehicle).and("transactionDate").gte(startDate).lte(endDate))
        });
        AggregationResults<Transaction> aggregationResults = this.mongoTemplate.aggregate(aggregation1, Transaction.class, Transaction.class);
        List<Transaction> transaction_list = aggregationResults.getMappedResults();
        return transaction_list;
    }

    JasperPrint printTransactions(List<Transaction> transactionList) {
        try {
            JRBeanCollectionDataSource transactionDataSource = new JRBeanCollectionDataSource(transactionList);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("TransactionDataSource", transactionDataSource);
            ClassPathResource fStream = new ClassPathResource("reportsDesign/transactions.jrxml");
            InputStream inputStream = fStream.getInputStream();
            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, (JRDataSource) new JREmptyDataSource());
            return jasperPrint;
        } catch (IOException | net.sf.jasperreports.engine.JRException ex) {
            LOG.info(ex.toString().toUpperCase());
            return null;
        }
    }

    @RequestMapping(path = {"/filter/by/{email}/{licensePlate}/{startDate}/{endDate}"}, produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> spartanTransactions(@PathVariable("email") String demail, @PathVariable("licensePlate") String licensePlate, @PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) throws JsonProcessingException, IOException {
        TransactionResponse result = new TransactionResponse();
        LOG.info("*****************LICENSE PLATE TRANSACTIONS DATE RANGE*******************");
        String email = this.decrypt(demail, "nospaniolnoah");
        EmailToken etoken = this.emailTokenService.findByEmailAddress(email.toLowerCase());
        User checkUser = this.userService.findByEmailToken(etoken);
        if (checkUser == null) {
            LOG.info("*****************EMAIL DOES NOT EXIST*******************");
            result.setMessage("Consumer does not exist!");
            result.setTitle("fail");
            return ResponseEntity.ok(result);
        }

        Date start = this.getConvertedDate(startDate);
        Date end = this.getConvertedDate(endDate);
        LOG.info("*****************START DATE RANGE*******************");
        LOG.info(start.toString());
        LOG.info("*****************END DATE RANGE*******************");
        LOG.info(end.toString());
        LOG.info("*****************VEHICLE PLATE*******************");
        LOG.info(licensePlate);
        LOG.info("*****************SEARCHING*******************");

        Vehicle vehicle = this.vehicleService.findByLicensePlate(licensePlate);
        if (vehicle == null) {
            vehicle = this.vehicleService.findByVin(licensePlate);
        }
        if (vehicle != null) {
            List<Transaction> transactionList = getTransactionsByDate(vehicle, start, end);
            result.setMessage("Vehicle found : " + licensePlate + " !");
            result.setTitle("success");
            result.setResults(transactionList);
        } else {
            result.setMessage("Vehicle not found : " + licensePlate + " !");
            result.setTitle("fail");
            List<Transaction> transactionList = new ArrayList<>();
            result.setResults(transactionList);
        }
        return ResponseEntity.ok(result);
    }

}
