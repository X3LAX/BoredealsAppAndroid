package filrouge.groupei.boredealsappandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

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

        SeekBar seekBar = findViewById(R.id.seekBar);
        TextView percentageTextView = findViewById(R.id.percentageTextView);

        seekBar.setMax(100);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress >= 0 && progress < getResources().getStringArray(R.array.discount_percentages).length) {
                    String percentage = getResources().getStringArray(R.array.discount_percentages)[progress];
                    percentageTextView.setText(percentage);
                    List<Store> filteredStores = new ArrayList<>();
                    for (Store store : storeList) {
                        if (store.getDiscountPercentage() >= parsePercentage(percentage)) {
                            filteredStores.add(store);
                        }
                    }
                    storeAdapter.setData(filteredStores);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Ne rien faire
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Ne rien faire
            }
        });
    }

    private int parsePercentage(String percentageString) {
        return Integer.parseInt(percentageString.replace("%", ""));
    }

    private List<Store> generateSampleData() {
        List<Store> stores = new ArrayList<>();

        String[] names = getResources().getStringArray(R.array.characters);
        String[] descriptions = getResources().getStringArray(R.array.description);
        String[] percentages = getResources().getStringArray(R.array.discount_percentages);

        if (names.length != descriptions.length || names.length != percentages.length) {
            return stores;
        }

        for (int i = 0; i < names.length; i++) {
            stores.add(new Store(names[i], descriptions[i], parsePercentage(percentages[i])));
        }

        return stores;
    }
}
