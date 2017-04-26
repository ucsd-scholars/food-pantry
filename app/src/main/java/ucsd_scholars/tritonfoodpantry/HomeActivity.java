package ucsd_scholars.tritonfoodpantry;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FragmentActivity fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = new FragmentActivity();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        @StringRes int text;
        switch (item.getItemId()) {
            case R.id.menu_home:
                break;
            case R.id.menu_calendar:
                break;
            case R.id.menu_more:
                break;
            default:
                return false;
        }
        return true;
    }

    public void onClickNotifications(View view){
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

}