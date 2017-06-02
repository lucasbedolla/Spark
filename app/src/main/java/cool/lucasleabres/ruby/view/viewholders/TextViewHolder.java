package cool.lucasleabres.ruby.view.viewholders;

import android.view.View;
import android.widget.TextView;

import cool.lucasleabres.ruby.R;

/**
 * Created by Lucas Bedolla on 5/31/2017.
 */

//class for text posts
public class TextViewHolder extends BasicViewHolder {

    private TextView vTitle;
    private TextView vContent;

    public TextViewHolder(View v) {
        super(v);
        vTitle = (TextView) v.findViewById(R.id.text_title);
        vContent = (TextView) v.findViewById(R.id.text_content);
    }
}