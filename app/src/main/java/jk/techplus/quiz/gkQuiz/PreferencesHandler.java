package jk.techplus.quiz.gkQuiz;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHandler {
    public static final String CORRECT_QUESTIONS = "CORRECT_QUESTIONS";

    public static final String CORRECT_QUESTIONS_HIGH = "CORRECT_QUESTIONS_HIGH";

    public static final String MAIN_HIGH_SCORE = "MAIN_HIGH_SCORE";

    public static final String MAIN_SCORE = "MAIN_SCORES";

    public static final String REMOVEADS = "Removeads";

    public static final String TOTAL_QUESTIONS = "TOTAL_QUESTIONS";

    public static final String TOTAL_QUESTIONS_HIGH = "TOTAL_QUESTIONS_HIGH";

    public static final String WRONG_QUESTIONS = "WRONG_QUESTIONS";

    public static final String WRONG_QUESTIONS_HIGH = "WRONG_QUESTIONS_HIGH";

    private static SharedPreferences.Editor editor;

    private static SharedPreferences pref;

    public PreferencesHandler(Context paramContext) {
        pref = paramContext.getSharedPreferences("UrduQuiz", 0);
        editor = pref.edit();
    }

    public int getCorrectQuestions() { return pref.getInt("CORRECT_QUESTIONS", 0); }

    public int getCorrectQuestionsHigh() { return pref.getInt("CORRECT_QUESTIONS_HIGH", 0); }

    public int getMainHighScore() { return pref.getInt("MAIN_HIGH_SCORE", 0); }

    public int getMainScore() { return pref.getInt("MAIN_SCORES", 0); }

    public boolean getRemoveads() { return pref.getBoolean("Removeads", false); }

    public int getTotalQuestions() { return pref.getInt("TOTAL_QUESTIONS", 0); }

    public int getTotalQuestionsHigh() { return pref.getInt("TOTAL_QUESTIONS_HIGH", 0); }

    public int getWrongQuestions() { return pref.getInt("WRONG_QUESTIONS", 0); }

    public int getWrongQuestionsHigh() { return pref.getInt("WRONG_QUESTIONS_HIGH", 0); }

    public void setCorrectQuestions(int paramInt) {
        editor.putInt("CORRECT_QUESTIONS", paramInt);
        editor.commit();
    }

    public void setCorrectQuestionsHigh(int paramInt) {
        editor.putInt("CORRECT_QUESTIONS_HIGH", paramInt);
        editor.commit();
    }

    public void setMainHighScore(int paramInt) {
        editor.putInt("MAIN_HIGH_SCORE", paramInt);
        editor.commit();
    }

    public void setMainScore(int paramInt) {
        editor.putInt("MAIN_SCORES", paramInt);
        editor.commit();
    }

    public void setRemoveads(boolean paramBoolean) {
        editor.putBoolean("Removeads", paramBoolean);
        editor.commit();
    }

    public void setTotalQuestions(int paramInt) {
        editor.putInt("TOTAL_QUESTIONS", paramInt);
        editor.commit();
    }

    public void setTotalQuestionsHigh(int paramInt) {
        editor.putInt("TOTAL_QUESTIONS_HIGH", paramInt);
        editor.commit();
    }

    public void setWrongQuestions(int paramInt) {
        editor.putInt("WRONG_QUESTIONS", paramInt);
        editor.commit();
    }

    public void setWrongQuestionsHigh(int paramInt) {
        editor.putInt("WRONG_QUESTIONS_HIGH", paramInt);
        editor.commit();
    }
}
