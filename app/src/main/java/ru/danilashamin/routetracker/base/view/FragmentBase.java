package ru.danilashamin.routetracker.base.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;


import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.danilashamin.routetracker.R;
import ru.danilashamin.routetracker.base.moxy.MvpAppCompatFragment;
import ru.danilashamin.routetracker.logic.usermessages.Message;
import ru.danilashamin.routetracker.ui.blocks.BlockNavBar;

public abstract class FragmentBase extends MvpAppCompatFragment implements ViewBase {

    private Unbinder unbinder;

    protected BlockNavBar navBar;

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

    protected BlockNavBar initNavbar(String title){
        return navBar = new BlockNavBar(getView())
                .setBackHandler(this::onBackPressed)
                .setTitle(title);
    }

    protected BlockNavBar initNavbar(@StringRes int title){
        return initNavbar(getString(title));
    }

    protected BlockNavBar initNavbar(){
        return initNavbar(R.string.app_name);
    }

    protected boolean onBackPressed(){
        return false;
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
