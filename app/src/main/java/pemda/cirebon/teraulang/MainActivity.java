package pemda.cirebon.teraulang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import io.paperdb.Paper;
import pemda.cirebon.teraulang.Model.UserLogin;
import pemda.cirebon.teraulang.Prevelant.Prevelante;

public class MainActivity extends AppCompatActivity {

   /* ImageButton next;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        /*next = findViewById(R.id.Next);

        next.setOnClickListener(v->{
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        });*/

        Paper.init(this);
        String UserKey = Paper.book().read(Prevelante.UserKey);
        String PassKey = Paper.book().read(Prevelante.PassKey);

        if (UserKey != null && PassKey != null)
        {
            if (!TextUtils.isEmpty(UserKey) && !TextUtils.isEmpty(PassKey)){
                AllowAccess(UserKey, PassKey);
            }
        } else {
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }, 2000);
        }

    }

    private void AllowAccess(String user, String pass) {
        DatabaseReference dReference;
        dReference = FirebaseDatabase.getInstance().getReference();
        dReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("LoginUser").child(user).exists()){
                    UserLogin userData = snapshot.child("LoginUser").child(user).getValue(UserLogin.class);

                    if (userData != null && userData.getNIP().equals(user)) {
                        if (userData.getPassword().equals(pass)) {
                            Toast.makeText(MainActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, Dashboard.class);
                            Prevelante.currentUserOnline = userData;
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Password Salah", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "NIP Tidak tersedia", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}