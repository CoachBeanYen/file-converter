package team.innovation.converter.elements;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.property.TextAlignment;

import team.innovation.converter.confs.PdfGenerationConfiguration;

/**
 * itext7 PDF header
 * 
 * @author bin.yan
 *
 */
public class Header extends PdfBaseElement implements IEventHandler {

	public Header(PdfGenerationConfiguration configuration) {
		super(configuration);
	}

	public void handleEvent(Event event) {

		PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
		PdfDocument pdf = docEvent.getDocument();
		PdfPage page = docEvent.getPage();
		if (pdf.getPageNumber(page) == 1) {
			Rectangle pageSize = page.getPageSize();
			PdfCanvas pdfCanvas = new PdfCanvas(page.getLastContentStream(), page.getResources(), pdf);
			Canvas canvas = new Canvas(pdfCanvas, pdf, pageSize);
			canvas.setBold();
			canvas.setFontSize(20f);
			canvas.setFont(getConfiguration().getFont());
			canvas.showTextAligned(getConfiguration().getHeaderContent(), pageSize.getWidth() / 2,
					pageSize.getTop() - 30, TextAlignment.CENTER);
			pdfCanvas.release();
			canvas.close();
		}
	}
}
