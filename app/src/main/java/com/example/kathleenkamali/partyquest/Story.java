package com.example.kathleenkamali.partyquest;

/**
 * Created by kathleenkamali on 1/8/17.
 */

public class Story {
    int _id;
    String _story;
    String[] _available_actions;
    int _place;

    public Story() {

    }

    public Story(String story, String[] actions, int place) {
        this._story = story;
        this._available_actions = actions;
        this._place = place;
    }

    public void setId(int id) {
        this._id = id;
    }

    public void setStory(String story_text) {
        this._story = story_text;
    }

    public void setActions(String[] actions) {
        this._available_actions = actions;
    }

    public void setPlace(int place) {
        this._place = place;
    }

    public int getId() {
        return this._id;
    }

    public String getStory() {
        return this._story;
    }

    public String[] getActions() {
        return this._available_actions;
    }

    public int getPlace() {
        return this._place;
    }
}
