package ru.danilashamin.routetracker.ui.blocks;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import butterknife.ButterKnife;

public abstract class Block {

    protected View view;

    public Block(View view) {
        findRootView(getRootViewId(), view);
        ButterKnife.bind(this,view);
    }


    private void findRootView(@IdRes int rootViewId, View blockOrParent) {
        if (rootViewId != 0 && blockOrParent != null) {
            if (blockOrParent.getId() == rootViewId) view = blockOrParent;
            else view = blockOrParent.findViewById(rootViewId);
        }
        if (view == null) view = blockOrParent;
    }

    public View getView() {
        return view;
    }

    @IdRes
    protected abstract int getRootViewId();

    protected Block visible(@Nullable View v) {
        if (v != null) {
            v.setVisibility(View.VISIBLE);
        }
        return this;
    }

    protected Block gone(@Nullable View v) {
        if (v != null) {
            v.setVisibility(View.GONE);
        }
        return this;
    }

    protected Block invisible(@Nullable View v) {
        if (v != null) {
            v.setVisibility(View.INVISIBLE);
        }
        return this;
    }

    protected String getString(@StringRes int stringRes){
        return view.getResources().getString(stringRes);
    }
}
