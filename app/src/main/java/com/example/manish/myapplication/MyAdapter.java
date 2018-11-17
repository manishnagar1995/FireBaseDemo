package com.example.manish.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by USER on 27-Aug-18.
 */

public class MyAdapter extends BaseAdapter {

    Context ct;
    ArrayList<Person> al;
    MyAdapter(Context ct, ArrayList<Person> al)
    {
        this.ct =ct;
        this.al = al;
    }
    @Override
    public int getCount() {
        return al.size();
    }

    @Override
    public Object getItem(int i) {
        return al.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Activity at = (Activity)ct;
        View v1  = at.getLayoutInflater().inflate(com.example.firebasedemo.R.layout.my_layout,viewGroup,false);

        TextView tv1,tv2,tv3;

        tv3 = v1.findViewById(com.example.firebasedemo.R.id.tv3);

        Person p = (Person) al.get(i);

        tv3.setText(p.getPass());

        return v1;
    }
}
