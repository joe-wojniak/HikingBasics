package blog.globalquality.hikingbasics.leaderboard;

/**
 * {@Link LeaderboardEntry} represents a single user's name and score.
 * Each object has 2 properties: name and score.
 */

public class LeaderboardEntry {

    private Object mKey;
    private Object mLeaderName;
    private Object mLeaderScore;

    public LeaderboardEntry() {
    }

    public LeaderboardEntry(Object key, Object name, Object score) {
        mKey = key;
        mLeaderName = name;
        mLeaderScore = score;
    }

    public Object getKey() {
        return mKey;
    }

    public Object getName() {
        return mLeaderName;
    }

    public Object getScore() {
        return mLeaderScore;
    }

}
