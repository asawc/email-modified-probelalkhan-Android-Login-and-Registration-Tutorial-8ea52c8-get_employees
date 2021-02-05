package net.simplifiedcoding.simplifiedcoding;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import myapp.AllEmployees;
import myapp.Employee;
import myapp.Product;

/**
 * Created by Belal on 9/5/2017. modified by Arek on 19.01.2021
 */

//here for this class we are using a singleton pattern

public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_EMPLOYEE_ID = "id";
    private static final String KEY_EMPLOYEE_NAME = "keyemployeename";
    private static final String KEY_EMPLOYEE_SURNAME = "keyemployeesurname";
    private static final String KEY_EMPLOYEE_SYMBOL = "employeesymbol";
    private static final String KEY_ALL_EMPLOYEES = "employees";
    //private static final String KEY_GENDER = "keygender";
    private static final String KEY_ID = "keyid";
    private static final String KEY_PRODUCTNAME = "productname";
    private static final String KEY_PRODUCTSYMBOL = "productsymbol";
    private static final String KEY_QUANTITY = "quantity";


    private static SharedPrefManager mInstance;
    private static SharedPrefManager mInstanceProduct;
    private static Context mCtx;
    private static Context mCtxProduct;

    private SharedPrefManager(Context context/*, Context contextproduct*/) {
        mCtx = context;
        //}
        //private SharedPrefManager(Context contextproduct) {
        //mCtxProduct = contextproduct;
    }

    public static synchronized SharedPrefManager getInstance(Context context/*, Context contextproduct*/) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context/*, contextproduct*/);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferencesUser = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesUser.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        //editor.putString(KEY_GENDER, user.getGender());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferencesUser = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferencesUser.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null) //,
                //sharedPreferences.getString(KEY_GENDER, null)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferencesUser = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesUser.edit();
        //editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }

    //method to let the product login
    //this method will store the product data in shared preferences
    public void productLogin(Product product) {
        SharedPreferences sharedPreferencesProduct = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesProduct.edit();
        editor.putInt(KEY_QUANTITY, product.getQuantity());
        editor.putString(KEY_PRODUCTNAME, product.getProductname());
        editor.putString(KEY_PRODUCTSYMBOL, product.getProductsymbol());
        //editor.putString(KEY_GENDER, user.getGender());
        editor.apply();
    }

    public void employeeLogin(Employee employee) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, employee.getEmployeeId());
        editor.putString(KEY_EMPLOYEE_NAME, employee.getEmployeeName());
        editor.putString(KEY_EMPLOYEE_SURNAME, employee.getEmployeeSurname());
        //editor.putString(KEY_GENDER, user.getGender());
        editor.apply();
    }

    //this method will give the employee
    public Employee getEmployee() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Employee(
                sharedPreferences.getInt(KEY_EMPLOYEE_ID, -1),
                sharedPreferences.getString(KEY_EMPLOYEE_NAME, null),
                sharedPreferences.getString(KEY_EMPLOYEE_SURNAME, null),
                sharedPreferences.getString(KEY_EMPLOYEE_SYMBOL, null) //,
                //sharedPreferences.getString(KEY_GENDER, null)
        );
    }
   /* //this method will give the registered employees
    public static AllEmployees getAllEmployees() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new AllEmployees(
        //        sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_ALL_EMPLOYEES, null)
        );
    }*/


        //this method will give the logged in ProductInfoActivity
        public Product getProduct() {
            SharedPreferences sharedPreferencesProduct = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            return new Product(
                    sharedPreferencesProduct.getInt(KEY_QUANTITY, -1),
                    sharedPreferencesProduct.getString(KEY_PRODUCTNAME, null),
                    sharedPreferencesProduct.getString(KEY_PRODUCTSYMBOL, null) //,
                    //sharedPreferences.getString(KEY_GENDER, null)
            );
        }
    }
