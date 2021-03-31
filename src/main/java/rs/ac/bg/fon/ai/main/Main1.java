package rs.ac.bg.fon.ai.main;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import rs.ac.bg.fon.ai.menjacnica.Transakcija;

public class Main1 {
	
	private static final String BASE_URL = "http://api.currencylayer.com";
	private static final String API_KEY = "2e4baadf5c5ae6ba436f53ae5558107f";

	public static void main(String[] args) {
		Transakcija t = new Transakcija();
		
		t.setIzvornaValuta("USD");
		t.setKrajnjaValuta("CAD");
		t.setPocetniIznos(3);
		t.setDatumTransakcije(new Date(System.currentTimeMillis()));
		
		try {
			Gson gson = new Gson();

			URL url = new URL(
					BASE_URL + "/live?access_key=" + API_KEY + "&source=" + t.getIzvornaValuta() + "&currencies=" + t.getKrajnjaValuta());

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");

			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			JsonObject res = gson.fromJson(reader, JsonObject.class);

			System.out.println(res);
			
			if (res.get("success").getAsBoolean()) {
				t.setKonvertovaniIznos(res.get("quotes").getAsJsonObject().get(t.getIzvornaValuta() + t.getKrajnjaValuta()).getAsDouble());
			} else {
				t.setKonvertovaniIznos(-1);
			}

			FileWriter file = new FileWriter("prva_transakcija.json");
			
			Gson gson1 = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
			
			gson1.toJson(t, file);
			
			file.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
