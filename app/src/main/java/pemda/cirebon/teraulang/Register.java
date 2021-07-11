package pemda.cirebon.teraulang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    FirebaseAuth auth;

    TextInputLayout username, password, nama;
    Button loginBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        nama = findViewById(R.id.Nama);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBTN = findViewById(R.id.masuk);

        loginBTN.setOnClickListener(v -> CreateAccount());
    }

    private void CreateAccount()
    {
        String namaUser = nama.getEditText().getText().toString();
        String user = username.getEditText().getText().toString();
        String pass = password.getEditText().getText().toString();

        if(TextUtils.isEmpty(namaUser)){
            Toast.makeText(this, "Masukkan Nama", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(user)){
            Toast.makeText(this, "Masukkan NIP", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Masukkan password", Toast.LENGTH_SHORT).show();
        }

        validateNip(namaUser, user, pass);
    }

    private void validateNip(String namaUser, String user, String pass)
    {
        final DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(!snapshot.child("LoginUser").child(user).exists()){
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("Nama", namaUser);
                    userDataMap.put("NIP", user);
                    userDataMap.put("Password", pass);

                    reference.child("LoginUser").child(user).updateChildren(userDataMap)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()){
                                    Toast.makeText(Register.this, "Register berhasil", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Register.this, Login.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(Register.this, "Jaringan bermasalah", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else {
                    Toast.makeText(Register.this, "Nip ini sudah tersedia", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }
        });
    }
}