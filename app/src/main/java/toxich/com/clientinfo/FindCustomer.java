package toxich.com.clientinfo;

import DB.DBHelper;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class FindCustomer extends AppCompatActivity {


    private EditText phone;
    private EditText lastName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_user_activity);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.find_user_toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
        }

        phone = (EditText) findViewById(R.id.editGetUserInfoByPhone);
        lastName = (EditText) findViewById(R.id.editGetUserInfoByLastName);

        //phone.setText("0960172540");
    }
    @Override
    public void onResume(){
        super.onResume();

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
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getCustomerInfoByNumber(View view){
        Intent intent = new Intent(FindCustomer.this, CustomerInfo.class);
        intent = MainActivity.mainActivity.getUserInfo(intent, DBHelper.COLUMN_Phone, phone.getText().toString());
        if (intent != null)
            startActivity(intent);
        finish();
    }
    public void getCustomerInfoByLastName(View view){
        Intent intent = MainActivity.mainActivity.getAllCustomer(lastName.getText().toString());
        if (intent != null)
            startActivity(intent);
        finish();
    }



    @Override
    public void onDestroy(){
        super.onDestroy();
    }


}