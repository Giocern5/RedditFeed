package com.example.tinderonsight.postfeed

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinderonsight.databinding.FragmentPostFeedBinding

class PostFeedFragment : Fragment() {

    companion object {
        val TAG ="HotFeedFragment"
    }
    //private val viewModel: PostFeedViewModel by viewModels()
    private  lateinit var viewModel: PostFeedViewModel
    private var _binding: FragmentPostFeedBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: PostFeedAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PostFeedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostFeedBinding.inflate(inflater, container, false)
        binding.postFeedRecyclerView.layoutManager =GridLayoutManager(context, 2)
        adapter = PostFeedAdapter(emptyList()) { post ->
            viewModel.setSelectedItem(post)
           findNavController().navigate(PostFeedFragmentDirections.postDetailsFragment())
        }

        binding.postFeedRecyclerView.adapter = adapter
        // Loads more pokemon when we scroll to the bottom of the recyclerview
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) &&
                    viewModel.isLoading.value == false
                ) {
                    viewModel.getHottestPost()
                }
            }
        }
        binding.postFeedRecyclerView.addOnScrollListener(scrollListener)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if(it)
                binding.progressbar.visibility = View.VISIBLE
            else
                binding.progressbar.visibility = View.GONE
        }

        viewModel.getHottestPost()

        viewModel.data.observe(viewLifecycleOwner) {
                adapter.updateData(it)
        }

    }
}