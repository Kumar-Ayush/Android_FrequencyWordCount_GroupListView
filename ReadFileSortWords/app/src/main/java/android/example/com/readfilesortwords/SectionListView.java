package android.example.com.readfilesortwords;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ayush on 15/3/17.
 */

public class SectionListView extends ListActivity {

    static String TAG="SectionListActivity";
    private CustomAdaptor mAdapter;
    private Set<Map.Entry<String, Integer>> entrySetSortedByValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new CustomAdaptor(this);
        Intent intent = getIntent();
        String result = intent.getStringExtra("RESULT");
        Log.i(TAG, "Result is: " + result);
        Set<Map.Entry<String, Integer>> set=frequencyCount(result);
        int range=0;
        mAdapter.addSectionHeaderItem("0-10");
        for (Map.Entry<String, Integer> mapping : set) {
            int quotient=mapping.getValue()/10;
            int remainder=mapping.getValue()%10;
            if (range != quotient && remainder!=0) {
                Log.i(TAG, "Found new section Header");
                range=quotient;


                mAdapter.addSectionHeaderItem(range+"0-"+(range+1)+"0");
            }
            mAdapter.addItem(mapping.getKey() + " ==> " + mapping.getValue());

        }
        setListAdapter(mAdapter);
    }
    private Set<Map.Entry<String, Integer>> frequencyCount(String st){
        st=st.trim();
        st = st + " ";
        int count = lengthx(st);
        Log.i(TAG, "count: " + count);

        String arr[] = new String[count];
        int p = 0;
        int c = 0;

        for (int i = 0; i < st.length(); i++) {
            if (st.charAt(i) == ' ') {
                arr[p] = st.substring(c,i);
                Log.i(TAG, "Array is: " + arr[p]);
                //System.out.println(arr[p]);
                c = i + 1;
                p++;
            }
        }
        Map<String, Integer> map = new HashMap<>();

        for (String w : arr) {
            Integer n = map.get(w);
            n = (n == null) ? 1 : ++n;
            map.put(w, n);
        }
        for (String key : map.keySet()) {
            Log.i(TAG, "Key Values: " + map.get(key));
            //System.out.println(key + " = " + map.get(key));
        }

        Set<Map.Entry<String, Integer>> entries = map.entrySet();

        Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String,Integer>>() {

            @Override
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                Integer v1 = e1.getValue();
                Integer v2 = e2.getValue();
                return v1.compareTo(v2); }
        };

        List<Map.Entry<String, Integer>> listOfEntries = new ArrayList<Map.Entry<String, Integer>>(entries);
        Collections.sort(listOfEntries, valueComparator);

        LinkedHashMap<String, Integer> sortedByValue = new LinkedHashMap<String, Integer>(listOfEntries.size());

        for(Map.Entry<String, Integer> entry : listOfEntries){

            sortedByValue.put(entry.getKey(), entry.getValue());
        }

        for(Map.Entry<String, Integer> entry : listOfEntries){

            sortedByValue.put(entry.getKey(), entry.getValue());
        }

        Log.i(TAG, "HashMaps after sorting entries by value");
        //System.out.println("HashMap after sorting entries by values ");
        Set<Map.Entry<String, Integer>> entrySetSortedByValue = sortedByValue.entrySet();

        for(Map.Entry<String, Integer> mapping : entrySetSortedByValue){
            Log.i(TAG, "Key Value Pair: " + mapping.getKey() + " ==> " + mapping.getValue());
            //System.out.println(mapping.getKey() + " ==> " + mapping.getValue());
        }
        // SectionListView sectionListView=new SectionListView(entrySetSortedByValue);
        return entrySetSortedByValue;

    }
    private static int lengthx(String a) {
        int count = 0;
        for (int j = 0; j < a.length(); j++) {
            if (a.charAt(j) == ' ') {
                count++;
            }
        }
        return count;
    }

}
