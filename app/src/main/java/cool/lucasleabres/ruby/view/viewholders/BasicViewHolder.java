package cool.lucasleabres.ruby.view.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import cool.lucasleabres.ruby.R;

/**
 * Created by LUCASVENTURES on 6/18/2016.
 */
public class BasicViewHolder extends RecyclerView.ViewHolder {
    private TextView vReblogger;
    private TextView vSource;
    private ImageView vSourcePic;
    private ImageView vReblogSign;
    private ImageView vReblog;
    private TextView vNotes;
    private ImageButton vLike;
    private Button vFollow;
    private TextView vTitle;

    public BasicViewHolder(View v) {
        super(v);
        vLike = (ImageButton) v.findViewById(R.id.like_post);
        vReblog = (ImageButton) v.findViewById(R.id.reblog_post);
        vNotes = (TextView) v.findViewById(R.id.notes);
        vFollow = (Button) v.findViewById(R.id.post_follow);
        vReblogSign = (ImageView) v.findViewById(R.id.reblog_sign);
        vReblogger = (TextView) v.findViewById(R.id.reblogger);
        vSource = (TextView) v.findViewById(R.id.source);
        vSourcePic = (ImageView) v.findViewById(R.id.source_pic);
        vTitle = (TextView) v.findViewById(R.id.text_title);
    }

    public TextView getReblogger() {
        return vReblogger;
    }

    public TextView getSource() {
        return vSource;
    }

    public ImageView getSourcePic() {
        return vSourcePic;
    }

    public ImageView getReblogSign() {
        return vReblogSign;
    }

    public ImageView getReblog() {
        return vReblog;
    }

    public TextView getNotes() {
        return vNotes;
    }

    public ImageButton getLike() {
        return vLike;
    }

    public Button getFollow() {
        return vFollow;
    }

    public TextView getTitle() {
        return vTitle;
    }
}