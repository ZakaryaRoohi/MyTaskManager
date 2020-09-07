package com.example.taskmanagerhw13.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskmanagerhw13.R;
import com.example.taskmanagerhw13.Repository.UserRepository;
import com.example.taskmanagerhw13.Utils.UserType;
import com.example.taskmanagerhw13.activity.MainActivity;
import com.example.taskmanagerhw13.activity.TaskPagerActivity;
import com.example.taskmanagerhw13.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private EditText mEditTextUsername;
    private EditText mEditTextPassword;
    private Button mButtonLogIn;
    private Button mButtonSignIn;
    private Callbacks mCallbacks;
    private UserRepository mUserRepository;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        findAllView(view);
        setListeners();

        return view;

    }

    private void findAllView(View view) {
        mEditTextUsername = view.findViewById(R.id.log_in_edit_text_username);
        mEditTextPassword = view.findViewById(R.id.log_in_edit_text_password);
        mButtonLogIn = view.findViewById(R.id.button_login);
        mButtonSignIn = view.findViewById(R.id.button_sign_in);
    }

    private void setListeners() {
        mButtonSignIn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbacks.onSinInClicked();
            }
        }));
        mButtonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mEditTextUsername.getText().toString();
                String password = mEditTextPassword.getText().toString();
                if (username.equals("") | password.equals("")) {
                    Toast.makeText(getActivity(), "please Username and Password.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = TaskPagerActivity.newIntent(getContext(),username);

                    if(mUserRepository.checkUserExist(username,password))
                    startActivity(intent);
                    else
                        Toast.makeText(getActivity(), "your username or password is wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Callbacks)
            mCallbacks = (Callbacks) context;
        else {
            throw new ClassCastException(context.toString()
                    + "you must Implement onLoginClicked");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    public interface Callbacks {
        void onLoginClicked();

        void onSinInClicked();
    }

    @Override
    public void onStart() {
        super.onStart();
        mEditTextUsername.setText("");
        mEditTextPassword.setText("");
    }
}