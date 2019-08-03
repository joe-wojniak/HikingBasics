package blog.globalquality.hikingbasics.Constants;

/**
 * These constants are used by the YouTube Player Fragment showing the videos for the QuizActivity
 */
public final class Constants {

    // The YouTube Player requires a VideoId and an optional videoStart in milliseconds to play a video.
    // The quiz is identified by quizSG, etc.

    public static final String sg_videoId = "LoidFvJpZ1g";
    public static final int sg_videoStart = 5000;
    public static final String quizSG = "QuizSG";

    public static final String av_videoId = "QylgHwF3tkk";
    public static final int av_videoStart = 12000;
    public static final String quizAv = "QuizAv";

    public static final String TenE_videoId = "tvIH-5B576w";
    public static final int TenE_videoStart = 12000;
    public static final String quizTenE = "QuizTenEssentials";

    public static final String Nav_videoId = "Ba67vHfxqxY";
    public static final int Nav_videoStart = 12000;
    public static final String quizNav = "QuizNav";

    public static final String LNT_videoId = "wf77YuQ_1ow";
    public static final int LNT_videoStart = 9000;
    public static final String quizLNT = "QuizLeaveNoTrace";

    // Video Wall play lists

    public static final String sg_playlistID = "PLm-g0Ce0XFfEyR9MEE2maV5oiZyJc1ocE";
    public static final String bestplaces_playlistID = "PLm-g0Ce0XFfEp7o3VVyakIgwaZSLg8EfF";

    // PRIVATE

    private Constants() {
        throw new AssertionError();
    }
}
