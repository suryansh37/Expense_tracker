package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.myapplication.databinding.HomepageActivityBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomepageActivity extends AppCompatActivity {
    Button button;
    TextView textview;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    DocumentReference UserRef= db.collection("User").document("Details");

    private AppBarConfiguration appBarConfiguration;
    private HomepageActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_activity);

        textview =findViewById(R.id.textView9);
        UserRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String Name= documentSnapshot.getString("Name");
                            textview.setText(Name);
                        }
                        else {
                            Toast.makeText(HomepageActivity.this, "Document does not exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HomepageActivity.this, "Error!", Toast.LENGTH_SHORT).show();
//                        Log.d(TAG,e.toString());
                    }
                });
        button=findViewById(R.id.button6);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });
        button=findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openExpensePage();
            }
        });
        button=findViewById(R.id.button7);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openIncomePage();
            }
        });
        button=findViewById(R.id.button8);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }


    public void openMainActivity(){
        Intent intent1 =new Intent(this, MainActivity.class);
        startActivity(intent1);
    }
    public void openExpensePage(){
        Intent intent =new Intent(this, ExpenseActivity.class);
        startActivity(intent);
    }
    public void openIncomePage(){
        Intent intent2 =new Intent(this, IncomeActivity.class);
        startActivity(intent2);
    }
}