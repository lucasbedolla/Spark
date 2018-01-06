package cool.lucasbedolla.swish.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Lucas Bedolla on 11/28/2017.
 */

public class SmartImageView extends android.support.v7.widget.AppCompatImageView {

    private float aspectRatio;

    public SmartImageView(Context context) {
        super(context);
        setAdjustViewBounds(true);
    }

    public SmartImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAdjustViewBounds(true);
    }

    public void setAspectRatio(float ratio) {
        aspectRatio = ratio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (width * aspectRatio), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}


