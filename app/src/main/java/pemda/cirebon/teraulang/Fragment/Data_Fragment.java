package pemda.cirebon.teraulang.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import pemda.cirebon.teraulang.Adapter.FirebaseAdapterRc;
import pemda.cirebon.teraulang.Model.TeraData;
import pemda.cirebon.teraulang.R;

public class Data_Fragment extends Fragment {

    RecyclerView rcTable;
    DatabaseReference dReference;
    FirebaseAdapterRc adapter;
    ArrayList<TeraData> tera;
    Spinner thSpinner;
    String spinnerTahun;
    TextView exportBtn;
    private static final int PERMISSION_REQUEST_CODE = 100;

    public static Data_Fragment newInstance() {
        return new Data_Fragment();
    }

        @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.activity_data_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState){
        exportBtn = view.findViewById(R.id.judul);

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
                tera.clear();
                fetchdata();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        exportBtn.setOnClickListener(v -> {
            if (checkPermission())
            {
                fetchingData();
            } else {
                reqPermissions();
            }
        });

        rcTable = view.findViewById(R.id.RcTable);
        rcTable.setHasFixedSize(true);
        rcTable.setLayoutManager(new LinearLayoutManager(getContext()));

        tera = new ArrayList<>();
        adapter = new FirebaseAdapterRc(getContext(), tera);
        rcTable.setAdapter(adapter);
    }

    private void fetchdata() {

        /*Add Database to Firestore*/

        CollectionReference dbase = FirebaseFirestore.getInstance().collection("InputTera");

        dbase.document("InputTera").collection(spinnerTahun).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list){
                                TeraData teraData = d.toObject(TeraData.class);
                                tera.add(teraData);
                                exportBtn.setVisibility(View.VISIBLE);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        /*Add Database to FirebaseDatabase*/

        /*dReference = FirebaseDatabase.getInstance().getReference("InputTera");
        dReference.child(spinnerTahun).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                    TeraData teraData = dataSnapshot.getValue(TeraData.class);
                    tera.add(teraData);
                    exportBtn.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });*/
    }

    private void fetchingData() {

        String[] res_data = getResources().getStringArray(R.array.PilihTahun);
        StringBuilder data = new StringBuilder();
        data.append("Tanggal Tera Ulang,Nama,No. Hp,Alamat, Kecamatan, Kelurahan, Timbangan, Kapasitas, Retribusi");
        for (int i=0; i<res_data.length; i++){

            data.append("\n").append(res_data[0]).append(",").append(res_data[1]).append(",")
                    .append(res_data[2]).append(",").append(res_data[3]).append(res_data[4]).append(",")
                    .append(res_data[5]).append(",").append(res_data[6]).append(res_data[7]).append(",")
                    .append(res_data[8]);
        }
        Createcsv(data);

    }

    private void Createcsv(StringBuilder data) {
        Calendar calendar = Calendar.getInstance();
        long time = calendar.getTimeInMillis();

        try{
            FileOutputStream out = requireContext().openFileOutput("CSV_Data_" + time + ".csv", Context.MODE_PRIVATE);
            out.write(data.toString().getBytes());
            out.close();

            Context context = getContext();
            final File newFile = new File(Environment.getExternalStorageDirectory(),"SimpleCSV");
            if (!newFile.exists()){
                newFile.mkdir();
            }

            assert context != null;
            File file = new File(context.getFilesDir(), "CSV_Data_"+time+".csv");

            Uri path = FileProvider.getUriForFile(context, "pemda.cirebon.teraulang",file);

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/csv");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Data");
            intent.putExtra(Intent.EXTRA_STREAM, path);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(intent,"Excel Data"));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void reqPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(getContext(), "Write External Storage permission allows us to save files. " +
                    "Please allow this permission in App Settings.", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
}