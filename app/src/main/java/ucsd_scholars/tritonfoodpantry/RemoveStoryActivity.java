package ucsd_scholars.tritonfoodpantry;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import static ucsd_scholars.tritonfoodpantry.MainActivity.db;

public class RemoveStoryActivity extends AppCompatActivity {

    private static final int STORY_TITLE_SIZE = 16;
    private static final int STORY_BODY_SIZE = 15;
    private static final int BOTTOM_PADDING = 10;
    private static final int HORIZONTAL_PADDING = 7;
    private static final int VERTICAL_PADDING = 8;
    private static final int HEADER_SIZE = 25;
    private static final int HEADER_PADDING = 5;
    private static final int MAX_LINES = 1000;

    private ScrollView scroll;
    private LinearLayout ll;
    LinearLayout.LayoutParams layoutParams;

    // TODO: Rename and change types of parameters
    private String storyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        scroll = (ScrollView) findViewById(R.id.home_scroll);
        ll = (LinearLayout) scroll.findViewById(R.id.notification_layout);
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       /* layoutParams.setMargins(convertToDP(HORIZONTAL_PADDING),convertToDP(VERTICAL_PADDING),
                                convertToDP(HORIZONTAL_PADDING), convertToDP(VERTICAL_PADDING));*/
        layoutParams.setMargins(0, convertToDP(VERTICAL_PADDING), 0, 0);
        updateInbox();
    }

    // this function adds notifications to our scrolling inbox page
    private void addNewStory(Story s) {
        // creates a new textView that will be placed in our scrolling linear layout
        TextView tv = new TextView(this);
        String text = "<b>" + s.title + "</b> <br />" + s.date + "<br /> <br />" + s.story;
        tv.setText(Html.fromHtml(text));
        tv.setLayoutParams(layoutParams);
        tv.setTextSize(STORY_TITLE_SIZE);
        // tv.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv.setBackgroundColor(getResources().getColor(R.color.white));
        tv.setPadding(convertToDP(HORIZONTAL_PADDING),convertToDP(VERTICAL_PADDING / 2),
                convertToDP(HORIZONTAL_PADDING), convertToDP(VERTICAL_PADDING - 2));
        //tv.setBackground(getResources().getDrawable(R.drawable.border));
        tv.setMaxLines(MAX_LINES);
        ll.addView(tv);

        // we recreate the story's hashkey to identify it in the database
        int hash = (s.title+s.story).hashCode();
        final String key = Integer.toString(hash);

        // makes each story clickable so that we can remove it
        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPromptRemove(v, key);
            }
        });
    }

    // loops through all notifications from database
    public void updateInbox(){
        clearInbox();
        for(int i = 0; i < db.stories.size(); i++){
            addNewStory(db.stories.get(i));
        }
    }

    // clears the inbox before updates
    public void clearInbox(){
        if(ll.getChildCount() > 0){
            ll.removeAllViews();
        }

        // re-adds our header
        TextView tv = new TextView(this);
        String text = "<b>" + "Select a Post to Remove" + "</b>";
        tv.setText(Html.fromHtml(text));
        tv.setTextColor(getResources().getColor(R.color.white));
        //tv.setBackground(getResources().getDrawable(R.drawable.border));
        tv.setBackgroundColor(getResources().getColor(R.color.Gold));
        tv.setLayoutParams(layoutParams);
        tv.setTextSize(HEADER_SIZE);
        tv.setGravity(Gravity.CENTER);
        ll.addView(tv);
    }

    // converts pixels to dp
    private int convertToDP(int paddingPixel){
        float density = this.getResources().getDisplayMetrics().density;
        int paddingDp = (int)(paddingPixel * density);
        return paddingDp;
    }

    // asks if admin is sure about removing story
    public void dialogPromptRemove(View view, String k){
        final String key = k;
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Are you sure you want to remove this post from the news feed?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        removeStory(key);
                        finish();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    // removes a story from the database using hashkey to identify it, calling databasewrapper's method
    private void removeStory(String key){
        db.removeStory(key);

        //Toast.makeText(getApplicationContext(), "id of removed post: " + key, Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "Post Removed!", Toast.LENGTH_SHORT).show();
    }
}
