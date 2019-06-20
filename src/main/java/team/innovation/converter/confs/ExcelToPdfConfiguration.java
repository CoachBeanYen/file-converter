package team.innovation.converter.confs;

import java.io.IOException;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;

/**
 * itext7 EXCEL TO PDF Configuration
 * 
 * @author bin.yan
 *
 */
public class ExcelToPdfConfiguration extends PdfGenerationConfiguration {

	/**
	 * fill blank with border
	 */
	private boolean fillBlankWithBorder = false;

	public ExcelToPdfConfiguration(PageSize pageSize, boolean pagingOrNot) {
		super();
		this.setPageSize(pageSize);
		try {
			this.setFont(PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true));
		} catch (IOException e) {
			throw new RuntimeException("cannot find default font");
		}
		this.setPagingOrNot(pagingOrNot);
	}

	public ExcelToPdfConfiguration(PageSize pageSize, boolean rotateOrNot, boolean pagingOrNot) {
		super();
		this.setPageSize(pageSize);
		this.setRotateOrNot(rotateOrNot);
		try {
			this.setFont(PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true));
		} catch (IOException e) {
			throw new RuntimeException("cannot find default font");
		}
		this.setPagingOrNot(pagingOrNot);
	}

	public ExcelToPdfConfiguration(PageSize pageSize, boolean rotateOrNot, PdfFont font, boolean pagingOrNot) {
		super();
		this.setPageSize(pageSize);
		this.setRotateOrNot(rotateOrNot);
		this.setFont(font);
		this.setPagingOrNot(pagingOrNot);
	}

	public ExcelToPdfConfiguration(PageSize pageSize, boolean rotateOrNot, PdfFont font, boolean pagingOrNot,
			String headerContent, String footerContent) {
		super();
		this.setPageSize(pageSize);
		this.setRotateOrNot(rotateOrNot);
		this.setFont(font);
		this.setPagingOrNot(pagingOrNot);
		this.setHeaderContent(headerContent);
		this.setFooterContent(footerContent);
	}

	public ExcelToPdfConfiguration(PageSize pageSize, boolean rotateOrNot, PdfFont font, boolean pagingOrNot,
			String pageHeaderContent, String pageFooterContent, String headerContent, String footerContent) {
		super();
		this.setPageSize(pageSize);
		this.setRotateOrNot(rotateOrNot);
		this.setFont(font);
		this.setPagingOrNot(pagingOrNot);
		this.setHeaderContent(headerContent);
		this.setFooterContent(footerContent);
		this.setPageHeaderContent(pageHeaderContent);
		this.setPageFooterContent(pageFooterContent);
	}

	public boolean isFillBlankWithBorder() {

		return fillBlankWithBorder;
	}

	public void setFillBlankWithBorder(boolean fillBlankWithBorder) {

		this.fillBlankWithBorder = fillBlankWithBorder;
	}
}
