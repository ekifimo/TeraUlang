package pemda.cirebon.teraulang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
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

public class Dashboard extends AppCompatActivity {

    LinearLayout rekamBtn, teraBtn, monitoringBtn;
    TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();

            rekamBtn = findViewById(R.id.relativeLayout);
            teraBtn = findViewById(R.id.relativeLayout2);
            monitoringBtn = findViewById(R.id.relativeLayout3);
            userName = findViewById(R.id.namapengguna);

            userName.setText(Prevelante.currentUserOnline.getNama());

            teraBtn.setOnClickListener(v ->{
                Intent intent = new Intent(Dashboard.this, ReportTera.class);
                startActivity(intent);
            });

            rekamBtn.setOnClickListener(v -> {
                Intent intent = new Intent(Dashboard.this, RekamData.class);
                startActivity(intent);
            });

            monitoringBtn.setOnClickListener(v -> {
                Intent intent = new Intent(Dashboard.this, Monitoring.class);
                startActivity(intent);
            });
        }
    }
}