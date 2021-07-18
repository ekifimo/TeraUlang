package pemda.cirebon.teraulang.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

import io.paperdb.Paper;
import pemda.cirebon.teraulang.Dashboard;
import pemda.cirebon.teraulang.EditData;
import pemda.cirebon.teraulang.Login;
import pemda.cirebon.teraulang.Model.TeraData;
import pemda.cirebon.teraulang.R;

public class FirebaseAdapterRc extends RecyclerView.Adapter<FirebaseAdapterRc.myViewHolder> {

    Context context;
    ArrayList<TeraData> teraList;

    public FirebaseAdapterRc(Context context, ArrayList<TeraData> teraList) {
        this.context = context;
        this.teraList = teraList;
    }

    @NonNull
    @NotNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_table, parent, false);
        return new myViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull FirebaseAdapterRc.myViewHolder holder, int position) {


        TeraData teraData = teraList.get(position);
        holder.tvTgl.setText(teraData.getTanggalTeraUlangAwal());
        holder.tvNama.setText(teraData.getNama());
        holder.tvNoHp.setText(teraData.getNoHp());
        holder.tvAlamat.setText(teraData.getAlamat());
        holder.tvKecamatan.setText(teraData.getKecamatan());
        holder.tvKelurahan.setText(teraData.getKelurahan());
        holder.tvTimbangan.setText(teraData.getJenisTimbangan());
        holder.tvQuantity.setText(teraData.getQuantity());
        holder.tvAnakTimbangan.setText(teraData.getAnakTimbangan());
        holder.tvRetribusi.setText(teraData.getBiaya());

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditData.class);
            intent.putExtra("pid", teraData.getPId());
            intent.putExtra("tahun", teraData.getTanggalDropdown());
            context.startActivity(intent);
        });

        holder.btnDelet.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Hapus Data")
                    .setMessage("Yakin Ingin Menghapus Data ?")
                    .setPositiveButton("Ya", (dialog, which) -> {
                        DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("InputTera");
                        dref.child(teraData.getTanggalDropdown()).child(teraData.getPId()).removeValue()
                                .addOnCompleteListener(task -> {
                                    Toast.makeText(context, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                                    teraList.clear();
                                    notifyDataSetChanged();
                                });

            })
                .setNegativeButton("Tidak", (dialog, which) -> dialog.cancel())
                    .show();


        });
    }

    @Override
    public int getItemCount() {
        return teraList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{

        TextView tvTgl, tvNama, tvNoHp, tvAlamat, tvKecamatan, tvKelurahan, tvTimbangan, tvAnakTimbangan,
            tvRetribusi, tvQuantity;
        ImageButton btnEdit, btnDelet;

        public myViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tvTgl = itemView.findViewById(R.id.TglTeraAwal_tv);
            tvNama = itemView.findViewById(R.id.Nama_tv);
            tvNoHp = itemView.findViewById(R.id.noHp_tv);
            tvAlamat = itemView.findViewById(R.id.alamat_tv);
            tvKecamatan = itemView.findViewById(R.id.kecamatan_tv);
            tvKelurahan = itemView.findViewById(R.id.kelurahan_tv);
            tvTimbangan = itemView.findViewById(R.id.timbangan_tv);
            tvQuantity = itemView.findViewById(R.id.quantity_tv);
            tvAnakTimbangan = itemView.findViewById(R.id.AnakTimbangan_tv);
            tvRetribusi = itemView.findViewById(R.id.retribusi_tv);
            btnEdit = itemView.findViewById(R.id.EditItem);
            btnDelet = itemView.findViewById(R.id.DeleteItem);
        }

    }

}
