package cool.lucasleabres.ruby.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tumblr.loglr.Interfaces.ExceptionHandler;
import com.tumblr.loglr.Interfaces.LoginListener;
import com.tumblr.loglr.LoginResult;
import com.tumblr.loglr.Loglr;

import cool.lucasleabres.ruby.R;
import cool.lucasleabres.ruby.util.Constants;
import cool.lucasleabres.ruby.util.PrefsManager;

public class LoginActivity extends AppCompatActivity implements ExceptionHandler, LoginListener {

    public static final String CALLBACK_URL = "gem://www.gem.com/ok";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PrefsManager.getIsLoggedIn(this)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            setContentView(R.layout.activity_login);
            login();
        }
    }

    private void login() {
        try {
            Loglr.getInstance()
                    .setConsumerKey(Constants.CONSUMER_KEY)
                    .setConsumerSecretKey(Constants.CONSUMER_SECRET)
                    .setLoginListener(this)
                    .setExceptionHandler(this)
                    .setUrlCallBack(CALLBACK_URL)
                    .initiateInActivity(this);
        } catch (Exception e) {
            Toast.makeText(this, "exception caught", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoginSuccessful(LoginResult loginResult) {
        PrefsManager.setOAuthToken(this, loginResult.getOAuthToken());
        PrefsManager.setOAuthTokenSecret(this, loginResult.getOAuthTokenSecret());
        PrefsManager.setIsLoggedIn(this, true);

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFailed(RuntimeException exception) {
        Toast.makeText(this, "Login failed. Please try again!", Toast.LENGTH_LONG).show();
    }
}
