package cool.lucasbedolla.swish.activities.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


/**
 * Created by Lucas Bedolla on 6/17/2017.
 */

public abstract class UnderTheHoodActivity extends AppCompatActivity implements View.OnClickListener {
    public Handler handler;

    //private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        //firebase :)
        //firebaseAnalytics = FirebaseAnalytics.getInstance(this);

    }


    //public FirebaseAnalytics getFirebaseInstance(){
    // return firebaseAnalytics;
    //}


}
