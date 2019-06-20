package team.innovation.converter.confs;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.PageSize;

/**
 * itext7 PDF Generation Configuration
 * 
 * @author bin.yan
 *
 */
public abstract class PdfGenerationConfiguration {

	/**
	 * page size
	 */
	private PageSize pageSize;

	/**
	 * rotate or not
	 */
	private boolean rotateOrNot = false;

	/**
	 * font,default font is (STSong-Light)
	 */
	private PdfFont font;

	/**
	 * paging or not
	 */
	private boolean pagingOrNot = false;

	/**
	 * page header content
	 */
	private String pageHeaderContent;

	/**
	 * page footer content
	 */
	private String pageFooterContent;

	/**
	 * header content
	 */
	private String headerContent;

	/**
	 * footer content
	 */
	private String footerContent;


	/**
	 * check the necessary parameters
	 * 
	 */
	public void selfCheck() {

		if (this.pageSize == null)
			throw new RuntimeException("not assign page size!");
		if (this.font == null)
			throw new RuntimeException("not assign font!");
	}

	public PageSize getPageSize() {

		return pageSize;
	}

	public void setPageSize(PageSize pageSize) {

		this.pageSize = pageSize;
	}

	public boolean isRotateOrNot() {

		return rotateOrNot;
	}

	public void setRotateOrNot(boolean rotateOrNot) {

		this.rotateOrNot = rotateOrNot;
	}

	public PdfFont getFont() {

		return font;
	}

	public void setFont(PdfFont font) {

		this.font = font;
	}

	public boolean isPagingOrNot() {

		return pagingOrNot;
	}

	public void setPagingOrNot(boolean pagingOrNot) {

		this.pagingOrNot = pagingOrNot;
	}

	public String getPageHeaderContent() {

		return pageHeaderContent;
	}

	public void setPageHeaderContent(String pageHeaderContent) {

		this.pageHeaderContent = pageHeaderContent;
	}

	public String getPageFooterContent() {

		return pageFooterContent;
	}

	public void setPageFooterContent(String pageFooterContent) {

		this.pageFooterContent = pageFooterContent;
	}

	public String getHeaderContent() {

		return headerContent;
	}

	public void setHeaderContent(String headerContent) {

		this.headerContent = headerContent;
	}

	public String getFooterContent() {

		return footerContent;
	}

	public void setFooterContent(String footerContent) {

		this.footerContent = footerContent;
	}

}
