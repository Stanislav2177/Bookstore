package com.project.bookstore.csv;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.project.bookstore.entity.Book;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;



public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = { "ID", "Title", "Genre","Author", "Price" ,"Description","Quantity" };
    private static final Logger logger = LoggerFactory.getLogger(CSVHelper.class);


    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            logger.error("Invalid file format. Expected: text/csv, Actual: {}", file.getContentType());
            return false;
        }

        return true;
    }

    public static List<Book> csvtoBooks(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Book> tutorials = new ArrayList<Book>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Book tutorial = new Book(
                        Long.parseLong(csvRecord.get("ID")),
                        csvRecord.get("Title"),
                        csvRecord.get("Genre"),
                        csvRecord.get("Author"),
                        Double.parseDouble(csvRecord.get("Price")),
                        csvRecord.get("Description"),
                        Integer.parseInt(csvRecord.get("Quantity"))
                );

                tutorials.add(tutorial);
            }

            return tutorials;
        } catch (IOException e) {
            logger.error("Failed to parse CSV file: {}", e.getMessage());
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

}
