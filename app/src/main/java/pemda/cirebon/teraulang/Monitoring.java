package pemda.cirebon.teraulang;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skyhope.eventcalenderlibrary.CalenderEvent;
import com.skyhope.eventcalenderlibrary.model.Event;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import pemda.cirebon.teraulang.Adapter.AdapterMonitoring;
import pemda.cirebon.teraulang.Model.CalenderNotes;


public class Monitoring extends AppCompatActivity {

    CalenderEvent calenderEvent;
    ArrayList<CalenderNotes> notesArrayList;
    AdapterMonitoring adapterMonitoring;
    RecyclerView rcTable;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        backButton = findViewById(R.id.backbutton);

        backButton.setOnClickListener(v-> {
            Intent intent = new Intent(this, Dashboard.class);
            startActivity(intent);
        });

        calenderEvent = findViewById(R.id.calenderEvent);
        rcTable = findViewById(R.id.tabelDetail);
        rcTable.setHasFixedSize(true);
        rcTable.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        notesArrayList = new ArrayList<>();
        adapterMonitoring = new AdapterMonitoring(getApplicationContext(), notesArrayList);
        rcTable.setAdapter(adapterMonitoring);

        /*calenderEvent.initCalderItemClickCallback(dayContainerModel -> {
            Event event = new Event(dayContainerModel.getTimeInMillisecond(), "Event");
            calenderEvent.removeEvent(event);
        });*/

        /*Fungsi delete mark*/

            DatabaseReference dbase;
            dbase = FirebaseDatabase.getInstance().getReference();
            dbase.child("Monitoring").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.hasChildren()){
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            CalenderNotes calenderNotes = dataSnapshot.getValue(CalenderNotes.class);
                            Event event = new Event(calenderNotes.getUnixTimestamp(), "Event", Color.GREEN);
                            calenderEvent.addEvent(event);

                            calenderEvent.initCalderItemClickCallback(dayContainerModel -> {
                                notesArrayList.clear();
                                String tanggal = dayContainerModel.getDate();
                                if (dayContainerModel.isHaveEvent()){
                                    dayContainerModel.setEvent(event);
                                    dayContainerModel.setHaveEvent(true);
                                    dbase.child("Monitoring").child(tanggal).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                            if (snapshot.hasChildren()){
                                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                                    CalenderNotes calenderNotes1 = dataSnapshot1.getValue(CalenderNotes.class);
                                                    notesArrayList.add(calenderNotes1);
                                                }
                                                adapterMonitoring.notifyDataSetChanged();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                        }
                                    });
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

        /*fungsi tambah mark*/

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
        finish();
    }
}