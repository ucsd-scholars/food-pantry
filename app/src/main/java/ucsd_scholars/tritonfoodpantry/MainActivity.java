package ucsd_scholars.tritonfoodpantry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //firebase authentication and listener
    protected static FirebaseAuth mAuth;
    protected static FirebaseAuth.AuthStateListener mAuthListener;
    protected static firebaseWrapper db;

    // used for facebook login button
    protected static CallbackManager callbackManager;

    private static final String TAG = "MainActivity";

    // user's uid which we will store here
    protected static String user_uid;
    protected static String user_email;

    // our SharedPreferences will save our settings like user uid (which indicates if admin or not)
    public static final String PREFS_NAME = "FoodPantryPreferences";
    protected static SharedPreferences settings;
    protected static SharedPreferences.Editor settingsEditor;

    protected static boolean signedInWithFacebook = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new firebaseWrapper();
        db.addMessage("Database!");
        db.readMsg();

        // these 2 reads from database will initialize our strings of all admin and client emails
        db.readAdminList();
        db.readUserList();

        // reads any recent notifications or stories from database so we can update ui
        db.readNotifications();
        db.readStories();

        // if we decided to exit from the login screen, then this will close the app
        if (getIntent().getBooleanExtra("EXIT", false))
        {
            finish();
        }

        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        settingsEditor = settings.edit();

        // initialize the FirebaseAuth instance and the AuthStateListener method so
        // you can track whenever the user signs in or out.
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    goToHomeActivity();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        // facebook login button
        com.facebook.login.widget.LoginButton facebookButton = (LoginButton) findViewById(R.id.button_facebook);
        facebookButton.setReadPermissions("email", "public_profile");
        // Callback registration
        callbackManager = CallbackManager.Factory.create();
        facebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                signedInWithFacebook = true;
                handleFacebookAccessToken(loginResult.getAccessToken());
                //goToHomeActivity();
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });

        // changes the logo to white
        //ImageView logo = (ImageView) findViewById(R.id.logo);
        //logo.setColorFilter(Color.WHITE);
    }

    public void onClickLogin(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onClickSignUp(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void onClickGoogle(View view){
        Intent intent = new Intent(this, GoogleSignInActivity.class);
        startActivity(intent);
    }

    // adds authentication listener to auth instance
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    // removes authentication listener from auth instance
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    // this method goes to home if user is already logged in
    private void goToHomeActivity() {
        finish();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    // for facebook login
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    // when user signs in through facebook, facebook access token exchanged for firebase token
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            goToHomeActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    /*// we check app's SharedPrefs to check if uid matches our stored admin uid
    private void checkIfAdmin(String user_uid){
        settingsEditor.putString("user_uid", user_uid);
        if(user_uid.equals(getString(R.string.admin_uid))){
            settingsEditor.putBoolean("isAdmin", true);
            Toast.makeText(this, "ADMIN user uid: " + user_uid, Toast.LENGTH_SHORT).show();
        }
        else {
            settingsEditor.putBoolean("isAdmin", false);
            Toast.makeText(this, "ClIENT user uid: " + user_uid, Toast.LENGTH_SHORT).show();
        }
        settingsEditor.commit();
    }*/

    // we check app's SharedPrefs to check if email matches our stored admin email
    /*private void checkIfAdmin(String user_email){
        settingsEditor.putString("user_email", user_email);
        if(user_email.equals(getString(R.string.admin_email))){
            settingsEditor.putBoolean("isAdmin", true);
            Toast.makeText(this, "ADMIN user email: " + user_email, Toast.LENGTH_SHORT).show();
            db.writeToAdminList(user_email);
        }
        else {
            settingsEditor.putBoolean("isAdmin", false);
            Toast.makeText(this, "ClIENT user email: " + user_email, Toast.LENGTH_SHORT).show();
        }
        settingsEditor.commit();
    }*/

    // we check app's SharedPrefs to check if uid is set; if not then user has never signed in
    private boolean userPreviouslySignedIn(){
        /*String current_uid = settings.getString("user_uid", null);
        if (current_uid == null) {
            return false;
        }*/
        /*String current_email = settings.getString("user_email", null);
        if (current_email == null) {
            return false;
        }*/
        if(settings.getBoolean("alreadySignedIn", false)){
            return true;
        }
        return false;
    }

    // used to reset uid pref and admin status if user signs out
    protected static void clear_uid_pref(){
        settingsEditor.remove("user_uid");
        settingsEditor.remove("user_email");
        settingsEditor.putBoolean("isAdmin", false);
        settingsEditor.commit();
    }

    // saves isAdmin pref to be true
    private void grantAdminStatus(){
            settingsEditor.putBoolean("isAdmin", true);
            Toast.makeText(this, "ADMIN user email: " + user_email, Toast.LENGTH_SHORT).show();
            db.writeToAdminList(user_email);
            settingsEditor.commit();
    }
}
