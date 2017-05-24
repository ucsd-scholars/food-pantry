package ucsd_scholars.tritonfoodpantry;

/**
 * Created by dillonliu on 5/24/17.
 */

public class Story {
    public String title;
    public String story;

    public Story(String t, String s){
        title = t;
        story = s;
    }

    @Override
    public String toString(){
        return "Title: " + title + "\n" + "Body: " + story;
    }
}
