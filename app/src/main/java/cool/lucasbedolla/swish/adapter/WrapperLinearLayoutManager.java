package cool.lucasbedolla.swish.adapter;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WrapperLinearLayoutManager extends LinearLayoutManager {

    public WrapperLinearLayoutManager(Context context) {
        super(context);
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