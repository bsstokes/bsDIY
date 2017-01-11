package com.bsstokes.bsdiy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;

class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;

    SkillsAdapter(@NonNull Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = layoutInflater.inflate(R.layout.skills_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final TextView titleTextView = ButterKnife.findById(holder.itemView, R.id.skill_title_text_view);
        titleTextView.setText("Skill #" + position);
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
