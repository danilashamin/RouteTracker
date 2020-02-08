package ru.danilashamin.routetracker.base.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.danilashamin.routetracker.R;
import ru.danilashamin.routetracker.base.moxy.MvpBottomSheetDialogFragment;
import ru.danilashamin.routetracker.logic.usermessages.Message;

public abstract class BottomSheetDialogFragmentBase extends MvpBottomSheetDialogFragment implements ViewBase {

    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.BaseBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(provideLayoutRes(), container, false);
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init();
    }

    @LayoutRes
    protected abstract int provideLayoutRes();

    protected void init(){}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(Message message) {
        Toast.makeText(requireContext(), message.getMessageStringRes(), Toast.LENGTH_SHORT).show();
    }
}
