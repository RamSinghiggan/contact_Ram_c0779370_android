package com.example.contact_ram_c0779370_android;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;
import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {

    ListView listView;
    DataBaseHelper mDatabase;
    List<User> userList;
    SearchView searchView;
    List<User> filterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        listView = findViewById(R.id.listView);
        userList = new ArrayList<>();
        mDatabase = new DataBaseHelper(this);
        filterList = new ArrayList<>();
        searchView = findViewById(R.id.searchView);
        loadUsers();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(!newText.isEmpty()) {
                    filterList.clear();
                    for (int i = 0; i < userList.size(); i++) {
                        User getcontact = userList.get(i);
                        if (getcontact.firstname.contains(newText)) {
                            filterList.add(getcontact);
                        }
                    }
                    UserAdapter userAdapter = new UserAdapter(UsersActivity.this,R.layout.list_layout,filterList,mDatabase);
                    listView.setAdapter(userAdapter);
                }
                if(newText.isEmpty()){
                    UserAdapter userAdapter = new UserAdapter(UsersActivity.this,R.layout.list_layout,userList,mDatabase);
                    listView.setAdapter(userAdapter);
                }
                return false;
            }
        });
    }

    private void loadUsers() {
        Cursor cursor = mDatabase.getAllUsers();
        if (cursor.moveToFirst()) {

            do {

                userList.add(new User(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5)));
                System.out.println(cursor.getInt(0));
                System.out.println(cursor.getString(1));

            } while (cursor.moveToNext());
            cursor.close();

            UserAdapter userAdapter = new UserAdapter(this,R.layout.list_layout,userList,mDatabase);
            listView.setAdapter(userAdapter);

        }
    }
}
