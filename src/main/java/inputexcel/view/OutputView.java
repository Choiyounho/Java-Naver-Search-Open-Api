package inputexcel.view;

import java.io.FileOutputStream;

public class OutputView {

    private static final String IMAGE_STATUS_CREATED = "201";
    private static final String IMAGE_STATUS_NOT_FOUND = "404";

    public static void response(FileOutputStream fileOutputStream) {
        System.out.println(responseStatus(fileOutputStream));
    }

    private static String responseStatus(FileOutputStream fileOutputStream) {
        return (fileOutputStream != null) ? IMAGE_STATUS_CREATED : IMAGE_STATUS_NOT_FOUND;
    }

}
