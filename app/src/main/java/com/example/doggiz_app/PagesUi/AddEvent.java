package com.example.doggiz_app.PagesUi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEvent extends AppCompatActivity {

    ImageView ivDate;
    EditText etTitle, etDesc;
    TextView tvDate;
    Button addButton;
    String alertTime = " 14:43:00" ;    //every day before the event the time of alert will be 12PM
    long oneDay = 86400000; //in ms
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
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                String dateString = dateEvent.day.concat("-").concat(dateEvent.month).concat("-").concat(dateEvent.year).concat(alertTime);
                Date notificationDate = null;
                try {
                    notificationDate = sdf.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date finalNotificationDate = notificationDate;
                if(!title.equals("")) {
                    if(pYear != 0 && pMonth != 0 && pDay != 0) {
                        dbEvent.add(dateEvent).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AddEvent.this, "Record is insert",Toast.LENGTH_SHORT).show();
                                long notificationDate = finalNotificationDate.getTime() - oneDay;
                                scheduleNotification(AddEvent.this, notificationDate, "Reminder! tomorrow you have: " + title, description);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                        });
                    }else {
                        Toast.makeText(AddEvent.this, "Please click on the calendar to choose a date!", Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(AddEvent.this, "Please enter a title!", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
    public void scheduleNotification(Context context, long delay, String title, String description) {//delay is after how much time(in millis) from current time you want to schedule the notification
        createNotificationChannel();
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        PendingIntent pending = PendingIntent.getBroadcast(context, 0, intent, 0);
        // Schdedule notification
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        manager.set(
                AlarmManager.RTC_WAKEUP,
                delay,
                pending
        );
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "notification";
            String description = "calender notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notification", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}