package com.b2b.passwork.Utility;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;

import com.b2b.passwork.Activity.LoginActivity;
import com.b2b.passwork.Activity.SplaceScreen;

import java.util.HashMap;

public class UserSessionManager {

    SharedPreferences pref;


    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREFER_NAME = "passwordPref";
    private static final String APP_VERSION = "app_version";
    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";
    private static final String IS_FIRST_TIME = "is_first_time";
    private static final String DEVICE_TOKEN = "access_token";
    // SocialLoginUserDetail name (make variable public to access from outside)
    public static final String KEY_ROLE = "name";
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_LAST_NAME = "last_name";
    public static final String KEY_USER_TYPE = "user_type";
    public static final String KEY_ACCESS_TOKEN = "access_token";
    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_ID = "id";
    public static final String KEY_apiToken = "apiToken";
    public static final String KEY_AVATAR = "avatar";
    public static final String KEY_COVERIMAGE = "cover";
    public static final String KEY_COMPANY_NAME = "company_name";
    public static final String KEY_COMPANY_ID = "company_id";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_PURCHASE = "purcahse";
    public static final String KEY_LIKED = "like";
    public static final String KEY_FAV = "favourite";
    public static final String KEY_RECENTPLAY = "recentplay";
    public static final String KEY_ONETIME_CALL_API = "onetimecallapi";
    public static final String KEY_TRACK_URL = "track_url";
    public static final String PREF_TYPE = "purchaseType";
    public static final String LANGUANGE = "language";
    public static final String LANGUANGE_NAME = "language_name";

    // Shared preferences file name
    public static final String PREF_NAME = "welcome";
    public static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    public static final String IS_NIGHT_MODE = "IsNightMode";
    public static final String IS_WORKSPACE_ID = "Isworkspace_id";
    public static final String IS_WORKSPACE_TILE = "IsworkspaceTitle";
    public static final String IS_WORKSPACE_ADDRESS = "Isworkspace_address";
    private static UserSessionManager instance;
    private static Context context;

    @SuppressLint("CommitPrefEdits")
    public static UserSessionManager getInstance(Context context)
    {
        UserSessionManager.context = context;
        if (instance == null) {
            instance = new UserSessionManager(context);
        }
        if(isDataSettingOn()==false)
        {
            if(!(context instanceof SplaceScreen))
            {
                showSettingsAlert();
            }
        }
        return instance;
    }



    public UserSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME,   PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void setNightMode(boolean isNgihtMode) {
        editor.putBoolean(IS_NIGHT_MODE, isNgihtMode).apply();
        editor.commit();
    }

    public boolean getNightMode() {
        return pref.getBoolean(IS_NIGHT_MODE, false);

    }

    public void setLang(String lang) {
        editor.putString(LANGUANGE, lang);
        editor.commit();
    }

    public void setLangTitle( String  languageTitle) {
        editor.putString(LANGUANGE_NAME, languageTitle);
        editor.commit();
    }

    public String getLanguange() {
        return pref.getString(LANGUANGE, "en");
    }

