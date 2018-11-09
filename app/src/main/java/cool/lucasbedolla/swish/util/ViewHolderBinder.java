package cool.lucasbedolla.swish.util;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tumblr.jumblr.types.AnswerPost;
import com.tumblr.jumblr.types.ChatPost;
import com.tumblr.jumblr.types.Dialogue;
import com.tumblr.jumblr.types.Note;
import com.tumblr.jumblr.types.Photo;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.QuotePost;
import com.tumblr.jumblr.types.TextPost;

import java.util.HashMap;
import java.util.List;

import cool.lucasbedolla.swish.R;
import cool.lucasbedolla.swish.activities.MainActivity;
import cool.lucasbedolla.swish.fragments.ProfileFragment;
import cool.lucasbedolla.swish.view.SmartImageView;
import cool.lucasbedolla.swish.view.viewholders.BasicViewHolder;

import static cool.lucasbedolla.swish.util.ImageHelper.downloadBlogAvatarIntoImageView;
import static cool.lucasbedolla.swish.util.ImageHelper.downloadImageIntoImageView;

/**
 * Created by Lucas Bedolla on 5/31/2017.
 */

public class ViewHolderBinder {

    private ViewHolderBinder() {
    }


    public static void placePhotos(Context ctx,
                                   BasicViewHolder inferredViewHolder,
                                   Post post,
                                   boolean isDual, View.OnClickListener listener,
                                   View.OnLongClickListener longClickListener) {
        placePhotos(ctx, inferredViewHolder, post, listener, longClickListener, null, isDual);
    }

    public static void placePhotos(Context ctx,
                                   BasicViewHolder inferredViewHolder,
                                   Post post,
                                   View.OnClickListener listener,
                                   View.OnLongClickListener longClickListener,
                                   Typeface font, boolean isDual) {
        PhotoPost photoPost = (PhotoPost) post;
        basicHolderSetUp(ctx, photoPost, inferredViewHolder, isDual);
        setPhotos(ctx, inferredViewHolder, photoPost, listener, longClickListener);
        if (photoPost.getCaption() != null && photoPost.getCaption().length() > 0) {
            String captionHtml = ((PhotoPost) post).getCaption();
            String caption = Html.fromHtml(captionHtml).toString().trim();

            if (post.getSourceTitle() != null) {
                //inferredViewHolder.getDescription().setText(caption);
            }

        } else {
            //inferredViewHolder.getDescription().setVisibility(View.GONE);
        }
    }

    private static String removeAuthorText(String authorTitle, String caption) {

        if (caption == null) {
            return null;
        }

        if (caption.contains(":") && caption.contains(authorTitle)) {

            int firstColon = caption.indexOf(":");

            return caption.subSequence(firstColon + 1, caption.length() - 1).toString().trim();
        } else {
            return caption;
        }
    }

    private static void setPhotos(Context ctx, BasicViewHolder holder, PhotoPost photoPost, View.OnClickListener listener, View.OnLongClickListener longCLickListener) {
        FrameLayout targetLayout = holder.getContentTargetLayout();
        if (targetLayout.getChildCount() > 0) {
            targetLayout.removeAllViews();
        }

        LinearLayout contentHolder = holder.getTargetLayoutAsLinearLayout();

        List<Photo> photos = photoPost.getPhotos();
        for (int i = 0; i < photos.size(); i++) {

            SmartImageView imageView = new SmartImageView(ctx);
            int h = photos.get(i).getOriginalSize().getHeight();
            int w = photos.get(i).getOriginalSize().getWidth();

            imageView.setAspectRatio(h, w);
            imageView.setImageUrl(photos.get(i).getSizes().get(0).getUrl());
            contentHolder.addView(imageView);
            imageView.setOnClickListener(listener);
            imageView.setOnLongClickListener(longCLickListener);

            downloadImageIntoImageView(imageView, imageView.getImageUrl());
        }
    }

    private static void basicHolderSetUp(Context context, Post post, BasicViewHolder holder, boolean isDual) {
        configureTopLayout(context, holder, post, isDual);
        configureBottomLayout(context, holder, post, isDual);
    }

