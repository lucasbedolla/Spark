package lucasleabres.ruby.free;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.EMVideoView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.AnswerPost;
import com.tumblr.jumblr.types.ChatPost;
import com.tumblr.jumblr.types.LinkPost;
import com.tumblr.jumblr.types.Photo;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.PhotoSize;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.QuotePost;
import com.tumblr.jumblr.types.TextPost;
import com.tumblr.jumblr.types.UnknownTypePost;
import com.tumblr.jumblr.types.Video;
import com.tumblr.jumblr.types.VideoPost;

import java.util.List;

import cool.lucasbedolla.ruby.R;

/**
 * Created by LUCASVENTURES on 5/9/2016.
 */

public class ViewHolderSetup {

    public static final String TAG = "VIEWHOLDERSETUP";
    private Object object;
    private Context context;
    private int position;
    private int type;
    private List<Object> list;
    private String authToken;
    private String authTokenSecret;
    private AppCompatActivity compat;
    private String picUrl;
    private String blogName;


    private RecyclerAdapter.PhotosetViewHolder photosetHolder;
    private RecyclerAdapter.TextViewHolder textHolder;
    private RecyclerAdapter.AnswerViewHolder answerHolder;
    private RecyclerAdapter.QuoteViewHolder quoteHolder;
    private RecyclerAdapter.VideoViewHolder videoHolder;
    private RecyclerAdapter.AudioViewHolder audioHolder;
    private RecyclerAdapter.ChatViewHolder chatHolder;
    private RecyclerAdapter.LinkViewHolder linkHolder;
    private RecyclerAdapter.UnknownPostViewHolder unknownHolder;

    private Handler handler;

    /*
    type int's are in same order as above (top, down) from 0 to 8.
     */
    ViewHolderSetup(Context context, Object object, int type, int position, List<Object> list, Handler handler) {
        //type determineds casting of object
        this.list = list;
        this.position = position;
        this.type = type;
        this.context = context;
        this.object = object;
        this.handler = handler;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        authToken = prefs.getString("access_token", null);
        authTokenSecret = prefs.getString("access_token_secret", null);
    }

    ViewHolderSetup(Context context, Object object, int type, int position, List<Object> list, Handler handler, AppCompatActivity compat) {
        //type determineds casting of object
        this.list = list;
        this.position = position;
        this.type = type;
        this.context = context;
        this.object = object;
        this.handler = handler;
        this.compat = compat;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        authToken = prefs.getString("access_token", null);
        authTokenSecret = prefs.getString("access_token_secret", null);
    }

