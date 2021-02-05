package myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.simplifiedcoding.simplifiedcoding.R;

import static myapp.Employee.employeesNames;
import static myapp.Employee.employeesSurnames;

public class CustomAdapter extends BaseAdapter {
    Context context;
    String countryList[];
    int flags[];
    //String employeesNames[], employeesSurnames[];
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, String[] employeesNames, String[] employeesSurnames) {
        this.context = context;
        Employee.employeesNames = employeesNames;
        Employee.employeesSurnames = employeesSurnames;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return employeesNames.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_show_employees, null);
        /*TextView employeeName = (TextView) view.findViewById(R.id.textViewEmployeeName);
        TextView employeeSurname = (TextView) view.findViewById(R.id.textViewEmployeeSurname);
        //ImageView icon = (ImageView) view.findViewById(R.id.icon);
        employeeName.setText(employeesNames[i]);
        employeeSurname.setText(employeesSurnames[i]);*/
        return view;
    }
}