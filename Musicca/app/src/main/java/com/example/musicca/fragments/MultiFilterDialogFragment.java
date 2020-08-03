package com.example.musicca.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.musicca.R;

public class MultiFilterDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private static final String TITLE_ARG = "title";

    private String YEAR_SELECTED;
    private TextView yearReleased;
    private Spinner yearSpinner;
    private TextView tvSongTitle;
    private EditText etSongTitle;
    private TextView tvSongArtist;
    private EditText etSongArtist;
    private Button btnApplyFilter;

    public MultiFilterDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        YEAR_SELECTED = adapterView.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    // Defines the listener interface with a method passing back data result.
    public interface onFilterActionListener{
        void onFinishFilterDialog(String year, String songTitle, String songArtist);
    }

    public static MultiFilterDialogFragment newInstance(String title) {
        MultiFilterDialogFragment frag = new MultiFilterDialogFragment();
        Bundle args = new Bundle();
        args.putString(TITLE_ARG, title);
        frag.setArguments(args);
        return frag;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_multi_filter_dialog, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        yearReleased = view.findViewById(R.id.yearReleased);
        yearSpinner = view.findViewById(R.id.yearSpinner);
        tvSongTitle = view.findViewById(R.id.tvSongTitle);
        etSongTitle = view.findViewById(R.id.etSongTitle);
        tvSongArtist = view.findViewById(R.id.tvSongArtist);
        etSongArtist = view.findViewById(R.id.etSongArtist);
        btnApplyFilter = view.findViewById(R.id.btnApplyFilter);

        // Fetch arguments from bundle and set title
        String title = getArguments().getString(TITLE_ARG, "Apply Filter");
        getDialog().setTitle(title);
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        yearSpinner.setOnItemSelectedListener(this);

        btnApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFilterActionListener listener = (onFilterActionListener) getActivity();
                listener.onFinishFilterDialog(YEAR_SELECTED,
                        etSongTitle.getText().toString().toLowerCase().trim(),
                        etSongArtist.getText().toString().toLowerCase().trim());
                Log.d("FILTER", YEAR_SELECTED);
                Log.d("FILTER", "song title: " + etSongTitle.getText().toString().toLowerCase().trim());
                Log.d("FILTER", "song artist: " + etSongArtist.getText().toString().toLowerCase().trim());
                dismiss();

            }
        });
    }
}