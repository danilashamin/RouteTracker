package ru.danilashamin.routetracker.ui.bottomsheets;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.danilashamin.routetracker.R;
import ru.danilashamin.routetracker.application.App;
import ru.danilashamin.routetracker.logic.entities.EntityRouteControl;
import ru.danilashamin.routetracker.logic.enums.RouteStatus;
import ru.danilashamin.routetracker.logic.utils.UtilTime;

public final class RouteControlBottomSheet {

    @BindView(R.id.tv_route_start_time)
    TextView tvRouteStartTime;
    @BindView(R.id.route_start_time_container)
    LinearLayout routeStartTimeContainer;
    @BindView(R.id.btn_stop_route)
    Button btnStopRoute;
    @BindView(R.id.btn_start_route)
    Button btnStartRoute;
    @BindView(R.id.btn_pause_route)
    Button btnPauseRoute;
    @BindView(R.id.btn_resume_route)
    Button btnResumeRoute;
    @BindView(R.id.route_control_container)
    LinearLayout routeControlContainer;

    @Inject
    UtilTime utilTime;

    private BottomSheetBehavior behavior;

    @Nullable
    private ActionListener actionListener;

    @Nullable
    private EntityRouteControl routeControl;

    private final Unbinder unbinder;

    public RouteControlBottomSheet(@NonNull View view) {
        App.getInstance().getAppComponent().inject(this);

        unbinder = ButterKnife.bind(this, view);

        behavior = BottomSheetBehavior.from(routeControlContainer);
        submitControlInfo(EntityRouteControl.startRouteControl());
    }

    public RouteControlBottomSheet setActionListener(@Nullable ActionListener actionListener) {
        this.actionListener = actionListener;
        return this;
    }

    public RouteControlBottomSheet submitControlInfo(@Nullable EntityRouteControl routeControl) {
        this.routeControl = routeControl;
        proceedControlInfo();
        return this;
    }

    private void proceedControlInfo() {
        if (routeControl == null || routeControl.isEmpty() || routeControl.isCompleted()) {
            setStartRouteState();
        } else {
            proceedRouteStatus();
        }
    }

    private void proceedRouteStatus() {
        switch (routeControl.getRouteStatus()) {
            case RouteStatus.ON_ROAD:
                setOnRoadRouteState();
                break;
            case RouteStatus.PAUSED:
                setPausedRouteState();
                break;
        }
    }


    private void setStartRouteState() {
        visible(btnStartRoute)
                .gone(routeStartTimeContainer)
                .gone(btnPauseRoute)
                .gone(btnResumeRoute)
                .gone(btnStopRoute);
    }

    private void setOnRoadRouteState() {
        visible(btnStopRoute)
                .visible(btnPauseRoute)
                .visible(routeStartTimeContainer)
                .gone(btnResumeRoute)
                .gone(btnStartRoute);
        setRouteStartTime();
    }

    private void setPausedRouteState(){
        visible(btnStopRoute)
                .visible(btnResumeRoute)
                .visible(routeStartTimeContainer)
                .gone(btnPauseRoute)
                .gone(btnStartRoute);
        setRouteStartTime();
    }

    private void setRouteStartTime() {
        tvRouteStartTime.setText(utilTime.formatTime(routeControl.getRouteStartTime()));
    }


    @OnClick({R.id.btn_stop_route, R.id.btn_start_route, R.id.btn_pause_route, R.id.btn_resume_route})
    void onControlButtonClicked(@NotNull View view) {
        switch (view.getId()) {
            case R.id.btn_stop_route:
                if (actionListener != null && routeControl != null) {
                    actionListener.onStopRouteClicked(routeControl.getRouteId());
                }
                break;
            case R.id.btn_start_route:
                if (actionListener != null && routeControl != null) {
                    actionListener.onStartRouteClicked();
                }
                break;
            case R.id.btn_pause_route:
                if (actionListener != null && routeControl != null) {
                    actionListener.onPauseRouteClicked(routeControl.getRouteId());
                }
                break;
            case R.id.btn_resume_route:
                if (actionListener != null && routeControl != null) {
                    actionListener.onResumeRouteClicked(routeControl.getRouteId());
                }
                break;
        }
    }

    public RouteControlBottomSheet collapse() {
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        return this;
    }

    public RouteControlBottomSheet expand() {
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        return this;
    }

    public void unbind() {
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private RouteControlBottomSheet visible(View v) {
        v.setVisibility(View.VISIBLE);
        return this;
    }

    private RouteControlBottomSheet gone(View v) {
        v.setVisibility(View.GONE);
        return this;
    }

    public interface ActionListener {
        void onStartRouteClicked();

        void onStopRouteClicked(long routeId);

        void onPauseRouteClicked(long routeId);

        void onResumeRouteClicked(long routeId);
    }
}
