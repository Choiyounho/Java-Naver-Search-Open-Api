package outputbookdata.domain;

import console.domain.ExcelVo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static utils.CommonsConstant.*;

public class NaverSearchApi {

    public static String getNaverUrl(ExcelVo vo) throws UnsupportedEncodingException {
        StringBuilder openApi = new StringBuilder();
        openApi.append(NAVER_SEARCH_URL)
                .append(URLEncoder.encode(vo.getTitle(), UTF_8))
                .append(AUTH)
                .append(URLEncoder.encode(vo.getAuthor(), UTF_8))
                .append(PUBL)
                .append(URLEncoder.encode(vo.getCompany(), UTF_8));

        return openApi.toString();
    }

    public static HttpURLConnection requestNaverSearchApi(String openApi) throws IOException {
        HttpURLConnection httpConnection;
        URL url = new URL(openApi);
        httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.setRequestMethod(HTTP_METHOD_GET);
        httpConnection.setRequestProperty(NAVER_SEARCH_API_KEY_ID, NAVER_SEARCH_CLIENT_ID);
        httpConnection.setRequestProperty(NAVER_SEARCH_API_KEY_SECRET, NAVER_SEARCH_CLIENT_SECRET);
        return httpConnection;
    }

    public static BufferedReader getResponseData(HttpURLConnection httpConnection, int responseCode) throws IOException {
        if (responseCode == HTTP_STATE_CODE_200_OK) {
            return new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), UTF_8));
        }
        return new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
    }

    public static StringBuilder getNaverSearchResponse(HttpURLConnection httpConnection) throws IOException {
        StringBuilder response;
        int responseCode = httpConnection.getResponseCode();
        BufferedReader bufferedReader;
        bufferedReader = getResponseData(httpConnection, responseCode);
        response = findErrorResponse(bufferedReader);
        bufferedReader.close();
        return response;
    }

    public static StringBuilder findErrorResponse(BufferedReader bufferedReader) throws IOException {
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = bufferedReader.readLine()) != null) {
            response.append(inputLine);
        }
        return response;
    }
}
