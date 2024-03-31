package filrouge.groupei.boredealsappandroid;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreViewHolder> {
    private List<Store> storeList;

    public StoreAdapter(List<Store> storeList) {
        this.storeList = storeList;
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store, parent, false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        holder.bind(storeList.get(position));
        holder.buttonJEnProfite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenez la position de l'article sélectionné
                int itemPosition = holder.getAdapterPosition();

                // Obtenez le magasin sélectionné
                Store selectedStore = storeList.get(itemPosition);

                // Créez un intent pour ouvrir l'activité CodeActivity
                Intent intent = new Intent(v.getContext(), CodeActivity.class);

                // Ajoutez les informations sur le magasin à l'intent
                intent.putExtra("selectedStore", selectedStore);

                // Démarrez l'activité CodeActivity
                v.getContext().startActivity(intent);
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
