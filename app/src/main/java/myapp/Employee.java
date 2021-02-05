package myapp;

import android.util.Log;

import net.simplifiedcoding.simplifiedcoding.SharedPrefManager;

import java.text.BreakIterator;
import java.util.Arrays;

//import static androidx.core.graphics.drawable.IconCompat.getResources;

public class Employee {
    public static String[] employeesNames;
    public static String[] employeesSurnames;
    public static BreakIterator getEmployeeName;
    public static BreakIterator getEmployeeSurname;
    private int id;
    public static String name, surname,  symbol/*, gender*/;
    private String[] employeesSymbols,employee;
    private String[][] employees = new String[id][2];

    public Employee(int id, String name, String surname, String symbol) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.symbol = symbol;
        //this.gender = gender;
        for (id = 0; id < 5; ++id) {
            //employee[id] = [name, surname];
            this.employeesNames[id] = name;
            this.employeesSurnames[id] = surname;
            this.employeesSymbols[id] = symbol;
            employees[id][0] = employeesNames[id];
            employees[id][1] = employeesSurnames[id];
            employees[id][2] = employeesSymbols[id];
        }
    }
    //employee[2] = [name, surname];
    //String[] strings = getEmployee();
    //Log.i("TAG", Arrays.toString(strings)));
    //tring[] employee = SharedPrefManager.getInstance(context).getEmployee();

    /*public void Employee(int id, String name, String surname, String symbol, String gender) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.symbol = symbol;
        //this.gender = gender;
        for (id = 0; id < 5; ++id) {
        //employee[id] = [name, surname];
        this.employeesNames[id] = name;
        this.employeesSurnames[id] = surname;
        this.employeesSymbols[id] = symbol;
        employees[id][0] = employeesNames[id];
        employees[id][1] = employeesSurnames[id];
        employees[id][2] = employeesSymbols[id];
    }*/

    /*public void Employees(String[] employees) {
        this.employees = employees;
    }*/

    public int getEmployeeId() {
        return id;
    }

    public String getEmployeeName() {
        return name;
    }

    public String getEmployeeSurname() { return surname; }

    public String getEmployeeSymbol() { return symbol; }

    public String[] getEmployee(int id) {

        return employee;
    }



    /*public String getGender() {
        return gender;
    }*/
}
