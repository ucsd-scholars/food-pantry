package ucsd_scholars.tritonfoodpantry;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class InboxActivity extends AppCompatActivity {

    private static final int STORY_TITLE_SIZE = 30;
    private static final int STORY_BODY_SIZE = 15;
    private static final int BOTTOM_PADDING = 10;
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
        layoutParams.setMargins(convertToDP(HEADER_PADDING),convertToDP(HEADER_PADDING),
                                convertToDP(HEADER_PADDING), convertToDP(HEADER_PADDING));
        updateInbox();
    }

    // this function adds notifications to our scrolling inbox page
    private void addNewStory(Story s) {
        // creates a new textView that will be placed in our scrolling linear layout
        TextView tv = new TextView(this);
        String text = "<b>" + s.title + "</b> <br />" + s.story;
        tv.setText(Html.fromHtml(text));
        tv.setLayoutParams(layoutParams);
        tv.setTextSize(STORY_TITLE_SIZE);
        tv.setTextColor(getResources().getColor(R.color.white));
        tv.setBackgroundColor(getResources().getColor(R.color.DeepSkyBlue));
        tv.setMaxLines(MAX_LINES);
        ll.addView(tv);

        /*TextView tv2 = new TextView(this);
        tv2.setText(s.story);
        tv2.setLayoutParams(layoutParams);
        tv2.setTextSize(STORY_BODY_SIZE);
        // tv2.setPadding(0,0,0,5);
        tv2.setBottom(BOTTOM_PADDING);
        ll.addView(tv2);*/
    }

    // loops through all notifications from database
    public void updateInbox(){
        clearInbox();
        for(int i = 0; i < MainActivity.db.notifications.size(); i++){
            addNewStory(MainActivity.db.notifications.get(i));
        }
    }

    // clears the inbox before updates
    public void clearInbox(){
        if(ll.getChildCount() > 0){
            ll.removeAllViews();
        }
    }

    // converts pixels to dp
    private int convertToDP(int paddingPixel){
        float density = this.getResources().getDisplayMetrics().density;
        int paddingDp = (int)(paddingPixel * density);
        return paddingDp;
    }
}
