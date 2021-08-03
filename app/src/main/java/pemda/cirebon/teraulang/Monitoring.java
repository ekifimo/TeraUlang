package pemda.cirebon.teraulang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pemda.cirebon.teraulang.Model.CalenderNotes;


public class Monitoring extends AppCompatActivity {

    ActionBar actionBar;
    CompactCalendarView compactCalendarView;
    TextView testing;
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM- yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);

        testing = findViewById(R.id.test);
        compactCalendarView = findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        DatabaseReference dbase;
        dbase = FirebaseDatabase.getInstance().getReference();
        dbase.child("Monitoring").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        CalenderNotes calenderNotes = dataSnapshot.getValue(CalenderNotes.class);

                        Event ev1 = new Event(Color.BLUE, calenderNotes.getUnixTimestamp(), "Testing");
                        compactCalendarView.addEvent(ev1);

                        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
                            @Override
                            public void onDayClick(Date dateClicked) {
                                Context context = getApplicationContext();

                                if (dateClicked.toString().equals(calenderNotes.getTanggalMonitoring())){
                                    Toast.makeText(context, "Testing Apps", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onMonthScroll(Date firstDayOfNewMonth) {
                                actionBar.setTitle(dateFormat.format(firstDayOfNewMonth));
                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
        finish();
    }
}