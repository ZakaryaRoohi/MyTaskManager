package com.example.taskmanagerhw14.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taskmanagerhw14.R;
import com.example.taskmanagerhw14.Repository.UserDBRoomRepository;
import com.example.taskmanagerhw14.Repository.UserRepository;
import com.example.taskmanagerhw14.Utils.PictureUtils;
import com.example.taskmanagerhw14.Utils.UserType;
import com.example.taskmanagerhw14.model.User;

import java.io.File;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserListFragment extends Fragment {
    private static final String FILE_PROVIDER_AUTHORITY = "com.example.taskmanagerhw14t.fileProvider";
    private static final int REQUEST_CODE_IMAGE_CAPTURE = 1;
    public static final int REQUEST_CODE_IMAGE_VIEW_PICTURE = 3;
    public static final String DIALOG_TAG_IMAGE_VIEW_FRAGMENT = "dialogTagImageViewFragment";

    private RecyclerView mRecyclerView;
    private UserDBRoomRepository mUserDBRoomRepository;
    private UserAdapter mAdapter;
    private File tempPhotoUri;

    public UserListFragment() {
        // Required empty public constructor
    }

    public static UserListFragment newInstance() {
        UserListFragment fragment = new UserListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserDBRoomRepository = UserDBRoomRepository.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        findViews(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;

    }

    public void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_users);
    }

    public class UserHolder extends RecyclerView.ViewHolder {
        private User mUser;
        private File mPhotoFile;

        private ImageView mImageViewUserProfilePhoto;
        private TextView mTextViewUsername;
        private TextView mTextViewDateUserCreate;
        private ImageView mImageViewDeleteUser;
        private ImageView mImageViewUserRole;
        private ImageView mImageViewAddPicture;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            mImageViewUserProfilePhoto = itemView.findViewById(R.id.list_row_user_image);
            mTextViewUsername = itemView.findViewById(R.id.list_row_user_name);
            mTextViewDateUserCreate = itemView.findViewById(R.id.text_view_user_date_created);
            mImageViewDeleteUser = itemView.findViewById(R.id.image_view_delete_user);
            mImageViewUserRole = itemView.findViewById(R.id.image_view_user_role);
            mImageViewAddPicture = itemView.findViewById(R.id.image_view_edit_user_picture);

            mImageViewDeleteUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPhotoFile = mUserDBRoomRepository.getPhotoFile(getActivity(), mUser);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Are You sure to delete User?");

                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mUserDBRoomRepository.remove(mUser);
                            if (mPhotoFile.exists())
                                mPhotoFile.delete();
                            updateUI();
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
            mImageViewUserProfilePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPhotoFile = mUserDBRoomRepository.getPhotoFile(getActivity(), mUser);
                    if (mPhotoFile != null&& mPhotoFile.exists()) {
                        ImageViewFragment imageViewFragment = new ImageViewFragment().newInstance(mPhotoFile);
                        imageViewFragment.setTargetFragment(UserListFragment.this, REQUEST_CODE_IMAGE_VIEW_PICTURE);
                        imageViewFragment.show(getFragmentManager(), DIALOG_TAG_IMAGE_VIEW_FRAGMENT);
                    }
                }
            });
            mImageViewAddPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPhotoFile = mUserDBRoomRepository.getPhotoFile(getActivity(), mUser);

                    Intent userPictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (userPictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

                        Uri photoURI = FileProvider.getUriForFile(
                                getActivity(),
                                FILE_PROVIDER_AUTHORITY,
                                mPhotoFile);
                        grantTemPermissionForTakePicture(userPictureIntent, photoURI);
                        tempPhotoUri = mPhotoFile;
                        userPictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(userPictureIntent, REQUEST_CODE_IMAGE_CAPTURE);
                    }
                }
            });

        }

        private void grantTemPermissionForTakePicture(Intent takePictureIntent, Uri photoURI) {
            PackageManager packageManager = getActivity().getPackageManager();
            List<ResolveInfo> activities = packageManager.queryIntentActivities(
                    takePictureIntent,
                    PackageManager.MATCH_DEFAULT_ONLY);

            for (ResolveInfo activity : activities) {
                getActivity().grantUriPermission(activity.activityInfo.packageName,
                        photoURI,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        }

        private void updatePhotoView() {
            mPhotoFile = mUserDBRoomRepository.getPhotoFile(getActivity(), mUser);
            if (mPhotoFile == null || !mPhotoFile.exists()) {
                mImageViewUserProfilePhoto.setImageDrawable(getResources().getDrawable(R.drawable.ic_person));

            } else {
                Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());

                mImageViewUserProfilePhoto.setImageBitmap(bitmap);
            }
        }

        public void bindUser(User user) {
            mUser = user;
            mTextViewUsername.setText(mUser.getUserName());
            mTextViewDateUserCreate.setText(mUser.getDateCreated().toString());

            mImageViewUserRole.setImageResource(setUserTypeImage(mUser));
            updatePhotoView();
        }

        private int setUserTypeImage(User user) {
            if (user.getUserType() == UserType.USER)
                return R.drawable.ic_user;
            else
                return R.drawable.ic_admin;
        }

    }

    private class UserAdapter extends RecyclerView.Adapter<UserHolder> {

        private List<User> mUsers;

        public UserAdapter(List<User> users) {
            mUsers = users;
        }

        public void setUsers(List<User> users) {
            mUsers = users;
        }

        @NonNull
        @Override
        public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_row_user, parent, false);
            UserHolder userHolder = new UserHolder(view);
            return userHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull UserHolder holder, int position) {
            User user = mUsers.get(position);
            holder.bindUser(user);
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }
    }

    public void updateUI() {
        List<User> users = mUserDBRoomRepository.getList();

        if (mAdapter == null) {
            mAdapter = new UserAdapter(users);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setUsers(users);
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

}