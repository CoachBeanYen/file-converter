package team.innovation.converter.elements;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import team.innovation.converter.confs.PdfGenerationConfiguration;

/**
 * itext7 PDF page x of y
 * 
 * @author bin.yan
 *
 */
public class PageXofY extends PdfBaseElement implements IEventHandler {

	public PageXofY(PdfGenerationConfiguration configuration) {
		super(configuration);
	}

	public void handleEvent(Event event) {

		PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
		PdfDocument pdf = docEvent.getDocument();
		PdfPage page = docEvent.getPage();
		int pageNumber = pdf.getPageNumber(page);
		Rectangle pageSize = page.getPageSize();
		PdfCanvas pdfCanvas = new PdfCanvas(page.getLastContentStream(), page.getResources(), pdf);
		Canvas canvas = new Canvas(pdfCanvas, pdf, pageSize);
		Paragraph p = new Paragraph().add("第 ").add(String.valueOf(pageNumber)).add(" 页");
		p.setFont(getConfiguration().getFont());
		// .add(" of ").add(String.valueOf(pdf.getNumberOfPages()))
		float pageWidth = page.getPageSize().getWidth();
		canvas.showTextAligned(p, pageWidth - (pageWidth / 10), 20, TextAlignment.RIGHT);
		pdfCanvas.release();
		canvas.close();
	}

}
