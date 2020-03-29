package com.ihermit.app.ui.main.achievement

import android.view.View
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.ihermit.app.R
import com.ihermit.app.data.entity.Achievement
import com.ihermit.app.databinding.AchievementItemBinding

@EpoxyModelClass(layout = R.layout.achievement_item)
abstract class AchievementModel : EpoxyModelWithHolder<AchievementHolder>() {
    @EpoxyAttribute
    lateinit var achievement: Achievement

    override fun bind(holder: AchievementHolder) {
        super.bind(holder)
        holder.binding.name.text = achievement.achievementName
        holder.binding.description.text = achievement.achievementDescription
        val isProgressVisible = achievement.achievementGoal > 1
        holder.binding.progress.isVisible = isProgressVisible
        holder.binding.progressText.isVisible = isProgressVisible
        holder.binding.progress.progress = achievement.achievementProgress
        holder.binding.progress.max = achievement.achievementGoal
        holder.binding.progressText.text = holder.binding.root.context.getString(
            R.string.progress_text_format,
            achievement.achievementProgress,
            achievement.achievementGoal
        )
    }
}

class AchievementHolder : EpoxyHolder() {
    lateinit var binding: AchievementItemBinding
    override fun bindView(itemView: View) {
        binding = AchievementItemBinding.bind(itemView)
    }
}
