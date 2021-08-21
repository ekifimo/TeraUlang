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

import pemda.cirebon.teraulang.Model.PointGrafikUttp;
import pemda.cirebon.teraulang.R;

public class Grafik_Uttp extends Fragment {

    LineChart barChart;
    ArrayList<Entry> barEntryArrayList;
    ArrayList<String> labelsNames;
    /*ArrayList<DataPoint> dataPointArrayList = new ArrayList<>();*/
    ArrayList<PointGrafikUttp> dataPointArrayList = new ArrayList<>();
    Spinner thSpinner;
    String spinnerTahun;

    public static Grafik_Uttp newInstance() {
        return new Grafik_Uttp();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grafik__uttp, container, false);
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
        dbase.child("GrafikUttp").child(spinnerTahun).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        PointGrafikUttp dataPoint = dataSnapshot.getValue(PointGrafikUttp.class);
                        dataPointArrayList.add(new PointGrafikUttp(dataPoint.getJenisUTTP(), dataPoint.getCount()));
                    }
                    showChart(dataPointArrayList);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void showChart(ArrayList<PointGrafikUttp> dataPointArrayList) {
        barEntryArrayList = new ArrayList<>();
        labelsNames = new ArrayList<>();

        for (int i = 0; i < dataPointArrayList.size(); i++){
            String month = dataPointArrayList.get(i).getJenisUTTP();
            int count = dataPointArrayList.get(i).getCount();
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
}