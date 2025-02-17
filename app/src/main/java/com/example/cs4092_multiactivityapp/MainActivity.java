package com.example.cs4092_multiactivityapp;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecycleViewInterface {
    RecyclerView list = null;
    CatRecyclerAdapter adapter = null;
    List<Cat> catList;
    List<Cat> filteredCatList;
    ArrayList<Cat> afterClean;
    EditText searchEditText;
    JSONCats jsonCats = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //wire
        list = findViewById(R.id.recyclerView);
        searchEditText = findViewById(R.id.searchCat);


        // load the data from JsonCats
        jsonCats = new JSONCats(this);
        catList = jsonCats.getCatDataList();

        Log.d("MainActivity", "Cat list size: " + catList.size());


        filteredCatList = new ArrayList<>(catList);  // 复制 catList
        afterClean = new ArrayList<>(catList);

        // set the recycler view adapter
        adapter = new CatRecyclerAdapter(this, R.layout.row_layout, filteredCatList, this);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);


        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH || event != null && event.getKeyCode() == android.view.KeyEvent.KEYCODE_ENTER && event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
                // use the filter method
                filterCats(searchEditText.getText().toString());
                return true;
            }
            return false;
        });


        // click the search line and clean the hint information just once
        searchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            boolean isCleared = false;
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && !isCleared) {
                    searchEditText.setText("");
                    isCleared = true;
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT);
                }
            }

        });
        
        // listen to the text change
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterCats(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    // filter the cats
    @SuppressLint("NotifyDataSetChanged")
    private void filterCats(String query) {
        filteredCatList.clear();

        if (query.equalsIgnoreCase("starred")) {
            Log.d("filterCat2","the input is starred");
            for (Cat cat : catList) {
                Log.d("filterCat2","isStarred is" + cat.isStarred());
                if (cat.isStarred()) {  // only add starred cats
                    filteredCatList.add(cat);
                }
            }
        } else if (query.isEmpty()) {
            filteredCatList.addAll(catList);
            Log.d("filterCat","the search is empty and show all the cats");
        } else {
            for (Cat cat : catList) {
                if (cat.getBreeds() != null && !cat.getBreeds().isEmpty()) {
                    String breedName = cat.getBreeds().get(0).getName();
                    Log.d("filterCat", "Checking cat: " + breedName);
                    if (breedName.toLowerCase().contains(query.toLowerCase())) {
                        filteredCatList.add(cat);
                    }else {
                        Log.d("filterCat", "Cat has no breed information: " + cat.getId());
                    }
                }
            }
        }
        Log.d("filterCat", "Filtered list size: " + filteredCatList.size());
        adapter.notifyDataSetChanged();
        Log.d("filterCat", "Adapter notified with new data.");


    }


    @Override
    public void onItemClick(int position) {
        // insure that the position is available
        if (position < 0 || position >= filteredCatList.size()) {
            Toast.makeText(this, "Invalid cat selected.", Toast.LENGTH_SHORT).show();
            return;
        }

        // create the intent and bundle
        Intent intent = new Intent(MainActivity.this, CatActivity.class);
        Bundle bundle = new Bundle();

        // click and show the cat's content
        Cat selectedCat = filteredCatList.get(position);
        if (selectedCat != null && selectedCat.getBreeds() != null && !selectedCat.getBreeds().isEmpty()) {
            String breedName = selectedCat.getBreeds().get(0).getName();
            Toast.makeText(this, "Selected Cat: " + breedName, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Selected Cat has no breed information.", Toast.LENGTH_SHORT).show();
        }



        bundle.putSerializable("cat", selectedCat);

        intent.putExtras(bundle);


        startActivity(intent);
    }
}