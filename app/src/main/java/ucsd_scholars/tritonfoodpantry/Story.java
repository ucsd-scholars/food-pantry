package ucsd_scholars.tritonfoodpantry;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by dillonliu on 5/24/17.
 */
public class Story{
    public String title;
    public String story;
    public long time;
    public String date;

    // new stories need a new time
    public Story(String t, String s){
        title = t;
        story = s;

        // use calendar time to sort stories by time
        time = System.currentTimeMillis();

        // string of the date in readable format: Jan. 20, 1996     3:30pm
        date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(new Date());
    }

    // if story is being retrieved from database, we just use the already-set time
    public Story(String t, String s, long ti, String d){
        title = t;
        story = s;
        time = ti;
        date = d;
    }

    @Override
    public String toString(){
        return "Title: " + title + "\n" + "Body: " + story;
    }
}
