package pemda.cirebon.teraulang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import io.paperdb.Paper;
import pemda.cirebon.teraulang.Model.UserLogin;
import pemda.cirebon.teraulang.Prevelant.Prevelante;

public class Login extends AppCompatActivity {

    String parentDBName = "LoginUser";
    TextInputEditText username,password;
    Button regisBTN, loginBTN;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Paper.init(this);
        username = findViewById(R.id.username_edit);
        password = findViewById(R.id.password_edit);
        loginBTN = findViewById(R.id.masuk);
        checkBox = findViewById(R.id.IngatSaya);


        /*regisBTN = findViewById(R.id.daftar);*/

        loginBTN.setOnClickListener(v -> masuk());


        /*regisBTN.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });*/
    }

    private Boolean validateUser() {
        String user = Objects.requireNonNull(username.getEditableText()).toString();
        if (user.isEmpty()) {
            username.setError("NIP Tidak Boleh Kosong");
            return false;
        } else {
            username.setError(null);
            return true;
        }
    }

    private Boolean validatePass() {
        String pass = Objects.requireNonNull(password.getEditableText()).toString();
        if (pass.isEmpty()) {
            password.setError("Password Tidak Boleh Kosong");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    private void masuk() {

        if (!validateUser() | !validatePass()) {
            return;
        }
        AllowAccess();
    }

    private void AllowAccess()
    {

        String user = Objects.requireNonNull(username.getEditableText()).toString();
        String pass = Objects.requireNonNull(password.getEditableText()).toString();

        if (checkBox.isChecked())
        {
            Paper.book().write(Prevelante.UserKey, user);
            Paper.book().write(Prevelante.PassKey, pass);
        }

        DatabaseReference dReference;
        dReference = FirebaseDatabase.getInstance().getReference();
        dReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(parentDBName).child(user).exists()){
                    UserLogin userData = snapshot.child(parentDBName).child(user).getValue(UserLogin.class);

                    if (userData != null && userData.getNIP().equals(user)) {
                        if (userData.getPassword().equals(pass)) {
                            Toast.makeText(Login.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, Dashboard.class);
                            Prevelante.currentUserOnline = userData;
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, "Password Salah", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else{
                    Toast.makeText(Login.this, "NIP Tidak tersedia", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}