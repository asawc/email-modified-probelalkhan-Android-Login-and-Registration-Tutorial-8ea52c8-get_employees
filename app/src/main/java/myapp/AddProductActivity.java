package myapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import net.simplifiedcoding.simplifiedcoding.R;
import net.simplifiedcoding.simplifiedcoding.RequestHandler;
import net.simplifiedcoding.simplifiedcoding.SharedPrefManager;
import net.simplifiedcoding.simplifiedcoding.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AddProductActivity extends AppCompatActivity {

    EditText editTextProductName, editTextProductSymbol, editTextQuantity;
    private Button buttonScan3, buttonAddProduct, buttonLogout3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        buttonScan3 = (Button) findViewById(R.id.buttonScan3);
        buttonLogout3 = (Button) findViewById(R.id.buttonLogout4);
        buttonAddProduct = (Button) findViewById(R.id.buttonAddProduct);
        //if the user is not logged in
        //starting the login activity
        /*if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }*/


        editTextProductName = (EditText) findViewById(R.id.editTextProductName);
        editTextProductSymbol = (EditText) findViewById(R.id.editTextProductSymbol);
        editTextQuantity = (EditText) findViewById(R.id.editTextQuantity);


        findViewById(R.id.buttonAddProduct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on button add product
                //here we will add product to server database
                addProduct();
            }
        });

        //when the user presses scan button
        //calling the ScanActivity
        findViewById(R.id.buttonScan3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                openScanActivity();
            }
        });

        //when the user presses logout button
        //then user will be logout
        findViewById(R.id.buttonLogout4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });


    }

    public void openScanActivity() {
        Intent intent = new Intent(this, ScanActivity.class);
        startActivity(intent);
    }

    private void addProduct() {
        final String productname = editTextProductName.getText().toString().trim();
        final String productsymbol = editTextProductSymbol.getText().toString().trim();
        final String quantity = editTextQuantity.getText().toString().trim();

        //final String gender = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();

        //first we will do the validations

        if (TextUtils.isEmpty(productname)) {
            editTextProductName.setError("Please enter product name");
            editTextProductName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(productsymbol)) {
            editTextProductSymbol.setError("Please enter product symbol");
            editTextProductSymbol.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(quantity)) {
            editTextQuantity.setError("Enter a quantity of product");
            editTextQuantity.requestFocus();
            return;
        }

        //if it passes all the validations

        class AddProduct extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("productname", productname);
                params.put("productsymbol", productsymbol);
                params.put("quantity", quantity);
                //params.put("gender", gender);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_ADD_PRODUCT, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
                progressBar.setVisibility(View.GONE);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message2"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject productJson = obj.getJSONObject("product");

                        //creating a new user object
                        Product product = new Product(
                                productJson.getInt("productsymbol"),
                                productJson.getString("productname"),
                                productJson.getString("quantity")//,
                                //userJson.getString("gender")
                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).productLogin(product);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), ProductInfoActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        AddProduct ap = new AddProduct();
        ap.execute();
    }

}