    public void setBasicFunctions() {

        switch (type) {
            case 0:

                PhotoPost photoPost = (PhotoPost) list.get(position);
                if (!photoPost.isPhotoset()) {


                    photosetHolder = (RecyclerAdapter.PhotosetViewHolder) object;

                    photosetHolder.vImage1.setVisibility(View.GONE);
                    photosetHolder.vImage2.setVisibility(View.GONE);
                    photosetHolder.vImage3.setVisibility(View.GONE);
                    photosetHolder.vImage4.setVisibility(View.GONE);
                    photosetHolder.vImage5.setVisibility(View.GONE);
                    photosetHolder.vImage6.setVisibility(View.GONE);
                    photosetHolder.vImage7.setVisibility(View.GONE);
                    photosetHolder.vImage8.setVisibility(View.GONE);
                    photosetHolder.vImage9.setVisibility(View.GONE);


                    setUpButtons(photoPost, photosetHolder);

                    if (photoPost.getSourceTitle() != null) {
                        //post is from person you dont follow

                        //LOAD SOURCE BLOG NAME
                        photosetHolder.vSource.setText(photoPost.getSourceTitle());
                        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/aleo.ttf");
                        photosetHolder.vSource.setTypeface(face);
                        //LOAD REBLOGGER NAME
                        photosetHolder.vReblogger.setText(photoPost.getBlogName());
                        photosetHolder.vReblogger.setTypeface(face);
                        //loads source pic


                    } else {
                        //post is from person you do follow

                        SharedPreferences preferences =
                                PreferenceManager.getDefaultSharedPreferences(context);
                        if (preferences.getBoolean("grid", false)) {
                            photosetHolder.vSource.setTextSize(10f);
                        } else {
                            photosetHolder.vSource.setTextSize(20f);
                            photosetHolder.vSource.setGravity(Gravity.CENTER_VERTICAL);
                        }
                        photosetHolder.vSource.setText(photoPost.getBlogName());
                        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/aleo.ttf");
                        photosetHolder.vSource.setTypeface(face);
                        photosetHolder.vSource.setTextColor(context.getResources().getColor(R.color.liked_red));
                        photosetHolder.vFollow.setVisibility(View.GONE);
                        photosetHolder.vReblogSign.setVisibility(View.GONE);
                        photosetHolder.vReblogger.setVisibility(View.GONE);
                    }
                    //content image
                    final List<PhotoSize> sizes = photoPost.getPhotos().get(0).getSizes();
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

                    if (prefs.getBoolean("cache", false)) {
                        Picasso.with(context)
                                .load(sizes.get(1).getUrl())
                                .placeholder(R.drawable.loadingshadow)
                                .error(R.drawable.loadingshadow)
                                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                                //.resize(sizes.get(3).getWidth(),sizes.get(3).getHeight())
                                .into(photosetHolder.vImage);

                        new SuperClickListener(photosetHolder.vImage, 1, photoPost, compat);

                    } else {
                        Picasso.with(context)
                                .load(sizes.get(0).getUrl())
                                .placeholder(R.drawable.loadingshadow)
                                .error(R.drawable.loadingshadow)
                                //.memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
                                //.networkPolicy(NetworkPolicy.NO_CACHE,NetworkPolicy.NO_STORE)
                                //.resize(sizes.get(3).getWidth(),sizes.get(3).getHeight())
                                .into(photosetHolder.vImage);

                        new SuperClickListener(photosetHolder.vImage, 1, photoPost, compat);
                    }


                    if (photoPost.getCaption() == null) {
                        //is already gone
                    } else {
                        photosetHolder.vTitle.setVisibility(View.VISIBLE);
                        photosetHolder.vTitle.setText(Html.fromHtml(photoPost.getCaption()));
                        photosetHolder.vTitle.setTextColor(Color.BLACK);
                    }

                    break;

                } else {


            /*
           is photoset
             */


                    photosetHolder = (RecyclerAdapter.PhotosetViewHolder) object;

                    List<Photo> photos = photoPost.getPhotos();

                    int setSize = photos.size();
                    int grid;
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
                    if (sp.getBoolean(("grid"), false)) {
                        //sized down for smaller grid view
                        grid = 2;
                    } else {
                        //original size
                        grid = 0;
                    }

                    switch (setSize) {
                        case 2:
                            picassoHelper(context, photos.get(0).getSizes().get(grid).getUrl(), photosetHolder.vImage);
                            picassoHelper(context, photos.get(1).getSizes().get(grid).getUrl(), photosetHolder.vImage1);
                            new SuperClickListener(photosetHolder.vImage, 1, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage1, 2, photoPost, compat);


                            photosetHolder.vImage2.setVisibility(View.GONE);
                            photosetHolder.vImage3.setVisibility(View.GONE);
                            photosetHolder.vImage4.setVisibility(View.GONE);
                            photosetHolder.vImage5.setVisibility(View.GONE);
                            photosetHolder.vImage6.setVisibility(View.GONE);
                            photosetHolder.vImage7.setVisibility(View.GONE);
                            photosetHolder.vImage8.setVisibility(View.GONE);
                            photosetHolder.vImage9.setVisibility(View.GONE);
                            break;
                        case 3:
                            picassoHelper(context, photos.get(0).getSizes().get(grid).getUrl(), photosetHolder.vImage);
                            picassoHelper(context, photos.get(1).getSizes().get(grid).getUrl(), photosetHolder.vImage1);
                            picassoHelper(context, photos.get(2).getSizes().get(grid).getUrl(), photosetHolder.vImage2);
                            new SuperClickListener(photosetHolder.vImage, 1, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage1, 2, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage2, 3, photoPost, compat);


                            photosetHolder.vImage3.setVisibility(View.GONE);
                            photosetHolder.vImage4.setVisibility(View.GONE);
                            photosetHolder.vImage5.setVisibility(View.GONE);
                            photosetHolder.vImage6.setVisibility(View.GONE);
                            photosetHolder.vImage7.setVisibility(View.GONE);
                            photosetHolder.vImage8.setVisibility(View.GONE);
                            photosetHolder.vImage9.setVisibility(View.GONE);


                            break;
                        case 4:
                            picassoHelper(context, photos.get(0).getSizes().get(grid).getUrl(), photosetHolder.vImage);
                            picassoHelper(context, photos.get(1).getSizes().get(grid).getUrl(), photosetHolder.vImage1);
                            picassoHelper(context, photos.get(2).getSizes().get(grid).getUrl(), photosetHolder.vImage2);
                            picassoHelper(context, photos.get(3).getSizes().get(grid).getUrl(), photosetHolder.vImage3);
                            new SuperClickListener(photosetHolder.vImage, 1, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage1, 2, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage2, 3, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage3, 4, photoPost, compat);


                            photosetHolder.vImage4.setVisibility(View.GONE);
                            photosetHolder.vImage5.setVisibility(View.GONE);
                            photosetHolder.vImage6.setVisibility(View.GONE);
                            photosetHolder.vImage7.setVisibility(View.GONE);
                            photosetHolder.vImage8.setVisibility(View.GONE);
                            photosetHolder.vImage9.setVisibility(View.GONE);


                            break;
                        case 5:
                            picassoHelper(context, photos.get(0).getSizes().get(grid).getUrl(), photosetHolder.vImage);
                            picassoHelper(context, photos.get(1).getSizes().get(grid).getUrl(), photosetHolder.vImage1);
                            picassoHelper(context, photos.get(2).getSizes().get(grid).getUrl(), photosetHolder.vImage2);
                            picassoHelper(context, photos.get(3).getSizes().get(grid).getUrl(), photosetHolder.vImage3);
                            picassoHelper(context, photos.get(4).getSizes().get(grid).getUrl(), photosetHolder.vImage4);
                            new SuperClickListener(photosetHolder.vImage, 1, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage1, 2, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage2, 3, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage3, 4, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage4, 5, photoPost, compat);


                            photosetHolder.vImage5.setVisibility(View.GONE);
                            photosetHolder.vImage6.setVisibility(View.GONE);
                            photosetHolder.vImage7.setVisibility(View.GONE);
                            photosetHolder.vImage8.setVisibility(View.GONE);
                            photosetHolder.vImage9.setVisibility(View.GONE);

                            break;
                        case 6:
                            picassoHelper(context, photos.get(0).getSizes().get(grid).getUrl(), photosetHolder.vImage);
                            picassoHelper(context, photos.get(1).getSizes().get(grid).getUrl(), photosetHolder.vImage1);
                            picassoHelper(context, photos.get(2).getSizes().get(grid).getUrl(), photosetHolder.vImage2);
                            picassoHelper(context, photos.get(3).getSizes().get(grid).getUrl(), photosetHolder.vImage3);
                            picassoHelper(context, photos.get(4).getSizes().get(grid).getUrl(), photosetHolder.vImage4);
                            picassoHelper(context, photos.get(5).getSizes().get(grid).getUrl(), photosetHolder.vImage5);
                            new SuperClickListener(photosetHolder.vImage, 1, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage1, 2, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage2, 3, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage3, 4, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage4, 5, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage5, 6, photoPost, compat);


                            photosetHolder.vImage6.setVisibility(View.GONE);
                            photosetHolder.vImage7.setVisibility(View.GONE);
                            photosetHolder.vImage8.setVisibility(View.GONE);
                            photosetHolder.vImage9.setVisibility(View.GONE);

                            break;
                        case 7:
                            picassoHelper(context, photos.get(0).getSizes().get(grid).getUrl(), photosetHolder.vImage);
                            picassoHelper(context, photos.get(1).getSizes().get(grid).getUrl(), photosetHolder.vImage1);
                            picassoHelper(context, photos.get(2).getSizes().get(grid).getUrl(), photosetHolder.vImage2);
                            picassoHelper(context, photos.get(3).getSizes().get(grid).getUrl(), photosetHolder.vImage3);
                            picassoHelper(context, photos.get(4).getSizes().get(grid).getUrl(), photosetHolder.vImage4);
                            picassoHelper(context, photos.get(5).getSizes().get(grid).getUrl(), photosetHolder.vImage5);
                            picassoHelper(context, photos.get(6).getSizes().get(grid).getUrl(), photosetHolder.vImage6);
                            new SuperClickListener(photosetHolder.vImage, 1, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage1, 2, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage2, 3, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage3, 4, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage4, 5, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage5, 6, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage6, 7, photoPost, compat);


                            photosetHolder.vImage7.setVisibility(View.GONE);
                            photosetHolder.vImage8.setVisibility(View.GONE);
                            photosetHolder.vImage9.setVisibility(View.GONE);

                            break;
                        case 8:
                            picassoHelper(context, photos.get(0).getSizes().get(grid).getUrl(), photosetHolder.vImage);
                            picassoHelper(context, photos.get(1).getSizes().get(grid).getUrl(), photosetHolder.vImage1);
                            picassoHelper(context, photos.get(2).getSizes().get(grid).getUrl(), photosetHolder.vImage2);
                            picassoHelper(context, photos.get(3).getSizes().get(grid).getUrl(), photosetHolder.vImage3);
                            picassoHelper(context, photos.get(4).getSizes().get(grid).getUrl(), photosetHolder.vImage4);
                            picassoHelper(context, photos.get(5).getSizes().get(grid).getUrl(), photosetHolder.vImage5);
                            picassoHelper(context, photos.get(6).getSizes().get(grid).getUrl(), photosetHolder.vImage6);
                            picassoHelper(context, photos.get(7).getSizes().get(grid).getUrl(), photosetHolder.vImage7);
                            new SuperClickListener(photosetHolder.vImage, 1, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage1, 2, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage2, 3, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage3, 4, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage4, 5, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage5, 6, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage6, 7, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage7, 8, photoPost, compat);


                            photosetHolder.vImage8.setVisibility(View.GONE);
                            photosetHolder.vImage9.setVisibility(View.GONE);
                            break;
                        case 9:
                            picassoHelper(context, photos.get(0).getSizes().get(grid).getUrl(), photosetHolder.vImage);
                            picassoHelper(context, photos.get(1).getSizes().get(grid).getUrl(), photosetHolder.vImage1);
                            picassoHelper(context, photos.get(2).getSizes().get(grid).getUrl(), photosetHolder.vImage2);
                            picassoHelper(context, photos.get(3).getSizes().get(grid).getUrl(), photosetHolder.vImage3);
                            picassoHelper(context, photos.get(4).getSizes().get(grid).getUrl(), photosetHolder.vImage4);
                            picassoHelper(context, photos.get(5).getSizes().get(grid).getUrl(), photosetHolder.vImage5);
                            picassoHelper(context, photos.get(6).getSizes().get(grid).getUrl(), photosetHolder.vImage6);
                            picassoHelper(context, photos.get(7).getSizes().get(grid).getUrl(), photosetHolder.vImage7);
                            picassoHelper(context, photos.get(8).getSizes().get(grid).getUrl(), photosetHolder.vImage8);
                            new SuperClickListener(photosetHolder.vImage, 1, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage1, 2, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage2, 3, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage3, 4, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage4, 5, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage5, 6, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage6, 7, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage7, 8, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage8, 9, photoPost, compat);


                            photosetHolder.vImage9.setVisibility(View.GONE);

                            break;
                        case 10:
                            picassoHelper(context, photos.get(0).getSizes().get(grid).getUrl(), photosetHolder.vImage);
                            picassoHelper(context, photos.get(1).getSizes().get(grid).getUrl(), photosetHolder.vImage1);
                            picassoHelper(context, photos.get(2).getSizes().get(grid).getUrl(), photosetHolder.vImage2);
                            picassoHelper(context, photos.get(3).getSizes().get(grid).getUrl(), photosetHolder.vImage3);
                            picassoHelper(context, photos.get(4).getSizes().get(grid).getUrl(), photosetHolder.vImage4);
                            picassoHelper(context, photos.get(5).getSizes().get(grid).getUrl(), photosetHolder.vImage5);
                            picassoHelper(context, photos.get(6).getSizes().get(grid).getUrl(), photosetHolder.vImage6);
                            picassoHelper(context, photos.get(7).getSizes().get(grid).getUrl(), photosetHolder.vImage7);
                            picassoHelper(context, photos.get(8).getSizes().get(grid).getUrl(), photosetHolder.vImage8);
                            picassoHelper(context, photos.get(9).getSizes().get(grid).getUrl(), photosetHolder.vImage9);
                            new SuperClickListener(photosetHolder.vImage, 1, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage1, 2, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage2, 3, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage3, 4, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage4, 5, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage5, 6, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage6, 7, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage7, 8, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage8, 9, photoPost, compat);
                            new SuperClickListener(photosetHolder.vImage9, 10, photoPost, compat);
                    }

                    //caption

                    // photosetHolder.vTitle.setText(Html.fromHtml(post.getCaption()));


                    //String[] tokens = noPrefixStr.split("/");
                    if (photoPost.getCaption() == null) {
                        //already gone
                    } else {
                        photosetHolder.vTitle.setVisibility(View.VISIBLE);
                        photosetHolder.vTitle.setText(Html.fromHtml(photoPost.getCaption()));
                        photosetHolder.vTitle.setTextColor(Color.BLACK);
                    }


                    setUpButtons(photoPost, photosetHolder);

                    if (photoPost.getSourceTitle() != null) {
                        //post is from person you dont follow

                        //LOAD SOURCE BLOG NAME
                        photosetHolder.vSource.setText(photoPost.getSourceTitle());
                        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/aleo.ttf");
                        photosetHolder.vSource.setTypeface(face);
                        //LOAD REBLOGGER NAME
                        photosetHolder.vReblogger.setText(photoPost.getBlogName());
                        photosetHolder.vReblogger.setTypeface(face);
                        //loads source pic

                    }
                    //content image
                    final List<PhotoSize> sizes1 = photoPost.getPhotos().get(0).getSizes();
                    SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(context);

                    if (prefs1.getBoolean("cache", false)) {
                        Picasso.with(context)
                                .load(sizes1.get(1).getUrl())
                                .placeholder(R.drawable.loadingshadow)
                                .error(R.drawable.loadingshadow)
                                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                                //.resize(sizes.get(3).getWidth(),sizes.get(3).getHeight())
                                .into(photosetHolder.vImage);
                    } else {
                        Picasso.with(context)
                                .load(sizes1.get(1).getUrl())
                                .placeholder(R.drawable.loadingshadow)
                                .error(R.drawable.loadingshadow)
                                //.memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
                                //.networkPolicy(NetworkPolicy.NO_CACHE,NetworkPolicy.NO_STORE)
                                //.resize(sizes.get(3).getWidth(),sizes.get(3).getHeight())
                                .into(photosetHolder.vImage);
                    }

                }
                break;



            /*
            text
             */


            case 2:

                TextPost textPost = (TextPost) list.get(position);
                textHolder = (RecyclerAdapter.TextViewHolder) object;

                /*
                text setting

                 */
                if (textPost.getRebloggedFromName() != null) {
                    //reblogged from someone
                    textHolder.vSource.setText(textPost.getRebloggedFromName());
                    textHolder.vReblogger.setText(textPost.getTitle());
                } else {
                    //original post
                    textHolder.vSource.setText(textPost.getBlogName());
                    textHolder.vSource.setVisibility(View.GONE);
                    textHolder.vReblogSign.setVisibility(View.GONE);
                }


                //String[] tokens = noPrefixStr.split("/");
                if (textPost.getTitle() == null) {
                    textHolder.vTitle.setVisibility(View.GONE);
                } else {
                    textHolder.vTitle.setText(Html.fromHtml(textPost.getTitle()));
                    textHolder.vTitle.setTextColor(Color.BLACK);
                }

                if (textPost.getBody() == null) {
                    textHolder.vContent.setVisibility(View.GONE);
                } else {
                    textHolder.vContent.setText(Html.fromHtml(textPost.getBody()));
                    textHolder.vContent.setTextColor(Color.BLUE);
                }

                setUpButtons(textPost, textHolder);


                if (textPost.getSourceTitle() != null) {
                    //post is from person you dont follow

                    //LOAD SOURCE BLOG NAME
                    textHolder.vSource.setText(textPost.getSourceTitle());
                    Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/aleo.ttf");
                    textHolder.vSource.setTypeface(face);
                    //LOAD REBLOGGER NAME
                    textHolder.vReblogger.setText(textPost.getBlogName());
                    textHolder.vReblogger.setTypeface(face);
                    //loads source pic
                }
                break;


            /*
            answer post
             */


            case 3:
                final AnswerPost answerPost = (AnswerPost) list.get(position);
                answerHolder = (RecyclerAdapter.AnswerViewHolder) object;
                //setUpButtons(answerPost, answerHolder);
                answerHolder.vQuestion.setText(answerPost.getAskingName() + ": " + answerPost.getQuestion());
                answerHolder.vAnswer.setText(Html.fromHtml(answerPost.getAnswer()));
                answerHolder.vSource.setText(answerPost.getSourceTitle());


                break;
            case 4:


                /*
                quote
                 */


                final QuotePost quotePost = (QuotePost) list.get(position);
                quoteHolder = (RecyclerAdapter.QuoteViewHolder) object;


                if (quotePost.getRebloggedFromName() != null) {
                    //reblogged from someone
                    quoteHolder.vSource.setText(quotePost.getRebloggedFromName());
                    quoteHolder.vReblogger.setText(quotePost.getSourceTitle());
                } else {
                    //original post
                    quoteHolder.vSource.setText(quotePost.getBlogName());
                    Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/aleo.ttf");
                    quoteHolder.vSource.setTypeface(face);
                    quoteHolder.vReblogger.setVisibility(View.GONE);
                    quoteHolder.vReblogSign.setVisibility(View.GONE);
                }

                setUpButtons(quotePost, quoteHolder);
                quoteHolder.vTitle.setText("\"" + Html.fromHtml(quotePost.getText()) + "\"");


                break;
            case 5:

                /*
                    video view
                */

                Log.d(TAG, "VIDEO VIEW CURRENTLY BEING SETUP");

                videoHolder = (RecyclerAdapter.VideoViewHolder) object;
                VideoPost vid = (VideoPost) list.get(position);
                //set buttons
                setUpButtons(vid, videoHolder);

                List<Video> vids = vid.getVideos();
                Video video = vids.get(0);
                String reformedUrl = video.getEmbedCode().trim().replaceAll(" ", "");
                Log.d(TAG, "link: " + reformedUrl);
                String start = "sourcesrc=" + "\"";
                String end = "\"type=\"video";

                //gets the int value of the video src url starting and ending point
                int linkStart = reformedUrl.indexOf(start);
                int linkEnd = reformedUrl.indexOf(end);
                Log.d(TAG, "link start:" + linkStart + ", " + "link end:" + linkEnd);

                /*
                checks if url matches format
                link start/end will be -1 if their parameters
                 do not match in the embed url string
                 */



                /*
                NOTE TO SELF:
                    MAKE SURE TO GET EM VIDEO PLAYER TOUCH CONTROLS WORKING
                    EMVIDEO IMAGEVIEW SEEMS TO BE GETTING IN THE WAY OF TOUCH CONTROLS
                 */


                if (linkStart == -1 | linkEnd == -1) {
                    //check to see if it is a vine
                    Toast.makeText(compat, "Vine unable to load.", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "lucasleabres.ruby.free.SuperClickListener: vine unable to load");
                } else {
                    //set video url into EM video object
                    String vidUrl = reformedUrl.substring(linkStart + start.length(), linkEnd);
                    Log.d("VIDURL", " : " + reformedUrl.substring(linkStart + start.length(), linkEnd));
                    final EMVideoView player = videoHolder.vid;

                    player.setOnPreparedListener(new OnPreparedListener() {
                        @Override
                        public void onPrepared() {
                            player.start();
                        }
                    });


                    //player.setPreviewImage(Uri.parse(vid.getThumbnailUrl()));
                    player.setVideoURI(Uri.parse(vidUrl));
                }

                break;

            case 6:
                chatHolder = (RecyclerAdapter.ChatViewHolder) object;
                ChatPost chatPost = (ChatPost) list.get(position);
                setUpButtons(chatPost, chatHolder);

                break;
            case 7:
                linkHolder = (RecyclerAdapter.LinkViewHolder) object;
                LinkPost linkPost = (LinkPost) list.get(position);
                setUpButtons(linkPost, linkHolder);

                break;
            case 8:
                unknownHolder = (RecyclerAdapter.UnknownPostViewHolder) object;
                UnknownTypePost unkPost = (UnknownTypePost) list.get(position);
                setUpButtons(unkPost, quoteHolder);

                break;
        }
    }

