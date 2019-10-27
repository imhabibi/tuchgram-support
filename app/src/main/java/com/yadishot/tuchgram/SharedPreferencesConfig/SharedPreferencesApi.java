package com.yadishot.tuchgram.SharedPreferencesConfig;

import android.content.Context;
import android.content.SharedPreferences;

import com.yadishot.tuchgram.R;

public class SharedPreferencesApi {
    Context context;
    SharedPreferences sharedPreferences;

    public SharedPreferencesApi(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.app_sharedPreferencesApi), Context.MODE_PRIVATE);
    }


    public void writeSuccessLoginStatus(boolean status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.app_sharedPreferencesApi_user_Loginstatus), status);
        editor.commit();
    }

    public void writePhoneNumber(String number) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.app_sharedPreferencesApi_user_phoneNumber), number);
        editor.commit();
    }

    public void writeUserId(String id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.app_sharedPreferencesApi_user_id), id);
        editor.commit();
    }

    public void writeFullname(String fullname) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.app_sharedPreferencesApi_fullname), fullname);
        editor.commit();
    }
    public void writeUserLoginNum(String codeLogin) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.app_sharedPreferencesApi_userLogin_Num), codeLogin);
        editor.commit();
    }

    public boolean readLoginStatus() {
        boolean status = sharedPreferences.getBoolean(context.getResources().getString(R.string.app_sharedPreferencesApi_user_Loginstatus), false);
        return status;
    }

    public String readFullname() {
        String status = sharedPreferences.getString(context.getResources().getString(R.string.app_sharedPreferencesApi_fullname), "un'know");
        return status;
    }

    public String readPhoneNumber() {
        String status = sharedPreferences.getString(context.getResources().getString(R.string.app_sharedPreferencesApi_user_phoneNumber), "+98");
        return status;
    }
    public String readUserId() {
        String status = sharedPreferences.getString(context.getResources().getString(R.string.app_sharedPreferencesApi_user_id), "0");
        return status;
    }

    public String readUserLoginNum() {
        String status = sharedPreferences.getString(context.getResources().getString(R.string.app_sharedPreferencesApi_userLogin_Num), "0");
        return status;
    }

}
