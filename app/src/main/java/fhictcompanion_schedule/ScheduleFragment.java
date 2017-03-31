package fhictcompanion_schedule;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.naydenov.papukchiev.fhictcomapnion.R;

import java.util.ArrayList;

public class ScheduleFragment extends android.app.Fragment {
    private static ArrayList<ScheduleItem> schedule;

    public ScheduleFragment() {
    }
    public static ScheduleFragment newInstance(ArrayList<ScheduleItem> schedule) {
        ScheduleFragment.schedule = schedule;
        return new ScheduleFragment();
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
        listView.setAdapter(new ScheduleAdapter(this.getContext() ,schedule));
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
