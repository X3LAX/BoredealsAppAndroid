package filrouge.groupei.boredealsappandroid;

import android.os.Parcel;
import android.os.Parcelable;

import java.security.AccessController;

public class Store implements Parcelable {
    private String name;
    private String description;
    private int discountPercentage;
    private String websiteLink;


    public Store(String name, String description, int discountPercentage, String websiteLink) {
        this.name = name;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.websiteLink = websiteLink;
    }

    protected Store(Parcel in) {
        name = in.readString();
        description = in.readString();
        discountPercentage = in.readInt();
        websiteLink = in.readString();
    }

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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

//    public int getLogoResourceId() {
//        String resourceName = name.toLowerCase().replace(" ", "_");
//
//        return App.getContext().getResources().getIdentifier(resourceName, "drawable", App.getContext().getPackageName());
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(discountPercentage);
        dest.writeString(websiteLink);
    }
}


