package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {

    private List<Items> addItem;   // Full product list
    private List<Items> filteredList;  // Filtered list based on search query
    private ProductAdapter productAdapter;
    private ListView listView;
    private TextView noResultsTextView;  // TextView to show when no results are found
    private TextView initialProductText;  // TextView to show "Type a Product"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        // Set up the back button functionality
        ImageView btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to the home activity
                startActivity(new Intent(Search.this, HomeActivity.class));
                finish();
            }
        });

        // Initialize the product list and filtered list
        addItem = new ArrayList<>();
        filteredList = new ArrayList<>();

        // Assuming the productList is populated with real data (from an API or database)
        // Example: hardcoded data
        hardcodeData();

        // Initialize the ListView and adapter for product listing
        listView = findViewById(R.id.recyclerView);
        productAdapter = new ProductAdapter(this, filteredList);
        listView.setAdapter(productAdapter);

        // TextView to show if no results are found
        noResultsTextView = findViewById(R.id.noResultsTextView);  // Make sure to add this TextView in your layout XML

        // TextView to show "Type a Product"
        initialProductText = findViewById(R.id.initialProductText);

        // Set up search functionality using SearchView widget
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Optional: Handle when the search query is submitted
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Hide "Type a Product" when the user starts typing
                if (newText.isEmpty()) {
                    initialProductText.setVisibility(View.VISIBLE);  // Show "Type a Product"
                } else {
                    initialProductText.setVisibility(View.GONE);  // Hide "Type a Product"
                }

                // Filter the product list based on the query text
                filterProducts(newText);
                return false;
            }
        });
    }

    private void hardcodeData() {


        // Adding items to the list
        addItem.add(new Items("Bazooka", "Tender, spicy chicken strips paired with mayo, pickles, jalapeños, and signature stacker sauce, all wrapped in a warm tortilla.", "from Rs. 630.00", R.drawable.bazooka, 1));
        addItem.add(new Items("Cheezy Sticks", "Freshly baked bread filled with the yummiest Cheese blend to satisfy your cravings.", "Rs. 630", R.drawable.cheezysticks , 2 ));

        addItem.add(new Items("Oven Baked Wings", "Fresh Oven baked wings served with Dip Sauce.", "from Rs. 600", R.drawable.ovenbakedwings , 2 ));

        addItem.add(new Items("Flaming Wings", "Fresh oven baked wings tossed in hot Peri Peri Sauce and served with Dip Sauce.", "from Rs. 650",R.drawable.flamingwings , 2 ));
///////////////////


        addItem.add(new Items("Chicken Tikka", "Tender chunks of Marinated Grilled Chicken with Savory Onion.", "from Rs. 690", R.drawable.chickentikka , 3 ));


        addItem.add(new Items("Chicken Fajita", "An authentic taste of fajita marinated Chicken, Onion and Bell Peppers.", "from Rs. 690", R.drawable.chickenfajita , 3 ));

        addItem.add(new Items("Chicken Lover", "Extreme quantity of Chicken and Onion with rich Mozzarella Cheese.", "from Rs. 690", R.drawable.chickenlover , 3 ));
//////////////////


        addItem.add(new Items("Euro", "A delightful combination of specially Marinated Smoked Chicken Bell Pepper, Mushrooms with Tomato Sauce", "from Rs. 690", R.drawable.euro , 4 ));

        addItem.add(new Items("Chicken Supreme", "A combination of 3 Flavors of Chicken, Black Olives, Mushrooms, Bell Pepper and Onion with Tomato Sauce.", "from Rs. 690", R.drawable.chickensupreme , 4 ));

        addItem.add(new Items("Black Pepper Tikka", "A Blend of Marinated Black Pepper Chicken, Onion and Bell Pepper.", "from Rs. 690", R.drawable.blackpeppertikka , 4 ));
//////////


        addItem.add(new Items("Cheezious Special", "Delicious Special Chicken with Black Olives, Sausages and Bell Pepper.", "from Rs. 1,550", R.drawable.cheeziousspecial , 5 ));

        addItem.add(new Items("Behari Kebab", "Enjoy Special Chicken Behari Kabab, Grilled Chicken with Onion Jalapenos and Ginger Garnishing", "from Rs. 1,550", R.drawable.beharikebab , 5 ));

        addItem.add(new Items("Chicken Extreme", "Combination of 3 Flavors of Chicken with Onion Bell Pepper, Green Olives, Mushrooms and Special Sauce.", "from Rs. 1,550", R.drawable.chickenextreme , 5 ));

///////

        addItem.add(new Items("Small Pizza Deal", "Any Flavor from Local Love Or Over the Sea Flavor Category & 1 Soft Drink.", "from Rs. 750", R.drawable.smallpizzadeal , 6 ));

        addItem.add(new Items("Regular Pizza Deal", "1 Regular Pizza and 2 Regular Drinks.", "from Rs. 1,450", R.drawable.regularpizzadeal , 6 ));

        addItem.add(new Items("Large Pizza Deal", "Any Flavor from Local Love or Over the Sea Flavor Category & 1 Liter Drink.", "from Rs. 1,990", R.drawable.largepizzadeal , 6 ));

///////////////

        addItem.add(new Items("Special Roasted Platter", "4 pcs Behari Rolls, 6 pcs Wings Served with Fries & Sauce", "Rs 1,200",  R.drawable.specialroastedplatter , 7 ));

        addItem.add(new Items("Mexican Sandwich", "Mozzarella Dipped Chicken Topped with Garlic Sauce and Tomatoes Served in Baked Bread.", "Rs 920", R.drawable.mexicansandwich , 7 ));

        addItem.add(new Items("Pizza Stacker", "A unique blend of Delicious Sauce, Crispy Chicken and Pizza Crust.", "Rs 920", R.drawable.pizzastacker , 7 ));
///////////



        addItem.add(new Items("Crown Crust", "Scrumptious Pizza with a yummy blend of Grilled Chicken, Olives, Onion, Capsicum and Special Sauce.", "from Rs 1,550", R.drawable.crowncrust, 8 ));

        addItem.add(new Items("Stuff Crust Pizza", "Special Chicken, Green Olives, Mushrooms, with the Crust filled with Cheese Or Kabab.", "from Rs 1,600", R.drawable.stuffcrustpizza , 8));
/////////////



        addItem.add(new Items("Somewhat Amazing 1", "2 Bazinga, Regular Fries, 2 Regular Drinks", "from Rs 1,250", R.drawable.somewhatamazing1 , 9 ));


        addItem.add(new Items("Somewhat Amazing 2", "2 Bazinga Burger, 2 pcs Chicken, Large Fries, 2 Regular Drink", "from Rs 1,750", R.drawable.somewhatamazing2 , 9 ));


        addItem.add(new Items("Somewhat Amazing 3", "3 Bazinga Burger, Large Fries, 1 Liter Drink", "from Rs 1,890", R.drawable.somewhatamazing3, 9 ));
/////////////


        addItem.add(new Items("Fettuccine Alfredo Pasta", "Pasta made in the yummiest White Sauce With Chicken Chunks Topped with Cheese.", "Rs 1,050", R.drawable.fettuccinealfredopasta , 10 ));


        addItem.add(new Items("Crunchy Chicken Pasta", "Yummiest Macaroni Pasta in White Sauce topped with Crispy Chicken and Cheese.", "Rs 950", R.drawable.crunchychickenpasta , 10 ));
///////////////////



        addItem.add(new Items("Reggy Burger", "Perfectly Fried Chicken Patty with Fresh Lettuce and Sauce in a Sesame Seed Bun", "from Rs 390", R.drawable.reggyburger,  11 ));


        addItem.add(new Items("Bazinga Burger", "Crispy fried to perfection boneless thigh with signature sauce and lettuce Held in Corn-Dusted Bun", "from Rs 560", R.drawable.bazingaburger  ,  11 ));
/////////////////


        addItem.add(new Items("Fries", " ", "from Rs 220", R.drawable.fries , 12 ));


        addItem.add(new Items("Nuggets", "5 pcs", "from Rs 450", R.drawable.nuggets , 12 ));


        addItem.add(new Items("Chicken Piece", "Crispy Piece", "from Rs 300", R.drawable.chickenpiece , 12 ));
///////////////////



        addItem.add(new Items("Juice", " ", "Rs 60", R.drawable.juice , 13 ));


        addItem.add(new Items("Mayo Dip", " ", "Rs 80",R.drawable.mayodip, 13 ));


        addItem.add(new Items("Water Small", " ", "Rs 60", R.drawable.watersmall  , 1));
        filteredList.clear();
    }

    private void filterProducts(String query) {
        // Clear the filtered list to avoid old results
        filteredList.clear();

        // If the query is empty, hide the ListView and show the "No results found" message
        if (query.isEmpty()) {
            filteredList.clear();
            listView.setVisibility(View.GONE);  // Hide ListView
            noResultsTextView.setVisibility(View.GONE);  // Hide "No Results" message
        } else {
            // Perform partial match search on product name and description
            for (Items item : addItem) {
                // Case-insensitive search for partial matches in name or description
                if (item.getName().toLowerCase().contains(query.toLowerCase()) ||
                        item.getDescription().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(item);
                }
            }

            // If filtered list is empty, show the "No results found" message
            if (filteredList.isEmpty()) {
                listView.setVisibility(View.GONE);  // Hide ListView
                noResultsTextView.setVisibility(View.VISIBLE);  // Show "No Results" message
            } else {
                listView.setVisibility(View.VISIBLE);  // Show ListView
                noResultsTextView.setVisibility(View.GONE);  // Hide "No Results" message
            }
        }

        // Notify the adapter that the data has changed
        productAdapter.notifyDataSetChanged();
    }
}
