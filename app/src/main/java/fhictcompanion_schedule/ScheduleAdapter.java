package fhictcompanion_schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.naydenov.papukchiev.fhictcomapnion.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ScheduleAdapter extends BaseAdapter {
    private ArrayList<ScheduleItem> schedule;
    private LayoutInflater layoutInflater;
    private Context context;

    public ScheduleAdapter(Context aContext, ArrayList<ScheduleItem> schedule) {
        this.schedule = schedule;
        this.context = aContext;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return schedule.size();
    }

    @Override
    public Object getItem(int position) {
        return schedule.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ScheduleViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.schedule_child_item, null);
            holder = new ScheduleViewHolder();
            holder.startTime = (TextView) convertView.findViewById(R.id.startTime);
            holder.endTime = (TextView) convertView.findViewById(R.id.endTime);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.subject = (TextView) convertView.findViewById(R.id.subject);
            holder.teacher = (TextView) convertView.findViewById(R.id.teacher);
            holder.room = (TextView) convertView.findViewById(R.id.room);
            convertView.setTag(holder);
        } else {
            holder = (ScheduleViewHolder) convertView.getTag();
        }

        ScheduleItem scheduleItem = this.schedule.get(position);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");

        holder.startTime.setText(time.format(scheduleItem.start));
        holder.endTime.setText(time.format(scheduleItem.end));
        holder.date.setText(df.format(scheduleItem.start));
        holder.subject.setText(scheduleItem.subject);
        holder.teacher.setText(scheduleItem.teacherAbbreviation);
        holder.room.setText(scheduleItem.room);

        return convertView;
    }

    static class ScheduleViewHolder{
        TextView startTime;
        TextView endTime;
        TextView date;
        TextView subject;
        TextView teacher;
        TextView room;
    }
}
