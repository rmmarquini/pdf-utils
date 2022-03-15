package com.rmmarquini;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MergePDF {

    public static void main( String[] args ) {

        try {

            List<InputStream> inputPdfList = new ArrayList<>();
            inputPdfList.add(new FileInputStream("D:\\Downloads\\Merged-PDF\\001.pdf"));
            inputPdfList.add(new FileInputStream("D:\\Downloads\\Merged-PDF\\002.pdf"));
            inputPdfList.add(new FileInputStream("D:\\Downloads\\Merged-PDF\\003.pdf"));
            inputPdfList.add(new FileInputStream("D:\\Downloads\\Merged-PDF\\004.pdf"));
            inputPdfList.add(new FileInputStream("D:\\Downloads\\Merged-PDF\\005.pdf"));
            inputPdfList.add(new FileInputStream("D:\\Downloads\\Merged-PDF\\006.pdf"));
            inputPdfList.add(new FileInputStream("D:\\Downloads\\Merged-PDF\\007.pdf"));
            inputPdfList.add(new FileInputStream("D:\\Downloads\\Merged-PDF\\008.pdf"));
            inputPdfList.add(new FileInputStream("D:\\Downloads\\Merged-PDF\\009.pdf"));
            inputPdfList.add(new FileInputStream("D:\\Downloads\\Merged-PDF\\010.pdf"));
            inputPdfList.add(new FileInputStream("D:\\Downloads\\Merged-PDF\\011.pdf"));
            inputPdfList.add(new FileInputStream("D:\\Downloads\\Merged-PDF\\012.pdf"));
            inputPdfList.add(new FileInputStream("D:\\Downloads\\Merged-PDF\\013.pdf"));
            inputPdfList.add(new FileInputStream("D:\\Downloads\\Merged-PDF\\014.pdf"));

            OutputStream outputStream = new FileOutputStream("D:\\Downloads\\Merged-PDF\\merged.pdf");

            mergePdfFiles(inputPdfList, outputStream);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unused")
    private static void mergePdfFiles(List<InputStream> inputPdfFiles, OutputStream mergedFile) throws IOException, DocumentException {

        Document document = new Document();
        List<PdfReader> readers = new ArrayList<>();

        int totalPages = 0;

        for (InputStream pdf : inputPdfFiles) {
            System.out.println("Reading file...\r\n ");
            PdfReader pdfReader = new PdfReader(pdf);
            readers.add(pdfReader);
            totalPages += pdfReader.getNumberOfPages();
        }

        PdfWriter writer = PdfWriter.getInstance(document, mergedFile);

        document.open();

        // Keeps the content pdf bytes
        PdfContentByte pageContentByte = writer.getDirectContent();

        PdfImportedPage pdfImportedPage;
        int curPdfReaderPage = 1;

        for (PdfReader pdfReader : readers) {
            while (curPdfReaderPage <= pdfReader.getNumberOfPages()) {
                document.newPage();
                pdfImportedPage = writer.getImportedPage(pdfReader, curPdfReaderPage);
                pageContentByte.addTemplate(pdfImportedPage, 0, 0);
                curPdfReaderPage++;
            }
            curPdfReaderPage = 1;
        }

        mergedFile.flush();
        document.close();
        mergedFile.close();

        System.out.println("The pdf files were successfully merged!");

    }

}
