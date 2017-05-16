package ucsd_scholars.tritonfoodpantry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RevokeAdminActivity extends AppCompatActivity {

    private EditText user;
    Button revoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revoke_admin);

        revoke = (Button)findViewById(R.id.button_revoke);
        user = (EditText)findViewById(R.id.user);

        revoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revokeAdmin();
            }
        });
    }

    private void revokeAdmin(){

        if (user.getText().toString().isEmpty()){
            Toast.makeText(getApplication(), "Please Enter an Administrator's Email", Toast.LENGTH_SHORT).show();
            return;
        }else if(true/*text is an admin*/){
            //revoke privileges
            Toast.makeText(getApplicationContext(), "Privileges Revoked", Toast.LENGTH_SHORT).show();
            return;
        }else{
            return;
        }

    }
}