package cool.lucasbedolla.swish.adapter;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
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
import com.tumblr.jumblr.types.Post;

import java.lang.ref.WeakReference;
import java.util.List;

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

    private final List<Post> itemList;
    private WeakReference<UnderTheHoodActivity> ctx;

    public RecyclerAdapter(UnderTheHoodActivity underTheHoodActivity, List<Post> inputList) {
        this.ctx = new WeakReference<>(underTheHoodActivity);
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
                ViewHolderBinder.placePhotos(ctx.get(), holder, post, this, this);
                break;
            case TEXT:
                ViewHolderBinder.placeText(ctx.get(), holder, post);
                break;
            case CHAT:
                ViewHolderBinder.placeChat(ctx.get(), holder, post, this);
                break;
            case AUDIO:
                ViewHolderBinder.placeAudio(ctx.get(), holder, post, this);
                break;
            case QUOTE:
                ViewHolderBinder.placeQuote(ctx.get(), holder, post, this);
                break;
            case VIDEO:
                ViewHolderBinder.placeVideo(ctx.get(), holder, post, this);
                break;
            case ANSWER:
                ViewHolderBinder.placeAnswer(ctx.get(), holder, post, this);
                break;
            case QUESTION:
                ViewHolderBinder.placeQuestion(ctx.get(), holder, post, this);
                break;
            case UNKNOWN:
                ViewHolderBinder.placeUnknown(ctx.get(), holder, post, this);
                break;
            case LOADING:
                ViewHolderBinder.placeLoading(ctx.get(), holder, this);
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

    public enum PostType {
        PHOTO, TEXT, VIDEO, QUESTION, ANSWER, CHAT, AUDIO, QUOTE, UNKNOWN, LOADING, LINK
    }

}

