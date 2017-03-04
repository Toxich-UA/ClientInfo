package toxich.com.clientinfo;

import Fragments.tab_1;
import Fragments.tab_2;
import Fragments.tab_3;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class CustomerInfo extends AppCompatActivity {

    private FragmentTabHost mTabHost;
    private String Client_ID;
    private String currentPhone;
    private String currentNumber = "";
    private String lastNumber;
    private Bundle tab_1_bundle;
    private Bundle tab_2_bundle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.customer_info_toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
        }

        Intent intent = getIntent();

        Client_ID = Integer.toString(intent.getExtras().getInt("Client_ID"));
        lastNumber = intent.getExtras().getString("lastNumber");

        tab_1_bundle = intent.getExtras().getBundle("tab_1_bundle");
        tab_2_bundle = intent.getExtras().getBundle("tab_2_bundle");

        currentPhone = tab_1_bundle.getString("phoneNumber");

        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);

        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator(getResources().getString(R.string.customer_info)),
                tab_1.class, tab_1_bundle);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator(getResources().getString(R.string.tab_2)),
                tab_2.class, tab_2_bundle);
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator(getResources().getString(R.string.tab_3)),
                tab_3.class, null);
    }

    //Menu create
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_edit_customer:

                Intent intent1 = new Intent(CustomerInfo.this, EditCustomer.class);
                intent1.putExtra("LastName", tab_1_bundle.getString("lastName"));
                intent1.putExtra("FirstName", tab_1_bundle.getString("firstName"));
                intent1.putExtra("MiddleName", tab_1_bundle.getString("middleName"));
                intent1.putExtra("Phone", tab_1_bundle.getString("phoneNumber"));
                startActivity(intent1);
                finish();
                return true;
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_add_waybill:

                Intent intent = new Intent(CustomerInfo.this, EditWaybill.class);
                intent.putExtra("Client_ID", Client_ID);
                intent.putExtra("currentPhone", currentPhone);
                if (lastNumber == null)
                    lastNumber = "0";

                intent.putExtra("lastNumber", lastNumber);
                intent.putExtra("isEdit", false);
                if (tab_2_bundle != null)
                    intent.putStringArrayListExtra("number_list", tab_2_bundle.getStringArrayList("number_list"));
                startActivity(intent);
                finish();
                return true;
            case R.id.menu_edit_waybill:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.enter_waybill_number);

                final EditText input = new EditText(this);

                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentNumber = input.getText().toString();

                        Intent intent2 = new Intent(CustomerInfo.this, EditWaybill.class);
                        intent2.putExtra("Client_ID", Client_ID);
                        intent2.putExtra("currentPhone", currentPhone);
                        intent2.putExtra("lastNumber", lastNumber);
                        intent2.putExtra("currentNumber", currentNumber);
                        intent2.putExtra("isEdit", true);

                        intent2.putStringArrayListExtra("number_list", tab_2_bundle.getStringArrayList("number_list"));
                        startActivity(intent2);
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
