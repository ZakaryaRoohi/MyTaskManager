package com.example.taskmanagerhw14.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.taskmanagerhw14.R;
import com.example.taskmanagerhw14.Repository.TaskDBRoomRepository;
import com.example.taskmanagerhw14.Repository.UserDBRoomRepository;
import com.example.taskmanagerhw14.Utils.PictureUtils;
import com.example.taskmanagerhw14.Utils.TaskState;
import com.example.taskmanagerhw14.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;


public class TasksFragment<EndlessRecyclerViewScrollListener> extends Fragment {
    /**
     * get username and number of Tasks from Task activity
     */
    private static final String ARG_TASK_STATE = "ArgTaskState";
    public static final String TASK_DETAIL_FRAGMENT_DIALOG_TAG = "TaskDetailFragmentDialogTag";
    public static final String ADD_TASK_FRAGMENT_DIALOG_TAG = "AddTaskFragmentDialogTag";
    public static final int TASK_DETAIL_REQUEST_CODE = 101;
    public static final String ARG_USER_ID = "ArgsUsername";
    public static final String BUNDLE_USER_ID = "bundleUsername";
    public static final String BUNDLE_TASK_STATE = "BundleTaskState";

    public static final int REQUEST_CODE_IMAGE_VIEW_PICTURE = 3;
    public static final String DIALOG_TAG_IMAGE_VIEW_FRAGMENT = "dialogTagImageViewFragment";
    private static final String FILE_PROVIDER_AUTHORITY = "com.example.taskmanagerhw14t.fileProvider";

    private TaskDBRoomRepository mTaskDBRoomRepository;
    private RecyclerView mRecyclerView;
    private TaskAdapter mAdapter;
    private FloatingActionButton mFloatingActionButtonAdd;
    private TaskState mTaskState;
    private LinearLayout mLinearLayout1;
    private LinearLayout mLinearLayout2;
    private Callbacks mCallbacks;
    private String mUsername;
    private File tempPhotoUri;
    private long mUserId;
    private UserDBRoomRepository mUserDBRoomRepository;

    public TaskAdapter getAdapter() {
        return mAdapter;
    }

    public TasksFragment() {
        // Required empty public constructor
    }

    public static TasksFragment newInstance(TaskState taskState, long userId) {

        TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_STATE, taskState);
        args.putLong(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskDBRoomRepository = TaskDBRoomRepository.getInstance(getActivity());
        mUserDBRoomRepository = UserDBRoomRepository.getInstance(getActivity());
        mUserId = getArguments().getLong(ARG_USER_ID);
        mUsername = mUserDBRoomRepository.get(mUserId).getUserName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mUserId = savedInstanceState.getLong(BUNDLE_USER_ID);
            mTaskState = (TaskState) savedInstanceState.getSerializable(BUNDLE_TASK_STATE);

        } else {
            mUserId = getArguments().getLong(ARG_USER_ID);
            mTaskState = (TaskState) getArguments().getSerializable(ARG_TASK_STATE);
        }
//        mTasksRepository = TasksRepository.getInstance();
        mTaskDBRoomRepository = TaskDBRoomRepository.getInstance(getActivity());

        mUserDBRoomRepository = UserDBRoomRepository.getInstance(getActivity());
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        findViews(view);
        setClickListener();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        switch (getResources().getConfiguration().orientation) {
            case 1:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                break;
            case 2:
                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                break;
        }

