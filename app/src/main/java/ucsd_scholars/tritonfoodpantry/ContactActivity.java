package ucsd_scholars.tritonfoodpantry;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        TextView link = (TextView) findViewById(R.id.link);
        link.setClickable(true);
        link.setMovementMethod(LinkMovementMethod.getInstance());

        TextView phoneNumber = (TextView) findViewById(R.id.phone_number);
        phoneNumber.setClickable(true);
        phoneNumber.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
