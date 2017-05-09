package ucsd_scholars.tritonfoodpantry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AdminMenuActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
    }

    @Override
    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.button_toAdminInventory:
                intent = new Intent(this, InventoryActivity.class);
                startActivity(intent);
                break;

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

        }
    }

}
