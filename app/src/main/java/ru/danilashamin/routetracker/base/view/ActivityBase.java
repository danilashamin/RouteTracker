package ru.danilashamin.routetracker.base.view;

import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;



import javax.inject.Inject;

import butterknife.ButterKnife;
import ru.danilashamin.routetracker.R;
import ru.danilashamin.routetracker.base.moxy.MvpAppCompatActivity;
import ru.danilashamin.routetracker.logic.usermessages.Message;
import ru.danilashamin.routetracker.ui.blocks.BlockNavBar;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;

public abstract class ActivityBase extends MvpAppCompatActivity implements ViewBase {

    @Inject
    NavigatorHolder navigatorHolder;

    private Navigator navigator = new SupportAppNavigator(this, R.id.container);

    protected BlockNavBar navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(provideLayoutRes());
        ButterKnife.bind(this);
        init();
        inject();
    }

    protected void init() { }

    protected BlockNavBar initNavbar(String title){
        return navBar = new BlockNavBar(findViewById(android.R.id.content))
                .setBackHandler(this::onBackPressed);
    }

    protected BlockNavBar initNavbar(@StringRes int title){
        return initNavbar(getString(title));
    }

    protected BlockNavBar initNavbar(){
        return initNavbar(R.string.app_name);
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        super.onPause();
        navigatorHolder.removeNavigator();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(Message message) {
        Toast.makeText(this, message.getMessageStringRes(), Toast.LENGTH_SHORT).show();
    }

    protected abstract void inject();

    @LayoutRes
    protected abstract int provideLayoutRes();
}
