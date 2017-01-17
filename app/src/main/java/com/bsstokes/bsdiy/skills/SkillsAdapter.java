package com.bsstokes.bsdiy.skills;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bsstokes.bsdiy.R;
import com.bsstokes.bsdiy.api.DiyApi;
import com.bsstokes.bsdiy.db.Skill;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.ViewHolder> {

    interface OnClickItemListener {
        void onClickSkill(long skillId);
    }

    private final @NonNull LayoutInflater layoutInflater;
    private final @NonNull Picasso picasso;
    private final @NonNull OnClickItemListener listener;

    private List<Skill> skills = Collections.emptyList();

    SkillsAdapter(@NonNull Context context, @NonNull Picasso picasso, @NonNull OnClickItemListener listener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.picasso = picasso;
        this.listener = listener;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = layoutInflater.inflate(R.layout.skills_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Skill skill = getSkill(position);
        holder.bindSkill(skill);
        holder.skillTitleTextView.setText(skill.getTitle());

        final String imageUrl = chooseImage(skill);
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
        return getSkill(position).getId();
    }

    void loadSkills(List<Skill> skills) {
        this.skills = skills;
        notifyDataSetChanged();
    }

    private Skill getSkill(int position) {
        return skills.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.skill_title_text_view) TextView skillTitleTextView;
        @BindView(R.id.skill_patch_image_view) ImageView skillPatchImageView;

        private long skillId = 0;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindSkill(@NonNull Skill skill) {
            skillId = skill.getId();
        }

        @OnClick
        void onClick() {
            listener.onClickSkill(skillId);
        }
    }

    @Nullable
    private String chooseImage(@NonNull Skill skill) {
        if (!TextUtils.isEmpty(skill.getImageLarge())) {
            return DiyApi.Helper.normalizeUrl(skill.getImageLarge());
        } else if (!TextUtils.isEmpty(skill.getImageMedium())) {
            return DiyApi.Helper.normalizeUrl(skill.getImageMedium());
        } else if (!TextUtils.isEmpty(skill.getImageSmall())) {
            return DiyApi.Helper.normalizeUrl(skill.getImageSmall());
        } else {
            return null;
        }
    }
}
