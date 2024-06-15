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

    public static class Person {
        private String imageSrc;
        private String name;
        private String auth;
        private String contact;
        private String bloodGr;
        private String formO;
        private String formA;
        private String vtc;
        private String fileName;

        // Constructors, getters, and setters
        public Person(String imageSrc, String name, String auth, String contact, String bloodGr, String formO, String formA, String vtc, String fileName) {
            this.imageSrc = imageSrc;
            this.name = name;
            this.auth = auth;
            this.contact = contact;
            this.bloodGr = bloodGr;
            this.formO = formO;
            this.formA = formA;
            this.vtc = vtc;
            this.fileName = fileName;
        }

        public String getImageSrc() {
            return imageSrc;
        }

        public String getName() {
            return name;
        }

        public String getAuth() {
            return auth;
        }

        public String getContact() {
            return contact;
        }

        public String getBloodGr() {
            return bloodGr;
        }

        public String getFormO() {
            return formO;
        }

        public String getFormA() {
            return formA;
        }

        public String getVtc() {
            return vtc;
        }

        public String getFileName() {
            return fileName;
        }
    }

    public void convert() {
        List<Person> people = readExcelFile(excelFilePath);
        generateHtmlFiles(people, htmlTemplatePath);
    }

    private List<Person> readExcelFile(String filePath) {
        List<Person> people = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // Skip the header row
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                String imageSrc = getCellValueAsString(row.getCell(0));
                String name = getCellValueAsString(row.getCell(1));
                String auth = getCellValueAsString(row.getCell(2));
                String contact = getCellValueAsString(row.getCell(3));
                String bloodGr = getCellValueAsString(row.getCell(4));
                String formO = getCellValueAsString(row.getCell(5));
                String formA = getCellValueAsString(row.getCell(6));
                String vtc = getCellValueAsString(row.getCell(7));
                String fileName = getCellValueAsString(row.getCell(8));

                people.add(new Person(imageSrc, name, auth, contact, bloodGr, formO, formA, vtc, fileName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return people;
    }

    private String getCellValueAsString(Cell cell) {
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
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }

    private void generateHtmlFiles(List<Person> people, String templateFileName) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        // Define the output directory
        int i = 1;

        for (Person person : people) {
            Context context = new Context();
            context.setVariable("person", person);

            String outputFileName = outputDir + person.getFileName();
            try (FileWriter writer = new FileWriter(outputFileName)) {
                templateEngine.process(templateFileName, context, writer);
                System.out.println("******** " + i++ + " Created HTML file " + person.getFileName() + " successfully *************");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
