package inputexcel.controller;

import inputexcel.domain.PoiApi;
import inputexcel.view.OutputView;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;

import static utils.CommonsConstant.*;

public class InputImageController {

    private static final String SHEETNAME = "My Sample Excel";
    private static final String CREATED_FILENAME = ROOT_DIRECTORY + "myFile.xls";
    private static final int CREATED_SHEET_ROW_NUM = 2;
    private static final int CREATED_SHEET_COLUMN_NUM = 1;
    private static final int COLUMN_INDEX = 1;

    public void inputImageInSheet() {
        try {
            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet(SHEETNAME);

            int pictureIndex = PoiApi.createImageFile(workbook);

            ClientAnchor anchor = PoiApi.anchorCell(workbook);

            PoiApi.createPicture(sheet, pictureIndex, anchor);

            Cell cell = sheet.createRow(CREATED_SHEET_ROW_NUM)
                    .createCell(CREATED_SHEET_COLUMN_NUM);

            sheet.setColumnWidth(COLUMN_INDEX, SHEET_WIDTH);

            cell.getRow().setHeight(CELL_HEIGHT);

            FileOutputStream fileOutputStream = PoiApi.excelOutputImage(workbook, CREATED_FILENAME);

            OutputView.response(fileOutputStream);
        } catch (IOException e) {
            System.out.println("IOException e " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
    }

}
