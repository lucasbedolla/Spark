package cool.lucasleabres.ruby.view.viewholders;

import android.view.View;
import android.widget.TextView;

import cool.lucasleabres.ruby.R;

/**
 * Created by Lucas Bedolla on 5/31/2017.
 */

//class for chat posts
public class ChatViewHolder extends BasicViewHolder {

    private TextView vTitle;
    private TextView vDescription;

    public ChatViewHolder(View v) {
        super(v);
        vTitle = (TextView) v.findViewById(R.id.chat_title);
        vDescription = (TextView) v.findViewById(R.id.chat_desc);
    }

}