    /*
    picasso
     */

    private void picassoHelper(Context c, String url, ImageView view) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        if (prefs.getBoolean("cache", false)) {
            Picasso.with(c)
                    .load(url)
                    .placeholder(R.drawable.loadingshadow)
                    .error(R.drawable.loadingshadow)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                    .into(view);
        } else {
            Picasso.with(c)
                    .load(url)
                    .placeholder(R.drawable.loadingshadow)
                    .error(R.drawable.loadingshadow)
                    .into(view);
        }
    }

    private void setUpButtons(final Post post, final BasicViewHolder holder) {

        Boolean clickedLike = Boolean.valueOf(false);

        if (post.isLiked()) {
            clickedLike = Boolean.valueOf(true);
        }

        //liking

        holder.vLike.setTag(clickedLike);
        holder.vLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change view to red/grey color

                if (((Boolean) holder.vLike.getTag()) == false) {
                    holder.vLike.setImageResource(R.drawable.liked);
                    holder.vLike.setTag(new Boolean(true));
                    //like post
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            post.like();
                        }
                    }).start();

                } else {

                    holder.vLike.setImageResource(R.drawable.like);
                    holder.vLike.setTag(new Boolean(false));
                    //unlike post
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            post.unlike();
                        }
                    }).start();
                }
            }
        });


        //reblogging
        Boolean clickedReblog = Boolean.valueOf(false);

        holder.vReblog.setTag(clickedReblog);
        holder.vReblog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((Boolean) holder.vReblog.getTag()) == false) {
                    holder.vReblog.setImageResource(R.drawable.reblogged);
                    holder.vReblog.setTag(new Boolean(true));
                    //reblog post
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                                blogName = prefs.getString("blog", "tumblr");
                                post.reblog(blogName);
                            } catch (NullPointerException e) {
                                //do nothing
                            }
                        }
                    }).start();

                } else {
                    holder.vReblog.setImageResource(R.drawable.reblog);
                    holder.vReblog.setTag(new Boolean(false));
                    //undo reblog
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            post.delete();
                        }
                    }).start();

                }

            }
        });
        //sets up notes
        if (post.getNoteCount() == 0) {
            holder.vNotes.setText(post.getNoteCount() + " notes :(");
        } else {
            holder.vNotes.setText(post.getNoteCount() + " notes");
        }

        holder.vNotes.setTextColor(context.getResources().getColor(R.color.orange));

        //to source

        holder.vSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //to reblogger
        holder.vReblogger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //off main thread

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //on main thread
                                Intent intent = new Intent(context, ProfileActivity.class);
                                intent.putExtra("pic", picUrl);
                                intent.putExtra("blog", post.getBlogName());
                                context.startActivity(intent);
                            }
                        });
                    }
                }).start();

            }
        });
        //to source

        holder.vSourcePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on main thread
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("pic", post.getSourceTitle());
                intent.putExtra("blog", post.getBlogName());
                context.startActivity(intent);
            }
        });

        //follow source
        holder.vFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JumblrClient client = new JumblrClient(Constants.CONSUMER_KEY,
                        Constants.CONSUMER_SECRET,
                        authToken, authTokenSecret);
                client.follow(post.getBlogName());

            }
        });

        /*
        setup source pic
         */

        String name = post.getBlogName();
        String hhy = "https://api.tumblr.com/v2/blog/" + name + ".tumblr.com/avatar";
        Log.d(TAG, "ava url:" + hhy);
        Picasso.with(context)
                .load(hhy)
                .placeholder(R.drawable.loadingshadow)
                .error(R.drawable.errorimage)
                .into(holder.vSourcePic);

    }


}


