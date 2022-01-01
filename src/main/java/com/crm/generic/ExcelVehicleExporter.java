package com.crm.generic;

/**
 *
 * @author nospaniol
 */
import com.crm.model.Vehicle;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelVehicleExporter {

    public static final Logger LOG = LoggerFactory.getLogger(Nospaniol.class);

    public static ByteArrayInputStream exportListToExcelFile(List<Vehicle> vehicles) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Vehicles");

            Row row = sheet.createRow(0);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            // Creating header
            Cell cell = row.createCell(0);
            cell.setCellValue("License Plate");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(1);
            cell.setCellValue("Toll Tag");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(2);
            cell.setCellValue("Unit");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(3);
            cell.setCellValue("Vin");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(4);
            cell.setCellValue("Make");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(5);
            cell.setCellValue("Model");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(6);
            cell.setCellValue("Color");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(7);
            cell.setCellValue("Start Date");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(8);
            cell.setCellValue("End Date");
            cell.setCellStyle(headerCellStyle);

            // Creating data rows for each vehicle
            for (int i = 0; i < vehicles.size(); i++) {
                Row dataRow = sheet.createRow(i + 1);
                dataRow.createCell(0).setCellValue(vehicles.get(i).getLicensePlate());
                dataRow.createCell(1).setCellValue(vehicles.get(i).getTollTagId());
                dataRow.createCell(2).setCellValue(vehicles.get(i).getUnit());
                dataRow.createCell(3).setCellValue(vehicles.get(i).getVin());
                dataRow.createCell(4).setCellValue(vehicles.get(i).getMake());
                dataRow.createCell(5).setCellValue(vehicles.get(i).getModel());
                dataRow.createCell(6).setCellValue(vehicles.get(i).getColor());
                dataRow.createCell(7).setCellValue(String.valueOf(vehicles.get(i).getStartDate()));
                dataRow.createCell(8).setCellValue(String.valueOf(vehicles.get(i).getEndDate()));
            }

            // Making size of column auto resize to fit with data
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);
            sheet.autoSizeColumn(7);
            sheet.autoSizeColumn(8);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException ex) {
            LOG.error(ex.toString());
            return null;
        }
    }
}
