package com.example.doggiz_app.PagesUi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doggiz_app.Backend.MainActivity;
import com.example.doggiz_app.Models.Counters;
import com.example.doggiz_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MyDog extends AppCompatActivity {

    private static final String DOG = "Dog";
    private static final String USERS = "Users";
    private static final String UPLOADS = "dogs/";
    public static String dogInUse, dogImage;
    private ImageView imgDog[] = new ImageView[20];
    private ImageView imgDeleteDog[] = new ImageView[20];
    private ImageView imgName;
    private LinearLayout linearLayout;
    private View view;
    private TextView dogName, owenerName;
    private TextView foodCounter[] = new TextView[20];
    private TextView walkCounter[] = new TextView[20];
    public Counters[] counters = new Counters[20];
    private View[] views = new View[20];
    private FirebaseDatabase database;
    private DatabaseReference dogRef, userRef;
    private StorageReference storageReference;

    public String email;
    public String owner;
    private boolean isShare = false;
    public String imagesName[] = new String[20];
    private int  index = 0, index2 = 0;
    private Bitmap bitmap[] = new Bitmap[20];
    private File imgFile[] = new File[20];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dog);
        email = LogIn.email;

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        database    = FirebaseDatabase.getInstance();
        userRef     = database.getReference(USERS);
        dogRef      = database.getReference(DOG);


        showDogs();


    }

    private void showDogs(){

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child("email").getValue().equals(email)) {
                        owner = ds.child("fullName").getValue().toString();
                        dogRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot ds2 : snapshot.getChildren()) {
                                        if (ds2.child("userEmail").getValue().equals(email) || checkUserIsShare(ds2.child("share").getValue().toString())) {

                                            linearLayout = findViewById(R.id.myDogsLayout);
                                            view = getLayoutInflater().inflate(R.layout.item_image, null);
                                            counters[index] =  new Counters();
                                            foodCounter[index] = view.findViewById(R.id.foodCount);
                                            foodCounter[index].setText(ds2.child("currentFood").getValue().toString());
                                            walkCounter[index] = view.findViewById(R.id.walkCounter);
                                            walkCounter[index].setText(ds2.child("currentWalk").getValue().toString());

                                            dogName = view.findViewById(R.id.dogName);
                                            dogName.setText(ds2.child("dogName").getValue().toString());
                                            owenerName = view.findViewById(R.id.ownerName);
                                            owenerName.setText(ds2.child("ownerName").getValue().toString());
                                            views[index] = view;
                                            counters[index].setDogName(dogName.getText().toString());
                                            counters[index].setFoodCounter(Integer.valueOf(ds2.child("currentFood").getValue().toString()));
                                            counters[index].setWalkCounter(Integer.valueOf(ds2.child("currentWalk").getValue().toString()));

                                            counters[index].setFoodM(views[index].findViewById(R.id.foodMinus));
                                            counters[index].getFoodM().setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    for(int i = 0; i< counters.length;i++) {
                                                        if(counters[i] != null){
                                                            if (counters[i].getFoodM().equals(v)) {
                                                                if(counters[i].getFoodCounter() > 0) {
                                                                    counters[i].setFoodCounter(counters[i].getFoodCounter() - 1);
                                                                    int foodNumber = counters[i].getFoodCounter();
                                                                    foodCounter[i].setText(String.valueOf(foodNumber));
                                                                    dogRef.child(ds2.getKey()).child("currentFood").setValue(foodNumber);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            });

                                            counters[index].setFoodP(views[index].findViewById(R.id.foodPluse));
                                            counters[index].getFoodP().setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    for(int i = 0; i< counters.length;i++) {
                                                        if(counters[i] != null){
                                                            if (counters[i].getFoodP().equals(v)) {
                                                                if(counters[i].getFoodCounter() < Integer.valueOf(ds2.child("maxFood").getValue().toString())) {
                                                                    counters[i].setFoodCounter(counters[i].getFoodCounter() + 1);
                                                                    int foodNumber = counters[i].getFoodCounter();
                                                                    foodCounter[i].setText(String.valueOf(foodNumber));
                                                                    dogRef.child(ds2.getKey()).child("currentFood").setValue(foodNumber);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            });

                                            counters[index].setWalkM(views[index].findViewById(R.id.walkMinus));
                                            counters[index].getWalkM().setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    for(int i = 0; i< counters.length;i++) {
                                                        if(counters[i] != null){
                                                            if (counters[i].getWalkM().equals(v)) {
                                                                if(counters[i].getWalkCounter() > 0) {
                                                                    counters[i].setWalkCounter(counters[i].getWalkCounter() - 1);
                                                                    int walkNumber = counters[i].getWalkCounter();
                                                                    walkCounter[i].setText(String.valueOf(walkNumber));
                                                                    dogRef.child(ds2.getKey()).child("currentWalk").setValue(walkNumber);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            });

                                            counters[index].setWalkP(views[index].findViewById(R.id.walkPluse));
                                            counters[index].getWalkP().setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    for(int i = 0; i< counters.length;i++) {
                                                        if(counters[i] != null){
                                                            if (counters[i].getWalkP().equals(v)) {
                                                                if(counters[i].getWalkCounter() < Integer.valueOf(ds2.child("maxWalk").getValue().toString())) {
                                                                    counters[i].setWalkCounter(counters[i].getWalkCounter() + 1);
                                                                    int walkNumber = counters[i].getWalkCounter();
                                                                    walkCounter[i].setText(String.valueOf(walkNumber));
                                                                    dogRef.child(ds2.getKey()).child("currentWalk").setValue(walkNumber);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            });

                                            index++;
                                            linearLayout.addView(view);

                                        }
                                    }

                                dogRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        index = 0;
                                        for (DataSnapshot ds2 : snapshot.getChildren()) {
                                            if (ds2.child("userEmail").getValue().equals(email) || checkUserIsShare(ds2.child("share").getValue().toString())) {
                                                imgDog[index] = views[index].findViewById(R.id.dogImg);
                                                imgDeleteDog[index] = views[index].findViewById(R.id.deleteImage);
                                                imgDeleteDog[index].setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        //
                                                        AlertDialog dlg = new AlertDialog.Builder(MyDog.this)
                                                                .setTitle("Delete Dog")
                                                                .setMessage("Are you sure you want to delete this dog")
                                                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        if((ds2.child("userEmail").getValue().equals(email))){
                                                                            ds2.getRef().removeValue();
                                                                        }else{
                                                                            String keyId = ds2.getKey();
                                                                            String shareString = ds2.child("share").getValue().toString();
                                                                            ArrayList<String> shareList = new ArrayList<>(Arrays.asList(shareString.split(",")));
                                                                            shareList.remove(String.valueOf(email));
                                                                            String newShareString = "";
                                                                            for(String s : shareList){
                                                                                newShareString += s + ",";
                                                                            }
                                                                            //String newShareString = shareString.replace(email,""); //delete the dogs that shared with him
                                                                            newShareString = newShareString.replace(",,",",");
                                                                            dogRef.child(keyId).child("share").setValue(newShareString);
                                                                        }
                                                                        finish();
                                                                        startActivity(new Intent(MyDog.this,MyDog.class));
                                                                        dialog.dismiss();
                                                                    }
                                                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        dialog.dismiss();
                                                                    }
                                                                })
                                                                .create();
                                                        dlg.show();
                                                    }
                                                });
                                                imagesName[index] = ds2.child("imageName").getValue().toString();
                                                    storageReference = FirebaseStorage.getInstance().getReference().child(UPLOADS + imagesName[index]);
                                                    try {
                                                        imgFile[index] = File.createTempFile("profile", ".jpg");
                                                        storageReference.getFile(imgFile[index]).addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
                                                                try {
                                                                    Thread.sleep(500);
                                                                } catch (InterruptedException e) {
                                                                    e.printStackTrace();
                                                                }
                                                                bitmap[index2] = BitmapFactory.decodeFile(imgFile[index2].getPath());
                                                                imgDog[index2].setImageBitmap(bitmap[index2]);
                                                                views[index2].findViewById(R.id.dogImg).setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {
                                                                        for(int i = 0;i < index; i++)
                                                                            if(v == imgDog[i]){
                                                                                imgName = v.findViewById(R.id.dogImg);
                                                                                dogImage = imagesName[i];
                                                                            }
                                                                        startActivity(new Intent(MyDog.this, MainActivity.class));
                                                                    }
                                                                });
                                                                views[index2].findViewById(R.id.dogName).setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {
                                                                        Toast.makeText(MyDog.this, "Please click on the image!", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                                index2++;
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(MyDog.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });


                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                    index++;

                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }

                                });


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }});




    }


    private void alert(){
        AlertDialog dlg = new AlertDialog.Builder(MyDog.this)
                .setTitle("Delete Dog")
                .setMessage("Delete this dog")
                .setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dlg.show();
    }

    public boolean checkUserIsShare(String shareString){
        ArrayList<String> shareList = new ArrayList<>(Arrays.asList(shareString.split(",")));
        for(String s : shareList){
            if(s.equals(email))
                return true;
        }
        return false;
    }

}