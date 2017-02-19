package com.example.kathleenkamali.partyquest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

public class Dash extends AppCompatActivity {
    private TextView info;
    private CallbackManager callbackManager;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Story_DatabaseHandler story_db = new Story_DatabaseHandler(this);
        SQLiteDatabase db = story_db.getWritableDatabase();
        String count = "SELECT count(*) FROM storyDatabase";
        boolean tableExists;
        try {
            Cursor cursor = db.rawQuery(count, null);
            tableExists = true;
            System.out.println("******* Table exists! ************");
            cursor.close();
        } catch(Exception e) {
            tableExists = false;
        }
        if (!tableExists) {
            createStoryDB(story_db);
        }


        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_dash);
        info = (TextView)findViewById(R.id.info);
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null) {
            LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {

                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    Log.v("LoginActivity", response.toString());
                                    try {
                                        String fb_id = object.getString("id");
                                        SharedPreferences sharedPref = getSharedPreferences("SESSION", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        editor.putString(getString(R.string.id), fb_id);
                                        editor.commit();

                                        intent = new Intent(Dash.this,character_main.class);
                                        startActivity(intent);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,email");
                    request.setParameters(parameters);
                    request.executeAsync();
                }

                @Override
                public void onCancel() {
                    info.setText("Login attempt canceled.");
                }

                @Override
                public void onError(FacebookException e) {
                    info.setText("Login attempt failed.");
                }
            });
        } else {
            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            // Application code
                            try {
                                String fb_id = object.getString("id");
                                SharedPreferences sharedPref = getSharedPreferences("SESSION", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString(getString(R.string.id), fb_id);
                                editor.commit();

                                intent = new Intent(Dash.this,character_main.class);
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link");
            request.setParameters(parameters);
            request.executeAsync();
        }

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    protected void createStoryDB(Story_DatabaseHandler sdb) {
        Resources res = getResources();
        String[] story_pieces = res.getStringArray(R.array.story_array);
        for (int i = 0; i < story_pieces.length - 1; i++) {
            String text = story_pieces[i];
            String actions = story_pieces[i + 1];
            String[] all_actions = actions.split(",");
            int place = i;
            Story s = new Story(text, all_actions, place);
            sdb.addStory(s);
        }
    }
}
