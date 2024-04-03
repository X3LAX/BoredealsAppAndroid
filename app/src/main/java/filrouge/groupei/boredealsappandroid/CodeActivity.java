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
import android.graphics.drawable.Drawable;
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

public class CodeActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private String userId; // Assuming userId is available in your app

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        Store store = getIntent().getParcelableExtra("selectedStore");
        if (store == null) return;

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

        Button buttonGenerateQRCode = findViewById(R.id.boutonQrCode);
        buttonGenerateQRCode.setOnClickListener(v -> showQRCodeDialog(qrCodeBitmap));

        // Récupérer l'ID utilisateur (à adapter à votre logique)
        userId = getUserId(); // Méthode à implémenter

        // Initialiser la référence de la base de données
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Récupérer et afficher la note sauvegardée localement
        float savedRating = getSavedRatingFromPreferences(String.valueOf(store.getId()));
        ratingBar.setRating(savedRating);

        // Enregistrer la note lorsqu'elle change et afficher un message Toast
        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
            saveRatingToFirebase(rating, String.valueOf(store.getId()));
            saveRatingLocally(rating, String.valueOf(store.getId()));
            Toast.makeText(CodeActivity.this, "Évaluation enregistrée avec succès", Toast.LENGTH_SHORT).show();
        });
    }

    // Méthode pour récupérer l'ID utilisateur (à adapter à votre logique)
    private String getUserId() {
        // Implémentez ici la logique pour récupérer l'ID utilisateur
        return "user123"; // Exemple statique, remplacez-le par votre propre logique
    }

    private float getSavedRatingFromPreferences(String storeId) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(storeId, 0.0f);
    }


    private void saveRatingLocally(float rating, String storeId) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(storeId, rating);
        editor.apply();
    }

    private void saveRatingToFirebase(float rating, String storeId) {
        databaseReference.child("ratings").child(userId).child(storeId).setValue(rating);
    }

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

    private void animateCard(CardView cardView) {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(cardView, View.SCALE_X, 0.8f, 1.0f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(cardView, View.SCALE_Y, 0.8f, 1.0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.setDuration(500);
        animatorSet.start();
    }
}