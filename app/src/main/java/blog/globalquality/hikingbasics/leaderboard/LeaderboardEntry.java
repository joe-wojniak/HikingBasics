package blog.globalquality.hikingbasics.leaderboard;

/**
 * {@Link LeaderboardEntry} represents a single user's name and score.
 * Each object has 2 properties: name and score.
 */

public class LeaderboardEntry {

    public String mLeaderName;
    public Integer mLeaderScore;

    public LeaderboardEntry() {
    }

    public LeaderboardEntry(String name, Integer score) {
        mLeaderName = name;
        mLeaderScore = score;
    }

    public String getName() {
        return mLeaderName;
    }

    public Integer getScore() {
        return mLeaderScore;
    }

}
