package filrouge.groupei.boredealsappandroid;

import filrouge.groupei.boredealsappandroid.Store;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

/**
 * Interface définissant un service pour récupérer la liste des magasins depuis une source externe.
 */
public interface StoreService {

    /**
     * Méthode GET pour récupérer la liste des magasins à partir de la source spécifiée.
     *
     * @return Un objet Call encapsulant la liste des magasins.
     */
    @GET("BoredealsAppAndroidRessources/main/brands.json")
    Call<List<Store>> getStores();
}
