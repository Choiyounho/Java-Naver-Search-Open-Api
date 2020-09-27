package inputexcel.domain;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static utils.CommonsConstant.ROOT_DIRECTORY;

public class ExcelFileManagement {

    private static final String IMAGE_FILE = ROOT_DIRECTORY + "pic.jpg";
    private static final String CREATED_FILENAME = ROOT_DIRECTORY + "myFile.xls";

    public static int createImageFile(Workbook workbook) throws IOException {
        InputStream inputStream = new FileInputStream(IMAGE_FILE);
        byte[] bytes = IOUtils.toByteArray(inputStream); // 스트림에 연결된 파일을 바이트 배열로 읽어 바이트 배열에 저장
        int pictureIdX = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
        inputStream.close();
        return pictureIdX;
    }

    public static FileOutputStream excelOutputImage(Workbook workbook) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(CREATED_FILENAME);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        return fileOutputStream;
    }

    public static ClientAnchor anchorCell(Workbook workbook) {
        CreationHelper helper = workbook.getCreationHelper();
        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setCol1(1);
        anchor.setRow1(1);
        anchor.setCol2(2);
        anchor.setRow2(3);
        return anchor;
    }

    public static void createPicture(Sheet sheet, int pictureIndex, ClientAnchor anchor) {
        Drawing drawing = sheet.createDrawingPatriarch();
        drawing.createPicture(anchor, pictureIndex);
    }

}
