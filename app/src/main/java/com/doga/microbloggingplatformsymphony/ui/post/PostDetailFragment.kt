package com.doga.microbloggingplatformsymphony.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.doga.microbloggingplatformsymphony.R
import com.doga.microbloggingplatformsymphony.databinding.FragmentPostDetailBinding
import com.doga.microbloggingplatformsymphony.domain.model.Comment
import com.doga.microbloggingplatformsymphony.domain.model.Post
import com.doga.microbloggingplatformsymphony.ui.comment.CommentAdapter
import com.doga.microbloggingplatformsymphony.ui.comment.CommentViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class PostDetailFragment : Fragment() {

    private lateinit var binding: FragmentPostDetailBinding
    var post : Post? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: CommentViewModel

    companion object {
        private const val EXTRA_POST = "extra_post"
        fun newInstance(post : Post) : PostDetailFragment {
            val bundle = Bundle()
            val fragment = PostDetailFragment()
            bundle.putParcelable(EXTRA_POST,post)
            fragment.arguments = bundle
            return fragment
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        post = arguments?.getParcelable(EXTRA_POST)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_detail, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AndroidSupportInjection.inject(this)
        activity?.title = getString(R.string.title_post_detail)
        binding.post = post
        configureViewModel()
        updateUi()
        initAdapter()
    }

    private fun configureViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(CommentViewModel::class.java)
        viewModel.init(post!!.id)
    }

    private fun updateUi() {
        Glide.with(binding.ivPostProfile.context)
            .load(post?.imageUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.ivPostProfile)
    }

    private fun initAdapter() {
        val adapter = CommentAdapter() {
            viewModel.retry()
        }
        binding.rvPosts.layoutManager = LinearLayoutManager(context)
        binding.rvPosts.adapter = adapter
        viewModel.comments?.observe(viewLifecycleOwner, Observer<PagedList<Comment>> {
            adapter.submitList(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.title = getString(R.string.title_author_profile)
    }

}