package myapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import net.simplifiedcoding.simplifiedcoding.R;
import net.simplifiedcoding.simplifiedcoding.RequestHandler;
import net.simplifiedcoding.simplifiedcoding.SharedPrefManager;
import net.simplifiedcoding.simplifiedcoding.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AddEmployeeActivity extends AppCompatActivity {
    EditText editTextEmployeeName, editTextEmployeeSurname;
    //RadioGroup radioGroupGender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        /*//if the user is already logged in we will directly start the profile activity
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
            return;
        }*/

        editTextEmployeeName = (EditText) findViewById(R.id.editTextEmployeeName);
        editTextEmployeeSurname = (EditText) findViewById(R.id.editTextEmployeeSurname);
        //editTextPassword = (EditText) findViewById(R.id.editTextPassword);


        findViewById(R.id.buttonRegisterEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on button register
                //here we will register the employee to server
                registerEmployee();
            }
        });

        findViewById(R.id.buttonShowListOfEmployees).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on button register
                //here we will register the employee to server
                openShowEmployeesActivity();
            }
        });

        findViewById(R.id.buttonScan4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on button register
                //here we will register the user to server
                openScanActivity();
            }
        });

        /*findViewById(R.id.textViewLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on login
                //we will open the login screen
                finish();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });*/

    }

    public void openShowEmployeesActivity() {
        Intent intent = new Intent(this, ShowEmployeesActivity.class);
        startActivity(intent);
    }

    public void openScanActivity() {
        Intent intent = new Intent(this, ScanActivity.class);
        startActivity(intent);
    }

    private void registerEmployee() {
        final String employeename = editTextEmployeeName.getText().toString().trim();
        final String employeesurname = editTextEmployeeSurname.getText().toString().trim();
        //final String password = editTextPassword.getText().toString().trim();

        /*//first we will do the validations

        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter username");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter your email");
            editTextEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Enter a password");
            editTextPassword.requestFocus();
            return;
        }

        //if it passes all the validations*/

        class RegisterEmployee extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("name", employeename);
                params.put("surname", employeesurname);
                //params.put("password", password);
                //params.put("gender", gender);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_ADD_EMPLOYEE, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
//                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
//                progressBar.setVisibility(View.GONE);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject employeeJson = obj.getJSONObject("employee");

                        //creating a new employee object
                        Employee employee = new Employee(
                                employeeJson.getInt("id"),
                                employeeJson.getString("name"),
                                employeeJson.getString("surname"),
                                employeeJson.getString("symbol")//,
                                //userJson.getString("gender")
                        );

                        //storing the employee in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).employeeLogin(employee);

                        //starting the profile activity
                        //finish();
                        //startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        RegisterEmployee re = new RegisterEmployee();
        re.execute();
    }
}
