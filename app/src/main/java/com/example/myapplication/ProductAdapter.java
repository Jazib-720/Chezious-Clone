package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

// Adapter for the ListView
 class ProductAdapter extends ArrayAdapter<Items> {
    private Context context;
    private List<Items> products;

    public ProductAdapter(Context context, List<Items> products) {
        super(context, R.layout.list_item, products);
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }
        Items currentItem = products.get(position);
       Items currentProduct = products.get(position);

        ImageView productImage = convertView.findViewById(R.id.image_dish);
        TextView productName = convertView.findViewById(R.id.text_name);
        TextView productDescription = convertView.findViewById(R.id.text_desc);
        TextView productPrice = convertView.findViewById(R.id.item_price_txt);

        productName.setText(currentProduct.getName());
        productDescription.setText(currentProduct.getDescription());
        productPrice.setText(currentProduct.getPrice());
        Glide.with(context).load(currentItem.getImageResId()).into(productImage);


        return convertView;
    }
}