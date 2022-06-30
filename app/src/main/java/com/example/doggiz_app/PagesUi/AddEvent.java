package com.example.doggiz_app.PagesUi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doggiz_app.Backend.GregorianCalendar;
import com.example.doggiz_app.Backend.NotificationReceiver;
import com.example.doggiz_app.Models.DateEvent;
import com.example.doggiz_app.Backend.MainActivity;
import com.example.doggiz_app.Models.DBevent;
import com.example.doggiz_app.R;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Calendar;
import java.util.Date;

public class AddEvent extends AppCompatActivity {

    ImageView ivDate;
    EditText etTitle, etDesc;
    TextView tvDate;
    Button addButton;
    public int pYear,pMonth,pDay;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        ivDate    = findViewById(R.id.calendarImageView);
        tvDate    = findViewById(R.id.EventDateText2);
        etDesc    = findViewById(R.id.eventDescriptionText);
        etTitle   = findViewById(R.id.eventTitleText);
        addButton = findViewById(R.id.eventAddNewBtn);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        DBevent dbEvent = new DBevent();

        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddEvent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month +1;
                        String date = day + "/" + month + "/" + year;
                        tvDate.setText(date);
                        tvDate.setVisibility(View.VISIBLE);
                        pYear = year; pMonth = month; pDay = day;
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email   = LogIn.email;
                String dogName = MyDog.dogInUse;
                String title   = etTitle.getText().toString().trim();
                String description = etDesc.getText().toString().trim();
                DateEvent dateEvent = new DateEvent(String.valueOf(pYear), String.valueOf(pMonth), String.valueOf(pDay), title, description, dogName, email);

//                long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
//                Date date = new GregorianCalendar(year, month - 1, day, 22, 58).getTime();
//                Date notificationDate = new Date(date.getTime() - MILLIS_IN_A_DAY);

                dbEvent.add(dateEvent).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddEvent.this, "Record is insert",Toast.LENGTH_SHORT).show();
//                        scheduleNotification(AddEvent.this, notificationDate.getTime(), title, description);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                });



                if(!title.equals("")) {
                    if(pYear != 0 && pMonth != 0 && pDay != 0) {
                        dbEvent.add(dateEvent).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AddEvent.this, "Record is insert", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                        });
                    }else {
                        Toast.makeText(AddEvent.this, "Please click on the calendar and choose a date!", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(AddEvent.this, "Please enter a title!", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
    public static void scheduleNotification(Context context, long delay, String title, String content) {//delay is after how much time(in millis) from current time you want to schedule the notification
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("text", content);
        PendingIntent pending = PendingIntent.getBroadcast(context, 42, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Schdedule notification
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, delay, pending);

        /*NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle((title))
                .setContentText((content));

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent activity = PendingIntent.getActivity(context, Integer.parseInt(NotificationReceiver.NOTIFICATION_ID), intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(activity);

        Notification notification = builder.build();

        Intent notificationIntent = new Intent(context, NotificationReceiver.class);
        notificationIntent.putExtra(NotificationReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(NotificationReceiver.NOTIFICATION_ID), notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);*/
    }
}