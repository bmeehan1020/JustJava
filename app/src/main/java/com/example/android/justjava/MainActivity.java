package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 1;
    int priceCoffee = 5;
    int priceWhippedCream = 1;
    int priceChocolate = 2;

    public void increment(View view) {
        if (quantity ==  100){
            Toast.makeText(this, "You can't order more than 100 coffees", Toast.LENGTH_SHORT).show();
        }
        else if (quantity < 100) {
            quantity++;
            displayQuantity(quantity);
        }

    }

    public void decrement(View view) {
        if (quantity == 1){
            Toast.makeText(this, "You can't order less than 1 coffee", Toast.LENGTH_SHORT).show();
        }
        else if(quantity > 1) {
            quantity--;
            displayQuantity(quantity);
        }
    }

    /**
     *
     * @return price of order
     */

    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate){
        int pricePerCup = priceCoffee;
        if(hasWhippedCream){
            pricePerCup += priceWhippedCream;
        }
        if (hasChocolate){
            pricePerCup += priceChocolate;
        }
        return pricePerCup * quantity;
    }

    /**
     * creates summary of the order
     *
     * @param price of the order
     * @param hasWhippedCream if whipped cream box checked
     * @return string summary
     */

    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name){
        String message = "Name: " + name;
        message += "\nAdd whipped cream? " + hasWhippedCream;
        message += "\nAdd chocolate? " + hasChocolate;
        message += "\nQuantity: " + quantity;
        message += "\nTotal: $" + price;
        message += "\nThank You!";

        return message;
    }

    public void submitOrder(View view) {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        Boolean hasWhippedCream = whippedCream.isChecked();

        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_check_box);
        Boolean hasChocolate = chocolate.isChecked();

        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        String name = nameEditText.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String sendMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        Intent sendIntent= new Intent();
        sendIntent.setData(Uri.parse("mailto"));
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + name);
        sendIntent.putExtra(Intent.EXTRA_TEXT, sendMessage);
        sendIntent.setType("text/plain");

        if(sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(sendIntent);
        }
    }

    private void displayQuantity(int quantity) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }

    /*
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
    */
}
