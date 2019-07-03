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
import android.view.View;
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

    // Quiz Questions
    private TextView mQuestion1;
    private TextView mQuestion2;
    private TextView mQuestion3;
    private TextView mQuestion4;
    private TextView mQuestion5;

    // Firebase instance variables
    private FirebaseDatabase mDatabase;
    private DatabaseReference mQuizDatabaseReference;
    private DatabaseReference mUsersDatabaseReference;
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
        mAuth = FirebaseAuth.getInstance();
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

        String quiz = null; // QuizQuestionAnswerScore database reference

        if (!wasRestored) {

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                quiz = extras.getString("quiz");
            }
            quiz(quiz);
        }
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
    }

    public void onClickClose(View view) {
        finish();
    }

    public void quiz(String quiz) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (null != user)
            Toast.makeText(this, "User logged in: " + user.getDisplayName(), Toast.LENGTH_SHORT).show();

        // TODO code snippet reference: https://stackoverflow.com/a/42204089/8811523

        mQuizDatabaseReference = mDatabase.getReference().child(quiz);
        mUsersDatabaseReference = mDatabase.getReference().child("Users");

        mQuestion1 = findViewById(R.id.fragmentQ1);
        mQuestion2 = findViewById(R.id.fragmentQ2);
        mQuestion3 = findViewById(R.id.fragmentQ3);
        mQuestion4 = findViewById(R.id.fragmentQ4);
        mQuestion5 = findViewById(R.id.fragmentQ5);

        //TODO
        // populate quiz questions from database
        // TODO code snippet reference: https://stackoverflow.com/a/42204089/8811523
        mQuizDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String question1 = (String) dataSnapshot.getValue();


                mQuestion1.setText("Question1");
                mQuestion2.setText("Question2");
                mQuestion3.setText("Question3");
                mQuestion4.setText("Question4");
                mQuestion5.setText("Question5");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            // TODO onCancelled
            }
        });

    }

    public void checkQuiz(String quiz){
        mQuizDatabaseReference = mDatabase.getReference().child(quiz);
        mUsersDatabaseReference = mDatabase.getReference().child("Users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //TODO
        // check answers
        Integer score = 0;
        storeScore(user, quiz,score);
    }

    public void storeScore(FirebaseUser user, String quiz, Integer score){
        mQuizDatabaseReference = mDatabase.getReference().child(quiz);
        mUsersDatabaseReference = mDatabase.getReference().child("Users");

        //TODO
        // store score
    }
}
