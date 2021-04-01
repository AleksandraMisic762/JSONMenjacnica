package rs.ac.bg.fon.ai.main;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import rs.ac.bg.fon.ai.menjacnica.Transakcija;

public class Main2 {

	private static final String BASE_URL = "http://api.currencylayer.com";
	private static final String API_KEY = "a62c45a83931a42a56bd75d355483ba8";
	private static final String SOURCE = "USD";
	private static final String CURRENCIES = "EUR,CHF,CAD";
	private static final LocalDate DATE = LocalDate.of(2020, 6, 21);
	private static final double IZNOS = 100;

	public static void main(String[] args) {
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

			URL url = new URL(BASE_URL + "/live?access_key=" + API_KEY + "&source=" + SOURCE
					+ "&currencies=" + CURRENCIES + "&date=" + DATE);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");

			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			JsonObject res = gson.fromJson(reader, JsonObject.class);

			System.out.println(res);
			
			List<Transakcija> transakcije = new ArrayList<Transakcija>();

			FileWriter file = new FileWriter("ostale_transakcije.json");

			for (int i = 0; i < 3; i++) {
				double kurs = -1;
				
				if (res.get("success").getAsBoolean()) {
					
					kurs = res.get("quotes").getAsJsonObject()
							.get(SOURCE + CURRENCIES.split(",")[i]).getAsDouble();
					
				}
				transakcije.add(new Transakcija(SOURCE, CURRENCIES.split(",")[i], IZNOS, IZNOS * kurs, DATE));
			}
			gson.toJson(transakcije, file);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
