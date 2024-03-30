package filrouge.groupei.boredealsappandroid;

import android.os.Parcel;
import android.os.Parcelable;

public class NotificationData implements Parcelable {
    private String notificationText;

    public NotificationData(String notificationText) {
        this.notificationText = notificationText;
    }

    protected NotificationData(Parcel in) {
        notificationText = in.readString();
    }

    public static final Creator<NotificationData> CREATOR = new Creator<NotificationData>() {
        @Override
        public NotificationData createFromParcel(Parcel in) {
            return new NotificationData(in);
        }

        @Override
        public NotificationData[] newArray(int size) {
            return new NotificationData[size];
        }
    };

    public String getNotificationText() {
        return notificationText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(notificationText);
    }
}