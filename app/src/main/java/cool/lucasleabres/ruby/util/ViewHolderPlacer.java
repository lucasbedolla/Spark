package cool.lucasleabres.ruby.util;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.tumblr.jumblr.types.Photo;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.PhotoSize;
import com.tumblr.jumblr.types.Post;

import java.util.List;

import cool.lucasleabres.ruby.R;
import cool.lucasleabres.ruby.listeners.BasicViewHolderActionListener;
import cool.lucasleabres.ruby.view.viewholders.BasicViewHolder;
import cool.lucasleabres.ruby.view.viewholders.PhotoSetViewHolder;

/**
 * Created by Lucas Bedolla on 5/31/2017.
 */

public class ViewHolderPlacer {
    private ViewHolderPlacer() {
    }

    public static void placePhotos(BasicViewHolder inferredViewHolder, Post post, BasicViewHolderActionListener listener) {

        PhotoSetViewHolder photoSetHolder = (PhotoSetViewHolder) inferredViewHolder;
        PhotoPost photoPost = (PhotoPost) post;
        int photoSetSize = photoPost.getPhotos().size();

        setPhotoVisibility(photoSetHolder, photoSetSize);
        setPhotos(photoSetHolder, photoPost);
        basicHolderSetUp(photoPost, photoSetHolder, listener);
    }

    private static void setPhotos(PhotoSetViewHolder photoSetHolder, PhotoPost photoPost) {
        ImageView[] image = photoSetHolder.getImages();
        for (int i = 0; i < photoPost.getPhotos().size(); i++) {
            List<PhotoSize> sizes = photoPost.getPhotos().get(i).getSizes();
            Picasso.with(photoSetHolder.getLike().getContext())
                    .load(sizes.get(0).getUrl())
                    .placeholder(R.drawable.loadingshadow)
                    .error(R.drawable.loadingshadow)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                    .into(image[i]);
        }
    }

    private static void basicHolderSetUp(Post post, PostType type, BasicViewHolder holder, BasicViewHolderActionListener listener) {

       if(type.equals(PostType.PHOTO)){
           
       }else if(type.equals(PostType.TEXT)){

       }else if(){

       }else if(){

       }

    }

