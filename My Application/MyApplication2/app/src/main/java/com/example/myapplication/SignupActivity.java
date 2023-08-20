package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.myapplication.databinding.SignupActivityBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    EditText email,name,password,income;
    Button button;
    FirebaseAuth fAuth;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    DocumentReference UserRef= db.collection("User").document("Details");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.myapplication.databinding.SignupActivityBinding binding = SignupActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        email=findViewById(R.id.editTextTextEmailAddress2);
        button=findViewById(R.id.button4);
        name=findViewById(R.id.textView4);
        password=findViewById(R.id.editTextNumberPassword);
        income=findViewById(R.id.editTextNumber);
        fAuth= FirebaseAuth.getInstance();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email=email.getText().toString();
                String Password=password.getText().toString();
                if (TextUtils.isEmpty((Email))){
                    email.setError("Email is required");
                }
                if(!Email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                    Toast.makeText(SignupActivity.this, "Valid Email Address!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SignupActivity.this, "Invalid Email Address!", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(Password)){
                    password.setError("Password is required");
                }
                if(Password.length()<8){
                    password.setError(("Password must be greater than 8 numbers"));
                }
                fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignupActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            openLoginActivity();
                            saveData();
                        }
                        else{
                            Toast.makeText(SignupActivity.this, "Error!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });
    }

//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_signup2);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

    public void setAppBarConfiguration(AppBarConfiguration appBarConfiguration) {
        this.appBarConfiguration = appBarConfiguration;
    }
    public void openLoginActivity(){
        Intent intent =new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public void saveData(){
        String Name=name.getText().toString();
        String Email=email.getText().toString();
        String Password=password.getText().toString();
        String Income=income.getText().toString();

        Map<String, String> map = new HashMap<>();
        map.put("Name", Name);
        map.put("Email", Email);
        map.put("Password", Password);
        map.put("Income", Income);

        db.collection("User").document("Details").set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(SignupActivity.this, "Details saved", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignupActivity.this, "Error!", Toast.LENGTH_SHORT).show();

                    }
                });
    }


}