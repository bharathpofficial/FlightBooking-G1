package org.agile.qa.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.DateUtil;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtils {

    /**
     * Reads the specified Excel sheet and returns data as a List of Maps.
     * Each map represents a row: header (from the first row) -> value.
     * Supports both .xls and .xlsx formats.
     *
     * @param filePath Path to the Excel file.
     * @param sheetName Name of the sheet to read.
     * @return List of rows where each row is a Map of header:value pairs.
     * @throws IOException if the file cannot be read.
     */
    public static List<Map<String, String>> readExcelAsListOfMaps(String filePath, String sheetName) throws IOException {
        List<Map<String, String>> sheetData = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy"); // Or update format as needed

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet '" + sheetName + "' does not exist in file: " + filePath);
            }

            DataFormatter formatter = new DataFormatter();
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalStateException("No header row found in sheet: " + sheetName);
            }

            int colCount = headerRow.getPhysicalNumberOfCells();
            List<String> headers = new ArrayList<>();
            for (int i = 0; i < colCount; i++) {
                headers.add(formatter.formatCellValue(headerRow.getCell(i)));
            }

            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) { // Start from row 1 (skip header)
                Row row = sheet.getRow(i);
                if (row == null) continue; // Skip completely empty rows

                Map<String, String> rowData = new LinkedHashMap<>();
                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j);
                    String cellValue = "";
                    if (cell != null) {
                        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                            Date date = cell.getDateCellValue();
                            cellValue = sdf.format(date);
                        } else {
                            cellValue = formatter.formatCellValue(cell);
                        }
                    }
                    rowData.put(headers.get(j), cellValue);
                }
                sheetData.add(rowData);
            }
        }
        return sheetData;
    }
}
