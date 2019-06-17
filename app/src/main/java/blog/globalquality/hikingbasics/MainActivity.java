package blog.globalquality.hikingbasics;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this); // binds butterknife to MainActivity
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
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
                i.putExtra("videoId","LoidFvJpZ1g");
                i.putExtra("videoStart",5000);
                startActivity(i);
                break;
            case R.id.nav_avalanche:
                i = new Intent(MainActivity.this, FragmentDemoActivity.class);
                i.putExtra("videoId","QylgHwF3tkk");
                i.putExtra("videoStart",12000);
                startActivity(i);
                startActivity(i);
                break;
            case R.id.nav_10Essentials:
                i = new Intent(MainActivity.this, FragmentDemoActivity.class);
                i.putExtra("videoId","tvIH-5B576w");
                i.putExtra("videoStart",12000);
                startActivity(i);
                break;
            case R.id.nav_Navigation:
                i = new Intent(MainActivity.this, FragmentDemoActivity.class);
                i.putExtra("videoId","Ba67vHfxqxY");
                i.putExtra("videoStart",12000);
                startActivity(i);
                break;
            case R.id.nav_LNT:
                i = new Intent(MainActivity.this, FragmentDemoActivity.class);
                i.putExtra("videoId","wf77YuQ_1ow");
                i.putExtra("videoStart",9000);
                startActivity(i);
                break;
            case R.id.nav_YouTubeWall:
                i = new Intent(MainActivity.this, VideoWallDemoActivity.class);
                i.putExtra("playlistID","PLm-g0Ce0XFfEyR9MEE2maV5oiZyJc1ocE");
                startActivity(i);
                break;
            case R.id.nav_YouTubeBestPlaces:
                i = new Intent(MainActivity.this, VideoWallDemoActivity.class);
                i.putExtra("playlistID","PLm-g0Ce0XFfEp7o3VVyakIgwaZSLg8EfF");
                startActivity(i);
                break;
            default:
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

