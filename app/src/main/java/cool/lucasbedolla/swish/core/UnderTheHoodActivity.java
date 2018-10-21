package cool.lucasbedolla.swish.core;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


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

}
