package com.example.User.UserService.Security;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

@Component
public class AuthnServiceOutboundCommunication {


    @Autowired
    RestTemplate rt;


    @Value("${medilab.authn.userinfo.endpoint}")
    String tokenEndpoint;




    public String getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<Void> requestData = new HttpEntity<>(headers);
        ignoreCertificates();

        try {
            ResponseEntity<String> iamResp = rt.exchange(tokenEndpoint, HttpMethod.GET, requestData, String.class);
            if (iamResp.getStatusCode() == HttpStatus.OK) {
                String respBody = iamResp.getBody();
                JSONObject jsonRespBody = new JSONObject(respBody);
                return jsonRespBody.toString();
            } else {
                // Log the error or throw an exception
                System.out.println("Failed to retrieve user info: " + iamResp.getStatusCode());
            }
        } catch (Exception e) {
            // Handle exceptions and log them
            System.out.println("Error in communication: " + e.getMessage());
        }
        return null;
    }




    private void ignoreCertificates() {
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        } };

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {

        }
    }


}
