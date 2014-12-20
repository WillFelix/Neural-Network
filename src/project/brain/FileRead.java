package project.brain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileRead {
	
	public static List<Double[]> pull(String base) {
		return pull(base, ",");
	}
	
	public static List<Double[]> pull(String base, String split) {
		String file = base.replace(".txt", "") + ".txt";
		BufferedReader br = null;
		String line = "";
		List<Double[]> database = new ArrayList<Double[]>();
	 
		try {
			
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				String[] data = line.split(split);
				Double[] array = new Double[data.length];
				for (int i = 0; i < data.length; i++) {
					array[i] = Double.parseDouble(data[i]);
				}
				database.add(array);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	 
		return database;
	}
}
