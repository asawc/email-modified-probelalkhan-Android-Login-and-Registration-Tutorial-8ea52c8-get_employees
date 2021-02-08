package myapp;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import net.simplifiedcoding.simplifiedcoding.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtilsHC4;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static net.simplifiedcoding.simplifiedcoding.URLs.URL_GET_ALL_EMPLOYEES;

public class FindEmployeeActivity extends Activity {

    JSONParser jsonParser = new JSONParser();

    public ArrayList<HashMap<String, String>> employeesList;

    //private static String url_display_user = "http://10.0.3.2/android_connect/display_user.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "error";
    private static final String TAG_MESSAGE = "message";

    private static final String TAG_ID = "id";

    private static final String TAG_EMPLOYEES = "employeeslist";

    private static final String TAG_EMPLOYEE = "employee";

    //private static final String TAG_NAME = "name";

    // employees JSONArray
    JSONArray employees = null;


    private AutoCompleteTextView autoComplete;
    ArrayList<HashMap<String,String>> cityZipList;
    ArrayList<HashMap<String,String>> autoCompleteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_employee);

        employeesList = new ArrayList<HashMap<String, String>>();

        new getEmployeesList().execute();

        // getListView
        //ListView lv = getListView();
//
        /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int arg2,
                                    long arg3) {

                //  String id = ((TextView) view.findViewById(R.id.uid)).getText()
                //          .toString();

                // Intent in = new Intent(getBaseContext(), StatusList.class);
                // in.putExtra(TAG_ID, uid);

                // startActivity(in);
            }
        });*/

        cityZipList = new ArrayList<HashMap<String, String>>();

        HashMap<String,String> row1 = new HashMap<String, String>();
        row1.put("city","AAC");
        row1.put("zip", "123");
        cityZipList.add(row1);

        HashMap<String,String> row2 = new HashMap<String, String>();
        row2.put("city","AAD");
        row2.put("zip", "231");
        cityZipList.add(row2);

        HashMap<String,String> row3 = new HashMap<String, String>();
        row3.put("city","EFG");
        row3.put("zip", "125");
        cityZipList.add(row3);

        HashMap<String,String> row4 = new HashMap<String, String>();
        row4.put("city","AAL");
        row4.put("zip", "334");
        cityZipList.add(row4);

        HashMap<String,String> row5 = new HashMap<String, String>();
        row5.put("city","EFD");
        row5.put("zip", "235");
        cityZipList.add(row5);

        HashMap<String,String> row6 = new HashMap<String, String>();
        row6.put("city","JKL");
        row6.put("zip", "333");
        cityZipList.add(row6);

        HashMap<String,String> row7 = new HashMap<String, String>();
        row7.put("city","JKM");
        row7.put("zip", "521");
        cityZipList.add(row7);

        HashMap<String,String> row8 = new HashMap<String, String>();
        row8.put("city","AAN");
        row8.put("zip","527");
        cityZipList.add(row8);

        autoComplete = (AutoCompleteTextView) findViewById(R.id.autoComplete);
        autoComplete.setAdapter(new AutoCompleteAdapter(this,R.layout.activity_add_employee, employeesList));

    }

    class getEmployeesList extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            FindEmployeeActivity.this.setProgressBarIndeterminateVisibility(true);
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

                FindEmployeeActivity.this
                        .setProgressBarIndeterminateVisibility(false);
                // updating UI from Background Thread
                runOnUiThread(new Runnable() {
                    public void run() {
                        /**
                         * Updating parsed JSON data into ListView
                         * */
                        ListAdapter adapter = new SimpleAdapter(
                                FindEmployeeActivity.this, employeesList,
                                R.layout.list_item, new String[] { //TAG_ID,
                                "name", "surname", "symbol"}, new int[] { //R.id.id,
                                R.id.name,  R.id.surname,  R.id.symbol });
                        // updating listview
                        //setListAdapter(adapter);
                    }
                });

                Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG)
                        .show();

            }
        }

    }

    private class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {


        public AutoCompleteAdapter(Context context, int textViewResourceId, ArrayList<HashMap<String,String>> data) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return autoCompleteList.size();
        }

        @Override
        public String getItem(int position) {
            return autoCompleteList.get(position).get("surname");
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(final CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {

                        autoCompleteList = new ArrayList<HashMap<String, String>>();
                        for (int i = 0; i < employeesList.size(); i++) {
                            if (employeesList.get(i).get("surname").startsWith(
                                    constraint.toString()) || employeesList.get(i).get("name").startsWith(
                                    constraint.toString())) {
                                autoCompleteList.add(employeesList.get(i));
                            }
                        }

                        // Now assign the values and count to the FilterResults
                        // object
                        filterResults.values = autoCompleteList;

                        filterResults.count = autoCompleteList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }

}