package com.crm.service;

import com.crm.enums.ExportReportType;
import static com.crm.enums.ExportReportType.DOCX;
import static com.crm.enums.ExportReportType.PDF;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Description;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import net.sf.jasperreports.engine.JRException;

@Description(value = "Report service responsible for processing data.")
@Service
public class ReportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportService.class);

    /**
     * Method for generating data source report
     *
     * @param reportList - provided report list
     * @param exportReportType - export type flag
     * @param jasperPrint
     * @return byte array resource (generated file)
     * @throws IOException - input | output exception
     */
    public ByteArrayResource generateDataSourceReport(List reportList, ExportReportType exportReportType, JasperPrint jasperPrint)
            throws IOException {
        switch (exportReportType) {
            case PDF:
                return exportReportToPDF(jasperPrint);

            case DOCX:
                return exportReportToDOCx(jasperPrint);

            default:
                return null;
        }
    }

    /**
     * Method for exporting report to PDF.
     *
     * @param targetStream - target report stream
     * @param parameters - generated parameters
     * @return byte array resource (file content)
     */
    private ByteArrayResource exportReportToPDF(JasperPrint jasperPrint) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            byte[] reportContent = outputStream.toByteArray();
            return new ByteArrayResource(reportContent);
        } catch (JRException e) {
            LOGGER.error("Exporting report to PDF error: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Method for exporting report to DOCx format
     *
     * @param targetStream - target report stream
     * @param parameters - generated parameters
     * @return byte array resource (generated report file).
     */
    private ByteArrayResource exportReportToDOCx(JasperPrint jasperPrint) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JRDocxExporter exporter = new JRDocxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            exporter.exportReport();
            byte[] reportContent = outputStream.toByteArray();
            return new ByteArrayResource(reportContent);
        } catch (JRException e) {
            LOGGER.error("Exporting report to DOCx error: {}", e.getMessage());
            return null;
        }
    }

}
