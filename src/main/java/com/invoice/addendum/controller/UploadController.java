package com.invoice.addendum.controller;

import com.invoice.addendum.service.ExcelPopUpWindow;
import com.invoice.addendum.service.UploadService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.MultipartConfigElement;
import java.util.List;
import java.util.Map;


@RestController
public class UploadController {

    private final UploadService uploadService;

    private final ExcelPopUpWindow excelPopUpWindow;

    public UploadController(UploadService uploadService, ExcelPopUpWindow excelPopUpWindow) {
        this.uploadService = uploadService;
        this.excelPopUpWindow = excelPopUpWindow;
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        return new MultipartConfigElement("");
    }

    @Bean
    public MultipartResolver multipartResolver() {
        org.springframework.web.multipart.commons.CommonsMultipartResolver multipartResolver = new org.springframework.web.multipart.commons.CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(1000000);
        return new CommonsMultipartResolver();
    }

    //mapping D365 invoice input with Invoice input excel sheet
    @PostMapping(value = "/D365InvoiceGenerator", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public List<Object> mappingInvoiceInputWithInputExcel(@RequestParam("D356InvoicePDFFile") MultipartFile invoicePdfInputFile, @RequestParam("InvoiceExcelInputFile") MultipartFile excelInputFile) throws Exception{
        return uploadService.mapInvoiceWithExcelInput(invoicePdfInputFile,excelInputFile);
    }

    //upload excel file and convert into json
    @PostMapping(value = "/uploadExcelInput", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public List<Map<String, String>> uploadExcelInvoice(@RequestParam("file") MultipartFile file) throws Exception{
        return uploadService.uploadExcel(file);
    }

    //x and y coordinate of pdf data
    @PostMapping(value = "/uploadInvoicePdf", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public String findPdfCoordinate(@RequestParam("uploadInvoicePdf") MultipartFile uploadInvoicePdf) throws Exception{
        return uploadService.getPdfCharLocationAndSize(uploadInvoicePdf);
    }


    @PostMapping(value = "/finalInvoicePdfContents", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public List<Object> getfinalInvoicePdfContents(@RequestParam("finalInvoicePdfContents") MultipartFile uploadInvoicePdf) throws Exception{
        return uploadService.getfinalInvoicePdfContents(uploadInvoicePdf);
    }

    @PostMapping(value = "/mergePdfWithExcel", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public List<Object> mergePdfWithExcel(@RequestParam("D356InvoicePDFFile") MultipartFile invoicePdfInputFile, @RequestParam("InvoiceExcelInputFile") MultipartFile InvoiceExcelInputFile) throws Exception{
        return uploadService.mergePdfWithExcel(invoicePdfInputFile,InvoiceExcelInputFile);
    }

    @PostMapping(value = "/displayPopUpUI")
    public void displayPopUpUI(){
        excelPopUpWindow.displayUI();
    }

}