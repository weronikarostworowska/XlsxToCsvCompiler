package com.example.demo2;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import static com.example.demo2.Converter.convert;

@WebServlet(name = "FileUploadServlet", urlPatterns = { "/upload" })
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class FileUploadServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIR = "uploads";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the file part from the request
        Part filePart = request.getPart("file");

        // Get the input stream from the file part
        InputStream inputStream = filePart.getInputStream();


        // Create a temporary file
        File inputFile = File.createTempFile("output", ".xlsx");
        String outputFileName = inputFile.getName().replaceAll("\\.xlsx$", "") + ".csv";

        // Write the input stream to the temporary file
        try (FileOutputStream outputStream = new FileOutputStream(inputFile)) {
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        // Convert the file to CSV
        File outputFile = convert(inputFile);

        // Set the content type and headers for the response
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + outputFileName + "\"");

        // Copy the converted file to the response output stream
        try (InputStream fileInputStream = new FileInputStream(outputFile)) {
            OutputStream outputStream = response.getOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        // Delete the temporary input and output files
        inputFile.delete();
        outputFile.delete();
    }

}
