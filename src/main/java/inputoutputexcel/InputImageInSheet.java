package inputoutputexcel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class InputImageInSheet {
    public static final String SHEETNAME = "My Sample Excel";
    public static final String PICTURE = "pic.jpg";
    public static final String CREATE_FILENAME = "myFile.xls";
    public static final int SHEET_WIDTH = 20 * 256;
    public static final short CELL_HEIGHT = 120 * 20;
    public static final int CREATE_SHEET_ROWNUM = 2;
    public static final int CREATE_SHEET_COLUMN = 1;
    public static final int COLUMN_INDEX = 1;
    public static final String CREATE_IMAGE_OK = "이미지 생성 성공";

    public static void main(String[] args) {

        try {
            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet(SHEETNAME);

            InputStream inputStream = new FileInputStream(PICTURE);
            byte[] bytes = IOUtils.toByteArray(inputStream); // 스트림에 연결된 파일을 바이트 배열로 읽어 바이트 배열에 저장
            int pictureIdX = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
            inputStream.close();

            CreationHelper helper = workbook.getCreationHelper();
            Drawing drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(1);
            anchor.setRow1(1);
            anchor.setCol2(2);
            anchor.setRow2(3);

            Picture picture = drawing.createPicture(anchor, pictureIdX);

            Cell cell = sheet.createRow(CREATE_SHEET_ROWNUM).createCell(CREATE_SHEET_COLUMN);
            sheet.setColumnWidth(COLUMN_INDEX, SHEET_WIDTH);

            cell.getRow().setHeight(CELL_HEIGHT);

            FileOutputStream fileOutputStream = new FileOutputStream(CREATE_FILENAME);
            workbook.write(fileOutputStream);
            fileOutputStream.close();

            System.out.println(CREATE_IMAGE_OK);

        }catch (Exception e){
            e.getMessage();
        }
    }
}
