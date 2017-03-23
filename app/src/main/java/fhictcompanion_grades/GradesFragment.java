package fhictcompanion_grades;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.naydenov.papukchiev.fhictcomapnion.R;

import java.util.ArrayList;

public class GradesFragment extends android.app.Fragment {
    private static ArrayList<Grade> grades;

    public GradesFragment() {
    }
    public static GradesFragment newInstance(ArrayList<Grade> grades) {
        GradesFragment.grades = grades;
        return new GradesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grades, container, false);
        ListView listView = (ListView) view.findViewById(R.id.gradesListView);
        listView.setAdapter(new GradesAdapter(this.getContext() ,grades));
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
