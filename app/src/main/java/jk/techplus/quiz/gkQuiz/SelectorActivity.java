package jk.techplus.quiz.gkQuiz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.internal.Constants;

import java.util.ArrayList;



public class SelectorActivity extends Activity implements View.OnClickListener {

    private static final int SOUND_BUTTON = 2131099648;
    /* access modifiers changed from: private */
    public static SparseBooleanArray checked = null;
    private Button back_button ;
    private RelativeLayout btn_layout;
    private TextView high_Score;
    private RelativeLayout.LayoutParams layoutParams;
    private ArrayList<String> list;
    private ArrayAdapter listAdapter;
    private int listSize;
    /* access modifiers changed from: private */
    public ListView listView;
    private RelativeLayout mAdView;
    /* access modifiers changed from: private */
    public int mButtonID;
    InterstitialAd mInterstitialAd;
    private RelativeLayout modeLayout;
    private RelativeLayout mode_head;
    private TextView mode_head_text;
    public static Ads ads;
    Context context = this;
    /* access modifiers changed from: private */

    /* renamed from: ph */
    public PreferencesHandler f32ph;
    /* access modifiers changed from: private */
    public SoundPool soundPool;
    private Button start_button;

    public boolean isTablet() {
        return (getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    private void init() {
        int i;
        String[] stringArray = getResources().getStringArray(R.array.category_names);
        this.listSize = stringArray.length;
        this.list = new ArrayList<>();
        for (int i2 = 0; i2 < this.listSize; i2++) {
            this.list.add(stringArray[i2]);
        }
        this.listAdapter = new ArrayAdapter(this, 17367056, this.list) {
            public View getView(int i, View view, ViewGroup viewGroup) {
                View view2 = super.getView(i, view, viewGroup);
//                CheckedTextView checkedTextView = (CheckedTextView) view2.findViewById(R.id.control);
//                if (SelectorActivity.this.isTablet()) {
//                    checkedTextView.setTextSize(1, 34.0f);
//                    return view2;
//                }
//                checkedTextView.setTextSize(1, 17.0f);
                return view2;
            }
        };
        this.listView = (ListView) findViewById(R.id.listView);
        this.listView.setAdapter(this.listAdapter);
        this.listView.setChoiceMode(2);
        this.listView.setItemsCanFocus(true);
        if (checked != null) {
            int i3 = 0;
            int i4 = 0;
            while (i4 < this.listSize) {
                if (checked.get(i4)) {
                    this.listView.setItemChecked(i4, true);
                    if (i3 == 0) {
                        i = i4;
                        i4++;
                        i3 = i;
                    }
                }
                i = i3;
                i4++;
                i3 = i;
            }
            this.listView.smoothScrollToPosition(i3);
        } else {
            this.listView.setItemChecked(0, true);
        }
        this.listView.setSmoothScrollbarEnabled(true);
        checked = this.listView.getCheckedItemPositions();
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                SelectorActivity.this.soundPool.play(SelectorActivity.this.mButtonID, 1.0f, 1.0f, 1, 0, 1.0f);
                if (!SelectorActivity.this.f32ph.getRemoveads() && SelectorActivity.this.mInterstitialAd.isLoaded()) {
                    GameOverActivity.SHOW_FULL_SCREEN_AD = false;
                    SelectorActivity.this.mInterstitialAd.show();
                }
                CheckedTextView checkedTextView = (CheckedTextView) view;
                if (checkedTextView.getText().equals("All")) {
                    if (checkedTextView.isChecked()) {
                        SelectorActivity.this.listView.clearChoices();
                        SelectorActivity.this.listView.requestLayout();
                        SelectorActivity.this.listView.setItemChecked(i, true);
                    } else {
                        SelectorActivity.this.listView.setItemChecked(i, false);
                    }
                } else if (checkedTextView.isChecked()) {
                    SelectorActivity.this.listView.setItemChecked(0, false);
                    SelectorActivity.this.listView.setItemChecked(i, true);
                } else {
                    SelectorActivity.this.listView.setItemChecked(i, false);
                }
                if (SelectorActivity.this.listView.getCheckedItemCount() == 0) {
                    SelectorActivity.this.listView.setItemChecked(0, true);
                }
                SelectorActivity.checked = SelectorActivity.this.listView.getCheckedItemPositions();
            }
        });
        this.start_button = (Button) findViewById(R.id.start);
        this.back_button = (Button) findViewById(R.id.back);
        this.high_Score = (TextView) findViewById(R.id.high_score);
        this.high_Score.setText(getResources().getString(R.string.high_score) + " " + this.f32ph.getMainHighScore() + " " + getResources().getString(R.string.points));
        this.modeLayout = (RelativeLayout) findViewById(R.id.infoLayout);
        this.btn_layout = (RelativeLayout) findViewById(R.id.btn_layout);
        this.mode_head = (RelativeLayout) findViewById(R.id.mode_head);
        this.mode_head_text = (TextView) findViewById(R.id.mode_head_text);
        setupLayout();

