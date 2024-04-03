package filrouge.groupei.boredealsappandroid;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {
    private List<Store> storeList;

    public StoreAdapter(List<Store> initialStoreList) {
        this.storeList = initialStoreList;
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_store, parent, false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        Store store = storeList.get(position);
        holder.bind(store);
        holder.buttonJEnProfite.setOnClickListener(v -> {
            int itemPosition = holder.getAdapterPosition();
            if (itemPosition != RecyclerView.NO_POSITION) {
                Store selectedStore = storeList.get(itemPosition);

                Intent intent = new Intent(v.getContext(), CodeActivity.class);
                intent.putExtra("selectedStore", selectedStore);
                v.getContext().startActivity(intent);
            }
        });

        holder.addToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (store.isFavourite()) {
                    holder.addToFav.setImageResource(R.drawable.ic_heart_empty);
                    store.setFavourite(false);
                    FirebaseUtils firebaseUtils = new FirebaseUtils();
                    firebaseUtils.removeStoreFromFavorites(String.valueOf(store.getId()));
                } else {
                    holder.addToFav.setImageResource(R.drawable.ic_heart_fill);
                    store.setFavourite(true);
                    FirebaseUtils firebaseUtils = new FirebaseUtils();
                    firebaseUtils.addStoreToFavorites(String.valueOf(store.getId()));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public void setData(List<Store> newStoreList) {
        this.storeList = newStoreList;
        notifyDataSetChanged();
    }

    public static class StoreViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView descriptionTextView;
        Button buttonJEnProfite;
        ImageButton addToFav;

        public StoreViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            buttonJEnProfite = itemView.findViewById(R.id.button_j_en_profite);
            addToFav = itemView.findViewById(R.id.addToFav);
        }

        public void bind(Store store) {
            nameTextView.setText(store.getName());
            descriptionTextView.setText(store.getDescription());
            if (store.isFavourite()) {
                addToFav.setImageResource(R.drawable.ic_heart_fill);
            } else {
                addToFav.setImageResource(R.drawable.ic_heart_empty);
            }
        }
    }
}
