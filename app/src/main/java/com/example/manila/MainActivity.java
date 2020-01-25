package com.example.manila;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Landmarks> landmarks;
    private DatabaseHelperForUsers mydb;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    private String DateAlertDialogBox;
    private String user;
    private ArrayList<String> landmarks2 = new ArrayList<>();
    private CharSequence[] sequences;
    private TextView dateAdded;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            mydb = new DatabaseHelperForUsers(getApplicationContext());
            int PassUser = mydb.getIDFromTableUser(user);
            Bundle bundle = new Bundle();
            bundle.putInt("User", PassUser);
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_mnlist:
                    selectedFragment = new ListFragment();
                    selectedFragment.setArguments(bundle);
                    break;
                case R.id.navigation_landmarks:
                    selectedFragment = new LandmarkFragment();
                    selectedFragment.setArguments(bundle);
                    break;
                case R.id.navigation_rewards:
                    selectedFragment = new RewardsFragment();
                    selectedFragment.setArguments(bundle);
                    break;
                case R.id.navigation_about:
                    selectedFragment = new AboutFragment();
                    break;
                case R.id.navigation_profile:
                    selectedFragment = new ProfileFragment();
                    selectedFragment.setArguments(bundle);
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new DatabaseHelperForUsers(this);
        calendar = Calendar.getInstance();
        user = getIntent().getStringExtra("User");

        int PassUser = mydb.getIDFromTableUser(user);

        Bundle bundle = new Bundle();
        bundle.putInt("User", PassUser);

        ListFragment listFragment = new ListFragment();
        listFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, listFragment).commit();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void onClickAdd(View view) {
        mydb = new DatabaseHelperForUsers(this);
        int UserID = mydb.getIDFromTableUser(user);
        Cursor res = mydb.getAllDataForLandmarks();
        int count = 0;
        if (landmarks2.isEmpty()) {
            while (res.moveToNext()) {
                if (!mydb.CheckIfUser_ListExist(UserID, res.getInt(0))) {
                    landmarks2.add(mydb.getLandmarksBaseOnID(res.getInt(0)));
                }

            }
        }else {
            landmarks2.clear();
            Cursor res2 = mydb.getAllDataForLandmarks();
            while (res2.moveToNext()) {
                if (!mydb.CheckIfUser_ListExist(UserID, res2.getInt(0))) {
                    landmarks2.add(mydb.getLandmarksBaseOnID(res2.getInt(0)));
                }

            }
        }
        sequences = new String[landmarks2.size()];
        for (String temp : landmarks2) {
            sequences[count] = temp;
            System.out.println(temp);
            count++;
        }
        SingleAlgo(UserID);
    }


    public void SingleAlgo(final int userID) {
        mydb = new DatabaseHelperForUsers(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Landmarks");
        builder.setItems(sequences, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                date = dateFormat.format(calendar.getTime());
                int landmarkID = mydb.getIDFromTableLandmarks(sequences[which].toString());
                Boolean CheckInsert = mydb.insertDataInUserVisit(userID, landmarkID, date);
                if (CheckInsert) {
                    loadDatabase(userID);
                    Toast.makeText(MainActivity.this, "Successfully Inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }

        });
        builder.setNeutralButton("Select Multiple Items", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MultipleAlgo(userID);
            }
        });
        builder.create();
        builder.show();
    }


    public void MultipleAlgo(final int userID) {
        final ArrayList selectedItem = new ArrayList();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Landmarks");
        builder.setMultiChoiceItems(sequences, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked == true) {
                    if (!selectedItem.contains(sequences[which])) {
                        selectedItem.add(sequences[which]);
                        landmarks2.remove(sequences[which]);
                    } else {
                        selectedItem.remove(sequences[which]);
                    }
                } else if (isChecked == false) {
                    selectedItem.remove(sequences[which]);
                }
            }
        });
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                date = dateFormat.format(calendar.getTime());
                for (int i = 0; i < selectedItem.size(); i++) {
                    String task = selectedItem.get(i).toString();
                    int landmarkID = mydb.getIDFromTableLandmarks(task);
                    Boolean CheckInsert = mydb.insertDataInUserVisit(userID, landmarkID, date);
                    if (CheckInsert) {
                        loadDatabase(userID);
                        Toast.makeText(MainActivity.this, "Successfully Inserted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        builder.setNeutralButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SingleAlgo(userID);
            }
        });
        builder.create();
        builder.show();
    }

    public void onClickStartActvitiy(View view) {
        View parent = (View) view.getParent();
        TextView text = (TextView) parent.findViewById(R.id.TextViewTitleDisplay);
        Intent intent = new Intent(getBaseContext(), LandmarkInfoActivity.class);
        intent.putExtra("Title", text.getText().toString());
        intent.putExtra("User", user);
        startActivity(intent);
    }
    public void StartRewardActivity(View view){
        View parent = (View) view.getParent();
        TextView text = (TextView) parent.findViewById(R.id.TextViewTitleRewards);
        Intent intent = new Intent(getBaseContext(), RewardsActivity.class);
        intent.putExtra("Title", text.getText().toString());
        intent.putExtra("User", user);
        startActivity(intent);
    }
    public void showCustomAlertDialogBox(View view) {
        mydb = new DatabaseHelperForUsers(this);
        final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        final View view1 = getLayoutInflater().inflate(R.layout.alertdialogboxcustomize, null);

        View parent = (View) view.getParent();
        final TextView text = (TextView) parent.findViewById(R.id.TextViewTitle);
        TextView test = (TextView) view1.findViewById(R.id.TextViewAlertDialogTitle);
        test.setText(text.getText().toString());
        final int userID = mydb.getIDFromTableUser(user);
        final int landmarksID = mydb.getIDFromTableLandmarks(text.getText().toString());
        TextView TextViewDateAlertDialogbox = view1.findViewById(R.id.TextViewDateAlertDialogbox);
        TextViewDateAlertDialogbox.setText(mydb.getDate(userID, landmarksID));

        alert.setView(view1);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        Button ButtonExit = view1.findViewById(R.id.ButtonExit);
        Button ButtonRemove = view1.findViewById(R.id.ButtonRemove);
        Button ButtonViewDetails = view1.findViewById(R.id.ButtonDetails);
        Button ButtonVerify = view1.findViewById(R.id.ButtonVerify);

        ButtonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int PassUser = mydb.getIDFromTableUser(user);
                Intent intent = new Intent(getBaseContext(), ScanCodeActivity.class);
                intent.putExtra("User", PassUser);
                intent.putExtra("UserString",user);
                intent.putExtra("Landmark",landmarksID);
                startActivity(intent);
            }
        });

        ButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
        ButtonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view1.getContext());
                builder.setTitle("Landmarks");
                builder.setMessage("Delete?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int i = mydb.DeleteDataUserList(userID, landmarksID);
                        if(i==1){
                            System.out.println("Successfully Deleted");
                        }
                        loadDatabase(userID);
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "No deletion", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create();
                builder.show();
            }
        });

        ButtonViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), LandmarkInfoActivity.class);
                intent.putExtra("User", userID);
                intent.putExtra("Title", text.getText().toString());
                startActivity(intent);
            }
        });

    }
    public void loadDatabase(int userID) {
        View parent = (View) ListFragment.passdata.getParent();
        dateAdded = findViewById(R.id.tvDateAdded);
        recyclerView = parent.findViewById(R.id.List);
        mydb = new DatabaseHelperForUsers(this);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        landmarks = new ArrayList<Landmarks>();
        Cursor res = mydb.getAllDataForList(userID);
        if (res.getCount() == 0) {
            landmarks.clear();
        } else {
            while (res.moveToNext()) {
                String temp = mydb.getLandmarksBaseOnID(res.getInt(1));
//                dateAdded.setText(res.getInt(2));
                landmarks.add(new Landmarks(temp, "Add Details", temp));
            }
        }

        myAdapter = new LandmarksAdapter(this, landmarks);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void onBackPressed() {
        String check=tellFragment();
        if(!check.equals("ListFragment")){
            super.onBackPressed();
        }
    }
    private String tellFragment() {
        List<Fragment> fragment = getSupportFragmentManager().getFragments();
        for (Fragment f : fragment) {
            if (f != null && f instanceof ListFragment) {
                ((ListFragment) f).onBackPressed();
                return "ListFragment";
            }
        }
        return "None";
    }

    public void verifyBtnClick(View view) {
        TextView text = (TextView) findViewById(R.id.TextViewTitle);
        final int landmarksID = mydb.getIDFromTableLandmarks(text.getText().toString());
        int PassUser = mydb.getIDFromTableUser(user);
        Intent intent = new Intent(getBaseContext(), ScanCodeActivity.class);
        intent.putExtra("User", PassUser);
        intent.putExtra("Landmark",landmarksID);
        startActivity(intent);
    }

    public void btnRemove(View view) {
        TextView text = (TextView) findViewById(R.id.TextViewTitle);
        final View view1 = getLayoutInflater().inflate(R.layout.alertdialogboxcustomize, null);
        final int userID = mydb.getIDFromTableUser(user);
        final int landmarksID = mydb.getIDFromTableLandmarks(text.getText().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(view1.getContext());
        builder.setTitle("Landmarks");
        builder.setMessage("Delete?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int i = mydb.DeleteDataUserList(userID, landmarksID);
                if(i==1){
                    System.out.println("Successfully Deleted");
                }
                loadDatabase(userID);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "No deletion", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create();
        builder.show();
    }
}
