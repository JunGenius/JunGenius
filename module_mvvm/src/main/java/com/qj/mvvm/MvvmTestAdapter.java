package com.qj.mvvm;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.qj.common.model.WanSubInfo;
import com.qj.mvvm.databinding.MvvmHomeItemBinding;


public class MvvmTestAdapter extends PagedListAdapter<WanSubInfo, MvvmTestAdapter.BindingHolder> {


    public MvvmTestAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        MvvmHomeItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.mvvm_home_item, parent, false);

        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder holder, int position) {
        holder.bindData(getItem(position));
    }


    // ============================  Holder =============================

    static class BindingHolder extends RecyclerView.ViewHolder {

        MvvmHomeItemBinding itemBinding;

        BindingHolder(MvvmHomeItemBinding itemBinding) {
            super(itemBinding.getRoot());

            this.itemBinding = itemBinding;
        }

        public void bindData(WanSubInfo item) {
            itemBinding.setMvvmItemModel(item);
        }
    }


    // ========================  public =================================

    public static final DiffUtil.ItemCallback<WanSubInfo> DIFF_CALLBACK =

            new DiffUtil.ItemCallback<WanSubInfo>() {
                @Override
                public boolean areItemsTheSame(@NonNull WanSubInfo oldUser, @NonNull WanSubInfo newUser) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldUser.getCourseId() == newUser.getCourseId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull WanSubInfo oldUser, @NonNull WanSubInfo newUser) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldUser == newUser;
                }
            };
}
