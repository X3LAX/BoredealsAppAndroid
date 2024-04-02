package filrouge.groupei.boredealsappandroid;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class CodeActivity extends AppCompatActivity {

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

        //String logoUrl = "https://github.com/X3LAX/BoredealsAppAndroidRessources/blob/main/logos/apple_logo.png?raw=true";//+ store.getName() + "_logo.png?raw=true";

        String baseUrl = "https://github.com/X3LAX/BoredealsAppAndroidRessources/blob/main/logos/";
        String logoUrl = baseUrl + store.getName().toLowerCase() + "_logo.png?raw=true";

        //Log.d("Store Name", store.getName());

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
                    // You can do something here while the image is being loaded
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

        // Définir l'échelle initiale
        float initialScale = 1f;

        // Définir l'échelle après l'animation
        float finalScale = 1.6f;

        // Créer un ObjectAnimator pour animer l'échelle en X
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(
                cardPromoCode, // Vue à animer
                View.SCALE_X, // Propriété à animer
                initialScale, // Échelle initiale
                finalScale // Échelle finale
        );

        // Créer un ObjectAnimator pour animer l'échelle en Y
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(
                cardPromoCode, // Vue à animer
                View.SCALE_Y, // Propriété à animer
                initialScale, // Échelle initiale
                finalScale // Échelle finale
        );

        // Combinez les deux animations dans un AnimatorSet
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);

        // Définir la durée de l'animation (en millisecondes)
        animatorSet.setDuration(300);

        // Démarrer l'animation
        animatorSet.start();

        // Après l'animation, réinitialisez la vue à son échelle d'origine
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // Créer un ObjectAnimator pour réinitialiser l'échelle en X
                ObjectAnimator scaleXResetAnimator = ObjectAnimator.ofFloat(
                        cardPromoCode, // Vue à animer
                        View.SCALE_X, // Propriété à animer
                        finalScale, // Échelle finale
                        initialScale // Échelle initiale
                );

                // Créer un ObjectAnimator pour réinitialiser l'échelle en Y
                ObjectAnimator scaleYResetAnimator = ObjectAnimator.ofFloat(
                        cardPromoCode, // Vue à animer
                        View.SCALE_Y, // Propriété à animer
                        finalScale, // Échelle finale
                        initialScale // Échelle initiale
                );

                // Combinez les deux animations de réinitialisation dans un AnimatorSet
                AnimatorSet resetAnimatorSet = new AnimatorSet();
                resetAnimatorSet.playTogether(scaleXResetAnimator, scaleYResetAnimator);

                // Définir la durée de l'animation de réinitialisation (en millisecondes)
                resetAnimatorSet.setDuration(150);

                // Démarrer l'animation de réinitialisation après un court délai
                resetAnimatorSet.setStartDelay(200); // Délai de 1000 millisecondes (1 seconde)
                resetAnimatorSet.start();
            }
        });

        TextView textPromoCode = findViewById(R.id.textPromoCode);
        TextView textCopier = findViewById(R.id.textCopier);

        // Écouteur d'événements de clic pour textCopier
        textCopier.setOnClickListener(v -> {
            // Copy the promocode to the clipboard
            String codePromo = store.getPromocode();
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("Promocode", codePromo);
            clipboardManager.setPrimaryClip(clipData);

            // Confirm copying to the user
            Toast.makeText(CodeActivity.this, "Code promo copié: " + codePromo, Toast.LENGTH_SHORT).show();

            // Open the store link in a web browser
            String link = store.getLink();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            startActivity(browserIntent);
        });


        // Animation pour le cardPromoCode
        animateCard(cardPromoCode);

        // Écouteur d'événements de clic pour textCopier

    }

    private void animateCard(CardView cardView) {
        // Animation pour animer l'échelle du cardView
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(cardView, View.SCALE_X, 0.8f, 1.0f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(cardView, View.SCALE_Y, 0.8f, 1.0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.setDuration(500);
        animatorSet.start();
    }

    private void explodeText(TextView textView) {
        // Animation pour faire éclater le texte
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(textView, View.SCALE_X, 1.0f, 1.5f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(textView, View.SCALE_Y, 1.0f, 1.5f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.setDuration(600);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // Rétablir l'état initial du TextView après l'animation
                textView.setScaleX(1.0f);
                textView.setScaleY(1.0f);
            }
        });
        animatorSet.start();
    }
}

