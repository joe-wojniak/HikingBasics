package blog.globalquality.hikingbasics;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

        //Populate quiz questions from database

        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Object score_temp = dataSnapshot.child("users").child(userId).child("score").getValue();
                if (score_temp != null) {
                    mScore.setText(score_temp.toString());
                }

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ArrayList<Object> questionList = new ArrayList<>();
                    questionList.add(postSnapshot.child(quiz).child("Question1").getValue());
                    questionList.add(postSnapshot.child(quiz).child("Question2").getValue());
                    questionList.add(postSnapshot.child(quiz).child("Question3").getValue());
                    questionList.add(postSnapshot.child(quiz).child("Question4").getValue());
                    questionList.add(postSnapshot.child(quiz).child("Question5").getValue());

                    int i = 0;
                    int max = 4;
                    while (i < max) {
                        if (null != questionList.get(i)) {
                            switch (i) {
                                case 0:
                                    mQuestion1.setVisibility(View.VISIBLE);
                                    mResponse1.setVisibility(View.VISIBLE);
                                    mQuestion1.setText(questionList.get(i).toString());
                                    break;
                                case 1:
                                    mQuestion2.setVisibility(View.VISIBLE);
                                    mResponse2.setVisibility(View.VISIBLE);
                                    mQuestion2.setText(questionList.get(i).toString());
                                    break;
                                case 2:
                                    mQuestion3.setVisibility(View.VISIBLE);
                                    mResponse3.setVisibility(View.VISIBLE);
                                    mQuestion3.setText(questionList.get(i).toString());
                                    break;
                                case 3:
                                    mQuestion4.setVisibility(View.VISIBLE);
                                    mResponse4.setVisibility(View.VISIBLE);
                                    mQuestion4.setText(questionList.get(i).toString());
                                    break;
                                case 4:
                                    mQuestion5.setVisibility(View.VISIBLE);
                                    mResponse5.setVisibility(View.VISIBLE);
                                    mQuestion5.setText(questionList.get(i).toString());
                                    break;
                                default:
                                    break;
                            }
                        }
                        i++; // increment counter in the while loop
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.e(TAG, "Failed to read value.", databaseError.toException());
            }
        });

    }

    public void checkQuiz(String quiz) {
        // Check responses
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();
        String userEmail = user.getEmail();

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            Integer score = 0;

            // The correct Answers to Questions
            Object answer1 = null;
            Object answer2 = null;
            Object answer3 = null;
            Object answer4 = null;
            Object answer5 = null;
            // User's Responses to Questions
            Object response1 = null;
            Object response2 = null;
            Object response3 = null;
            Object response4 = null;
            Object response5 = null;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                answer1 = dataSnapshot.child("Quiz").child(quiz).child("Answer1").getValue();
                answer2 = dataSnapshot.child("Quiz").child(quiz).child("Answer2").getValue();
                answer3 = dataSnapshot.child("Quiz").child(quiz).child("Answer3").getValue();
                answer4 = dataSnapshot.child("Quiz").child(quiz).child("Answer4").getValue();
                answer5 = dataSnapshot.child("Quiz").child(quiz).child("Answer5").getValue();

                List<Object> answerList = new ArrayList<>(Arrays.asList(answer1, answer2, answer3,
                        answer4, answer5));

                Object score_temp = dataSnapshot.child("users").child(userId).child("score").getValue();
                if (score_temp != null) {
                    mScore.setText(score_temp.toString());
                    score = Integer.parseInt(score_temp.toString());
                }

                response1 = dataSnapshot.child("users").child(userId).child(quiz).child("response0").getValue();
                response2 = dataSnapshot.child("users").child(userId).child(quiz).child("response1").getValue();
                response3 = dataSnapshot.child("users").child(userId).child(quiz).child("response2").getValue();
                response4 = dataSnapshot.child("users").child(userId).child(quiz).child("response3").getValue();
                response5 = dataSnapshot.child("users").child(userId).child(quiz).child("response4").getValue();

                List<Object> responseList = new ArrayList<>(Arrays.asList(response1, response2, response3,
                        response4, response5));
                List<Integer> new_try = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0));

                for (int i = 0; i < 5; ) {
                    switch (i) {
                        case 0:
                            if (null == responseList.get(i)) {
                                new_try.add(i, 1);
                                responseList.remove(i);
                                responseList.add(i, mResponse1.getText().toString());
                            }
                            break;
                        case 1:
                            if (null == responseList.get(i)) {
                                new_try.add(i, 1);
                                responseList.remove(i);
                                responseList.add(i, mResponse2.getText().toString());
                            }
                            break;
                        case 2:
                            if (null == responseList.get(i)) {
                                new_try.add(i, 1);
                                responseList.remove(i);
                                responseList.add(i, mResponse3.getText().toString());
                            }
                            break;
                        case 3:
                            if (null == responseList.get(i)) {
                                new_try.add(i, 1);
                                responseList.remove(i);
                                responseList.add(i, mResponse4.getText().toString());
                            }
                            break;
                        case 4:
                            if (null == responseList.get(i)) {
                                new_try.add(i, 1);
                                responseList.remove(i);
                                responseList.add(i, mResponse5.getText().toString());
                            }
                            break;
                        default:
                            break;
                    }
                    i++;
                }

                int max = 0; // max number of answers

                for (int i = 0; i < answerList.size(); ) {
                    if (null != answerList.get(i)) {
                        max++;
                    }
                    i++;
                }

                for (int i = 0; i < max; ) {
                    if (new_try.get(i) == 1) {
                        if (responseList.get(i).toString().toLowerCase().contains(answerList.get(i).toString().toLowerCase())) {
                            score++;
                        }
                    }
                    i++;
                }

                mScore.setText(score.toString()); // update displayed score

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

                if (null != userName) {
                    mDatabaseReference.child("users").child(userId).child("name").setValue(userName);
                    mDatabaseReference.child("leaderboard").child("name").setValue(userName);
                }

                if (null != score) {
                    mDatabaseReference.child("users").child(userId).child("score").setValue(score);
                    mDatabaseReference.child("leaderboard").child("score").setValue(score);
                }

                Map<String, Object> userResponses = new TreeMap<>();
                for (int i = 0; i < max; ) {
                    if (responseList.get(i).toString().toLowerCase().contains(answerList.get(i).toString().toLowerCase())) {
                        userResponses.put("/users/" + userId + "/" + quiz + "/response" + i + "/", responseList.get(i));
                    }
                    i++;
                }
                mDatabaseReference.updateChildren(userResponses);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.e(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }
}