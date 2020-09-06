package com.example.taskmanagerhw13.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.taskmanagerhw13.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {
    private Button mButtonSignUp;
    private Button mButtonBack;
    private EditText mEditTextUsername;
    private EditText mEditTextPassword;
    private Callbacks mCallBacks;
    private UserRepository mUserRepository;
    public SignInFragment() {
        // Required empty public constructor
    }

    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        mUserRepository = UserRepository.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        findAllView(view);
        setListeners();
        return view;
    }

    private void findAllView(View view) {
        mButtonSignUp = view.findViewById(R.id.button_Sign_up);
        mButtonBack = view.findViewById(R.id.button_back);
        mEditTextPassword = view .findViewById(R.id.sign_in_edit_text_password);
        mEditTextUsername=view.findViewById(R.id.sign_in_edit_text_username);
    }

    private void setListeners() {
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBacks.onBackClicked();
            }
        });
        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mEditTextUsername.getText().toString();
                String password = mEditTextPassword.getText().toString();
                if(username.equals("")|password.equals(""))
                {
                    Toast.makeText(getActivity(), "please Username and Password.", Toast.LENGTH_SHORT).show();
                }else {
                    User user = new User(username, password, UserType.USER);
                    mUserRepository.addUser(user);
                    mCallBacks.onBackClicked();
                }
            }
        });
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Callbacks)
            mCallBacks = (Callbacks) context;
        else {
            throw new ClassCastException(context.toString()
                    + "you must implement onBackClicked");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallBacks = null;
    }

    public interface Callbacks {
        void onBackClicked();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }
}