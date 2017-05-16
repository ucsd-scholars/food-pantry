package ucsd_scholars.tritonfoodpantry;

import android.content.Intent;
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
        }else if(true/*text is a user email*/){
            //grant privileges
            //user.grant();
            Toast.makeText(getApplicationContext(), "Privileges Granted", Toast.LENGTH_SHORT).show();
            //send email to target
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{user.getText().toString()});
            i.putExtra(Intent.EXTRA_SUBJECT, "Triton Food Pantry Administration");
            i.putExtra(Intent.EXTRA_TEXT, "This is an email to inform you that you have been granted administrator privileges of the Triton Food Pantry app.");
            try{
                startActivity(Intent.createChooser(i, "Send mail..."));
            }catch (android.content.ActivityNotFoundException ex){
                Toast.makeText(getApplicationContext(), "There are no Email clients installed.", Toast.LENGTH_SHORT).show();
            }
        }

    }
}