        this.back_button.setOnClickListener(this);
    }
    private void setupLayout() {
        @SuppressLint("WrongConstant") Display defaultDisplay = ((WindowManager) getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        int i = point.x;
        int i2 = point.y;
        this.layoutParams = new RelativeLayout.LayoutParams(-1, 800);
        this.layoutParams.addRule(13);
        this.layoutParams.setMargins((i / 100) * 2, 0, (i / 100) * 2, 0);
        this.modeLayout.setLayoutParams(this.layoutParams);
        int i3 = (i2 / 100) * 60;
        this.layoutParams = new RelativeLayout.LayoutParams(-1, (i3 / 100) * 20);
        this.layoutParams.addRule(20);
        this.layoutParams.setMargins(0, 0, 0, 0);
        this.mode_head.setLayoutParams(this.layoutParams);
        this.layoutParams = new RelativeLayout.LayoutParams(-1, ((i3 * 4) / 100) * 20);
        this.layoutParams.addRule(3, R.id.mode_head);
        this.layoutParams.setMargins(10, 0, 10, 0);
        this.listView.setLayoutParams(this.layoutParams);
        this.layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        this.layoutParams.addRule(20);
        this.layoutParams.addRule(3, R.id.infoLayout);
        this.layoutParams.setMargins((i / 100) * 2, 0, (i / 100) * 2, 0);
        this.btn_layout.setLayoutParams(this.layoutParams);
        this.layoutParams = new RelativeLayout.LayoutParams((i / 100) * 30, (i2 / 100) * 7);
        this.layoutParams.addRule(9);
        this.back_button.setLayoutParams(this.layoutParams);
        this.layoutParams = new RelativeLayout.LayoutParams((i / 100) * 30, (i2 / 100) * 7);
        this.layoutParams.addRule(11);
        this.start_button.setLayoutParams(this.layoutParams);
        if (isTablet()) {
            this.mode_head_text.setTextSize(2, 50.0f);
            this.start_button.setTextSize(2, 45.0f);
            this.back_button.setTextSize(2, 45.0f);
            this.high_Score.setTextSize(2, 45.0f);
        }
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (view.getId()) {

            case R.id.back /*2131624110*/:
                this.soundPool.play(this.mButtonID, 1.0f, 1.0f, 1, 0, 1.0f);
                onBackPressed();
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    @SuppressLint("WrongConstant")
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.mode_selector);
        this.f32ph = new PreferencesHandler(this);
        this.mInterstitialAd = new InterstitialAd(this);
        this.mInterstitialAd.setAdUnitId(getResources().getString(R.string.inter_id));

        this.soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        this.mButtonID = this.soundPool.load(this, R.raw.button_sound, 1);
        ads = new Ads(this, true);
        this.mAdView = (RelativeLayout) findViewById(R.id.adView);
        SelectorActivity.ads.loadBanner(mAdView);

        start_button = (Button)findViewById(R.id.start);
        this.start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(mButtonID, 1.0f, 1.0f, 1, 0, 1.0f);
                if(isOnline()){
                    checked = listView.getCheckedItemPositions();
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < listSize; i++) {
                        if (checked.get(i)) {
                            arrayList.add(getResources().getTextArray(R.array.category_names)[i].toString());
                        }
                    }
                    Intent intent = new Intent(SelectorActivity.this, QuestionnaireActivity.class);
                    intent.putStringArrayListExtra("SELECTIONS", arrayList);
                    startActivity(intent);
                    finish();
                }else{

                    try {
                        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

                        alertDialog.setTitle("Info");
                        alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
                        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });

                        alertDialog.show();
                    } catch (Exception e) {

                    }
                }


            }
        });
        init();
    }

    private void requestNewInterstitial() {
        this.mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
    }

    public boolean isOnline() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;

        return connected;
    }
    @Override
    public void onDestroy() {
       // this.mAdView.destroy();
        super.onDestroy();
    }

    @Override
    public void onPause() {
//        if (!this.f32ph.getRemoveads()) {
//            this.mAdView.pause();
//        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (!this.f32ph.getRemoveads()) {
//            if (isOnline() ) {
//                requestNewInterstitial();
//            }
//            this.mAdView.resume();
//        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
