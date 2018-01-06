package cool.lucasbedolla.swish.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LUCASURE on 2/7/2016.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private int[] mResources;

    public ViewPagerAdapter(Context mContext, int[] mResources) {
        this.mContext = mContext;
        this.mResources = mResources;
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(mResources[position], container, false);

        switch (position) {
            case 0:
                layout.setTag("zero");
                //ImageView spin = (ImageView) layout.findViewById(R.id.gem_spinner);
                //spin.setVisibility(View.VISIBLE);
                //ImageView gem = (ImageView) layout.findViewById(R.id.gem_center);
                //gem.setVisibility(View.VISIBLE);
                break;
            case 1:
                layout.setTag("one");

                break;
            case 2:
                layout.setTag("two");

                break;
            case 3:
                layout.setTag("three");

                break;
            case 4:
                layout.setTag("four");

                break;
            default:
                break;
        }

        container.addView(layout);

        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}