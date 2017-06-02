package lucasleabres.ruby.paid.loginpager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cool.lucasbedolla.ruby.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginPagerContentFragment extends Fragment {

    public LoginPagerContentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_login_pager_content, container, false);
        ImageView bgn = (ImageView) view.findViewById(R.id.content);
        int num = (int) getArguments().get("frag");
        switch (num) {
            case 1:
                bgn.setImageDrawable(getResources().getDrawable(R.drawable.client));
                break;
            case 2:
                bgn.setImageDrawable(getResources().getDrawable(R.drawable.navigation));
                break;
            case 3:
                bgn.setImageDrawable(getResources().getDrawable(R.drawable.single));
                break;
            case 4:
                bgn.setImageDrawable(getResources().getDrawable(R.drawable.enjoy));
                break;
            default:
        }

        return view;
    }

}
