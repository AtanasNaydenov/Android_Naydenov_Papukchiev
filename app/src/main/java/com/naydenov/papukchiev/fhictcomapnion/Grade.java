package com.naydenov.papukchiev.fhictcomapnion;

import android.support.annotation.NonNull;

import java.util.Date;

public class Grade implements Comparable<Grade>{
    public Date date;
    public String description;
    public String itemCode;
    public double grade;
    public boolean passed;


    @Override
    public int compareTo(@NonNull Grade o) {
        return this.date.compareTo(o.date);
    }
}
