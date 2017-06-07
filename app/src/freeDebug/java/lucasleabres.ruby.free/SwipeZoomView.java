package lucasleabres.ruby.free;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import cool.lucasbedolla.ruby.R;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by LUCASVENTURES on 8/21/2016.
 */
public class SwipeZoomView extends Fragment implements View.OnClickListener {

    public static final String TAG = "SWIPEZOOMVIEW FRAGMENT";

    private String URL;

    private ImageView imageView;

    public SwipeZoomView() {
        super();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle = getArguments();
        setURL(bundle.getString("URL"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View layout = inflater.inflate(R.layout.zoom_view, container, false);

        ImageButton button = (ImageButton) layout.findViewById(R.id.button);
        button.setOnClickListener(this);
        ImageButton save = (ImageButton) layout.findViewById(R.id.save);
        save.setOnClickListener(this);

        imageView = (ImageView) layout.findViewById(R.id.image);

        PhotoViewAttacher zoomer = new PhotoViewAttacher(imageView);
        Picasso.with(getActivity())
                .load(URL)
                .into(imageView);
        zoomer.update();


        MainActivity act = (MainActivity) getActivity();
        act.fab.setVisibility(View.INVISIBLE);

        return layout;
    }


    private void setURL(String url) {
        URL = url;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.button):
                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                MainActivity act = (MainActivity) getActivity();
                act.fab.setVisibility(View.VISIBLE);
                break;
            case (R.id.save):
                savePhoto();
                break;
        }

    }


    private void savePhoto() {
        if (imageView.getDrawable() != null) {
            Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            vibe.vibrate(50);

            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            View customView = inflater.inflate(R.layout.dialog_save, null, false);
            Button savePhoto = (Button) customView.findViewById(R.id.save);
            savePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try {


                                Drawable d = imageView.getDrawable();
                                Bitmap image = ((BitmapDrawable) d).getBitmap();
                                //create or check for directory
                                File rubyDir = new File(Environment.getExternalStorageDirectory()

                                        + "/ruby");

                                // Create the storage directory if it does not exist
                                if (!rubyDir.exists()) {
                                    if (!rubyDir.mkdirs()) {

                                    }
                                }
                                //continue
                                String instaGator = new String(Environment.getExternalStorageDirectory() + "/ruby");
                                String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
                                File file = new File(instaGator, timeStamp + ".jpg");

                                FileOutputStream fos = new FileOutputStream(file);
                                image.compress(Bitmap.CompressFormat.JPEG, 100, fos);

                                MediaScannerConnection.scanFile(getActivity(), new String[]{file.getPath()},
                                        new String[]{timeStamp + ".jpg"}, null);
                                fos.close();

                                int id = 10;

                                NotificationManager manager =
                                        (NotificationManager) getActivity()
                                                .getSystemService(Context.NOTIFICATION_SERVICE);

                                NotificationCompat.Builder builder =
                                        (NotificationCompat.Builder) new NotificationCompat.Builder(getActivity())
                                                .setContentTitle("Picture Download")
                                                .setContentText("Download in progress")
                                                .setSmallIcon(R.mipmap.ic_launcher);

                                int incr;
                                // Do the "lengthy" operation 20 times
                                for (incr = 0; incr <= 100; incr += 20) {
                                    // Sets the progress indicator to a max value, the
                                    // current completion percentage, and "determinate"
                                    // state
                                    builder.setProgress(100, incr, false);
                                    // Displays the progress bar for the first time.
                                    manager.notify(id, builder.build());
                                    // Sleeps the thread, simulating an operation
                                    // that takes time
                                    try {
                                        // Sleep for 1 seconds
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        Log.d(TAG, "sleep failure");
                                    }
                                }


                                // When the loop is finished, updates the notification
                                builder.setContentText("Download complete")
                                        // Removes the progress bar
                                        .setProgress(0, 0, false);
                                manager.notify(id, builder.build());

                                Log.d(TAG, "downloading image.");

                            } catch (Throwable e) {

                                e.printStackTrace();

                            }

                        }
                    }).start();


                }
            });
            AlertDialog dialog;
            dialog = new AlertDialog.Builder(getActivity())
                    .setCustomTitle(customView)
                    .create();
            dialog.show();
        }
    }
}
