package lucasleabres.ruby.paid;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cool.lucasbedolla.ruby.R;

public class TestingActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

    }

    private void inflateImageViewer(String urly) {
        SwipeZoomView zoomView = new SwipeZoomView();
        Bundle bundle = new Bundle();
        bundle.putString("URL", urly);
        zoomView.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.your_placeholder, zoomView).commit();
    }

    @Override
    public void onClick(View v) {
        button.setVisibility(View.INVISIBLE);
    }
}
