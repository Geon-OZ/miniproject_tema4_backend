package com.sparta.miniproject_team4.validator;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class URLValidator {

    public static boolean isValidUrl(String url) //포멧에 맞는지
    {
        try {
            new URL(url).toURI();
            return true;        //정상
        }
        catch (URISyntaxException exception) {
            return false;       //에러
        }
        catch (MalformedURLException exception) {
            return false;       //에러
        }
    }
}
