package toxich.com.clientinfo;

import DB.DBHelper;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

import static DB.DBHelper.*;
import static DB.DBHelper.COLUMN_Phone;

public class MainActivity extends AppCompatActivity {


    public DBHelper sqlHelper;
    public static MainActivity mainActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mainActivity = this;

        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        sqlHelper = new DBHelper(getApplicationContext());
        // создаем базу данных
        sqlHelper.create();
        try {
            sqlHelper.open();
        }catch (SQLException e){
            Toast.makeText(this, "Не удалось открить базу", Toast.LENGTH_SHORT).show();
        }
    }
//Menu create
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_exit:
                this.finishAffinity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume(){
        super.onResume();

    }

    public Intent getUserInfo(Intent intent, String p){
        String[] selectionArgs = new String[]{p};


        if (!(p.equals(""))) {
            Cursor cursor = sqlHelper.getData(DBHelper.TABLE_CLIENT ,DBHelper.CLIENT_COLUMN, "Phone = ?", selectionArgs);

            if (cursor.moveToFirst()) {

                int id = cursor.getColumnIndex(COLUMN_ID);

                Bundle tab_1_bundle = new Bundle();
                tab_1_bundle.putString("lastName", cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)));
                tab_1_bundle.putString("firstName", cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)));
                tab_1_bundle.putString("middleName", cursor.getString(cursor.getColumnIndex(COLUMN_MIDDLE_NAME)));
                tab_1_bundle.putString("phoneNumber", cursor.getString(cursor.getColumnIndex(COLUMN_Phone)));

                intent.putExtra("Client_ID", cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));

                intent.putExtra("tab_1_bundle", tab_1_bundle);


                selectionArgs = new String[]{cursor.getString(id)};

                cursor = sqlHelper.getData(DBHelper.TABLE_WAYBILL, DBHelper.WAYBILL_COLUMN, "Client_id = ?", selectionArgs);

                if (cursor.moveToFirst()) {
                    ArrayList<String> number = new ArrayList<>();
                    ArrayList<String> date = new ArrayList<>();
                    ArrayList<String> comment = new ArrayList<>();
                    ArrayList<String> sum = new ArrayList<>();



                    do {
                        number.add(Integer.toString(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_NUMBER))));
                        date.add(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DATE)));
                        comment.add(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_COMMENT)));
                        sum.add(Double.toString(cursor.getDouble(cursor.getColumnIndex(DBHelper.COLUMN_SUM))));
                    } while (cursor.moveToNext());

                    cursor.moveToLast();
                    intent.putExtra("lastNumber", number.get(number.size()-1));
                    Bundle tab_2_bundle = new Bundle();

                    tab_2_bundle.putStringArrayList("number_list", number);
                    tab_2_bundle.putStringArrayList("date_list", date);
                    tab_2_bundle.putStringArrayList("comment_list", comment);
                    tab_2_bundle.putStringArrayList("sum_list", sum);


                    intent.putExtra("tab_2_bundle", tab_2_bundle);


                }

            } else {
                Toast.makeText(this, R.string.incorrect_phone_number, Toast.LENGTH_SHORT).show();
                return null;
            }
            cursor.close();

        }
        return intent;
    }

    public void findCustomerInfo(View view){
        Intent intent = new Intent(MainActivity.this, FindCustomer.class);
        startActivity(intent);
    }

    public void editCustomerInfo(View view){
        Intent intent = new Intent(MainActivity.this, EditCustomer.class);
        startActivity(intent);
    }
    public void getAllCustomer(View view){

        Cursor cursor = sqlHelper.database.query(DBHelper.TABLE_CLIENT, DBHelper.CLIENT_COLUMN, null, null, null, null, null);
        Intent intent = new Intent(MainActivity.this, CustomerList.class);


            if (cursor.moveToFirst()) {
                ArrayList<String> id = new ArrayList<>();
                ArrayList<String> firstName = new ArrayList<>();
                ArrayList<String> lastName = new ArrayList<>();
                ArrayList<String> middleName = new ArrayList<>();
                ArrayList<String> phoneNumber = new ArrayList<>();

                do {
                    id.add(Integer.toString(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID))));
                    firstName.add(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_FIRST_NAME)));
                    lastName.add(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_LAST_NAME)));
                    middleName.add(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_MIDDLE_NAME)));
                    phoneNumber.add(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_Phone)));
                } while (cursor.moveToNext());


                intent.putExtra("id", id);
                intent.putExtra("firstName", firstName);
                intent.putExtra("lastName", lastName);
                intent.putExtra("middleName", middleName);
                intent.putExtra("phoneNumber", phoneNumber);
            }

            startActivity(intent);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }


}