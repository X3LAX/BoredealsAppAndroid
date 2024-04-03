package filrouge.groupei.boredealsappandroid;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Cette activité affiche le code promotionnel et les informations sur un magasin sélectionné.
 */
public class CodeActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private String userId;

    /**
     * Méthode appelée lors de la création de l'activité.
     *
     * @param savedInstanceState L'état enregistré précédemment de l'activité.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        // Récupérer le magasin sélectionné depuis l'intent
        Store store = getIntent().getParcelableExtra("selectedStore");
        if (store == null) return;

        // Initialisation des vues et chargement des données du magasin
        initializeViews(store);

        // Gestion de l'évaluation du magasin par l'utilisateur
        handleRating(store);
    }

    /**
     * Initialise les vues et charge les données du magasin.
     *
     * @param store Le magasin sélectionné.
     */
    private void initializeViews(Store store) {
        TextView textRecommendation = findViewById(R.id.textRecommendation);
        textRecommendation.setText(store.getName());

        TextView textDiscount = findViewById(R.id.textDiscount);
        textDiscount.setText(store.getDescription());

        String websiteLink = store.getLink();
        Bitmap qrCodeBitmap = generateQRCode(websiteLink, 500, 500);

        String baseUrl = "https://github.com/X3LAX/BoredealsAppAndroidRessources/blob/main/logos/";
        String logoUrl = baseUrl + store.getName().toLowerCase() + "_logo.png?raw=true";

        ImageView imageLogo = findViewById(R.id.imageLogo);
        Picasso.get()
                .load(logoUrl)
                .error(R.drawable.bell)
                .into(imageLogo);

        RatingBar ratingBar = findViewById(R.id.ratingBar);

        CardView cardPromoCode = findViewById(R.id.cardPromoCode);
        animateCard(cardPromoCode);

        TextView textPromoCode = findViewById(R.id.textPromoCode);
        textPromoCode.setText(store.getPromocode());

        // Gestion du clic sur le bouton pour copier le code promo dans le presse-papiers
        TextView textCopier = findViewById(R.id.textCopier);
        textCopier.setOnClickListener(v -> {
            String codePromo = store.getPromocode();
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("Promocode", codePromo);
            clipboardManager.setPrimaryClip(clipData);

            Toast.makeText(CodeActivity.this, "Code promo copié: " + codePromo, Toast.LENGTH_SHORT).show();

            String link = store.getLink();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            startActivity(browserIntent);
        });

        // Gestion du clic sur le bouton pour afficher le code QR
        Button buttonGenerateQRCode = findViewById(R.id.boutonQrCode);
        buttonGenerateQRCode.setOnClickListener(v -> showQRCodeDialog(qrCodeBitmap));

        userId = getUserId();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Récupération de l'évaluation précédente du magasin et affichage dans la RatingBar
        float savedRating = getSavedRatingFromPreferences(String.valueOf(store.getId()));
        ratingBar.setRating(savedRating);
    }

    /**
     * Gestion de l'évaluation du magasin par l'utilisateur.
     *
     * @param store Le magasin sélectionné.
     */
    private void handleRating(Store store) {
        RatingBar ratingBar = findViewById(R.id.ratingBar);

        // Enregistrement de l'évaluation dans Firebase et en local lorsqu'elle est modifiée
        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
            saveRatingToFirebase(rating, String.valueOf(store.getId()));
            saveRatingLocally(rating, String.valueOf(store.getId()));
            Toast.makeText(CodeActivity.this, "Évaluation enregistrée avec succès", Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * Génère un code QR à partir du texte donné.
     *
     * @param text   Le texte à encoder dans le code QR.
     * @param width  La largeur du code QR.
     * @param height La hauteur du code QR.
     * @return Le bitmap du code QR généré.
     */
    private Bitmap generateQRCode(String text, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Affiche une boîte de dialogue contenant le code QR.
     *
     * @param qrCodeBitmap Le bitmap du code QR à afficher.
     */
    private void showQRCodeDialog(Bitmap qrCodeBitmap) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_qrcode, null);
        builder.setView(dialogView);

        ImageView imageViewQRCode = dialogView.findViewById(R.id.imageQRCode);
        imageViewQRCode.setImageBitmap(qrCodeBitmap);

        AlertDialog dialog = builder.create();
        dialog.show();

        Button buttonClose = dialogView.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(v -> dialog.dismiss());
    }

    /**
     * Anime une vue en effectuant un zoom avant.
     *
     * @param cardView La vue à animer.
     */
    private void animateCard(CardView cardView) {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(cardView, View.SCALE_X, 0.8f, 1.0f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(cardView, View.SCALE_Y, 0.8f, 1.0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.setDuration(500);
        animatorSet.start();
    }

    /**
     * Récupère l'identifiant de l'utilisateur.
     *
     * @return L'identifiant de l'utilisateur.
     */
    private String getUserId() {
        return "user123"; // Remplacer par la vraie méthode de récupération de l'ID de l'utilisateur
    }

    /**
     * Récupère l'évaluation précédemment enregistrée du magasin depuis les préférences.
     *
     * @param storeId L'identifiant du magasin.
     * @return L'évaluation précédemment enregistrée.
     */
    private float getSavedRatingFromPreferences(String storeId) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(storeId, 0.0f);
    }

    /**
     * Enregistre localement l'évaluation du magasin.
     *
     * @param rating  L'évaluation du magasin.
     * @param storeId L'identifiant du magasin.
     */
    private void saveRatingLocally(float rating, String storeId) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(storeId, rating);
        editor.apply();
    }

    /**
     * Enregistre l'évaluation du magasin dans Firebase.
     *
     * @param rating  L'évaluation du magasin.
     * @param storeId L'identifiant du magasin.
     */
    private void saveRatingToFirebase(float rating, String storeId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("ratings").child(userId).child(storeId).setValue(rating);
    }
}
