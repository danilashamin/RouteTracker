package ru.danilashamin.routetracker.ui.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import javax.inject.Inject;

import ru.danilashamin.routetracker.application.App;
import ru.danilashamin.routetracker.logic.utils.ResourcesUtils;

public final class MarginDividerDecoration extends RecyclerView.ItemDecoration {

    private final int marginTop;
    private final int marginBottom;
    private final int marginStart;
    private final int marginEnd;

    private final boolean addLastElementBottomMargin;

    private MarginDividerDecoration(Builder builder) {
        marginTop = builder.marginTop;
        marginBottom = builder.marginBottom;
        marginStart = builder.marginStart;
        marginEnd = builder.marginEnd;
        addLastElementBottomMargin = builder.addLastElementBottomMargin;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.set(marginStart, marginTop, marginEnd, marginBottom);

        if (!addLastElementBottomMargin) {
            return;
        }
        RecyclerView.ViewHolder vh = parent.findContainingViewHolder(view);
        if (vh == null) {
            return;
        }

        int position = vh.getAdapterPosition();

        if (parent.getAdapter() == null || position == RecyclerView.NO_POSITION || position != parent.getAdapter().getItemCount() - 1) {
            return;
        }

        outRect.bottom = marginTop;
    }

    public static class Builder {

        @Inject
        ResourcesUtils resourcesUtils;

        private int marginTop;
        private int marginBottom;
        private int marginStart;
        private int marginEnd;

        private boolean addLastElementBottomMargin;

        public Builder() {
            App.getInstance().getAppComponent().inject(this);
        }

        public Builder setMarginTop(@DimenRes int marginTopDimenRes) {
            this.marginTop = getDimenPxSize(marginTopDimenRes);
            return this;
        }

        public Builder setMarginBottom(@DimenRes int marginBottomDimenRes) {
            this.marginBottom = getDimenPxSize(marginBottomDimenRes);
            return this;
        }

        public Builder setMarginStart(@DimenRes int marginStartDimenRes) {
            this.marginStart = getDimenPxSize(marginStartDimenRes);
            return this;
        }

        public Builder setMarginEnd(@DimenRes int marginEndDimenRes) {
            this.marginEnd = getDimenPxSize(marginEndDimenRes);
            return this;
        }

        public Builder setMarginHorizontal(@DimenRes int marginHorizontalDimenRes) {
            return setMarginStart(marginHorizontalDimenRes)
                    .setMarginEnd(marginHorizontalDimenRes);
        }

        public Builder setMarginVertical(@DimenRes int marginVerticalDimenRes) {
            return setMarginTop(marginVerticalDimenRes)
                    .setMarginBottom(marginVerticalDimenRes);
        }

        public Builder setMargin(@DimenRes int marginDimenRes) {
            return setMarginTop(marginDimenRes)
                    .setMarginBottom(marginDimenRes)
                    .setMarginStart(marginDimenRes)
                    .setMarginEnd(marginDimenRes);
        }

        public Builder setAddLastElementBottomMargin(boolean addLastElementBottomMargin) {
            this.addLastElementBottomMargin = addLastElementBottomMargin;
            return this;
        }

        private int getDimenPxSize(@DimenRes int dimenRes) {
            return resourcesUtils.getDimenPx(dimenRes);
        }

        public MarginDividerDecoration build() {
            return new MarginDividerDecoration(this);
        }
    }
}