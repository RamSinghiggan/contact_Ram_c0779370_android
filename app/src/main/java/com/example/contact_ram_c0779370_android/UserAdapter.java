package com.example.contact_ram_c0779370_android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

public class UserAdapter extends ArrayAdapter {
    Context mContext;
    int layoutResource;
    List<User> user;
    DataBaseHelper mDatabase;

    public UserAdapter(@NonNull  Context mContext, int layoutResource, List<User> user, DataBaseHelper mDatabase) {
        super(mContext, layoutResource,user);
        this.mContext = mContext;
        this.layoutResource = layoutResource;
        this.user = user;
        this.mDatabase = mDatabase;
    }

//
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View v = layoutInflater.inflate(layoutResource,null);

        TextView tvfirstname = v.findViewById(R.id.tv_firstname);
        TextView tvlastname = v.findViewById(R.id.tv_lastname);
        TextView tvemail = v.findViewById(R.id.tv_email);
        TextView tvaddress = v.findViewById(R.id.tv_address);
        TextView tvphone = v.findViewById(R.id.tv_phone);

         final User users = user.get(position);
         tvfirstname.setText(users.getFirstname());
         tvlastname.setText(users.getLastName());
         tvemail.setText(users.getEmail());
         tvaddress.setText(users.getAddress());
         tvphone.setText(String.valueOf(users.getPhone()));

         v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 deleteUser(users);
             }
         });
         v.findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 updateUser(users);
             }
         });
        return  v;


    }
     private  void updateUser(final User user){


         AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
         LayoutInflater inflater = LayoutInflater.from(mContext);
         View v = inflater.inflate(R.layout.dialog_update, null);
         alert.setView(v);

         final AlertDialog alertDialog = alert.create();
         alertDialog.show();

         final EditText fname = v.findViewById(R.id.et_firstname);
         final EditText lname = v.findViewById(R.id.et_lastname);
         final EditText email = v.findViewById(R.id.et_email);
         final EditText addrs = v.findViewById(R.id.et_address);
         final  EditText phn = v.findViewById(R.id.et_phone);

         fname.setText(user.getFirstname());
         lname.setText(user.getLastName());
         email.setText(user.getEmail());
         addrs.setText(user.getAddress());
         phn.setText(String.valueOf(user.getPhone()));


         v.findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String firstname = fname.getText().toString().trim();
                 String lastname = lname.getText().toString().trim();
                 String mail = email.getText().toString().trim();
                 String address = addrs.getText().toString().trim();
                 String phone = phn.getText().toString().trim();
                 if(mDatabase.updateUser(user.getId(),firstname,lastname,mail,address,Integer.parseInt(phone))){
                     Toast.makeText(mContext, "Contact updated", Toast.LENGTH_SHORT).show();
                     loadUser();
                 }
                 else
                 {
                    // Toast.makeText(mContext, "Contact update", Toast.LENGTH_SHORT).show();
                 }
                 alertDialog.dismiss();
             }

             });
         };





     private  void deleteUser(final User user) {
         AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
         builder.setTitle("Are you sure");
         builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                /*
                String sql = "DElETE FROM employees WHERE id =?";
                mDatabase.execSQL(sql,new Integer[] {employee.getId()});

                 */
                 if(mDatabase.deleteUser(user.getId()))
                     loadUser();
             }
         });
         builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {

             }
         });
         AlertDialog alertDialog = builder.create();
         alertDialog.show();

     }
     private void loadUser() {
         Cursor cursor = mDatabase.getAllUsers();
         if (cursor.moveToFirst()){
             user.clear();
             do{
                 user.add(new User(cursor.getInt(0),
                         cursor.getString(1),
                         cursor.getString(2),
                         cursor.getString(3),
                         cursor.getString(4),
                         cursor.getInt(5)));
                }
             while (cursor.moveToNext());
             cursor.close();
         }
         notifyDataSetChanged();

     }

    }



