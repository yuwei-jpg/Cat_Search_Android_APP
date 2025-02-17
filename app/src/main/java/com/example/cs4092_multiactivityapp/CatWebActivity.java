package com.example.cs4092_multiactivityapp;
import android.os.Bundle;
import android.webkit.WebView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class CatWebActivity extends AppCompatActivity {
    WebView webView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_web);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // wire
        webView = findViewById(R.id.webView);


        // pass the intent
        Cat selectedCat = (Cat) getIntent().getSerializableExtra("cat");

        if (selectedCat != null && selectedCat.getBreeds() != null && !selectedCat.getBreeds().isEmpty()) {
            String breedUrl = selectedCat.getBreeds().get(0).getVetstreet_url();
            if (breedUrl != null) {
                webView.loadUrl(breedUrl);
            } else {

                webView.loadData("<html><body>No URL available for this breed.</body></html>", "text/html", "UTF-8");
            }

        }
    }
}
