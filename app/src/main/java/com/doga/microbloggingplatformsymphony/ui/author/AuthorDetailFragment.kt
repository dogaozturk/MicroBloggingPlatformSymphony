package com.doga.microbloggingplatformsymphony.ui.author

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
import com.doga.microbloggingplatformsymphony.databinding.FragmentAuthorDetailBinding
import com.doga.microbloggingplatformsymphony.domain.model.Author
import com.doga.microbloggingplatformsymphony.domain.model.Post
import com.doga.microbloggingplatformsymphony.ui.post.PostAdapter
import com.doga.microbloggingplatformsymphony.ui.post.PostDetailFragment
import com.doga.microbloggingplatformsymphony.ui.post.PostViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class AuthorDetailFragment : Fragment(), PostAdapter.PostItemClickListener {

    private lateinit var binding: FragmentAuthorDetailBinding
    var author : Author? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory//ViewModelFactory
    private lateinit var viewModel: PostViewModel

    companion object {
        private const val EXTRA_AUTHOR = "extra_author"
        fun newInstance(author : Author) : AuthorDetailFragment {
            val bundle = Bundle()
            val fragment = AuthorDetailFragment()
            bundle.putParcelable(EXTRA_AUTHOR,author)
            fragment.arguments = bundle
            return fragment
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        author = arguments?.getParcelable(EXTRA_AUTHOR)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_author_detail, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AndroidSupportInjection.inject(this)
        activity?.title = getString(R.string.title_author_profile)
        binding.author = author
        configureViewModel()
        updateUi()
        initAdapter()
    }

    private fun configureViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(PostViewModel::class.java)
        viewModel.init(author!!.id)
    }

    private fun updateUi() {
        binding.author = author
        Glide.with(binding.ivAuthorProfile.context)
            .load(author?.avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.ivAuthorProfile)
    }

    private fun initAdapter() {
        val adapter = PostAdapter(itemClickListener = this) {
            viewModel.retry()
        }
        binding.rvPosts.layoutManager = LinearLayoutManager(context)
        binding.rvPosts.adapter = adapter
        viewModel.posts?.observe(viewLifecycleOwner, Observer<PagedList<Post>> {
            adapter.submitList(it)
        })
    }

    override fun onItemClicked(post: Post) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.add(R.id.container, PostDetailFragment.newInstance(post))
            ?.addToBackStack(PostDetailFragment::class.java.name)
            ?.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.title = getString(R.string.title_author_list)
    }
}