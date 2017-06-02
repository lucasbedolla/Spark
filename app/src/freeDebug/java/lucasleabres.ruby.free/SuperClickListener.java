package lucasleabres.ruby.free;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tumblr.jumblr.types.Photo;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.PhotoSize;
import com.tumblr.jumblr.types.Post;

import java.util.List;

import cool.lucasbedolla.ruby.R;

/**
 * Created by LUCASVENTURES on 8/21/2016.
 */
public class SuperClickListener {

    public static final String TAG = "VIDURL";

    //views are image views
    private int postNum;
    private Post post;
    //image urls
    private String url;

    private AppCompatActivity compat;

    SuperClickListener(View view, int number, Post post, AppCompatActivity compat) {

        postNum = number;
        this.post = post;
        this.compat = compat;

        //this extracts the particular image url
        if (postNum == 0) {

            /*
            is a video
             */

        } else if (postNum == 1) {

            //is single image
            PhotoPost photoSet = (PhotoPost) post;
            List<Photo> photos = photoSet.getPhotos();
            Photo one = photos.get(postNum - 1);
            PhotoSize size = one.getOriginalSize();
            //now set the url as the one mentioned in the onclick;
            url = size.getUrl();
            view.setOnClickListener(imageListener);
        }else{

            //is photoset
            PhotoPost photoSet = (PhotoPost) post;
            List<Photo> photos = photoSet.getPhotos();
            Photo one = photos.get(postNum - 1);
            PhotoSize size = one.getOriginalSize();
            //now set the url as the one mentioned in the onclick;
            url = size.getUrl();
            view.setOnClickListener(imageListener);
        }
    }

    View.OnClickListener imageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(url!=null){
                inflateImageViewer(url);
            }

        }
    };

    View.OnClickListener videoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(url!=null){
                inflateVideoViewer(url);
            }

        }
    };


    private void inflateImageViewer(String urly) {

        SwipeZoomView zoomView = new SwipeZoomView();
        Bundle bundle = new Bundle();
        bundle.putString("URL", urly);
        zoomView.setArguments(bundle);
        FragmentTransaction transaction = compat.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.viewer_placeholder, zoomView).commit();
    }

    private void inflateVideoViewer(String urly) {
        /*
            must create a video class that can be inflated into a fragment
            //currently using code to zoom into


                attempt:
                    1. create an html document with the embed code.
                    2. have browser read html document in layout.
         */



        SwipeZoomView zoomView = new SwipeZoomView();
        Bundle bundle = new Bundle();
        bundle.putString("URL", urly);
        zoomView.setArguments(bundle);
        FragmentTransaction transaction = compat.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.viewer_placeholder, zoomView).commit();
    }
}
