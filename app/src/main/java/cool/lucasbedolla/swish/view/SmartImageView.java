package cool.lucasbedolla.swish.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import cool.lucasbedolla.swish.R;

/**
 * Created by Lucas Bedolla on 11/28/2017.
 */

public class SmartImageView extends androidx.appcompat.widget.AppCompatImageView implements View.OnClickListener {

    private String imageUrl;
    private float aspectRatio;

    public SmartImageView(Context context) {
        super(context);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setAdjustViewBounds(true);
        setId(R.id.smartImageView);

    }

    @Override
    public void onClick(View view) {
        //TODO: make view inflate into new layout that responds to gestures.
        // make the view load into a special view made for swiping away, but also for zooming and whatnot.
    }

    public void setAspectRatio(int height, int width) {
        this.aspectRatio = ((float) height / width);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (width * aspectRatio), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}


