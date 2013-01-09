package me.naithantu.flippo;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Menu extends Activity implements OnClickListener{

	Button game, options;
	ArrayList<String> generatedNumbers = new ArrayList<String>();
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set the layout to activity_menu xml file.
        setContentView(R.layout.activity_menu);
        //Find button in xml file.
        game = (Button) findViewById(R.id.bGame);
       	options = (Button) findViewById(R.id.bOptions);
       	//Create onClickListeners for the buttons.
       	game.setOnClickListener(this);
       	options.setOnClickListener(this);
    }

	public void onClick(View v) {
		switch(v.getId()){
		case R.id.bGame:
			Intent game = new Intent(Menu.this, Game.class);
			startActivity(game);
			break;
		case R.id.bOptions:
			Toast.makeText(getApplicationContext(), "This button doesn't do anything (yet)!", Toast.LENGTH_SHORT).show();
			break;
		}
	}
	
	public void onPause(){
		super.onPause();
		finish();
	}
	
	
}
