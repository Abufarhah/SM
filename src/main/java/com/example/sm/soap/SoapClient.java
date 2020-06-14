package com.example.sm.soap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SoapClient {
    public static int provision(int id, String name, double price) {
        URL url;
        try {
            url = new URL("http://localhost:8083/SoapWS");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/xml");
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            String POST_PARAMS = "";
            POST_PARAMS += "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:me=\"http://example.com/me\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <me:addBundleRequest>\n" +
                    "         <me:id>"+id+"</me:id>\n" +
                    "         <me:name>"+name+"</me:name>\n" +
                    "         <me:price>"+price+"</me:price>\n" +
                    "      </me:addBundleRequest>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            os.write(POST_PARAMS.getBytes());
            os.flush();
            os.close();
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output;
            String result = "";
            while ((output = br.readLine()) != null) {
                result += output;
            }
            //System.out.println(result);
            if(result.contains("exist")) {
                return 0;
            }
            return 1;

        } catch (Exception e) {
            return -1;
            //System.out.println("***provision-errer");
        }

    }

}
