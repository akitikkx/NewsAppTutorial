package com.ahmedtikiwa.mynewsapp.ui.headlines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmedtikiwa.mynewsapp.R
import com.ahmedtikiwa.mynewsapp.databinding.FragmentHeadlinesBinding
import com.ahmedtikiwa.mynewsapp.domain.Article

class HeadlinesFragment : Fragment() {

    private lateinit var viewModelAdapter: HeadlinesAdapter

    private lateinit var binding : FragmentHeadlinesBinding

    private val viewModel: HeadlinesViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated"
        }
        ViewModelProviders.of(
            this,
            HeadlinesViewModel.Factory(activity.application)
        ).get(HeadlinesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = viewLifecycleOwner

        // Giving the binding access to the HeadlinesViewModel
        binding.viewModel = viewModel

        // When the headline item is clicked then the HeadlineViewModel#displayHeadlineDetails
        // is called
        viewModelAdapter = HeadlinesAdapter(HeadlinesAdapter.HeadlineClickListener {
            viewModel.displayHeadlineDetails(it)
        })

        binding.root.findViewById<RecyclerView>(R.id.headline_list).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.headlinesList.observe(viewLifecycleOwner, Observer<List<Article>> { articles ->
            articles?.apply {
                viewModelAdapter.headlines = articles
            }
        })

        viewModel.navigateToSelectedHeadline.observe(this, Observer {
            if (null != it) {
                // TODO use Navigation to navigate to the selected headline
                viewModel.displayHeadlineDetailsComplete()
            }
        })
    }

}