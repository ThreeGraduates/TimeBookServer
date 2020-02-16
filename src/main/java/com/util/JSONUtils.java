package com.util;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JSONUtils {

    public static JSONObject getJsonObjFromRequest(HttpServletRequest request) throws IOException {
        InputStream is=request.getInputStream();
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        String result=br.readLine();
        return JSONObject.fromObject(result);
    }

}
