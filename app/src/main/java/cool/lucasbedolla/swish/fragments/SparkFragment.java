package cool.lucasbedolla.swish.fragments;


import android.app.ActivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import cool.lucasbedolla.swish.R;
import cool.lucasbedolla.swish.util.MyPrefs;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SparkFragment extends Fragment implements View.OnClickListener {

    public static final int ID = 1;


    //    private RadioButton classic, minimalist, extremeMinimalist;
    private CheckBox dualMode;


    public SparkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_spark, container, false);

        dualMode = layout.findViewById(R.id.dual_mode);

        dualMode.setChecked(MyPrefs.getIsDualMode(getActivity()));

        dualMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                MyPrefs.setIsDualMode(getActivity(), b);
            }
        });
        // Other click listeners are set in XML

        TextView logout = layout.findViewById(R.id.logout);
        logout.setOnClickListener(this);

        return layout;
    }


    @Override
    public void onClick(View view) {
        ((ActivityManager) getActivity().getSystemService(ACTIVITY_SERVICE))
                .clearApplicationUserData(); // note: it has a return value!
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    public void onRadioButtonClicked(View view) {

        boolean b = ((RadioButton) view).isChecked();
        switch (view.getId()) {

            case R.id.classic:

                MyPrefs.setIsClassicMode(getActivity(), b);

                MyPrefs.setIsMinimalistMode(getActivity(), !b);
                MyPrefs.setIsExtremeMinimalistMode(getActivity(), !b);

                break;

            case R.id.minimalist:
                MyPrefs.setIsMinimalistMode(getActivity(), b);

                MyPrefs.setIsClassicMode(getActivity(), !b);
                MyPrefs.setIsExtremeMinimalistMode(getActivity(), !b);
                break;

            case R.id.extreme_minimalist:
                MyPrefs.setIsExtremeMinimalistMode(getActivity(), b);
                MyPrefs.setIsClassicMode(getActivity(), !b);
                MyPrefs.setIsMinimalistMode(getActivity(), !b);
                break;
        }
    }

}
