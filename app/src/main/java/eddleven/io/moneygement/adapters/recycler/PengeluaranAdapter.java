package eddleven.io.moneygement.adapters.recycler;

import android.content.Context;
import android.content.Intent;
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
import eddleven.io.moneygement.dialogs.UbahPemasukanDialog;
import eddleven.io.moneygement.models.DaoSession;
import eddleven.io.moneygement.models.Pemasukan;
import eddleven.io.moneygement.models.PemasukanDao;
import eddleven.io.moneygement.models.Pengeluaran;
import eddleven.io.moneygement.models.PengeluaranDao;

public class PengeluaranAdapter extends RecyclerView.Adapter<PengeluaranAdapter.viewHolder> {

    private Context context;
    private List<Pengeluaran> pengeluaranList;
    private UpdateEvent updateEvent;
    public interface UpdateEvent{
        public void update();
    }

    public PengeluaranAdapter(Context context, List<Pengeluaran> pengeluaranList, UpdateEvent updateEvent) {
        this.context = context;
        this.setList(pengeluaranList);
        this.updateEvent = updateEvent;
    }

    public void setList(List<Pengeluaran> pengeluaranList) {
        if(pengeluaranList.size() >= 1){
            Pengeluaran pengeluaran = new Pengeluaran();
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

    public void updateList(List<Pengeluaran> listPemasukan) {
        this.setList(listPemasukan);
        this.notifyDataSetChanged();
    }

    public void setUpdateEvent(UpdateEvent updateEvent) {
        this.updateEvent = updateEvent;
    }

    public static class viewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private Pengeluaran pengeluaran;
        private Context context;
        public UpdateEvent updateEvent = null;

        public viewHolder(@NonNull View itemView, Context context, @NotNull UpdateEvent updateEvent) {
            super(itemView);
            this.context = context;
            this.updateEvent = updateEvent;
        }

        public void bind(int position, Pengeluaran pengeluaran) {
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
            menu.add(0, v.getId(), 0, "Ubah").setOnMenuItemClickListener(this);
            menu.add(1, v.getId(), 0, "Hapus").setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(item.getGroupId() == 0){
//                DialogFragment newFragment = new UbahPemasukanDialog(pemasukan.getId(), new UbahPemasukanDialog.UbahPemasukanInterface() {
//                    @Override
//                    public void setIntent(Intent intent) {
//                        if(intent.getBooleanExtra("status", false)){
//                            Toast.makeText(context, "Berhasil Menyimpan Pemasukan!", Toast.LENGTH_SHORT).show();
//                            updateEvent.update();
//                        } else {
//                            Toast.makeText(context, "Gagal Menyimpan Pemasukan!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//                newFragment.setCancelable(false);
//                FragmentActivity activity = (FragmentActivity) context;
//                newFragment.show(activity.getSupportFragmentManager(), "ubahpengeluaran");
            } else {
                FragmentActivity activity = (FragmentActivity) context;
                DaoSession daoSession = ((App) activity.getApplication()).getDaoSession();
                PengeluaranDao pengeluaranDao = daoSession.getPengeluaranDao();
                long id = pengeluaran.getId();
                Pengeluaran pengeluaran = pengeluaranDao.loadByRowId(id);
                pengeluaranDao.delete(pengeluaran);
                Toast.makeText(context, "Data berhasil dihapus!", Toast.LENGTH_SHORT).show();
                updateEvent.update();
            }
            return true;
        }
    }
}
