package blog.globalquality.hikingbasics.leaderboard;

/** {@Link LeaderboardEntry} represents a single user's name and score.
 * Each object has 2 properties: name and score.
 */

public class LeaderboardEntry {

    private Object mLeaderName;
    private Object mLeaderScore;

    public LeaderboardEntry(Object name, Object score)
    {
        mLeaderName = name;
        mLeaderScore = score;
    }

    public Object getName() {return mLeaderName;}
    public Object getScore() {return mLeaderScore;}

}
