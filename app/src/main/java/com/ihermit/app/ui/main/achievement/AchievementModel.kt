package com.ihermit.app.ui.main.achievement

import android.view.View
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.ihermit.app.R
import com.ihermit.app.data.entity.Achievement
import com.ihermit.app.databinding.AchievementItemBinding

@EpoxyModelClass(layout = R.layout.achievement_item)
abstract class AchievementModel : EpoxyModelWithHolder<AchievementHolder>() {
    @EpoxyAttribute
    lateinit var achievement: Achievement
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var clickListener: View.OnClickListener? = null

    override fun bind(holder: AchievementHolder) {
        super.bind(holder)
        holder.binding.root.setOnClickListener(clickListener)
        holder.binding.name.text = achievement.title
        holder.binding.description.text = achievement.description
        val isProgressVisible = achievement.goal > 1
        holder.binding.progress.isVisible = isProgressVisible
        holder.binding.progressText.isVisible = isProgressVisible
        holder.binding.progress.progress = achievement.progress
        holder.binding.progress.max = achievement.goal
        holder.binding.progressText.text = holder.binding.root.context.getString(
            R.string.progress_text_format,
            achievement.progress,
            achievement.goal
        )
        Glide.with(holder.binding.badge)
            .load(achievement.imgUrl)
            .placeholder(R.drawable.image_placeholder)
            .error(R.drawable.image_placeholder)
            .into(holder.binding.badge)
        holder.binding.badge.alpha = if (achievement.completed) 1.0F else 0.3F
    }
}

class AchievementHolder : EpoxyHolder() {
    lateinit var binding: AchievementItemBinding
    override fun bindView(itemView: View) {
        binding = AchievementItemBinding.bind(itemView)
    }
}
