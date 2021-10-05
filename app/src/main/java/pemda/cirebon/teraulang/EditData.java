package pemda.cirebon.teraulang;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import pemda.cirebon.teraulang.Model.TeraData;

public class EditData extends AppCompatActivity {

    TextInputEditText namaPemilik, noHp, kapasitas, anakTimbangan, biaya, quantity;
    String saveCurrentDate, saveCurrentTime;
    String dataID = "";
    String tahunID = "";
    String jenisUTTP = "";
    TextView teraUlangAwal, teraUlangBrkt, emptyText, emptyBulan, emptyTimeMillis, emptyTimeFormat, emptyTimeMili;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat simpleDateFormat, simpleDateFormat2, simpleDateFormat3, simpleDateFormat4;
    ArrayList<String> arrayList_parent;
    ArrayList<String> arrayList_Harjamukti, arrayList_Kejaksan, arrayList_Kesambi, arrayList_Lemahwungkuk, arrayList_Pekalipan, arrayList_default ;
    ArrayAdapter<String> arrayAdapter_parent;
    ArrayAdapter<String> arrayAdapter_child;
    Button submitBtn;
    AutoCompleteTextView atAlamat;
    ImageButton imageButton, backButton;
    Spinner jtSpinner, kecamatanSpinner, kelurahanSpinner;
    MultiLineRadioGroup multiLineRadioGroup;
    String errorMessage = "Pilihan tidak boleh kosong";

    @SuppressLint({"WrongViewCast", "CutPasteId", "SimpleDateFormat"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        dataID = getIntent().getStringExtra("pid");
        tahunID = getIntent().getStringExtra("tahun");
        jenisUTTP = getIntent().getStringExtra("jenisUttp");
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        simpleDateFormat2 =  new SimpleDateFormat("yyyy");
        simpleDateFormat3 =  new SimpleDateFormat("MMM");
        simpleDateFormat4 = new SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH);

        multiLineRadioGroup = findViewById(R.id.main_activity_multi_line_radio_group);
        namaPemilik = findViewById(R.id.NamaPemilik);
        kecamatanSpinner = findViewById(R.id.SpinnerKecamatan);
        kelurahanSpinner = findViewById(R.id.SpinnerKelurahan);
        noHp = findViewById(R.id.NoHp);
        kapasitas = findViewById(R.id.Kapasitas);
        anakTimbangan = findViewById(R.id.AnakTimbangan);
        biaya = findViewById(R.id.Biaya);
        submitBtn = findViewById(R.id.masuk);
        atAlamat = findViewById(R.id.dropdown_alamat);
        jtSpinner = findViewById(R.id.SpinnerTimbangan);
        emptyText = findViewById(R.id.invisble);
        emptyBulan = findViewById(R.id.invisble2);
        emptyTimeMillis = findViewById(R.id.invisble3);
        emptyTimeFormat = findViewById(R.id.invisble4);
        emptyTimeMili = findViewById(R.id.invisble5);
        backButton = findViewById(R.id.backbutton);
        quantity = findViewById(R.id.Quantity);

        teraUlangAwal = findViewById(R.id.teraulangawal);
        teraUlangBrkt = findViewById(R.id.teraulangbrk);
        imageButton = findViewById(R.id.DateButton);

        imageButton.setOnClickListener(v -> {
            if (jtSpinner.getSelectedItem().toString().equals("Pilih Jenis UTTP")){
                setErrorJenisTimbangan(errorMessage);
            } else {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                showdatedialog();
            }

        });

        int layoutItemId = android.R.layout.simple_dropdown_item_1line;
        String[] alamatt = getResources().getStringArray(R.array.Alamat);
        List<String> daftaralamat = Arrays.asList(alamatt);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, layoutItemId, daftaralamat);
        atAlamat = findViewById(R.id.dropdown_alamat);
        atAlamat.setAdapter(adapter);

