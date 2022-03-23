package com.nikhilchakravartula.stocksearch.adapters.suggestions;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nikhilchakravartula.stocksearch.models.SuggestionModel;

import java.util.List;

public class SuggestionsAdapter extends ArrayAdapter<SuggestionModel>
{


    public SuggestionsAdapter(@NonNull Context context,int resource, @NonNull List<SuggestionModel> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null)
        {
            LayoutInflater inflater = LayoutInflater.from(super.getContext());
            convertView = inflater.inflate(android.R.layout.simple_dropdown_item_1line,null);
        }
        SuggestionModel suggestionModel = getItem(position);
        ((TextView)convertView.findViewById(android.R.id.text1))
                .setText(
                suggestionModel.getTicker()+" | "+ suggestionModel.getDescription());
        Log.d("In convert view", suggestionModel.getTicker());
        return convertView;
    }

}
