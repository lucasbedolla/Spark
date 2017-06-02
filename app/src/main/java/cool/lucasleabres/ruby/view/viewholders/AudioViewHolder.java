package cool.lucasleabres.ruby.view.viewholders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cool.lucasleabres.ruby.R;

/**
 * Created by Lucas Bedolla on 5/31/2017.
 */

//class for chat posts
public class AudioViewHolder extends BasicViewHolder {

    private TextView vDescription;
    private ImageView vArtwork;
    private Button vPlayPause;

    public AudioViewHolder(View v) {
        super(v);

        vDescription = (TextView) v.findViewById(R.id.vDescription);
        vPlayPause = (Button) v.findViewById(R.id.audio_play);
        vArtwork = (ImageView) v.findViewById(R.id.vartwork);
    }
}