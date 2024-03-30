package filrouge.groupei.boredealsappandroid;

import android.media.MediaPlayer;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;


public class Store extends AppCompatActivity {
        private String name;
        private String description;
        private int discountPercentage;
        private ImageButton imageButton;
        private MediaPlayer mediaPlayer;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.item_store);
        }

        public Store(String name, String description, int discountPercentage) {
            this.name = name;
            this.description = description;
            this.discountPercentage = discountPercentage;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public int getDiscountPercentage() {
            return discountPercentage;
        }
}

