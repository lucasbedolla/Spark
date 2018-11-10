package cool.lucasbedolla.swish.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import cool.lucasbedolla.swish.R;
import cool.lucasbedolla.swish.activities.onboarding.OnboardingActivity;
import cool.lucasbedolla.swish.listeners.FragmentEventController;
import cool.lucasbedolla.swish.util.MyPrefs;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {

    public static final int ID = 3;


    //    private RadioButton classic, minimalist, extremeMinimalist;
    private CheckBox dualMode;


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentr
        View layout = inflater.inflate(R.layout.fragment_settings, container, false);

        layout.findViewById(R.id.white_space).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

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
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().clear().apply();
                Intent intent = new Intent(getActivity(), OnboardingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        return layout;
    }


    @Override
    public void onClick(View v) {
        ((FragmentEventController) getActivity()).submitEvent(ID, v, 0);
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
