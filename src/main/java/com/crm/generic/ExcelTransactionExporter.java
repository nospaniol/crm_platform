package com.crm.generic;

/**
 *
 * @author nospaniol
 */
import com.crm.model.Transaction;
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

public class ExcelTransactionExporter {

    public static final Logger LOG = LoggerFactory.getLogger(Nospaniol.class);

    public static ByteArrayInputStream exportListToExcelFile(List<Transaction> transactions) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Transactions");

            Row row = sheet.createRow(0);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            // Creating header
            Cell cell = row.createCell(0);
            cell.setCellValue("License Plate");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(1);
            cell.setCellValue("State");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(2);
            cell.setCellValue("Agency");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(3);
            cell.setCellValue("Exit Date/Time");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(4);
            cell.setCellValue("Exit Location");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(5);
            cell.setCellValue("Exit Lane");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(6);
            cell.setCellValue("Amount");
            cell.setCellStyle(headerCellStyle);

            // Creating data rows for each transaction
            for (int i = 0; i < transactions.size(); i++) {
                Row dataRow = sheet.createRow(i + 1);
                dataRow.createCell(0).setCellValue(transactions.get(i).getVehicle().getLicensePlate());
                dataRow.createCell(1).setCellValue(transactions.get(i).getState());
                dataRow.createCell(2).setCellValue(transactions.get(i).getAgency());
                dataRow.createCell(3).setCellValue(String.valueOf(transactions.get(i).getExitDateTime()));
                dataRow.createCell(4).setCellValue(transactions.get(i).getExitLocation());
                dataRow.createCell(5).setCellValue(transactions.get(i).getExitLane());
                if (transactions.get(i).getAmount() != null) {
                    dataRow.createCell(6).setCellValue(transactions.get(i).getAmount());
                } else {
                    dataRow.createCell(6).setCellValue("");
                }
            }

            // Making size of column auto resize to fit with data
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException ex) {
            LOG.error(ex.getMessage());
            return null;
        }
    }
}
