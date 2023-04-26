package com.uniguide.adminapply.Helperclass;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionManager {

        private static final String KEY_AUTH_TOKEN = "auth_token";
        private SharedPreferences pref;

        public SessionManager(Context context) {
            pref = PreferenceManager.getDefaultSharedPreferences(context);
        }

        public void setAuthToken(String authToken) {
            pref.edit().putString(KEY_AUTH_TOKEN, authToken).apply();
        }

        public String getAuthToken() {
            return pref.getString(KEY_AUTH_TOKEN, null);
        }

        public boolean isLoggedIn() {
            return getAuthToken() != null;
        }

}
