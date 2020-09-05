package inputoutputexcel;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileInputStream;
import java.util.Iterator;

public class OutputExcelInJava {
    public static final String EXCEL_FILENAME = "cellDataType.xls";
    public static final String BRACKET = "[";
    public static final String COMMA = ",";
    public static final String STRING_VALUE = "] = STRING; Value=";
    public static final String NUMERIC_VALUE = "] = NUMERIC; Value=";
    public static final String BOOLEAN_VALUE = "] = BOOLEAN; Value=";
    public static final String BLANK_CELL = "] = BLANK CELL";

    public static void main(String[] args) {

        try (FileInputStream fis = new FileInputStream(EXCEL_FILENAME)) {
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
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
            e.printStackTrace();
        }
    }
}

