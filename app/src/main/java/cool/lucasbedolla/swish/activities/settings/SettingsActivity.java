package cool.lucasbedolla.swish.activities.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
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