    public String getLanguangeTitle() {
        return pref.getString(LANGUANGE_NAME, "English");
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setworkspaceDetail(String workspaceID, String workspaceName, String workspaceAddress) {
        editor.putString(IS_WORKSPACE_ID, workspaceID);
        editor.putString(IS_WORKSPACE_TILE, workspaceName);
        editor.putString(IS_WORKSPACE_ADDRESS, workspaceAddress);
        editor.commit();
    }

    public void setUserDetail(String userName,  String email) {
        editor.putString(KEY_ROLE, userName);
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }

    public void setCoverIMage(String cover) {
        editor.putString(KEY_COVERIMAGE, cover);

        editor.commit();
    }

    public String getCoverImage() {

        return pref.getString(KEY_COVERIMAGE, "");
    }


    public void setAvtar(String avtar) {
        editor.putString(KEY_AVATAR, avtar);
        editor.commit();
    }

    public void setPurchaseCount(int count) {
        editor.putInt(KEY_PURCHASE , count);
        editor.commit();
    }

    public void setOneTimeCallAPI() {
        editor.putInt(KEY_ONETIME_CALL_API , 1);
        editor.commit();
    }

    public int getOneTimeCallAPI() {
        return pref.getInt(KEY_ONETIME_CALL_API, 0);
    }

    public int getPurchaseCount() {

        return pref.getInt(KEY_PURCHASE, 0);
    }

    public void setFovCount(int count) {
        editor.putInt(KEY_FAV , count);
        editor.commit();
    }

    public int getFovCount() {

        return pref.getInt(KEY_FAV, 0);
    }

    public void setLIkedCount(int count) {
        editor.putInt(KEY_LIKED , count);
        editor.commit();
    }

    public int getLikedCount() {

        return pref.getInt(KEY_LIKED, 0);
    }

    public void setRecentyPlayCount(int count) {
        editor.putInt(KEY_RECENTPLAY , count);
        editor.commit();
    }

    public int getRecentPlayCount() {

        return pref.getInt(KEY_RECENTPLAY, 0);
    }





    public void setPurchaseType(String Type) {

        editor.putString(PREF_TYPE, Type);
        editor.commit();
    }

    public void setGender(String Gender) {

        editor.putString(KEY_GENDER, Gender);
        editor.commit();
    }

    public String getPurchaseType() {

        return pref.getString(PREF_TYPE, "");
    }

    public String getKeyGender(){

        return pref.getString(KEY_GENDER, "");
    }

    public String getUserName() {

        return pref.getString(KEY_ROLE, "");
    }



    //Create login session
   /* public void setcreateUserLoginSession(String userId, String userName, String firstName, String lastName, String userType, String accessToken, String email, String avtar, String contry){        // Storing login value as TRUE

        editor.putString(KEY_ROLE, userName);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_FIRST_NAME, firstName);
        editor.putString(KEY_LAST_NAME, lastName);
        editor.putString(KEY_USER_TYPE, userType);
        editor.putString(KEY_AVATAR, avtar);
        editor.putString(KEY_COUNTRY, contry);
        editor.putString(KEY_ACCESS_TOKEN, accessToken);
        editor.putString(KEY_ID, userId);
        // commit changes
        editor.commit();

    }*/

    public void setUserLoginSession(String userId, String role, String firstName, String lastName, String emailid, String accessToken, String mobileNo, String companyName, String companyId){        // Storing login value as TRUE

        editor.putString(KEY_ROLE, role);
        editor.putString(KEY_EMAIL, emailid);
        editor.putString(KEY_FIRST_NAME, firstName);
        editor.putString(KEY_LAST_NAME, lastName);
        editor.putString(KEY_MOBILE, mobileNo);
        editor.putString(KEY_ACCESS_TOKEN, accessToken);
        editor.putString(KEY_ID, userId);
        editor.putString(KEY_COMPANY_ID, companyId);
        editor.putString(KEY_COMPANY_NAME, companyName);
        // commit changes
        editor.commit();

    }

    public void setUpdateProfile(String firstName, String lastName, String mobile) {

        editor.putString(KEY_FIRST_NAME, firstName);
        editor.putString(KEY_LAST_NAME, lastName);
        editor.putString(KEY_MOBILE, mobile);
        // commit changes
        editor.commit();


    }

    public void setUserLogin(boolean isFirstTime) {
        editor.putBoolean(IS_USER_LOGIN, isFirstTime);
        editor.commit();
    }

    public void setFirstTime(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME, isFirstTime);
        editor.commit();
    }


    public String getUserId() {
        Log.i("getUserId", "SESSION_VAR : " + KEY_ID);
        return pref.getString(KEY_ID, "0");
    }


    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */
    public boolean checkLogin(){
        // Check login status
       /* if(this.isUserLoggedIn()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }*/


        return false;

    }


    public HashMap<String, String> getworkspaceList(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(IS_WORKSPACE_ID, pref.getString(IS_WORKSPACE_ID, null));
        user.put(IS_WORKSPACE_TILE, pref.getString(IS_WORKSPACE_TILE, null));
        user.put(IS_WORKSPACE_ADDRESS, pref.getString(IS_WORKSPACE_ADDRESS, null));
        //   user.put(KEY_ID, pref.getString(KEY_apiToken, null));

        // return user

        return user;
    }



    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user name
        user.put(KEY_ROLE, pref.getString(KEY_ROLE, null));
        user.put(KEY_FIRST_NAME, pref.getString(KEY_FIRST_NAME, null));
        user.put(KEY_LAST_NAME, pref.getString(KEY_LAST_NAME, null));
       user.put(KEY_COMPANY_NAME, pref.getString(KEY_COMPANY_NAME, null));
       user.put(KEY_ACCESS_TOKEN, pref.getString(KEY_ACCESS_TOKEN, null));
        user.put(KEY_MOBILE, pref.getString(KEY_MOBILE, null));     // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
       user.put(KEY_COMPANY_ID, pref.getString(KEY_COMPANY_ID, null));
        // user email id
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        //   user.put(KEY_ID, pref.getString(KEY_apiToken, null));

        // return user

        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){

        // Clearing all user data from Shared Preferences

        editor.putBoolean(IS_USER_LOGIN, false);
        editor.putInt(KEY_ONETIME_CALL_API , 0);
        editor.putInt(KEY_PURCHASE , 0);
        editor.putInt(KEY_LIKED , 0);
        editor.putInt(KEY_RECENTPLAY , 0);
        editor.putInt(KEY_FAV , 0);
        editor.commit();
        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, LoginActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }



    public boolean isUserLoggedIn() {
        return pref.getBoolean(IS_USER_LOGIN, false);
    }
    public static boolean isDataSettingOn() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = networkInfo.isConnected();
        NetworkInfo networkInfo1 = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = networkInfo1.isConnected();
        return isMobileConn == true || isWifiConn == true;
    }

    public static void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("UserData Settings");

        // Setting Dialog Message
        alertDialog.setMessage("UserData access is not enabled. Please visit to settings menu and Enable the data?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                context.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                System.exit(0);
            }
        });
        Log.i("showSettingsAlert", "calling for showSettingsAlert before try catch");
        try {
            // Showing Alert Message
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
