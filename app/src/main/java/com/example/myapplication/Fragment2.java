package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class Fragment2 extends Fragment {

    private List<Items> productList;
    private ProductAdapter productAdapter;

    public Fragment2() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);

        // Initialize the ListView
        ListView listView = view.findViewById(R.id.listView);

        // Initialize product list and adapter
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(requireContext(), productList);

        listView.setAdapter(productAdapter);

        // Load category 2 data
        loadCategory2Data();

        return view;
    }

    private void loadCategory2Data() {
        // Fetch category 2 items using the Items class
        List<Items> category2Items = Items.getItemsByCategory(2); // Get items from category 2

        // Add items to the productList
        productList.clear(); // Clear the existing data (if any)
        productList.addAll(category2Items);

        // Notify the adapter that the data has changed
        productAdapter.notifyDataSetChanged();
    }
}
