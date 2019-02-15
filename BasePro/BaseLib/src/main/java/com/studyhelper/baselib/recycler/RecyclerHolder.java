package com.studyhelper.baselib.recycler;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import java.util.List;

/**
 * RecyclerView.ViewHolder做成一个中间类
 * Create on 17-9-14.
 * @author jogern
 */

final class RecyclerHolder<A extends BaseRecyclerItemAdapter> extends RecyclerView.ViewHolder {

      private SparseArray<View>     mViews = new SparseArray<>();
      private RecyclerViewHolder<A> mViewHolder;

      RecyclerHolder(View itemView, A adp, RecyclerViewHolder<A> viewHolder) {
            super(itemView);
            mViewHolder = viewHolder;
            mViewHolder.setHolder(adp, this);
            viewHolder.initHolderView();
      }

      View findViewById(@IdRes int id) {
            View viewById = mViews.get(id);
            if (viewById == null) {
                  viewById = itemView.findViewById(id);
                  mViews.put(id, viewById);
            }
            return viewById;
      }

      void onBinder(int position, List<Object> payloads) {
            mViewHolder.onBinder(position, payloads);
      }

      void onViewRecycled() {
            mViewHolder.onViewRecycled();
      }

      Context getContext() {
            return itemView.getContext();
      }

      @Override
      protected void finalize() throws Throwable {
            mViewHolder = null;
            mViews.clear();
            super.finalize();
      }
}