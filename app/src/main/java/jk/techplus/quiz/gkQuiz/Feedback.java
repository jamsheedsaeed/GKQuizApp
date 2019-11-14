package jk.techplus.quiz.gkQuiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;


public class Feedback extends Activity {

    CardView cardfeedback;
    EditText edtEmail, edtDescription;
    String valEmail, valFeedback;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase;
    Context context =this;
    boolean isTrue = false;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public static Ads ads;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_activity);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        this.cardfeedback = (CardView) findViewById(R.id.feedbackbtn);
        this.edtEmail = (EditText) findViewById(R.id.EditText_Enter_your_Email);
        this.edtDescription = (EditText) findViewById(R.id.EditText_FeedbackBody);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        ads = new Ads(this, true);

        RelativeLayout adViewRectangle = findViewById(R.id.adViewfeedback);
//
//       requestAd();
         ads.showInterstitial();

        Feedback.ads.loadBanner(adViewRectangle);


        cardfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valEmail = edtEmail.getText().toString();
                valFeedback = edtDescription.getText().toString();
                if( valEmail.length() == 0  ){
                    edtEmail.setError( "Email Required!" );
                }
                else if(valFeedback.length() == 0){
                    edtDescription.setError( "Feedback Required!" );
                }else {
                    if(!isValidEmailId(valEmail)){
                        Toast.makeText(context, "Email Address is Invalid!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(isOnline()){
                        SendFeedback(valEmail,valFeedback);
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

//                    if(isTrue){
//                        edtEmail.setText("");
//                        edtDescription.setText("");
//                        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
//                                .setTitleText("Good job!")
//                                .setContentText("You clicked the button!")
//                                .show();
//                    }
                }

            }
        });


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

    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
    public void SendFeedback(String email , final String Feedback){
        UserFeedback feedback = new UserFeedback(email,Feedback);

      //  mDatabase.child("userfeedback").setValue(feedback);
       // mDatabase.push().child("userfeedback").setValue(feedback);

        //Push Creates a Unique Id in database Everytime when user enters new Feedback
        mDatabase.push().child("userfeedback").setValue(feedback, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e("ERROR", "Data could not be saved " + databaseError.getMessage());
                    Toast.makeText(context, "Please Check Your Internet Connection", Toast.LENGTH_LONG).show();
                   // isTrue = false;
                } else {
                   // isTrue = true;
                    edtEmail.setText("");
                        edtDescription.setText("");
//                        new SweetAlertDialog(context)
//                                .setTitleText("Good job!")
//                                .setContentText("You clicked the button!")
//                                .show();
                    Toast.makeText(context, "Thanks For Providing Your Valueable Feedback!", Toast.LENGTH_LONG).show();
                    Log.e("SUCCESS", "Data saved successfully.");
                }
            }



        });

      //  return isTrue;
    }
}
