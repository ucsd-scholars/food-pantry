package ucsd_scholars.tritonfoodpantry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static android.R.attr.button;
import static ucsd_scholars.tritonfoodpantry.R.id.button_addAdmin;
import static ucsd_scholars.tritonfoodpantry.R.id.button_revokeAdmin;
import static ucsd_scholars.tritonfoodpantry.R.id.button_toNotificationActivity;

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
