package cool.lucasbedolla.swish.adapter;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class WrapperStaggeredLayout extends StaggeredGridLayoutManager {


    public WrapperStaggeredLayout(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            Log.e("ERROR", "INDEX OUT OF BOUNDS EXCEPTION - RECYCLERVIEW QUIRK");
        }
    }
}