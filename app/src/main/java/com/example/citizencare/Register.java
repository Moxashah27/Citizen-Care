package com.example.citizencare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Register extends AppCompatActivity {
    private EditText editTextRegisterFirstName, editTextRegisterMiddleName, editTextRegisterLastName, editTextRegisterEmail, editTextRegisterMobile, editTextRegisterPwd, editTextRegisterConfirmPwd;
    private ProgressBar progressBar;
    private RadioGroup radioGroupRegisterGender;
    private RadioButton radioButtonRegisterGenderSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //getSupportActionBar().setTitle("Register");
        Toast.makeText(Register.this, "You can register now", Toast.LENGTH_LONG).show();

        progressBar = findViewById(R.id.progressBar);
        editTextRegisterFirstName = findViewById(R.id.editText_register_first_name);
        editTextRegisterMiddleName = findViewById(R.id.editText_register_middle_name);
        editTextRegisterLastName = findViewById(R.id.editText_register_last_name);
        editTextRegisterEmail = findViewById(R.id.editText_register_email);
        editTextRegisterMobile = findViewById(R.id.editText_register_mobile);
        editTextRegisterPwd = findViewById(R.id.editText_register_password);
        editTextRegisterConfirmPwd = findViewById(R.id.editText_register_confirm_password);

        radioGroupRegisterGender = findViewById(R.id.radio_group_register_gender);
        radioGroupRegisterGender.clearCheck();

        Button buttonRegister = findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(view -> {
            int selectedGenderId = radioGroupRegisterGender.getCheckedRadioButtonId();
            radioButtonRegisterGenderSelected = findViewById(selectedGenderId);

            //obtain the entered data
            String textFirstName = editTextRegisterFirstName.getText().toString();
            String textMiddleName = editTextRegisterMiddleName.getText().toString();
            String textLastName = editTextRegisterLastName.getText().toString();
            String textEmail = editTextRegisterEmail.getText().toString();
            String textMobile = editTextRegisterMobile.getText().toString();
            String textPwd = editTextRegisterPwd.getText().toString();
            String textConfirmPwd = editTextRegisterConfirmPwd.getText().toString();
            String textGender; //cant obtain value before verifying if any button was selected or not

            if (TextUtils.isEmpty(textFirstName)) {
                Toast.makeText(Register.this, "Please enter your first name", Toast.LENGTH_LONG).show();
                editTextRegisterFirstName.setError("First Name is required");
                editTextRegisterFirstName.requestFocus();
            } else if (TextUtils.isEmpty(textMiddleName)) {
                Toast.makeText(Register.this, "Please enter your middle name", Toast.LENGTH_LONG).show();
                editTextRegisterMiddleName.setError("Middle Name is required");
                editTextRegisterMiddleName.requestFocus();
            } else if (TextUtils.isEmpty(textLastName)) {
                Toast.makeText(Register.this, "Please enter your last name", Toast.LENGTH_LONG).show();
                editTextRegisterLastName.setError("Last Name is required");
                editTextRegisterLastName.requestFocus();
            } else if (TextUtils.isEmpty(textEmail)) {
                Toast.makeText(Register.this, "Please enter your email", Toast.LENGTH_LONG).show();
                editTextRegisterEmail.setError("Email is required");
                editTextRegisterEmail.requestFocus();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                Toast.makeText(Register.this, "Please re-enter your email", Toast.LENGTH_LONG).show();
                editTextRegisterEmail.setError("Valid email is required");
                editTextRegisterEmail.requestFocus();
            } else if (radioGroupRegisterGender.getCheckedRadioButtonId() == -1) {
                Toast.makeText(Register.this, "Please select your gender", Toast.LENGTH_LONG).show();
                radioButtonRegisterGenderSelected.setError("Gender is required");
                radioButtonRegisterGenderSelected.requestFocus();
            } else if (TextUtils.isEmpty(textMobile)) {
                Toast.makeText(Register.this, "Please enter your mobile number", Toast.LENGTH_LONG).show();
                editTextRegisterMobile.setError("Mobile Number is required");
                editTextRegisterMobile.requestFocus();
            } else if (textMobile.length() != 10) {
                Toast.makeText(Register.this, "Please re-enter your mobile number", Toast.LENGTH_LONG).show();
                editTextRegisterMobile.setError("Mobile Number should be of 10 digits");
                editTextRegisterMobile.requestFocus();
            } else if (TextUtils.isEmpty(textPwd)) {
                Toast.makeText(Register.this, "Please enter your password", Toast.LENGTH_LONG).show();
                editTextRegisterPwd.setError("Password is required");
                editTextRegisterPwd.requestFocus();
            } else if (textPwd.length() < 6) {
                Toast.makeText(Register.this, "Password should be at least 6 digits", Toast.LENGTH_LONG).show();
                editTextRegisterPwd.setError("Password too weak");
                editTextRegisterPwd.requestFocus();
            } else if (TextUtils.isEmpty(textConfirmPwd)) {
                Toast.makeText(Register.this, "Please confirm your password", Toast.LENGTH_LONG).show();
                editTextRegisterConfirmPwd.setError("Password Confirmation is required");
                editTextRegisterConfirmPwd.requestFocus();
            } else if (!textPwd.equals(textConfirmPwd)) {
                Toast.makeText(Register.this, "Please enter same password", Toast.LENGTH_LONG).show();
                editTextRegisterConfirmPwd.setError("Password Confirmation is required");
                editTextRegisterConfirmPwd.requestFocus();
                //clear the entered passwords
                editTextRegisterPwd.clearComposingText();
                editTextRegisterConfirmPwd.clearComposingText();
            } else {
                textGender = radioButtonRegisterGenderSelected.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                registerUser(textFirstName, textMiddleName, textLastName, textEmail, textGender, textMobile, textPwd);
            }
        });
    }

    //Register
    private void registerUser(String textFirstName, String textMiddleName, String textLastName, String textEmail, String textGender, String textMobile, String textPwd) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail, textPwd).addOnCompleteListener(Register.this, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(Register.this, "User registered successfully", Toast.LENGTH_LONG).show();
                FirebaseUser firebaseUser =auth.getCurrentUser();

                //Send verification email
                if (firebaseUser != null) {
                    firebaseUser.sendEmailVerification();
                }


            }
        });
    }
}