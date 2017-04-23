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

    public void writeData(Object data){
        DatabaseReference Data = database.getReference("Data");
        Data.setValue(data.toString());
    }

}
