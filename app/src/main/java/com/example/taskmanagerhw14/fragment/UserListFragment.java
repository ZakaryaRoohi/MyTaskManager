package com.example.taskmanagerhw14.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taskmanagerhw14.R;
import com.example.taskmanagerhw14.Repository.UserRepository;
import com.example.taskmanagerhw14.Utils.UserType;
import com.example.taskmanagerhw14.model.User;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private UserRepository mUserRepository;
    private UserAdapter mAdapter;

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
        mUserRepository = UserRepository.getInstance();
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
        private ImageView mImageViewUserProfilePhoto;
        private TextView mTextViewUsername;
        private TextView mTextViewDateUserCreate;
        private ImageView mImageViewDeleteUser;
        private ImageView mImageViewUserRole;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            mImageViewUserProfilePhoto = itemView.findViewById(R.id.list_row_user_image);
            mTextViewUsername = itemView.findViewById(R.id.list_row_user_name);
            mTextViewDateUserCreate = itemView.findViewById(R.id.text_view_user_date_created);
            mImageViewDeleteUser = itemView.findViewById(R.id.image_view_delete_user);
            mImageViewUserRole = itemView.findViewById(R.id.image_view_user_role);


            mImageViewDeleteUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Are You sure to delete User?");

                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mUserRepository.deleteUser(mUser);
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
        }

        public void bindUser(User user) {
            mUser = user;
            mTextViewUsername.setText(mUser.getUsername());
            mTextViewDateUserCreate.setText(mUser.getUserDateCreated().toString());

            mImageViewUserRole.setImageResource(setUserTypeImage(mUser));
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

        public UserAdapter(List<User> users){
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
    public void updateUI(){
        List<User> users = mUserRepository.getList();

        if (mAdapter == null) {
            mAdapter = new UserAdapter(users);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setUsers(users);
            mAdapter.notifyDataSetChanged();
        }
    }



}