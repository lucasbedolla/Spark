package cool.lucasbedolla.swish.view.viewholders;

import android.view.View;
import android.widget.TextView;

import cool.lucasbedolla.swish.R;
import cool.lucasbedolla.swish.view.SmartImageView;


/**
 * Created by Lucas Bedolla on 5/31/2017.
 */

public class PhotoSetViewHolder extends BasicViewHolder {

    private SmartImageView[] images = new SmartImageView[10];

    private TextView title;

    public PhotoSetViewHolder(View v) {
        super(v);

        title = (TextView) v.findViewById(R.id.vTitle);
        images[0] = v.findViewById(R.id.image_post);
        images[1] = v.findViewById(R.id.image_post1);
        images[2] = v.findViewById(R.id.image_post2);
        images[3] = v.findViewById(R.id.image_post3);
        images[4] = v.findViewById(R.id.image_post4);
        images[5] = v.findViewById(R.id.image_post5);
        images[6] = v.findViewById(R.id.image_post6);
        images[7] = v.findViewById(R.id.image_post7);
        images[8] = v.findViewById(R.id.image_post8);
        images[9] = v.findViewById(R.id.image_post9);
    }

    public SmartImageView[] getImages() {
        return images;
    }

}