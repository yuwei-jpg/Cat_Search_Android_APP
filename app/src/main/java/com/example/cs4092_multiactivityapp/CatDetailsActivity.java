package com.example.cs4092_multiactivityapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CatDetailsActivity extends ComponentActivity {

    TextView origin,height,width,life,tem =null;
    Button webBT = null;
    ImageButton backBT = null;
    ImageView backimageView = null;
    FloatingActionButton floatingActionButton =null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_catdetails);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // wire
        origin = findViewById(R.id.originTextView);
        height =findViewById(R.id.heightTextView);
        width = findViewById(R.id.widthTextView);
        life = findViewById(R.id.lifeTextView);
        tem = findViewById(R.id.temTextView);
        webBT =findViewById(R.id.webButton);
        backimageView = findViewById(R.id.backgroundImage);
        backBT =findViewById(R.id.backButton1);
        floatingActionButton =findViewById(R.id.floatingActionButton);



        // pass the intent
        Cat selectedCat = (Cat) getIntent().getSerializableExtra("cat");

        if (selectedCat != null && selectedCat.getBreeds() != null && !selectedCat.getBreeds().isEmpty()) {
            Cat.Breed breed = selectedCat.getBreeds().get(0);  // get the breed information


            origin.setText(breed.getOrigin());
            life.setText(breed.getLife_span());
            height.setText(String.valueOf(selectedCat.getHeight()));
            width.setText(String.valueOf(selectedCat.getWidth()));
            tem.setText(breed.getTemperament());

            webBT.setOnClickListener(view -> {
                Intent intent = new Intent(CatDetailsActivity.this, CatWebActivity.class);
                Bundle bundle = new Bundle();

                // put the data into the bundle and the bundle into te intent
                bundle.putSerializable("cat", selectedCat);
                intent.putExtras(bundle);

                // start activity
                startActivity(intent);
            });
            // back to the previous page
            backBT.setOnClickListener(v -> finish());

            // Use Glide to load the picture
            Glide.with(this)
                    .load(selectedCat.getUrl())
                    .into(backimageView);

        }

        floatingActionButton.setOnClickListener(view -> {
            // Create a dialog box
            AlertDialog.Builder builder = new AlertDialog.Builder(CatDetailsActivity.this);
            builder.setTitle("Add Notes about your CAT");

            // Set the input (multi-line)
            final EditText input = new EditText(CatDetailsActivity.this);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);  // Enable multi-line input
            input.setLines(5);  // Set the number of visible lines
            input.setGravity(Gravity.START | Gravity.TOP);  // Start text from top left

            // Apply custom background with rounded corners and padding
            input.setBackgroundResource(R.drawable.rounded_edittext); // Apply the rounded background

            // Customize text appearance
            input.setTextColor(Color.parseColor("#000000")); // Set text color
            input.setHintTextColor(Color.parseColor("#808080")); // Set hint text color
            input.setPadding(24, 24, 24, 24); // Add padding for better spacing

//             Get the selected cat from the intent
            String catKey = selectedCat.getId();  // Use the cat's ID or any unique identifier (like name) as the key



            // Set margins for the EditText
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(16, 16, 16, 16);  // Set margins around the input
            input.setLayoutParams(params);

            // Retrieve previously saved note from SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("CatNotes", MODE_PRIVATE);
            String savedNote = sharedPreferences.getString(catKey, "");
            input.setText(savedNote);  // Pre-fill the EditText with saved note if exists

            builder.setView(input);

            // Set the Save button
            builder.setPositiveButton("Save", (dialog, which) -> {
                String note = input.getText().toString();

                // Save the note to SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(catKey, note);  // Save the note under the key "catNote"
                editor.apply();  // Commit the changes

                Toast.makeText(CatDetailsActivity.this, "Note Saved: " + note, Toast.LENGTH_SHORT).show();
            });

            // Set the Cancel button
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            // Show the dialog
            builder.show();
        });


    }
}
