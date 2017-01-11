package com.bsstokes.bsdiy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bsstokes.bsdiy.api.DiyApi;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private final Picasso picasso;

    private List<DiyApi.Skill> skills = Collections.emptyList();

    SkillsAdapter(@NonNull Context context, Picasso picasso) {
        layoutInflater = LayoutInflater.from(context);
        this.picasso = picasso;
        setHasStableIds(true);
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

        final String imageUrl = chooseImage(skill);
        Log.d("SkillsAdapter", "onBindViewHolder: [" + position + "] imageUrl=" + imageUrl);
        if (!TextUtils.isEmpty(imageUrl)) {
            picasso.load(imageUrl).into(holder.skillPatchImageView);
        }
    }

    @Override
    public int getItemCount() {
        return skills.size();
    }

    @Override
    public long getItemId(int position) {
        return getSkill(position).id;
    }

    void loadSkills(List<DiyApi.Skill> skills) {
        this.skills = skills;
        notifyDataSetChanged();
    }

    private DiyApi.Skill getSkill(int position) {
        return skills.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.skill_title_text_view) TextView skillTitleTextView;
        @BindView(R.id.skill_patch_image_view) ImageView skillPatchImageView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Nullable
    private String chooseImage(@NonNull DiyApi.Skill skill) {
        final DiyApi.Skill.Images images = skill.images;
        if (null == images) {
            return null;
        } else if (!TextUtils.isEmpty(images.medium)) {
            return DiyApi.Helper.normalizeUrl(images.medium);
        } else if (!TextUtils.isEmpty(images.large)) {
            return DiyApi.Helper.normalizeUrl(images.large);
        } else if (!TextUtils.isEmpty(images.small)) {
            return DiyApi.Helper.normalizeUrl(images.small);
        } else {
            return null;
        }
    }
}
