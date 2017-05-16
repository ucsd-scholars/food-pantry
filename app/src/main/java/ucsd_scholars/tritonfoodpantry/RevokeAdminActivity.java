package ucsd_scholars.tritonfoodpantry;

import android.content.Intent;
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
        }else if(true/*text is an admin*/){
            //revoke privileges
            //user.revoke();
            Toast.makeText(getApplicationContext(), "Privileges Revoked", Toast.LENGTH_SHORT).show();
            //send email to target
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{user.getText().toString()});
            i.putExtra(Intent.EXTRA_SUBJECT, "Triton Food Pantry Administration");
            i.putExtra(Intent.EXTRA_TEXT, "This is an email to inform you that your administrator privileges of the Triton Food Pantry app have been revoked.");
            try{
                startActivity(Intent.createChooser(i, "Send mail..."));
            }catch (android.content.ActivityNotFoundException ex){
                Toast.makeText(getApplicationContext(), "There are no Email clients installed.", Toast.LENGTH_SHORT).show();
            }
        }

    }
}