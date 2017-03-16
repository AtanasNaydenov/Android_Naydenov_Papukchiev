package com.naydenov.papukchiev.fhictcomapnion;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Daniel on 16-Mar-17.
 */

public class GradesAdapter extends BaseAdapter {
    private ArrayList<Grade> grades;
    private LayoutInflater layoutInflater;
    private Context context;

    public GradesAdapter(Context aContext,ArrayList<Grade> grades){
        this.grades = grades;
        this.context = aContext;
        layoutInflater = LayoutInflater.from(aContext);
    }
    @Override
    public int getCount() {
        return grades.size();
    }

    @Override
    public Object getItem(int position) {
        return grades.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.grade_child_item, null);
            holder = new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.description = (TextView) convertView.findViewById(R.id.description);
            holder.grade = (TextView) convertView.findViewById(R.id.grade);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Grade grade = this.grades.get(position);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        holder.date.setText(df.format(grade.date));
        holder.description.setText(grade.description);
        holder.grade.setText(Double.toString(grade.grade));

        return convertView;
    }
    static class ViewHolder {
        TextView grade;
        TextView description;
        TextView date;
    }
}
