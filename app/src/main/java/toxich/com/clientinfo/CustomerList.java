package toxich.com.clientinfo;

import DB.DBHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomerList extends AppCompatActivity {

    private ArrayList<String> id;
    private ArrayList<String> firstName;
    private ArrayList<String> lastName;
    private ArrayList<String> middleName;
    private ArrayList<String> phoneNumber;
    private TableLayout mainTable;
    private View v;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_list_layout);
        mainTable = (TableLayout)findViewById(R.id.customer_list);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.customer_list_toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
        }



        v = mainTable.getRootView().getRootView().getRootView();
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            id = intent.getExtras().getStringArrayList("id");
            firstName = intent.getExtras().getStringArrayList("firstName");
            lastName = intent.getExtras().getStringArrayList("lastName");
            middleName = intent.getExtras().getStringArrayList("middleName");
            phoneNumber = intent.getExtras().getStringArrayList("phoneNumber");
        }
        if (id != null) {
            for (int i = 0; i < id.size(); i++) {
                mainTable.addView(createNewRow(id.get(i), firstName.get(i), lastName.get(i), middleName.get(i), phoneNumber.get(i)));
            }
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
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private TableRow createNewRow(String sId, String sFirstName, String sLastName, String sMiddleName, String sPhoneNumber){
        TableRow newRow = new TableRow(this);
        newRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));


        TextView id = new TextView(this);
        final TextView firstName = new TextView(this);
        final TextView lastName = new TextView(this);
        TextView middleName = new TextView(this);
        final TextView phoneNumber = new TextView(this);

        id.setPadding(3,3,3,3);
        firstName.setPadding(3,3,3,3);
        lastName.setPadding(3,3,3,3);
        middleName.setPadding(3,3,3,3);
        phoneNumber.setPadding(3,3,3,3);

        id.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0f));
        firstName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        lastName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        middleName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        phoneNumber.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));


        firstName.setGravity(Gravity.RIGHT);
        lastName.setGravity(Gravity.RIGHT);
        middleName.setGravity(Gravity.RIGHT);
        phoneNumber.setGravity(Gravity.RIGHT);

        id.setText(sId);
        firstName.setText(sFirstName);
        lastName.setText(sLastName);
        middleName.setText(sMiddleName);
        phoneNumber.setText(sPhoneNumber);

        id.setTextAppearance(v.getContext(), R.style.AppTheme);
        firstName.setTextAppearance(v.getContext(), R.style.AppTheme);
        lastName.setTextAppearance(v.getContext(), R.style.AppTheme);
        middleName.setTextAppearance(v.getContext(), R.style.AppTheme);
        phoneNumber.setTextAppearance(v.getContext(), R.style.AppTheme);

//        id.setTextSize(18);
//        firstName.setTextSize(18);
//        lastName.setTextSize(18);
//        middleName.setTextSize(18);
//        phoneNumber.setTextSize(18);

        lastName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerList.this, CustomerInfo.class);
                startActivity( MainActivity.mainActivity.getUserInfo(intent, DBHelper.COLUMN_Phone, phoneNumber.getText().toString()) );

            }
        });



        newRow.addView(id);
        newRow.addView(lastName);
        newRow.addView(firstName);
        newRow.addView(middleName);
        newRow.addView(phoneNumber);


        return newRow;
    }
}
