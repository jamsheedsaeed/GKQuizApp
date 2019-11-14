package jk.techplus.quiz.gkQuiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewCategories extends AppCompatActivity {

    ListView listView;
    ArrayList<category> items = new ArrayList<>();
    public static Ads ads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_categories);

        ads = new Ads(this, true);


        listView = (ListView)findViewById(R.id.listviewcat);


       String[] item = getResources().getStringArray(R.array.category_names);

        for(int i= 1 ; i < item.length;i++){
            items.add(new category((item[i])));
        }


        CustomListAdapter adapter = new CustomListAdapter(this, items);

// get the ListView and attach the adapter
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ViewCategories.this, SolvedQuestions.class);
                intent.putExtra("cat",items.get(position).itemName);
                startActivity(intent);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ads.showInterstitial();
            }
        }, 3000);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            String shareBody = "Download this app, rate with 5 Star and Share it with Your Friends. - https://play.google.com/store/apps/details?id=";
//            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//            sharingIntent.setType("text/plain");
//            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
//            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody + getPackageName());
//            Log.e("Link", shareBody + getPackageName());
//            startActivity(Intent.createChooser(sharingIntent, "Share via"));
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

}
