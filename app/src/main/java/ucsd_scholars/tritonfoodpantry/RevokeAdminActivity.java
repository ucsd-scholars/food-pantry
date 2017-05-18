package ucsd_scholars.tritonfoodpantry;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static ucsd_scholars.tritonfoodpantry.MainActivity.db;
import static ucsd_scholars.tritonfoodpantry.firebaseWrapper.adminEmails;

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
        // if valid user email, we write the email to our database of admin emails

        if(user.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter a valid user email", Toast.LENGTH_SHORT).show();
        }
        else {
            revokeAdminStatus(user.getText().toString());
        }
    }

    // checks if email exists, returns true if so
    private void revokeAdminStatus(String email){
        // tells user that admin exists, if so we remove
        if(adminEmails.toLowerCase().contains(email.toLowerCase())){
            adminEmails = adminEmails.replace(email, "");
            db.writeToRevokedAdminList(adminEmails);
            Toast.makeText(getApplicationContext(), "User's admin status removed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Admin doesn't exist", Toast.LENGTH_SHORT).show();
        }
    }
}