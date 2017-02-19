package com.example.kathleenkamali.partyquest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by kathleenkamali on 12/24/16.
 */

public class character_main extends Activity {
    Intent intent;
    Button startButton;
    Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_view);
        DatabaseHandler db = new DatabaseHandler(this);
        SharedPreferences sharedPref = this.getSharedPreferences("SESSION", Context.MODE_PRIVATE);
        String user_id = sharedPref.getString("id", null);
        boolean exists = db.heroExists(user_id);
        if (exists) {
            Hero current_hero = db.getHero(user_id);
            if (current_hero.getPlace() == 0) {
                startButton = (Button)findViewById(R.id.startButton);
                startButton.setVisibility(View.VISIBLE);
                startButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        intent = new Intent(character_main.this, quest_view.class);
                        startActivity(intent);
                    }
                });
            } else {
                continueButton = (Button)findViewById(R.id.continueButton);
                continueButton.setVisibility(View.VISIBLE);
                continueButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        intent = new Intent(character_main.this, quest_view.class);
                        startActivity(intent);
                    }
                });
            }
        } else {
            intent = new Intent(character_main.this, character_creator.class);
            startActivity(intent);
        }
    }

}
