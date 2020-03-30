package com.ihermit.app.ui.main

import android.text.format.DateUtils
import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.ihermit.app.R
import com.ihermit.app.data.entity.Achievement
import com.ihermit.app.data.entity.Breach
import com.ihermit.app.databinding.ActionItemBinding

@EpoxyModelClass(layout = R.layout.action_item)
abstract class ActionItemModel : EpoxyModelWithHolder<ActionItemHolder>() {

    @EpoxyAttribute
    lateinit var actionItem: ActionItem
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var clickListener: View.OnClickListener? = null

    override fun bind(holder: ActionItemHolder) {
        super.bind(holder)
        when (val item = actionItem) {
            is ActionItem.BreachAction -> {
                holder.binding.badge.setImageResource(R.drawable.image_breach)
                holder.binding.actionType.setText(R.string.breach_dialog_title)
                holder.binding.name.setText(R.string.breach_dialog_name)
                holder.binding.description.text = DateUtils.getRelativeTimeSpanString(
                    holder.binding.root.context,
                    item.breach.createdAt.time
                )
            }
            is ActionItem.NonCompletedAchievement -> {
                Glide.with(holder.binding.badge)
                    .load(item.achievement.imgUrl)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .into(holder.binding.badge)
                holder.binding.actionType.setText(R.string.challenge_of_the_day)
                holder.binding.name.text = item.achievement.title
                holder.binding.description.text = item.achievement.description
            }
        }
        holder.binding.root.setOnClickListener(clickListener)
    }
}

sealed class ActionItem {
    data class BreachAction(val breach: Breach) : ActionItem()
    data class NonCompletedAchievement(val achievement: Achievement) : ActionItem()
}

class ActionItemHolder : EpoxyHolder() {
    lateinit var binding: ActionItemBinding
    override fun bindView(itemView: View) {
        binding = ActionItemBinding.bind(itemView)
    }

}
