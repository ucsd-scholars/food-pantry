package ucsd_scholars.tritonfoodpantry;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.firebase.messaging.FirebaseMessaging;

import static ucsd_scholars.tritonfoodpantry.MainActivity.db;
import static ucsd_scholars.tritonfoodpantry.MainActivity.mAuth;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
                                                    HomeFragment.OnFragmentInteractionListener,
                                                    CalendarFragment.OnFragmentInteractionListener,
                                                    MoreOptionsFragment.OnFragmentInteractionListener,
                                                    OnConnectionFailedListener{

    private Fragment fragment;
    FragmentManager fragmentManager;
    protected GoogleApiClient mGoogleApiClient;

    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // adds current user to email database
        if(firebaseWrapper.userEmails != null){
            db.writeToEmailList(mAuth.getCurrentUser().getEmail().toLowerCase());
        }

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //  .requestIdToken(getString(R.string.default_web_client_id))
                .requestIdToken("778703925901-s9f5iu6u7fvnpkiq5gb20cf1qkqgljkn.apps.googleusercontent.com")
                .requestEmail()
                .build();
        // [END config_signin]
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // for user to receive notifications
        FirebaseMessaging.getInstance().subscribeToTopic("news");

        fragmentManager = getSupportFragmentManager();
        fragment = new HomeFragment();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment).commit();
        
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        @StringRes int text;
        boolean isHome = false;
        switch (item.getItemId()) {
            case R.id.menu_home:
                fragment = new HomeFragment();
                isHome = true;
                break;
            case R.id.menu_calendar:
                fragment = new CalendarFragment();
                break;
            case R.id.menu_more:
                fragment = new MoreOptionsFragment();
                break;
            default:
                return false;
        }

        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment).commit();
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //
    }

//    @Override
//    public void onBackPressed() {
//        // when back is pressed, we go back to mainactivity and tell it to exit
//        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("EXIT", true);
//        startActivity(intent);
//    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d("HomeActivity", "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    // call this from moreoptionsfragment to help sign out user
    protected GoogleApiClient getGoogleApiClient(){
        return mGoogleApiClient;
    }
}