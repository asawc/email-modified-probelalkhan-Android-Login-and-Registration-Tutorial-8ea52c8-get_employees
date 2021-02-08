package net.simplifiedcoding.simplifiedcoding;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import myapp.AddEmployeeActivity;
import myapp.FindEmployeeActivity;
import myapp.ScanActivity;
import myapp.ShowEmployeesActivity;

//import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    TextView textViewId, textViewUsername, textViewEmail/*, textViewGender*/;
    private Button buttonScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        buttonScan = (Button) findViewById(R.id.buttonScan2);
        // if the user is not logged in
        // starting the login activity
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        //textViewGender = (TextView) findViewById(R.id.textViewGender);


        //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();

        //setting the values to the textviews
        textViewId.setText(String.valueOf(user.getId()));
        textViewUsername.setText(user.getUsername());
        textViewEmail.setText(user.getEmail());
        //textViewGender.setText(user.getGender());

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
                finish(); //
                //ScanActivity.getInstance(getApplicationContext()).logout();
                openScanActivity();
            }
        });

        findViewById(R.id.buttonAddEmployeeActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); //
                //ScanActivity.getInstance(getApplicationContext()).logout();
                openAddEmployeeActivity();
            }
        });

        findViewById(R.id.buttonShowEmployeesActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                openShowEmployeesActivity();
            }
        });

        findViewById(R.id.buttonFindEmployeeActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                openFindEmployeeActivity();
            }
        });
    }

    public void openScanActivity() {
        Intent intent = new Intent(this, ScanActivity.class);
        startActivity(intent);
    }

    public void openAddEmployeeActivity() {
        Intent intent = new Intent(this, AddEmployeeActivity.class);
        startActivity(intent);
    }

    public void openShowEmployeesActivity() {
        Intent intent = new Intent(this, ShowEmployeesActivity.class);
        startActivity(intent);
    }

    public void openFindEmployeeActivity() {
        Intent intent = new Intent(this, FindEmployeeActivity.class);
        startActivity(intent);
    }
}