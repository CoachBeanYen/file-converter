package team.innovation.converter.utils;

import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfDocument;

import team.innovation.converter.confs.PdfGenerationConfiguration;
import team.innovation.converter.elements.*;

/**
 * PDF generator interface
 * 
 * @author bin.yan
 *
 */
interface PdfGenerator {

	/**
	 * add event listeners,like PageXofY,PageHeader,Header,Footer
	 * 
	 * @param configuration
	 * @param pdfDoc
	 */
	default void addEventListeners(PdfGenerationConfiguration configuration, PdfDocument pdfDoc) {

		if (configuration.isPagingOrNot()) {
			PageXofY pagination = new PageXofY(configuration);
			pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, pagination);
		}
		if (StringUtils.isNotBlank(configuration.getPageHeaderContent())) {
			PageHeader pageHeader = new PageHeader(configuration);
			pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, pageHeader);
		}
		if (StringUtils.isNotBlank(configuration.getPageFooterContent())) {
			PageFooter pageFooter = new PageFooter(configuration);
			pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, pageFooter);
		}
		if (StringUtils.isNotBlank(configuration.getHeaderContent())) {
			Header header = new Header(configuration);
			pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, header);
		}
		if (StringUtils.isNotBlank(configuration.getFooterContent())) {
			Footer footer = new Footer(configuration);
			pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, footer);
		}
	}
}
