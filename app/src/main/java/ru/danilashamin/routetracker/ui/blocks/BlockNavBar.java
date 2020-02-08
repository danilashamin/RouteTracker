package ru.danilashamin.routetracker.ui.blocks;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.elogroup.tracker.R;
import com.elogroup.tracker.ui.interfaces.IClickListener;

import butterknife.BindView;

public class BlockNavBar extends Block {

    @BindView(R.id.back_navigation_icon)
    ImageButton backNavigationIcon;

    @BindView(R.id.navbar_title)
    TextView navbarTitle;

    public BlockNavBar(View view) {
        super(view);
    }

    public BlockNavBar setBackHandler(@Nullable IClickListener clickListener) {
        backNavigationIcon.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.click();
            }
        });
        return this;
    }

    public BlockNavBar setTitle(@StringRes int title){
        navbarTitle.setText(title);
        return this;
    }

    public BlockNavBar setTitle(String title){
        navbarTitle.setText(title);
        return this;
    }

    @Override
    protected int getRootViewId() {
        return R.id.block_navbar;
    }

}
