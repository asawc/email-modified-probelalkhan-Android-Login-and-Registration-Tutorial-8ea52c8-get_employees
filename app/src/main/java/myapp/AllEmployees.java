package myapp;

import java.util.ArrayList;

public class AllEmployees {
    private static ArrayList<String> employees;
    //private String[] employees;

    public void AllEmployees(ArrayList<String> employees) {
        AllEmployees.employees = employees;
    }
    public static ArrayList<String> getAllEmployees() { return employees; }
}
