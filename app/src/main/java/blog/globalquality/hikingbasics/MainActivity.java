package blog.globalquality.hikingbasics;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import blog.globalquality.hikingbasics.authenticateUser.EmailPasswordActivity;
import blog.globalquality.hikingbasics.leaderboard.LeaderboardEntry;
import blog.globalquality.hikingbasics.leaderboard.LeaderboardEntryAdapter;
import blog.globalquality.hikingbasics.youTube.FragmentDemoActivity;
import blog.globalquality.hikingbasics.youTube.VideoWallDemoActivity;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    // Firebase instance variables
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Main Activity
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");

        // Initialize Firebase components
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference().child("leaderboard");
        mAuth = FirebaseAuth.getInstance();

        // Initialize leaderboard ListView and its adapter
        ArrayList<LeaderboardEntry> leaderboardEntries = new ArrayList<>();
        LeaderboardEntryAdapter entryAdapter = new LeaderboardEntryAdapter(MainActivity.this, leaderboardEntries);
        ListView listView = findViewById(R.id.lvLeaderboard);
        listView.setAdapter(entryAdapter);

        // Start User sign-in activity
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (null != user) {
            Toast.makeText(this, getString(R.string.user_logged_in, user), Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(MainActivity.this, EmailPasswordActivity.class);
            startActivity(i);
        }

        mDatabaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Object key = postSnapshot.child("key").getValue();
                    Object name = postSnapshot.child("name").getValue();
                    Object score = postSnapshot.child("score").getValue();
                    LeaderboardEntry leaderboardEntry = new LeaderboardEntry(key, name, score);
                    entryAdapter.add(leaderboardEntry);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.e(TAG, "Failed to read value.", databaseError.toException());
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_sign_out:
                Intent i = new Intent(MainActivity.this, EmailPasswordActivity.class);
                startActivity(i);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks.
        int id = item.getItemId();

        /* Stackoverflow code snippet used-
           https://stackoverflow.com/a/36463784/8811523
        */

        switch (id) {
            case R.id.nav_home:
                break;
            case R.id.nav_intro:
                Intent i = new Intent(MainActivity.this, FragmentDemoActivity.class);
                i.putExtra("videoId", "LoidFvJpZ1g");
                i.putExtra("videoStart", 5000);
                i.putExtra("quiz", "QuizSG");
                startActivity(i);
                break;
            case R.id.nav_avalanche:
                i = new Intent(MainActivity.this, FragmentDemoActivity.class);
                i.putExtra("videoId", "QylgHwF3tkk");
                i.putExtra("videoStart", 12000);
                i.putExtra("quiz", "QuizAv");
                startActivity(i);
                break;
            case R.id.nav_10Essentials:
                i = new Intent(MainActivity.this, FragmentDemoActivity.class);
                i.putExtra("videoId", "tvIH-5B576w");
                i.putExtra("videoStart", 12000);
                i.putExtra("quiz", "QuizTenEssentials");
                startActivity(i);
                break;
            case R.id.nav_Navigation:
                i = new Intent(MainActivity.this, FragmentDemoActivity.class);
                i.putExtra("videoId", "Ba67vHfxqxY");
                i.putExtra("videoStart", 12000);
                i.putExtra("quiz", "QuizNav");
                startActivity(i);
                break;
            case R.id.nav_LNT:
                i = new Intent(MainActivity.this, FragmentDemoActivity.class);
                i.putExtra("videoId", "wf77YuQ_1ow");
                i.putExtra("videoStart", 9000);
                i.putExtra("quiz", "QuizLeaveNoTrace");
                startActivity(i);
                break;
            case R.id.nav_YouTubeWall:
                i = new Intent(MainActivity.this, VideoWallDemoActivity.class);
                i.putExtra("playlistID", "PLm-g0Ce0XFfEyR9MEE2maV5oiZyJc1ocE");
                startActivity(i);
                break;
            case R.id.nav_YouTubeBestPlaces:
                i = new Intent(MainActivity.this, VideoWallDemoActivity.class);
                i.putExtra("playlistID", "PLm-g0Ce0XFfEp7o3VVyakIgwaZSLg8EfF");
                startActivity(i);
                break;
            default:
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");

        // End Quiz Activity
        final Button button = findViewById(R.id.buttonEndQuiz);
        Intent j = new Intent(MainActivity.this, EmailPasswordActivity.class);
        button.setOnClickListener(v ->
                startActivity(j));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");

        // End Quiz Activity
        final Button button = findViewById(R.id.buttonEndQuiz);
        Intent j = new Intent(MainActivity.this, EmailPasswordActivity.class);
        button.setOnClickListener(v ->
                startActivity(j));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");

    }
}

