package ucsd_scholars.tritonfoodpantry;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static ucsd_scholars.tritonfoodpantry.MainActivity.db;

public class AddAdminActivity extends AppCompatActivity {

    private EditText user;
    Button grant;

    boolean emailExists = false;

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

        // if valid user email, we write the email to our database of admin emails
        if(user.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter a valid user email", Toast.LENGTH_SHORT).show();
        }
        else {
            grantAdminStatus(user.getText().toString());
        }
    }

    // checks if email exists, returns true if so
    private void grantAdminStatus(String email){
            // tells user that admin already exists
           if(firebaseWrapper.adminEmails.toLowerCase().contains(email.toLowerCase())){
               Toast.makeText(getApplicationContext(), "User already has admin status", Toast.LENGTH_SHORT).show();
           }
           //
           else if(!firebaseWrapper.userEmails.toLowerCase().contains(email.toLowerCase())){
               Toast.makeText(getApplicationContext(), "No user under this email address", Toast.LENGTH_SHORT).show();
           }
           else {
               db.writeToAdminList(user.getText().toString());
               Toast.makeText(getApplicationContext(), "Admin added!", Toast.LENGTH_SHORT).show();
           }
    }
}