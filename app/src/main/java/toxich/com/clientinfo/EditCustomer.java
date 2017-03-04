package toxich.com.clientinfo;

import DB.DBHelper;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditCustomer extends AppCompatActivity {

    private EditText lastName;
    private EditText firstName;
    private EditText middleName;
    private EditText phone;
    private Button btnUpdate;
    private boolean isEdit = false;
    public DBHelper sqlHelper;

    private String prevLastName, prevFirstName, prevMiddleName, prevPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_layout);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.customer_info_toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
        }
        this.sqlHelper = MainActivity.mainActivity.sqlHelper;

        lastName = (EditText)findViewById(R.id.eLastName);
        firstName = (EditText)findViewById(R.id.eFiName);
        middleName = (EditText)findViewById(R.id.eMiddleName);
        phone = (EditText)findViewById(R.id.ePhone);
        btnUpdate = (Button) findViewById(R.id.btnSet);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            prevLastName = intent.getExtras().getString("LastName");
            prevFirstName = intent.getExtras().getString("FirstName");
            prevMiddleName = intent.getExtras().getString("MiddleName");
            prevPhone = intent.getExtras().getString("Phone");

            lastName.setText(prevLastName);
            firstName.setText(prevFirstName);
            middleName.setText(prevMiddleName);
            phone.setText(prevPhone);

            isEdit = true;
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
                        if (phone.getText().toString().equals("")) {
                            Toast.makeText(EditCustomer.this, R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
                        }else {
                            String[] whereArgs = new String[]{prevPhone};
                            sqlHelper.database.delete(DBHelper.TABLE_CLIENT, DBHelper.COLUMN_Phone + " =?", whereArgs);
                            Toast.makeText(EditCustomer.this, R.string.successfully_deleted, Toast.LENGTH_SHORT).show();
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

    public void set(View view){

        String sLastName = lastName.getText().toString();
        String sFirstName = firstName.getText().toString();
        String sMiddleName = middleName.getText().toString();
        String sPhone = phone.getText().toString();

        ContentValues values = new ContentValues();

        values.put("Last_Name", sLastName);
        values.put("First_Name", sFirstName);
        values.put("Middle_Name", sMiddleName);
        values.put("Phone", sPhone);

        if (!(sLastName.isEmpty() || sFirstName.isEmpty()|| sMiddleName.isEmpty()|| sPhone.isEmpty())) {
            if (isEdit){
                String[] whereArgs = new String[]{prevPhone};
                MainActivity.mainActivity.sqlHelper.database.update(DBHelper.TABLE_CLIENT, values, DBHelper.COLUMN_Phone+" =?", whereArgs);
                Toast.makeText(this, R.string.customer_info_edit, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(EditCustomer.this, CustomerInfo.class);
                startActivity(MainActivity.mainActivity.getUserInfo(intent, sPhone));
                finish();
            }else{
                MainActivity.mainActivity.sqlHelper.database.insert(DBHelper.TABLE_CLIENT, null, values);

                Toast.makeText(this, R.string.successfully_added, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(EditCustomer.this, CustomerInfo.class);
                startActivity(MainActivity.mainActivity.getUserInfo(intent, sPhone));
                finish();
            }
        }else
            Toast.makeText(this, R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
    }
}
