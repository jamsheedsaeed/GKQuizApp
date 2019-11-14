package jk.techplus.quiz.gkQuiz;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
//import com.startapp.android.publish.ads.banner.Banner;
//import com.startapp.android.publish.ads.banner.BannerListener;
//import com.startapp.android.publish.adsCommon.Ad;
//import com.startapp.android.publish.adsCommon.StartAppAd;
//import com.startapp.android.publish.adsCommon.StartAppSDK;
//import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;

public class Ads {

    private Boolean AdMob = false;
    private Context context;
    private Boolean StartApp = false;
    private InterstitialAd interstitialAd;
    private AdView adView;
  //  private StartAppAd startAppAd;

    public Ads(Context context, Boolean adMob) {
        this.context = context;
        this.AdMob = adMob;
        this.StartApp = StartApp;
        setupSDK(context, adMob);
    }

    private void setupSDK(Context context, Boolean adMob) {
        if (adMob) {
            MobileAds.initialize(context, context.getResources().getString(R.string.app_id));
            //Log.e("App ID", context.getResources().getString(R.string.app_id));
            loadInterstitial();
        }
//        if (startApp && Activity) {
//          //  StartAppSDK.init(context, context.getResources().getString(R.string.startApp_id), true);
//          //  StartAppAd.disableSplash();
//        //  loadInterstitial();
//        }

    }
    public void loadBanner(final RelativeLayout relativeLayout) {
        adView = new AdView(context);
        adView.setAdUnitId(context.getResources().getString(R.string.banner_id));
        // Log.e("Banner ID", context.getResources().getString(R.string.banner_id));
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.loadAd(new AdRequest.Builder()
                .addTestDevice("EEE6142221A4416C64119C872C299C6D")
                .build());
        relativeLayout.addView(adView);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.e("AdMob Banner Ad", "Loaded");
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                relativeLayout.removeAllViews();
                Log.e("AdMob Banner Ad Fail", String.valueOf(i));
//                Banner banner = new Banner(context, new BannerListener() {
//                    @Override
//                    public void onReceiveAd(View view) {
//                        Log.e("StartApp Banner", "Receive");
//                    }
//
//                    @Override
//                    public void onFailedToReceiveAd(View view) {
//                        Log.e("On", "Fail");
//                    }
//
//                    @Override
//                    public void onClick(View view) {
//                        Log.e("On", "Click");
//                    }
//                });
//                relativeLayout.addView(banner);


            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                adView.loadAd(new AdRequest.Builder()
                        .addTestDevice("EEE6142221A4416C64119C872C299C6D")
                        .build());
                relativeLayout.addView(adView);


            }
        });
    }


    private void loadInterstitial() {
        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(context.getResources().getString(R.string.inter_id));
        //.e("Inter ID", context.getResources().getString(R.string.inter_id));
        interstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice("EEE6142221A4416C64119C872C299C6D")
                .build());
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.e("Inter Ad", "Loaded");

            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                interstitialAd.loadAd(new AdRequest.Builder()
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

    public void showInterstitial() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
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

}
