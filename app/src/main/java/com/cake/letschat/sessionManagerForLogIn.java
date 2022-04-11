package com.cake.letschat;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class sessionManagerForLogIn {
    //Variable
    SharedPreferences userLoginDetails;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_LOGIN ="IsLoggedIn";

    public static final String KEY_FIRSTNAME ="firstname";
    public static final String KEY_PHONE ="phone";
    public static final String KEY_LASTNAME ="LASTname";
    public static final String KEY_PROFILE ="PROFILE";
//    public static final String KEY_FIRSTNAME ="firstname";


    public sessionManagerForLogIn(Context context) {
        this.context = context;
        userLoginDetails=context.getSharedPreferences("userLoginSession",context.MODE_PRIVATE);
        editor = userLoginDetails.edit();
    }

    public void createLoginSession (String firstName , String lastName , String phone , String profile){
        editor.putBoolean(IS_LOGIN , true);

        editor.putString(KEY_FIRSTNAME , firstName);
        editor.putString(KEY_LASTNAME , lastName);
        editor.putString(KEY_PHONE , phone);
        editor.putString(KEY_PROFILE , profile);

        editor.commit();
    }

    public HashMap<String , String > getUserDataFromSession(){
        HashMap<String , String> userData = new HashMap<String, String>();

        userData.put(KEY_FIRSTNAME,userLoginDetails.getString(KEY_FIRSTNAME,null));
        userData.put(KEY_LASTNAME,userLoginDetails.getString(KEY_LASTNAME,null));
        userData.put(KEY_PHONE,userLoginDetails.getString(KEY_PHONE,null));
        userData.put(KEY_PROFILE,userLoginDetails.getString(KEY_PROFILE,null));
        return userData;
    }
    public boolean checkedLogin(){
        if (userLoginDetails.getBoolean(IS_LOGIN,false)){
            return true;
        }
        else
            return false;
    }
    public void logoutUserFromSession(){
        editor.clear();
        editor.commit();
    }

}
