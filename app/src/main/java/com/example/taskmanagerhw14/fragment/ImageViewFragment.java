package com.example.taskmanagerhw14.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.taskmanagerhw14.R;
import com.example.taskmanagerhw14.Utils.PictureUtils;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImageViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageViewFragment extends DialogFragment {

    public static final String ARG_IMAGE_FILE = "imageFile";
    private ImageView mImageViewPicture;
    private File mPhotoFile;
    private PhotoView mPhotoView;


    public ImageViewFragment() {
        // Required empty public constructor
    }
    public static ImageViewFragment newInstance(File imageFile) {
        ImageViewFragment fragment = new ImageViewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_IMAGE_FILE,imageFile);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPhotoFile = (File) getArguments().getSerializable(ARG_IMAGE_FILE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_image_view, container, false);

        findViews(view);

        Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
        mPhotoView.setImageBitmap(bitmap);


        return view;
    }
    private void findViews(View view){


        mPhotoView = (PhotoView) view.findViewById(R.id.photo_view);

    }
}