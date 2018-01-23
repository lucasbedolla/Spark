package cool.lucasbedolla.swish.view.viewholders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cool.lucasbedolla.swish.R;
import cool.lucasbedolla.swish.view.SmartImageView;


/**
 * Created by Lucas Bedolla on 5/31/2017.
 */

public class PhotoSetViewHolder extends BasicViewHolder {

    private TextView title;

    private View top;

    private View bottom;
    private LinearLayout imageViewHolderLayout;

    public PhotoSetViewHolder(View v) {
        super(v);

        top = v.findViewById(R.id.top);
        bottom = v.findViewById(R.id.bottom);

        imageViewHolderLayout = v.findViewById(R.id.image_holder);

        title = v.findViewById(R.id.vTitle);

    }

    public LinearLayout getImageViewHolderLayout() {
        return imageViewHolderLayout;
    }

    public View getBottom() {
        return bottom;
    }

    public View getTop() {
        return top;
    }
}