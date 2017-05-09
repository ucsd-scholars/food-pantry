package ucsd_scholars.tritonfoodpantry;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
                                                    HomeFragment.OnFragmentInteractionListener,
                                                    CalendarFragment.OnFragmentInteractionListener,
                                                    MoreOptionsFragment.OnFragmentInteractionListener {

    private Fragment fragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
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
        switch (item.getItemId()) {
            case R.id.menu_home:
                fragment = new HomeFragment();
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


    // where the button takes you


    public void onClickNotifications(View view){
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //
    }
}