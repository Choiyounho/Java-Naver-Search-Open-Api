package utils;

import console.domain.BookVo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static utils.CommonsConstant.*;

public class NaverSearchApiUtils {

    public static String getImageUrl(Document document) {
        String image = document.toString();
        String imageTag = image.substring(image.indexOf(INDEX_IMG) + 5);
        return imageTag.substring(0, imageTag.indexOf(QUERY));
    }

    public static String getNaverUrl(BookVo vo) throws UnsupportedEncodingException {
        return new StringBuilder()
                .append(NAVER_SEARCH_URL)
                .append(URLEncoder.encode(vo.getTitle(), UTF_8))
                .append(AUTH)
                .append(URLEncoder.encode(vo.getAuthor(), UTF_8))
                .append(PUBL)
                .append(URLEncoder.encode(vo.getCompany(), UTF_8)).toString();
    }

    public static HttpURLConnection requestNaverSearchApi(String openApi) throws IOException {
        HttpURLConnection httpConnection = (HttpURLConnection) new URL(openApi).openConnection();
        httpConnection.setRequestMethod(HTTP_METHOD_GET);
        httpConnection.setRequestProperty(NAVER_SEARCH_API_KEY_ID, NAVER_SEARCH_CLIENT_ID);
        httpConnection.setRequestProperty(NAVER_SEARCH_API_KEY_SECRET, NAVER_SEARCH_CLIENT_SECRET);
        return httpConnection;
    }

    private static BufferedReader getResponseData(HttpURLConnection httpConnection, int responseCode) throws IOException {
        if (responseCode == HTTP_STATE_CODE_200_OK) {
            return new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), UTF_8));
        }
        return new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
    }

    public static StringBuilder getNaverSearchResponse(HttpURLConnection httpConnection) throws IOException {
        int responseCode = httpConnection.getResponseCode();
        BufferedReader bufferedReader = getResponseData(httpConnection, responseCode);
        StringBuilder response = findErrorResponse(bufferedReader);
        bufferedReader.close();
        return response;
    }

    private static StringBuilder findErrorResponse(BufferedReader bufferedReader) throws IOException {
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = bufferedReader.readLine()) != null) {
            response.append(inputLine);
        }
        return response;
    }
    
}
