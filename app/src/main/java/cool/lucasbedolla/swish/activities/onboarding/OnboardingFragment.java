package cool.lucasbedolla.swish.activities.onboarding;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import cool.lucasbedolla.swish.R;

/**
 * Created by Lucas Bedolla on 10/23/2017.
 */

public class OnboardingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String text = bundle.getString("text");
        String url = bundle.getString("url");
        View layout = inflater.inflate(R.layout.fragment_onboarding, container, false);
        return configureView(layout, url, text);
    }

    private View configureView(View layout, String url, String text) {

        TextView title = layout.findViewById(R.id.title);
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(),
                "OdinBold.otf");
        title.setText(text);
        title.setTypeface(face);

        ImageView image = layout.findViewById(R.id.image);

        Glide.with(getActivity())
                .load(url)
                .thumbnail(0.1f)
                .into(image);

        return layout;
    }

}
