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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicca.R;

public class MultiFilterDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private static final String TITLE_ARG = "title";
    private static final String TITLE_DEFAULT_VALUE = "Apply Filter";

    private String yearSelectedAfter;
    private String yearSelectedBefore;
    private TextView yearReleased;
    private Spinner yearSpinnerAfter;
    private Spinner yearSpinnerBefore;
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
        if (adapterView.getId() == R.id.yearSpinnerAfter) {
            yearSelectedAfter = adapterView.getSelectedItem().toString();
        }
        else if (adapterView.getId() == R.id.yearSpinnerBefore) {
            yearSelectedBefore = adapterView.getSelectedItem().toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    // Defines the listener interface with a method passing back data result.
    public interface onFilterActionListener{
        void onFinishFilterDialog(String yearAfter, String yearBefore, String songTitle, String songArtist);
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
        yearSpinnerAfter = view.findViewById(R.id.yearSpinnerAfter);
        yearSpinnerBefore = view.findViewById(R.id.yearSpinnerBefore);
        tvSongTitle = view.findViewById(R.id.tvSongTitle);
        etSongTitle = view.findViewById(R.id.etSongTitle);
        tvSongArtist = view.findViewById(R.id.tvSongArtist);
        etSongArtist = view.findViewById(R.id.etSongArtist);
        btnApplyFilter = view.findViewById(R.id.btnApplyFilter);

        // Fetch arguments from bundle and set title
        String title = getArguments().getString(TITLE_ARG, TITLE_DEFAULT_VALUE);
        getDialog().setTitle(title);
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        ArrayAdapter<String> spinnerAfter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.yearAfter));
        spinnerAfter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinnerAfter.setAdapter(spinnerAfter);

        ArrayAdapter<String> spinnerBefore = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.yearBefore));
        spinnerBefore.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinnerBefore.setAdapter(spinnerBefore);

        yearSpinnerAfter.setOnItemSelectedListener(this);
        yearSpinnerBefore.setOnItemSelectedListener(this);

        btnApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFilterActionListener listener = (onFilterActionListener) getActivity();
                listener.onFinishFilterDialog(yearSelectedAfter, yearSelectedBefore,
                        etSongTitle.getText().toString().toLowerCase().trim(),
                        etSongArtist.getText().toString().toLowerCase().trim());
                dismiss();
            }
        });
    }
}