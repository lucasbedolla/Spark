package cool.lucasbedolla.swish.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.chrisbanes.photoview.PhotoView;

import cool.lucasbedolla.swish.R;
import cool.lucasbedolla.swish.util.ImageHelper;

/**
 * Created by Lucas Bedolla on 2/3/2018.
 */


/**
 * CURRENTLY ONLY OFFERING THE ABILITY TO HAVE IMAGEVIEW INTERACTION
 */

public class InteractionFragment extends Fragment {

    public static final String RESOURCE_URL = "RESOURCE_URL";
    public static final String RESOURCE_TYPE = "RESOURCE_TYPE";
    public static final String RESOURCE_VIDEO = "VIDEO";
    public static final String RESOURCE_IMAGE = "IMAGE";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_interaction, container, false);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.getString(RESOURCE_URL) != null && bundle.getString(RESOURCE_TYPE) != null) {
            setUpResource(layout, bundle.getString(RESOURCE_URL), bundle.getString(RESOURCE_TYPE));
        } else {
            //failure!
            return null;
        }

        return layout;
    }

    /**
     * CURRENTLY ONLY OFFERING THE ABILITY TO HAVE IMAGEVIEW INTERACTION
     */

    private void setUpResource(View layout, String url, String type) {

        if (type.equals(RESOURCE_IMAGE)) {
            setUpImage(layout, url);
        } else if (type.equals(RESOURCE_VIDEO)) {

            // setUpVideo(layout, url);
        }
    }

    private void setUpImage(View layout, String url) {
        FrameLayout frame = layout.findViewById(R.id.frameLayout);
        PhotoView photoView = new PhotoView(frame.getContext());
        frame.addView(photoView);
        ImageHelper.downloadImageIntoImageView(photoView, url);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
