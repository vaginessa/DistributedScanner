package dsMM.NetworkNotifier;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;



public class SessionManager {

    SharedPreferences pref;
    Editor editor;
    Context context;

    // Only this app has read/write permission for this file
    private static final int MODE = Context.MODE_PRIVATE;
    private static final String PREF_NAME = "mmprop.conf";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_NAME = "username";
    private static final String KEY_PASS = "password";



    public SessionManager(Context context){
        this.context = context;
        pref = this.context.getSharedPreferences(PREF_NAME, MODE);
        editor = pref.edit();
    }



    /**
     * Create login session for a user.
     *
     * His credentials are stored externally
     * to survive when app is destroyed
     *
     * */
    public void loginUser(String name, String pass){
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_PASS, pass);
        editor.putBoolean(IS_LOGGED_IN, true);

        editor.commit();
    }
    // Call inside onPostExecute() of LoginAsyncTask.java
    //      case "ok":
    // ->       SessionManager session = new SessionManager( activity );
    // ->       String credentials = data.split(',');
    // ->       session.loginUser( credentials[0],credentials[1] );



    /**
     * Clear session details.
     *
     * Next time the app is launched no user
     * info will be found
     * and a login will be prompted
     *
     * */
    public void logoutUser(){
        editor.clear();
        editor.commit();
    }
    // Call inside onPostExecute() of LogoutAsyncTask.java
    //      context.startActivity(new Intent(context, LoginActivity.class));
    // ->   SessionManager session = new SessionManager(context);
    // ->   session.logoutUser();



    /**
     * Check session status.
     * @return Returns true if ANY user is logged in
     *  or false if no session was previously stored
     *
     * **/
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGGED_IN, false);
    }
    // Call inside onCreate() of LoginActivity.java
    //      super.onCreate(savedInstanceState);
    // ->   SessionManager session = new SessionManager( this );
    // ->   if( session.isLoggedIn() ){
    // ->        data = session.getSessionDetails();
    // ->        startActivity(new Intent(this, MainActivity.class).putExtra("data",data));
    // ->        finish();
    // ->   }



    /**
     * Get stored session data.
     * @return Returns the user's string-encoded credentials
     *  in the form "username,password"
     *
     * */
    public String getSessionDetails(){

        String user = pref.getString(KEY_NAME, null)
                + ',' + pref.getString(KEY_PASS, null);

        return user;
    }

}
