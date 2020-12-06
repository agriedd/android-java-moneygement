package eddleven.io.moneygement.adapters.recycler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import eddleven.io.moneygement.App;
import eddleven.io.moneygement.R;
import eddleven.io.moneygement.dialogs.UbahPengeluaranDialog;
import eddleven.io.moneygement.models.BayarHutang;
import eddleven.io.moneygement.models.DaoSession;
import eddleven.io.moneygement.models.Pengeluaran;
import eddleven.io.moneygement.models.PengeluaranDao;
import eddleven.io.moneygement.repo.BayarHutangRepo;
import eddleven.io.moneygement.repo.PengeluaranRepo;

public class BayarUtangAdapter extends RecyclerView.Adapter<BayarUtangAdapter.viewHolder> {

    private Context context;
    private List<BayarHutang> pengeluaranList;
    private UpdateEvent updateEvent;
    public interface UpdateEvent{
        public void update();
    }

    public BayarUtangAdapter(Context context, List<BayarHutang> pengeluaranList, UpdateEvent updateEvent) {
        this.context = context;
        this.setList(pengeluaranList);
        this.updateEvent = updateEvent;
    }

    public void setList(List<BayarHutang> pengeluaranList) {
        if(pengeluaranList.size() >= 1){
            BayarHutang pengeluaran = new BayarHutang();
            pengeluaranList.add(pengeluaran);
        }
        this.pengeluaranList = pengeluaranList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == 0){
            view = LayoutInflater.from(this.context).inflate(R.layout.list_item_pengeluaran, parent, false);
        } else {
            view = LayoutInflater.from(this.context).inflate(R.layout.message_from_bottom, parent, false);
        }
        return new viewHolder(view, context, updateEvent);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        if(this.getItemViewType(position) == 0){
            holder.bind(position, this.pengeluaranList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == this.getItemCount() - 1 ? 1 : 0;
    }

    @Override
    public int getItemCount() {
        return this.pengeluaranList.size();
    }

    public void updateList(List<BayarHutang> listPemasukan) {
        this.setList(listPemasukan);
        this.notifyDataSetChanged();
    }

    public void setUpdateEvent(UpdateEvent updateEvent) {
        this.updateEvent = updateEvent;
    }

    public static class viewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private BayarHutang pengeluaran;
        private Context context;
        public UpdateEvent updateEvent = null;

        public viewHolder(@NonNull View itemView, Context context, @NotNull UpdateEvent updateEvent) {
            super(itemView);
            this.context = context;
            this.updateEvent = updateEvent;
        }

        public void bind(int position, BayarHutang pengeluaran) {
            this.pengeluaran = pengeluaran;
            itemView.setOnCreateContextMenuListener(this);
            FloatingActionButton color = itemView.findViewById(R.id.item_kategori_color);
            TextView title = itemView.findViewById(R.id.item_nominal);
            TextView subtitle = itemView.findViewById(R.id.item_subtitle);
            TextView date = itemView.findViewById(R.id.item_date);
            title.setText("Rp." + pengeluaran.getNominal() + ",-");
            subtitle.setText(pengeluaran.getKeterangan().toString());
            date.setText("Tanggal: " + pengeluaran.getTanggal().toString());
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Aksi untuk Item #" + pengeluaran.getId());
            menu.add(1, v.getId(), 0, "Hapus").setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(item.getGroupId() == 0){

            } else {
                Activity activity = (Activity) context;

                PengeluaranRepo.deleteByHutang(activity.getApplication(), pengeluaran.getId_hutang());
                BayarHutangRepo.delete(activity.getApplication(), pengeluaran.getId());

                Toast.makeText(context, "Data berhasil dihapus!", Toast.LENGTH_SHORT).show();
                updateEvent.update();
            }
            return true;
        }
    }
}
