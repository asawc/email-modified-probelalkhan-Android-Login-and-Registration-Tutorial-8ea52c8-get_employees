package myapp;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.simplifiedcoding.simplifiedcoding.R;
import net.simplifiedcoding.simplifiedcoding.SharedPrefManager;

//import androidx.appcompat.app.AppCompatActivity;

public class ProductInfoActivity extends AppCompatActivity {

    TextView textViewQuantity, textViewProductname, textViewProductsymbol;
    private Button buttonScan2 /*, buttonAddProductActivity*/;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        //buttonScan2 = (Button) findViewById(R.id.buttonScan2);
        //buttonAddProductActivity = (Button) findViewById(R.id.buttonAddProductActivity);
        //if the user is not logged in
        //starting the login activity
        /*if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }*/


        textViewQuantity = (TextView) findViewById(R.id.textViewQuantity);
        textViewProductname = (TextView) findViewById(R.id.textViewProductname);
        textViewProductsymbol = (TextView) findViewById(R.id.textViewProductsymbol);


        //getting the current product
        Product product = SharedPrefManager.getInstance(this).getProduct();

        //setting the values to the textviews
        textViewQuantity.setText(String.valueOf(product.getQuantity()));
        textViewProductname.setText(product.getProductname());
        textViewProductsymbol.setText(product.getProductsymbol());

        //when the user presses logout button
        //calling the logout method
       findViewById(R.id.buttonLogout3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });

        //when the user presses scan button
        //calling the ScanActivity
        findViewById(R.id.buttonScan2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                openScanActivity();
            }
        });

        /*findViewById(R.id.buttonAddProductActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                openAddProductActivity();
            }
        });*/
    }

    public void openScanActivity() {
        Intent intent = new Intent(this, ScanActivity.class);
        startActivity(intent);
    }

    /*public void openAddProductActivity() {
        Intent intent = new Intent(this, AddProductActivity.class);
        startActivity(intent);
    }*/
}
