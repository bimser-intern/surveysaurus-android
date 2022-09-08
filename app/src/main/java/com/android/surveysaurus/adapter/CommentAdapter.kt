package com.android.surveysaurus.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View.generateViewId
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.allViews
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import androidx.recyclerview.widget.RecyclerView
import com.android.surveysaurus.R
import com.android.surveysaurus.databinding.CommentLayoutBinding
import com.android.surveysaurus.databinding.SurveyLayoutBinding
import com.android.surveysaurus.fragment.MySurveyFragmentDirections
import com.android.surveysaurus.model.ListedComment

class CommentAdapter(
    private val commentList: ArrayList<ListedComment>,
    private val listener: Listener) : RecyclerView.Adapter<CommentAdapter.CommentHolder>() {

    interface Listener {
        fun onItemClick(myCommentModel: ListedComment)
        fun onItemClick2()
    }


    private val imageList: ArrayList<Int> =
        arrayListOf(R.drawable.ic_upvoteempty, R.drawable.ic_reply, R.drawable.ic_reportempty)

    class CommentHolder(val binding: CommentLayoutBinding) : RecyclerView.ViewHolder(binding.root) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val binding =
            CommentLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CommentHolder(binding)

    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        holder.setIsRecyclable(false)


        if (!commentList.get(position).comment.isNullOrEmpty()) {
            holder.binding.commentRecycler.text = commentList.get(position).comment
            holder.binding.authornameRecycler.text = commentList.get(position).comment
            holder.binding.authorPicture.setImageResource(imageList.get(position % 3))
            holder.binding.upvotePicture.setImageResource(imageList.get(position % 3))
            holder.binding.replyPicture.setImageResource(imageList.get(position % 3))
            holder.binding.reportPicture.setImageResource(imageList.get(position % 3))

        }

        holder.itemView.setOnClickListener {

            listener.onItemClick(commentList.get(position))

        }

    }
    override fun getItemCount(): Int {
        return commentList.size
    }

}

