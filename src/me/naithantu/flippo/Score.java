package me.naithantu.flippo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Score extends Activity {

	TextView tvScore, tvGamesRemaining;
	Button bNext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score);
		tvScore = (TextView) findViewById(R.id.tvScore);
		tvGamesRemaining = (TextView) findViewById(R.id.tvGamesRemaining);
		bNext = (Button) findViewById(R.id.bNext);
		final Bundle scoreBundle = getIntent().getExtras();
		tvScore.setText(Long.toString(scoreBundle.getLong("time")));
		tvGamesRemaining.setText(Integer.toString(3 - scoreBundle.getInt("gameNumber")));
		bNext.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Bundle gameBundle = new Bundle();
				gameBundle.putInt("gameNumber", scoreBundle.getInt("gameNumber"));
				gameBundle.putLong("totalTime", scoreBundle.getLong("totalTime"));
				if (scoreBundle.getInt("gameNumber") < 3) {
					Intent startGame = new Intent(Score.this, Game.class);
					startGame.putExtras(gameBundle);
					startActivity(startGame);
				} else {
					Intent endScreen = new Intent(Score.this, ScoreEnd.class);
					endScreen.putExtras(gameBundle);
					startActivity(endScreen);
				}
			}
		});
	}

	@Override
	public void onPause() {
		super.onPause();
		finish();
	}
}
