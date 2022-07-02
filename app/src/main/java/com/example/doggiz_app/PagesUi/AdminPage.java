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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class AdminPage extends AppCompatActivity {

    private static final String USERS = "Users";
    private static final String DOG = "Dog";
    private static final String UPLOADS = "uploads/";

    private LinearLayout linearLayout;
    private View view;
    private TextView personName, personEmail, personWork, personAddress, personPhone;
    private ImageView imgPerson[] = new ImageView[100];
    private ImageView imgDeletePerson[] = new ImageView[100];
    public Counters[] counters = new Counters[10];
    private View[] views = new View[100];
    private Bitmap bitmap[] = new Bitmap[100];
    private File imgFile[] = new File[100];
    public String imagesName[] = new String[100];
    private int  index = 0, index2 = 0;

    private FirebaseDatabase database;
    private DatabaseReference userRef, dogRef;
    private StorageReference storageReference;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        database    = FirebaseDatabase.getInstance();
        userRef     = database.getReference(USERS);
        dogRef      = database.getReference(DOG);
        fAuth       =  FirebaseAuth.getInstance();

        showUsers();
    }

    private void showUsers() {

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {

                    String name = ds.child("fullName").getValue(String.class);
                    String email = ds.child("email").getValue(String.class);
                    String phone = ds.child("phone").getValue(String.class);
                    String workingPlace = ds.child("workingPlace").getValue(String.class);
                    String address = ds.child("address").getValue(String.class);
                    String imageName = ds.child("imageName").getValue(String.class);

                    linearLayout = findViewById(R.id.allProfilesLayout);
                    view = getLayoutInflater().inflate(R.layout.item_person, null);

                    personName = view.findViewById(R.id.personName);
                    personName.setText(name);
                    personEmail = view.findViewById(R.id.personEmail);
                    personEmail.setText(email);
                    personWork = view.findViewById(R.id.personWork);
                    personWork.setText(workingPlace);
                    personPhone = view.findViewById(R.id.personAddress);
                    personPhone.setText(phone);
                    personAddress = view.findViewById(R.id.personPhone);
                    personAddress.setText(address);

                    views[index] = view;
                    index++;
                    linearLayout.addView(view);
                }

                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        index = 0;
                        for (DataSnapshot ds2 : snapshot.getChildren()) {
                                imgPerson[index] = views[index].findViewById(R.id.PersonImg);
                                imgDeletePerson[index] = views[index].findViewById(R.id.personDeleteImage);
                                imgDeletePerson[index].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //
                                        AlertDialog dlg = new AlertDialog.Builder(AdminPage.this)
                                                .setTitle("Delete Person")
                                                .setMessage("Are you sure you want to delete this user?")
                                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        String userEmail = ds2.child("email").getValue().toString();
                                                        String userPassword = ds2.child("password").getValue().toString();

                                                        fAuth.signInWithEmailAndPassword(userEmail,userPassword)
                                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                                        if (task.isSuccessful()) {
                                                                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                                            user.delete() //delete the authentication
                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            if (task.isSuccessful()) {
                                                                                                dogRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                    @Override
                                                                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                                        for (DataSnapshot ds3 : snapshot.getChildren()) {

                                                                                                            if(ds3.child("userEmail").getValue().toString().equals(userEmail)){
                                                                                                                ds3.getRef().removeValue(); //delete the dogs the user owens
                                                                                                            }
                                                                                                            if(ds3.child("share").getValue().toString().contains(userEmail)){
                                                                                                                String keyId = ds3.getKey();
                                                                                                                String shareString = ds3.child("share").getValue().toString();
                                                                                                                ArrayList<String> shareList = new ArrayList<>(Arrays.asList(shareString.split(",")));
                                                                                                                shareList.remove(String.valueOf(userEmail));
                                                                                                                String newShareString = "";// = shareString.replace(userEmail,""); //delete the dogs that shared with him
                                                                                                                for(String s : shareList){
                                                                                                                    newShareString += s + ",";
                                                                                                                }
                                                                                                                newShareString = newShareString.replace(",,",",");
                                                                                                                dogRef.child(keyId).child("share").setValue(newShareString);

                                                                                                            }
                                                                                                        }
                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                                                                    }
                                                                                                });

                                                                                                ds2.getRef().removeValue(); //delete the user from the realtime database
                                                                                                finish();
                                                                                                startActivity(new Intent(AdminPage.this, AdminPage.class));
                                                                                                Toast.makeText(AdminPage.this, "User account deleted.", Toast.LENGTH_LONG).show();
                                                                                                dialog.dismiss();
                                                                                            }
                                                                                        }
                                                                                    });

                                                                        } else {
                                                                            Toast.makeText(AdminPage.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                                        }

                                                                    }
                                                                });
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
                                            imgPerson[index2].setImageBitmap(bitmap[index2]);
                                            views[index2].findViewById(R.id.personEmail).setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    personEmail = v.findViewById(R.id.personEmail);
                                                    LogIn.email = personEmail.getText().toString();
                                                    startActivity(new Intent(AdminPage.this, PersonProfile.class));
                                                }
                                            });
                                            index2++;
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(AdminPage.this, "213Error Occurred", Toast.LENGTH_SHORT).show();
                                        }
                                    });


                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                index++;


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