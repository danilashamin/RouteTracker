package ru.danilashamin.routetracker.base.moxy;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public abstract class MvpBottomSheetDialogFragment extends MvpAppCompatDialogFragment {
    public MvpBottomSheetDialogFragment() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomSheetDialog(requireContext(), getTheme());
    }
}