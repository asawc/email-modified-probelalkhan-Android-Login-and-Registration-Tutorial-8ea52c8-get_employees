package myapp;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filterable;

public class AutoCompleteAdapter extends ArrayAdapter<String>
        implements Filterable {
    private ArrayList<HashMap<String, String>> mData;

    public AutoCompleteAdapter(Context context, int textViewResourceId,
                               ArrayList<HashMap<String, String>> mData) {
        super(context, textViewResourceId);
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int index) {
        return mData.get(index).toString();
    }

    @Override
    public android.widget.Filter getFilter() {
        // TODO Auto-generated method stub

        android.widget.Filter filter = new android.widget.Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                // TODO Auto-generated method stub

                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    // A class that queries a web API, parses the data and
                    // returns an ArrayList<Style>
                    ArrayList<String> suggestion = new ArrayList<String>();
                    for (int i = 0; i < getCount(); i++) {
                        if (getItem(i).startsWith(
                                constraint.toString())) {
                            suggestion.add(getItem(i));

                            Log.v("Adapter: ", getItem(i)
                                    + "const" + constraint.toString());
                        }
                    }

                    // Now assign the values and count to the FilterResults
                    // object
                    filterResults.values = suggestion;

                    filterResults.count = suggestion.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                // TODO Auto-generated method stub

                clear();
                @SuppressWarnings("unchecked")
                ArrayList<String> newValues = (ArrayList<String>) results.values;
                for (int i = 0; i < newValues.size(); i++) {
                    add(newValues.get(i));

                }

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