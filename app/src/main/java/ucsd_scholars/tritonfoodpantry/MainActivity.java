package ucsd_scholars.tritonfoodpantry;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //firebase authentication and listener
    protected static FirebaseAuth mAuth;
    protected static FirebaseAuth.AuthStateListener mAuthListener;

    private static final String TAG = "MainActivity";

    boolean isSignedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseWrapper db = new firebaseWrapper();
        db.addMessage("Database!");
        db.readMsg();

        // initialize the FirebaseAuth instance and the AuthStateListener method so
        // you can track whenever the user signs in or out.
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    isSignedIn = true;
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    isSignedIn = false;
                }
                // ...
            }
        };

        // if mAuth tells us that user is already signed in, we go to homescreen
        if(isSignedIn){
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }

        // if we decided to exit from the login screen, then this will close the app
        if (getIntent().getBooleanExtra("EXIT", false))
        {
            finish();
        }

    }

    public void onClickLogin(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onClickSignUp(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
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
}
