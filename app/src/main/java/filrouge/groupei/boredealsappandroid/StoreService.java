package filrouge.groupei.boredealsappandroid;

import filrouge.groupei.boredealsappandroid.Store;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface StoreService {
    @GET("BoredealsAppAndroidRessources/main/brands.json")
    Call<List<Store>> getStores();
}