    private void setFunctions(){
        holder.getTitle().setText();
        holder.getSource();
        holder.getNotes();
        holder.getReblogger();
        holder.getReblogSign();
        holder.getReblog();
        holder.getLike();
        holder.getFollow();
        holder.getSourcePic();
    }
    }



    }

    public static void placeText(BasicViewHolder inferredViewHolder, Post post, BasicViewHolderActionListener listener) {

    }

    public static void placeVideo(BasicViewHolder inferredViewHolder, Post post, BasicViewHolderActionListener listener) {

    }

    public static void placeAudio(BasicViewHolder inferredViewHolder, Post post, BasicViewHolderActionListener listener) {

    }

    public static void placeUnknown(BasicViewHolder inferredViewHolder, Post post, BasicViewHolderActionListener listener) {

    }

    public static void placeChat(BasicViewHolder inferredViewHolder, Post post, BasicViewHolderActionListener listener) {

    }

    public static void placeAnswer(BasicViewHolder inferredViewHolder, Post post, BasicViewHolderActionListener listener) {

    }

    public static void placeQuote(BasicViewHolder inferredViewHolder, Post post, BasicViewHolderActionListener listener) {
    }

    public static void placeLoading(BasicViewHolder inferredViewHolder, BasicViewHolderActionListener listener) {

    }

    private static void setPhotoVisibility(PhotoSetViewHolder viewHolder, int numberOfPhotos) {
        ImageView[] images = viewHolder.getImages();
        int remainder = 10;
        for (int i = 0; i < numberOfPhotos; i++) {
            images[i].setVisibility(View.VISIBLE);
            remainder--;
        }
        while (remainder != 0) {
            images[remainder].setVisibility(View.GONE);
            remainder--;
        }
    }

}



        switch(int i){

                case 2:

                TextPost textPost=(TextPost)post;
                textHolder=(RecyclerAdapter.TextViewHolder)inferredViewHolder;

                /*
                text setting

                 */
                if(textPost.getRebloggedFromName()!=null){
                //reblogged from someone
                textHolder.vSource.setText(textPost.getRebloggedFromName());
                textHolder.vReblogger.setText(textPost.getTitle());
                }else{
                //original post
                textHolder.vSource.setText(textPost.getBlogName());
                textHolder.vSource.setVisibility(View.GONE);
                textHolder.vReblogSign.setVisibility(View.GONE);
                }


                //String[] tokens = noPrefixStr.split("/");
                if(textPost.getTitle()==null){
                textHolder.vTitle.setVisibility(View.GONE);
                }else{
                textHolder.vTitle.setText(Html.fromHtml(textPost.getTitle()));
                textHolder.vTitle.setTextColor(Color.BLACK);
                }

                if(textPost.getBody()==null){
                textHolder.vContent.setVisibility(View.GONE);
                }else{
                textHolder.vContent.setText(Html.fromHtml(textPost.getBody()));
                textHolder.vContent.setTextColor(Color.BLUE);
                }

                setUpButtons(textPost,textHolder);


                if(textPost.getSourceTitle()!=null){
                //post is from person you dont follow

                //LOAD SOURCE BLOG NAME
                textHolder.vSource.setText(textPost.getSourceTitle());
                Typeface face=Typeface.createFromAsset(context.getAssets(),"fonts/aleo.ttf");
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

                AnswerPost answerPost=(AnswerPost)list.get(position);
                answerHolder=(RecyclerAdapter.AnswerViewHolder)inferredViewHolder;
                answerHolder.vQuestion.setText(answerPost.getAskingName()+": "+answerPost.getQuestion());
                answerHolder.vAnswer.setText(Html.fromHtml(answerPost.getAnswer()));
                answerHolder.vSource.setText(answerPost.getAuthorId());

                if(answerPost.getRebloggedFromName()!=null){
                //reblogged from someone
                answerHolder.vSource.setText(answerPost.getRebloggedFromName());
                answerHolder.vReblogger.setText(answerPost.getSourceTitle());
                }else{
                //original post
                answerHolder.vSource.setText(answerPost.getBlogName());
                Typeface face=Typeface.createFromAsset(context.getAssets(),"fonts/aleo.ttf");
                answerHolder.vSource.setTypeface(face);
                answerHolder.vReblogger.setVisibility(View.GONE);
                answerHolder.vReblogSign.setVisibility(View.GONE);
                }


                setUpButtons(answerPost,answerHolder);

                break;
                case 4:


                /*
                quote
                 */


                QuotePost quotePost=(QuotePost)list.get(position);
                quoteHolder=(RecyclerAdapter.QuoteViewHolder)inferredViewHolder;

                //setUpButtons(quotePost, quoteHolder);

                if(quotePost.getRebloggedFromName()!=null){
                //reblogged from someone
                quoteHolder.vSource.setText(quotePost.getRebloggedFromName());
                quoteHolder.vSource.setTextColor(Color.BLACK);
                quoteHolder.vReblogger.setText(quotePost.getSourceTitle());
                }else{
                //original post
                quoteHolder.vSource.setText(quotePost.getBlogName());
                Typeface face=Typeface.createFromAsset(context.getAssets(),"fonts/aleo.ttf");
                quoteHolder.vSource.setTypeface(face);
                quoteHolder.vReblogger.setVisibility(View.GONE);
                quoteHolder.vReblogSign.setVisibility(View.GONE);
                }


                quoteHolder.vTitle.setText("\""+Html.fromHtml(quotePost.getText())+"\"");


                break;
                case 5:

                /*
                    video view
                */

                Log.d(TAG,"VIDEO VIEW CURRENTLY BEING SETUP");

                RecyclerAdapter.VideoViewHolder videoHolder=(RecyclerAdapter.VideoViewHolder)inferredViewHolder;
                VideoPost vid=(VideoPost)list.get(position);
                //set buttons


                List<Video> vids=vid.getVideos();
        Video video=vids.get(0);
        String reformedUrl=video.getEmbedCode().trim().replaceAll(" ","");
        Log.d(TAG,"link: "+reformedUrl);
        String start="sourcesrc="+"\"";
        String end="\"type=\"video";

        //gets the int value of the video src url starting and ending point
        int linkStart=reformedUrl.indexOf(start);
        int linkEnd=reformedUrl.indexOf(end);
        Log.d(TAG,"link start:"+linkStart+", "+"link end:"+linkEnd);

                /*
                checks if url matches format
                link start/end will be -1 if their parameters
                 do not match in the embed url string
                 */

        if(linkStart==-1|linkEnd==-1){
        //check to see if it is a vine

        videoHolder.vSource.setText("Thanks for trying out ruby! :)");
        videoHolder.vDescription.setVisibility(View.GONE);
        videoHolder.vid.setVisibility(View.GONE);
        videoHolder.vTitle.setVisibility(View.GONE);
        videoHolder.vFollow.setVisibility(View.GONE);
        videoHolder.vLike.setVisibility(View.GONE);

        videoHolder.vReblogger.setVisibility(View.GONE);
        videoHolder.vReblogSign.setVisibility(View.GONE);
        videoHolder.vReblog.setVisibility(View.GONE);
        videoHolder.vSourcePic.setVisibility(View.GONE);

        }else{
        videoHolder.vTitle.setVisibility(View.GONE);
        videoHolder.vDescription.setVisibility(View.GONE);
        setUpButtons(vid,videoHolder);

        videoHolder.vSource.setText(vid.getRebloggedFromName());
        videoHolder.vReblogger.setText(vid.getAuthorId());

        //set video url into EM video object
        String vidUrl=reformedUrl.substring(linkStart+start.length(),linkEnd);
        Log.d("VIDURL"," : "+reformedUrl.substring(linkStart+start.length(),linkEnd));

        WebView web=videoHolder.vid;
        web.loadUrl(vidUrl);


        }

        break;

        case 6:
        chatHolder=(RecyclerAdapter.ChatViewHolder)inferredViewHolder;
        ChatPost chatPost=(ChatPost)list.get(position);
        setUpButtons(chatPost,chatHolder);

        break;
        case 7:
        linkHolder=(RecyclerAdapter.LinkViewHolder)inferredViewHolder;
        LinkPost linkPost=(LinkPost)list.get(position);
        setUpButtons(linkPost,linkHolder);

        break;
        case 8:
        unknownHolder=(RecyclerAdapter.UnknownPostViewHolder)inferredViewHolder;
        UnknownTypePost unkPost=(UnknownTypePost)list.get(position);
        setUpButtons(unkPost,unknownHolder);
        break;
        case 9: //loading holder
        break;
        case 10:

        audioHolder=(RecyclerAdapter.AudioViewHolder)inferredViewHolder;
final AudioPost audio=(AudioPost)list.get(position);
        setUpButtons(audio,audioHolder);
        //set up audio visuals
        picassoHelper(context,audio.getAlbumArtUrl(),audioHolder.vArtwork);
        audioHolder.vDescription.setText(audio.getArtistName());
        audioHolder.vTitle.setText(audio.getTrackName());
        if(player==null){
        player=new MediaPlayer();

        }

        player.setDataSource(context,Uri.parse(audio.getAudioUrl()));


        audioHolder.vPlayPause.setOnClickListener(new View.OnClickListener(){
@Override
public void onClick(View v){

        Toast.makeText(context,"Loading audio...",Toast.LENGTH_SHORT).show();

        new Thread(new Runnable(){
@Override
public void run(){
        try{
        player.prepare();
        }catch(IOException e){
        e.printStackTrace();
        }

        }
        }).start();
        }
        });


                /*
                change appearance of search page + functionality.
                    get rid of profile page slider buttons
                    fix audio view holder issue
                    test video playback
                    change settings page look
                    add legal thing for libraries used

                    DONE!

                    work on BlueFin!
                 */


        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
@Override
public void onPrepared(MediaPlayer mp){
        player.start();
        audioHolder.vPlayPause.setOnClickListener(new View.OnClickListener(){
@Override
public void onClick(View v){
        if(!player.isPlaying()){
        player.start();
        v.setBackground(context.getResources().getDrawable(R.drawable.pause));
        }else{
        player.pause();
        v.setBackground(context.getResources().getDrawable(R.drawable.play));
        }
        }
        });
        }
        });
        if(audio.getSourceTitle()!=null){
        //post is from person you dont follow

        //LOAD SOURCE BLOG NAME
        audioHolder.vSource.setText(audio.getSourceTitle());
        Typeface face=Typeface.createFromAsset(context.getAssets(),"fonts/aleo.ttf");
        audioHolder.vSource.setTypeface(face);
        //LOAD REBLOGGER NAME
        audioHolder.vReblogger.setText(audio.getBlogName());
        audioHolder.vReblogger.setTypeface(face);
        //loads source pic


        }else{
        //post is from person you do follow

        SharedPreferences preferences=
        PreferenceManager.getDefaultSharedPreferences(context);
        if(preferences.getBoolean("grid",false)){
        audioHolder.vSource.setTextSize(10f);
        }else{
        audioHolder.vSource.setTextSize(20f);
        audioHolder.vSource.setGravity(Gravity.CENTER_VERTICAL);
        }
        audioHolder.vSource.setText(audio.getBlogName());
        Typeface face=Typeface.createFromAsset(context.getAssets(),"fonts/aleo.ttf");
        audioHolder.vSource.setTypeface(face);
        audioHolder.vSource.setTextColor(context.getResources().getColor(R.color.liked_red));
        audioHolder.vFollow.setVisibility(View.GONE);
        audioHolder.vReblogSign.setVisibility(View.GONE);
        audioHolder.vReblogger.setVisibility(View.GONE);
        }

        break;
        case 11:{
        // this code makes ui devoid of anything but actual content
        }

        }

        }

    /*
    picasso
     */

