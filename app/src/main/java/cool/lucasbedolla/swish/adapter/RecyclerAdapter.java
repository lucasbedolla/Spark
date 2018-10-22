package cool.lucasbedolla.swish.adapter;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.tumblr.jumblr.types.AnswerPost;
import com.tumblr.jumblr.types.AudioPost;
import com.tumblr.jumblr.types.ChatPost;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.QuotePost;
import com.tumblr.jumblr.types.TextPost;
import com.tumblr.jumblr.types.VideoPost;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cool.lucasbedolla.swish.R;
import cool.lucasbedolla.swish.SparkApplication;
import cool.lucasbedolla.swish.activities.MainActivity;
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

    private ArrayList<Post> itemList;
    private WeakReference<UnderTheHoodActivity> ctx;
    private boolean isDual;

    public RecyclerAdapter(UnderTheHoodActivity underTheHoodActivity, ArrayList<Post> inputList) {
        this.ctx = new WeakReference<>(underTheHoodActivity);
        itemList = inputList;
        isDual = MyPrefs.getIsDualMode(underTheHoodActivity);
    }

    @NonNull
    @Override
    public BasicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int postType) {
        if (isDual) {
            return new BasicViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.dual_view_holder_base, parent, false));
        } else {
            return new BasicViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mono_view_holder_base, parent, false));
        }
    }


    //not using magic numbers and enums anymore-
    // favoring a cleaner polymorphic method
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(BasicViewHolder holder, int position) {
        //need to reset the views recycling them, DUH

        resetViewsIfNeeded(holder);

        Post post = itemList.get(position);

        if (post instanceof PhotoPost) {
            ViewHolderBinder.placePhotos(ctx.get(), holder, post, isDual, this, this);
        } else if (post instanceof TextPost) {
            ViewHolderBinder.placeText(ctx.get(), holder, post, isDual);
        } else if (post instanceof ChatPost) {
            ViewHolderBinder.placeChat(ctx.get(), holder, post, isDual, this);
        } else if (post instanceof QuotePost) {
            ViewHolderBinder.placeQuote(ctx.get(), holder, post, isDual, this);
        } else if (post instanceof VideoPost) {
            ViewHolderBinder.placeVideo(ctx.get(), holder, post, isDual, this);
        } else if (post instanceof AnswerPost) {
            ViewHolderBinder.placeAnswer(ctx.get(), holder, post, isDual, this);
        } else if (post instanceof AudioPost) {
            ViewHolderBinder.placeAudio(ctx.get(), holder, post, isDual, this);
        }
    }

    private void resetViewsIfNeeded(BasicViewHolder holder) {
        //just returning it to bone stock base view

        if (holder.getContentTargetLayout().getChildCount() > 0) {
            holder.getContentTargetLayout().removeAllViews();
        }

        holder.getDescription().setVisibility(View.VISIBLE);
        holder.getDescription().setText("");

        holder.getNotes().setText("");
        holder.getAuthorText().setText("");
        holder.getFollowSource().setText("");
        holder.getProfilePicture().setImageDrawable(null);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onClick(View view) {

        if (view instanceof SmartImageView & ctx.get() instanceof MainActivity) {
            String url = ((SmartImageView) view).getImageUrl();
            if (url != null) {
                showImageFragment(url);
            }
        }
    }

    @Override
    public boolean onLongClick(final View view) {
        if (view instanceof SmartImageView) {
            final String url = ((SmartImageView) view).getImageUrl();
            if (url != null) {

                AlertDialog.Builder alert = new AlertDialog.Builder(ctx.get())
                        .setCancelable(true)
                        .setTitle("Save image?")
                        .setPositiveButton("save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //TODO: check for storage permissions!, if accepted, allow download, else, prompt that storage access must be granted
                                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                                    Dexter.withActivity(ctx.get())
                                            .withPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                            .withListener(new PermissionListener() {
                                                @Override
                                                public void onPermissionGranted(PermissionGrantedResponse response) {
                                                    if (url.contains(".gif")) {
                                                        Toast.makeText(ctx.get(), "Saving a GIF file is currently not supported.", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        WeakReference<Application> applicationWeakReference = new WeakReference<>(SparkApplication.getContext());
                                                        ImageHelper.downloadImagefromDrawable(applicationWeakReference, ((ImageView) view).getDrawable());
                                                    }
                                                }

                                                @Override
                                                public void onPermissionDenied(PermissionDeniedResponse response) {
                                                    Toast.makeText(view.getContext(), "Please grant permission in order to save to current device.", Toast.LENGTH_LONG).show();
                                                }

                                                @Override
                                                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                                            }).check();
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

        ctx.get().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.fragment_container, interactionFragment, "IMAGE")
                .commitNow();
    }
}

