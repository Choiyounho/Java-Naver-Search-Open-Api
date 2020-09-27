package outputexcel.view;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class OutputView {

    private static final String BRACKET = "[";
    private static final String COMMA = ",";
    private static final String STRING_VALUE = "] = STRING; Value=";
    private static final String NUMERIC_VALUE = "] = NUMERIC; Value=";
    private static final String BOOLEAN_VALUE = "] = BOOLEAN; Value=";
    private static final String BLANK_CELL = "] = BLANK CELL";

    public static void printResult(Cell cell, CellType type) {
        System.out.println(readCellType(cell, type));
    }

    private static String readCellType(Cell cell, CellType type) {
        if (type == CellType.STRING) {
            return setCellValue(cell, STRING_VALUE, cell.getRichStringCellValue().toString());
        }
        if (type == CellType.NUMERIC) {
            return setCellValue(cell, NUMERIC_VALUE, cell.getNumericCellValue());
        }
        if (type == CellType.BOOLEAN) {
            return setCellValue(cell, BOOLEAN_VALUE, cell.getBooleanCellValue());
        }
        return printDefaultFrame(cell, BLANK_CELL);
    }

    private static String setCellValue(Cell cell, String cellType, Object cellValue) {
        return printDefaultFrame(cell, cellType) + cellValue;
    }

    private static String printDefaultFrame(Cell cell, String cellType) {
        return new StringBuilder().append(BRACKET)
                .append(cell.getRowIndex())
                .append(COMMA)
                .append(cell.getColumnIndex())
                .append(cellType)
                .toString();
    }
}
