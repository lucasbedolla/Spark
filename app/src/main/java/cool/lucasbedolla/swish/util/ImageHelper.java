package cool.lucasbedolla.swish.util;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;

import cool.lucasbedolla.swish.SparkApplication;

/**
 * Created by Lucas Bedolla on 2/5/2018.
 */


public class ImageHelper {

    private static final String TAG = ImageHelper.class.toString();
    private static final String IMAGE_GIF = ".gif";

    public static File createSparkFile(String extension) {

        //must ensure our special directory exists
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        if (!file.exists()) {
            file.mkdirs();
        }

        return new File(file.getAbsolutePath() + generatePath(extension));
    }

    private static String generatePath(String extension) {
        return "/spark/downloads/" +
                "spark_download_" + System.currentTimeMillis() + extension;
    }


    public static void downloadImageIntoImageView(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .thumbnail(0.1f)
                .into(imageView);
    }

    public static void downloadBlogAvatarIntoImageView(ImageView imageView, String blogName) {
        Glide.with(imageView.getContext())
                .load("http://api.tumblr.com/v2/blog/" + blogName.trim() + "/avatar/512")
                .thumbnail(0.1f)
                .into(imageView);
    }

    public static void downloadImagefromDrawable(WeakReference<Application> context, Drawable drawable) {
        DownloadImagesTask downloadImagesTask = new DownloadImagesTask(context, drawable);
        downloadImagesTask.execute();
    }

    public static void downloadImagefromUrl(WeakReference<Application> context, String resourceUrl) {
        DownloadImagesTask downloadImagesTask = new DownloadImagesTask(context, resourceUrl);
        downloadImagesTask.execute();
    }

    public static boolean saveGIFToInternalStorage(byte[] gif) {
        boolean success;

        File file = createSparkFile(IMAGE_GIF);

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(gif);

            outputStream.flush();
            outputStream.close();

            scanFile(file);

            success = true;
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    private static void scanFile(File file) {
        MediaScannerConnection.scanFile(SparkApplication.getContext(), new String[]{file.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String path, Uri uri) {
                Log.d(TAG, "onScanCompleted: File scanned");
            }
        });
    }

    private static boolean saveImageToInternalStorage(Bitmap image, String dotExtension) {
        File imageFile = createSparkFile(dotExtension);

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

            scanFile(imageFile);

            return true;
        } catch (Exception e) {
            Log.e("saveToInternalStorage()", e.getMessage());
            return false;
        }
    }

    public static class DownloadImagesTask extends AsyncTask<Void, Void, Boolean> {

        private WeakReference<Application> ctx;
        private Drawable drawable;
        private String resourceUrl;

        DownloadImagesTask(WeakReference<Application> context, Drawable drawable) {
            this.drawable = drawable;
            ctx = context;
        }

        DownloadImagesTask(WeakReference<Application> context, String resourceUrl) {
            ctx = context;
            this.resourceUrl = resourceUrl;
        }

        @Override
        protected Boolean doInBackground(Void... url) {
            if (drawable == null) {

                return false;
            } else {
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                return ImageHelper.saveImageToInternalStorage(bitmap, getExtension(resourceUrl));
            }


//            if (getExtension(imageUrl).equals(".gif")) {
//                byte[] gif = downloadGIFFile(imageUrl);
//                return gif != null && gif.length > 0 && saveGIFToInternalStorage(gif);
//            } else {
//                Bitmap image = downloadImageFile(imageUrl);
//                return image != null && saveImageToInternalStorage(image, getExtension(imageUrl));
//            }
        }

        private byte[] downloadGIFFile(String gifUrl) {


            try {
                URL url = new URL(gifUrl);
                URLConnection conn = url.openConnection();
                conn.connect();

                InputStream is = conn.getInputStream();

                BufferedInputStream bis = new BufferedInputStream(is);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                int current;
                while ((current = bis.read()) != -1) {
                    baos.write(current);
                }
                return baos.toByteArray();

            } catch (IOException e) {
                Log.e(TAG, "downloadGIFFile: IO ERROR downloading gif.", e);
            }
            return new byte[0];
        }


        private Bitmap downloadImageFile(String imageUrl) {
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
                Log.e(TAG, "Error getting the image from server : " + e.getMessage());

            }

            return bm;
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            Toast.makeText(ctx.get(), "Save complete.", Toast.LENGTH_LONG).show();
        }

        private String getExtension(String uri) {
            String extension = null;
            if (uri.contains(".")) {
                extension = uri.substring(uri.lastIndexOf("."));
            }

            if (extension == null) {
                extension = ".jpg";
            }

            return extension.trim();
        }
    }

}
