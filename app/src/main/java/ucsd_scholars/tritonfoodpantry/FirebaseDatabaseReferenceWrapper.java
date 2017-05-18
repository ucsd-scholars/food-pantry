package ucsd_scholars.tritonfoodpantry;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by dillonliu on 5/17/17.
 */

public class FirebaseDatabaseReferenceWrapper {
    private DatabaseReference mDatabase;

    public FirebaseDatabaseReferenceWrapper(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        initPostListener();
    }

    // writes to the database by appending new email to list of admin_emails
    protected void writeNewAdmin(String email) {
        String emailList = mDatabase.child("admin_emails").toString();
        mDatabase.child("admin_emails").setValue(emailList + " " + email);
       //  if mDatabase.child("").child("").toString().contains()
       // String newEmailList = mDatabase.child("admin_emails").toString().replace(email, "");

    }

    // listens for new posts to the news feed
    private void initPostListener(){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Post post = dataSnapshot.getValue(Post.class);
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        //mPostReference.addValueEventListener(postListener);
    }

    // loads a post object from the database; posts are displayed on news feed
    private void writeNewPost(String userId, String username, String title, String body) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("posts").push().getKey();
        Post post = new Post(userId, username, title, body);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }
}
