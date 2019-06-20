package team.innovation.converter.demo;

import com.itextpdf.kernel.geom.PageSize;
import team.innovation.converter.confs.ExcelToPdfConfiguration;
import team.innovation.converter.utils.ExcelToPdfConverter;
import team.innovation.converter.utils.PdfConverterFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ConverterDemo {

    public static void main(String[] args) throws Exception {

        File inputFile = new File("C:\\Users\\ALIENWARE\\Desktop\\2.xlsx");
        FileInputStream in = new FileInputStream(inputFile);
        File outPutFile = new File("C:\\Users\\ALIENWARE\\Desktop\\1.pdf");
        FileOutputStream os = new FileOutputStream(outPutFile);
        final ExcelToPdfConfiguration excelToPdfConfiguration = new ExcelToPdfConfiguration(PageSize.A4, true);
        ExcelToPdfConverter excelToPdfConverter = PdfConverterFactory.INSTANCE.excelToPdfConverter(excelToPdfConfiguration);
        excelToPdfConverter.convert(in, os);
    }
}
