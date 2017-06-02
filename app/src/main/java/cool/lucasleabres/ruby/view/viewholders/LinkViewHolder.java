package cool.lucasleabres.ruby.view.viewholders;

import android.view.View;
import android.webkit.WebChromeClient;
import android.widget.TextView;

import cool.lucasleabres.ruby.R;

/**
 * Created by Lucas Bedolla on 5/31/2017.
 */

public class LinkViewHolder extends BasicViewHolder {

    private TextView vTitle;
    private TextView vDescription;
    private WebChromeClient vWebView;

    public LinkViewHolder(View v) {
        super(v);
        vTitle = (TextView) v.findViewById(R.id.link_title);
        vDescription = (TextView) v.findViewById(R.id.link_desc);
    }
}