package cool.lucasbedolla.swish.view.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import cool.lucasbedolla.swish.R;

/**
 * Created by Lucas Bedolla on 6/6/2017.
 */


public class BasicViewHolder extends RecyclerView.ViewHolder {
    private ImageView profilePicture;
    private LinearLayout extrasParentLayout;

    private TextView titleTextView;

    private ImageView likeButton;

    private ImageView reblogButton;

    private ImageView extrasButton;

    public BasicViewHolder(View itemView) {
        super(itemView);
        //profilePicture = itemView.findViewById(R.id.profile_picture);
        extrasParentLayout = itemView.findViewById(R.id.extras_parent);
        extrasButton = itemView.findViewById(R.id.extras_button);
        likeButton = itemView.findViewById(R.id.like_button);
        reblogButton = itemView.findViewById(R.id.reblog_button);
        titleTextView = itemView.findViewById(R.id.vTitle);
    }


    public ImageView getProfilePicture() {
        return profilePicture;
    }

    public LinearLayout getExtrasParentLayout() {
        return extrasParentLayout;
    }

    public ImageView getLikeButton() {
        return likeButton;
    }

    public ImageView getExtrasButton() {
        return extrasButton;
    }

    public ImageView getReblogButton() {
        return reblogButton;
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }
}
