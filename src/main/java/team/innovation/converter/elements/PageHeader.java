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
 * itext7 PDF page header
 * 
 * @author bin.yan
 *
 */
public class PageHeader extends PdfBaseElement implements IEventHandler {

	public PageHeader(PdfGenerationConfiguration configuration) {
		super(configuration);
	}

	public void handleEvent(Event event) {

		PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
		PdfDocument pdf = docEvent.getDocument();
		PdfPage page = docEvent.getPage();
		Rectangle pageSize = page.getPageSize();
		PdfCanvas pdfCanvas = new PdfCanvas(page.getLastContentStream(), page.getResources(), pdf);
		Canvas canvas = new Canvas(pdfCanvas, pdf, pageSize);
		canvas.setFontSize(12f);
		canvas.setFont(getConfiguration().getFont());
		canvas.showTextAligned(getConfiguration().getPageHeaderContent(), pageSize.getWidth() / 2,
				pageSize.getTop() - 50, TextAlignment.CENTER);
		pdfCanvas.release();
		canvas.close();
	}
}
