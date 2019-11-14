package jk.techplus.quiz.gkQuiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONException;
import org.json.JSONObject;



import static android.content.Intent.ACTION_VIEW;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MAIN_SOUND_BUTTON = 2131099650;
    private static final int SOUND_BUTTON = 2131099648;
    private RelativeLayout about;
  //  private AnalyticsHit analyticsHit;
   // private AppInvites appInvites;
    private RelativeLayout invite;
    private RelativeLayout.LayoutParams layoutParams;
    CharSequence name;// The user-visible name of the channel.

    String CHANNEL_ID = "my_channel_01";
    int importance = NotificationManager.IMPORTANCE_HIGH;
    boolean notification;

    private RelativeLayout likeUs;
    /* access modifiers changed from: private */
    public int mButtonID;
    InterstitialAd mInterstitialAd;
    private int mMainButtonID;
    private RelativeLayout more;
    private Button new_game;
    private ImageView no_ads_img;
    private TextView no_ads_key;
//    private PDMSMessage pdmsMessage;
    /* access modifiers changed from: private */
    public PreferencesHandler prefs;
    Toolbar mToolbar;
    private RelativeLayout removeAds;
    /* access modifiers changed from: private */
    public SoundPool soundPool;

    private Button upper_btn;

    CardView cardplatbtn, cardcatbtn, cardhighsbtn , cardfeedback;

    public static Ads ads;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.prefs = new PreferencesHandler(this);
        this.soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        this.mMainButtonID = this.soundPool.load(this, R.raw.main_button_sound, 1);
        this.mButtonID = this.soundPool.load(this, R.raw.button_sound, 1);
        this.mInterstitialAd = new InterstitialAd(this);

        this.mInterstitialAd.setAdUnitId(getResources().getString(R.string.inter_id));


        this.likeUs = (RelativeLayout) findViewById(R.id.likeUS);
        this.about = (RelativeLayout) findViewById(R.id.about);
        this.invite = (RelativeLayout) findViewById(R.id.appinvite);
        this.more = (RelativeLayout) findViewById(R.id.moreApps);
        this.removeAds = (RelativeLayout) findViewById(R.id.removeAds);

        cardplatbtn = (CardView)findViewById(R.id.playgame);
        cardcatbtn = (CardView)findViewById(R.id.viewcategory);
        cardhighsbtn = (CardView)findViewById(R.id.highscore);
        cardfeedback = (CardView)findViewById(R.id.feedback);

//        this.likeUs.setOnClickListener(this);
        this.about.setOnClickListener(this);
        this.invite.setOnClickListener(this);
        this.more.setOnClickListener(this);
//        this.removeAds.setOnClickListener(this);

        this.no_ads_key = (TextView) findViewById(R.id.no_ads_text);
        this.no_ads_img = (ImageView) findViewById(R.id.no_ads_img);
       // this.new_game = (Button) findViewById(R.id.new_game);
        this.upper_btn = (Button) findViewById(R.id.upper_btn);
        this.cardplatbtn.setOnClickListener(this);
        this.cardhighsbtn.setOnClickListener(this);


        ads = new Ads(this, true);

        RelativeLayout adViewRectangle = findViewById(R.id.adRectangle);
//
//       requestAd();
      // ads.showInterstitial();

        ads.showInterstitial();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendNotification();
            }
        }, 40000);

       // setupLayout();
        cardcatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ViewCategories.class);
                startActivity(intent);
            }
        });

        cardfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Feedback.class);
                startActivity(intent);
                ads.showInterstitial();
            }
        });




    }

    JSONObject object;
    String appLink;

    public void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.e("TAG", "The interstitial wasn't loaded yet.");
