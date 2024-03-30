package filrouge.groupei.boredealsappandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreViewHolder> {
    private List<Store> storeList;
    private NotificationClickListener notificationClickListener;
    public StoreAdapter(List<Store> storeList, NotificationClickListener listener) {
        this.storeList = storeList;
        this.notificationClickListener = listener;
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store, parent, false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bind(storeList.get(position));
        holder.notifyBell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Appeler la méthode dans StoreActivity pour ajouter les données à NotifActivity
                ((StoreActivity) v.getContext()).addDataToNotification(storeList.get(position));
            }
        });
    }




    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public void setData(List<Store> storeList) {
        this.storeList = storeList;
        notifyDataSetChanged();
    }
}
