package filrouge.groupei.boredealsappandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StoreAdapter storeAdapter;

    private List<Store> allStores;
    private SeekBar seekBar;
    private TextView percentageTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));


        // Initialize the adapter with an empty list
        storeAdapter = new StoreAdapter(new ArrayList<>());
        recyclerView.setAdapter(storeAdapter);

        // Initialize SeekBar and TextView
        seekBar = findViewById(R.id.seekBar);
        percentageTextView = findViewById(R.id.percentageTextView);


        fetchStores();


    }

    private void fetchStores() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/X3LAX/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        StoreService service = retrofit.create(StoreService.class);

        service.getStores().enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(@NonNull Call<List<Store>> call, @NonNull Response<List<Store>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allStores = response.body();
                    storeAdapter.setData(allStores);

                    // After fetching the stores, set up the SeekBar based on the maximum discount percentage
                    int maxDiscount = allStores.stream().mapToInt(Store::getDiscountPercentage).max().orElse(100);
                    setupSeekBar(maxDiscount);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch stores.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Store>> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupSeekBar(int maxDiscount) {
        // Assuming the discount percentages are in increments of 5
        seekBar.setMax(maxDiscount / 5);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int percentage = progress * 5;
                percentageTextView.setText(percentage + "%");

                List<Store> filteredStores = new ArrayList<>();
                for (Store store : allStores) {
                    if (store.getDiscountPercentage() >= percentage) {
                        filteredStores.add(store);
                    }
                }
                storeAdapter.setData(filteredStores);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Optional: Implement if needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Optional: Implement if needed
            }
        });
    }


    private void updateUI(List<Store> stores) {
        if (storeAdapter == null) {
            storeAdapter = new StoreAdapter(stores);
            recyclerView.setAdapter(storeAdapter);
        } else {
            storeAdapter.setData(stores);
            storeAdapter.notifyDataSetChanged();
        }
    }
}
