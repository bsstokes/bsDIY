package com.bsstokes.bsdiy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bsstokes.bsdiy.api.DiyApi;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<DiyApi.Skill> skills = Collections.emptyList();

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
        final DiyApi.Skill skill = getSkill(position);
        holder.skillTitleTextView.setText(skill.title);
    }

    @Override
    public int getItemCount() {
        return skills.size();
    }

    public void loadSkills(List<DiyApi.Skill> skills) {
        this.skills = skills;
        notifyDataSetChanged();
    }

    private DiyApi.Skill getSkill(int position) {
        return skills.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.skill_title_text_view) TextView skillTitleTextView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
