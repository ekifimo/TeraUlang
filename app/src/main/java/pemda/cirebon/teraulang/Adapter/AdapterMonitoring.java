package pemda.cirebon.teraulang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import pemda.cirebon.teraulang.Model.CalenderNotes;
import pemda.cirebon.teraulang.R;

public class AdapterMonitoring extends RecyclerView.Adapter<AdapterMonitoring.myViewHolder> {

    Context context;
    ArrayList<CalenderNotes> calenderNotesArrayList;

    public AdapterMonitoring(Context context, ArrayList<CalenderNotes> calenderNotesArrayList) {
        this.context = context;
        this.calenderNotesArrayList = calenderNotesArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_monitoring, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterMonitoring.myViewHolder holder, int position) {

        CalenderNotes calenderNotes = calenderNotesArrayList.get(position);
        holder.tvTgl.setText(calenderNotes.getTanggalTeraUlangBerikutnya());
        holder.tvNama.setText(calenderNotes.getNama());
        holder.tvNoHp.setText(calenderNotes.getNoHp());
        holder.tvAlamat.setText(calenderNotes.getAlamat());
        holder.tvTimbangan.setText(calenderNotes.getJenisTimbangan());
        holder.tvAnakTimbangan.setText(calenderNotes.getAnakTimbangan());
        holder.tvQuantity.setText(calenderNotes.getQuantity());
    }

    @Override
    public int getItemCount() {
        return calenderNotesArrayList.size();
    }


    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView tvTgl, tvNama, tvNoHp, tvAlamat, tvTimbangan, tvAnakTimbangan,
                tvQuantity;

        public myViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tvTgl = itemView.findViewById(R.id.tanggalteraulangbrkt);
            tvNama = itemView.findViewById(R.id.namapemilikdetail);
            tvNoHp = itemView.findViewById(R.id.NoHpDetail);
            tvAlamat = itemView.findViewById(R.id.alamatdetail);
            tvTimbangan = itemView.findViewById(R.id.JenisTimbanganDetail);
            tvAnakTimbangan = itemView.findViewById(R.id.AnakTimbanganQuantity);
            tvQuantity = itemView.findViewById(R.id.QuantityDetail);
        }
    }
}