    private static void configureBottomLayout(final Context context, final BasicViewHolder holder, final Post post, boolean isDual) {
        //done to reset reused viewholder

        final Drawable filledHeart = context.getResources().getDrawable(R.drawable.ic_filled_heart);
        final Drawable emptyHeart = context.getResources().getDrawable(R.drawable.ic_empty_heart);
        final Drawable filledReblog = context.getResources().getDrawable(R.drawable.ic_filled_reblog);
        final Drawable emptyReblog = context.getResources().getDrawable(R.drawable.ic_empty_reblog);

        if (post.isLiked()) {
            //make post imageview to selected
            holder.getLikeButton().setBackground(filledHeart);
        } else {
            holder.getLikeButton().setBackground(emptyHeart);
        }

        holder.getReblogButton().setBackground(emptyReblog);

        if (post.getNoteCount() == 1) {
            holder.getNotes().setText(post.getNoteCount() + " note");
        } else {
            long notes = post.getNoteCount();
            if (notes > 1000) {
                long kNotes = notes / 1000;
                holder.getNotes().setText(kNotes + "k notes");
                Note k;
            } else {
                holder.getNotes().setText(post.getNoteCount() + " notes");
            }
        }

        holder.getLikeButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v.getBackground() == emptyHeart) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            post.like();
                        }
                    }).start();

                    holder.getLikeButton().setBackground(filledHeart);

                } else if (v.getBackground() == filledHeart) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            post.unlike();
                        }
                    }).start();
                    holder.getLikeButton().setBackground(emptyHeart);
                }
            }
        });

        holder.getReblogButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getBackground() == emptyReblog) {
                    //do  reblog
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (MyPrefs.getCurrentBlog(context) != null) {
                                    post.reblog(MyPrefs.getCurrentBlog(context), new HashMap<String, Object>());
                                } else {
                                    post.reblog(MyPrefs.getCurrentUser(context), new HashMap<String, Object>());
                                }
                            } catch (Exception e) {
                                //there's an issue with the jumblr library, but call is still successful
                            }

                        }
                    }).start();
                    holder.getReblogButton().setBackground(filledReblog);

                } else if (holder.getReblogButton().getBackground() == filledReblog) {
                    //delete reblog
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            post.delete();
                        }
                    }).start();

                    holder.getReblogButton().setBackground(emptyReblog);
                }
            }
        });


    }

    private static void configureTopLayout(final Context context, BasicViewHolder holder, final Post post, boolean isDual) {

        if (!isDual) {
            downloadBlogAvatarIntoImageView(holder.getProfilePicture(), post.getBlogName());
        }

        //set up blog text
        if (post.getSourceTitle() == null) {
            holder.getAuthorText().setText(Html.fromHtml("<b>" + post.getBlogName() + "</b>"));
            holder.getAuthorText().setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.getFollowSource().setVisibility(View.GONE);
        } else {
            holder.getAuthorText().setText(Html.fromHtml("<b>" +
                    post.getBlogName() +
                    " <br> <font color='#5387ff'>reblogged</font> </br> " + "<br>"
                    + post.getSourceTitle() + "</br> </b>"));

            if (isFollowingSourceOrReblogger()) {
                holder.getFollowSource().setVisibility(View.GONE);
            } else {
                String sourceText = "view\nsource";
                if (holder.getFollowSource() != null && !post.getSourceTitle().contains(".")&& !post.getSourceTitle().contains(" ")) {
                    holder.getFollowSource().setText(sourceText);
                    holder.getFollowSource().setVisibility(View.VISIBLE);
                    holder.getFollowSource().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Bundle arguments = new Bundle();
                            arguments.putString(ProfileFragment.BLOG_NAME, post.getSourceTitle());
                            ProfileFragment interactionFragment = new ProfileFragment();

                            interactionFragment.setArguments(arguments);

                            ((MainActivity) context).getSupportFragmentManager().beginTransaction()
                                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                                    .replace(R.id.fragment_container, interactionFragment, "IMAGE")
                                    .commitNow();
                        }
                    });
                } else {
                    holder.getFollowSource().setVisibility(View.GONE);
                }
            }
        }

        if (!isDual) {
            holder.getProfilePicture().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //todo: open up profile fragment
                    ProfileFragment interactionFragment = new ProfileFragment();
                    Bundle arguments = new Bundle();
                    arguments.putString(ProfileFragment.BLOG_NAME, post.getBlogName());
                    interactionFragment.setArguments(arguments);

                    ((MainActivity) context).getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                            .replace(R.id.fragment_container, interactionFragment, "IMAGE")
                            .commitNow();
                }
            });
        }

    }

    private static boolean isFollowingSourceOrReblogger() {
        return false;
    }

    public static void placeText(Context context, BasicViewHolder holder, Post post, boolean isDual) {

        LinearLayout contentHolder = holder.getTargetLayoutAsLinearLayout();

        View contentLayout;
        if (isDual) {
            contentLayout = LayoutInflater.from(context).inflate(R.layout.dual_text_post, null, false);
        } else {
            contentLayout = LayoutInflater.from(context).inflate(R.layout.mono_text_post, null, false);
        }

        TextView title = contentLayout.findViewById(R.id.text_title);
        TextView body = contentLayout.findViewById(R.id.text_body);

        TextPost textPost = (TextPost) post;

        //todo: fix the blue square issue

        if (textPost.getTitle() != null && !textPost.getTitle().isEmpty()) {
            title.setText(Html.fromHtml(textPost.getTitle().trim()));
        } else {
            title.setVisibility(View.GONE);
        }

        if (textPost.getBody() != null && !textPost.getBody().isEmpty()) {
            body.setText(Html.fromHtml(textPost.getBody().trim()));
        } else {
            body.setVisibility(View.GONE);
        }

        basicHolderSetUp(context, post, holder, isDual);
        contentHolder.addView(contentLayout);
        //holder.getDescription().setVisibility(View.GONE);
    }

