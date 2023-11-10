package com.example.tinderonsight.postdetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.tinderonsight.R
import com.example.tinderonsight.databinding.FragmentPostDetailsBinding
import com.example.tinderonsight.postfeed.PostFeedViewModel
import com.squareup.picasso.Picasso

private const val SIZE = 800
class PostDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = PostDetailsFragment()
    }

    private var _binding: FragmentPostDetailsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private  lateinit var viewModel: PostFeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PostFeedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.selectedPost.observe(viewLifecycleOwner) {
            binding.title.text = it.title
            Picasso.get()
                .load(it.url)
                .resize(SIZE,SIZE)
                .centerCrop()
                .into(binding.image)
            binding.author.text = getString(R.string.author_text, it.author)
            binding.upvotes.text = getString(R.string.post_ups, it.ups)
        }
    }

}