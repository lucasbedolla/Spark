package cool.lucasbedolla.swish.util;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import cool.lucasbedolla.swish.SparkApplication;

/**
 * Created by Lucas Bedolla on 2/5/2018.
 */

//TODO: fix downloader, learn why it wont download, learn how to get it to download. refresh on system storage permisssion needs and downloading

public class ImageHelper {
    private static final String TAG = ImageHelper.class.toString();

    /**
     * CONTEXT SHOULD BE AN APPLICATION CONTEXT IN ORDER TO AVOID A MEMORY LEAK
     */


    public static void downloadImageIntoImageView(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .thumbnail(0.1f)
                .into(imageView);
    }


    public static void downloadImagefromUrl(Application context, String imageUrl) {
        DownloadImagesTask downloadImagesTask = new DownloadImagesTask(context);
        downloadImagesTask.execute(imageUrl);
    }

    private static boolean saveImageToInternalStorage(Bitmap image, String dotExtension) {


        File file;


        String path = System.currentTimeMillis() + dotExtension;
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/spark/downloads/");
        if (!file.exists()) {
            file.mkdirs();
        }

        File imageFile = new File(file.getAbsolutePath() + "/" + path);

        try {

            FileOutputStream fos = new FileOutputStream(imageFile);

            switch (dotExtension) {
                case ".jpg":
                    image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    break;
                case ".png":
                    image.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    break;
                case ".webp":
                    image.compress(Bitmap.CompressFormat.WEBP, 100, fos);
                    break;
                default:
                    return false;
            }

            fos.flush();
            fos.close();

            MediaScannerConnection.scanFile(SparkApplication.getContext(), new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                @Override
                public void onScanCompleted(String path, Uri uri) {
                    Log.d(TAG, "onScanCompleted: image scanned");
                }
            });


            return true;
        } catch (Exception e) {
            Log.e("saveToInternalStorage()", e.getMessage());
            return false;
        }
    }

    public static class DownloadImagesTask extends AsyncTask<String, Void, Boolean> {

        private Application ctx;
        private String imageUrl;

        DownloadImagesTask(Application context) {
            ctx = context;
        }

        @Override
        protected Boolean doInBackground(String... url) {
            this.imageUrl = url[0];

            Bitmap image = downloadFile(imageUrl);
            return image != null && saveImageToInternalStorage(image, getExtension(imageUrl));
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            Toast.makeText(ctx, "done saving image", Toast.LENGTH_LONG).show();
        }

        private String getExtension(String uri) {
            String extension = null;
            if (uri.contains(".")) {
                extension = uri.substring(uri.lastIndexOf("."));
            }
            return extension;
        }

        private Bitmap downloadFile(String imageUrl) {
            Bitmap bm = null;
            try {
                URL url = new URL(imageUrl);
                URLConnection conn = url.openConnection();
                conn.connect();

                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bm = BitmapFactory.decodeStream(bis);

                bis.close();
                is.close();
            } catch (IOException e) {
                Log.e(TAG, "Error getting the image from server : " + e.getMessage().toString());
                ctx = null;
            }
            return bm;
        }
    }

}
