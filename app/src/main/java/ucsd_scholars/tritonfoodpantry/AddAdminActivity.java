package ucsd_scholars.tritonfoodpantry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddAdminActivity extends AppCompatActivity {

    private EditText user;
    Button grant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

        grant = (Button)findViewById(R.id.button_grant);
        user = (EditText)findViewById(R.id.user);

        grant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grantAdmin();
            }
        });
    }

    private void grantAdmin(){

        if(user.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please Enter a User's Email", Toast.LENGTH_SHORT).show();
            return;
        }else if(true/*text is a user email*/){
            //grant to user
            Toast.makeText(getApplicationContext(), "Privileges Granted", Toast.LENGTH_SHORT).show();
            return;
        }else{
            return;
        }

    }
}