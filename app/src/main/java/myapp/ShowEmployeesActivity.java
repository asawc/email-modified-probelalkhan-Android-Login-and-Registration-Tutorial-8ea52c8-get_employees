package myapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtilsHC4;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import net.simplifiedcoding.simplifiedcoding.R;

import static net.simplifiedcoding.simplifiedcoding.URLs.URL_GET_ALL_EMPLOYEES;

public class ShowEmployeesActivity extends ListActivity {

    JSONParser jsonParser = new JSONParser();

    ArrayList<HashMap<String, String>> employeesList;

    //private static String url_display_user = "http://10.0.3.2/android_connect/display_user.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "error";
    private static final String TAG_MESSAGE = "message";

    private static final String TAG_ID = "id";
 // sdsf

    private static final String TAG_EMPLOYEES = "employeeslist";

    private static final String TAG_EMPLOYEE = "employee";

    //private static final String TAG_NAME = "name";

    // employees JSONArray
    JSONArray employees = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_employees);

        employeesList = new ArrayList<HashMap<String, String>>();

        new getEmployeesList().execute();

        // getListView
        ListView lv = getListView();
//
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int arg2,
                                    long arg3) {

                //  String id = ((TextView) view.findViewById(R.id.uid)).getText()
                //          .toString();

                // Intent in = new Intent(getBaseContext(), StatusList.class);
                // in.putExtra(TAG_ID, uid);

                // startActivity(in);
            }
        });
    }

    class getEmployeesList extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ShowEmployeesActivity.this.setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            // Building Parameters
            List<NameValuePair> parametres = new ArrayList<NameValuePair>();

            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(URL_GET_ALL_EMPLOYEES);
            String json = null;
            try {
                HttpResponse response = client.execute(request);
                HttpEntity httpEntity = response.getEntity();
                json = EntityUtilsHC4.toString(httpEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return json;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String json) {

            Boolean result = null;
            String message = null;

            Log.d("All Employees: ", json);
            // dismiss the dialog after getting all employees
            try {
                JSONObject jsonObj = new JSONObject(json);
                // wybranie tablicy employees
                result = jsonObj.getBoolean("error");
                message = jsonObj.getString("message");

                JSONArray jsonArray = jsonObj.getJSONArray("employees");
                for (int i = 0; i < jsonArray.length(); i++) {
                    // Wybranie pojedyńczego obiektu w tablicy - employee
                    JSONObject jsonObj2 = jsonArray.getJSONObject(i);
                    // Wybranie poszczególnych
                    // id= jsonObj.getString("id").toInteger ?? // id konwertuj na int
                    String id = jsonObj2.getString("id");
                    String name = jsonObj2.getString("name");
                    String surname = jsonObj2.getString("surname");
                    String symbol = jsonObj2.getString("symbol");

                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put("id", id);
                    map.put("name", name);
                    map.put("surname", surname);
                    map.put("symbol", symbol);

                    // adding HashList to ArrayList
                    employeesList.add(map);
                }
            } catch(Exception e) {
                Log.e("Error", e.getMessage());
                Toast.makeText(getBaseContext(), "Error while parsing response - " + e.getMessage(),  Toast.LENGTH_LONG).show();
            }

            if (result != null && (result == false)) {

                ShowEmployeesActivity.this
                        .setProgressBarIndeterminateVisibility(false);
                // updating UI from Background Thread
                runOnUiThread(new Runnable() {
                    public void run() {
                        /**
                         * Updating parsed JSON data into ListView
                         * */
                        ListAdapter adapter = new SimpleAdapter(
                                ShowEmployeesActivity.this, employeesList,
                                R.layout.list_item, new String[] { //TAG_ID,
                                "name", "surname", "symbol"}, new int[] { //R.id.id,
                                R.id.name,  R.id.surname,  R.id.symbol });
                        // updating listview
                        setListAdapter(adapter);
                    }
                });

                Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG)
                        .show();

            }
        }

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_list, menu);
        return true;
    }*/

}