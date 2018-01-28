package cool.lucasbedolla.swish.view.viewholders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import cool.lucasbedolla.swish.R;


/**
 * Created by Lucas Bedolla on 5/31/2017.
 */

public class PhotoSetViewHolder extends BasicViewHolder {

    public PhotoSetViewHolder(View v) {
        super(v);
    }

    public LinearLayout getImageViewHolderLayout() {
        LinearLayout linearLayout = new LinearLayout(getContentTargetLayout().getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setId(R.id.image_holder);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        getContentTargetLayout().addView(linearLayout);
        return linearLayout;
    }
}