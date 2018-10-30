package charmingdev.d.c3.prefs;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by CHARMING on 7/13/2018.
 */

public class UserInfo {
    private static final String TAG = UserInfo.class.getSimpleName();
    private static final String PREF_NAME = "login_data";
    private static final String KEY_ID = "id";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_LASTNAME = "lastname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_ADDRESS_TWO = "address_two";
    private static final String KEY_STATE = "state";
    private static final String KEY_COUNTRY = "country";
    private static final String KEY_STATUS = "status";
    private static final String KEY_DAY = "day";
    private static final String KEY_MONTH = "month";
    private static final String KEY_YEAR = "year";
    private static final String KEY_TIME = "time";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_PROFILE_DP = "dp";
    private static final String KEY_PROFILE_URL = "url";
    private static final String KEY_NOTIFICATION = "notices";
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;

    public UserInfo(Context ctx){
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences(PREF_NAME, ctx.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void clearUserInfo(){
        editor.clear();
        editor.commit();
    }

    public void setKeyId(String id){
        editor.putString(KEY_ID, id);
        editor.apply();
    }

    public void setKeyFirstname(String firstname){
        editor.putString(KEY_FIRSTNAME, firstname);
        editor.apply();
    }

    public void setKeyLastname(String lastname){
        editor.putString(KEY_LASTNAME, lastname);
        editor.apply();
    }

    public void setKeyEmail(String email){
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    public void setKeyUsername(String username){
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public void setKeyGender(String gender){
        editor.putString(KEY_GENDER, gender);
        editor.apply();
    }

    public void setKeyPhone(String phone){
        editor.putString(KEY_PHONE, phone);
        editor.apply();
    }

    public void setKeyAddress(String address){
        editor.putString(KEY_ADDRESS, address);
        editor.apply();
    }

    public void setKeyAddressTwo(String addressTwo){
        editor.putString(KEY_ADDRESS_TWO, addressTwo);
        editor.apply();
    }

    public void setKeyState(String state){
        editor.putString(KEY_STATE, state);
        editor.apply();
    }

    public void setKeyCountry(String country){
        editor.putString(KEY_COUNTRY, country);
        editor.apply();
    }

    public void setKeyStatus(String status){
        editor.putString(KEY_STATUS, status);
        editor.apply();
    }

    public void setKeyDay(String day){
        editor.putString(KEY_DAY, day);
        editor.apply();
    }

    public void setKeyMonth(String month){
        editor.putString(KEY_MONTH, month);
        editor.apply();
    }

    public void setKeyYear(String year){
        editor.putString(KEY_YEAR, year);
        editor.apply();
    }

    public void setKeyTime(String time){
        editor.putString(KEY_TIME, time);
        editor.apply();
    }

    public void setKeyTimestamp(String timestamp){
        editor.putString(KEY_TIMESTAMP, timestamp);
        editor.apply();
    }

    public void setKeyProfileUrl(String profileUrl){
        editor.putString(KEY_PROFILE_URL, profileUrl);
        editor.apply();
    }

    public void setKeyNotification(String notification){
        editor.putString(KEY_NOTIFICATION, notification);
        editor.apply();
    }


    public String getKeyId(){
        return prefs.getString(KEY_ID, "");
    }

    public String getKeyFirstname(){
        return prefs.getString(KEY_FIRSTNAME, "");
    }

    public String getKeyLastname(){
        return prefs.getString(KEY_LASTNAME, "");
    }

    public String getKeyEmail(){
        return prefs.getString(KEY_EMAIL, "");
    }

    public String getKeyUsername(){
        return prefs.getString(KEY_USERNAME, "");
    }

    public String getKeyGender(){
        return prefs.getString(KEY_GENDER, "");
    }

    public String getKeyPhone(){
        return prefs.getString(KEY_PHONE, "");
    }

    public String getKeyAddress(){
        return prefs.getString(KEY_ADDRESS, "");
    }

    public String getKeyAddressTwo(){
        return prefs.getString(KEY_ADDRESS_TWO, "");
    }

    public String getKeyState(){
        return prefs.getString(KEY_STATE, "");
    }

    public String getKeyCountry(){
        return prefs.getString(KEY_COUNTRY, "");
    }

    public String getKeyStatus(){
        return prefs.getString(KEY_STATUS, "");
    }

    public String getKeyDay(){
        return prefs.getString(KEY_DAY, "");
    }

    public String getKeyMonth(){
        return prefs.getString(KEY_MONTH, "");
    }

    public String getKeyYear(){
        return prefs.getString(KEY_YEAR, "");
    }

    public String getKeyTime(){
        return prefs.getString(KEY_TIME, "");
    }

    public String getKeyTimestamp(){
        return prefs.getString(KEY_TIMESTAMP, "");
    }

    public String getKeyProfileUrl(){
        return prefs.getString(KEY_PROFILE_URL, "");
    }

    public String getKeyNotification(){
        return prefs.getString(KEY_NOTIFICATION, "");
    }
}
