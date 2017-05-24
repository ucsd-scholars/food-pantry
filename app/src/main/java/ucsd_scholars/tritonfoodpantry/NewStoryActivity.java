package ucsd_scholars.tritonfoodpantry;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static ucsd_scholars.tritonfoodpantry.MainActivity.db;

public class NewStoryActivity extends AppCompatActivity {

    private EditText eventTitle;
    private EditText eventText;
    Button btn;

    static final String ACTION_ADD_STORY = "add new event to home page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_story);

        btn = (Button)findViewById(R.id.button_addNewStory);
        eventTitle = (EditText) findViewById(R.id.eventTitle);
        eventText =  (EditText) findViewById(R.id.storyDetailsText);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewStory();
            }
        });
    }

    // this function adds events/stories to our scrolling home page
    private void addNewStory() {
        // first extract story title and details from editText views
        String eventTitleText = eventTitle.getText().toString();
        String eventDetailsText = eventText.getText().toString();
        if(eventTitleText.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter an event title", Toast.LENGTH_SHORT).show();
            return;
        }
        if(eventDetailsText.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter event details", Toast.LENGTH_SHORT).show();
            return;
        }

        // add the story to the database
        db.writeToStories(eventTitleText, eventDetailsText);

        Toast.makeText(getApplicationContext(), "Story added!", Toast.LENGTH_SHORT).show();

        finish();

        // we send the inputted event title and details to the home page so that the home page can show it
        /*Intent intent=new Intent(NewStoryActivity.this, HomeActivity.class);
        intent.putExtra(HomeFragment.STORY_TITLE_AND_DETAILS, eventTitleText + " " + eventDetailsText);
        intent.setAction(ACTION_ADD_STORY);
        startActivity(intent);*/
    }
}