        updateUI();
        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_tasks);
        mLinearLayout2 = view.findViewById(R.id.layout2);
        mLinearLayout1 = view.findViewById(R.id.layout1);
        mFloatingActionButtonAdd = view.findViewById(R.id.floating_action_button_add);
    }

    private void setClickListener() {
        mFloatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddTaskFragmentFragment addTaskFragmentFragment = AddTaskFragmentFragment.newInstance(mUserId);
                addTaskFragmentFragment.show(getActivity().getSupportFragmentManager(), ADD_TASK_FRAGMENT_DIALOG_TAG);
                updateUI();
            }
        });

    }


    public interface Callbacks {
        void onImageClicked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Callbacks)
            mCallbacks = (Callbacks) context;
        else {
            throw new ClassCastException(context.toString() + "must implement on Image Clicked");
        }

    }
    //

    private class TaskHolder extends RecyclerView.ViewHolder {
        private static final String FILE_PROVIDER_AUTHORITY = "com.example.taskmanagerhw14t.fileProvider";
        private static final int REQUEST_CODE_IMAGE_CAPTURE = 1;


        private TextView mTextViewTaskTittle;
        private ImageView mImageViewTaskPicture;
        private TextView mTextViewTaskDate;
        private ImageView mImageViewShareTask;
        private ImageView mImageViewDeleteTask;
        private TextView mTextViewUser;
        private ImageView mImageViewEditPicture;


        private Task mTask;
        private File mPhotoFile;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewTaskTittle = itemView.findViewById(R.id.list_row_task_title);
            mImageViewTaskPicture = itemView.findViewById(R.id.list_row_Task_image);
            mTextViewTaskDate = itemView.findViewById(R.id.text_view_task_date);
            mImageViewShareTask = itemView.findViewById(R.id.image_view_share);
            mImageViewDeleteTask = itemView.findViewById(R.id.image_view_delete_task);
            mTextViewUser = itemView.findViewById(R.id.text_view_task_user);
            mImageViewEditPicture = itemView.findViewById(R.id.image_view_edit_picture);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TaskDetailFragment taskDetailFragment = TaskDetailFragment.newInstance(mTask.getId());
                    taskDetailFragment.setTargetFragment(TasksFragment.this, TASK_DETAIL_REQUEST_CODE);
                    taskDetailFragment.show(getFragmentManager(), TASK_DETAIL_FRAGMENT_DIALOG_TAG);


                }
            });
            mImageViewEditPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPhotoFile = mTaskDBRoomRepository.getPhotoFile(getActivity(), mTask);

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

                        Uri photoURI = FileProvider.getUriForFile(
                                getActivity(),
                                FILE_PROVIDER_AUTHORITY,
                                mPhotoFile);
                        grantTemPermissionForTakePicture(takePictureIntent, photoURI);
                        tempPhotoUri = mPhotoFile;
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_CODE_IMAGE_CAPTURE);
                    }
                }
            });
            mImageViewTaskPicture.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mPhotoFile = mTaskDBRoomRepository.getPhotoFile(getActivity(), mTask);
                    if (mPhotoFile != null&&mPhotoFile.exists()) {
                        ImageViewFragment imageViewFragment = new ImageViewFragment().newInstance(mPhotoFile);
                        imageViewFragment.setTargetFragment(TasksFragment.this, REQUEST_CODE_IMAGE_VIEW_PICTURE);
                        imageViewFragment.show(getFragmentManager(), DIALOG_TAG_IMAGE_VIEW_FRAGMENT);
                    }
                    mCallbacks.onImageClicked();
                }

            });


            mImageViewShareTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, getReportText());
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Task Report");
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    if (sendIntent.resolveActivity(getActivity().getPackageManager()) != null)
                        startActivity(shareIntent);

                }
            });
            mImageViewDeleteTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPhotoFile = mTaskDBRoomRepository.getPhotoFile(getActivity(), mTask);
                    if(mPhotoFile.exists())
                        mPhotoFile.delete();
                    mTaskDBRoomRepository.remove(mTask);
                    updateUI();
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
            mPhotoFile = mTaskDBRoomRepository.getPhotoFile(getActivity(), mTask);
            if (mPhotoFile == null || !mPhotoFile.exists()) {
                mImageViewTaskPicture.setImageDrawable(getResources().getDrawable(R.drawable.ic_person));

            } else {
                Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());

                mImageViewTaskPicture.setImageBitmap(bitmap);
            }
        }

        private String getReportText() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String dateString = simpleDateFormat.format(mTask.getTaskDate());


            String titleString = mTask.getTaskTitle() == null ?
                    "there is no Title" :
                    "Task Title: " + mTask.getTaskTitle();
            String descriptionString = mTask.getTaskDescription() == null ?
                    " there is no description" :
                    " Task description: " + mTask.getTaskTitle();

            String report = (titleString +
                    descriptionString +
                    " state: " + mTask.getTaskState() +
                    " user: " + mUsername +
                    "  date: " + dateString
            );

            return report;
        }

        public void bindTask(Task task) {
            mTask = task;
            if (getAdapterPosition() % 2 == 1)
                itemView.setBackgroundColor(Color.LTGRAY);
            else
                itemView.setBackgroundColor(Color.WHITE);

            mTextViewTaskTittle.setText(task.getTaskTitle());

            mTextViewTaskDate.setText(task.getTaskDate().toString());
            mTextViewUser.setText(mUserDBRoomRepository.get(task.getUserId()).getUserName());
            updatePhotoView();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        else if (requestCode == REQUEST_CODE_IMAGE_VIEW_PICTURE) {
//            updatePhotoView();
            Uri photoUri = FileProvider.getUriForFile(
                    getActivity(),
                    FILE_PROVIDER_AUTHORITY,
                    tempPhotoUri);
            getActivity().revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        private List<Task> mTasks;

        public void setTasks(List<Task> tasks) {
            mTasks = tasks;
        }

        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_row_task, parent, false);

            TaskHolder taskHolder = new TaskHolder(view);

            return taskHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.bindTask(task);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }

    public void updateUI() {

        List<Task> tasks;
        mUserDBRoomRepository = UserDBRoomRepository.getInstance(getActivity());
        switch (mUserDBRoomRepository.get(mUserId).getUserType()) {
            case USER:
                tasks = mTaskDBRoomRepository.getUserTaskListByState(mTaskState, mUserId);
                adapter(tasks);
                break;
            case ADMIN:
                tasks = mTaskDBRoomRepository.getTaskListByState(mTaskState);
                adapter(tasks);
        }
    }


    private void adapter(List<Task> tasks) {
        if (mAdapter == null) {
            mAdapter = new TaskAdapter(tasks);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setTasks(tasks);
            mAdapter.notifyDataSetChanged();

        }
        if (tasks.size() == 0) {
            mLinearLayout1.setVisibility(View.GONE);
            mLinearLayout2.setVisibility(View.VISIBLE);
        } else {
            mLinearLayout1.setVisibility(View.VISIBLE);
            mLinearLayout2.setVisibility(View.GONE);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(BUNDLE_USER_ID, mUserId);
        outState.putSerializable(BUNDLE_TASK_STATE, mTaskState);
    }

}
