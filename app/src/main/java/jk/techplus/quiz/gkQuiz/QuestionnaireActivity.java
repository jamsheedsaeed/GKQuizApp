package jk.techplus.quiz.gkQuiz;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.internal.view.SupportMenu;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class QuestionnaireActivity extends Activity implements OnClickListener {
    private static final int SOUND_BUTTON = 2131099648;
    private static final int SOUND_CORRECT = 2131099649;
    private static final int SOUND_TICK = 2131099651;
    private static final int SOUND_TIME_OUT = 2131099652;
    private static final int SOUND_WRONG = 2131099653;
    public static int correct;
    public static int index;
    public static int levelNumber = 1;
    private static int life = 3;
    public static int mainScore = 0;
    private static CountDownTimer nextCountDownTimer;
    public static int ques_number = 1;
    public static int scorePoints = 0;
    /* access modifiers changed from: private */
    public static CountDownTimer tickingCountDownTimer;
    public static int wrong;
    private String Categ;
    Animation anim;
    private RelativeLayout btn1Layout;
    private RelativeLayout btn2Layout;
    private RelativeLayout btn3Layout;
    private RelativeLayout btn4Layout;
    /* access modifiers changed from: private */
    public Button button1;
    /* access modifiers changed from: private */
    public Button button2;
    /* access modifiers changed from: private */
    public Button button3;
    /* access modifiers changed from: private */
    public Button button4;
    /* access modifiers changed from: private */
    public Button buttonExchange;
    /* access modifiers changed from: private */
    public Button buttonFiftyFifty;
    /* access modifiers changed from: private */
    public Button buttonTwoShots;

    /* renamed from: df */
    DecimalFormat f29df;
    /* access modifiers changed from: private */
    public boolean exchangePressed = false;
    /* access modifiers changed from: private */
    public boolean fiftyfiftyPressed = false;
    private RelativeLayout left;
    /* access modifiers changed from: private */
    public TextView level;
    private TextView levelView;
    private ImageView life1;
    private ImageView life2;
    private ImageView life3;
    private LayoutParams linearParams;
    private AdView mAdView;
    private int mButtonID;
    private int mCorrectSoundID;
    /* access modifiers changed from: private */
    public int mTickSoundID;
    /* access modifiers changed from: private */
    public int mTimeOutID;
    private int mWrongSoundID;
    private RelativeLayout.LayoutParams marginParams;
    /* access modifiers changed from: private */
    public MultipleChoice mcQuestion;
    private List<MultipleChoice> multipleChoiceList;
    private boolean paused;

    /* renamed from: ph */
    PreferencesHandler f30ph;
    private TextView points;
    private TextView pointsView;
    private QuestionDataSource qDataSource;
    private RelativeLayout ques_lay;
    LinearLayout questionLayout;
    /* access modifiers changed from: private */
    public TextView questionView;
    private RelativeLayout right;
    private ArrayList<String> selections;
    /* access modifiers changed from: private */
    public SoundPool soundPool;
    /* access modifiers changed from: private */
    public long ticks;
    /* access modifiers changed from: private */
    public TextView timerView;
    /* access modifiers changed from: private */
    public int totalQuestions;
    /* access modifiers changed from: private */
    public boolean twoShotButtonDisable = false;
    private boolean twoShotPressed = false;

    boolean notification;
    JSONObject object;
    String appLink;
    Vibrator vib;
    public static Ads ads;
    static /* synthetic */ int access$610() {
        int i = life;
        life = i - 1;
        return i;
    }

    /* access modifiers changed from: private */
    @SuppressLint("WrongConstant")
    public void getQuestion() {
        this.qDataSource.open();
        this.mcQuestion = this.qDataSource.getRandomQuestion(this.Categ);
        while (true) {
            if (!checkIfListContainsQuestion()) {
                break;
            }
            this.mcQuestion = this.qDataSource.getRandomQuestion(this.Categ);
            if (this.multipleChoiceList.size() == this.totalQuestions) {
                Toast.makeText(this, "Questions are Finished", 0).show();
                finishGame();
                break;
            }
        }
        if (this.mcQuestion == null || index == this.totalQuestions) {
            Toast.makeText(this, "Questions are Finished", 0).show();
            finishGame();
        } else {
            this.multipleChoiceList.add(this.mcQuestion);
            index++;
        }
        this.qDataSource.close();
    }

    @SuppressLint("WrongConstant")
    private void init() {
        this.questionView = (TextView) findViewById(R.id.questionView);
        this.vib = (Vibrator) getSystemService("vibrator");
        this.life1 = (ImageView) findViewById(R.id.first_life);
        this.life2 = (ImageView) findViewById(R.id.second_life);
        this.life3 = (ImageView) findViewById(R.id.third_life);
        this.button1 = (Button) findViewById(R.id.choice1);
        this.button2 = (Button) findViewById(R.id.choice2);
        this.button3 = (Button) findViewById(R.id.choice3);
        this.button4 = (Button) findViewById(R.id.choice4);
        this.buttonFiftyFifty = (Button) findViewById(R.id.button_fiftyfifty);
        this.buttonTwoShots = (Button) findViewById(R.id.button_two_shots);
        this.buttonExchange = (Button) findViewById(R.id.button_Exchange);
        this.points = (TextView) findViewById(R.id.points);
        this.level = (TextView) findViewById(R.id.level);
        this.f29df = new DecimalFormat("00");
        this.questionLayout = (LinearLayout) findViewById(R.id.questionLayout);
        this.btn1Layout = (RelativeLayout) findViewById(R.id.btn1Layout);
        this.btn2Layout = (RelativeLayout) findViewById(R.id.btn2Layout);
        this.btn3Layout = (RelativeLayout) findViewById(R.id.btn3Layout);
        this.btn4Layout = (RelativeLayout) findViewById(R.id.btn4Layout);
        this.left = (RelativeLayout) findViewById(R.id.leftLayout);
        this.right = (RelativeLayout) findViewById(R.id.rightLayout);
        this.pointsView = (TextView) findViewById(R.id.pointsView);
        this.levelView = (TextView) findViewById(R.id.levelView);
        this.ques_lay = (RelativeLayout) findViewById(R.id.ques_lay);
        this.timerView = (TextView) findViewById(R.id.timmerView);
        setupLayout();
        this.anim = AnimationUtils.loadAnimation(this, R.anim.end_timmer);
        this.button1.setOnClickListener(this);
        this.button2.setOnClickListener(this);
        this.button3.setOnClickListener(this);
        this.button4.setOnClickListener(this);
        this.buttonFiftyFifty.setOnClickListener(this);
        this.buttonTwoShots.setOnClickListener(this);
        this.buttonExchange.setOnClickListener(this);
    }

    private boolean isTablet() {
        return (getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    /* access modifiers changed from: private */
    public void nextQuestion() {
        Log.i("Score Calculated", "" + (this.ticks / 1000));
        Log.i("Main Score", "" + mainScore);
        Log.i("Ticks", "" + this.ticks);
        switch (life) {
            case 0:
                this.life1.setImageDrawable(getResources().getDrawable(R.drawable.life_off));
                break;
            case 1:
                this.life2.setImageDrawable(getResources().getDrawable(R.drawable.life_off));
                break;
            case 2:
                this.life3.setImageDrawable(getResources().getDrawable(R.drawable.life_off));
                break;
        }
        if (life <= 0) {
            finishGame();
            return;
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.questionLayout, "rotationY", new float[]{180.0f, 360.0f});
        ofFloat.setDuration(500);
        ofFloat.setInterpolator(new AccelerateDecelerateInterpolator());
        this.button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.pressed));
        this.button2.setBackgroundDrawable(getResources().getDrawable(R.drawable.pressed));
        this.button3.setBackgroundDrawable(getResources().getDrawable(R.drawable.pressed));
        this.button4.setBackgroundDrawable(getResources().getDrawable(R.drawable.pressed));
        ofFloat.addListener(new AnimatorListener() {
            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                QuestionnaireActivity.tickingCountDownTimer.cancel();
                QuestionnaireActivity.tickingCountDownTimer.start();
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }
        });
        ofFloat.start();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                QuestionnaireActivity.this.getQuestion();
                QuestionnaireActivity.ques_number++;
                if (QuestionnaireActivity.correct % 3 == 0 && QuestionnaireActivity.correct != 0) {
                    QuestionnaireActivity.levelNumber = (QuestionnaireActivity.correct / 3) + 1;
                }
                QuestionnaireActivity.this.level.setText(QuestionnaireActivity.this.f29df.format((long) QuestionnaireActivity.levelNumber).toString());
                QuestionnaireActivity.this.button1.setEnabled(true);
                QuestionnaireActivity.this.button2.setEnabled(true);
                QuestionnaireActivity.this.button3.setEnabled(true);
                QuestionnaireActivity.this.button4.setEnabled(true);
                if (!QuestionnaireActivity.this.fiftyfiftyPressed) {
                    QuestionnaireActivity.this.buttonFiftyFifty.setEnabled(true);
                }
                if (!QuestionnaireActivity.this.twoShotButtonDisable) {
                    QuestionnaireActivity.this.buttonTwoShots.setEnabled(true);
                }
                if (!QuestionnaireActivity.this.exchangePressed) {
                    QuestionnaireActivity.this.buttonExchange.setEnabled(true);
                }
                QuestionnaireActivity.this.questionView.setText(QuestionnaireActivity.this.mcQuestion.getQuestion());
                QuestionnaireActivity.this.button1.setText(QuestionnaireActivity.this.mcQuestion.getChoice1());
                QuestionnaireActivity.this.button2.setText(QuestionnaireActivity.this.mcQuestion.getChoice2());
                QuestionnaireActivity.this.button3.setText(QuestionnaireActivity.this.mcQuestion.getChoice3());
                QuestionnaireActivity.this.button4.setText(QuestionnaireActivity.this.mcQuestion.getChoice4());
            }
        }, 250);
    }

    /* access modifiers changed from: private */
    public void rightAnswer() {
        try {
            if (this.button1.getText().equals(this.mcQuestion.getAnswer())) {
                this.button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_draw));
            } else if (this.button2.getText().equals(this.mcQuestion.getAnswer())) {
                this.button2.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_draw));
            } else if (this.button3.getText().equals(this.mcQuestion.getAnswer())) {
                this.button3.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_draw));
            } else if (this.button4.getText().equals(this.mcQuestion.getAnswer())) {
                this.button4.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_draw));
            }
            this.button1.setEnabled(false);
            this.button2.setEnabled(false);
            this.button3.setEnabled(false);
            this.button4.setEnabled(false);
            if (!this.fiftyfiftyPressed) {
                this.buttonFiftyFifty.setEnabled(false);
            }
            if (!this.twoShotButtonDisable) {
                this.buttonTwoShots.setEnabled(false);
            }
            if (!this.exchangePressed) {
                this.buttonExchange.setEnabled(false);
            }
            nextCountDownTimer.start();
        } catch (IndexOutOfBoundsException e) {
        }
    }

    private void setupLayout() {
        @SuppressLint("WrongConstant") Display defaultDisplay = ((WindowManager) getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        int i = point.x;
        int i2 = point.y;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((i / 100) * 40, (i2 / 100) * 8);
        layoutParams.addRule(11);
        this.left.setLayoutParams(new RelativeLayout.LayoutParams((i / 100) * 40, (i2 / 100) * 8));
        this.right.setLayoutParams(layoutParams);
        this.marginParams = (RelativeLayout.LayoutParams) this.points.getLayoutParams();
        this.marginParams.setMargins((i / 100) * 3, 0, 0, 0);
        this.marginParams = (RelativeLayout.LayoutParams) this.level.getLayoutParams();
        this.marginParams.setMargins(0, 0, (i / 100) * 3, 0);
        this.marginParams = (RelativeLayout.LayoutParams) this.pointsView.getLayoutParams();
        this.marginParams.setMargins(0, 0, (i / 100) * 5, 0);
        this.marginParams = (RelativeLayout.LayoutParams) this.questionLayout.getLayoutParams();
        this.marginParams.setMargins(0, 8, 0, 0);
        this.questionLayout.setLayoutParams(this.marginParams);
        this.questionView.setTextSize((float) ((i / 90) * 3));
        this.ques_lay.setLayoutParams(new LayoutParams(-1, (i2 / 100) * 22));
        this.button1.setLayoutParams(new RelativeLayout.LayoutParams(-1, (i2 / 100) * 10));
        this.button1.setTextSize(2, 22.0f);
        this.marginParams = (RelativeLayout.LayoutParams) this.button1.getLayoutParams();
        this.marginParams.setMargins((i / 120) * 2, 0, (i / 120) * 2, 0);
        this.button2.setLayoutParams(new RelativeLayout.LayoutParams(-1, (i2 / 100) * 10));
        this.button2.setTextSize(2, 22.0f);
        this.marginParams = (RelativeLayout.LayoutParams) this.button2.getLayoutParams();
        this.marginParams.setMargins((i / 120) * 2, 0, (i / 120) * 2, 0);
        this.button3.setLayoutParams(new RelativeLayout.LayoutParams(-1, (i2 / 100) * 10));
        this.button3.setTextSize(2, 22.0f);
        this.marginParams = (RelativeLayout.LayoutParams) this.button3.getLayoutParams();
        this.marginParams.setMargins((i / 120) * 2, 0, (i / 120) * 2, 0);
        this.button4.setLayoutParams(new RelativeLayout.LayoutParams(-1, (i2 / 100) * 10));
        this.button4.setTextSize(2, 22.0f);
        this.marginParams = (RelativeLayout.LayoutParams) this.button4.getLayoutParams();
        this.marginParams.setMargins((i / 120) * 2, 0, (i / 120) * 2, 0);
        this.linearParams = (LayoutParams) this.btn1Layout.getLayoutParams();
        this.linearParams.setMargins(0, (i2 / 120) * 2, 0, (i2 / 120) * 2);
        this.btn1Layout.setLayoutParams(this.linearParams);
        this.linearParams = (LayoutParams) this.btn2Layout.getLayoutParams();
        this.linearParams.setMargins(0, 0, 0, (i2 / 120) * 2);
        this.btn2Layout.setLayoutParams(this.linearParams);
        this.linearParams = (LayoutParams) this.btn3Layout.getLayoutParams();
        this.linearParams.setMargins(0, 0, 0, (i2 / 120) * 2);
        this.btn3Layout.setLayoutParams(this.linearParams);
        this.linearParams = (LayoutParams) this.btn4Layout.getLayoutParams();
        this.linearParams.setMargins(0, 0, 0, (i2 / 120) * 2);
        this.btn4Layout.setLayoutParams(this.linearParams);
        if (!isTablet()) {
            this.level.setTextSize(2, 20.0f);
            this.points.setTextSize(2, 20.0f);
            this.timerView.setTextSize(2, 38.0f);
            this.pointsView.setTextSize(2, 20.0f);
            this.levelView.setTextSize(2, 20.0f);
            this.button1.setTextSize(2, 20.0f);
            this.button2.setTextSize(2, 20.0f);
            this.button3.setTextSize(2, 20.0f);
            this.button4.setTextSize(2, 20.0f);
            this.buttonFiftyFifty.setTextSize(2, 20.0f);
            this.buttonTwoShots.setTextSize(2, 20.0f);
            this.buttonExchange.setTextSize(2, 20.0f);
            return;
        }
        this.level.setTextSize(2, 40.0f);
        this.points.setTextSize(2, 40.0f);
        this.pointsView.setTextSize(2, 40.0f);
        this.levelView.setTextSize(2, 40.0f);
        this.timerView.setTextSize(2, 58.0f);
        this.button1.setTextSize(2, 40.0f);
        this.button2.setTextSize(2, 40.0f);
        this.button3.setTextSize(2, 40.0f);
        this.button4.setTextSize(2, 40.0f);
        this.buttonFiftyFifty.setTextSize(2, 40.0f);
        this.buttonTwoShots.setTextSize(2, 40.0f);
        this.buttonExchange.setTextSize(2, 40.0f);
    }

    private void startQuestionOnResume() {
        this.mcQuestion = (MultipleChoice) this.multipleChoiceList.get(this.multipleChoiceList.size() - 1);
        this.questionView.setText(this.mcQuestion.getQuestion());
        this.button1.setText(this.mcQuestion.getChoice1());
        this.button2.setText(this.mcQuestion.getChoice2());
        this.button3.setText(this.mcQuestion.getChoice3());
        this.button4.setText(this.mcQuestion.getChoice4());
        new CountDownTimer(this.ticks, 1000) {
            public void onFinish() {
                QuestionnaireActivity.this.timerView.setText("00");
                QuestionnaireActivity.this.soundPool.stop(QuestionnaireActivity.this.mTickSoundID);
                QuestionnaireActivity.this.soundPool.play(QuestionnaireActivity.this.mTimeOutID, 1.0f, 1.0f, 1, 0, 1.0f);
                QuestionnaireActivity.this.rightAnswer();
                QuestionnaireActivity.wrong = 1;
            }

            public void onTick(long j) {
                QuestionnaireActivity.this.ticks = j;
                QuestionnaireActivity.this.timerView.setText(QuestionnaireActivity.this.f29df.format(j / 1000));
                QuestionnaireActivity.this.soundPool.stop(QuestionnaireActivity.this.mTimeOutID);
                QuestionnaireActivity.this.soundPool.stop(QuestionnaireActivity.this.mTickSoundID);
                if (j / 1000 == 10) {
                    QuestionnaireActivity.this.timerView.setTextColor(SupportMenu.CATEGORY_MASK);
                } else if (j / 1000 > 10) {
                    QuestionnaireActivity.this.timerView.setTextColor(-1);
                }
                if (j / 1000 <= 10) {
                    QuestionnaireActivity.this.soundPool.play(QuestionnaireActivity.this.mTickSoundID, 1.0f, 1.0f, 1, 0, 1.0f);
                    QuestionnaireActivity.this.timerView.startAnimation(QuestionnaireActivity.this.anim);
                }
            }
        }.start();
    }

    private void startQuestions() {
        getQuestion();
        this.questionView.setText(this.mcQuestion.getQuestion());
        this.button1.setText(this.mcQuestion.getChoice1());
        this.button2.setText(this.mcQuestion.getChoice2());
        this.button3.setText(this.mcQuestion.getChoice3());
        this.button4.setText(this.mcQuestion.getChoice4());
        tickingCountDownTimer.start();
    }

    private void xchangeQuestion() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.questionLayout, "rotationY", new float[]{180.0f, 360.0f});
        ofFloat.setDuration(500);
        ofFloat.setInterpolator(new AccelerateDecelerateInterpolator());
        this.button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.pressed));
        this.button2.setBackgroundDrawable(getResources().getDrawable(R.drawable.pressed));
        this.button3.setBackgroundDrawable(getResources().getDrawable(R.drawable.pressed));
        this.button4.setBackgroundDrawable(getResources().getDrawable(R.drawable.pressed));
        ofFloat.addListener(new AnimatorListener() {
            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                QuestionnaireActivity.tickingCountDownTimer.cancel();
                QuestionnaireActivity.tickingCountDownTimer.start();
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }
        });
        ofFloat.start();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                QuestionnaireActivity.this.getQuestion();
                QuestionnaireActivity.this.level.setText(QuestionnaireActivity.this.f29df.format((long) QuestionnaireActivity.levelNumber).toString());
                QuestionnaireActivity.this.button1.setEnabled(true);
                QuestionnaireActivity.this.button2.setEnabled(true);
                QuestionnaireActivity.this.button3.setEnabled(true);
                QuestionnaireActivity.this.button4.setEnabled(true);
                QuestionnaireActivity.this.questionView.setText(QuestionnaireActivity.this.mcQuestion.getQuestion());
                QuestionnaireActivity.this.button1.setText(QuestionnaireActivity.this.mcQuestion.getChoice1());
                QuestionnaireActivity.this.button2.setText(QuestionnaireActivity.this.mcQuestion.getChoice2());
                QuestionnaireActivity.this.button3.setText(QuestionnaireActivity.this.mcQuestion.getChoice3());
                QuestionnaireActivity.this.button4.setText(QuestionnaireActivity.this.mcQuestion.getChoice4());
            }
        }, 250);
    }
    /* access modifiers changed from: 0000 */
    public boolean checkIfListContainsQuestion() {
        for (MultipleChoice id : this.multipleChoiceList) {
            if (id.getId() == this.mcQuestion.getId()) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public void finishGame() {
        this.f30ph.setMainScore(mainScore);
        this.f30ph.setTotalQuestions(ques_number);
        this.f30ph.setCorrectQuestions(correct);
        this.f30ph.setWrongQuestions(wrong);
        if (mainScore > this.f30ph.getMainHighScore()) {
            this.f30ph.setMainHighScore(mainScore);
            this.f30ph.setTotalQuestionsHigh(ques_number);
            this.f30ph.setCorrectQuestionsHigh(correct);
            this.f30ph.setWrongQuestionsHigh(wrong);
        }
        nextCountDownTimer.cancel();
        tickingCountDownTimer.cancel();
        this.soundPool.release();
        finish();
        Intent intent = new Intent(QuestionnaireActivity.this,GameOverActivity.class);
        startActivity(intent);
      //  startActivity(new Intent(this, GameOverActivity.class));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishGame();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_fiftyfifty /*2131624157*/:
                this.soundPool.play(this.mButtonID, 1.0f, 1.0f, 1, 0, 1.0f);
                if (this.button1.getText().equals(this.mcQuestion.getAnswer())) {
                    this.button1.setEnabled(true);
                    this.button2.setEnabled(true);
                    this.button3.setEnabled(false);
                    this.button4.setEnabled(false);
                } else if (this.button2.getText().equals(this.mcQuestion.getAnswer())) {
                    this.button1.setEnabled(false);
                    this.button2.setEnabled(true);
                    this.button3.setEnabled(true);
                    this.button4.setEnabled(false);
                } else if (this.button3.getText().equals(this.mcQuestion.getAnswer())) {
                    this.button1.setEnabled(false);
                    this.button2.setEnabled(false);
                    this.button3.setEnabled(true);
                    this.button4.setEnabled(true);
                } else if (this.button4.getText().equals(this.mcQuestion.getAnswer())) {
                    this.button1.setEnabled(true);
                    this.button2.setEnabled(false);
                    this.button3.setEnabled(false);
                    this.button4.setEnabled(true);
                }
                this.fiftyfiftyPressed = true;
                this.buttonFiftyFifty.setEnabled(false);
                return;
            case R.id.button_two_shots /*2131624158*/:
                this.soundPool.play(this.mButtonID, 1.0f, 1.0f, 1, 0, 1.0f);
                this.twoShotPressed = true;
                this.twoShotButtonDisable = true;
                this.buttonTwoShots.setEnabled(false);
                return;
            case R.id.button_Exchange /*2131624159*/:
                this.soundPool.play(this.mButtonID, 1.0f, 1.0f, 1, 0, 1.0f);
                xchangeQuestion();
                this.exchangePressed = true;
                this.buttonExchange.setEnabled(false);
                return;
            case R.id.choice1 /*2131624161*/:
                if (this.button1.getText().equals(this.mcQuestion.getAnswer())) {
                    scorePoints++;
                    this.points.setText(this.f29df.format((long) scorePoints) + "");
                    this.soundPool.play(this.mCorrectSoundID, 1.0f, 1.0f, 1, 0, 1.0f);
                    correct++;
                    mainScore += (int) (this.ticks / 1000);
                    this.button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_draw));
                    this.button2.setEnabled(false);
                    this.button3.setEnabled(false);
                    this.button4.setEnabled(false);
                    if (!this.fiftyfiftyPressed) {
                        this.buttonFiftyFifty.setEnabled(false);
                    }
                    if (!this.twoShotPressed) {
                        this.buttonTwoShots.setEnabled(false);
                    }
                    if (!this.exchangePressed) {
                        this.buttonExchange.setEnabled(false);
                    }
                    nextCountDownTimer.start();
                } else if (!this.twoShotPressed) {
                    this.soundPool.play(this.mWrongSoundID, 1.0f, 1.0f, 1, 0, 1.0f);
                    wrong++;
                    life--;
                    this.button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_draw));
                    this.vib.vibrate(800);
                    rightAnswer();
                } else if (this.twoShotPressed) {
                    this.button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_draw));
                }
                if (!this.twoShotPressed) {
                    tickingCountDownTimer.cancel();
                }
                this.twoShotPressed = false;
                return;
            case R.id.choice2 /*2131624163*/:
                if (this.button2.getText().equals(this.mcQuestion.getAnswer())) {
                    this.button2.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_draw));
                    this.soundPool.play(this.mCorrectSoundID, 1.0f, 1.0f, 1, 0, 1.0f);
                    this.button1.setEnabled(false);
                    this.button3.setEnabled(false);
                    this.button4.setEnabled(false);
                    if (!this.fiftyfiftyPressed) {
                        this.buttonFiftyFifty.setEnabled(false);
                    }
                    if (!this.twoShotPressed) {
                        this.buttonTwoShots.setEnabled(false);
                    }
                    if (!this.exchangePressed) {
                        this.buttonExchange.setEnabled(false);
                    }
                    scorePoints++;
                    this.points.setText(this.f29df.format((long) scorePoints));
                    correct++;
                    mainScore += (int) (this.ticks / 1000);
                    nextCountDownTimer.start();
                } else if (!this.twoShotPressed) {
                    this.button2.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_draw));
                    this.soundPool.play(this.mWrongSoundID, 1.0f, 1.0f, 1, 0, 1.0f);
                    wrong++;
                    life--;
                    this.vib.vibrate(800);
                    rightAnswer();
                } else if (this.twoShotPressed) {
                    this.button2.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_draw));
                }
                if (!this.twoShotPressed) {
                    tickingCountDownTimer.cancel();
                }
                this.twoShotPressed = false;
                return;
            case R.id.choice3 /*2131624165*/:
                if (this.button3.getText().equals(this.mcQuestion.getAnswer())) {
                    this.button3.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_draw));
                    this.soundPool.play(this.mCorrectSoundID, 1.0f, 1.0f, 1, 0, 1.0f);
                    this.button2.setEnabled(false);
                    this.button1.setEnabled(false);
                    this.button4.setEnabled(false);
                    if (!this.fiftyfiftyPressed) {
                        this.buttonFiftyFifty.setEnabled(false);
                    }
                    if (!this.twoShotPressed) {
                        this.buttonTwoShots.setEnabled(false);
                    }
                    if (!this.exchangePressed) {
                        this.buttonExchange.setEnabled(false);
                    }
                    scorePoints++;
                    this.points.setText(this.f29df.format((long) scorePoints));
                    correct++;
                    mainScore += (int) (this.ticks / 1000);
                    nextCountDownTimer.start();
                } else if (!this.twoShotPressed) {
                    this.button3.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_draw));
                    this.soundPool.play(this.mWrongSoundID, 1.0f, 1.0f, 1, 0, 1.0f);
                    wrong++;
                    life--;
                    this.vib.vibrate(800);
                    rightAnswer();
                } else if (this.twoShotPressed) {
                    this.button3.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_draw));
                }
                if (!this.twoShotPressed) {
                    tickingCountDownTimer.cancel();
                }
                this.twoShotPressed = false;
                return;
            case R.id.choice4 /*2131624167*/:
                if (this.button4.getText().equals(this.mcQuestion.getAnswer())) {
                    this.button4.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_draw));
                    this.soundPool.play(this.mCorrectSoundID, 1.0f, 1.0f, 1, 0, 1.0f);
                    this.button2.setEnabled(false);
                    this.button3.setEnabled(false);
                    this.button1.setEnabled(false);
                    if (!this.fiftyfiftyPressed) {
                        this.buttonFiftyFifty.setEnabled(false);
                    }
                    if (!this.twoShotPressed) {
                        this.buttonTwoShots.setEnabled(false);
                    }
                    if (!this.exchangePressed) {
                        this.buttonExchange.setEnabled(false);
                    }
                    scorePoints++;
                    this.points.setText(this.f29df.format((long) scorePoints));
                    correct++;
                    mainScore += (int) (this.ticks / 1000);
                    nextCountDownTimer.start();
                } else if (!this.twoShotPressed) {
                    this.button4.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_draw));
                    this.soundPool.play(this.mWrongSoundID, 1.0f, 1.0f, 1, 0, 1.0f);
                    wrong++;
                    life--;
                    this.vib.vibrate(800);
                    rightAnswer();
                } else if (this.twoShotPressed) {
                    this.button4.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_draw));
                }
                if (!this.twoShotPressed) {
                    tickingCountDownTimer.cancel();
                }
                this.twoShotPressed = false;
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    @SuppressLint("WrongConstant")
    public void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        super.onCreate(bundle);
        setContentView(R.layout.questionaire);
        ads = new Ads(this, true);
        this.f30ph = new PreferencesHandler(this);
        this.selections = new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.selections = extras.getStringArrayList("SELECTIONS");
        }
        this.Categ = "";
        int i = 0;
        while (true) {
            if (i >= this.selections.size()) {
                break;
            }
            if (i == this.selections.size() - 1) {
                this.Categ += "Categ = '" + ((String) this.selections.get(i)) + "'";
                this.Categ = "where " + this.Categ;
            } else {
                this.Categ += "Categ = '" + ((String) this.selections.get(i)) + "' or ";
            }
            if (((String) this.selections.get(i)).equals("All")) {
                this.Categ = "";
                break;
            }
            i++;
        }



