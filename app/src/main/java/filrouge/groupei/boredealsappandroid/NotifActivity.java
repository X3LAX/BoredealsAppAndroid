package filrouge.groupei.boredealsappandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NotifActivity extends AppCompatActivity {
    private ListView notificationListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);

        // Récupérer les données envoyées depuis StoreActivity
        String storeName = getIntent().getStringExtra("storeName");
        NotificationData notificationData = getIntent().getParcelableExtra("notificationData");

        // Utiliser les données pour afficher les informations dans NotifActivity
        if (storeName != null && notificationData != null) {
            // Utilisez les données pour afficher les informations dans l'interface utilisateur
            addNotification("Nouvelle notification pour " + storeName);
            // Vous pouvez également utiliser d'autres informations de notificationData ici
        }
    }

    private void addNotification(String notification) {
        notificationList.add(notification);
        adapter.notifyDataSetChanged();
    }

    // Méthode statique pour démarrer l'activité NotifActivity avec le nom du magasin
    public static void start(Activity activity, String storeName, NotificationData notificationData) {
        Intent intent = new Intent(activity, NotifActivity.class);
        intent.putExtra("storeName", storeName);
        intent.putExtra("notificationData", notificationData);
        activity.startActivity(intent);
    }

}
