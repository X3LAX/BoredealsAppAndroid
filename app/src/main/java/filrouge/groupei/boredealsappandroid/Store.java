package filrouge.groupei.boredealsappandroid;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Modèle représentant un magasin.
 */
public class Store implements Parcelable {
    private int id;
    private String name;
    private String description;
    private String url;
    private int discountPercentage;
    private String promocode;
    private String link;
    private boolean isFavourite;

    /**
     * Constructeur par défaut de la classe Store.
     */
    public Store() {
    }

    /**
     * Constructeur de la classe Store.
     * @param id L'identifiant du magasin.
     * @param name Le nom du magasin.
     * @param description La description du magasin.
     * @param url L'URL du magasin.
     * @param discountPercentage Le pourcentage de réduction du magasin.
     * @param promocode Le code promotionnel du magasin.
     * @param isFavourite Indique si le magasin est favori ou non.
     */
    public Store(int id, String name, String description, String url, int discountPercentage, String promocode, boolean isFavourite) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.discountPercentage = discountPercentage;
        this.promocode = promocode;
        this.isFavourite = isFavourite;
    }

    /**
     * Obtient l'identifiant du magasin.
     * @return L'identifiant du magasin.
     */
    public int getId() { return id; }

    /**
     * Définit l'identifiant du magasin.
     * @param id L'identifiant du magasin.
     */
    public void setId(int id) { this.id = id; }

    /**
     * Obtient le nom du magasin.
     * @return Le nom du magasin.
     */
    @JsonProperty("name")
    public String getName() { return name; }

    /**
     * Définit le nom du magasin.
     * @param name Le nom du magasin.
     */
    public void setName(String name) { this.name = name; }

    /**
     * Obtient la description du magasin.
     * @return La description du magasin.
     */
    @JsonProperty("description")
    public String getDescription() { return description; }

    /**
     * Définit la description du magasin.
     * @param description La description du magasin.
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Obtient l'URL du magasin.
     * @return L'URL du magasin.
     */
    @JsonProperty("url")
    public String getUrl() { return url; }

    /**
     * Définit l'URL du magasin.
     * @param url L'URL du magasin.
     */
    public void setUrl(String url) { this.url = url; }

    /**
     * Obtient le pourcentage de réduction du magasin.
     * @return Le pourcentage de réduction du magasin.
     */
    @JsonProperty("discount_percentages")
    public int getDiscountPercentage() { return discountPercentage; }

    /**
     * Définit le pourcentage de réduction du magasin.
     * @param discountPercentage Le pourcentage de réduction du magasin.
     */
    public void setDiscountPercentage(int discountPercentage) { this.discountPercentage = discountPercentage; }

    /**
     * Obtient le code promotionnel du magasin.
     * @return Le code promotionnel du magasin.
     */
    @JsonProperty("promocode")
    public String getPromocode() { return promocode; }

    /**
     * Définit le code promotionnel du magasin.
     * @param promocode Le code promotionnel du magasin.
     */
    public void setPromocode(String promocode) { this.promocode = promocode; }

    /**
     * Obtient le lien du magasin.
     * @return Le lien du magasin.
     */
    @JsonProperty("link")
    public String getLink() { return link; }

    /**
     * Définit le lien du magasin.
     * @param link Le lien du magasin.
     */
    public void setLink(String link) { this.link = link; }

    /**
     * Vérifie si le magasin est favori.
     * @return true si le magasin est favori, sinon false.
     */
    public boolean isFavourite() {
        return isFavourite;
    }

    /**
     * Définit si le magasin est favori ou non.
     * @param favourite true si le magasin est favori, sinon false.
     */
    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    @Override
    public int describeContents() { return 0; }

    /**
     * Écriture des données de l'objet dans un Parcel.
     * @param dest Le Parcel dans lequel écrire les données.
     * @param flags Indicateurs spéciaux sur la façon de traiter le Parcel. Non utilisé dans ce cas.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(url);
        dest.writeInt(discountPercentage);
        dest.writeString(promocode);
        dest.writeString(link);
    }

    /**
     * Interface Parcelable.Creator pour créer des instances de la classe Store à partir d'un Parcel.
     */
    public static final Creator<Store> CREATOR = new Creator<Store>() {
        @Override
        public Store createFromParcel(Parcel in) {
            return new Store(in);
        }

        @Override
        public Store[] newArray(int size) {
            return new Store[size];
        }
    };

    /**
     * Constructeur utilisé pour créer une instance de Store à partir d'un Parcel.
     * @param in Le Parcel contenant les données nécessaires pour créer l'instance de Store.
     */
    protected Store(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        url = in.readString();
        discountPercentage = in.readInt();
        promocode = in.readString();
        link = in.readString();
    }
}
