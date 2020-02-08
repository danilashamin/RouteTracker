package ru.danilashamin.routetracker.ui.blocks;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.elogroup.tracker.R;
import com.elogroup.tracker.ui.customviews.orders.OrdersLastTimeSyncView;
import com.elogroup.tracker.ui.interfaces.IClickListener;

import org.threeten.bp.LocalDateTime;

import butterknife.BindView;

public final class BlockOrdersListNavBar extends Block {

    @BindView(R.id.orders_last_time_sync_view)
    OrdersLastTimeSyncView ordersLastTimeSyncView;
    @BindView(R.id.back_navigation_icon)
    ImageButton backNavigationIcon;
    @BindView(R.id.navbar_title)
    TextView navbarTitle;

    @Nullable
    private ActionListener actionListener;

    public BlockOrdersListNavBar(View view) {
        super(view);
        ordersLastTimeSyncView.setActionListener(orderLastTimeSyncViewActionListener);
    }

    public BlockOrdersListNavBar setBackHandler(@Nullable IClickListener clickListener) {
        backNavigationIcon.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.click();
            }
        });
        return this;
    }

    public BlockOrdersListNavBar setTitle(@StringRes int title) {
        navbarTitle.setText(title);
        return this;
    }

    public BlockOrdersListNavBar setTitle(String title) {
        navbarTitle.setText(title);
        return this;
    }

    public BlockOrdersListNavBar setActionListener(@Nullable ActionListener actionListener) {
        this.actionListener = actionListener;
        return this;
    }

    public BlockOrdersListNavBar setLastTimeOrdersSync(LocalDateTime time) {
        ordersLastTimeSyncView.setLastTimeOrdersSynced(time);
        return this;
    }

    private OrdersLastTimeSyncView.ActionListener orderLastTimeSyncViewActionListener = new OrdersLastTimeSyncView.ActionListener() {
        @Override
        public void onButtonSyncOrdersClicked() {
            if (actionListener != null) {
                actionListener.onButtonSyncOrdersClicked();
            }
        }
    };

    @Override
    protected int getRootViewId() {
        return R.id.app_bar_orders_list;
    }

    public interface ActionListener {
        void onButtonSyncOrdersClicked();
    }
}
