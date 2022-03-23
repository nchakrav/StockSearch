package com.nikhilchakravartula.stocksearch.trade;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.nikhilchakravartula.stocksearch.R;
import com.nikhilchakravartula.stocksearch.activities.MainActivity;


public class SuccessDialog extends DialogFragment {


    private final Double numShares;
    private final String action;
    private final String ticker;
    private  Button doneButton;
    private TextView desc;
    public SuccessDialog(String ticker,
                         Double numShares,
                         String action
                         )
    {
        super();
        this.numShares = numShares;
        this.action = action;
        this.ticker = ticker;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.success_dialog,null);
        builder.setView(view);
        doneButton = view.findViewById(R.id.done_button);
        desc = (TextView)view.findViewById(R.id.success_desc);
        desc.setText("You have successfully "+action+" "+numShares+ " shares of "+ticker);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return builder.create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
