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

/**
 *  This activity appears after the user has signed in got to the GetLocation activity and pressed
 *  the button to see the items in the users cart.
 */
public class ItemsActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextView mItems;

    //  If the user gets to this activity the application will show what is in the users cart or write it's empty.
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
                if (dataSnapshot.exists()) {            //  if there are items in the cart.
                    int i = 1;
                    long size = dataSnapshot.getChildrenCount();

                    mItems.append("\n\n");

                    //  Prints the list of items and there prices from the users cart.
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
                    //  Prints that there is nothing in the cart and shows a toast that says the same.
                    Toast.makeText(ItemsActivity.this, "No items on the list!",
                            Toast.LENGTH_LONG).show();

                    mItems.append("\n\n\n\tYou have no items!");

                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //  Pass. There is no option to cancel.
            }
        });

    }

}
