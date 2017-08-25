package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TestDb {
	public static void main(String[] args) {
		File f = new File("D:/AndroidStudioWorkspaceTest/LWAndroid/app/src/main/assets/meituan_cities.db");
		String string;
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			while ((string = br.readLine()) != null) {
				System.out.println(string);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
