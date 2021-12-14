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

import com.example.edmaths.ui.RegisterFragmentDirections;
import com.example.edmaths.R;
import com.example.edmaths.util.PreferenceUtil;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {
    EditText etFirstName, etSurname, etEmailAddress, etPassword;
    Button registerBtn;
    String etFirstNameInput1, etSurnameInput1, etEmailInput1, etPasswordInput1;

    private FirebaseAuth firebaseAuth;
    private PreferenceUtil preferenceUtil;

    private final String emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"; // regex

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View registerView = inflater.inflate(R.layout.fragment_register, container, false);
        etFirstName = registerView.findViewById(R.id.et_firstName);
        etSurname = registerView.findViewById(R.id.et_surname);
        etEmailAddress = registerView.findViewById(R.id.et_EmailAddress);
        etPassword = registerView.findViewById(R.id.et_Pass);
        registerBtn = registerView.findViewById(R.id.btnRegister);

        return registerView;
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
        registerBtn.setOnClickListener(view1 -> {
            etFirstNameInput1 = etFirstName.getText().toString();
            etSurnameInput1 = etSurname.getText().toString();
            etEmailInput1 = etEmailAddress.getText().toString();
            etPasswordInput1 = etPassword.getText().toString();

            if ((etFirstNameInput1.isEmpty()
                    || etSurnameInput1.isEmpty() || etEmailInput1.isEmpty()
                    || etPasswordInput1.isEmpty())) {
                Toast.makeText(getActivity(), "Fill in all fields!", Toast.LENGTH_SHORT).show();

            } else if ((!etEmailInput1.matches(emailRegex))) {
                Toast.makeText(getActivity(), "Email is not valid!", Toast.LENGTH_SHORT).show();
            } else {
                firebaseAuth.createUserWithEmailAndPassword(etEmailInput1, etPasswordInput1).addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        preferenceUtil.saveUsername(etFirstNameInput1);
                        navigateToMainScreen();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(requireContext(), "Registration failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void navigateToMainScreen() {
        NavDirections mainMenuAction = RegisterFragmentDirections.actionRegisterFragmentToMainMenuFragment();
        Navigation.findNavController(requireView()).navigate(mainMenuAction);
    }

}