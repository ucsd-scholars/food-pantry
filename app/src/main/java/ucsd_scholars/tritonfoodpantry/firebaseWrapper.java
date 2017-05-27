package ucsd_scholars.tritonfoodpantry;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

/**
 * Created by Nihar on 4/14/2017.
 */

public class firebaseWrapper {

    static FirebaseDatabase database;
    private static final String TAG = "firebaseWrapper";
    protected static String adminEmails;
    protected static String userEmails;
    protected String currEmail;
    protected static ArrayList<Story> notifications = new ArrayList<Story>();
    protected static ArrayList<Story> stories = new ArrayList<Story>();

    public firebaseWrapper(){
        database = FirebaseDatabase.getInstance();
    }

    public void addMessage(String msg){
        DatabaseReference Msg = database.getReference("Message");
        Msg.setValue(msg);
    }

    public void readMsg(){
        DatabaseReference Msg = database.getReference("Message");
        // Read from the database
        Msg.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.i(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void readAdminList(){
        DatabaseReference adminList = database.getReference("admin_emails");
        // Read from the database
        adminList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.i(TAG, "Value is: " + value);
                adminEmails = value;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                //adminEmails = "";
            }
        });
    }

    public void readUserList(){
        DatabaseReference userList = database.getReference("user_emails");
        // Read from the database
        userList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.i(TAG, "Value is: " + value);
                userEmails = value;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                //userEmails = "";
            }
        });
    }

    public void writeToNotifications(String title, String body){
        DatabaseReference notifications = database.getReference("notifications");

        int hash = (title+body).hashCode();
        String key = Integer.toString(hash);

        notifications.child(key).setValue(new Story(title, body));
    }

    public void writeToStories(String title, String body){
        DatabaseReference stories = database.getReference("stories");

        int hash = (title+body).hashCode();
        String key = Integer.toString(hash);

        stories.child(key).setValue(new Story(title, body));
    }

    // writes to the database by appending new email to list of admin_emails
    public void writeToAdminList(String email){
        // null check
        if(adminEmails == null){
            return;
        }

        // we don't add admin email again if already in database
        if(adminEmails != null && adminEmails.toLowerCase().contains(email.toLowerCase())){
            return;
        }

        DatabaseReference ref = database.getReference("admin_emails");
        ref.setValue(adminEmails + " " + email);
    }

    // we use this method when we revoke an admin
    public void writeToRevokedAdminList(String email){
        // null check
        if(adminEmails == null){
            return;
        }

        DatabaseReference ref = database.getReference("admin_emails");
        ref.setValue(adminEmails);
    }

    // writes to the database by appending new email to list of user emails
    public void writeToEmailList(String email){
        // null check
        if(userEmails == null){
            return;
        }

        // we don't add new user email again if already in database
        if(userEmails != null && userEmails.toLowerCase().contains(email.toLowerCase())){
            return;
        }

        DatabaseReference ref = database.getReference("user_emails");
        ref.setValue(userEmails + " " + email);
    }

    public void setCurrEmail(String email){
        currEmail = email;
    }

    public void readNotifications(){
        DatabaseReference notifications = database.getReference("notifications");
        // Read from the database
        notifications.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //Get map of notifications in datasnapshot
                if(dataSnapshot.exists()){
                    addToRecentNotifications((Map<String,Object>) dataSnapshot.getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void readStories(){
        DatabaseReference stories = database.getReference("stories");
        // Read from the database
        stories.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //Get map of stories in datasnapshot
                if(dataSnapshot.exists()) {
                    addToStories((Map<String, Object>) dataSnapshot.getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    // adds a notification to the arraylist
    protected static void addToRecentNotifications(Map<String,Object> map){
        //iterate through each story
        notifications.clear();

        for (Map.Entry<String, Object> entry : map.entrySet()){

            //Get user map
            Map singleStory = (Map) entry.getValue();
            //Get story and append to list
            Story story = new Story((String) singleStory.get("title"), (String) singleStory.get("story"),
                                    (long) singleStory.get("time"), (String) singleStory.get("date"));
            notifications.add((Story) story);

            Log.d("firebasewrapper", singleStory.toString());
        }

        // Sorting our notifications by time, should put oldest/highest time stories first
        Collections.sort(notifications, new OrderByTime());

        // for testing sort
        for(int i = 0; i < notifications.size(); i++){
            System.out.println(notifications.get(i).title);
        }
    }

    // adds a story to the arraylist
    protected static void addToStories(Map<String,Object> map){
        stories.clear();

        for (Map.Entry<String, Object> entry : map.entrySet()){

            //Get user map
            Map singleStory = (Map) entry.getValue();
            //Get story and append to list
            Story story = new Story((String) singleStory.get("title"), (String) singleStory.get("story"),
                                    (long) singleStory.get("time"), (String) singleStory.get("date"));
            stories.add((Story) story);

            Log.d("firebasewrapper", singleStory.toString());
        }

        // Sorting our notifications by time, should put oldest/highest time stories first
        Collections.sort(stories, new OrderByTime());

        // for testing sort
        for(int i = 0; i < stories.size(); i++){
            System.out.println(stories.get(i).title);
        }
    }

    // should be used to sort stories by latest time, so newest stories are shown first
    static class OrderByTime implements Comparator<Story>
    {
        @Override
        public int compare(Story s1, Story s2)
        {
            int t1 = (int) s1.time;
            int t2 = (int) s2.time;
            return t2 - t1;
        }
    }
}
