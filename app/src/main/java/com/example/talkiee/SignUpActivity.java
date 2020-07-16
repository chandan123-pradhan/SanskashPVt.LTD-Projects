package com.example.talkiee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class SignUpActivity extends AppCompatActivity {
    private ImageView backButton;
    private FirebaseAuth mAuth;
    private Button signUpButton;
    private EditText email,password;
    private DatabaseReference RootRef;
    private ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mAuth = FirebaseAuth.getInstance();
        RootRef=FirebaseDatabase.getInstance().getReference();



       initializer();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToLoginActivity();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }
        });
    }
    private void initializer() {
        backButton=(ImageView)findViewById(R.id.back_button);
        email=(EditText)findViewById(R.id.sign_up_email);
        password=(EditText)findViewById(R.id.sign_up_password);
        loadingBar=new ProgressDialog(this);
        signUpButton=(Button)findViewById(R.id.signUp);
    }

    private void sendUserToLoginActivity() {
        Intent loginIntent=new Intent(SignUpActivity.this,LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

        private void CreateNewAccount() {

        String Email=email.getText().toString();
        String Password=password.getText().toString();

        if(TextUtils.isEmpty(Email))
            Toast.makeText(this, "Please Enter your Email", Toast.LENGTH_SHORT).show();
        if(TextUtils.isEmpty(Password))
            Toast.makeText(this, "Please Enter Email Password", Toast.LENGTH_SHORT).show();
        else{
            loadingBar.setTitle("Sign Up");
            loadingBar.setMessage("please wait while..");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        String deviceToken= FirebaseInstanceId.getInstance().getToken();
                        String CurrentUserID=mAuth.getCurrentUser().getUid();
                        RootRef.child("Users").child(CurrentUserID).setValue("");

                        RootRef.child("Users").child(CurrentUserID).child("device_token").setValue(deviceToken);
                        loadingBar.dismiss();
                        sendUserToMainActvity();
                        Toast.makeText(SignUpActivity.this,"Account Created Successfully...",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String message=task.getException().toString();
                        loadingBar.dismiss();
                        Toast.makeText(SignUpActivity.this,"Error"+message,Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

    }

    private void sendUserToMainActvity() {
        Intent mainIntent=new Intent(SignUpActivity.this,MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

}
