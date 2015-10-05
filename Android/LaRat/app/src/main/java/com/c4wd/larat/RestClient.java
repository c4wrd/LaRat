package com.c4wd.larat;

/**
 * Created by cory on 10/4/15.
 */
import com.loopj.android.http.*;

public class RestClient {
    private static final String BASE_URL = "http://c4wd.com/larat/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static class DefaultJsonHandler extends JsonHttpResponseHandler {

    }
}