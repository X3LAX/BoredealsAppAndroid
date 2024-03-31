package filrouge.groupei.boredealsappandroid;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
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

        Store store = getIntent().getParcelableExtra("selectedStore");

        TextView textRecommendation = findViewById(R.id.textRecommendation);
        textRecommendation.setText(store.getName());

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

        TextView textCopier = findViewById(R.id.textCopier);

        // Ajoutez un OnClickListener au TextView "Code Promo"
        textCopier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenez le texte du TextView
                String promoCode = textCopier.getText().toString();

                // Copiez le texte dans le presse-papiers
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Code Promo", promoCode);
                clipboardManager.setPrimaryClip(clipData);

                // Affichez un message de succès
                Toast.makeText(CodeActivity.this, "Code Promo copié dans le presse-papiers", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
