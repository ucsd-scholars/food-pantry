package ucsd_scholars.tritonfoodpantry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        //if no text, toast

        //if text, check if valid

        //if text and valid adress
        //Revoke user.getText().toString() admin privileges
        //toast successful
        //send recipient an email?

        //else toast invalid

    }
}