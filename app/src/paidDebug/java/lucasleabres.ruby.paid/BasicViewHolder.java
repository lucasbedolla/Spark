package lucasleabres.ruby.paid;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import cool.lucasbedolla.ruby.R;

/**
 * Created by LUCASVENTURES on 6/18/2016.
 */
public class BasicViewHolder extends RecyclerView.ViewHolder {
    protected TextView vReblogger;
    protected TextView vSource;
    protected ImageView vSourcePic;
    protected ImageView vReblogSign;
    protected ImageView vReblog;
    protected TextView vNotes;
    protected ImageButton vLike;
    protected Button vFollow;
    protected TextView vTitle;

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
}