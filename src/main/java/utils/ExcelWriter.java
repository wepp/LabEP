package tools;

import com.sun.org.glassfish.external.statistics.Statistic;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ExcelWriter {

    private static String[] columns = {"NSeeds", "NP", "GP", "LP", "PR", "GPR", "LPR", "FPR"};

    public ExcelWriter(Statistics statistics, int numberOfIteration) throws IOException, InvalidFormatException {

        // Create a Workbook
        //Workbook workbook = new XSSFWorkbook();     // new HSSFWorkbook() for generating `.xls` file

        Workbook workbook = WorkbookFactory.create(new File("tables/012_temp.xlsx"));


        /* CreationHelper helps us create instances for various things like DataFormat,
           Hyperlink, RichTextString etc in a format (HSSF, XSSF) independent way */
        CreationHelper createHelper = workbook.getCreationHelper();

        // Create a Sheet
        //Sheet sheet = workbook.createSheet("deba1");

        Sheet sheet;
        try {
            sheet = workbook.getSheetAt(0);
        }
        catch (IllegalArgumentException e){
            sheet = workbook.createSheet("deba1");
        }

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        //Row headerRow = sheet.createRow(0);

        Row headerRow1 = null;
        Row headerRow2 = null;

        headerRow1 = sheet.getRow(0);
        if(headerRow1 == null)
            headerRow1 = sheet.createRow(0);

        headerRow2 = sheet.getRow(1);
        if(headerRow2 == null)
            headerRow2 = sheet.createRow(1);

        headerRow1.createCell(numberOfIteration*8).setCellValue(String.format("Прогін %d", numberOfIteration));

        // Creating cells
        for(int i = numberOfIteration*8 + 0, j = 0; j < columns.length; i++, j++) {
            Cell cell = headerRow2.createCell(i);
            cell.setCellValue(columns[j]);
            cell.setCellStyle(headerCellStyle);
        }

        // Create Other rows and cells with employees data
        int rowNum = 2;

        Row row = sheet.getRow(rowNum);
        if(row == null){
            row = sheet.createRow(rowNum);
        }

        row.createCell(numberOfIteration*8 + 0).setCellValue(statistics.getNseeds());
        row.createCell(numberOfIteration*8 + 1).setCellValue(statistics.getNp());
        row.createCell(numberOfIteration*8 + 2).setCellValue(statistics.getGp());
        row.createCell(numberOfIteration*8 + 3).setCellValue(statistics.getLp());
        row.createCell(numberOfIteration*8 + 4).setCellValue(statistics.getPr());
        row.createCell(numberOfIteration*8 + 5).setCellValue(statistics.getGpr());
        row.createCell(numberOfIteration*8 + 6).setCellValue(statistics.getLpr());
        row.createCell(numberOfIteration*8 + 7).setCellValue(statistics.getFpr());


        // Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        System.out.println("DONE 1");

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("tables/012.xlsx");
        workbook.write(fileOut);
        fileOut.close();
        fileOut.flush();

        workbook.close();

        System.out.println("DONE 2");
    }


    // Example to modify an existing excel file
    private static void modifyExistingWorkbook() throws InvalidFormatException, IOException {
        // Obtain a workbook from the excel file
        Workbook workbook = WorkbookFactory.create(new File("existing-spreadsheet.xlsx"));

        // Get Sheet at index 0
        Sheet sheet = workbook.getSheetAt(0);

        // Get Row at index 1
        Row row = sheet.getRow(1);

        // Get the Cell at index 2 from the above row
        Cell cell = row.getCell(2);

        // Create the cell if it doesn't exist
        if (cell == null)
            cell = row.createCell(2);

        // Update the cell's value
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Updated Value");

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("existing-spreadsheet.xlsx");
        workbook.write(fileOut);
        fileOut.close();

        // Closing the workbook
        workbook.close();
    }
}
