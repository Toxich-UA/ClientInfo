package Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import toxich.com.clientinfo.*;

import java.util.ArrayList;

public class tab_2 extends Fragment {

    private ArrayList<String> number;
    private ArrayList<String> date;
    private ArrayList<String> comment;
    private ArrayList<String> sum;
    private TableLayout mainTable;
    private View v;
    private double totalSum;
    private boolean color = true;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tab_2, container, false);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            number = bundle.getStringArrayList("number_list");
            date = bundle.getStringArrayList("date_list");
            comment = bundle.getStringArrayList("comment_list");
            sum = bundle.getStringArrayList("sum_list");
        }

        if (number != null) {
            for (int i = 0; i < number.size(); i++) {
                mainTable = (TableLayout) v.findViewById(R.id.mainTable);
                mainTable.addView(createNewRow(number.get(i), date.get(i), comment.get(i), sum.get(i)));
            }
            mainTable.addView(lastRow());
            mainTable.setColumnStretchable(0, true);
            mainTable.setColumnStretchable(1, true);
            mainTable.setColumnStretchable(2, true);
            mainTable.setColumnStretchable(3, true);
        }
        return v;
    }

    private TableRow createNewRow(String sNumber, String sDate, String sComment, String sSum){

        TableRow newRow = new TableRow(getActivity());
        newRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));



        TextView number = new TextView(v.getContext());
        TextView date = new TextView(v.getContext());
        TextView comment = new TextView(v.getContext());
        TextView sum = new TextView(v.getContext());

        number.setPadding(3,3,3,3);
        date.setPadding(3,3,3,3);
        comment.setPadding(3,3,3,3);
        sum.setPadding(3,3,3,3);

        number.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0f));
        date.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        comment.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        sum.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));

        date.setGravity(Gravity.RIGHT);
        comment.setGravity(Gravity.RIGHT);
        sum.setGravity(Gravity.RIGHT);

        number.setText(sNumber);
        date.setText(sDate);
        comment.setText(sComment);
        sum.setText(sSum);
        totalSum+= Double.parseDouble(sSum);

        number.setTextAppearance(v.getContext(), R.style.AppTheme);
        date.setTextAppearance(v.getContext(), R.style.AppTheme);
        comment.setTextAppearance(v.getContext(), R.style.AppTheme);
        sum.setTextAppearance(v.getContext(), R.style.AppTheme);
        if (color) {
            newRow.setBackgroundColor(Color.GRAY);
            color = !color;
        }else {
            newRow.setBackgroundColor(Color.LTGRAY);
            color = !color;
        }


        newRow.addView(number);
        newRow.addView(date);
        newRow.addView(comment);
        newRow.addView(sum);


        return newRow;
    }

    private TableRow lastRow(){
        TableRow newRow = new TableRow(getActivity());
        newRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.span = 3;

        newRow.setBackgroundColor(Color.DKGRAY);
        TextView total = new TextView(v.getContext());

        total.setPadding(3,3,3,3);
        total.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        total.setLayoutParams(params);
        total.setTextColor(Color.LTGRAY);
        total.setGravity(Gravity.RIGHT);
        total.setText(Double.toString(totalSum));
        total.setTextAppearance(v.getContext(), R.style.AppTheme);



        TextView sum = new TextView(v.getContext());
        sum.setPadding(3,3,3,3);
        sum.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        sum.setGravity(Gravity.RIGHT);
        sum.setTextColor(Color.LTGRAY);
        sum.setText(R.string.waybill_table_total_sum);
        sum.setTextAppearance(v.getContext(), R.style.AppTheme);



        newRow.addView(sum);
        newRow.addView(total);

        return newRow;
    }
}