private static void picassoHelper(Context c,String url,ImageView view){
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(c);
        //if cache is true, then picasso caches, ELSE -> picasso does not cache.
        if(prefs.getBoolean("cache",false)){
        Picasso.with(c)
        .load(url)
        .placeholder(R.drawable.loadingshadow)
        .error(R.drawable.loadingshadow)
        .into(view);
        }else{
        Picasso.with(c)
        .load(url)
        .placeholder(R.drawable.loadingshadow)
        .error(R.drawable.loadingshadow)
        .memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
        .networkPolicy(NetworkPolicy.NO_CACHE,NetworkPolicy.NO_STORE)
        .into(view);
        }
        }

private static void setUpButtons(final Post post,final BasicViewHolder holder){

        holder.getLike().setImageResource(R.drawable.like);

        //liking
        holder.getLike().setTag(false);
        holder.getLike().setOnClickListener(new View.OnClickListener(){
@Override
public void onClick(View v){
        //change view to red/grey color

        if(!((Boolean)holder.getLike().getTag())){
        holder.getLike().setImageResource(R.drawable.liked);
        holder.getLike().setTag(Boolean.TRUE);
        //like post
        new Thread(new Runnable(){
@Override
public void run(){
        post.like();
        }
        }).start();

        }else{

        holder.getLike().setImageResource(R.drawable.like);
        holder.getLike().setTag(Boolean.FALSE);
        //unlike post
        new Thread(new Runnable(){
@Override
public void run(){
        post.unlike();
        }
        }).start();
        }
        }
        });


        holder.getReblogSign().setImageResource(R.drawable.reblog);
        holder.getReblogSign().setOnClickListener(new View.OnClickListener(){
@Override
public void onClick(View v){

        if(!((boolean)holder.getReblogSign().getTag())){
        holder.getReblogSign().setImageResource(R.drawable.reblogged);
        holder.getReblogSign().setTag(Boolean.TRUE);
        //reblog post
        new Thread(new Runnable(){
@Override
public void run(){
        try{
        post.reblog(blogName);
        }catch(NullPointerException e){
        //do nothing
        }
        }
        }).start();

        }else{
        holder.vReblog.setImageResource(R.drawable.reblog);
        holder.vReblog.setTag(Boolean.FALSE);
        //undo reblog
        new Thread(new Runnable(){
@Override
public void run(){
        post.delete();
        }
        }).start();

        }

        }
        });
        //sets up notes
        if(post.getNoteCount()==0){
        holder.vNotes.setText(post.getNoteCount()+" notes :(");
        }else{
        holder.vNotes.setText(post.getNoteCount()+" notes");
        }

        holder.vNotes.setTextColor(context.getResources().getColor(R.color.orange));

        //to source

        holder.vSource.setOnClickListener(new View.OnClickListener(){
@Override
public void onClick(View v){

        }
        });

        //to reblogger
        holder.vReblogger.setOnClickListener(new View.OnClickListener(){
@Override
public void onClick(View v){

        Intent intent=new Intent(context,ProfileActivity.class);
        intent.putExtra("pic",picUrl);
        intent.putExtra("blog",post.getBlogName());
        context.startActivity(intent);

        }
        });
        //to source

        holder.vSourcePic.setOnClickListener(new View.OnClickListener(){
@Override
public void onClick(View v){
        //on main thread
        Intent intent=new Intent(context,ProfileActivity.class);
        intent.putExtra("pic",post.getSourceTitle());
        intent.putExtra("blog",post.getBlogName());
        context.startActivity(intent);
        }
        });

        //follow source
        holder.vFollow.setOnClickListener(new View.OnClickListener(){
@Override
public void onClick(View v){

        JumblrClient client=new JumblrClient(Constants.CONSUMER_KEY,
        Constants.CONSUMER_SECRET,
        authToken,authTokenSecret);
        client.follow(post.getBlogName());

        }
        });

        /*
        setup source pic
         */

        new Thread(new Runnable(){
@Override
public void run(){

        Log.d(TAG,"post blog name:"+post.getBlogName());
        Log.d(TAG,"post author id:"+post.getAuthorId());
        Log.d(TAG,"post url:"+post.getPostUrl());
        Log.d(TAG,"post reblogged from id:"+post.getRebloggedFromId());
        String name=post.getBlogName();
final String hhy="https://api.tumblr.com/v2/blog/"+name+".tumblr.com/avatar";
        Log.d(TAG,"ava url:"+hhy);

        Looper.prepare();
        handler.post(new Runnable(){