//    public static void placeVideo(Context context, BasicViewHolder holder, Post post, boolean isDual, View.OnClickListener listener) {
//
//        LinearLayout contentHolder = holder.getTargetLayoutAsLinearLayout();
//
//        View contentLayout;
//        if (isDual) {
//            contentLayout = LayoutInflater.from(context).inflate(R.layout.dual_video_post, contentHolder, true);
//        } else {
//            contentLayout = LayoutInflater.from(context).inflate(R.layout.mono_video_post, contentHolder, true);
//        }
//
//        VideoPost videoPost = (VideoPost) post;
//        holder.getDescription().setText(videoPost.getCaption());
//
//        List<Video> videos = videoPost.getVideos();
//        for (Video video : videos) {
//            View videoRow;
//
//            if (isDual) {
//                videoRow = LayoutInflater.from(context).inflate(R.layout.dual_video_row, (ViewGroup) contentLayout);
//            } else {
//                videoRow = LayoutInflater.from(context).inflate(R.layout.mono_video_row, (ViewGroup) contentLayout);
//            }
//
//
//            BetterVideoPlayer player = videoRow.findViewById(R.id.player);
////            int srcIndex = video.getEmbedCode().indexOf("src");
////
////            int heightIndex = video.getEmbedCode().indexOf("height") + 8;
////            String heightString = video.getEmbedCode().substring(heightIndex);
////            int heightEndIndex = video.getEmbedCode().indexOf("\'");
////            heightString = heightString.substring(0, heightEndIndex);
////            heightEndIndex = video.getEmbedCode().indexOf("\'");
////            heightString = heightString.substring(0, heightEndIndex);
////            //height/width of video
////            double height = Integer.valueOf(heightString.substring(0, heightString.indexOf("\'")));
////            double width = video.getWidth();
////            //make sure this is returning no zero
////            int displayWidth = ((MainActivity) context).getWindowManager().getDefaultDisplay().getWidth();
////
////            double aspectTrueHeight = (height / width) * displayWidth;
////
////            videoRow.setMinimumHeight((int) aspectTrueHeight);
////            videoRow.setMinimumWidth(displayWidth);
////
////
////            String https = video.getEmbedCode().substring(srcIndex + 5);
////            https = https.substring(0, https.indexOf("\""));
//            player.setSource(Uri.parse("https://www.youtube.com/watch?v=yEGth4SKv-c"));
//
//            player.setCallback((MainActivity) context);
//        }
//
//        basicHolderSetUp(context, post, holder);
//    }
//
//    public static void placeAudio(Context ctx, BasicViewHolder inferredViewHolder, Post post, boolean isDual, View.OnClickListener listener) {
//        LinearLayout contentHolder = inferredViewHolder.getTargetLayoutAsLinearLayout();
//        TextView viewIndicator = new TextView(ctx);
//        viewIndicator.setHeight(250);
//        viewIndicator.setWidth(300);
//        viewIndicator.setText("audio Post");
//        contentHolder.addView(viewIndicator);
//    }

    public static void placeUnknown(Context ctx, BasicViewHolder inferredViewHolder, Post post, View.OnClickListener listener) {
        LinearLayout contentHolder = inferredViewHolder.getTargetLayoutAsLinearLayout();
        TextView viewIndicator = new TextView(ctx);
        viewIndicator.setHeight(250);
        viewIndicator.setWidth(300);
        viewIndicator.setText("Unknown Post");
        contentHolder.addView(viewIndicator);
    }

    public static void placeChat(Context context, BasicViewHolder holder, Post post, boolean isDual, View.OnClickListener listener) {
        LinearLayout container = holder.getTargetLayoutAsLinearLayout();

        View contentLayout;
        if (isDual) {
            contentLayout = LayoutInflater.from(context).inflate(R.layout.dual_chat_post, null, false);
        } else {
            contentLayout = LayoutInflater.from(context).inflate(R.layout.mono_chat_post, null, false);
        }


        ChatPost chatPost = (ChatPost) post;

        List<Dialogue> dialogueList = chatPost.getDialogue();


        for (Dialogue dialogue : dialogueList) {
            //each iteration should be identifiable
            View dialogueRow;

            if (isDual) {
                dialogueRow = LayoutInflater.from(context).inflate(R.layout.dual_dialogue_row, (ViewGroup) contentLayout, false);
            } else {
                dialogueRow = LayoutInflater.from(context).inflate(R.layout.mono_dialogue_row, (ViewGroup) contentLayout, false);
            }

            TextView label = dialogueRow.findViewById(R.id.label);
            TextView name = dialogueRow.findViewById(R.id.name);
            TextView phrase = dialogueRow.findViewById(R.id.phrase);

            label.setText(dialogue.getLabel());
            name.setText(dialogue.getName());
            phrase.setText(dialogue.getPhrase());
//
//            int index = dialogue.getName().length() - 1;
//            if (index > 0) {
//                if(dialogue.getPhrase().contains(dialogue.getName())){
//                    int indexEnd = dialogue.getPhrase().indexOf(dialogue.getName());
//                }
//            }
            ((ViewGroup) contentLayout).addView(dialogueRow);
        }
        basicHolderSetUp(context, post, holder, isDual);
        //holder.getDescription().setVisibility(View.GONE);
        container.addView(contentLayout);
    }

    public static void placeAnswer(Context ctx, BasicViewHolder inferredViewHolder, Post post, boolean isDual, View.OnClickListener listener) {

        LinearLayout contentHolder = inferredViewHolder.getTargetLayoutAsLinearLayout();
        if (contentHolder.getChildCount() > 0) {
            contentHolder.removeAllViews();
        }
        View contentLayout;
        if (isDual) {
            contentLayout = LayoutInflater.from(ctx).inflate(R.layout.dual_answer_post, null, false);
        } else {
            contentLayout = LayoutInflater.from(ctx).inflate(R.layout.mono_answer_post, null, false);
        }

        TextView answer = contentLayout.findViewById(R.id.answer);
        TextView question = contentLayout.findViewById(R.id.question);
        TextView askingUrl = contentLayout.findViewById(R.id.url);
        TextView askingName = contentLayout.findViewById(R.id.askingName);

        AnswerPost answerPost = (AnswerPost) post;

        answer.setText(Html.fromHtml(answerPost.getAnswer()).toString().trim());
        question.setText(Html.fromHtml(answerPost.getQuestion()).toString().trim());
        askingName.setText(answerPost.getAskingName() + " asked:");
        if (answerPost.getAskingUrl() == null) {
            askingUrl.setVisibility(View.GONE);
        } else {
            askingUrl.setText(answerPost.getAskingUrl());
        }
        contentHolder.addView(contentLayout);

        basicHolderSetUp(ctx, post, inferredViewHolder, isDual);
        //inferredViewHolder.getDescription().setVisibility(View.GONE);
    }

    public static void placeQuote(Context ctx, BasicViewHolder inferredViewHolder, Post post, boolean isDual, View.OnClickListener listener) {

        LinearLayout contentHolder = inferredViewHolder.getTargetLayoutAsLinearLayout();
        if (contentHolder.getChildCount() > 0) {
            contentHolder.removeAllViews();
        }


        View contentLayout;
        if (isDual) {
            contentLayout = LayoutInflater.from(ctx).inflate(R.layout.dual_quote_post, null, false);
        } else {
            contentLayout = LayoutInflater.from(ctx).inflate(R.layout.mono_quote_post, null, false);
        }
        TextView quoteTextView = contentLayout.findViewById(R.id.quote);
        TextView sourceTextView = contentLayout.findViewById(R.id.source);

        QuotePost quotePost = (QuotePost) post;
        String quote = quotePost.getText();
        String source = quotePost.getSource();

        quoteTextView.setText("\"" + quote + "\"");
        sourceTextView.setText("-" + Html.fromHtml(source) + "-");

        contentHolder.addView(contentLayout);

        basicHolderSetUp(ctx, post, inferredViewHolder, isDual);
        //inferredViewHolder.getDescription().setVisibility(View.GONE);
    }
}