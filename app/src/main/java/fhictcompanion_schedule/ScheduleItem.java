package fhictcompanion_schedule;

import android.support.annotation.NonNull;

import java.util.Date;

public class ScheduleItem implements Comparable<ScheduleItem>{
    /**
         "room": "R1_2.84",
         "subject": "andr1",
         "teacherAbbreviation": "avet",
         "start": "2017-03-16T08:45:00",
         "end": "2017-03-16T10:15:00",
         "uid": "ad3f40d8-d84b-4679-8e15-8b5414b8a4e7",
         "hoursMask": 3,
         "description": "t/p",
         "updatedAt": "2017-01-23T19:02:04.67"
     */
    public String room;
    public String subject;
    public String teacherAbbreviation;
    public Date start;
    public Date end;

    @Override
    public int compareTo(@NonNull ScheduleItem o) {
        return this.start.compareTo(o.start);
    }
}
