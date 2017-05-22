package ucsd_scholars.tritonfoodpantry;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static ucsd_scholars.tritonfoodpantry.MainActivity.mAuth;
import static ucsd_scholars.tritonfoodpantry.MainActivity.settingsEditor;

/**
 * Created by Nihar on 4/14/2017.
 */

public class firebaseWrapper {

    static FirebaseDatabase database;
    private static final String TAG = "firebaseWrapper";
    protected static String adminEmails;
    protected static String userEmails;
    protected String currEmail;

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

    public void checkAdminList(){
        DatabaseReference adminList = database.getReference("users").child(mAuth.getCurrentUser().getUid()).child("isAdmin");
        // Read from the database
        adminList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                if(value != null){
                    //Log.i(TAG, "Value is: " + value);
                    settingsEditor.putBoolean("isAdmin", true);
                    settingsEditor.commit();
                }
                else{
                    //Log.i(TAG, "Value is: " + value);
                    settingsEditor.putBoolean("isAdmin", false);
                    settingsEditor.commit();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                //adminEmails = "";
            }
        });
    }
    public void writeData(Object data){
        DatabaseReference Data = database.getReference("Data");
        Data.setValue(data.toString());
    }

    // writes to the database by appending new email to list of admin_emails
    public void writeToAdminList(String email){
        // we don't add admin email again if already in database
        /*if(adminEmails != null && adminEmails.toLowerCase().contains(email.toLowerCase())){
            return;
        }*/

       /* DatabaseReference ref = database.getReference("admin_emails");
        ref.setValue(adminEmails + " " + email);
*/
        //Store data in database
        DatabaseReference usersRef = database.getReference().child("users");
        Map<String, String> userData = new HashMap<String, String>();

        //userData.put("uid", mAuth.getCurrentUser().getUid());
        userData.put("email", email);
        userData.put("isAdmin", "true");

        usersRef.child(mAuth.getCurrentUser().getUid()).setValue(userData);
    }

    // we use this method when we revoke an admin
    public void writeToRevokedAdminList(String email){
        /*DatabaseReference ref = database.getReference("admin_emails");
        ref.setValue(adminEmails);*/

        //Store data in database
        DatabaseReference usersRef = database.getReference().child("users");
        Map<String, String> userData = new HashMap<String, String>();

        //userData.put("uid", mAuth.getCurrentUser().getUid());
        userData.put("email", email);

        usersRef = usersRef.child(mAuth.getCurrentUser().getUid());
        usersRef.setValue(userData);
        //userData.remove("isAdmin");
    }

    // writes to the database by appending new email to list of user emails
    public void writeToEmailList(String email){
        // we don't add new user email again if already in database
        /*if(userEmails != null && userEmails.toLowerCase().contains(email.toLowerCase())){
            return;
        }*/

        /*DatabaseReference ref = database.getReference("user_emails");
        ref.setValue(userEmails + " " + email);*/

        //Store data in database
        DatabaseReference usersRef = database.getReference().child("users");
        Map<String, String> userData = new HashMap<String, String>();

        //userData.put("uid", mAuth.getCurrentUser().getUid());
        userData.put("email", email);
        // userData.put("isAdmin", "false");

        usersRef.child(mAuth.getCurrentUser().getUid()).setValue(userData);
    }

    public void setCurrEmail(String email){
        currEmail = email;
    }
}
