package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.myapplication.databinding.LoginActivityBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {



    public AppBarConfiguration appBarConfiguration;

    EditText email,password;
    Button button;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        LoginActivityBinding binding = LoginActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        email=findViewById(R.id.editTextTextEmailAddress2);
        password=findViewById(R.id.editTextNumberPassword);
        button=findViewById(R.id.button3);
        fAuth= FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email=email.getText().toString();
                String Password=password.getText().toString();
                if (TextUtils.isEmpty((Email))){
                    email.setError("Email is required");
                    return;
                }
//                if(!Email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
//                    Toast.makeText(LoginActivity.this, "Valid Email Address!", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(LoginActivity.this, "Invalid Email Address!", Toast.LENGTH_SHORT).show();
//                }
                if(TextUtils.isEmpty(Password)){
                    password.setError("Password is required");
                    return;
                }
                if(Password.length()<8){
                    password.setError(("Password must be greater than 8 numbers"));
                    return;
                }
                fAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                            openHomepageActivity();
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Error!"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });
    }



//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_login);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
    public void openHomepageActivity(){
        Intent intent1 =new Intent(this, HomepageActivity.class);
        startActivity(intent1);
    }
}