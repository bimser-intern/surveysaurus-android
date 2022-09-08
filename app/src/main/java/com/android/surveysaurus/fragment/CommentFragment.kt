package com.android.surveysaurus.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.android.surveysaurus.adapter.CommentAdapter
import com.android.surveysaurus.adapter.SlidePageAdapter2
import com.android.surveysaurus.adapter.SurveyAdapter
import com.android.surveysaurus.databinding.FragmentCommentBinding
import com.android.surveysaurus.model.ListedComment
import com.android.surveysaurus.service.ApiService
import java.util.*
import kotlin.collections.ArrayList


abstract class CommentFragment : Fragment(), CommentAdapter.Listener {
    private var _binding: FragmentCommentBinding?=null
    private val binding get() = _binding!!
    private lateinit var commentAdapter: CommentAdapter
    private var Comments: ArrayList<ListedComment> = ArrayList()
    private var CommentModels: ArrayList<ListedComment> = ArrayList()
    private var controlComment:Boolean=false
    private var index:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommentBinding.inflate(inflater, container, false)
        val view = binding.root
        val layoutManager =  GridLayoutManager(view.context, 2)
        binding.commentRecycler.setHasFixedSize(false);
        binding.commentRecycler.layoutManager = layoutManager
        commentAdapter = CommentAdapter(CommentModels, this@CommentFragment)
        binding.commentRecycler.adapter = commentAdapter

        try {
            val apiService = ApiService()
            apiService.getComments (title=""){ item->
                if(item!=null){
                    if(controlComment==false){
                        Comments.addAll(item)
                        var myComment: ListedComment = ListedComment(comment ="" , commentID =0, report =0, upvote =0, surveytitle = "", author ="", deletable =false, time = Date() )

                        Comments.add(myComment)
                        commentAdapter.notifyDataSetChanged()


                        binding.commentRecycler.refreshDrawableState()
                        binding.commentRecycler.requestLayout()
                        println("oldu bu iş")
                        controlComment=true
                    }


                }
                else {
                    Toast.makeText(
                        view.context,
                        "Fail", Toast.LENGTH_SHORT
                    ).show();

                }
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
        }

        return  view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}