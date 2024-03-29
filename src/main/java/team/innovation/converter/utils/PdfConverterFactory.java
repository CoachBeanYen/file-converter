package team.innovation.converter.utils;

import team.innovation.converter.confs.ExcelToPdfConfiguration;

/**
 * itext7 PDF converter factory
 * 
 * <pre>
 * example:
 * 	<code>FileInputStream excelStream = new FileInputStream(inputFile);
 *	FileOutputStream out = new FileOutputStream(outPutFile);
 *	ExcelToPdfConfiguration configuration = new ExcelToPdfConfiguration(PageSize.A4, false, true);
 *	ExcelToPdfConverter excelToPdfConverter = PdfConverterFactory.INSTANCE.excelToPdfConverter(configuration);
 *	excelToPdfConverter.excelToPdf(excelStream, out);</code>
 * </pre>
 * 
 * @author bin.yan
 *
 */
public enum PdfConverterFactory {

	INSTANCE;

	/**
	 * get ExcelToPdfConverter instance
	 * 
	 * 
	 * @param configuration
	 * @return
	 */
	public ExcelToPdfConverter excelToPdfConverter(ExcelToPdfConfiguration configuration) {

		return new ExcelToPdfConverter(configuration);
	}

}
