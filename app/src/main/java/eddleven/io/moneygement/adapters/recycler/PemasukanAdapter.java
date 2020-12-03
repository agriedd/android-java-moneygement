package eddleven.io.moneygement.adapters.recycler;

import android.app.Activity;
import android.app.Application;
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
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import eddleven.io.moneygement.App;
import eddleven.io.moneygement.R;
import eddleven.io.moneygement.dialogs.TambahPemasukanDialog;
import eddleven.io.moneygement.dialogs.UbahPemasukanDialog;
import eddleven.io.moneygement.models.DaoSession;
import eddleven.io.moneygement.models.Pemasukan;
import eddleven.io.moneygement.models.PemasukanDao;
import eddleven.io.moneygement.ui.pemasukan.PemasukanViewModel;

public class PemasukanAdapter extends RecyclerView.Adapter<PemasukanAdapter.viewHolder> {

    private Context context;
    private List<Pemasukan> pemasukanList;
    private UpdateEvent updateEvent;
    public interface UpdateEvent{
        public void update();
    }

    public PemasukanAdapter(Context context, List<Pemasukan> pemasukanList, UpdateEvent updateEvent) {
        this.context = context;
        this.setList(pemasukanList);
        this.updateEvent = updateEvent;
    }

    public void setList(List<Pemasukan> pemasukanList) {
        if(pemasukanList.size() >= 1){
            Pemasukan pemasukan = new Pemasukan();
            pemasukanList.add(pemasukan);
        }
        this.pemasukanList = pemasukanList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == 0){
            view = LayoutInflater.from(this.context).inflate(R.layout.list_item_pemasukan, parent, false);
        } else {
            view = LayoutInflater.from(this.context).inflate(R.layout.message_from_bottom, parent, false);
        }
        return new viewHolder(view, context, updateEvent);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        if(this.getItemViewType(position) == 0){
            holder.bind(position, this.pemasukanList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == this.getItemCount() - 1 ? 1 : 0;
    }

    @Override
    public int getItemCount() {
        return this.pemasukanList.size();
    }

    public void updateList(List<Pemasukan> listPemasukan) {
        this.setList(listPemasukan);
        this.notifyDataSetChanged();
    }

    public void setUpdateEvent(UpdateEvent updateEvent) {
        this.updateEvent = updateEvent;
    }

    public static class viewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private Pemasukan pemasukan;
        private Context context;
        public UpdateEvent updateEvent = null;

        public viewHolder(@NonNull View itemView, Context context, @NotNull UpdateEvent updateEvent) {
            super(itemView);
            this.context = context;
            this.updateEvent = updateEvent;
        }

        public void bind(int position, Pemasukan pemasukan) {
            this.pemasukan = pemasukan;
            itemView.setOnCreateContextMenuListener(this);
            FloatingActionButton color = itemView.findViewById(R.id.item_kategori_color);
            TextView title = itemView.findViewById(R.id.item_nominal);
            TextView subtitle = itemView.findViewById(R.id.item_subtitle);
            TextView date = itemView.findViewById(R.id.item_date);
            title.setText("Rp." + pemasukan.getNominal() + ",-");
            subtitle.setText(pemasukan.getKeterangan().toString());
            date.setText("Tanggal: " + pemasukan.getTanggal().toString());
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Aksi untuk Item #" + pemasukan.getId());
            menu.add(0, v.getId(), 0, "Ubah").setOnMenuItemClickListener(this);
            menu.add(1, v.getId(), 0, "Hapus").setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(item.getGroupId() == 0){
                DialogFragment newFragment = new UbahPemasukanDialog(pemasukan.getId(), new UbahPemasukanDialog.UbahPemasukanInterface() {
                    @Override
                    public void setIntent(Intent intent) {
                        if(intent.getBooleanExtra("status", false)){
                            Toast.makeText(context, "Berhasil Menyimpan Pemasukan!", Toast.LENGTH_SHORT).show();
                            updateEvent.update();
                        } else {
                            Toast.makeText(context, "Gagal Menyimpan Pemasukan!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                newFragment.setCancelable(false);
                FragmentActivity activity = (FragmentActivity) context;
                newFragment.show(activity.getSupportFragmentManager(), "ubahpemasukan");
            } else {
                FragmentActivity activity = (FragmentActivity) context;
                DaoSession daoSession = ((App) activity.getApplication()).getDaoSession();
                PemasukanDao pemasukanDao = daoSession.getPemasukanDao();
                long id = pemasukan.getId();
                Pemasukan pemasukan = pemasukanDao.loadByRowId(id);
                pemasukanDao.delete(pemasukan);
                Toast.makeText(context, "Data berhasil dihapus!", Toast.LENGTH_SHORT).show();
                updateEvent.update();
            }
            return true;
        }
    }
}
