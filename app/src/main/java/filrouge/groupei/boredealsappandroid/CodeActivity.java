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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DatabaseReference;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import android.os.Bundle;
import android.widget.RatingBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CodeActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        // Utilisation de la classe store parcelée pour afficher les informations dans l'activité CodeActivity
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
            .into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    imageLogo.setImageBitmap(bitmap);
                    Log.d("Picasso", "Image loaded successfully");
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    imageLogo.setImageDrawable(errorDrawable);
                    Log.e("Picasso", "Error loading image", e);
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            });


        RatingBar ratingBar = findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(CodeActivity.this, String.valueOf(rating), Toast.LENGTH_SHORT).show();
            }
        });



        CardView cardPromoCode = findViewById(R.id.cardPromoCode);

        float initialScale = 1f;

        float finalScale = 1.6f;

        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(
                cardPromoCode,
                View.SCALE_X,
                initialScale,
                finalScale
        );

        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(
                cardPromoCode,
                View.SCALE_Y,
                initialScale,
                finalScale
        );

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);

        animatorSet.setDuration(300);

        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ObjectAnimator scaleXResetAnimator = ObjectAnimator.ofFloat(
                        cardPromoCode,
                        View.SCALE_X,
                        finalScale,
                        initialScale
                );

                ObjectAnimator scaleYResetAnimator = ObjectAnimator.ofFloat(
                        cardPromoCode,
                        View.SCALE_Y,
                        finalScale,
                        initialScale
                );

                AnimatorSet resetAnimatorSet = new AnimatorSet();
                resetAnimatorSet.playTogether(scaleXResetAnimator, scaleYResetAnimator);

                resetAnimatorSet.setDuration(300);

                resetAnimatorSet.setStartDelay(300);
                resetAnimatorSet.start();
            }
        });

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


        animateCard(cardPromoCode);

        Button buttonGenerateQRCode = findViewById(R.id.boutonQrCode);
        buttonGenerateQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQRCodeDialog(qrCodeBitmap);
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference().child("ratings");



        if (store == null) return;

        float savedRating = getSavedRatingFromPreferences();

        ratingBar.setRating(savedRating);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                saveRatingToFirebase(rating);

                saveRatingLocally(rating);

                Toast.makeText(CodeActivity.this, "Évaluation enregistrée avec succès", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveRatingLocally(float rating) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("rating", rating);
        editor.apply();
    }

    private void saveRatingToFirebase(float rating) {
        databaseReference.push().setValue(rating);
    }

    private float getSavedRatingFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getFloat("rating", 0.0f);
    }

    private void animateCard(CardView cardView) {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(cardView, View.SCALE_X, 0.8f, 1.0f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(cardView, View.SCALE_Y, 0.8f, 1.0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.setDuration(500);
        animatorSet.start();
    }

    private void explodeText(TextView textView) {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(textView, View.SCALE_X, 1.0f, 1.5f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(textView, View.SCALE_Y, 1.0f, 1.5f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.setDuration(600);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                textView.setScaleX(1.0f);
                textView.setScaleY(1.0f);
            }
        });
        animatorSet.start();
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
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}



