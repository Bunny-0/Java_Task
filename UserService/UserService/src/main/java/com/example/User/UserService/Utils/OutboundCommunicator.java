package com.example.User.UserService.Utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class OutboundCommunicator {

	public static String sendConfirmAccountEmail(String notificationData) {
		RestTemplate rt = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		
		HttpEntity<String> entity = new HttpEntity<String>(notificationData, headers);
		
		ResponseEntity<String> respJson = rt.postForEntity(ServiceUtil.NOTIFICATION_SERVICE_URI, entity, String.class);
		
		System.out.println(respJson.getBody());
		return respJson.getBody();
	}
	
}
