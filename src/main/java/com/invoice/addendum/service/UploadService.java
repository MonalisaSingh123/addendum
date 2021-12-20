package com.invoice.addendum.service;

import com.invoice.addendum.util.UploadUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import technology.tabula.ObjectExtractor;
import technology.tabula.Page;
import technology.tabula.RectangularTextContainer;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;


/*import com.spire.doc.Document;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfDocumentBase;
import com.spire.pdf.PdfPageBase;
import com.spire.pdf.graphics.*;
import com.spire.presentation.Presentation;
import com.spire.xls.Workbook;*/


@Service
public class UploadService {

    private final UploadUtil uploadUtil;

    public UploadService(UploadUtil uploadUtil) {
        this.uploadUtil = uploadUtil;
    }


    public List<Map<String, String>> uploadExcel(MultipartFile file) throws Exception {

        DataFormatter formatter = new DataFormatter();
        Path tempDir = Files.createTempDirectory("");
        File tempFile = tempDir.resolve(file.getOriginalFilename()).toFile();
        file.transferTo(tempFile);

        Workbook workbook = WorkbookFactory.create(tempFile);
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        Sheet sheet = workbook.getSheetAt(0);

        Supplier<Stream<Row>> rowStreamSupplier = uploadUtil.getRowStreamSupplier(sheet);
        Row headerRow = rowStreamSupplier.get().findFirst().get();
        List<String> headerCells = uploadUtil.getStream(headerRow)
                .map(Cell::getStringCellValue)
                .map(String::valueOf)
                .collect(Collectors.toList());

        int colCount = headerCells.size();

        List<Map<String,String>> content = rowStreamSupplier.get()
                .skip(1)
                .map(row -> {
                    List<String> cellList = uploadUtil.getStream(row)
                            .map((cell) -> formatter.formatCellValue(cell,evaluator))
                            .collect(Collectors.toList());
                    return uploadUtil.cellIteratorSupplier(colCount)
                            .get()
                            .collect(toMap(headerCells::get, cellList::get));
                }).collect(Collectors.toList());

        return content;
    }

    public String getPdfCharLocationAndSize(MultipartFile file) throws IOException {
        PDDocument document = null;
        String fileName = "C://Users//Monalisa.Singh//Downloads//Sony-Debit Note - 011-Apr'21 sd.pdf";
        String text = null;
        try {
            document = PDDocument.load(new File(fileName));
            text = extractPdfWordLocations(document);
        }
        finally {
            if( document != null ) {
                document.close();
            }
        }

        return text;
    }

    String extractPdfWordLocations(PDDocument document) throws IOException {

        PDFTextStripper stripper = new PDFTextStripper()
        {
            @Override
            protected void writeString(String text, List<TextPosition> textPositions) throws IOException
            {
                super.writeString(text, textPositions);

                TextPosition firstProsition = textPositions.get(0);
                TextPosition lastPosition = textPositions.get(textPositions.size() - 1);
                writeString(String.format("[%s  %s]", firstProsition.getXDirAdj(), lastPosition.getXDirAdj() + lastPosition.getWidthDirAdj(), firstProsition.getYDirAdj()));
            }
        };
        stripper.setSortByPosition(true);
        stripper.setShouldSeparateByBeads(true);
        return stripper.getText(document);
    }

    public List<Object> mapInvoiceWithExcelInput(MultipartFile invoicePdfInputFile, MultipartFile excelInputFile) throws Exception {

        List<String> pdfInvoiceinput= Collections.singletonList(getPdfCharLocationAndSize(invoicePdfInputFile));
        List<Map<String, String>> excelData = uploadExcel(excelInputFile);
        List<Object> mappedresponse = Stream.concat(pdfInvoiceinput.stream(), excelData.stream()).toList();


        return mappedresponse;
    }


    public List<Object> getfinalInvoicePdfContents(MultipartFile uploadInvoicePdf) throws IOException {
        List<Object> tableContent = new ArrayList<>();
        PDDocument document = null;
        List<technology.tabula.Table> table;
        String fileName = "C://Users//Monalisa.Singh//Downloads//Sony-Debit Note - 011-Apr'21 sd.pdf";

        String text = null;

        try {
            document = PDDocument.load(new File(fileName));
            int totalPages = document.getNumberOfPages();
            System.out.println("Total Pages in Document: "+totalPages);
            ObjectExtractor oe = new ObjectExtractor(document);

            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm(); // Tabula algo.

            Page page = oe.extract(1); // extract only the first page
            // extract text from the table after detecting
            table = sea.extract(page);
            for(technology.tabula.Table tables: table) {
                List<List<RectangularTextContainer>> rows = tables.getRows();

                for(int i=1; i<rows.size(); i++) {

                    List<RectangularTextContainer> cells = rows.get(i);

                    for(int j=1; j<cells.size(); j++) {
                        text = cells.get(j).getText()+"";
                        tableContent.add(text);
                    }

                }
            }

        }
        finally {
            if( document != null ) {
                document.close();
            }
        }

        return tableContent;
    }

    public List<Object> mergePdfWithExcel(MultipartFile invoicePdfInputFile, MultipartFile invoiceExcelInputFile) {
        return null;
    }

}

