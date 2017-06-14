package net.sqlengineer.comics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import net.sqlengineer.comics.data.Result;

import java.io.InputStream;

/**
 * Created by druckebusch on 6/7/17.
 */

public class ImageUtils {

    public static enum AspectRatios{

        LANDSCAPE("landscape"),
        PORTRAIT("portrait"),
        STANDARD("standard");

        private final String text;

        private AspectRatios(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public static enum Sizes {

        SMALL("small"),
        MEDIUM("medium"),
        LARGE("large"),
        XLARGE("xlarge"),
        AMAZING("amazing"),
        INCREDIBLE("incredible");

        private final String text;

        private Sizes(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }


    public static String getUrl(String path, String extension, Sizes size, AspectRatios ratio){
        String fileName = ratio.toString() + "_" + size.toString() + "." + extension;
        //TODO build the correct way
        return path + "/" + fileName;
    }

    public static void setBitmapAsync(CoverImage view, String url) {
        new DownloadImageTask(view).execute(url);
    }

    public static void setBitmapAsync(Result result, String url) {
        new DownloadResultImageTask(result).execute(url);
    }




    private static class DownloadResultImageTask extends AsyncTask<String, Void, Bitmap> {

        // TODO REMOVE DEBUG
        static int count = 0;

        Result mResult;

        public DownloadResultImageTask(Result result) {mResult = result;}

        @Override
        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap ret = null;
            Log.i("TAG", "Download number " + (++count));
            try {
                InputStream in = new java.net.URL(url).openStream();
                ret = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            mResult.setCoverBitmap(ret);
            NewReleasesList.getInstance().notifyItemChanged(mResult);
            return ret;
        }
    }



    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {


        // TODO REMOVE DEBUG
        static int count = 0;

        CoverImage mImageView;

        public DownloadImageTask(CoverImage imageView) {
            mImageView = imageView;
        }

        @Override
         protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap ret = null;
            Log.i("TAG", "Download number " + (++count));
            try {
                InputStream in = new java.net.URL(url).openStream();
                ret = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            final CoverImage view = mImageView;
            final Bitmap toSave = ret;
            mImageView.post(new Runnable() {
                @Override
                public void run() {
                    mImageView.saveCoverBitmapInResult(toSave);
                }
            });
            return ret;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mImageView.setImageBitmap(bitmap);
        }
    }


}
