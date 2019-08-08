package blog.globalquality.hikingbasics.leaderboard;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import blog.globalquality.hikingbasics.R;

/**
 * Adapted from example code ud839_CustomAdapter_Example-master
 */

public class LeaderboardEntryAdapter extends ArrayAdapter<LeaderboardEntry> {

    public LeaderboardEntryAdapter(@NonNull Activity context, ArrayList<LeaderboardEntry> leaderboardEntries) {
        super(context, 0, leaderboardEntries);
    }

    private static final String TAG = "LeaderboardEntryAdapter";

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.leaderboard_list_item, parent, false);
        }

        LeaderboardEntry currentLeaderboardEntry = getItem(position);

        TextView nameTextView = listItemView.findViewById(R.id.tvLeaderboardName);

        //Update Leaderboard Name
        String currentLeaderBoardName = null;
        try {
            currentLeaderBoardName = currentLeaderboardEntry.getName();
        } catch (Exception e) {
            Log.e(TAG, "Name set text null value error");
            nameTextView.setText(R.string.no_scores);
        }
        if (null != currentLeaderBoardName) {
            nameTextView.setText(currentLeaderBoardName);
        }

        //Update Leaderboard Score
        TextView numberTextView = listItemView.findViewById(R.id.tvLeaderboardScore);
        String currentLeaderBoardScore = null;
        try {
            numberTextView.setText(currentLeaderBoardScore = currentLeaderboardEntry.getScore().toString());
        } catch (Exception e) {
            Log.e(TAG, "Score set text null value error");
            numberTextView.setText(R.string.zero);
        }
        if (null != currentLeaderBoardScore) {
            numberTextView.setText(currentLeaderBoardScore);
        }

        return listItemView;
    }
}
