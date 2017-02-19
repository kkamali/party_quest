package com.example.kathleenkamali.partyquest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by kathleenkamali on 12/27/16.
 */

public class character_creator extends Activity {
    Button rollButton;
    Button saveButton;
    EditText nameEditText;
    int click_counter = 0;
    TextView createdCharacter;
    Intent intent;
    Hero createdHero;
    DatabaseHandler db = new DatabaseHandler(this);

    public void createHero() {
        SharedPreferences sharedPref = this.getSharedPreferences("SESSION", Context.MODE_PRIVATE);
        String user_id = sharedPref.getString("id", null);
        EditText nameText = (EditText)findViewById(R.id.editText);
        String heroName = nameText.getText().toString();
        Spinner classSpinner=(Spinner) findViewById(R.id.classes_spinner);
        String heroClass = classSpinner.getSelectedItem().toString();
        String stat;
        int health;
        int power;
        if (heroClass.equals("Bard")) {
            stat = "charisma";
            health = 10;
            power = (int) Math.floor(Math.random() * 21);
        } else if (heroClass.equals("Cleric")) {
            stat = "wisdom";
            health = 13;
            power = (int) Math.floor(Math.random() * 21);
        } else if (heroClass.equals("Fighter")) {
            stat = "strength";
            health = 18;
            power = (int) Math.floor(Math.random() * 21);
        } else if (heroClass.equals("Rogue")) {
            stat = "dexterity";
            health = 15;
            power = (int) Math.floor(Math.random() * 21);
        } else {
            stat = "intelligence";
            health = 12;
            power = (int) Math.floor(Math.random() * 21);
        }
        createdHero = new Hero(heroName, heroClass, stat, power, health, user_id, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_creator);
        Spinner spinner = (Spinner) findViewById(R.id.classes_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.classes_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        rollButton=(Button)findViewById(R.id.button);
        saveButton=(Button)findViewById(R.id.saveButton);
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_counter++;
                System.out.println(click_counter);
                nameEditText = (EditText)findViewById(R.id.editText);
                final String name = nameEditText.getText().toString();
                if (name.length() == 0) {
                    nameEditText.requestFocus();
                    nameEditText.setError("FIELD CANNOT BE EMPTY");
                } else {
                    createHero();
                    createdCharacter = (TextView) findViewById(R.id.character);
                    if (click_counter == 2) {
                        createdCharacter.setText("You have one roll left! Your current character is " + createdHero.getName() +
                                ". They are a " + createdHero.getHeroClass() + " and their stat is " + createdHero.getStat() + " which is at level "
                                + createdHero.getPower());
                    } else  if (click_counter == 1){
                        createdCharacter.setText("You have two rolls left! Your current character is " + createdHero.getName() +
                        ". They are a " + createdHero.getHeroClass() + " and their stat is " + createdHero.getStat() + " which " +
                                "is at level " + createdHero.getPower());
                        saveButton.setVisibility(View.VISIBLE);
                    } else {
                        createdCharacter.setText("No more rolls! Your character is " + createdHero.getName() +
                        ". They are " + createdHero.getHeroClass() + " and their stat is " + createdHero.getStat() + " which is at level "
                        + createdHero.getPower());
                        rollButton.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addHero(createdHero);
                intent = new Intent(character_creator.this, character_main.class);
                startActivity(intent);
            }
        });
    }
}
