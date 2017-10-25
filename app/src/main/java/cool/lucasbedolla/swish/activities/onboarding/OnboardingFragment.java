package cool.lucasbedolla.swish.activities.onboarding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import cool.lucasbedolla.swish.R;

/**
 * Created by Lucas Bedolla on 10/23/2017.
 */

public class OnboardingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int type = bundle.getInt("key");
        String url = bundle.getString("url");
        View layout = inflater.inflate(R.layout.fragment_onboarding, container, false);


        return configureView(type, layout, url);
    }

    private View configureView(int type, View layout, String url) {

        switch (type) {
            case 0:
                break;
            case 1:
                //layout.findViewById(R.id.go).setVisibility(View.VISIBLE);
                break;
        }

        ImageView image = layout.findViewById(R.id.image);

        Glide.with(getActivity())
                .load(url)
                .thumbnail(0.1f)
                .into(image);
        return layout;
    }


}
