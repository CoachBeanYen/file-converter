package team.innovation.converter.utils;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.layout.splitting.DefaultSplitCharacters;

import team.innovation.converter.confs.ExcelToPdfConfiguration;

/**
 * EXCEL to PDF converter
 *
 * @author bin.yan
 */
public class ExcelToPdfConverter implements PdfGenerator, FileConverter {

    private ExcelToPdfConfiguration configuration;

    ExcelToPdfConverter(ExcelToPdfConfiguration configuration) {
        if (configuration == null)
            throw new RuntimeException("configuration cannot be null");
        this.configuration = configuration;
    }

    /**
     * read excel by POI AND convert to PDF
     *
     * @param excelInput
     * @param pdfOut
     * @throws Exception
     */
    public void convert(InputStream excelInput, OutputStream pdfOut) throws IOException {

        configuration.selfCheck();
        PdfWriter writer = new PdfWriter(pdfOut);
        PdfDocument pdfDoc = new PdfDocument(writer);
        PdfFont font = configuration.getFont();
        addEventListeners(configuration, pdfDoc);
        Document document = new Document(pdfDoc,
                configuration.isRotateOrNot() ? configuration.getPageSize().rotate() : configuration.getPageSize());
        Workbook workbook = WorkbookFactory.create(excelInput);
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            if (sheet != null) {
                Table table = initTable(sheet);
                if (StringUtils.isNotBlank(configuration.getHeaderContent()))
                    document.setTopMargin(60);
                if (table != null) {
                    assembleTable(sheet, table, font);
                    document.add(table);
                }
            }
        }
        document.close();
    }

    /**
     * Assemble table
     *
     * @param sheet
     * @param table
     * @param font
     */
    private void assembleTable(Sheet sheet, Table table, PdfFont font) {

        com.itextpdf.layout.element.Cell pdfCell;
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
        List<CellRangeAddress> usedCellRangeAddress = new ArrayList<CellRangeAddress>();
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null)
                continue;
            for (int j = 0; j < table.getNumberOfColumns(); j++) {
                Cell cell = row.getCell(j);
                float height = 0;
                if (cell == null) {// fill blank cell
                    for (CellRangeAddress mr : usedCellRangeAddress) {
		        if (mr.containsRow(i) && mr.containsColumn(j)) {
		            inRange = true;
			    break;
			}
		    }
		    if (inRange)// skip cell which in used cell range address
		        continue;
		    com.itextpdf.layout.element.Cell blankCell = new com.itextpdf.layout.element.Cell();
		    if (!configuration.isFillBlankWithBorder())
			blankCell.setBorder(Border.NO_BORDER);
		    blankCell.setHeight(row.getHeightInPoints());
		    table.addCell(blankCell);
		    continue;
                }
                boolean inRange = false;
                CellRangeAddress address = null;
                for (CellRangeAddress mr : mergedRegions) {
                    address = mr;
                    inRange = mr.isInRange(cell);
                    if (inRange)
                        break;
                }
                if (inRange) {
                    if (!usedCellRangeAddress.contains(address)) {
                        usedCellRangeAddress.add(address);
                        pdfCell = new com.itextpdf.layout.element.Cell(address.getLastRow() - address.getFirstRow() + 1,
                                address.getLastColumn() - address.getFirstColumn() + 1);
                        if (address.getFirstRow() != address.getLastRow()) {// if need merge cell,should set row height
                            for (int k = address.getFirstRow(); k < address.getLastRow(); k++) {
                                height += sheet.getRow(k).getHeightInPoints();
                            }
                            pdfCell.setHeight(height);
                        }
                    } else
                        continue;
                } else {
                    pdfCell = new com.itextpdf.layout.element.Cell();// cell without mergeAddress
                }
                Paragraph phrase = new Paragraph(
                        cell.getCellType().equals(CellType.NUMERIC) ? String.valueOf(cell.getNumericCellValue())
                                : cell.getStringCellValue());
                phrase.setFont(font);
                phrase.setSplitCharacters(new DefaultSplitCharacters());
                pdfCell.add(phrase);
                pdfCell.setFont(font);
                copyCellStyle(sheet.getWorkbook(), pdfCell, cell);
                table.addCell(pdfCell);
            }
        }
    }

    /**
     * copy excel cell style to pdf cell
     *
     * @param workbook
     * @param pdfCell
     * @param cell
     */
    private void copyCellStyle(Workbook workbook, com.itextpdf.layout.element.Cell pdfCell, Cell cell) {

        CellStyle excelCellStyle = cell.getCellStyle();
        switch (excelCellStyle.getAlignment()) {
            case LEFT:
                pdfCell.setTextAlignment(TextAlignment.LEFT);
                break;
            case CENTER:
                pdfCell.setTextAlignment(TextAlignment.CENTER);
                break;
            case RIGHT:
                pdfCell.setTextAlignment(TextAlignment.RIGHT);
                break;
            default:
                break;
        }
        switch (excelCellStyle.getVerticalAlignment()) {
            case TOP:
                pdfCell.setVerticalAlignment(VerticalAlignment.TOP);
                break;
            case CENTER:
                pdfCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                break;
            case BOTTOM:
                pdfCell.setVerticalAlignment(VerticalAlignment.BOTTOM);
                break;
            default:
                break;
        }
        int fontIndex = excelCellStyle.getFontIndexAsInt();
        Font font = workbook.getFontAt(fontIndex);
        if (font.getBold())
            pdfCell.setBold();
        if (font.getItalic())
            pdfCell.setItalic();
        pdfCell.setFontSize(font.getFontHeightInPoints());
        if (workbook instanceof XSSFWorkbook) {
            Optional.ofNullable(((XSSFFont) font).getXSSFColor()).map(c -> c.getARGBHex())
                    .ifPresent(copyXSSFFontColor(pdfCell));
            Optional.ofNullable((XSSFColor) excelCellStyle.getFillForegroundColorColor()).map(c -> c.getARGBHex())
                    .ifPresent(copyXSSFBackgroundColor(pdfCell));
        } else {
            Optional.ofNullable(((HSSFFont) font).getHSSFColor((HSSFWorkbook) workbook)).map(c -> c.getTriplet())
                    .ifPresent(copyHSSFFontColor(pdfCell));
            Optional.ofNullable((HSSFColor) excelCellStyle.getFillForegroundColorColor()).map(c -> c.getTriplet())
                    .ifPresent(copyHSSFBackgroundColor(pdfCell));
        }
    }

    /**
     * copy HSSFFont color to pdf cell
     *
     * @param pdfCell
     * @return
     */
    private Consumer<? super short[]> copyHSSFFontColor(com.itextpdf.layout.element.Cell pdfCell) {
        return rgb -> {
            if (rgb[0] != 0 || rgb[1] != 0 || rgb[2] != 0) {// 不处理HssfWorkbook中没有设置背景色单元格
                pdfCell.setFontColor(new DeviceRgb(rgb[0], rgb[1], rgb[2]));
            }
        };
    }

    /**
     * copy HSSFBackground color to pdf cell
     *
     * @param pdfCell
     * @return
     */
    private Consumer<? super short[]> copyHSSFBackgroundColor(com.itextpdf.layout.element.Cell pdfCell) {
        return rgb -> {
            if (rgb[0] != 0 || rgb[1] != 0 || rgb[2] != 0) {// 不处理HssfWorkbook中没有设置背景色单元格
                pdfCell.setBackgroundColor(new DeviceRgb(rgb[0], rgb[1], rgb[2]));
            }
        };
    }

    /**
     * copy XSSFFont color to pdf cell
     *
     * @param pdfCell
     * @return
     */
    private Consumer<? super String> copyXSSFFontColor(com.itextpdf.layout.element.Cell pdfCell) {
        return rgbHex -> {
            pdfCell.setFontColor(new DeviceRgb(Color.decode("#" + rgbHex.substring(rgbHex.length() - 6))));
        };
    }

    /**
     * copy XSSFBackground color to pdf cell
     *
     * @param pdfCell
     * @return
     */
    private Consumer<? super String> copyXSSFBackgroundColor(com.itextpdf.layout.element.Cell pdfCell) {
        return rgbHex -> {
            pdfCell.setBackgroundColor(new DeviceRgb(Color.decode("#" + rgbHex.substring(rgbHex.length() - 6))));
        };
    }

    /**
     * init table
     *
     * <pre>
     * Because POI itself does not read blank spaces, but ITEXT merge needs to fill blank spaces,
     * so the maximum number of columns in the worksheet is needed to determine the number and width of the columns in the table.
     * </pre>
     *
     * @param sheet
     * @return
     */
    private Table initTable(Sheet sheet) {

        Table table = null;
        Row maxColumnRow = null;
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            if (sheet.getRow(i) != null && sheet.getRow(i).getLastCellNum() > 0) {
                if (maxColumnRow == null)
                    maxColumnRow = sheet.getRow(i);
                else
                    maxColumnRow = sheet.getRow(i).getLastCellNum() >= maxColumnRow.getLastCellNum() ? sheet.getRow(i)
                            : maxColumnRow;
            }
        }
        if (maxColumnRow != null) {
            int maxColumn = maxColumnRow.getLastCellNum();
            float[] columnWidths = new float[maxColumn];
            for (int i = 0; i < maxColumn; i++) {
                columnWidths[i] = sheet.getColumnWidthInPixels(i);
            }
            table = new Table(columnWidths);
            // table.setHorizontalAlignment(HorizontalAlignment.CENTER);
            // table.setWidth(UnitValue.createPercentValue(100));
        }
        return table;
    }
}
