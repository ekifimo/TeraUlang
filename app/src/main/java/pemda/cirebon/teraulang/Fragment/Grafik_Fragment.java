package pemda.cirebon.teraulang.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pemda.cirebon.teraulang.Model.PointGrafikRetribusi;
import pemda.cirebon.teraulang.R;

public class Grafik_Fragment extends Fragment {

    LineChart barChart;
    ArrayList<Entry> barEntryArrayList;
    ArrayList<String> labelsNames;
    /*ArrayList<DataPoint> dataPointArrayList = new ArrayList<>();*/
    ArrayList<PointGrafikRetribusi> dataPointArrayList = new ArrayList<>();
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

        final int[] iSelect = {thSpinner.getSelectedItemPosition()};
            thSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (iSelect[0] == position){

                    } else {
                        spinnerTahun = parent.getItemAtPosition(position).toString();
                        retrieveData();
                        dataPointArrayList.clear();
                    }
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
                        PointGrafikRetribusi dataPoint = dataSnapshot.getValue(PointGrafikRetribusi.class);
                        dataPointArrayList.add(new PointGrafikRetribusi(dataPoint.getBulan(), dataPoint.getBiayaRetribusi()));
                    }
                    showChart(dataPointArrayList);
                } /*else {
                    barChart.clear();
                    barChart.invalidate();
                }*/
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void showChart(ArrayList<PointGrafikRetribusi> dataPointArrayList) {
        barEntryArrayList = new ArrayList<>();
        labelsNames = new ArrayList<>();

        for (int i = 0; i < dataPointArrayList.size(); i++){
            String month = dataPointArrayList.get(i).getBulan();
            int count = dataPointArrayList.get(i).getBiayaRetribusi();
            barEntryArrayList.add(new BarEntry(i,count));
            labelsNames.add(month);
        }

        LineDataSet barDataSet = new LineDataSet(barEntryArrayList, "Grafik Tera Ulang");
        barDataSet.setColor(Color.YELLOW);
        Description description = new Description();
        description.setText("Jumlah Tera Ulang");
        barChart.setDescription(description);
        LineData barData = new LineData(barDataSet);
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