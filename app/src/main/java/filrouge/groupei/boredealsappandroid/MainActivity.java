package filrouge.groupei.boredealsappandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StoreAdapter storeAdapter;
    private List<Store> storeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));


        storeList = generateSampleData();
        storeAdapter = new StoreAdapter(storeList);
        recyclerView.setAdapter(storeAdapter);
    }

    private List<Store> generateSampleData() {
        List<Store> stores = new ArrayList<>();

        String[] names = getResources().getStringArray(R.array.characters);
        String[] descriptions = getResources().getStringArray(R.array.description);

        for (int i = 0; i < names.length; i++) {
            stores.add(new Store(names[i], descriptions[i]));
        }

        return stores;
    }

}