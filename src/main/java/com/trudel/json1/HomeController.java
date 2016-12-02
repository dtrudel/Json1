package com.trudel.json1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "home";
		
	}
	
	@RequestMapping(value = "/location", method = RequestMethod.GET)
	public String form(Locale locale, Model model, @RequestParam("city") String city) {
//city park 39.747193, -104.948321
//st louis  38.6263836,-90.2032471
//savannah  32.093464, -81.129665
//portland  45.484618, -122.875514		
		double lat = 0;
		double lon = 0;
		if (city.equals("park city")){
			lat = 39.747193;
			lon = -104.948321;
		}
		else if (city.equals("st louis")){
			lat = 38.626383;
			lon = -90.203247;
		}
		else if (city.equals("savannah")){
			lat = 32.093464;
			lon = -81.129665;
		}
		else if (city.equals("portland")){
			lat = 45.484618;
			lon = -122.875514;
		}
		String url = "http://forecast.weather.gov/MapClick.php?lat=" + lat + "&lon=" + lon + "&FcstType=json";
		
		URL urlobj;
		
		try {
			urlobj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) urlobj.openConnection();
		con.setRequestMethod("GET");
		
		int responseCode = con.getResponseCode();
		
		if (responseCode == 200){
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null){
				response.append(inputLine);
				System.out.println(inputLine);
			}
			in.close();
			Gson gson = new Gson();
			NOAA2 noaa = gson.fromJson(response.toString(), NOAA2.class);
			Data data = noaa.getData();
			List<String> forecast = data.getTemperature();
			model.addAttribute("name", noaa.getCurrentobservation().getName());
			model.addAttribute("temp1", forecast.get(0));
			model.addAttribute("temp2", forecast.get(2));
			model.addAttribute("temp3", forecast.get(4));
			model.addAttribute("temp4", forecast.get(6));
			model.addAttribute("temp5", forecast.get(8));
		} else {
			
			
		}
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		return "form";
	}
	
}
