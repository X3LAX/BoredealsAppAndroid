package filrouge.groupei.boredealsappandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class StoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_store);

        ImageButton notifyBell = findViewById(R.id.notifyBell);

        // Assurez-vous que notificationData est initialisée ici
        NotificationData notificationData = new NotificationData("Votre message de notification ici.");

        notifyBell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer le nom du magasin associé au clic
                String storeName = "Nom du magasin"; // Remplacer par la méthode pour obtenir le nom du magasin

            }
        });
    }

    public void addDataToNotification(Store store) {
        // Préparer les données à envoyer à NotifActivity
        String storeName = store.getName();
        NotificationData notificationData = store.getNotificationData();

        // Démarrer NotifActivity avec les données
        NotifActivity.start(this, storeName, notificationData);
    }


}
