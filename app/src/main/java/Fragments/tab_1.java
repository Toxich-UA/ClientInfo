package Fragments;

import DB.DBHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import toxich.com.clientinfo.R;

public class tab_1 extends Fragment {


    private TextView tLastName;
    private TextView tFirstName;
    private TextView tMiddleName;
    private TextView tPhoneNumber;

    String firstName;
    String lastName;
    String middleName;
    String phoneNumber;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab_1, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            firstName = bundle.getString("firstName");
            lastName = bundle.getString("lastName");
            middleName = bundle.getString("middleName");
            phoneNumber = bundle.getString("phoneNumber");
        }
        tLastName = (TextView) v.findViewById(R.id.tLastName);
        tFirstName = (TextView) v.findViewById(R.id.tFirstName);
        tMiddleName = (TextView) v.findViewById(R.id.tMiddleName);
        tPhoneNumber = (TextView) v.findViewById(R.id.tPhoneNumber);

        tLastName.setText(lastName);
        tFirstName.setText(firstName);
        tMiddleName.setText(middleName);
        tPhoneNumber.setText(phoneNumber);

        return v;
    }

}

