package com.skp.blogg.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/mets", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		
		String tstoreOpenApiUrl = "http://pushapi.skplanetx.com/push/message";
		URL url = null;
		HttpURLConnection httpUrlConnection = null;
		OutputStreamWriter parameterWriter;
		InputStream xmlStream = null;
		
		String paramString = "version=1&target=gc5gsp5bqhhzk55apy4g6hd4g7aun6digh5i6m3ugyadrqj3&message=HelloBye";

		

		try {
			url = new URL(tstoreOpenApiUrl);
			httpUrlConnection = (HttpURLConnection) url.openConnection();
			
			String userCredentials = "7c6474fb662447648dfda8705df7d0cb:e6f967231c774b378ee4c371ddb5833c";
			String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
			
			httpUrlConnection.setRequestProperty("Authorization", basicAuth);
			
			httpUrlConnection.setDoOutput(true);
			httpUrlConnection.setDoInput(true);
			httpUrlConnection.setRequestMethod("POST");
			httpUrlConnection.setUseCaches(false);
			httpUrlConnection.setDefaultUseCaches(false);

			parameterWriter = new OutputStreamWriter(
					httpUrlConnection.getOutputStream());
			parameterWriter.write(paramString);
			parameterWriter.flush();
			
			InputStream result = httpUrlConnection.getInputStream();
			InputStreamReader reader = new InputStreamReader(result);
			char[] buffer = new char[10];
			while(reader.read(buffer) > 0) {
				System.out.println(buffer);
			}
			parameterWriter.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		String userCredentials = "7c6474fb662447648dfda8705df7d0cb:e6f967231c774b378ee4c371ddb5833c";
		String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
		headers.set("Authorization", basicAuth);
		
		MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<String, String>();
		postParameters.add("version", "1");
		postParameters.add("target", "gc5gsp5bqhhzk55apy4g6hd4g7aun6digh5i6m3ugyadrqj3");
		postParameters.add("message", "HelloRestTemplate");
		
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(postParameters, headers);
		
		ResponseEntity<String> response = restTemplate.exchange("http://pushapi.skplanetx.com/push/message", HttpMethod.POST, requestEntity, String.class);
		System.out.println("body #####################");
		System.out.println(response.getBody());
		System.out.println("#######################");*/
		
		return "mets";
	}
	
}

