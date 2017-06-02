package cool.lucasleabres.ruby.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tumblr.loglr.Interfaces.ExceptionHandler;
import com.tumblr.loglr.Interfaces.LoginListener;
import com.tumblr.loglr.LoginResult;
import com.tumblr.loglr.Loglr;

import cool.lucasleabres.ruby.util.Constants;
import cool.lucasleabres.ruby.R;

public class LoglrLogin extends AppCompatActivity implements ExceptionHandler,LoginListener
{

    public static final String CALLBACK_URL = "gem://www.gem.com/ok";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loglr_login);

        try{
            Loglr.getInstance()
                    .setConsumerKey(Constants.CONSUMER_KEY)
                    .setConsumerSecretKey(Constants.CONSUMER_SECRET)
                    .setLoginListener(this)
                    .setExceptionHandler(this)
                    .setUrlCallBack(CALLBACK_URL)
                    .initiateInActivity(this);
        }catch (Exception e){
            Toast.makeText(this,"exception caught",Toast.LENGTH_SHORT).show();
        }

    }

    private void login()
    {
        try{
            Loglr.getInstance()
                    .setConsumerKey(Constants.CONSUMER_KEY)
                    .setConsumerSecretKey(Constants.CONSUMER_SECRET)
                    .setLoginListener(this)
                    .setExceptionHandler(this)
                    .setUrlCallBack(CALLBACK_URL)
                    .initiateInActivity(this);
        }catch (Exception e){
            Toast.makeText(this,"exception caught",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoginSuccessful(LoginResult loginResult) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("access_token", loginResult.getOAuthToken());
        editor.putString("access_token_secret", loginResult.getOAuthTokenSecret());
        editor.apply();
        Intent intent = new Intent(LoglrLogin.this, MyLauncher.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFailed(RuntimeException exception) {
        Toast.makeText(this,"Login failed. Please try again!",Toast.LENGTH_LONG).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                login();
            }
        },3000);

    }
}
