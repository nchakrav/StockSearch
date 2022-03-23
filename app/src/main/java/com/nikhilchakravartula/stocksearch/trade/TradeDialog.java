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
import com.nikhilchakravartula.stocksearch.utils.Formatter;


public class TradeDialog extends DialogFragment {
    Button buyBtn;
    Button sellBtn;
    TextView dialogTitle;
    EditText numShares;
    TextView assetsAvailable;
    TextView sharesComputation;

    String ticker;
    Double sharePrice;
    Double currentNumShares;
    public interface OnActionListener
    {
        public void onBuy(Double numShares);
        public void onSell(Double numShares);
    }
    OnActionListener actionListener;
    public TradeDialog(String ticker,
                       Double currentNumShares,
                       Double sharePrice,
                       OnActionListener actionListener)
    {
        super();
        this.sharePrice = sharePrice;
        this.currentNumShares = currentNumShares;
        this.actionListener = actionListener;
        this.ticker = ticker;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.trade_dialog,null);
        buyBtn = view.findViewById(R.id.buy_btn);
        sellBtn = view.findViewById(R.id.sell_btn);
        dialogTitle = view.findViewById(R.id.dialog_title);
        numShares  = view.findViewById(R.id.num_shares);
        assetsAvailable = view.findViewById(R.id.assets_available);
        assetsAvailable.setText("$"+ Formatter.format(MainActivity.moneyInWallet) +" to buy "+ticker);
        sharesComputation = view.findViewById(R.id.shares_computation);
        builder.setView(view);
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double numSharesTyped = 0.0;
                if(numShares.getText().toString()!=null && numShares.getText().toString().length()>0) {
                    numSharesTyped = Double.parseDouble(numShares.getText().toString());
                    Double totalCost = numSharesTyped*sharePrice;
                    if(totalCost>MainActivity.moneyInWallet)
                    {
                        Toast.makeText(getActivity(),"Not enough assets to buy",Toast.LENGTH_LONG).show();
                    }
                    else actionListener.onBuy(numSharesTyped);
                    dismiss();
                }
                else
                {
                    Toast.makeText(getActivity(),"Cannot buy less than 0 shares",Toast.LENGTH_LONG).show();
                }



            }
        });

        sellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double numSharesTyped = 0.0;
                if(numShares.getText().toString()!=null && numShares.getText().toString().length()>0) {
                    numSharesTyped = Double.parseDouble(numShares.getText().toString());
                    Double totalCost = numSharesTyped*sharePrice;
                    if(numSharesTyped>currentNumShares)
                    {
                        Toast.makeText(getActivity(),"Not enough shares to sell",Toast.LENGTH_LONG).show();
                    }
                    else {
                        actionListener.onSell(numSharesTyped);
                        dismiss();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(),"Cannot sell less than 0 shares",Toast.LENGTH_LONG).show();
                }
            }
        });

        numShares.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Double numSharesTyped = 0.0;
                if(numShares.getText().toString()!=null && numShares.getText().toString().length()>0) {
                    numSharesTyped = Double.parseDouble(numShares.getText().toString());
                    sharesComputation.setText(numSharesTyped + "*$" + Formatter.format(sharePrice) + "/share = " + Formatter.format(numSharesTyped * sharePrice));
                }
                else
                {
                    sharesComputation.setText(numSharesTyped + "*$" + Formatter.format(sharePrice) + "/share = " + Formatter.format(numSharesTyped * sharePrice));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