//            startAppAd = new StartAppAd(context);
//            startAppAd.loadAd(StartAppAd.AdMode.AUTOMATIC, new AdEventListener() {
//                @Override
//                public void onReceiveAd(Ad ad) {
//                    Log.e("StartApp Inter", "ReceiveAd");
//                    startAppAd.showAd();
//                }
//
//                @Override
//                public void onFailedToReceiveAd(Ad ad) {
//                    Log.e("StartApp Inter Ad", "Fail-" + ad.getErrorMessage());
//                }
//            });

        }
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
    public boolean isTablet() {
        return (getResources().getConfiguration().screenLayout & 15) >= 3;
    }


    /* access modifiers changed from: 0000 */
    public void highScoreDialogue() {
        runOnUiThread(new Runnable() {
            public void run() {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(1);
                dialog.setContentView(R.layout.mydialog_layout);
                dialog.show();
                Button button = (Button) dialog.findViewById(R.id.close_dialog);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        MainActivity.this.soundPool.play(MainActivity.this.mButtonID, 1.0f, 1.0f, 1, 0, 1.0f);
                        dialog.dismiss();
                    }
                });
                TextView textView = (TextView) dialog.findViewById(R.id.dialog_title);
                TextView textView2 = (TextView) dialog.findViewById(R.id.score_title);
                TextView textView3 = (TextView) dialog.findViewById(R.id.total_questions_current);
                TextView textView4 = (TextView) dialog.findViewById(R.id.correct_questions_current);
                TextView textView5 = (TextView) dialog.findViewById(R.id.wrong_questions_current);
                TextView textView6 = (TextView) dialog.findViewById(R.id.score_current);
                TextView textView7 = (TextView) dialog.findViewById(R.id.high_score_title);
                TextView textView8 = (TextView) dialog.findViewById(R.id.total_questions_high);
                TextView textView9 = (TextView) dialog.findViewById(R.id.correct_questions_high);
                TextView textView10 = (TextView) dialog.findViewById(R.id.wrong_questions_high);
                TextView textView11 = (TextView) dialog.findViewById(R.id.score_high);
                if (MainActivity.this.isTablet()) {
                    button.setTextSize(40.0f);
                    textView.setTextSize(45.0f);
                    textView2.setTextSize(40.0f);
                    textView3.setTextSize(34.0f);
                    textView4.setTextSize(34.0f);
                    textView5.setTextSize(34.0f);
                    textView6.setTextSize(34.0f);
                    textView7.setTextSize(40.0f);
                    textView8.setTextSize(34.0f);
                    textView9.setTextSize(34.0f);
                    textView10.setTextSize(34.0f);
                    textView11.setTextSize(34.0f);
                }
                textView6.setText(MainActivity.this.getResources().getString(R.string.points) + " : " + MainActivity.this.prefs.getMainScore());
                textView3.setText(MainActivity.this.getResources().getString(R.string.total_ques) + " : " + MainActivity.this.prefs.getTotalQuestions());
                textView4.setText(MainActivity.this.getResources().getString(R.string.correct_ans) + " : " + MainActivity.this.prefs.getCorrectQuestions());
                textView5.setText(MainActivity.this.getResources().getString(R.string.wrong_ans) + " : " + MainActivity.this.prefs.getWrongQuestions());
                textView11.setText(MainActivity.this.getResources().getString(R.string.points) + " : " + MainActivity.this.prefs.getMainHighScore());
                textView8.setText(MainActivity.this.getResources().getString(R.string.total_ques) + " : " + MainActivity.this.prefs.getTotalQuestionsHigh());
                textView9.setText(MainActivity.this.getResources().getString(R.string.correct_ans) + " : " + MainActivity.this.prefs.getCorrectQuestionsHigh());
                textView10.setText(MainActivity.this.getResources().getString(R.string.wrong_ans) + " : " + MainActivity.this.prefs.getWrongQuestionsHigh());
            }
        });
    }

    public boolean isOnline() {
        @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
    }
    private void moreAppsFromStore() {
        Intent bIntent1 = new Intent(ACTION_VIEW, Uri.parse(getString(R.string.playStoreLink)));
        startActivity(bIntent1);
    }

    @Override
    public void onBackPressed() {
        new MaterialDialog.Builder(this)
                .title("Thanks")
                .items(R.array.moreList)
                .itemsIds(R.array.moreIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        //Toast.makeText(MainActivity.this, which + ": " + text + ", ID = " + view.getId(), Toast.LENGTH_SHORT).show();
                        if (which == 0) {
                            moreAppsFromStore();
                        }
                        if (which == 1) {
                            Uri uri = Uri.parse("market://details?id=" + getPackageName());
                            Intent goToMarket = new Intent(ACTION_VIEW, uri);
                            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                            try {
                                startActivity(goToMarket);
                            } catch (Exception e) {
                                startActivity(new Intent(ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                            }
                        }
                        if (which == 2) {
                            String shareBody = "Download this app, rate with 5 Star and Share it with Your Friends. - https://play.google.com/store/apps/details?id=";
                            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody + getPackageName());
                            Log.e("Link", shareBody + getPackageName());
                            startActivity(Intent.createChooser(sharingIntent, "Share via"));

                        }
                        if (which == 3) {
                            sendNotification();
                            finishAffinity();
                        }


                    }
                })
                .show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.highscore /*2131624075*/:
                this.soundPool.play(this.mButtonID, 1.0f, 1.0f, 1, 0, 1.0f);
                highScoreDialogue();
                return;
            case R.id.playgame /*2131624076*/:
                this.soundPool.play(this.mMainButtonID, 1.0f, 1.0f, 1, 0, 1.0f);
