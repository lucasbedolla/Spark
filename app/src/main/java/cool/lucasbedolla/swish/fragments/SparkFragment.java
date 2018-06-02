package cool.lucasbedolla.swish.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cool.lucasbedolla.swish.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SparkFragment extends Fragment {

    public static final int ID = 1;

    public SparkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spark, container, false);
    }

}
