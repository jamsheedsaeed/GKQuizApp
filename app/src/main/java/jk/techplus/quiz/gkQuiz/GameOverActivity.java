package jk.techplus.quiz.gkQuiz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.AudioManager;
import android.view.View;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class GameOverActivity extends Activity implements View.OnClickListener {

    public static boolean SHOW_FULL_SCREEN_AD = false;
    private static final int SOUND_BUTTON = 2131099648;
    private TextView again;
    private TextView headingText;
    private LinearLayout infoLayout;
    private TextView infoText;
    private LayoutParams layoutParams;
    private int mButtonID;
    private TextView mainScoreTextView;
    private Button quit_game;
    private TextView scoreview;
    private SoundPool soundPool;
    private Button start_game;
    private TextView total_ques;
    private TextView wrongScore;

    private void settingUp() {
        @SuppressLint("WrongConstant") Display defaultDisplay = ((WindowManager) getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        int i = point.x;
        int i2 = point.y;
        this.layoutParams = new LayoutParams((i / 100) * 30, (i2 / 100) * 7);
        this.layoutParams.addRule(9);
        this.quit_game.setLayoutParams(this.layoutParams);
        this.layoutParams = new LayoutParams((i / 100) * 30, (i2 / 100) * 7);
        this.layoutParams.addRule(11);
        this.start_game.setLayoutParams(this.layoutParams);
        if (isTablet()) {
            this.quit_game.setTextSize(2, 40.0f);
            this.start_game.setTextSize(2, 40.0f);
            this.total_ques.setTextSize(2, 40.0f);
            this.headingText.setTextSize(2, 40.0f);
            this.again.setTextSize(2, 40.0f);
            this.scoreview.setTextSize(2, 40.0f);
            this.wrongScore.setTextSize(2, 40.0f);
            this.infoText.setTextSize(2, 40.0f);
            this.mainScoreTextView.setTextSize(2, 40.0f);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.quit_game /*2131624073*/:
                this.soundPool.play(this.mButtonID, 1.0f, 1.0f, 1, 0, 1.0f);
                finish();
                return;
            case R.id.start_game /*2131624074*/:
                this.soundPool.play(this.mButtonID, 1.0f, 1.0f, 1, 0, 1.0f);
                startActivity(new Intent(this, SelectorActivity.class));
                finish();
                return;
            default:
                return;
        }
    }
    public boolean isTablet() {
        return (getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.game_over);
        SHOW_FULL_SCREEN_AD = true;
        this.soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        this.mButtonID = this.soundPool.load(this, R.raw.button_sound, 1);
        this.scoreview = (TextView) findViewById(R.id.scoreview1);
        this.scoreview.setText(getResources().getString(R.string.correct_ans) + " : " + QuestionnaireActivity.correct);
        this.mainScoreTextView = (TextView) findViewById(R.id.mainScore1);
       this.mainScoreTextView.setText(getResources().getString(R.string.points) + " : " + QuestionnaireActivity.mainScore);
        this.wrongScore = (TextView) findViewById(R.id.wrongScore1);
        this.headingText = (TextView) findViewById(R.id.headingText);
        this.total_ques = (TextView) findViewById(R.id.total_ques);
      this.wrongScore.setText(getResources().getString(R.string.wrong_ans) + " : " + QuestionnaireActivity.wrong);
        this.total_ques = (TextView) findViewById(R.id.total_ques);
       this.total_ques.setText(getResources().getString(R.string.total_ques) + " : " + QuestionnaireActivity.ques_number);
        this.infoLayout = (LinearLayout) findViewById(R.id.infoLayout);
        this.again = (TextView) findViewById(R.id.again);
        this.infoText = (TextView) findViewById(R.id.infoText);
       this.again.setText(getResources().getString(R.string.start) + " " + QuestionnaireActivity.mainScore + " " + getResources().getString(R.string.end));
        this.start_game = (Button) findViewById(R.id.start_game);
        this.quit_game = (Button) findViewById(R.id.quit_game);
        settingUp();
        this.start_game.setOnClickListener(this);
        this.quit_game.setOnClickListener(this);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
