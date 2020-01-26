package com.example.chatroom;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;
    private FirebaseAuth mAuth;
    String TAG = "MainActivity";
    Boolean check = false;
    ProgressBar pgBar;




    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = (EditText)findViewById(R.id.E_mail_text);
        etPassword = (EditText)findViewById(R.id.password_text);
        pgBar = (ProgressBar) findViewById(R.id.progress_log);

      final  Intent move_main_menu = new Intent(MainActivity.this, menu_activity.class);

        FirebaseApp.initializeApp(this);


       mAuth = FirebaseAuth.getInstance();


        //Sign In Button
        Button log_btnn = (Button) findViewById(R.id.signin_Button);
        log_btnn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String stEmail = etEmail.getText().toString();
                String stPassword = etPassword.getText().toString();


                signIn(stEmail, stPassword);
                startActivity(move_main_menu);


            }
        });


        //Register In Button
        Button register_Button = (Button) findViewById(R.id.register_Button);
        register_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String stEmail = etEmail.getText().toString();
                String stPassword = etPassword.getText().toString();


                createAccouunt(stEmail, stPassword);

            }
        });
    }

    public void  updateUI(FirebaseUser account){
        if(account != null){
            Toast.makeText(this,"U Signed In successfully",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"U Didnt signed in",Toast.LENGTH_LONG).show();
        }
    }

    public void createAccouunt(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });

    }

     public void signIn(String email, String password) {
         pgBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            check = true;
                            updateUI(user);
                            pgBar.setVisibility(View.GONE);

                        } else {

                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                            check = false;
                        }

                        // ...
                    }
                });


    }


}
