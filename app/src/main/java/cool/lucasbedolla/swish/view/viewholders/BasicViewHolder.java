package cool.lucasbedolla.swish.view.viewholders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import androidx.recyclerview.widget.RecyclerView;
import cool.lucasbedolla.swish.R;

/**
 * Created by Lucas Bedolla on 6/6/2017.
 */


public class BasicViewHolder extends RecyclerView.ViewHolder {

    private TextView notes;
    private RoundedImageView profileImageView;
    private FrameLayout contentTargetLayout;
    private TextView description;
    private TextView authorText;
    private TextView followSource;
    private ImageView likeButton, reblogButton;

    public BasicViewHolder(View itemView) {
        super(itemView);
        setupLayout(itemView);
    }

    private void setupLayout(View itemView) {
        //toplayout
        profileImageView = itemView.findViewById(R.id.profile_picture);
        authorText = itemView.findViewById(R.id.post_author);
        followSource = itemView.findViewById(R.id.follow_text);

        //content center
        contentTargetLayout = itemView.findViewById(R.id.target_layout);
        description = itemView.findViewById(R.id.post_text_content);

        //bottom layout
        notes = itemView.findViewById(R.id.notes);
        reblogButton = itemView.findViewById(R.id.reblog_button);
        likeButton = itemView.findViewById(R.id.like_button);
    }

    public RoundedImageView getProfilePicture() {
        return profileImageView;
    }

    public TextView getAuthorText() {
        return authorText;
    }

    public RoundedImageView getProfileImageView() {
        return profileImageView;
    }

    public TextView getFollowSource() {
        return followSource;
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

    public TextView getNotes() {
        return notes;
    }

    public ImageView getReblogButton() {
        return reblogButton;
    }

    public ImageView getLikeButton() {
        return likeButton;
    }


}
