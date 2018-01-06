package cool.lucasbedolla.swish.view.viewholders;

import android.view.View;
import android.widget.ProgressBar;

import cool.lucasbedolla.swish.R;

/**
 * Created by Lucas Bedolla on 5/31/2017.
 */
//progress bar
public class LoadingViewHolder extends BasicViewHolder {


    public LoadingViewHolder(View itemView) {
        super(itemView);
        ProgressBar progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
    }
}