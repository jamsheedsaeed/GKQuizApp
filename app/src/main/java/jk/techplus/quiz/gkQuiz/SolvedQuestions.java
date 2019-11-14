package jk.techplus.quiz.gkQuiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SolvedQuestions extends AppCompatActivity {

    private QuestionDataSource qDataSource ;
    public MultipleChoice mcQuestion = new MultipleChoice();
    String category;
    private ArrayList<MultipleChoice> multipleChoiceList = new ArrayList<>();

    SolvedQuestionAdapter adapter;

    public static Ads ads;
    ListView solvedList;
    boolean notification;
    JSONObject object;
    String appLink;
   Toolbar mtoolbar;
    Vibrator vib;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solved_questions);

        ads = new Ads(this, true);
        solvedList = (ListView)findViewById(R.id.listviewsolved);

        this.qDataSource = new QuestionDataSource(this);
        Bundle extras = getIntent().getExtras();
        category = extras.getString("cat");



        this.qDataSource.open();
        Cursor c = qDataSource.getData(category);

        if (c.moveToFirst()) {
            do {
                // get the data into array, or class variable
                String question=c.getString(1);
                String answer=c.getString(6);
                mcQuestion.setQuestion(question);
                mcQuestion.setAnswer(answer);
                MultipleChoice p = new MultipleChoice(question,answer);
                multipleChoiceList.add(p);

            } while (c.moveToNext());
        }
        c.close();

        mtoolbar = (Toolbar)findViewById(R.id.toolbar);
        TextView mTitle = (TextView) mtoolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(mtoolbar);
        mTitle.setText(category);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        adapter = new SolvedQuestionAdapter(SolvedQuestions.this, multipleChoiceList);
        solvedList.setAdapter(adapter);


        RelativeLayout adView = findViewById(R.id.adViewban);
        // this.mAdView = (AdView) findViewById(R.id.adView);
        SolvedQuestions.ads.loadBanner(adView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ads.showInterstitial();
            }
        }, 10000);

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
}
