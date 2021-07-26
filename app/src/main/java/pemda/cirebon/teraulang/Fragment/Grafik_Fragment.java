package pemda.cirebon.teraulang.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pemda.cirebon.teraulang.Model.DataPoint;
import pemda.cirebon.teraulang.R;

public class Grafik_Fragment extends Fragment {

    HorizontalBarChart barChart;
    BarDataSet barDataSet;
    ArrayList<IBarDataSet> iBarDataSets = new ArrayList<>();
    BarData barData;
    Spinner thSpinner;
    String spinnerTahun;

    public static Grafik_Fragment newInstance() {
        return new Grafik_Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.activity_grafik_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        barChart = view.findViewById(R.id.mp_chart);

        String[] plhnThn = getResources().getStringArray(R.array.PilihTahun);
        List<String> dftrThn = Arrays.asList(plhnThn);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(), R.layout.custom_spinner, dftrThn);
        adapter2.setDropDownViewResource(R.layout.custom_dropdown);
        thSpinner = view.findViewById(R.id.PilihTahun);
        thSpinner.setAdapter(adapter2);

        thSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerTahun = parent.getItemAtPosition(position).toString();
                retrieveData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void retrieveData() {
        DatabaseReference dbase;
        dbase = FirebaseDatabase.getInstance().getReference("Grafik");
        dbase.child(spinnerTahun).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ArrayList<BarEntry> dataVals = new ArrayList<>();

                if (snapshot.hasChildren()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        DataPoint dataPoint = dataSnapshot.getValue(DataPoint.class);
                        dataVals.add(new BarEntry(dataPoint.getBulan(), dataPoint.getCount()));
                    }
                    showChart(dataVals);
                } else {
                    barChart.clear();
                    barChart.invalidate();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void showChart(ArrayList<BarEntry> dataVals) {
        barDataSet = new BarDataSet(dataVals, "Data Grafik");
        barDataSet.setValues(dataVals);
        barDataSet.setLabel("Grafik Tera Ulang");
        iBarDataSets.clear();
        iBarDataSets.add(barDataSet);
        barData = new BarData(iBarDataSets);
        barChart.clear();
        barChart.setData(barData);
        barChart.invalidate();
    }
}