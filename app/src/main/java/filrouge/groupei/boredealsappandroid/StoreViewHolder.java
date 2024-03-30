package filrouge.groupei.boredealsappandroid;

// StoreViewHolder.java
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StoreViewHolder extends RecyclerView.ViewHolder {
    private TextView nameTextView;
    private TextView descriptionTextView;
    public ImageButton notifyBell;


    public StoreViewHolder(@NonNull View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.nameTextView);
        descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        notifyBell = itemView.findViewById(R.id.notifyBell);
    }

    public void bind(Store store) {
        nameTextView.setText(store.getName());
        descriptionTextView.setText(store.getDescription());
    }
}