//        if (this.f30ph.getRemoveads()) {
//            this.mAdView.setVisibility(8);
//        } else {
//            this.mAdView.loadAd(build);
//        }
        this.qDataSource = new QuestionDataSource(this);
        this.mcQuestion = new MultipleChoice();
        this.multipleChoiceList = new ArrayList();

        this.soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

        this.mCorrectSoundID = this.soundPool.load(this, R.raw.correct_answer, 1);
        this.mWrongSoundID = this.soundPool.load(this, R.raw.wrong_answer, 1);
        this.mTickSoundID = this.soundPool.load(this, R.raw.ticking_sec, 1);
        this.mTimeOutID = this.soundPool.load(this, R.raw.time_out, 1);
        this.mButtonID = this.soundPool.load(this, R.raw.button_sound, 1);


        this.ticks = 0;
        tickingCountDownTimer = new CountDownTimer(30000, 1000) {
            public void onFinish() {
                QuestionnaireActivity.this.timerView.setText("00");
                QuestionnaireActivity.this.soundPool.stop(QuestionnaireActivity.this.mTickSoundID);
                QuestionnaireActivity.this.soundPool.play(QuestionnaireActivity.this.mTimeOutID, 1.0f, 1.0f, 1, 0, 1.0f);
                QuestionnaireActivity.this.rightAnswer();
                QuestionnaireActivity.wrong++;
                QuestionnaireActivity.access$610();
            }

            public void onTick(long j) {
                QuestionnaireActivity.this.ticks = j;
                QuestionnaireActivity.this.timerView.setText(QuestionnaireActivity.this.f29df.format(j / 1000));
                QuestionnaireActivity.this.soundPool.stop(QuestionnaireActivity.this.mTimeOutID);
                QuestionnaireActivity.this.soundPool.stop(QuestionnaireActivity.this.mTickSoundID);
                if (j / 1000 == 10) {
                    QuestionnaireActivity.this.timerView.setTextColor(SupportMenu.CATEGORY_MASK);
                } else if (j / 1000 > 10) {
                    QuestionnaireActivity.this.timerView.setTextColor(-1);
                }
                if (j / 1000 <= 10) {
                    QuestionnaireActivity.this.soundPool.play(QuestionnaireActivity.this.mTickSoundID, 1.0f, 1.0f, 1, 0, 1.0f);
                    QuestionnaireActivity.this.timerView.startAnimation(QuestionnaireActivity.this.anim);
                }
            }
        };
        nextCountDownTimer = new CountDownTimer(2000, 500) {
            public void onFinish() {
                if (QuestionnaireActivity.index != QuestionnaireActivity.this.totalQuestions) {
                    QuestionnaireActivity.this.nextQuestion();
                } else {
                    QuestionnaireActivity.this.finishGame();
                }
            }

            public void onTick(long j) {
            }
        };
        life = 3;
        index = 0;
        scorePoints = 0;
        levelNumber = 1;
        correct = 0;
        wrong = 0;
        ques_number = 1;
        mainScore = 0;
        init();
        this.qDataSource.open();
        this.totalQuestions = this.qDataSource.getNumberOfQuestions(this.Categ);
        this.qDataSource.close();
        startQuestions();

        RelativeLayout adView = findViewById(R.id.adView);
        // this.mAdView = (AdView) findViewById(R.id.adView);
        QuestionnaireActivity.ads.loadBanner(adView);

       // ads.showInterstitial();
        //  AdRequest build = new Builder().build();
        requestAd();

    }
    private void requestAd() {
        AndroidNetworking.post(getString(R.string.baseURL) + "getapp")
                .addBodyParameter("package", "jk.techplus.quiz.gkQuiz")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        // Log.e("response", response.toString());
                        object = response;
                        try {
                            JSONObject jsonObject = object.getJSONObject("banner");
                            Log.e("JSON", jsonObject.toString());
                            //imgUrl = jsonObject.getString("banner");
                            appLink = jsonObject.getString("package");
                            //Log.e("imgUrl", imgUrl);
                            Log.e("appLink", appLink);

                            notification = true;


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("JSON Exception", e.toString());
                        }


                    }

                    @Override
                    public void onError(ANError error) {
                        Log.e("error", error.toString());
                    }
                });


    }
    @Override
    public void onDestroy() {
       // this.mAdView.destroy();
        this.soundPool.release();
        super.onDestroy();
    }

    @Override
    public void onPause() {
//        if (!this.f30ph.getRemoveads()) {
//            this.mAdView.pause();
//        }
        this.soundPool.release();
        nextCountDownTimer.cancel();
        tickingCountDownTimer.cancel();
        this.paused = true;
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.paused) {

            this.soundPool = new SoundPool(1, 3, 0);
            this.mCorrectSoundID = this.soundPool.load(this, R.raw.correct_answer, 1);
            this.mWrongSoundID = this.soundPool.load(this, R.raw.wrong_answer, 1);
            this.mTickSoundID = this.soundPool.load(this, R.raw.ticking_sec, 1);
            this.mTimeOutID = this.soundPool.load(this, R.raw.time_out, 1);
            startQuestionOnResume();
            this.paused = false;
        }
//        if (!this.f30ph.getRemoveads()) {
//            this.mAdView.resume();
//        }
    }
}
