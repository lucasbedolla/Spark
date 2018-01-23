package cool.lucasbedolla.swish.activities.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import cool.lucasbedolla.swish.R;
import cool.lucasbedolla.swish.core.UnderTheHoodActivity;
import cool.lucasbedolla.swish.util.MyPrefs;

public class SettingsActivity extends UnderTheHoodActivity {

    private RadioButton classic, minimalist, extremeMinimalist;
    private CheckBox dualMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        classic = findViewById(R.id.classic);
        minimalist = findViewById(R.id.minimalist);
        extremeMinimalist = findViewById(R.id.extreme_minimalist);
        dualMode = findViewById(R.id.dual_mode);

        classic.setChecked(MyPrefs.getIsClassicMode(this));
        minimalist.setChecked(MyPrefs.getIsMinimalistMode(this));
        extremeMinimalist.setChecked(MyPrefs.getIsExtremeMinimalist(this));
        dualMode.setChecked(MyPrefs.getIsDualMode(this));

        dualMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                MyPrefs.setIsDualMode(SettingsActivity.this, b);
            }
        });
        // Other click listeners are set in XML
    }

    @Override
    public void onClick(View view) {

    }


    public void onRadioButtonClicked(View view) {

        boolean b = ((RadioButton) view).isChecked();
        switch (view.getId()) {

            case R.id.classic:

                MyPrefs.setIsClassicMode(this, b);

                MyPrefs.setIsMinimalistMode(this, !b);
                MyPrefs.setIsExtremeMinimalistMode(this, !b);

                break;

            case R.id.minimalist:
                MyPrefs.setIsMinimalistMode(this, b);

                MyPrefs.setIsClassicMode(this, !b);
                MyPrefs.setIsExtremeMinimalistMode(this, !b);
                break;

            case R.id.extreme_minimalist:
                MyPrefs.setIsExtremeMinimalistMode(this, b);
                MyPrefs.setIsClassicMode(this, !b);
                MyPrefs.setIsMinimalistMode(this, !b);
                break;
        }
    }

}
