package pemda.cirebon.teraulang;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.paperdb.Paper;
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

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.logo)
                .setTitle(R.string.app_name)
                .setMessage("Yakin ingin keluar?")
                .setPositiveButton("OK", (dialog, which) -> {
                    Paper.book().destroy();
                    Intent intent = new Intent(Dashboard.this, Login.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .show();
    }
}