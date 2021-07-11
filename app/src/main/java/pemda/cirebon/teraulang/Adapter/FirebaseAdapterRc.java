package pemda.cirebon.teraulang.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
        holder.tvTimbangan.setText(teraData.getJenisTimbangan() + teraData.getAnakTimbangan());
        holder.tvKapasitas.setText(teraData.getKapasitas());
        holder.tvRetribusi.setText(teraData.getBiaya());
    }

    @Override
    public int getItemCount() {
        return teraList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{

        TextView tvTgl, tvNama, tvNoHp, tvAlamat, tvKecamatan, tvKelurahan, tvTimbangan, tvKapasitas,
            tvRetribusi;

        public myViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tvTgl = itemView.findViewById(R.id.TglTeraAwal_tv);
            tvNama = itemView.findViewById(R.id.Nama_tv);
            tvNoHp = itemView.findViewById(R.id.noHp_tv);
            tvAlamat = itemView.findViewById(R.id.alamat_tv);
            tvKecamatan = itemView.findViewById(R.id.kecamatan_tv);
            tvKelurahan = itemView.findViewById(R.id.kelurahan_tv);
            tvTimbangan = itemView.findViewById(R.id.timbangan_tv);
            tvKapasitas = itemView.findViewById(R.id.kapasitas_tv);
            tvRetribusi = itemView.findViewById(R.id.retribusi_tv);
        }
    }
}
