package cool.lucasbedolla.swish.view.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import cool.lucasbedolla.swish.R;

/**
 * Created by Lucas Bedolla on 6/6/2017.
 */


public class BasicViewHolder extends RecyclerView.ViewHolder {

    private TextView notes;
    private RoundedImageView profileImageView;
    private LinearLayout extrasParentLayout;
    private FrameLayout contentTargetLayout;
    private TextView description;
    private ImageView likeButton;
    private ImageView reblogButton;
    private ImageButton followButtom;
    private TextView authorText;

    public BasicViewHolder(View itemView) {
        super(itemView);


        //top layout
        profileImageView = itemView.findViewById(R.id.profile_picture);
        authorText = itemView.findViewById(R.id.post_author);

        //content center
        contentTargetLayout = itemView.findViewById(R.id.target_layout);
        description = itemView.findViewById(R.id.post_text_content);

        //bottom layout
        extrasParentLayout = itemView.findViewById(R.id.extras_parent);
        notes = itemView.findViewById(R.id.notes);
        likeButton = itemView.findViewById(R.id.like_button);
        reblogButton = itemView.findViewById(R.id.reblog_button);
    }

    public RoundedImageView getProfilePicture() {
        return profileImageView;
    }

    public TextView getAuthorText() {
        return authorText;
    }

    public LinearLayout getExtrasParentLayout() {
        return extrasParentLayout;
    }

    public ImageView getLikeButton() {
        return likeButton;
    }

    public ImageView getReblogButton() {
        return reblogButton;
    }

    public TextView getDescription() {
        return description;
    }

    public FrameLayout getContentTargetLayout() {
        return contentTargetLayout;
    }

    public LinearLayout getTargetLayoutAsLinearLayout() {
        LinearLayout linearLayout = new LinearLayout(getContentTargetLayout().getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setId(R.id.image_holder);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        getContentTargetLayout().addView(linearLayout);
        return linearLayout;
    }

    public ImageButton getFollowButtom() {
        return followButtom;
    }

    public TextView getNotes() {
        return notes;
    }
}
