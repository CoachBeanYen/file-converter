package team.innovation.converter.elements;

import team.innovation.converter.confs.PdfGenerationConfiguration;

/**
 * PDF element superClass
 * 
 * @author bin.yan
 *
 */
public abstract class PdfBaseElement {

	/**
	 * PDF generation configuration
	 */
	private PdfGenerationConfiguration configuration;

	public PdfBaseElement(PdfGenerationConfiguration configuration) {
		this.setConfiguration(configuration);
	}

	public PdfGenerationConfiguration getConfiguration() {

		return configuration;
	}

	public void setConfiguration(PdfGenerationConfiguration configuration) {

		this.configuration = configuration;
	}
}
