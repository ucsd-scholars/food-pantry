package ucsd_scholars.tritonfoodpantry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        //if no text, toast

        //if text, check if valid

        //if text and valid adress
        //Grant user.getText().toString() admin privileges
        //toast successful
        //send recipient an email?

        //else toast invalid

    }
}
