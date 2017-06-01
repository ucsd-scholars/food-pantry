package ucsd_scholars.tritonfoodpantry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AdminMenuActivity extends AppCompatActivity implements View.OnClickListener{

    private Button button_addAdmin;
    private Button button_revokeAdmin;
    private Button button_toNotificationActivity;
    private Button button_editHomePage;
    private Button button_removeStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        button_toNotificationActivity = (Button)findViewById(R.id.button_toNotificationActivity);
        button_toNotificationActivity.setOnClickListener(this);
        button_addAdmin = (Button)findViewById(R.id.button_addAdmin);
        button_addAdmin.setOnClickListener(this);
        button_revokeAdmin = (Button)findViewById(R.id.button_revokeAdmin);
        button_revokeAdmin.setOnClickListener(this);
        button_editHomePage = (Button)findViewById(R.id.button_editHomePage);
        button_editHomePage.setOnClickListener(this);
        button_removeStory = (Button)findViewById(R.id.button_removeStories);
        button_removeStory.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.button_addAdmin:
                intent = new Intent(this, AddAdminActivity.class);
                startActivity(intent);
                break;

            case R.id.button_revokeAdmin:
                intent = new Intent(this, RevokeAdminActivity.class);
                startActivity(intent);
                break;

            case R.id.button_toNotificationActivity:
                intent = new Intent(this, NotificationActivity.class);
                startActivity(intent);
                break;

            case R.id.button_editHomePage:
                intent = new Intent(this, NewStoryActivity.class);
                startActivity(intent);
                break;

            case R.id.button_removeStories:
                intent = new Intent(this, RemoveStoryActivity.class);
                startActivity(intent);
                break;
        }
    }

}
