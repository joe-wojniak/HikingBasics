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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import blog.globalquality.hikingbasics.QuizActivity;
import blog.globalquality.hikingbasics.R;

public class FragmentDemoActivity extends YouTubeFailureRecoveryActivity {

    private static final String TAG = "FragmentDemoActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragments_demo);

        // Setup YouTube Player Fragment
        YouTubePlayerFragment youTubePlayerFragment =
                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
        youTubePlayerFragment.initialize(DeveloperKey.DEVELOPER_KEY, this);
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

        final Button buttonStartQuiz = findViewById(R.id.buttonStartQuiz);
        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    final String quiz = extras.getString("quiz");
        // start Quiz Activity
                    Intent i = new Intent(FragmentDemoActivity.this, QuizActivity.class);
                    i.putExtra("quiz", quiz);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
    }
}
