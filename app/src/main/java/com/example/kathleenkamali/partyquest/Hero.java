package com.example.kathleenkamali.partyquest;

/**
 * Created by kathleenkamali on 12/23/16.
 */

public class Hero {

    int _id;
    String _name;
    String _heroClass;
    String _stat;
    int _power;
    int _health;
    String _user_id;
    int _place;

    public Hero() {

    }

    public Hero(String name, String hero_class, String stat, int power, int health, String user_id, int place) {
        this._name = name;
        this._heroClass = hero_class;
        this._stat = stat;
        this._power = power;
        this._health = health;
        this._user_id = user_id;
        this._place = place;
    }

    public int getId() {
        return this._id;
    }

    public String getName() {
        return this._name;
    }

    public String getHeroClass() {
        return this._heroClass;
    }

    public String getStat() {
        return this._stat;
    }

    public int getPower() {
        return this._power;
    }

    public int getHealth() {
        return this._health;
    }

    public String getUserId() { return this._user_id; }

    public int getPlace() { return this._place; }

    public void setId(int new_id) {
        this._id = new_id;
    }

    public void setName(String new_name) {
        this._name = new_name;
    }

    public void setHeroClass(String new_class) {
        this._heroClass = new_class;
    }

    public void setStat(String new_stat) {
        this._stat = new_stat;
    }

    public void setPower(int new_power) {
        this._power = new_power;
    }

    public void setHealth(int new_health) {
        this._health = new_health;
    }

    public void setUserID(String new_user_id) {
        this._user_id = new_user_id;
    }

    public void set_place(int place) { this._place = place; }
}
