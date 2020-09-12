package com.example.taskmanagerhw14.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
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


public class AddTaskFragmentFragment extends DialogFragment {

    public static final String ARG_USERNAME = "argUsername";
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
    private String mUsername;
    private IRepository mTasksRepository;
    public AddTaskFragmentFragment() {
        // Required empty public constructor
    }

    public static AddTaskFragmentFragment newInstance(String username) {
        AddTaskFragmentFragment fragment = new AddTaskFragmentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUsername = getArguments().getString(ARG_USERNAME);
        }
        mTask = new Task(mUsername);
        mTasksRepository = TaskDBRepository.getInstance(getActivity());
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
                    mTask.setUser(mUsername);
                    mTasksRepository.insert(mTask);
                    mCallbacks.updateTasksFragment(mTask.getTaskState(), mTask.getUsername());
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
        outState.putSerializable(BUNDLE_TASK_USERNAME, mUsername);
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
}