//                if (!this.mInterstitialAd.isLoaded() || this.prefs.getRemoveads()) {
                    startActivity(new Intent(this, SelectorActivity.class));
                    return;
               // }
              //  GameOverActivity.SHOW_FULL_SCREEN_AD = false;
                //this.mInterstitialAd.show();
              //  return;

            case R.id.likeUS /*2131624078*/:

                return;
            case R.id.appinvite /*2131624081*/:
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent goToMarket = new Intent(ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (Exception e) {
                    startActivity(new Intent(ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }
                return;
            case R.id.moreApps /*2131624084*/:
                moreAppsFromStore();
                return;
            case R.id.about /*2131624087*/:

                String shareBody = "Download this app, rate with 5 Star and Share it with Your Friends. - https://play.google.com/store/apps/details?id=";
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody + getPackageName());
                Log.e("Link", shareBody + getPackageName());
                startActivity(Intent.createChooser(sharingIntent, "Share via"));


               // startActivity(new Intent(this, AboutUsActivity.class));
                return;
            case R.id.removeAds /*2131624090*/:
               // this.upgrade.doUpgrade();
                return;
            default:
                return;
        }
    }
    private void setupLayout() {
        @SuppressLint("WrongConstant") Display defaultDisplay = ((WindowManager) getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        int i = point.x;
        int i2 = point.y;
        this.cardplatbtn.setLayoutParams(new RelativeLayout.LayoutParams((i / 100) * 50, (i / 100) * 50));
        this.layoutParams = (RelativeLayout.LayoutParams) this.cardplatbtn.getLayoutParams();
        this.layoutParams.addRule(13);
        this.layoutParams = new RelativeLayout.LayoutParams((i / 100) * 25, (i2 / 100) * 7);
        this.layoutParams.addRule(14);
        this.layoutParams.setMargins(0, (i2 / 100) * 12, 0, 0);
        this.cardhighsbtn.setLayoutParams(this.layoutParams);
        if (!isTablet()) {
           // this.cardplatbtn.setTextSize(2, 22.0f);
          //  this.upper_btn.setTextSize(2, 20.0f);
            return;
        }
      //  this.new_game.setTextSize(2, 55.0f);
       // this.upper_btn.setTextSize(2, 40.0f);
    }




    @Override
    public void onDestroy() {
        //this.upgrade.releaseEP();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();

        loadInterstitial();

       // this.no_ads_key.setText("Full");
        //this.no_ads_img.setImageResource(R.drawable.no_ads_grey);
    }
    private void loadInterstitial() {
      //  mInterstitialAd = new InterstitialAd(this);
      //  mInterstitialAd.setAdUnitId(this.getResources().getString(R.string.inter_id));
        //.e("Inter ID", context.getResources().getString(R.string.inter_id));
        mInterstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice("EEE6142221A4416C64119C872C299C6D")
                .build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.e("Inter Ad", "Loaded");


            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                mInterstitialAd.loadAd(new AdRequest.Builder()
                        .addTestDevice("EEE6142221A4416C64119C872C299C6D")
                        .build());
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Log.e("Inter Ad Fail", String.valueOf(i));
                /*interstitialAd.loadAd(new AdRequest.Builder()
                        .addTestDevice("F9C11300C3CD356F82C1EB61104B234F")
                        .build());*/


            }
        });
    }

    private void requestNewInterstitial() {
        this.mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("EEE6142221A4416C64119C872C299C6D").build());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            String shareBody = "Download this app, rate with 5 Star and Share it with Your Friends. - https://play.google.com/store/apps/details?id=";
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody + getPackageName());
            Log.e("Link", shareBody + getPackageName());
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    public void sendNotification() {
        if (notification) {
            //  Intent intent = new Intent(ACTION_VIEW, Uri.parse("market://details?id=" + appLink));
            Intent intent = new Intent(ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=JK+TechPlus"));
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            name = getString(R.string.channel_name);
            NotificationChannel mChannel = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            }

            Notification notification =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("Get More Apps...")
                            .setContentText("Try More New Apps from our studio")
                            .setContentIntent(pendingIntent)
                            .setChannelId(CHANNEL_ID).build();

            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mNotificationManager.createNotificationChannel(mChannel);
            }

            mNotificationManager.notify(101, notification);
        } else {

            //  Intent intent = new Intent(ACTION_VIEW, Uri.parse(getString(R.string.playStoreLink)));
            Intent intent = new Intent(ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=JK+TechPlus"));
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            name = getString(R.string.channel_name);
            NotificationChannel mChannel = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            }

            Notification notification =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("Get More!")
                            .setContentText("Try More Exciting Apps")
                            .setContentIntent(pendingIntent)
                            .setChannelId(CHANNEL_ID).build();

            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mNotificationManager.createNotificationChannel(mChannel);
            }

            mNotificationManager.notify(101, notification);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
