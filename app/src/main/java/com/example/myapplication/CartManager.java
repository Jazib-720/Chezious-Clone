package com.example.myapplication;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CartManager {
    private FirebaseDatabase database;
    private DatabaseReference cartRef;

    public CartManager() {
        // Initialize Firebase Database reference
        database = FirebaseDatabase.getInstance();
    }

    // Save cart items for a specific user
    public void saveCartItems(String userId, List<CartItem> cartItems) {
        cartRef = database.getReference("carts").child(userId); // "carts" is the parent node for user carts

        // Store the list of cart items under the user's ID
        cartRef.setValue(cartItems);
    }

    // Add a single item to the user's cart
    public void addToCart(String userId, CartItem newItem) {
        cartRef = database.getReference("carts").child(userId); // Access user's cart

        // You can also use push() if you want to create a unique key for each item
        String itemId = cartRef.push().getKey();
        cartRef.child(itemId).setValue(newItem); // Add item to cart with a unique key
    }
}
