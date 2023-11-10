package com.example.tinderonsight.postfeed

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tinderonsight.databinding.PostItemBinding
import com.example.tinderonsight.model.Post
import com.example.tinderonsight.model.PostDetails
import com.example.tinderonsight.utils.DateTimeUtils
import com.squareup.picasso.Picasso

class PostFeedAdapter(private var posts: List<Post>,
                      private val onItemClick: (PostDetails) -> Unit)
    : RecyclerView.Adapter<PostFeedAdapter.PostViewHolder>() {

    fun updateData(mData: List<Post>) {
        posts = mData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        with(holder){
            with(posts[position]) {
                binding.title.text = data.title
                binding.created.text = DateTimeUtils.timeAgo(data.created)
                Picasso
                    .get()
                    .load(data.thumbnail)
                    .resize(400, 400)
                    .centerInside()
                    .into(binding.image)

                holder.itemView.setOnClickListener {
                    onItemClick(data)
                    Toast.makeText(holder.itemView.context, data.title,
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    inner class PostViewHolder(val binding: PostItemBinding)
        :RecyclerView.ViewHolder(binding.root)

}