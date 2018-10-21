package cool.lucasbedolla.swish.activities.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.loglr.Interfaces.ExceptionHandler;
import com.tumblr.loglr.Interfaces.LoginListener;
import com.tumblr.loglr.LoginResult;
import com.tumblr.loglr.Loglr;

import java.util.ArrayList;
import java.util.List;

import cool.lucasbedolla.swish.R;
import cool.lucasbedolla.swish.activities.MainActivity;
import cool.lucasbedolla.swish.core.UnderTheHoodActivity;
import cool.lucasbedolla.swish.util.Constants;
import cool.lucasbedolla.swish.util.MyPrefs;

public class OnboardingActivity extends UnderTheHoodActivity implements OnClickListener {

    public static final String CALLBACK_URL = "gem://www.gem.com/ok";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (MyPrefs.getIsLoggedIn(OnboardingActivity.this)) {
            Intent intent = new Intent(OnboardingActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_onboarding);

        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        ImageView animView = findViewById(R.id.bgn_anim);
        animView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_anim));

        findViewById(R.id.go).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        login();
    }

    private void login() {
        final Handler handler = new Handler();
        try {
            Loglr.INSTANCE
                    .setConsumerKey(Constants.CONSUMER_KEY)
                    .setConsumerSecretKey(Constants.CONSUMER_SECRET)
                    .setLoginListener(new LoginListener() {
                        @Override
                        public void onLoginSuccessful(final LoginResult loginResult) {

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    MyPrefs.setOAuthToken(OnboardingActivity.this, loginResult.getOAuthToken());
                                    MyPrefs.setOAuthTokenSecret(OnboardingActivity.this, loginResult.getOAuthTokenSecret());
                                    MyPrefs.setIsLoggedIn(OnboardingActivity.this, true);

                                    JumblrClient client = new JumblrClient(Constants.CONSUMER_KEY,
                                            Constants.CONSUMER_SECRET,
                                            loginResult.getOAuthToken(),
                                            loginResult.getOAuthTokenSecret());

                                    MyPrefs.setCurrentBlog(OnboardingActivity.this, client.user().getName());
                                    MyPrefs.setCurrentUser(OnboardingActivity.this, client.user().getName());
                                    List<Blog> blogs = client.user().getBlogs();

                                    List<String> blogsNames = new ArrayList<>();
                                    for (Blog blog : blogs) {
                                        blogsNames.add(blog.getName());
                                    }
                                    String jsonBlog = new Gson().toJson(blogsNames, List.class);

                                    MyPrefs.setBlogNames(OnboardingActivity.this, jsonBlog);
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(OnboardingActivity.this, MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    });
                                }
                            }).start();

                        }
                    })
                    .setExceptionHandler(new ExceptionHandler() {
                        @Override
                        public void onLoginFailed(RuntimeException exception) {
                            Toast.makeText(OnboardingActivity.this, "Login failed. Please try again!", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setUrlCallBack(CALLBACK_URL)
                    .initiate(this);
        } catch (Exception e) {
            Toast.makeText(this, "An error occurred.", Toast.LENGTH_SHORT).show();
        }
    }
}
