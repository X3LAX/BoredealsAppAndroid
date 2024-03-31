package filrouge.groupei.boredealsappandroid;

// StoreViewHolder.java
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StoreViewHolder extends RecyclerView.ViewHolder {
    private TextView nameTextView;
    private TextView descriptionTextView;
    public Button buttonJEnProfite; // Ajoutez cette ligne

    public StoreViewHolder(@NonNull View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.nameTextView);
        descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        buttonJEnProfite = itemView.findViewById(R.id.button_j_en_profite);
    }

    public void bind(Store store) {
        nameTextView.setText(store.getName());
        descriptionTextView.setText(store.getDescription());
    }
}




