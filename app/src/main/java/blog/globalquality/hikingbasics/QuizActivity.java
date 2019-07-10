package blog.globalquality.hikingbasics;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";

    // Quiz Questions
    private TextView mQuestion1;
    private TextView mQuestion2;
    private TextView mQuestion3;
    private TextView mQuestion4;
    private TextView mQuestion5;

    // Quiz Responses
    private EditText mResponse1;
    private EditText mResponse2;
    private EditText mResponse3;
    private EditText mResponse4;
    private EditText mResponse5;

    // Quiz Score
    private TextView mUserScoreTitle;
    private TextView mScore;

    // Firebase instance variables
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz);

        // Initialize Firebase components
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();

        // Setup variables to display Questions
        mQuestion1 = findViewById(R.id.fragmentQ1);
        mQuestion2 = findViewById(R.id.fragmentQ2);
        mQuestion3 = findViewById(R.id.fragmentQ3);
        mQuestion4 = findViewById(R.id.fragmentQ4);
        mQuestion5 = findViewById(R.id.fragmentQ5);

        // Setup variables to get Responses
        mResponse1 = findViewById(R.id.fragmentA1);
        mResponse2 = findViewById(R.id.fragmentA2);
        mResponse3 = findViewById(R.id.fragmentA3);
        mResponse4 = findViewById(R.id.fragmentA4);
        mResponse5 = findViewById(R.id.fragmentA5);

        mUserScoreTitle = findViewById(R.id.tvUserScoreTitle);
        mScore = findViewById(R.id.tvUserScore);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final String quiz = extras.getString("quiz");
            quiz(quiz);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (null != user) {
            Toast.makeText(this, "User logged in: " + user, Toast.LENGTH_SHORT).show();
        }

        final Button buttonScoreQuiz = findViewById(R.id.buttonScoreQuiz);
        buttonScoreQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    final String quiz = extras.getString("quiz");
                    checkQuiz(quiz);
                }
            }
        });
    }

    public void quiz(String quiz) {

        //TODO populate quiz questions from database

        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            Integer score;
            Long score_temp;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String question1 = (String) postSnapshot.child(quiz).child("Question1").getValue();
                    String question2 = (String) postSnapshot.child(quiz).child("Question2").getValue();
                    String question3 = (String) postSnapshot.child(quiz).child("Question3").getValue();
                    String question4 = (String) postSnapshot.child(quiz).child("Question4").getValue();
                    String question5 = (String) postSnapshot.child(quiz).child("Question5").getValue();

                    mQuestion1.setText(question1);
                    mQuestion2.setText(question2);
                    mQuestion3.setText(question3);
                    mQuestion4.setText(question4);
                    mQuestion5.setText(question5);

                    score_temp = (Long) dataSnapshot.child("users").child(userId).child("score").getValue();
                    if (score_temp != null) {
                        score = score_temp.intValue();
                        mScore.setText(score.toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", databaseError.toException());
            }
        });

    }

    public void checkQuiz(String quiz) {
        // TODO check responses
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();
        String userEmail = user.getEmail();

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            Integer score = 0;
            Long score_temp;
            // The correct Answers to Questions
            String answer1 = null;
            String answer2 = null;
            String answer3 = null;
            String answer4 = null;
            String answer5 = null;
            // User's Responses to Questions
            String response1 = null;
            String response2 = null;
            String response3 = null;
            String response4 = null;
            String response5 = null;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                answer1 = (String) dataSnapshot.child("Quiz").child(quiz).child("Answer1").getValue();
                answer2 = (String) dataSnapshot.child("Quiz").child(quiz).child("Answer2").getValue();
                answer3 = (String) dataSnapshot.child("Quiz").child(quiz).child("Answer3").getValue();
                answer4 = (String) dataSnapshot.child("Quiz").child(quiz).child("Answer4").getValue();
                answer5 = (String) dataSnapshot.child("Quiz").child(quiz).child("Answer5").getValue();

                List<String> answerList = new ArrayList<>(Arrays.asList(answer1, answer2, answer3,
                        answer4, answer5));

                score_temp = (Long) dataSnapshot.child("users").child(userId).child("score").getValue();
                if (score_temp != null) {
                    score = score_temp.intValue();
                    mScore.setText(score.toString());
                }

                response1 = (String) dataSnapshot.child("users").child(userId).child("response1").getValue();
                response2 = (String) dataSnapshot.child("users").child(userId).child("response2").getValue();
                response3 = (String) dataSnapshot.child("users").child(userId).child("response3").getValue();
                response4 = (String) dataSnapshot.child("users").child(userId).child("response4").getValue();
                response5 = (String) dataSnapshot.child("users").child(userId).child("response5").getValue();

                List<String> responseList = new ArrayList<>(Arrays.asList(response1, response2, response3,
                        response4, response5));

                for (int i = 0; i < responseList.size(); ++i) {
                    if (null == responseList.get(i)) {
                        switch (i) {
                            case 0:
                                responseList.add(i, mResponse1.getText().toString());
                            case 1:
                                responseList.add(i, mResponse2.getText().toString());
                            case 2:
                                responseList.add(i, mResponse3.getText().toString());
                            case 3:
                                responseList.add(i, mResponse4.getText().toString());
                            case 4:
                                responseList.add(i, mResponse5.getText().toString());
                        }
                    } else if (answerList.get(i).equalsIgnoreCase(responseList.get(i))) {
                        ++score;
                    }
                }

                String userName = user.getDisplayName();

                if (null == userName) {
                    // [Make userName if DisplayName doesn't exist]
                    if (null != userEmail) {
                        int index = userEmail.indexOf("@");
                        if (index != -1) {
                            userName = userEmail.substring(0, index);
                        }
                    }
                }

                mScore.setText(score.toString()); // update displayed score

                String key = mDatabaseReference.child(userId).push().getKey();
                mDatabaseReference.child("users").child(userId).child("name").setValue(userName);
                mDatabaseReference.child("users").child(userId).child("/score/" + key).setValue(score);

                Map<String, Object> userResponses = new HashMap<>();
                for (int i = 0; i < responseList.size(); ++i) {
                    userResponses.put("/users/" + userId + "/response/" + key + i, responseList.get(i));
                }
                mDatabaseReference.updateChildren(userResponses);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", databaseError.toException());
            }
        });
    }
}