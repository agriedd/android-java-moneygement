package eddleven.io.moneygement.adapters.recycler;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import eddleven.io.moneygement.R;
import eddleven.io.moneygement.models.Pemasukan;

public class PemasukanAdapter extends RecyclerView.Adapter<PemasukanAdapter.viewHolder> {

    private Context context;
    private List<Pemasukan> pemasukanList;

    public PemasukanAdapter(Context context, List<Pemasukan> pemasukanList) {
        this.context = context;
        this.setList(pemasukanList);
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
        return new viewHolder(view, context);
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

    public static class viewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private Pemasukan pemasukan;
        private Context context;
        public viewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
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
            menu.add(0, v.getId(), 0, "Hapus").setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Toast.makeText(context, pemasukan.getId().toString(), Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
