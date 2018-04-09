package cool.lucasbedolla.swish.util;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Lucas Bedolla on 2/5/2018.
 */

public class ImageHelper {
    private static final String TAG = ImageHelper.class.toString();

    /**
     * CONTEXT SHOULD BE AN APPLICATION CONTEXT IN ORDER TO AVOID A MEMORY LEAK
     *
     * @param context
     * @param imageUrl
     */

    public static void downloadImagefromUrl(Application context, String imageUrl) {
        DownloadImagesTask downloadImagesTask = new DownloadImagesTask(context);
        downloadImagesTask.execute(imageUrl);
    }

    private static boolean saveImageToInternalStorage(Context context, Bitmap image, String dotExtension) {

        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String path = file.getAbsolutePath() + "/" + System.currentTimeMillis() + dotExtension;
        try {
            FileOutputStream fos = context.openFileOutput(path, Context.MODE_PRIVATE);
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
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            MediaScannerConnection.scanFile(context, new String[]{path}, new String[]{"image/" + dotExtension.substring(1)}, null);

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
            return image != null && saveImageToInternalStorage(ctx, image, getExtension(imageUrl));
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
