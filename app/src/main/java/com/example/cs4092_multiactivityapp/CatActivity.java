package com.example.cs4092_multiactivityapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class CatActivity extends ComponentActivity {
    TextView catName,catDescription = null;
    Button bt =null;
    ImageView catImage =null;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LinearLayout layout = findViewById(R.id.main);
        layout.setAlpha(0f);
        layout.animate().alpha(1f).setDuration(1000).setListener(null);

        // wire
        catName = findViewById(R.id.catName);
        catDescription = findViewById(R.id.catDescription);
        bt = findViewById(R.id.button);
        catImage = findViewById(R.id.imageView);

        // the initial state is invisible
        catName.setAlpha(0f);
        catImage.setAlpha(0f);
        catDescription.setAlpha(0f);

        // set animation
        catName.animate().alpha(1f).setDuration(2000).setListener(null);
        catImage.animate().alpha(1f).setDuration(5000).setListener(null);
        catDescription.animate().alpha(1f).setDuration(7000).setListener(null);



        Cat selectedCat = (Cat) getIntent().getSerializableExtra("cat");

        if (selectedCat != null) {
            // Set cat's name and description
            if (selectedCat.getBreeds() != null && !selectedCat.getBreeds().isEmpty()) {
                catName.setText(selectedCat.getBreeds().get(0).getName());
                catDescription.setText(selectedCat.getBreeds().get(0).getDescription());
            }else {
                catName.setText("No breed information");
                catDescription.setText("No description available.");
            }


            // Use Glide to load the cat image
            Glide.with(this)
                    .load(selectedCat.getUrl())
                    .circleCrop()
                    .into(catImage);
        }else {
            catName.setText("No Cat Selected");
            catDescription.setText("No description available.");
        }



        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatActivity.this, CatDetailsActivity.class);
                Bundle bundle = new Bundle();

                // put the data into the bundle and the bundle into te intent
                bundle.putSerializable("cat", selectedCat);
                intent.putExtras(bundle);

                // start activity
                startActivity(intent);
            }
        });



    }


}
