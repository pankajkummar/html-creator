package com.pankaj.html_creator;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelToHtmlConverter {

    @Value("${excel.file.path}")
    String excelFilePath;
    @Value("${dest.folder.path}")
    String outputDir;
    @Value("${html.template.path}")
    String htmlTemplatePath;



    public void convert() {
        List<GenericClass> people = readExcelFile(excelFilePath);
        generateHtmlFiles(people, htmlTemplatePath);
    }

    private List<GenericClass> readExcelFile(String filePath) {
        List<GenericClass> people = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // Skip the header row
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                String name = getCellValueAsString(row.getCell(1), evaluator);
                String designation = getCellValueAsString(row.getCell(2), evaluator);
                String id = getCellValueAsString(row.getCell(3),evaluator); //vtc
                String authorization = getCellValueAsString(row.getCell(4), evaluator);
                String fromA = getCellValueAsString(row.getCell(5), evaluator); //form A
                String ime = getCellValueAsString(row.getCell(6), evaluator); //IME
                String dob = getCellValueAsString(row.getCell(7), evaluator); //PME
                String doj = getCellValueAsString(row.getCell(8), evaluator); //SOP SL NO
                String sop = getCellValueAsString(row.getCell(9), evaluator);
                String vtc = getCellValueAsString(row.getCell(10), evaluator);
               // String imageName = "Images/" + getCellValueAsString(row.getCell(2), evaluator).toLowerCase().replace(" ","")+".jpeg";
                String imageName = "Images/dummy.jpeg";

                // String imageName = getCellValueAsString(row.getCell(21), evaluator).replace(" ","");

                String fileName = id + ".html";  // Append ".html" to the id for the file name

                people.add(new GenericClass(imageName,fileName,name,id,designation,authorization,fromA,ime,dob,doj,sop,vtc));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return people;
    }

    private String getCellValueAsString(Cell cell, FormulaEvaluator evaluator) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((int) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                CellValue cellValue = evaluator.evaluate(cell);
                switch (cellValue.getCellType()) {
                    case STRING:
                        return cellValue.getStringValue();
                    case NUMERIC:
                        return String.valueOf((int) cellValue.getNumberValue());
                    case BOOLEAN:
                        return String.valueOf(cellValue.getBooleanValue());
                    case ERROR:
                        return FormulaError.forInt(cellValue.getErrorValue()).getString();
                    default:
                        return "";
                }
            case BLANK:
                return "";
            default:
                return "";
        }
    }

    private void generateHtmlFiles(List<GenericClass> people, String templateFileName) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        int i = 0;

        for (GenericClass person : people) {
            Context context = new Context();
            context.setVariable("person", person);

            String outputFileName = outputDir + person.getFileName();
            try (FileWriter writer = new FileWriter(outputFileName)) {
                templateEngine.process(templateFileName, context, writer);
                System.out.println(i++ + "Created HTML file " + person.getFileName() + " successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
