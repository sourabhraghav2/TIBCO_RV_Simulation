package com.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReaderWriter {
	public static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		return data;
	}

	public static boolean writeToFile(String filePath, String data) throws Exception {

		BufferedWriter output = null;
		File file = new File(filePath);
		output = new BufferedWriter(new FileWriter(file));
		output.write(data);
		output.close();

		return true;
	}

}
