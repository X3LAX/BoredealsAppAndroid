package filrouge.groupei.boredealsappandroid;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class CodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        // Utilisation de la classe store parcelée pour afficher les informations dans l'activité CodeActivity
        Store store = getIntent().getParcelableExtra("selectedStore");

        TextView textRecommendation = findViewById(R.id.textRecommendation);
        textRecommendation.setText(store.getName());

        TextView textDiscount = findViewById(R.id.textDiscount);
        textDiscount.setText(store.getDescription());

        int logoResourceId = getResources().getIdentifier(store.getName().toLowerCase() + "_logo", "drawable", getPackageName());

        if (logoResourceId != 0) {
            ImageView imageLogo = findViewById(R.id.imageLogo);
            imageLogo.setImageResource(logoResourceId);
        } else {
            Log.e("CodeActivity", "Logo introuvable pour le magasin : " + store.getName());
        }


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
        textCopier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenir le texte de textPromoCode
                String codePromo = textPromoCode.getText().toString();

                // Copier le texte dans le presse-papiers
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Code promo", codePromo);
                clipboardManager.setPrimaryClip(clipData);

                // Afficher un message de confirmation
                Toast.makeText(CodeActivity.this, "Code promo copié dans le presse-papiers", Toast.LENGTH_SHORT).show();
            }
        });

        // Animation pour le cardPromoCode
        animateCard(cardPromoCode);

        // Écouteur d'événements de clic pour textCopier
        textCopier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Animation pour faire éclater le textCopier
                explodeText(textCopier);

                // Obtenir le texte de textPromoCode
                String codePromo = textPromoCode.getText().toString();

                // Copier le texte dans le presse-papiers
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Code promo", codePromo);
                clipboardManager.setPrimaryClip(clipData);

                // Afficher un message de confirmation
                Toast.makeText(CodeActivity.this, "Code promo copié dans le presse-papiers", Toast.LENGTH_SHORT).show();
            }
        });
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

