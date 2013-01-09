package me.naithantu.flippo;

import java.util.ArrayList;
import java.util.Date;
import java.util.EmptyStackException;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class Game extends Activity implements OnClickListener {

	ShuntingJava sh = new ShuntingJava();
	ImageButton add, subtract, divide, multiply, done, remove, b1, b2, b3, b4;
	int n1 = 0, n2 = 0, n3 = 0, n4 = 0, duration = Toast.LENGTH_SHORT;
	boolean allowCalculation = false;
	List<Integer> usedButtons = new ArrayList<Integer>();
	List<String> calculationText = new ArrayList<String>();
	Long startTime;
	TextView answer;
	final int[] flippoNumbers = { R.id.b1, R.id.b2, R.id.b3, R.id.b4 };
	Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		Date date = new Date();
		startTime = Long.valueOf(date.getTime());
		initVars();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	void initVars() {
		context = getApplicationContext();
		//Answer textview
		answer = (TextView) findViewById(R.id.CalcTv);

		//Calculation buttons
		findViewById(R.id.add).setOnClickListener(this);
		findViewById(R.id.substract).setOnClickListener(this);
		findViewById(R.id.divide).setOnClickListener(this);
		findViewById(R.id.multiply).setOnClickListener(this);
		findViewById(R.id.ibDone).setOnClickListener(this);
		findViewById(R.id.ibRemove).setOnClickListener(this);
		findViewById(R.id.ibBracketclose).setOnClickListener(this);
		findViewById(R.id.ibBracketopen).setOnClickListener(this);

		//Number buttons
		b1 = (ImageButton) findViewById(R.id.b1);
		b2 = (ImageButton) findViewById(R.id.b2);
		b3 = (ImageButton) findViewById(R.id.b3);
		b4 = (ImageButton) findViewById(R.id.b4);
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
		b4.setOnClickListener(this);

		GenerateNumbers genNumbers = new GenerateNumbers(context);
		List<String> generatedList = genNumbers.getNumbers();
		//b1.setImageResource(R.drawable.ic_action_1);
		System.out.println("Using: " + generatedList);
		for (int i = 0; i <= 3; i++) {
			String letter = generatedList.get(i * 2);
			ImageView image = (ImageView) findViewById(flippoNumbers[i]);
			int id = getResources().getIdentifier("me.naithantu.flippo:drawable/ic_action_" + letter, null, null);
			image.setImageResource(id);
		}
		n1 = Integer.parseInt(generatedList.get(0));
		n2 = Integer.parseInt(generatedList.get(2));
		n3 = Integer.parseInt(generatedList.get(4));
		n4 = Integer.parseInt(generatedList.get(6));
	}

	public void onClick(View v) {
		String toAdd = null;
		int buttonPressed = v.getId();

		if (buttonPressed == R.id.ibRemove) {
			removePrevious();
			return;
		}

		if (buttonPressed == R.id.ibDone) {
			//Check if all numbers have been used.
			if (!allNumbersUsed(false)) {
				Toast.makeText(context, "You haven't used all numbers yet! ", duration).show();
				return;
			}
			double answer;
			try {
				answer = sh.calcAnswer(calculationText);
			} catch (EmptyStackException e) {
				Toast.makeText(context, "Can't calculate that, check your calculation!", duration).show();
				return;
			}
			if (answer == 24) {
				//Correct answer!
				Bundle scoreBundle = new Bundle();
				Date date = new Date();
				Long endTime = Long.valueOf(date.getTime());
				scoreBundle.putLong("time", (endTime - startTime) / 1000);
				Bundle gameBundle = getIntent().getExtras();
				if (gameBundle != null) {
					scoreBundle.putInt("gameNumber", gameBundle.getInt("gameNumber") + 1);
					scoreBundle.putLong("totalTime", gameBundle.getLong("totalTime") + (endTime - startTime) / 1000);
				} else {
					scoreBundle.putLong("totalTime", (endTime - startTime) / 1000);
					scoreBundle.putInt("gameNumber", 1);
				}
				Intent scoreScreen = new Intent(Game.this, Score.class);
				scoreScreen.putExtras(scoreBundle);
				finish();
				startActivity(scoreScreen);
			} else {
				//Incorrect answer!
				Toast.makeText(context, "That is not 24!", duration).show();
			}
			return;
		}

		//Calculation buttons.
		//Allowcalculation == true if you need to use an operator and false if you need to use a number.
		if (allowCalculation) {
			if (buttonPressed == R.id.add && !allNumbersUsed(true)) {
				toAdd = "+";
			} else if (buttonPressed == R.id.substract && !allNumbersUsed(true)) {
				toAdd = "-";
			} else if (buttonPressed == R.id.divide && !allNumbersUsed(true)) {
				toAdd = "/";
			} else if (buttonPressed == R.id.multiply && !allNumbersUsed(true)) {
				toAdd = "*";
			} else if (buttonPressed == R.id.ibBracketclose) {
				toAdd = ")";
				calculationText.add(toAdd);
				updateScreenText();
				return;
			}
		} else {
			//Number buttons;
			if (buttonPressed == R.id.b1) {
				b1.setVisibility(View.INVISIBLE);
				toAdd = Integer.toString(n1);
			} else if (buttonPressed == R.id.b2) {
				b2.setVisibility(View.INVISIBLE);
				toAdd = Integer.toString(n2);
			} else if (buttonPressed == R.id.b3) {
				b3.setVisibility(View.INVISIBLE);
				toAdd = Integer.toString(n3);
			} else if (buttonPressed == R.id.b4) {
				b4.setVisibility(View.INVISIBLE);
				toAdd = Integer.toString(n4);
			} else if (buttonPressed == R.id.ibBracketopen) {
				toAdd = "(";
				calculationText.add(toAdd);
				updateScreenText();
				return;
			}
		}

		//Do stuff if button press was a success.
		if (toAdd != null) {
			usedButtons.add(buttonPressed);
			calculationText.add(toAdd);
			updateScreenText();
			allowCalculation = !allowCalculation;
		}
	}

	void updateScreenText() {
		String screenText = "";
		for (String letter : calculationText) {
			screenText = screenText + letter + " ";
		}
		answer.setText(screenText);
		/*
		 * int i = 0; for (String letter : calculationText) { if
		 * (letter.equals("+")) { letter = "plus"; } else if
		 * (letter.equals("*")) { letter = "multiply"; } else if
		 * (letter.equals("-")) { letter = "minus"; } else if
		 * (letter.equals("/")) { letter = "divide"; } //TODO Fix to new thing.
		 * ImageView image = (ImageView) findViewById(answerID[i]); int id =
		 * getResources
		 * ().getIdentifier("me.naithantu.flippo:drawable/ic_action_" + letter,
		 * null, null); image.setImageResource(id);
		 * image.setVisibility(View.VISIBLE); i++; }
		 */
	}

	void removePrevious() {
		if (calculationText.size() == 0) {
			Toast.makeText(context, "There is nothing to remove!", duration).show();
			return;
		}
		//Filter brackets out.
		if (calculationText.get(calculationText.size() - 1).equals("(") || calculationText.get(calculationText.size() - 1).equals(")")) {
			calculationText.remove(calculationText.size() - 1);
			updateScreenText();
		} else {
			findViewById(usedButtons.get(usedButtons.size() - 1)).setVisibility(View.VISIBLE);
			usedButtons.remove(usedButtons.size() - 1);
			allowCalculation = !allowCalculation;
			calculationText.remove(calculationText.size() - 1);
			updateScreenText();
		}
	}

	boolean allNumbersUsed(boolean opCheck) {
		if (b1.getVisibility() == View.INVISIBLE && b2.getVisibility() == View.INVISIBLE && b3.getVisibility() == View.INVISIBLE && b4.getVisibility() == View.INVISIBLE) {
			if (opCheck)
				Toast.makeText(context, "You can't add more operators!", duration).show();
			return true;
		}
		return false;
	}
}

//4+3+6+3
//2

//1,2,3,4
//4*3*2 = 24