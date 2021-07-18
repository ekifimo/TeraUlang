package pemda.cirebon.teraulang;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class RekamData extends AppCompatActivity {

    TextInputEditText namaPemilik, noHp, kapasitas, anakTimbangan, biaya, quantity;
    String saveCurrentDate, saveCurrentTime;
    TextView teraUlangAwal, teraUlangBrkt, emptyText;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat simpleDateFormat, simpleDateFormat2;
    ArrayList<String> arrayList_parent;
    ArrayList<String> arrayList_Harjamukti, arrayList_Kejaksan, arrayList_Kesambi, arrayList_Lemahwungkuk, arrayList_Pekalipan, arrayList_default ;
    ArrayAdapter<String> arrayAdapter_parent;
    ArrayAdapter<String> arrayAdapter_child;
    Button submitBtn;
    AutoCompleteTextView atAlamat;
    ImageButton imageButton, backButton;
    Spinner jtSpinner, kecamatanSpinner, kelurahanSpinner;

    @SuppressLint({"WrongViewCast", "CutPasteId", "SimpleDateFormat"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekam_data);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        simpleDateFormat2 =  new SimpleDateFormat("yyyy");

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
        backButton = findViewById(R.id.backbutton);
        quantity = findViewById(R.id.Quantity);

        teraUlangAwal = findViewById(R.id.teraulangawal);
        teraUlangBrkt = findViewById(R.id.teraulangbrk);
        imageButton = findViewById(R.id.DateButton);

        imageButton.setOnClickListener(v -> {
            v = this.getCurrentFocus();
            if (v != null){
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            if (jtSpinner.getSelectedItem().equals("Pilih Jenis Timbangan")) {
                Toast.makeText(this, "Masukkan Jenis Timbangan", Toast.LENGTH_LONG).show();

            }
            else
                showdatedialog();
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

        backButton.setOnClickListener(v->{
            Intent intent = new Intent(this, Dashboard.class);
            startActivity(intent);
        });

        submitBtn.setOnClickListener(v -> Input());

    }


//    private void Tahun() {
//        String teksKosong = emptyText.getText().toString();
//
//        final DatabaseReference dbase2;
//        dbase2 = FirebaseDatabase.getInstance().getReference().child("Tahun");
//        dbase2.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                if (!snapshot.child("ListTahun").child(teksKosong).exists()){
//                    HashMap<String, Object> inputTahun = new HashMap<>();
//                    inputTahun.put("ListTahun", teksKosong);
//
//                    dbase2.updateChildren(inputTahun).addOnCompleteListener(task -> {
//
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });
//    }

    private void showdatedialog() {

        Calendar calender = Calendar.getInstance();

        String Timbangan = jtSpinner.getSelectedItem().toString();

        datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, month, dayOfMonth);
            teraUlangAwal.setText(simpleDateFormat.format(newDate.getTime()));
            emptyText.setText(simpleDateFormat2.format(newDate.getTime()));
            if(Timbangan.matches("Timbangan Meja|Timbangan Sentisimal|Timbangan Pegas|Timbangan Elektronik|" +
                    "Timbangan Dacin Logam|Timbangan Jembatan|Timbangan Bobot Ingsut|Neraca Emas/Obat|" +
                    "PU BBM|Lain-Lain")){
                newDate.add(Calendar.YEAR, 1);
                teraUlangBrkt.setText(simpleDateFormat.format(newDate.getTime()));
            }else if(Timbangan.equals("Meter Air")) {
                newDate.add(Calendar.YEAR, 5);
                teraUlangBrkt.setText(simpleDateFormat.format(newDate.getTime()));
            }else if(Timbangan.equals("Meter Gas")) {
                newDate.add(Calendar.YEAR, 10);
                teraUlangBrkt.setText(simpleDateFormat.format(newDate.getTime()));
            }
        },calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void Input() {

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
        String biayaInput = biaya.getEditableText().toString();
        String tanggalTeraAwal = teraUlangAwal.getText().toString();
        String tanggalTeraAkhir = teraUlangBrkt.getText().toString();
        String teksKosong = emptyText.getText().toString();
        String jumlah = Objects.requireNonNull(quantity.getEditableText()).toString();


        final DatabaseReference dbase;
        dbase = FirebaseDatabase.getInstance().getReference().child("InputTera");
        dbase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(!snapshot.child(tanggalID).exists()){
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
                    userInputMap.put("TanggalDropdown", teksKosong);
                    userInputMap.put("TanggalTeraUlangAwal", tanggalTeraAwal);
                    userInputMap.put("TanggalTeraUlangBerikutnya", tanggalTeraAkhir);
                    userInputMap.put("Quantity", jumlah);

                    dbase.child(teksKosong).child(tanggalID).updateChildren(userInputMap)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()){
                                    Toast.makeText(RekamData.this, "Input Tera Ulang berhasil", Toast.LENGTH_LONG).show();
                                    setDefault();
                                }
                                else{
                                    Toast.makeText(RekamData.this, "Jaringan bermasalah", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
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
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }
}