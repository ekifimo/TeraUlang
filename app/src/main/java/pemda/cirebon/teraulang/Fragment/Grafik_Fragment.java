package pemda.cirebon.teraulang.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
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
    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<String> labelsNames;
    ArrayList<DataPoint> dataPointArrayList = new ArrayList<>();
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
                if (snapshot.hasChildren()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        DataPoint dataPoint = dataSnapshot.getValue(DataPoint.class);
                        dataPointArrayList.add(new DataPoint(dataPoint.getBulan(), dataPoint.getCount()));
                    }
                    showChart(dataPointArrayList);
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

    private void showChart(ArrayList<DataPoint> dataPointArrayList) {
        barEntryArrayList = new ArrayList<>();
        labelsNames = new ArrayList<>();

        for (int i = 0; i < dataPointArrayList.size(); i++){
            String month = dataPointArrayList.get(i).getBulan();
            int count = dataPointArrayList.get(i).getCount();
            barEntryArrayList.add(new BarEntry(i,count));
            labelsNames.add(month);
        }

        BarDataSet barDataSet = new BarDataSet(barEntryArrayList, "Grafik Tera Ulang");
        barDataSet.setColor(Color.YELLOW);
        Description description = new Description();
        description.setText("Bulan");
        barChart.setDescription(description);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        /*Set Value xAxis value formatter*/
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsNames));
        /*Set posisi labelnya (nama bulan)*/
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labelsNames.size());
        xAxis.setLabelRotationAngle(270);
        barChart.animateY(2000);
        barChart.invalidate();
    }

    /*private void retrieveData() {
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
    }*/
}