        String[] jnstmbngn = getResources().getStringArray(R.array.JenisTimbangan);
        List<String> dftrtmbngn = Arrays.asList(jnstmbngn);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.custom_spinner, dftrtmbngn);
        adapter2.setDropDownViewResource(R.layout.custom_dropdown);
        jtSpinner = findViewById(R.id.SpinnerTimbangan);
        jtSpinner.setAdapter(adapter2);

        arrayList_parent = new ArrayList<>();
        arrayList_parent.add("Pilih Kecamatan");
        arrayList_parent.add("Harjamukti");
        arrayList_parent.add("Kejaksan");
        arrayList_parent.add("Kesambi");
        arrayList_parent.add("Lemahwungkuk");
        arrayList_parent.add("Pekalipan");

        arrayAdapter_parent = new ArrayAdapter<>(this, R.layout.custom_spinner, arrayList_parent);
        arrayAdapter_parent.setDropDownViewResource(R.layout.custom_dropdown);
        kecamatanSpinner.setAdapter(arrayAdapter_parent);

        arrayList_Harjamukti = new ArrayList<>();
        arrayList_Harjamukti.add("Argasunya");
        arrayList_Harjamukti.add("Harjamukti");
        arrayList_Harjamukti.add("Kalijaga");
        arrayList_Harjamukti.add("Kecapi");
        arrayList_Harjamukti.add("Larangan");

        arrayList_Kejaksan = new ArrayList<>();
        arrayList_Kejaksan.add("KebonBaru");
        arrayList_Kejaksan.add("Kejaksan");
        arrayList_Kejaksan.add("Kesenden");
        arrayList_Kejaksan.add("Sukapura");

        arrayList_Kesambi = new ArrayList<>();
        arrayList_Kesambi.add("Drajat");
        arrayList_Kesambi.add("Karyamulya");
        arrayList_Kesambi.add("Kesambi");
        arrayList_Kesambi.add("Pekiringan");
        arrayList_Kesambi.add("Sunyaragi");

        arrayList_Lemahwungkuk = new ArrayList<>();
        arrayList_Lemahwungkuk.add("Kesepuhan");
        arrayList_Lemahwungkuk.add("Lemahwungkuk");
        arrayList_Lemahwungkuk.add("Panjunan");
        arrayList_Lemahwungkuk.add("Pegambiran");

        arrayList_Pekalipan = new ArrayList<>();
        arrayList_Pekalipan.add("Jagasatru");
        arrayList_Pekalipan.add("Pekalangan");
        arrayList_Pekalipan.add("Pekalipan");
        arrayList_Pekalipan.add("Pulasaren");

        arrayList_default = new ArrayList<>();
        arrayList_default.add("Pilih Kelurahan");

        kecamatanSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), R.layout.custom_spinner, arrayList_default);
                }
                if (position==1){
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), R.layout.custom_spinner, arrayList_Harjamukti);
                    arrayAdapter_child.setDropDownViewResource(R.layout.custom_dropdown);
                }
                if (position==2){
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), R.layout.custom_spinner, arrayList_Kejaksan);
                    arrayAdapter_child.setDropDownViewResource(R.layout.custom_dropdown);
                }
                if (position==3){
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), R.layout.custom_spinner, arrayList_Kesambi);
                    arrayAdapter_child.setDropDownViewResource(R.layout.custom_dropdown);
                }
                if (position==4){
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), R.layout.custom_spinner, arrayList_Lemahwungkuk);
                    arrayAdapter_child.setDropDownViewResource(R.layout.custom_dropdown);
                }
                if (position==5){
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), R.layout.custom_spinner, arrayList_Pekalipan);
                    arrayAdapter_child.setDropDownViewResource(R.layout.custom_dropdown);
                }

                kelurahanSpinner.setAdapter(arrayAdapter_child);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getData(dataID, tahunID);

        backButton.setOnClickListener(v-> cancelData(jenisUTTP));

        submitBtn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(namaPemilik.getText().toString())){
                ValidasiNama(errorMessage);
            } else if (TextUtils.isEmpty(atAlamat.getText().toString())){
                ValidasiAlamat(errorMessage);
            } else if (kecamatanSpinner.getSelectedItem().toString().equals("Pilih Kecamatan")){
                ValidasiKecamatan(errorMessage);
            } else if (TextUtils.isEmpty(quantity.getText().toString())){
                ValidasiQuantity(errorMessage);
            } else if (TextUtils.isEmpty(biaya.getText().toString())){
                ValidasiBiaya(errorMessage);
            } else if (jtSpinner.getSelectedItem().toString().equals("Pilih Jenis UTTP")){
                setErrorJenisTimbangan(errorMessage);
            }
            else {
                Input(dataID);
            }
        });

    }

    private void ValidasiBiaya(String errorMessage) {
        if (errorMessage != null){
            biaya.setError("Masukkan Jumlah Biaya");
        } else {
            biaya.setError(null);
        }
    }

    private void ValidasiQuantity(String errorMessage) {
        if (errorMessage != null){
            quantity.setError("Masukkan Quantitas");
        } else {
            quantity.setError(null);
        }
    }

    private void ValidasiKecamatan(String errorMessage) {
        View view = kecamatanSpinner.getSelectedView();

        TextView tvListItem = (TextView) view;

        if (errorMessage != null){
            tvListItem.setError("Masukkan Kecamatan");
        } else {
            tvListItem.setError(null);
        }
    }

    private void ValidasiAlamat(String errorMessage) {
        if (errorMessage != null){
            atAlamat.setError("Masukkan Alamat");
        } else {
            namaPemilik.setError(null);
        }
    }

    private void ValidasiNama(String errorMessage) {
        if (errorMessage != null){
            namaPemilik.setError("Masukkan Nama");
        } else {
            namaPemilik.setError(null);
        }
    }

    private void setErrorJenisTimbangan(String errorMessage) {
        View view = jtSpinner.getSelectedView();

        TextView tvListItem = (TextView) view;
        TextView tvInvisible = findViewById(R.id.tvInvisibleError2);

        if (errorMessage != null){
            tvListItem.setError(errorMessage);
            tvListItem.requestFocus();
            tvInvisible.requestFocus();
            tvInvisible.setError(errorMessage);
        } else {
            tvInvisible.setError(null);
            tvListItem.setError(null);
        }
    }

    private void cancelData(String jenisUTTP) {
        int biayaInput = Integer.parseInt(biaya.getEditableText().toString());
        String teksKosong = emptyText.getText().toString();
        String saveBulan = emptyBulan.getText().toString();

        new AlertDialog.Builder(this)
                .setTitle("Batalkan Perubahan")
                .setMessage("Yakin ingin membatalkan Perubahan ?")
                .setPositiveButton("Ya", ((dialog, which) -> {
                    DatabaseReference dbase6 = FirebaseDatabase.getInstance().getReference().child("Grafik")
                            .child("GrafikUttp").child(teksKosong).child(jenisUTTP);

                    dbase6.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            dbase6.runTransaction(new Transaction.Handler() {
                                @NonNull
                                @Override
                                public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                                    HashMap<String, Object> tempdata = (HashMap<String, Object>) currentData.getValue();
                                    if (tempdata == null) {
                                        return Transaction.success(currentData);
                                    }
                                    long newCount = (Long) tempdata.get("Count") + 1;
                                    tempdata.put("Count", newCount);
                                    currentData.setValue(tempdata);
                                    return Transaction.success(currentData);
                                }

                                @Override
                                public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    /*Grafik Retribusi*/

                    DatabaseReference dbase3 = FirebaseDatabase.getInstance().getReference().child("Grafik").child(teksKosong).child(saveBulan);
                    dbase3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                            dbase3.runTransaction(new Transaction.Handler() {
                                @NonNull
                                @NotNull
                                @Override
                                public Transaction.Result doTransaction(@NonNull @NotNull MutableData currentData) {
                                    HashMap<String, Object> tempdata = (HashMap<String, Object>) currentData.getValue();
                                    if (tempdata == null) {
                                        return Transaction.success(currentData);
                                    }
                                    long newCount = (Long) tempdata.get("BiayaRetribusi") + biayaInput;
                                    tempdata.put("BiayaRetribusi", newCount);
                                    currentData.setValue(tempdata);
                                    return Transaction.success(currentData);
                                }

                                @Override
                                public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, boolean committed, @Nullable @org.jetbrains.annotations.Nullable DataSnapshot currentData) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                    Intent intent = new Intent(this, Dashboard.class);
                    startActivity(intent);
                }))
                .setNegativeButton("Tidak", (dialog, which) -> dialog.cancel())
                .show();

    }

    private void getData(String dataID, String tahunID) {

        String[] jnstmbngn = getResources().getStringArray(R.array.JenisTimbangan);
        List<String> dftrtmbngn = Arrays.asList(jnstmbngn);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.custom_spinner, dftrtmbngn);
        adapter2.setDropDownViewResource(R.layout.custom_dropdown);
        jtSpinner = findViewById(R.id.SpinnerTimbangan);
        jtSpinner.setAdapter(adapter2);
        DatabaseReference dataReference = FirebaseDatabase.getInstance().getReference().child("InputTera").child(tahunID);

        dataReference.child(dataID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    TeraData teraData = snapshot.getValue(TeraData.class);

                    namaPemilik.setText(Objects.requireNonNull(teraData).getNama());
                    noHp.setText(Objects.requireNonNull(teraData).getNoHp());
                    atAlamat.setText(Objects.requireNonNull(teraData).getAlamat());
                    anakTimbangan.setText(teraData.getAnakTimbangan());
                    quantity.setText(teraData.getQuantity());
                    kapasitas.setText(teraData.getKapasitas());
                    biaya.setText(String.valueOf(teraData.getBiaya()));
                    teraUlangAwal.setText(teraData.getTanggalTeraUlangAwal());
                    teraUlangBrkt.setText(teraData.getTanggalTeraUlangBerikutnya());
                    emptyText.setText(teraData.getTanggalDropdown());
                    emptyBulan.setText(teraData.getBulan());
                    emptyTimeFormat.setText(teraData.getTanggalMonitoring());
                    emptyTimeMillis.setText(String.valueOf(teraData.getUnixTimestamp()));

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private void showdatedialog() {

        Calendar calender = Calendar.getInstance();

        String Timbangan = jtSpinner.getSelectedItem().toString();

        datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, month, dayOfMonth);
            teraUlangAwal.setText(simpleDateFormat.format(newDate.getTime()));
            emptyText.setText(simpleDateFormat2.format(newDate.getTime()));
            emptyBulan.setText(simpleDateFormat3.format(newDate.getTime()));
            /*long timeStamp = newDate.getTimeInMillis();
            String ts = Long.toString(timeStamp);
            emptyTimeMillis.setText(ts);*/

            if(Timbangan.matches("Timbangan Meja|Timbangan Sentisimal|Timbangan Pegas|Timbangan Elektronik|" +
                    "Timbangan Dacin Logam|Timbangan Jembatan|Timbangan Bobot Ingsut|Neraca Emas/Obat|" +
                    "PU BBM|Lain-Lain")){
                newDate.add(Calendar.YEAR, 1);
                teraUlangBrkt.setText(simpleDateFormat.format(newDate.getTime()));
                long timeStamp = newDate.getTimeInMillis();
                String ts = Long.toString(timeStamp);
                emptyTimeMillis.setText(ts);
                newDate.set(Calendar.HOUR_OF_DAY, 0);
                newDate.set(Calendar.MINUTE, 0);
                newDate.set(Calendar.SECOND, 0);
                emptyTimeFormat.setText(simpleDateFormat4.format(newDate.getTime()));
            }else if(Timbangan.equals("Meter Air")) {
                newDate.add(Calendar.YEAR, 5);
                teraUlangBrkt.setText(simpleDateFormat.format(newDate.getTime()));
                long timeStamp = newDate.getTimeInMillis();
                String ts = Long.toString(timeStamp);
                emptyTimeMillis.setText(ts);
                newDate.set(Calendar.HOUR_OF_DAY, 0);
                newDate.set(Calendar.MINUTE, 0);
                newDate.set(Calendar.SECOND, 0);
                emptyTimeFormat.setText(simpleDateFormat4.format(newDate.getTime()));
            }else if(Timbangan.equals("Meter Gas")) {
                newDate.add(Calendar.YEAR, 10);
                teraUlangBrkt.setText(simpleDateFormat.format(newDate.getTime()));
                long timeStamp = newDate.getTimeInMillis();
                String ts = Long.toString(timeStamp);
                emptyTimeMillis.setText(ts);
                newDate.set(Calendar.HOUR_OF_DAY, 0);
                newDate.set(Calendar.MINUTE, 0);
                newDate.set(Calendar.SECOND, 0);
                emptyTimeFormat.setText(simpleDateFormat4.format(newDate.getTime()));
            }
        },calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void Input(String dataID) {

        String tanggalID;

        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MMM-dd ");
        saveCurrentDate = currentDate.format(calendar.getTime());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currentTime.format(calendar.getTime());

        tanggalID = saveCurrentDate + saveCurrentTime;

        String namaInput = namaPemilik.getEditableText().toString();
        String noHpInput = noHp.getEditableText().toString();
        String alamatInput = Objects.requireNonNull(atAlamat.getText().toString());
        String kecamatanInput = kecamatanSpinner.getSelectedItem().toString();
        String kelurahanInput = kelurahanSpinner.getSelectedItem().toString();
        String jenisTimbanganInput = jtSpinner.getSelectedItem().toString();
        String kapasitasInput = kapasitas.getEditableText().toString();
        String anakTimbanganInput = anakTimbangan.getEditableText().toString();
        int biayaInput = Integer.parseInt(biaya.getEditableText().toString());
        String tanggalTeraAwal = teraUlangAwal.getText().toString();
        String tanggalTeraAkhir = teraUlangBrkt.getText().toString();
        String teksKosong = emptyText.getText().toString();
        String saveBulan = emptyBulan.getText().toString();
        String time = emptyTimeMillis.getText().toString();
        long teksTimeMilis = Long.parseLong(time);
        String teksTimeFormat = emptyTimeFormat.getText().toString();
        String satuan = multiLineRadioGroup.getCheckedRadioButtonText().toString();
        String jumlah = Objects.requireNonNull(quantity.getEditableText()).toString();

            DatabaseReference dbase6 = FirebaseDatabase.getInstance().getReference().child("Grafik")
                    .child("GrafikUttp").child(teksKosong).child(jenisTimbanganInput);

            dbase6.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    dbase6.runTransaction(new Transaction.Handler() {
                        @NonNull
                        @Override
                        public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                            HashMap<String, Object> tempdata = (HashMap<String, Object>) currentData.getValue();
                            if (tempdata == null){
                                return Transaction.success(currentData);
                            }
                            long newCount = (Long) tempdata.get("Count") + 1;
                            tempdata.put("Count", newCount);
                            currentData.setValue(tempdata);
                            return Transaction.success(currentData);
                        }

                        @Override
                        public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            /*Grafik Retribusi*/

            DatabaseReference dbase3 = FirebaseDatabase.getInstance().getReference().child("Grafik").child(teksKosong).child(saveBulan);
            dbase3.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                    dbase3.runTransaction(new Transaction.Handler() {
                        @NonNull
                        @NotNull
                        @Override
                        public Transaction.Result doTransaction(@NonNull @NotNull MutableData currentData) {
                            HashMap<String, Object> tempdata = (HashMap<String, Object>) currentData.getValue();
                            if (tempdata == null){
                                return Transaction.success(currentData);
                            }
                            long newCount = (Long) tempdata.get("BiayaRetribusi") + biayaInput;
                            tempdata.put("BiayaRetribusi", newCount);
                            currentData.setValue(tempdata);
                            return Transaction.success(currentData);
                        }

                        @Override
                        public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, boolean committed, @Nullable @org.jetbrains.annotations.Nullable DataSnapshot currentData) {

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

            /*Monitoring*/

            DatabaseReference dbase4 = FirebaseDatabase.getInstance().getReference().child("Monitoring");
            String id = dbase4.push().getKey();
            dbase4.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (!snapshot.child(id).exists()){
                        HashMap<String, Object> userInputMap = new HashMap<>();
                        userInputMap.put("Nama", namaInput);
                        userInputMap.put("Pid", tanggalID);
                        userInputMap.put("NoHp", noHpInput);
                        userInputMap.put("Alamat", alamatInput);
                        userInputMap.put("Quantity", jumlah);
                        userInputMap.put("JenisTimbangan", jenisTimbanganInput);
                        userInputMap.put("Kapasitas", kapasitasInput);
                        userInputMap.put("AnakTimbangan", anakTimbanganInput);
                        userInputMap.put("UnixTimestamp", teksTimeMilis);
                        userInputMap.put("TanggalTeraUlangBerikutnya", tanggalTeraAkhir);
                        userInputMap.put("TanggalMonitoring", teksTimeFormat);

                        dbase4.child(teksTimeFormat).child(id).updateChildren(userInputMap);
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

            dbase4.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (!snapshot.child(id).exists()){
                        HashMap<String, Object> userInputMap = new HashMap<>();
                        userInputMap.put("Pid", tanggalID);
                        userInputMap.put("UnixTimestamp", teksTimeMilis);
                        userInputMap.put("TanggalMonitoring", teksTimeFormat);

                        dbase4.child(id).updateChildren(userInputMap);
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

            /*Input Tera*/

            final DatabaseReference dbase;
            dbase = FirebaseDatabase.getInstance().getReference().child("InputTera");
            dbase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if(!snapshot.child(dataID).exists()){
                        HashMap<String, Object> userInputMap = new HashMap<>();
                        userInputMap.put("PId", tanggalID);
                        userInputMap.put("Nama", namaInput);
                        userInputMap.put("NoHp", noHpInput);
                        userInputMap.put("Alamat", alamatInput);
                        userInputMap.put("Kecamatan", kecamatanInput);
                        userInputMap.put("Kelurahan", kelurahanInput);
                        userInputMap.put("JenisTimbangan", jenisTimbanganInput);
                        userInputMap.put("Kapasitas", kapasitasInput);
                        userInputMap.put("AnakTimbangan", anakTimbanganInput);
                        userInputMap.put("Biaya", biayaInput);
                        userInputMap.put("TanggalMonitoring", teksTimeFormat);
                        userInputMap.put("TanggalDropdown", teksKosong);
                        userInputMap.put("UnixTimestamp", teksTimeMilis);
                        userInputMap.put("TanggalTeraUlangAwal", tanggalTeraAwal);
                        userInputMap.put("Satuan", satuan);
                        userInputMap.put("TanggalTeraUlangBerikutnya", tanggalTeraAkhir);
                        userInputMap.put("Quantity", jumlah);

                        dbase.child(teksKosong).child(dataID).updateChildren(userInputMap)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()){
                                        Toast.makeText(EditData.this, "Edit Tera Ulang berhasil", Toast.LENGTH_LONG).show();
                                        setDefault();
                                    }
                                    else{
                                        Toast.makeText(EditData.this, "Jaringan bermasalah", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

            Intent intent = new Intent(this, Dashboard.class);
            startActivity(intent);
        }

    @SuppressLint("SetTextI18n")
    private void setDefault() {
        namaPemilik.setText("");
        noHp.setText("");
        atAlamat.clearFocus();
        kecamatanSpinner.setSelection(0);
        kelurahanSpinner.setSelection(0);
        jtSpinner.setSelection(0);
        quantity.setText("");
        kapasitas.setText("");
        anakTimbangan.setText("");
        biaya.setText("");
        teraUlangAwal.setText("--");
        teraUlangBrkt.setText("--");
        namaPemilik.setFocusable(true);
    }

    @Override
    public void onBackPressed() {

        String jenisTimbanganInput = jenisUTTP;
        int biayaInput = Integer.parseInt(biaya.getEditableText().toString());
        String teksKosong = emptyText.getText().toString();
        String saveBulan = emptyBulan.getText().toString();

        new AlertDialog.Builder(this)
                .setTitle("Batalkan Perubahan")
                .setMessage("Yakin ingin membatalkan Perubahan ?")
                .setPositiveButton("Ya", ((dialog, which) -> {
                    DatabaseReference dbase6 = FirebaseDatabase.getInstance().getReference().child("Grafik")
                            .child("GrafikUttp").child(teksKosong).child(jenisTimbanganInput);

                    dbase6.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            dbase6.runTransaction(new Transaction.Handler() {
                                @NonNull
                                @Override
                                public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                                    HashMap<String, Object> tempdata = (HashMap<String, Object>) currentData.getValue();
                                    if (tempdata == null){
                                        return Transaction.success(currentData);
                                    }
                                    long newCount = (Long) tempdata.get("Count") + 1;
                                    tempdata.put("Count", newCount);
                                    currentData.setValue(tempdata);
                                    return Transaction.success(currentData);
                                }

                                @Override
                                public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    /*Grafik Retribusi*/

                    DatabaseReference dbase3 = FirebaseDatabase.getInstance().getReference().child("Grafik").child(teksKosong).child(saveBulan);
                    dbase3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                            dbase3.runTransaction(new Transaction.Handler() {
                                @NonNull
                                @NotNull
                                @Override
                                public Transaction.Result doTransaction(@NonNull @NotNull MutableData currentData) {
                                    HashMap<String, Object> tempdata = (HashMap<String, Object>) currentData.getValue();
                                    if (tempdata == null){
                                        return Transaction.success(currentData);
                                    }
                                    long newCount = (Long) tempdata.get("BiayaRetribusi") + biayaInput;
                                    tempdata.put("BiayaRetribusi", newCount);
                                    currentData.setValue(tempdata);
                                    return Transaction.success(currentData);
                                }

                                @Override
                                public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, boolean committed, @Nullable @org.jetbrains.annotations.Nullable DataSnapshot currentData) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                    Intent intent = new Intent(this, Dashboard.class);
                    startActivity(intent);
                }))
                .setNegativeButton("Tidak", (dialog, which) -> dialog.cancel())
                .show();
    }
}