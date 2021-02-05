//package com.example.codescannerjava;
package myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.budiyev.android.codescanner.*;
import com.google.zxing.Result;

import net.simplifiedcoding.simplifiedcoding.R;
import net.simplifiedcoding.simplifiedcoding.RequestHandler;
import net.simplifiedcoding.simplifiedcoding.SharedPrefManager;
import net.simplifiedcoding.simplifiedcoding.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ScanActivity extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    private Button buttonCheck, buttonLogout2;
    //final String[] productsymbol_array = new String[1];
    public String productsymbol; // = productsymbol_array[0];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        buttonCheck = (Button) findViewById(R.id.buttonCheck);
        buttonLogout2 = (Button) findViewById(R.id.buttonLogout2);
        mCodeScanner = new CodeScanner(this, scannerView);

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(ScanActivity.this, result.getText(), Toast.LENGTH_SHORT); //.show();
                        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 330);
                        toast.show();
                        productsymbol = result.getText();
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
        findViewById(R.id.buttonCheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                //ScanActivity.getInstance(getApplicationContext()).logout();
                productLogin();
            }

        });

        findViewById(R.id.buttonLogout2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });
    }



    // public void checkItemInWarehouse() {
    //Intent intent = new Intent(this, ProductInfoActivity.class);
    // startActivity(intent);
    private void productLogin() {


        class ProductLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //progressBar = (ProgressBar) findViewById(R.id.progressBar);
                //progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //progressBar.setVisibility(View.GONE);


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the product from the response
                        JSONObject productJson = obj.getJSONObject("product");

                        //creating a product object
                        Product product = new Product(
                                productJson.getInt("quantity"),
                                productJson.getString("productname"),
                                productJson.getString("productsymbol")
                        );

                        //storing the product in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).productLogin(product);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), ProductInfoActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Product does not exist in warehouse database", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("productsymbol", productsymbol);
                //params.put("password", password);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_CHECK_PRODUCT, params);
            }
        }

        ProductLogin ul = new ProductLogin();
        ul.execute();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}