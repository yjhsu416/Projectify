package com.example.kartikgupta.projectify;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UpdateProfileWrite extends Activity implements View.OnClickListener{

    private EditText editTextUpSkill, editTextUpInterest, editTextUpExperience, editTextUpDesignation, editTextUpMoreInfo;
    private Button buttonUpdate;
    private TextView textViewEmail;

    //add menu bar
    private Button buttonMyProject;
    private Button buttonProfile;
    private Button buttonProject;
    private ImageButton imageButton2;
    //menu bar end

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_write);

        //menu bar
        buttonProfile = (Button) findViewById(R.id.buttonProfile);
        buttonMyProject = (Button) findViewById(R.id.buttonMyProject);
        buttonProject = (Button) findViewById(R.id.buttonProject);
 //       imageButton2 = (ImageButton) findViewById(R.id.btnCreateProject);

//        imageButton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(UpdateProfileWrite.this, NewProject.class);
//                startActivity(intent);
//
//            }
//        });


        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(UpdateProfileWrite.this, UpdateProfile.class);
                startActivity(intent);

            }
        });

        buttonMyProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(UpdateProfileWrite.this, MyProjects.class);
                startActivity(intent);

            }
        });

        buttonProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(UpdateProfileWrite.this, ProjectList.class);
                startActivity(intent);

            }
        });



        //menu bar end


        editTextUpSkill = findViewById(R.id.editTextUpSkill);
        editTextUpInterest = findViewById(R.id.editTextUpInterest);
        editTextUpExperience = findViewById(R.id.editTextUpExperience);
        editTextUpDesignation = findViewById(R.id.editTextUpDesignation);
        editTextUpMoreInfo = findViewById(R.id.editTextUpMoreInfo);
        buttonUpdate = findViewById(R.id.buttonUpdateProfile);
        textViewEmail = findViewById(R.id.textViewEmail);


        buttonUpdate.setOnClickListener(this);



        ///find user account information
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            final String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();


            //test

            FirebaseDatabase db = FirebaseDatabase.getInstance();

            final DatabaseReference userRef = db.getReference("UserProfile");
            userRef.orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue() == null) {
                        editTextUpSkill.setText("You haven't added profile yet" );
                        //                        Toast.makeText(Main2Activity.this, "card not found", Toast.LENGTH_SHORT).show();
                    }else {
                        userRef.orderByChild("email").equalTo(email).addChildEventListener(new ChildEventListener() {

                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                UserProfile finduser = new UserProfile();
                                finduser = dataSnapshot.getValue(UserProfile.class);
                                //                                Toast.makeText(MainActivity.this, "Card limit is: "+findcard.cardLimit, Toast.LENGTH_SHORT).show();
                                textViewEmail.setText(finduser.email);
                                editTextUpSkill.setText(finduser.skill);
                                editTextUpInterest.setText(finduser.interest);
                                editTextUpExperience.setText(finduser.experience);
                                editTextUpDesignation.setText(finduser.designation);
                                editTextUpMoreInfo.setText(finduser.moreInfo);
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            ///test end
        }


    }

    @Override
    public void onClick(View view) {

        //
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            final String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();

            FirebaseDatabase db = FirebaseDatabase.getInstance();
            final DatabaseReference userRef = db.getReference("UserProfile");
            if (view == buttonUpdate) {
                final String newSkill = editTextUpSkill.getText().toString();
                final String newInterest = editTextUpInterest.getText().toString();
                final String newExperience = editTextUpExperience.getText().toString();
                final String newDesignation = editTextUpDesignation.getText().toString();
                final String newMoreInfo = editTextUpMoreInfo.getText().toString();


                //                                editTextUpSkill.setText(finduser.skill);
                //                                editTextUpInterest.setText(finduser.interest);
                //                                editTextUpExperience.setText(finduser.experience);
                //                                editTextUpDesignation.setText(finduser.designation);
                //                                editTextUpMoreInfo.setText(finduser.moreInfo);

                userRef.orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() == null) {
                            Toast.makeText(UpdateProfileWrite.this, "profile not found", Toast.LENGTH_SHORT).show();
                        } else {
                            userRef.orderByChild("email").equalTo(email).addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    String userKey = dataSnapshot.getKey();
                                    userRef.child(userKey).child("skill").setValue(newSkill);
                                    userRef.child(userKey).child("interest").setValue(newInterest);
                                    userRef.child(userKey).child("experience").setValue(newExperience);
                                    userRef.child(userKey).child("designation").setValue(newDesignation);
                                    userRef.child(userKey).child("moreInfo").setValue(newMoreInfo);
                                    //userRef.child(userKey).child("appliedProjects").setValue("")；

                                    Intent intentt = new Intent(UpdateProfileWrite.this, UpdateProfile.class);
                                    UpdateProfileWrite.this.startActivity(intentt);
                                }

                                @Override
                                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onChildRemoved(DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

        }//
    }


}
