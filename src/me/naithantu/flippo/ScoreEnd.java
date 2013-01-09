package me.naithantu.flippo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;

public class ScoreEnd extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score_end);
		final Bundle endBundle = getIntent().getExtras();
		//Add highScore if score is good enough.
		int userScore = ((Long) endBundle.getLong("totalTime")).intValue();
		String userName = "nanana";
		//TODO Get userscore out of bundle (totalTime)
		//TODO Let user choose username in an edittext.
		//HashMap<String, Integer> highScores = new HashMap<String, Integer>();
		//highScores.put("mic", 12);
		//highScores.put("fra", 0);
		//saveHighScores(highScores);

		HashMap<String, Integer> highScores = readHighScores();

		List<String> nameList = new ArrayList<String>(highScores.keySet());
		List<Integer> scoreList = new ArrayList<Integer>(highScores.values());
		int i = 0;
		if (nameList.size() == 0) {
			scoreList.add(userScore);
			nameList.add(userName);
		} else {
			for (int score : highScores.values()) {
				if (score < userScore) {
					scoreList.add(i, userScore);
					nameList.add(i, userName);
					if (scoreList.size() > 5) {
						scoreList.remove(5);
						nameList.remove(5);
					}
				}
				i++;
			}
		}
		saveHighScores(nameList, scoreList);
		System.out.println(readHighScores());
	}

	public void saveHighScores(List<String> names, List<Integer> scores) {
		try {
			BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(openFileOutput("highscores", Context.MODE_PRIVATE)));
			for (int i = 0; i < names.size(); i++) {
				bos.write(names.get(i) + ":" + scores.get(i) + "\n");
			}
			bos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveHighScores(HashMap<String, Integer> highScores) {
		try {
			BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(openFileOutput("highscores", Context.MODE_PRIVATE)));
			for (Entry<String, Integer> entry : highScores.entrySet()) {
				bos.write(entry.getKey() + ":" + entry.getValue() + "\n");
			}
			bos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, Integer> readHighScores() {
		HashMap<String, Integer> highScores = new HashMap<String, Integer>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("highscores")));
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println("Current line: " + line);
				highScores.put(line.split(":")[0], Integer.parseInt(line.split(":")[1]));
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return highScores;
	}
}