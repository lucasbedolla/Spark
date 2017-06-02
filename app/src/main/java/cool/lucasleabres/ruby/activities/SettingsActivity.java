package cool.lucasleabres.ruby.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cool.lucasleabres.ruby.R;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    CheckBox cachingChecked;
    CheckBox multiPostChecked;

    Toolbar toolbar;

    Button info;
    Button logout;

    ImageView gem;
    TextView rubyText;
    private boolean isUp = false;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        context = this;

        toolbar.setNavigationIcon(R.drawable.sb);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");


        multiPostChecked.setOnCheckedChangeListener(this);
        cachingChecked.setOnCheckedChangeListener(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getBoolean("grid", false)) {
            multiPostChecked.setChecked(true);
        } else {
            multiPostChecked.setChecked(false);
        }

        if (preferences.getBoolean("cache", false)) {
            cachingChecked.setChecked(true);
        } else {
            cachingChecked.setChecked(false);
        }

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/arial.ttf");
        rubyText.setTypeface(face);
        info.setOnClickListener(this);
        logout.setOnClickListener(this);
        logout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PreferenceManager.getDefaultSharedPreferences(context).edit().clear().commit();
                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return false;
            }
        });


        startAnimation(gem, 1400);
        startAnimation(rubyText, 1800);
    }

    @Override
    public void onResume() {
        super.onResume();


    }


    private void startAnimation(View v, int milis) {

        v.setVisibility(View.VISIBLE);

        Animation scaleAnim = new ScaleAnimation(
                0f, 1f,
                0f, 1f,
                Animation.RELATIVE_TO_SELF, .5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnim.setInterpolator(new OvershootInterpolator(4f));


        //Animation alphaAnim = new AlphaAnimation(0f,1f);
        //alphaAnim.setInterpolator(new LinearInterpolator());


        AnimationSet set = new AnimationSet(false);
        set.addAnimation(scaleAnim);
        //set.addAnimation(alphaAnim);
        set.setFillAfter(true);
        set.setDuration(milis);
        set.start();
        v.setAnimation(set);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info:
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Ruby for Tumblr, v" + getPackageInfo().versionName + "\n by Lucas Leabres")
                        .setMessage("Libraries Used, and Legal Licences:\n" +
                                "\n" +
                                "Butterknife\n" +
                                "\n" +
                                "Copyright 2013 Jake Wharton\n" +
                                "\n" +
                                "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                                "you may not use this file except in compliance with the License.\n" +
                                "You may obtain a copy of the License at\n" +
                                "\n" +
                                "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                                "\n" +
                                "Unless required by applicable law or agreed to in writing, software\n" +
                                "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                                "See the License for the specific language governing permissions and\n" +
                                "limitations under the License.\n" +
                                "\n" +
                                "=======================================================================\n" +
                                "\n" +
                                "\n" +
                                "Retrofit\n" +
                                "\n" +
                                "Copyright 2013 Square, Inc.\n" +
                                "\n" +
                                "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                                "you may not use this file except in compliance with the License.\n" +
                                "You may obtain a copy of the License at\n" +
                                "\n" +
                                "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                                "\n" +
                                "Unless required by applicable law or agreed to in writing, software\n" +
                                "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                                "See the License for the specific language governing permissions and\n" +
                                "limitations under the License.\n" +
                                "\n" +
                                "=======================================================================\n" +
                                "\n" +
                                "Loglr\n" +
                                "\n" +
                                "Copyright (C) 2016  Daksh Srivastava\n" +
                                "\n" +
                                "This program is free software: you can redistribute it and/or modify\n" +
                                "it under the terms of the GNU General Public License as published by\n" +
                                "the Free Software Foundation, either version 3 of the License, or\n" +
                                "(at your option) any later version.\n" +
                                "\n" +
                                "This program is distributed in the hope that it will be useful,\n" +
                                "but WITHOUT ANY WARRANTY; without even the implied warranty of\n" +
                                "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" +
                                "GNU General Public License for more details.\n" +
                                "\n" +
                                "You should have received a copy of the GNU General Public License\n" +
                                "along with this program.  If not, see <http://www.gnu.org/licenses/>.\n" +
                                "\n" +
                                "=======================================================================\n" +
                                "\n" +
                                "Signpost\n" +
                                "\n" +
                                " Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                                "    you may not use this file except in compliance with the License.\n" +
                                "    You may obtain a copy of the License at\n" +
                                "\n" +
                                "       http://www.apache.org/licenses/LICENSE-2.0\n" +
                                "\n" +
                                "    Unless required by applicable law or agreed to in writing, software\n" +
                                "    distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                                "    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                                "    See the License for the specific language governing permissions and\n" +
                                "    limitations under the License.\n" +
                                "\n" +
                                "=======================================================================\n" +
                                "\n" +
                                "Picasso\n" +
                                "\n" +
                                "Copyright 2013 Square, Inc.\n" +
                                "\n" +
                                "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                                "you may not use this file except in compliance with the License.\n" +
                                "You may obtain a copy of the License at\n" +
                                "\n" +
                                "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                                "\n" +
                                "Unless required by applicable law or agreed to in writing, software\n" +
                                "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                                "See the License for the specific language governing permissions and\n" +
                                "limitations under the License.\n" +
                                "\n" +
                                "=======================================================================\n" +
                                "\n" +
                                "Floating Action Button\n" +
                                "\n" +
                                "The MIT License (MIT)\n" +
                                "\n" +
                                "Copyright (c) 2014 Oleksandr Melnykov\n" +
                                "\n" +
                                "Permission is hereby granted, free of charge, to any person obtaining a copy\n" +
                                "of this software and associated documentation files (the \"Software\"), to deal\n" +
                                "in the Software without restriction, including without limitation the rights\n" +
                                "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
                                "copies of the Software, and to permit persons to whom the Software is\n" +
                                "furnished to do so, subject to the following conditions:\n" +
                                "\n" +
                                "The above copyright notice and this permission notice shall be included in all\n" +
                                "copies or substantial portions of the Software.\n" +
                                "\n" +
                                "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
                                "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
                                "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" +
                                "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
                                "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
                                "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n" +
                                "SOFTWARE.\n" +
                                "\n" +
                                "\n" +
                                "=======================================================================\n" +
                                "\n" +
                                "Jumblr\n" +
                                "\n" +
                                "Copyright 2015 Tumblr, Inc.\n" +
                                "\n" +
                                "Licensed under the Apache License, Version 2.0 (the \"License\"); you may not \n" +
                                "use this work except in compliance with the License. You may obtain a copy of \n" +
                                "the License in the LICENSE file, or at:\n" +
                                "\n" +
                                "http://www.apache.org/licenses/LICENSE-2.0\n" +
                                "\n" +
                                "Unless required by applicable law or agreed to in writing, software \n" +
                                "distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT WARRANTIES \n" +
                                "OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific \n" +
                                "language governing permissions and limitations.\n" +
                                "\n" +
                                "=========================================================================\n" +
                                "\n" +
                                "PhotoView\n" +
                                "\n" +
                                "Copyright 2011, 2012 Chris Banes\n" +
                                "\n" +
                                "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                                "you may not use this file except in compliance with the License.\n" +
                                "You may obtain a copy of the License at\n" +
                                "\n" +
                                "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                                "\n" +
                                "Unless required by applicable law or agreed to in writing, software\n" +
                                "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                                "See the License for the specific language governing permissions and\n" +
                                "limitations under the License.\n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "\n")
                        .create();
                dialog.show();

                break;
            case R.id.logout:
                Toast.makeText(SettingsActivity.this, "Hold down to logout.", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    private PackageInfo getPackageInfo() {
        PackageInfo pi = null;
        try {
            pi = this.getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi;
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.gridCheckBox:
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                if (isChecked) {
                    multiPostChecked.setChecked(true);
                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putBoolean("grid", true).apply();
                } else {
                    multiPostChecked.setChecked(false);
                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putBoolean("grid", false).apply();
                }
                break;
            case R.id.cachingCheckBox:
                SharedPreferences refs = PreferenceManager.getDefaultSharedPreferences(this);
                if (isChecked) {
                    cachingChecked.setChecked(true);
                    SharedPreferences.Editor edit = refs.edit();
                    edit.putBoolean("cache", true).apply();

                } else {
                    cachingChecked.setChecked(false);
                    SharedPreferences.Editor edit = refs.edit();
                    edit.putBoolean("cache", false).apply();
                }
                break;
        }
    }
}
