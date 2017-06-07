package cool.lucasleabres.ruby.view.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cool.lucasleabres.ruby.R;

/**
 * Created by Lucas Bedolla on 5/31/2017.
 */

public class PhotoSetViewHolder extends BasicViewHolder {

    private ImageView[] images = new ImageView[10];

    private TextView title;

    public PhotoSetViewHolder(View v) {
        super(v);

        title = (TextView) v.findViewById(R.id.vTitle);
        images[0] = (ImageView) v.findViewById(R.id.image_post);
        images[1] = (ImageView) v.findViewById(R.id.image_post1);
        images[2] = (ImageView) v.findViewById(R.id.image_post2);
        images[3] = (ImageView) v.findViewById(R.id.image_post3);
        images[4] = (ImageView) v.findViewById(R.id.image_post4);
        images[5] = (ImageView) v.findViewById(R.id.image_post5);
        images[6] = (ImageView) v.findViewById(R.id.image_post6);
        images[7] = (ImageView) v.findViewById(R.id.image_post7);
        images[8] = (ImageView) v.findViewById(R.id.image_post8);
        images[9] = (ImageView) v.findViewById(R.id.image_post9);
    }

    public ImageView[] getImages() {
        return images;
    }

}