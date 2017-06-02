package lucasleabres.ruby.paid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.Post;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cool.lucasbedolla.ruby.R;

public class ProfileActivity extends AppCompatActivity {

    public static final String TAG = "PROFILE_ACTIVITY";
    public Handler handler;
    public String blogName;
    public List<Post> userPosts;
    public List<MyModel> globalList;
    public RecyclerAdapter mRecyclerAdapter;

    private String token;
    private String token_secret;



    @Bind(R.id.profile_recycler)
    RecyclerView mRecyclerView;
    @Bind(R.id.profile_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.profile_pic)
    ImageView profPic;
    @Bind(R.id.profile_progress)
    ProgressBar prog;
    @Bind(R.id.profile_name)
    TextView profName;
    @Bind(R.id.profile_menu)
    ImageButton profMenu;
    @Bind(R.id.right_drawer)
    ListView menuList;
    @Bind(R.id.d_layout)
    DrawerLayout dLayout;
    @Bind(R.id.profile_back)
    ImageView mBack;
    @Bind(R.id.description)
    TextView description;
    private String[] menuItems =
            new String[]{"Ask", "Message", "Likes", "Submit"};
    private View.OnClickListener layoutToggler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (dLayout.isDrawerOpen(GravityCompat.END)) {
                dLayout.closeDrawer(GravityCompat.END);
            } else {
                dLayout.openDrawer(GravityCompat.END);
            }

        }
    };
    private View.OnClickListener backer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //go back
            onBackPressed();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        handler = new Handler();
        //getblog name from resource
        String blogPicUrl = getIntent().getExtras().getString("pic");
        blogName = getIntent().getExtras().getString("blog");
        profName.setText(blogName);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/aleo.ttf");
        profName.setTypeface(face);
       // mToolbar.setTitle(blogName);
        Picasso.with(this)
                .load(blogPicUrl)
                .placeholder(R.drawable.loadingshadow)
                .error(R.drawable.errorimage)
                .fit()
                .into(profPic);
        NetworkChecker checker = new NetworkChecker(this);
        AlertDialog dialog = checker.isConnected();
        if(dialog!=null){
            dialog.show();
        }else{
            //party!
            setupRecyclerView();
        }

        menuList.setAdapter(new MyAdapter(this));
        profMenu.setOnClickListener(layoutToggler);
        mBack.setOnClickListener(backer);

    }

    @Override public void onBackPressed (){
        super.onBackPressed();
    }


    private void setupRecyclerView() {

        getTokens();

        new Thread(new Runnable() {
            @Override
            public void run() {

                //jumblr stuff
                Log.d(TAG,"setting up jumblr client");
                JumblrClient client = new JumblrClient(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
                Log.d(TAG,"setting jumblr tokens");
                client.setToken(token, token_secret);
                //get user posts
                userPosts = client.blogPosts(blogName);
                Log.d(TAG, "post list size: "+userPosts.size());

                Blog blog = client.blogInfo(blogName);
                final String desc =  blog.getDescription();

                //looper must be called in order to handle handler messages to main thread
                Looper.prepare();


                //note: handler must be created in related thread (and is)
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        description.setText(desc);
                        setUpRecyclerView(userPosts);
                        Log.d(TAG, "setuprecyclerview has been called with globallist with size of: " + userPosts.size());
                    }
                });
                //end of thread
            }
        }).start();

    }


    private void setUpRecyclerView(List<Post> model) {
        //remove progress bar
        prog.setVisibility(View.GONE);

        // pull to refresh layout config
       /*
        refreshLayout.setVisibility(View.VISIBLE);
        refreshLayout.setColorSchemeResources(R.color.lightRuby, R.color.red, R.color.crimson);
        refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        refreshLayout.setProgressViewOffset(false,0,225);
        refreshLayout.setOnRefreshListener(refreshListener);
        */
        //recyclerview setup

        mRecyclerView = (RecyclerView) findViewById(R.id.profile_recycler);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerAdapter = new RecyclerAdapter(PostSorter.returnCasted(model), mRecyclerView);
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    //nav adapter class
    class MyAdapter extends BaseAdapter{
        String[]navBar;
        int[] images = {
                R.drawable.ask, R.drawable.white_likes, R.drawable.white_message, R.drawable.submit
        };
        private Context context;

        public MyAdapter(Context context){
            this.context = context;
            navBar = menuItems;
            this.context.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return navBar.length;
        }

        @Override
        public Object getItem(int position) {
            return navBar[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = null;
            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row =  inflater.inflate(R.layout.profile_menu, null);

            }else{
                row = convertView;
            }
            switch (navBar[position]){
                case "Ask": row.setBackgroundResource(R.color.notification_green);
                    break;
                case "Message": row.setBackgroundResource(R.color.liked_red);
                    break;
                case "Likes": row.setBackgroundResource(R.color.orange);
                    break;
                case "Submit": row.setBackgroundResource(R.color.search_blue);
                    break;

            }

            TextView tv = (TextView) row.findViewById(R.id.row_text);
            ImageView iv = (ImageView) row.findViewById(R.id.row_pic);
            tv.setText(navBar[position]);
            iv.setImageResource(images[position]);
            return row;
        }
    }

    private void getTokens() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        token = preferences.getString("access_token", null);
        Log.d(TAG, "1, token: " + token);
        token_secret = preferences.getString("access_token_secret", null);
        Log.d(TAG, "1, token secret: " + token_secret);
    }
}
