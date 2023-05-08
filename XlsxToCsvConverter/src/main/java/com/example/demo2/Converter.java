package com.example.demo2;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import javax.servlet.http.Part;

public class Converter {
    public static File convert(File inputFile) {
        // Convert XLSX file to CSV
        try {
            String outputFilename = System.getProperty("user.home") + "/Downloads/" + inputFile.getName().replace(".xlsx", ".csv");
            Workbook workbook = WorkbookFactory.create(inputFile);
            Sheet sheet = workbook.getSheetAt(0);
            FileWriter fw = new FileWriter(outputFilename);
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        Cell cell = row.getCell(j);
                        if (cell != null) {
                            switch (cell.getCellType()) {
                                case Cell.CELL_TYPE_STRING:
                                    fw.write(cell.getStringCellValue());
                                    break;
                                case Cell.CELL_TYPE_NUMERIC:
                                    if (DateUtil.isCellDateFormatted(cell)) {
                                        fw.write(cell.getDateCellValue().toString());
                                    } else {
                                        fw.write(Double.toString(cell.getNumericCellValue()));
                                    }
                                    break;
                                case Cell.CELL_TYPE_BOOLEAN:
                                    fw.write(Boolean.toString(cell.getBooleanCellValue()));
                                    break;
                                case Cell.CELL_TYPE_FORMULA:
                                    switch (cell.getCachedFormulaResultType()) {
                                        case Cell.CELL_TYPE_NUMERIC:
                                            fw.write(Double.toString(cell.getNumericCellValue()));
                                            break;
                                        case Cell.CELL_TYPE_STRING:
                                            fw.write(cell.getRichStringCellValue().getString());
                                            break;
                                    }
                                    break;
                                default:
                                    fw.write("");
                                    break;
                            }
                        }
                        if (j < row.getLastCellNum() - 1) {
                            fw.write(",");
                        }
                    }
                    fw.write("\n");
                }
            }
            fw.flush();
            fw.close();
            System.out.println("Conversion successful. Output file saved to: " + outputFilename);
            return new File(outputFilename);
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}