package eddleven.io.moneygement.adapters.recycler;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import eddleven.io.moneygement.HutangActivity;
import eddleven.io.moneygement.R;
import eddleven.io.moneygement.dialogs.UbahPengeluaranDialog;
import eddleven.io.moneygement.models.DaoSession;
import eddleven.io.moneygement.models.Hutang;
import eddleven.io.moneygement.models.Pemasukan;
import eddleven.io.moneygement.models.Pengeluaran;
import eddleven.io.moneygement.models.PengeluaranDao;
import eddleven.io.moneygement.repo.HutangRepo;
import eddleven.io.moneygement.repo.PemasukanRepo;

public class HutangAdapter extends RecyclerView.Adapter<HutangAdapter.viewHolder> {

    private Context context;
    private List<Hutang> hutangList;
    private UpdateEvent updateEvent;
    public interface UpdateEvent{
        public void update();
        public void openActivity(long id);
    }

    public HutangAdapter(Context context, List<Hutang> hutangList, UpdateEvent updateEvent) {
        this.context = context;
        this.setList(hutangList);
        this.updateEvent = updateEvent;
    }

    public void setList(List<Hutang> pengeluaranList) {
        if(pengeluaranList.size() >= 1){
            Hutang pengeluaran = new Hutang();
            pengeluaranList.add(pengeluaran);
        }
        this.hutangList = pengeluaranList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == 0){
            view = LayoutInflater.from(this.context).inflate(R.layout.list_item_hutang, parent, false);
        } else {
            view = LayoutInflater.from(this.context).inflate(R.layout.message_from_bottom, parent, false);
        }
        return new viewHolder(view, context, updateEvent);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        if(this.getItemViewType(position) == 0){
            holder.bind(position, this.hutangList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == this.getItemCount() - 1 ? 1 : 0;
    }

    @Override
    public int getItemCount() {
        return this.hutangList.size();
    }

    public void updateList(List<Hutang> hutangList) {
        this.setList(hutangList);
        this.notifyDataSetChanged();
    }

    public void setUpdateEvent(UpdateEvent updateEvent) {
        this.updateEvent = updateEvent;
    }

    public static class viewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener, View.OnClickListener {
        private Hutang hutang;
        private Context context;
        public UpdateEvent updateEvent = null;

        public viewHolder(@NonNull View itemView, Context context, @NotNull UpdateEvent updateEvent) {
            super(itemView);
            this.context = context;
            this.updateEvent = updateEvent;
        }

        public void bind(int position, Hutang hutang) {
            this.hutang = hutang;
            itemView.setOnCreateContextMenuListener(this);
            FloatingActionButton color = itemView.findViewById(R.id.item_kategori_color);
            TextView title = itemView.findViewById(R.id.item_nominal);
            TextView subtitle = itemView.findViewById(R.id.item_subtitle);
            TextView date = itemView.findViewById(R.id.item_date);
            TextView date_expired = itemView.findViewById(R.id.item_date_expired);
            LinearLayout container = itemView.findViewById(R.id.item_container);
            container.setOnClickListener(this);
            title.setText("Rp." + hutang.getNominal() + ",-");
            subtitle.setText(hutang.getKeterangan().toString());
            date.setText("Tanggal: " + hutang.getTanggal().toString());
            date_expired.setText("Tenggat: " + hutang.getTanggal_lunas().toString());
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Aksi untuk Item #" + hutang.getId());
            menu.add(0, v.getId(), 0, "Atur Lunas").setOnMenuItemClickListener(this);
            menu.add(1, v.getId(), 0, "Hapus").setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(item.getGroupId() == 0){

            } else {
                FragmentActivity activity = (FragmentActivity) context;
                long id = hutang.getId();
                //hapus hutang repo
                HutangRepo.delete(activity.getApplication(), id);
                Toast.makeText(context, "Data berhasil dihapus!", Toast.LENGTH_SHORT).show();
                updateEvent.update();
            }
            return true;
        }

        @Override
        public void onClick(View v) {
            updateEvent.openActivity(hutang.getId());
        }
    }
}
