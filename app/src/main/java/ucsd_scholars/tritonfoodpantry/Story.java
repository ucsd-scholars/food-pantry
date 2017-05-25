package ucsd_scholars.tritonfoodpantry;

/**
 * Created by dillonliu on 5/24/17.
 */
public class Story{
    public String title;
    public String story;
    public long time;

    // new stories need a new time
    public Story(String t, String s){
        title = t;
        story = s;

        // use calendar time to sort stories by time
        time = System.currentTimeMillis();
    }

    // if story is being retrieved from database, we just use the already-set time
    public Story(String t, String s, long ti){
        title = t;
        story = s;
        time = ti;
    }

    @Override
    public String toString(){
        return "Title: " + title + "\n" + "Body: " + story;
    }
}
