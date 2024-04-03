package filrouge.groupei.boredealsappandroid;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.ArrayList;


public class StoreActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StoreAdapter storeAdapter;
    private List<Store> storeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        storeAdapter = new StoreAdapter(storeList);
        recyclerView.setAdapter(storeAdapter);
    }
}
