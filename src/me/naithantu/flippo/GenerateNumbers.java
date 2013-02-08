package me.naithantu.flippo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;

public class GenerateNumbers {
	Context context;

	public GenerateNumbers(Context context) {
		context.getFilesDir();
		this.context = context;
	}

	List<String> generatedNumbers = new ArrayList<String>();

	public List<String> getNumbers() {
		List<String> numbers = new ArrayList<String>();
		try {
			//Get numbers 
			List<String> numbersToAdd = new ArrayList<String>();
			BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput("numbers")));
			String lineTemp = br.readLine();
			if (lineTemp == null)
				return emptyFile();
			numbers = Arrays.asList(lineTemp.split(":"));
			String line;
			while ((line = br.readLine()) != null) {
				numbersToAdd.add(line);
			}
			br.close();
			//Put not used 2 numbers back into file.
			BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(context.openFileOutput("numbers", Context.MODE_PRIVATE)));
			for (String number : numbersToAdd) {
				bos.write(number);
				bos.newLine();
			}
			bos.close();
			addMore();
			return numbers;
		} catch (FileNotFoundException e) {
			//If file did not yet exist. Generate a set of numbers.
			return emptyFile();
		} catch (IOException e) {
			e.printStackTrace();
			return numbers;
		}
	}

	public List<String> generate(boolean add) {
		final Order order = new Order();
		generatedNumbers = order.generateNumbers();
		if (!add)
			return generatedNumbers;
		addThatNumber(generatedNumbers);
		return null;
	}

	private void addThatNumber(List<String> numbers) {
		try {
			String line = "";
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(context.openFileOutput("numbers", Context.MODE_APPEND)));
			for (String number : numbers) {
				line += number + ":";
			}
			bw.write(line);
			bw.newLine();
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	List<String> emptyFile() {
		System.out.println("Generating first number!");
		List<String> numbers = generate(false);
		addMore();
		return numbers;
	}

	public int printEntireFile() {
		int i = 0;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput("numbers")));
			String line;
			while ((line = br.readLine()) != null) {
				i++;
				System.out.println(i + ": " + line);
			}
			br.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		return i;
	}

	public void addMore() {
		int i = printEntireFile();
		if (i < 3) {
			System.out.println("Going to generate " + (3 - i) + " more!");
			Thread genThread = new Thread(new Runnable() {
				public void run() {
					generate(true);
					addMore();
				}
			});
			genThread.start();
		}
	}
}
