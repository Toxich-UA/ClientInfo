package toxich.com.clientinfo;

import DB.DBHelper;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static DB.DBHelper.*;

public class EditWaybill extends AppCompatActivity {

    private String Client_ID = "NULL";
    private String currentPhone = "NULL";
    private String currentNumber = "NULL";

    private int lastNumber = 0;

    private DBHelper sqlHelper;

    private EditText Number;
    private EditText Date;
    private EditText Comment;
    private EditText Sum;
    private ArrayList<String> numberList;
    private boolean isEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_waybill);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.edit_waybill_toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
        }
        this.sqlHelper = MainActivity.mainActivity.sqlHelper;

        Number = (EditText)findViewById(R.id.tNumber);
        Date = (EditText)findViewById(R.id.tDate);
        Comment = (EditText)findViewById(R.id.tComment);
        Sum = (EditText)findViewById(R.id.tSum);


        Intent intent = getIntent();
        Client_ID = intent.getExtras().getString("Client_ID");
        currentPhone = intent.getExtras().getString("currentPhone");
        currentNumber = intent.getExtras().getString("currentNumber");
        lastNumber = Integer.parseInt(intent.getExtras().getString("lastNumber"));


        numberList = getAllNumber();

        isEdit = intent.getBooleanExtra("isEdit", false);



        if (isEdit){

            String[] numberArgs = new String[]{currentNumber};
            Cursor cursor = sqlHelper.getData(DBHelper.TABLE_WAYBILL, DBHelper.WAYBILL_COLUMN, "Number = ?", numberArgs);
            if (cursor.moveToFirst()) {
                do {
                    Number.setText(Integer.toString(cursor.getInt(cursor.getColumnIndex(COLUMN_NUMBER))));
                    Date.setText(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                    Comment.setText(cursor.getString(cursor.getColumnIndex(COLUMN_COMMENT)));
                    Sum.setText(cursor.getString(cursor.getColumnIndex(COLUMN_SUM)));
                }while (cursor.moveToNext());
            }
        }

    }
    //Menu create
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle(R.string.confirm_delete);

                builder.setMessage(R.string.confirm_delete_msg);
                builder.setNegativeButton(R.string.cancel, null);

                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (Number.getText().toString().equals("")) {
                            Toast.makeText(EditWaybill.this, R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
                        }else {
                            String[] whereArgs = new String[]{currentNumber};
                            sqlHelper.database.delete(DBHelper.TABLE_WAYBILL, DBHelper.COLUMN_NUMBER + " =?", whereArgs);
                            Toast.makeText(EditWaybill.this, R.string.successfully_deleted, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });

                builder.show();



                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void update(View view) {
        boolean isError = false;

        String number = Number.getText().toString();
        String date = Date.getText().toString();
        String comment = Comment.getText().toString();
        String sum = Sum.getText().toString();

        if (!(number.isEmpty() || date.isEmpty()|| sum.isEmpty())) {
            ContentValues values = new ContentValues();

            values.put("Client_id", Client_ID);
            values.put("Number", number);
            values.put("Date", date);
            values.put("Comment", comment);
            values.put("Sum", sum);
            if (isEdit) {
                String[] whereArgs = new String[]{currentNumber};
                MainActivity.mainActivity.sqlHelper.database.update(DBHelper.TABLE_WAYBILL, values, DBHelper.COLUMN_NUMBER+" =?", whereArgs);
            }else {
                if (numberList != null)
                    if (numberList.contains(number))
                        isError = true;
            }

            if (isError) {
                Toast.makeText(this, R.string.waybill_number_error, Toast.LENGTH_SHORT).show();
            } else {
                MainActivity.mainActivity.sqlHelper.database.insert(DBHelper.TABLE_WAYBILL, null, values);

                Toast.makeText(this, R.string.successfully_added, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(EditWaybill.this, CustomerInfo.class);
                startActivity(MainActivity.mainActivity.getUserInfo(intent, DBHelper.COLUMN_Phone, currentPhone));
                finish();
            }
        }else
            Toast.makeText(this, R.string.fill_all_fields, Toast.LENGTH_SHORT).show();


    }

    public void setCurrentDate(View view){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yy", Locale.ROOT);
        String formattedDate = df.format(c.getTime());

        Date.setText(formattedDate);
    }

    public void setLastNumberPlusOne(View view){
        Number.setText(lastNumber + 1 + "");
        lastNumber += 1;
    }

    private ArrayList<String> getAllNumber(){
        String[] columnArgs = new String[]{DBHelper.COLUMN_NUMBER};
        String[] selectionArgs = new String[]{Client_ID};
        Cursor cursor = sqlHelper.database.query(DBHelper.TABLE_WAYBILL, columnArgs, DBHelper.COLUMN_CLIENT_ID+" =?", selectionArgs, null, null, null);

        ArrayList<String> number = new ArrayList<>();
        if (cursor.moveToFirst()) {

            do {
                number.add(Integer.toString(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_NUMBER))));
            } while (cursor.moveToNext());
        }
        return number;
    }
}
