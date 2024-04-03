package filrouge.groupei.boredealsappandroid;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageButton; // Import ajouté

public class StoreViewHolder extends RecyclerView.ViewHolder {
    private TextView nameTextView;
    private TextView descriptionTextView;
    public Button buttonJEnProfite;
    ImageButton addToFav;

    public StoreViewHolder(@NonNull View itemView) {
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




