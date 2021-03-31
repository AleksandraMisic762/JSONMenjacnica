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

public class Main2 {

	private static final String BASE_URL = "http://api.currencylayer.com";
	private static final String API_KEY = "2e4baadf5c5ae6ba436f53ae5558107f";

	public static void main(String[] args) {
		String valute = "EUR,CHF,CAD";

		Transakcija t = new Transakcija();
		t.setIzvornaValuta("USD");
		t.setPocetniIznos(100);
		t.setDatumTransakcije(LocalDate.of(2020, 6, 21));

		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

			URL url = new URL(BASE_URL + "/live?access_key=" + API_KEY + "&source=" + t.getIzvornaValuta()
					+ "&currencies=" + valute + "&date=" + t.getDatumTransakcije());

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");

			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			JsonObject res = gson.fromJson(reader, JsonObject.class);

			System.out.println(res);
			
			Transakcija[] transakcije = new Transakcija[3];

			FileWriter file = new FileWriter("ostale_transakcije.json");

			for (int i = 0; i < 3; i++) {
				t.setKrajnjaValuta(valute.split(",")[i]);
				if (res.get("success").getAsBoolean()) {
					t.setKonvertovaniIznos(res.get("quotes").getAsJsonObject()
							.get(t.getIzvornaValuta() + valute.split(",")[i]).getAsDouble());
				}
				transakcije[i] = t;
			}
			gson.toJson(transakcije, file);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
