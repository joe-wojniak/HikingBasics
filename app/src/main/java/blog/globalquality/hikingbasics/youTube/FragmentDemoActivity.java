/*
 * Copyright 2012 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package blog.globalquality.hikingbasics.youTube;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import blog.globalquality.hikingbasics.R;

public class FragmentDemoActivity extends YouTubeFailureRecoveryActivity {

    private static final String TAG = "FragmentDemoActivity";

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

    // Firebase instance variables
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragments_demo);

        // Setup YouTube Player Fragment
        YouTubePlayerFragment youTubePlayerFragment =
                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
        youTubePlayerFragment.initialize(DeveloperKey.DEVELOPER_KEY, this);

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

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        String videoId = null; // YouTube video ID
        int videoStart = 0;     // point at which to start the video, in milliseconds

        if (!wasRestored) {

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                videoId = extras.getString("videoId");
                videoStart = extras.getInt("videoStart", 0);
            }

            player.cueVideo(videoId, videoStart);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (null != user)
            Toast.makeText(this, "User logged in: " + user, Toast.LENGTH_SHORT).show();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final String quiz = extras.getString("quiz");
            quiz(quiz);
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

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
    }

    public void onClickClose(View view) {
        finish();
    }

    public void quiz(String quiz) {

        //TODO
        // populate quiz questions from database
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String question1 = (String) postSnapshot.child(quiz).child("Question1").getValue();
                    /*String question2 = (String) postSnapshot.child(quiz).child("Question2").getValue();
                    String question3 = (String) postSnapshot.child(quiz).child("Question3").getValue();
                    String question4 = (String) postSnapshot.child(quiz).child("Question4").getValue();
                    String question5 = (String) postSnapshot.child(quiz).child("Question5").getValue();*/

                    mQuestion1.setText(question1);
                    /*mQuestion2.setText(question2);
                     mQuestion3.setText(question3);
                    mQuestion4.setText(question4);
                    mQuestion5.setText(question5);*/
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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            String answer1 = null;
            String response1;
            Integer score = 0;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String userId = user.getUid();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    answer1 = (String) postSnapshot.child(quiz).child("Answer1").getValue();
                    /*String answer2 = (String) postSnapshot.child(quiz).child("Answer2").getValue();
                    String answer3 = (String) postSnapshot.child(quiz).child("Answer3").getValue();
                    String answer4 = (String) postSnapshot.child(quiz).child("Answer4").getValue();
                    String answer5 = (String) postSnapshot.child(quiz).child("Answer5").getValue();*/
                    score = (Integer) postSnapshot.child("users").child(userId).getValue();
                }

                response1 = mResponse1.getText().toString();
                if (answer1.equalsIgnoreCase(response1)) {
                    score++;
                    storeUserScore(userId, score);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", databaseError.toException());
            }

            private void storeUserScore(String userId, Integer score) {
                mDatabaseReference.child("users").child(userId).setValue(score);
            }
        });
    }

}
