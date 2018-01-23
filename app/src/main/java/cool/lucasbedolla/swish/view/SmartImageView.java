package cool.lucasbedolla.swish.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Lucas Bedolla on 11/28/2017.
 */

public class SmartImageView extends android.support.v7.widget.AppCompatImageView implements View.OnClickListener {

    String imageUrl;

    public SmartImageView(Context context) {
        super(context);
    }

    @Override
    public void onClick(View view) {
        //TODO: make view inflate into new layout that responds to gestures.
       // make the view load into a special view made for swiping away, but also for zooming and whatnot.
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}


