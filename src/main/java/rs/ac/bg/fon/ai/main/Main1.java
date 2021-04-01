package rs.ac.bg.fon.ai.main;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import rs.ac.bg.fon.ai.menjacnica.Transakcija;

public class Main1 {
	
	private static final String BASE_URL = "http://api.currencylayer.com";
	private static final String API_KEY = "a62c45a83931a42a56bd75d355483ba8";
	private static final String SOURCE = "USD";
	private static final String CURRENCIES = "CAD";
	private static final double IZNOS = 3;
	
	
	public static void main(String[] args) {
		
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

			URL url = new URL(
					BASE_URL + "/live?access_key=" + API_KEY + "&source=" + SOURCE + "&currencies=" + CURRENCIES);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");

			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			JsonObject res = gson.fromJson(reader, JsonObject.class);

			System.out.println(res);
			Transakcija t = new Transakcija();
			
			t.setIzvornaValuta(SOURCE);
			t.setKrajnjaValuta(CURRENCIES);
			t.setPocetniIznos(IZNOS);
			t.setDatumTransakcije(LocalDate.now());
			
			if (res.get("success").getAsBoolean()) {
				t.setKonvertovaniIznos(res.get("quotes").getAsJsonObject().get(SOURCE + CURRENCIES).getAsDouble() * IZNOS);
			} else {
				t.setKonvertovaniIznos(-1);
			}

			FileWriter file = new FileWriter("prva_transakcija.json");
			
			gson.toJson(t, file);
			
			file.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
