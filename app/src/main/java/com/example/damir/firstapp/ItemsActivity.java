package com.example.damir.firstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ItemsActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextView mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mItems = (TextView) findViewById(R.id.item_list);

        Intent Previous = getIntent();
        String userName = Previous.getStringExtra("name");

        mDatabase.child("users").child(userName).child("items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int i = 1;
                    long size = dataSnapshot.getChildrenCount();

                    mItems.append("\n\n");


                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        if(i == size) break;
                        String name = (String) postSnapshot.child("name").getValue();
                        String price = (String) postSnapshot.child("price").getValue();

                        mItems.append("\n" + i + ") Name: " + name + "  |  Price:" + price + "$");

                        i++;

                    }



                    return;

                }
                else {
                    Toast.makeText(ItemsActivity.this, "No items on the list!",
                            Toast.LENGTH_LONG).show();

                    mItems.append("\n\n\n\tYou have no items!");

                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }







}
