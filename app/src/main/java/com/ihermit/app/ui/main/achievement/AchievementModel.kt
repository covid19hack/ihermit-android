package com.ihermit.app.ui.main.achievement

import android.view.View
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
        holder.binding.title.text = achievement.achievementName
    }
}

class AchievementHolder : EpoxyHolder() {
    lateinit var binding: AchievementItemBinding
    override fun bindView(itemView: View) {
        binding = AchievementItemBinding.bind(itemView)
    }
}
