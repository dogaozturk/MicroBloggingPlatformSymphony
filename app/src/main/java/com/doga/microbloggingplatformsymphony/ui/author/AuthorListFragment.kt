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
import com.doga.microbloggingplatformsymphony.R
import com.doga.microbloggingplatformsymphony.databinding.FragmentAuthorListBinding
import com.doga.microbloggingplatformsymphony.domain.model.Author
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class AuthorListFragment : Fragment(), AuthorAdapter.AuthorItemClickListener {

    private lateinit var binding: FragmentAuthorListBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {
        fun newInstance() = AuthorListFragment()
    }

    private lateinit var viewModel: AuthorViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_author_list, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AndroidSupportInjection.inject(this)
        activity?.title = getString(R.string.title_author_list)
        configureViewModel()
        initAdapter()
    }

    private fun configureViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(AuthorViewModel::class.java)
    }

    private fun initAdapter() {
        val adapter = AuthorAdapter(itemClickListener = this) {
            viewModel.retry()
        }
        binding.rvAuthors.layoutManager = LinearLayoutManager(context)
        binding.rvAuthors.adapter = adapter
        viewModel.authors.observe(viewLifecycleOwner,Observer<PagedList<Author>> {
            adapter.submitList(it)
        })
    }

    override fun onItemClicked(author: Author) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.add(R.id.container, AuthorDetailFragment.newInstance(author))
            ?.addToBackStack(AuthorDetailFragment::class.java.name)
            ?.commit()
    }

}
