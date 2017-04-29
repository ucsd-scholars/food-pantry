package ucsd_scholars.tritonfoodpantry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseWrapper db = new firebaseWrapper();
        db.addMessage("Database!");
        db.readMsg();

        // if we decided to exit from the login screen, then this will close the app
        if (getIntent().getBooleanExtra("EXIT", false))
        {
            finish();
        }

    }

    public void onClickLogin(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onClickSignUp(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
