package cool.lucasbedolla.swish.activities.onboarding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        View layout = inflater.inflate(R.layout.fragment_onboarding, container, false);
        return configureView(type, layout);
    }

    private View configureView(int type, View layout) {

        switch (type) {
            case 0:
                break;
            case 1:
                layout.findViewById(R.id.go).setVisibility(View.VISIBLE);
                break;
        }

        return layout;
    }


}
