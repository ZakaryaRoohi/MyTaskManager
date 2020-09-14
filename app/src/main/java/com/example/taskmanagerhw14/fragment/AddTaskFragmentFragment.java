package com.example.taskmanagerhw14.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


import com.example.taskmanagerhw14.R;

import com.example.taskmanagerhw14.Repository.TaskDBRoomRepository;
import com.example.taskmanagerhw14.Repository.UserDBRoomRepository;
import com.example.taskmanagerhw14.Utils.TaskState;
import com.example.taskmanagerhw14.model.Task;
import com.example.taskmanagerhw14.model.User;

import java.util.Date;


public class AddTaskFragmentFragment extends DialogFragment {

    public static final String ARG_USER_ID = "argUsername";
    public static final String DIALOG_FRAGMENT_TAG = "Dialog";
    public static final int REQUEST_CODE_DATE_PICKER = 0;
    public static final String BUNDLE_TASK_USERNAME = "bundleTaskUsername";
    private EditText mEditTextTaskTitle;
    private EditText mEditTextDescription;
    private RadioButton mRadioButtonDone;
    private RadioButton mRadioButtonDoing;
    private RadioButton mRadioButtonTodo;
    private Button mButtonDate;
    private Button mButtonSave;
    private Button mButtonDiscard;
    private Task mTask;
    private Callbacks mCallbacks;
    private Long mUserId;
    private TaskDBRoomRepository mTaskDBRoomRepository;
    private UserDBRoomRepository mUserDBRoomRepository;

    public AddTaskFragmentFragment() {
        // Required empty public constructor
    }

    public static AddTaskFragmentFragment newInstance(Long userId) {
        AddTaskFragmentFragment fragment = new AddTaskFragmentFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getLong(ARG_USER_ID);
        }
        mUserDBRoomRepository = UserDBRoomRepository.getInstance(getContext());
        User user = mUserDBRoomRepository.get(mUserId);
        mTask = new Task(user);
        mTaskDBRoomRepository = TaskDBRoomRepository.getInstance(getActivity());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_task_fragment, container, false);
        findViews(view);

        setListeners();
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

    private void setListeners() {
        mButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mTask.getTaskDate());
                datePickerFragment.setTargetFragment(AddTaskFragmentFragment.this, REQUEST_CODE_DATE_PICKER);
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
                    mTask.setUserId(mUserId);
                    mTaskDBRoomRepository.insert(mTask);

                    mCallbacks.updateTasksFragment(mTask.getTaskState(), mTask.getUserId().toString());
//                    Toast.makeText(getActivity(), "d:   "+mTask.toString(), Toast.LENGTH_SHORT).show();
                    getDialog().cancel();
//                    TasksFragment tasksFragment = (TasksFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//                    tasksFragment.updateUI();

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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(BUNDLE_TASK_USERNAME, mUserId);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof TaskDetailFragment.Callbacks)
            mCallbacks = (AddTaskFragmentFragment.Callbacks) context;
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


    public interface Callbacks {
        void updateTasksFragment(TaskState taskState, String username);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_DATE_PICKER) {
            Date userSelectedDate = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_USER_SELECTED_DATE);

            mTask.setTaskDate(userSelectedDate);
            mButtonDate.setText(mTask.getTaskDate().toString());

        }
    }
}