package weikun.mydiary.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

import weikun.mydiary.Common.ThemeManager;

/**
 * Created by Weikun on 2017/10/24.
 */

public class DatePickerFragment extends DialogFragment {
    private long savedTime;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    public static DatePickerFragment newInstance(long savedTime) {
        Bundle args = new Bundle();
        DatePickerFragment fragment = new DatePickerFragment();
        args.putLong("savedTime", savedTime);
        fragment.setArguments(args);
        return fragment;
    }
    public void setOnDateSetListener(DatePickerDialog.OnDateSetListener onDateSetListener) {
        this.onDateSetListener = onDateSetListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar;
        savedTime = getArguments().getLong("savedTime", -1);
        calendar = Calendar.getInstance();
        if (savedTime != -1) {
            calendar.setTimeInMillis(savedTime);
        }
        int year = calendar.get(Calendar.YEAR);         //年
        int month = calendar.get(Calendar.MONTH);       //月
        int day = calendar.get(Calendar.DAY_OF_MONTH);  //日

        return new DatePickerDialog(getActivity(), ThemeManager.getInstance().getPickerStyle(getActivity()),
                onDateSetListener, year, month, day);
    }
}
