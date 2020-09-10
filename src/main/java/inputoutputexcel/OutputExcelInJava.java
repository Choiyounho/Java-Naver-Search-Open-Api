package inputoutputexcel;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileInputStream;
import java.util.Iterator;

public class OutputExcelInJava {

    private static final String EXCEL_FILENAME = "cellDataType.xls";
    private static final String BRACKET = "[";
    private static final String COMMA = ",";
    private static final String STRING_VALUE = "] = STRING; Value=";
    private static final String NUMERIC_VALUE = "] = NUMERIC; Value=";
    private static final String BOOLEAN_VALUE = "] = BOOLEAN; Value=";
    private static final String BLANK_CELL = "] = BLANK CELL";

    public static void main(String[] args) {

        try (FileInputStream fileInputStream = new FileInputStream(EXCEL_FILENAME)) {
            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.rowIterator();

            while (rows.hasNext()) {
                Row row = rows.next();
                Iterator<Cell> cells = row.cellIterator();
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    CellType type = cell.getCellType();
                    if (type == CellType.STRING) {
                        System.out.println(BRACKET + cell.getRowIndex() + COMMA
                                + cell.getColumnIndex() + STRING_VALUE
                                + cell.getRichStringCellValue().toString());
                    } else if (type == CellType.NUMERIC) {
                        System.out.println(BRACKET + cell.getRowIndex() + COMMA
                                + cell.getColumnIndex() + NUMERIC_VALUE
                                + cell.getNumericCellValue());
                    } else if (type == CellType.BOOLEAN) {
                        System.out.println(BRACKET + cell.getRowIndex() + COMMA
                                + cell.getColumnIndex() + BOOLEAN_VALUE
                                + cell.getBooleanCellValue());
                    } else if (type == CellType.BLANK) {
                        System.out.println(BRACKET + cell.getRowIndex() + COMMA
                                + cell.getColumnIndex() + BLANK_CELL);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
    }
}

