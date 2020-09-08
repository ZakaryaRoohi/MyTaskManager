package com.example.taskmanagerhw14.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.taskmanagerhw14.R;
import com.example.taskmanagerhw14.Repository.IRepository;
import com.example.taskmanagerhw14.Repository.TaskDBRepository;
import com.example.taskmanagerhw14.Repository.TasksRepository;
import com.example.taskmanagerhw14.Utils.TaskState;
import com.example.taskmanagerhw14.model.Task;

import java.util.Date;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskDetailFragment extends DialogFragment {

    public static final String ARG_TASK_ID = "ARGTaskId";
    public static final String DIALOG_FRAGMENT_TAG = "Dialog";
    public static final int REQUEST_CODE_DATE_PICKER = 0;
    public static final String BUNDLE_TASK_ID = "bundleTaskId";

//    private TasksRepository mTasksRepository;
private IRepository mTasksRepository;

    private Task mTask;

    private EditText mEditTextTaskTitle;
    private EditText mEditTextDescription;
    private RadioButton mRadioButtonDone;
    private RadioButton mRadioButtonDoing;
    private RadioButton mRadioButtonTodo;
    private Button mButtonDate;
    private Button mButtonSave;
    private Button mButtonDiscard;
    private Callbacks mCallbacks;
    private UUID mTaskId;

    public TaskDetailFragment() {
        // Required empty public constructor
    }

    public static TaskDetailFragment newInstance() {
        TaskDetailFragment fragment = new TaskDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public static TaskDetailFragment newInstance(UUID taskId) {
        TaskDetailFragment fragment = new TaskDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_ID, taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            mTaskId = (UUID) savedInstanceState.getSerializable(BUNDLE_TASK_ID);
        }else{
            mTaskId = (UUID) getArguments().getSerializable(ARG_TASK_ID);
        }
        mTasksRepository = TaskDBRepository.getInstance(getActivity());
        mTask = (Task) mTasksRepository.get(mTaskId);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(BUNDLE_TASK_ID, mTaskId);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Callbacks)
            mCallbacks = (Callbacks) context;
        else {
            throw new ClassCastException(context.toString()
                    + "you must Implements onTaskUpdated");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);

        findViews(view);
        setListeners();
        initViews();

        return view;
    }

    private void findViews(View view) {
        mEditTextTaskTitle = view.findViewById(R.id.edit_text_task_title);
        mEditTextDescription = view.findViewById(R.id.edit_text_task_description);
        mButtonDate = view.findViewById(R.id.task_date);
        mButtonSave = view.findViewById(R.id.button_save);
        mButtonDiscard = view.findViewById(R.id.button_discard);
        mRadioButtonDone = view.findViewById(R.id.radio_button_done);
        mRadioButtonDoing = view.findViewById(R.id.radio_button_doing);
        mRadioButtonTodo = view.findViewById(R.id.radio_button_todo);
    }

    private void initViews() {
        mEditTextTaskTitle.setText(mTask.getTaskTitle());
        mEditTextDescription.setText(mTask.getTaskDescription());
        mButtonDate.setText(mTask.getTaskDate().toString());
        setTaskState(mTask.getTaskState());


    }

    private void setTaskState(TaskState taskState) {
        if (taskState != null) {
            switch (taskState) {
                case DONE:
                    mRadioButtonDone.setChecked(true);
                    break;
                case DOING:
                    mRadioButtonDoing.setChecked(true);
                    break;
                case TODO:
                    mRadioButtonTodo.setChecked(true);
                    break;

            }
        }
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
////        TasksFragment tasksFragment = (TasksFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
////
////        tasksFragment.updateUI();
//
//        mCallbacks.onTaskUpdated();
//    }

    private void setListeners() {
        mButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mTask.getTaskDate());
                datePickerFragment.setTargetFragment(TaskDetailFragment.this, REQUEST_CODE_DATE_PICKER);
                datePickerFragment.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
            }
        });
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTask.getTaskState() == null)
                    Toast.makeText(getActivity(), "please choose Task State", Toast.LENGTH_SHORT).show();
                else {
                    mTask.setTaskTitle(mEditTextTaskTitle.getText().toString());
                    mTask.setTaskDescription((mEditTextDescription.getText().toString()));
                    if (mTasksRepository.checkTaskExists(mTask))
                        Toast.makeText(getActivity(), "this Task Already exist!", Toast.LENGTH_SHORT).show();
                    else {
                        updateTask();
                        mCallbacks.updateTasksFragment(mTask.getTaskState(), mTask.getUsername());
                        getDialog().cancel();
//                    TasksFragment tasksFragment = (TasksFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//                    tasksFragment.updateUI();
                    }
                }
            }
        });
        mButtonDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });
        mRadioButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTask.setTaskState(TaskState.DONE);
            }
        });
        mRadioButtonDoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTask.setTaskState(TaskState.DOING);

            }
        });
        mRadioButtonTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTask.setTaskState(TaskState.TODO);

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_DATE_PICKER) {
            //get response from intent extra, which is user selected date
            Date userSelectedDate = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_USER_SELECTED_DATE);

            mTask.setTaskDate(userSelectedDate);
            mButtonDate.setText(mTask.getTaskDate().toString());

            updateTask();
        }

    }

    private void updateTask() {
        mTasksRepository.update(mTask);
//        mCallbacks.onTaskUpdated();

    }

    public interface Callbacks {
        void updateTasksFragment(TaskState taskState, String username);
    }
//    @Override
//    public void onStart()
//    {
//        super.onStart();
//        // lock screen;
//        DisplayTools.Orientation orientation = DisplayTools.getDisplatOrientation(getActivity());
//        getActivity().setRequestedOrientation(orientation == DisplayTools.Orientation.LANDSCAPE
//                ? ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
//                : ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
//    }
//    @Override
//    public void onStop()
//    {
//        super.onStop();
//        // unlock screen;
//        getActivity().setRequestedOrientation(getActivity().getResources().getConfiguration().orientation);
//    }

}