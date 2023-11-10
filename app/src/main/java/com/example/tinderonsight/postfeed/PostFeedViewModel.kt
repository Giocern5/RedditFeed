package com.example.tinderonsight.postfeed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinderonsight.model.Post
import com.example.tinderonsight.model.PostDetails
import com.example.tinderonsight.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class PostFeedViewModel : ViewModel() {
    companion object {
        val TAG = "PostFeedViewModel"
    }
    private val _data = MutableLiveData<List<Post>>()
    val data: LiveData<List<Post>> = _data
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _selectedPost = MutableLiveData<PostDetails>()
    val selectedPost: LiveData<PostDetails> = _selectedPost
    private var after: String? = null
    private val limit = 40

    fun getHottestPost() {
        _isLoading.value = true
        viewModelScope.launch() {
            try {
                val res = RetrofitInstance.instance.getHotPosts(after, limit)
                if (res.isSuccessful) {
                    val newData = res.body()?.data?.children
                    newData?.let {
                        val nonVideoPosts = it.filter { postDetails -> !postDetails.data.is_video }
                        val currentData = _data.value?.toMutableList() ?: mutableListOf()
                        currentData.addAll(nonVideoPosts)
                        _data.postValue(currentData)
                        _data   .value
                        // Update the 'after' parameter for the next page
                        after = res.body()?.data?.after
                    }
                    Log.e(TAG, res.body().toString())
                } else {
                    Log.e(TAG, res.message().toString())
                }
            } catch (err: IOException) {
                Log.e(TAG, err.message.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun setSelectedItem(post: PostDetails) {
        _selectedPost.postValue(post)
    }
}