package com.example.kathleenkamali.partyquest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by kathleenkamali on 1/6/17.
 */

public class quest_view extends Activity {
    TextView stats;
    TextView story_view;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quest_view);

        DatabaseHandler db = new DatabaseHandler(this);
        SharedPreferences sharedPref = this.getSharedPreferences("SESSION", Context.MODE_PRIVATE);
        String user_id = sharedPref.getString("id", null);
        Hero current_hero = db.getHero(user_id);
        stats = (TextView)findViewById(R.id.current_character_view);
        stats.append("Name: " + current_hero.getName() + "\nClass: " + current_hero.getHeroClass() + "\nStat: " +
        current_hero.getStat() + "\nStat Power: " + current_hero.getPower() + "\nHealth: " + current_hero.getHealth());
        Story_DatabaseHandler sdb = new Story_DatabaseHandler(this);
        Story s = sdb.getStory(current_hero.getPlace() + 1);
        story_view = (TextView)findViewById(R.id.story_view);
        story_view.append(s._story);
        for (int i = 0; i < s._available_actions.length; i++) {
            Button newButton = new Button(this);
            newButton.setText(s._available_actions[i]);
            LinearLayout ll = (LinearLayout)findViewById(R.id.story_layout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.addView(newButton, lp);
        }
    }
}
