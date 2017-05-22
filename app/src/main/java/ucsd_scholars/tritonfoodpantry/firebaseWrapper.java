package ucsd_scholars.tritonfoodpantry;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public void writeData(Object data){
        DatabaseReference Data = database.getReference("Data");
        Data.setValue(data.toString());
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
}
