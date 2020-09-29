package outputbookdata.domain;

import java.io.*;
import java.net.URL;

import static utils.CommonsConstant.ROOT_DIRECTORY;

public class DownloadBroker implements Runnable {

    private static final String DOWNLOAD_COMPLETE = "download complete...";

    private String address;
    private String fileName;

    public DownloadBroker(String address, String fileName) {
        this.address = address;
        this.fileName = ROOT_DIRECTORY + fileName;
    }

    @Override
    public void run() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            InputStream inputStream = new URL(address).openStream();
            BufferedInputStream input = new BufferedInputStream(inputStream);

            int data;
            while ((data = input.read()) != -1) {
                bufferedOutputStream.write(data);
            }

            bufferedOutputStream.close();
            input.close();
            System.out.println(DOWNLOAD_COMPLETE);
        } catch (IOException e) {
            System.out.println("IOException e)" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
    }

}