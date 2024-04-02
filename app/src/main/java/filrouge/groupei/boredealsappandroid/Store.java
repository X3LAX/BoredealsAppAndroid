package filrouge.groupei.boredealsappandroid;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Store implements Parcelable {
    private int id;
    private String name;
    private String description;
    private String url;
    private int discountPercentage;
    private String promocode;
    private String link;

    // Default constructor required for Jackson
    public Store() {
    }

    // Constructor used for parcel
    public Store(int id, String name, String description, String url, int discountPercentage, String promocode) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.discountPercentage = discountPercentage;
        this.promocode = promocode;

    }

    // Getters and setters (Ensure you have these for all fields)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @JsonProperty("name") // Maps JSON property name to this Java field
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @JsonProperty("description")
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @JsonProperty("url")
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    @JsonProperty("discount_percentages")
    public int getDiscountPercentage() { return discountPercentage; }
    public void setDiscountPercentage(int discountPercentage) { this.discountPercentage = discountPercentage; }

    @JsonProperty("promocode")
    public String getPromocode() { return promocode; }
    public void setPromocode(String promocode) { this.promocode = promocode; }

    @JsonProperty("link")
    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    // Parcelable implementation methods

    @Override
    public int describeContents() { return 0; }

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

    // Parcelable.Creator

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

    // Constructor used for Parcelable
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
