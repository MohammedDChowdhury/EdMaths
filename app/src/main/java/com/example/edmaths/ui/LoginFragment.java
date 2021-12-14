package com.example.edmaths.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.edmaths.ui.LoginFragmentDirections;
import com.example.edmaths.R;
import com.example.edmaths.util.PreferenceUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {
    private EditText etEmail, etPassword;
    private Button loginBtn, registerHereBtn;

    private final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"; // regex

    private FirebaseAuth firebaseAuth;
    private PreferenceUtil preferenceUtil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
        initFireBase();
    }

    private void initFireBase() {
        preferenceUtil = new PreferenceUtil(requireContext());
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void initViews() {
        etEmail = requireView().findViewById(R.id.et_Email);
        loginBtn = requireView().findViewById(R.id.materialButton);

        etPassword = requireView().findViewById(R.id.et_Password);
        registerHereBtn = requireView().findViewById(R.id.btnRegisterHere);

        registerHereBtn.setOnClickListener(v -> {
            NavDirections action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment();
            Navigation.findNavController(requireView()).navigate(action);
        });


        loginBtn.setOnClickListener(v -> {
            String etEmailInput = etEmail.getText().toString();
            String etPasswordInput = etPassword.getText().toString();

            if (etEmailInput.isEmpty() || etPasswordInput.isEmpty()) { // DB reading here possibly?
                Toast.makeText(getActivity(), "Fill in all fields!", Toast.LENGTH_SHORT).show();

            } else if ((!etEmailInput.matches(emailPattern))) {
                Toast.makeText(getActivity(), "Email is not valid!", Toast.LENGTH_SHORT).show();
            } else {
                firebaseAuth.signInWithEmailAndPassword(etEmailInput, etPasswordInput)
                        .addOnCompleteListener(requireActivity(), task -> {
                            if (task.isSuccessful()) {
                                navigateToMainScreen();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(requireContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void navigateToMainScreen() {
        NavDirections mainMeuAction = LoginFragmentDirections.actionLoginFragmentToMainMenuFragment();
        Navigation.findNavController(requireView()).navigate(mainMeuAction);
    }

 //   @Override
//    public void onResume() {
//        super.onResume();
//
//        if (firebaseAuth != null) {
//            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//
//            if (currentUser != null) {
//                navigateToMainScreen();
//            }
//        }
//    }
}