package cool.lucasbedolla.swish.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tumblr.jumblr.types.Post;

import java.util.List;

import cool.lucasbedolla.swish.R;
import cool.lucasbedolla.swish.SparkApplication;
import cool.lucasbedolla.swish.activities.dashboard.DashboardActivity;
import cool.lucasbedolla.swish.core.UnderTheHoodActivity;
import cool.lucasbedolla.swish.fragments.InteractionFragment;
import cool.lucasbedolla.swish.util.ImageHelper;
import cool.lucasbedolla.swish.util.MyPrefs;
import cool.lucasbedolla.swish.util.ViewHolderBinder;
import cool.lucasbedolla.swish.view.SmartImageView;
import cool.lucasbedolla.swish.view.viewholders.BasicViewHolder;

/**
 * Created by LUCASURE on 2/4/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<BasicViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    public static final String TAG = "RECYCLER ADAPTER";
    private final List<Post> itemList;
    private UnderTheHoodActivity ctx;
    public RecyclerAdapter(UnderTheHoodActivity context, List<Post> inputList) {
        this.ctx = context;
        itemList = inputList;
    }

    @Override
    public BasicViewHolder onCreateViewHolder(ViewGroup parent, int postType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (MyPrefs.getIsDualMode(parent.getContext())) {
            //tweak a new set of this layout to have smaller scale for dual mode
            return new BasicViewHolder(inflater.inflate(R.layout.view_holder_base_dual, parent, false));
        } else {
            return new BasicViewHolder(inflater.inflate(R.layout.view_holder_base, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(BasicViewHolder holder, int position) {

        Post post = itemList.get(position);

        PostType type = mapPost(holder);

        switch (type) {
            case PHOTO:
                ViewHolderBinder.placePhotos(ctx, holder, post, this, this);
                break;
            case TEXT:
                ViewHolderBinder.placeText(ctx, holder, post);
                break;
            case CHAT:
                ViewHolderBinder.placeChat(ctx, holder, post, this);
                break;
            case AUDIO:
                ViewHolderBinder.placeAudio(ctx, holder, post, this);
                break;
            case QUOTE:
                ViewHolderBinder.placeQuote(ctx, holder, post, this);
                break;
            case VIDEO:
                ViewHolderBinder.placeVideo(ctx, holder, post, this);
                break;
            case ANSWER:
                ViewHolderBinder.placeAnswer(ctx, holder, post, this);
                break;
            case QUESTION:
                ViewHolderBinder.placeQuestion(ctx, holder, post, this);
                break;
            case UNKNOWN:
                ViewHolderBinder.placeUnknown(ctx, holder, post, this);
                break;
            case LOADING:
                ViewHolderBinder.placeLoading(ctx, holder, this);

                break;

        }

    }

    private PostType mapPost(BasicViewHolder holder) {
        switch (holder.getItemViewType()) {
            case 0:
                return PostType.PHOTO;
            case 1:
                return PostType.PHOTO;
            case 2:
                return PostType.TEXT;
            case 3:
                return PostType.ANSWER;
            case 4:
                return PostType.VIDEO;
            case 5:
                return PostType.QUOTE;
            case 6:
                return PostType.CHAT;
            case 7:
                return PostType.LINK;
            case 8:
                return PostType.QUESTION;
            case 9:
                return PostType.LOADING;
            case 10:
                return PostType.AUDIO;
            default:
                return PostType.UNKNOWN;
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onClick(View view) {

        if (view instanceof SmartImageView && ctx instanceof DashboardActivity) {
            String url = ((SmartImageView) view).getImageUrl();
            if (url != null) {
                showImageFragment(url);
            }
            return;
        }

        switch (view.getId()) {
            case R.id.extras_button:
                break;
            case R.id.like_button:
                Toast.makeText(view.getContext(), "Liked!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.reblog_button:
                Toast.makeText(view.getContext(), "Reblogged!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (view instanceof SmartImageView) {
            String url = ((SmartImageView) view).getImageUrl();
            if (url != null) {

                final SmartImageView imageView = (SmartImageView) view;

                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext())
                        .setCancelable(true)
                        .setTitle("Save image?")
                        .setPositiveButton("save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //TODO: check for storage permissions!, if accepted, allow download, else, prompt that storage access must be granted
                                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                                    ImageHelper.downloadImagefromUrl(SparkApplication.getContext(), imageView.getImageUrl());
                                } else {
                                    //TODO: write cool extensible alertDialog class
                                }

                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

                alert.create().show();

                Toast.makeText(ctx, "long click", Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }

    private void showImageFragment(String url) {

        InteractionFragment interactionFragment = new InteractionFragment();
        Bundle arguments = new Bundle();
        arguments.putString(InteractionFragment.RESOURCE_URL, url);
        arguments.putString(InteractionFragment.RESOURCE_TYPE, InteractionFragment.RESOURCE_IMAGE);
        interactionFragment.setArguments(arguments);

        ctx.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.fragment_container, interactionFragment, "IMAGE")
                .commitNow();

    }

    enum PostType {
        PHOTO, TEXT, VIDEO, QUESTION, ANSWER, CHAT, AUDIO, QUOTE, UNKNOWN, LOADING, LINK
    }

}

