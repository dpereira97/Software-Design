package database;

import java.util.List;
import java.util.ArrayList;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

import com.google.gson.Gson;

/**
 * Inicializes the Database of contacts
 * @author fc47806, fc47878, fc47888
 */
public class StartDatabase {
	
	// Constants
	public static final String DATABASE = "db.txt";

	public static void startDatabase() {
		
		Gson gson = new Gson();
		List<Contact> list = new ArrayList<>();
		
		for (int i = 0; i < 3; i++)
			list.add(new Contact("+351" + "98765432" + i));

		String json = gson.toJson(list);
		
		try (BufferedWriter out = new BufferedWriter(new FileWriter(DATABASE));) {
			out.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}