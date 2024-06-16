package com.akmal.wordpress

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.akmal.wordpress.ViewModel.MyViewModel
import com.akmal.wordpress.ViewModel.MyViewModelFactory
import com.akmal.wordpress.adapter.ArtikelAdapter
import com.akmal.wordpress.adapter.LAYOUT_CARD
import com.akmal.wordpress.adapter.LAYOUT_LIST
import com.akmal.wordpress.databinding.ActivityMainBinding
import com.akmal.wordpress.model.ArtikelModel
import com.akmal.wordpress.repositories.ApiResponse
import com.akmal.wordpress.repositories.MyRepository
import com.akmal.wordpress.utilities.logError
import com.akmal.wordpress.utilities.showMessage
import com.android.volley.VolleyLog.TAG

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val activity = this
    private lateinit var viewModel: MyViewModel
    private val refreshLiveData =MutableLiveData<Boolean>()
    val list: ArrayList<ArtikelModel> = ArrayList()
    val adapter = ArtikelAdapter(list, activity)
    var layoutCurrent = LAYOUT_LIST
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
            setupRecyclerView()
            loadArticlesFromBackend()
        }
    }
    private fun setupRecyclerView(){
        binding.apply {
            binding.rvListberita.adapter = adapter
            binding.rvListberita.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1))
                        viewModel.loadArtikel()
                }
            })
        }

    }
    private fun loadArticlesFromBackend() {
        val myRepository = MyRepository(context = activity)
        viewModel = ViewModelProvider(
            activity,
            MyViewModelFactory(myRepository = myRepository)
        )[MyViewModel::class.java]
        viewModel.loadArtikel()
        viewModel.artikelLiveData.observe(this) {
//            logInfo(TAG,it.toString())
            when (it) {
                is ApiResponse.Error -> {
                    showMessage(activity, "Error ${it.errorMassage}")
                    logError(TAG, "Error ${it.errorMassage}")
                    refreshLiveData.value = false
                }

                is ApiResponse.Loading -> {
//                    showMessage(activity, "Loading")
                    refreshLiveData.value = true
                }

                is ApiResponse.Okay -> {
                    refreshLiveData.value = false
                    if (it.data!!.isNotEmpty()) {
                        it.data?.forEach { model ->
                            list.add(model)
                        }
                        adapter.notifyDataSetChanged()
                    }

                }

                else -> {}
            }
        }

    }
    private fun updateRefreshLayout() {
        updateShimmerLayout(true)
        refreshLiveData.observe(this) {
            binding.refreshLayout.isRefreshing = it
            updateShimmerLayout(it)

        }
        binding.refreshLayout.setOnRefreshListener {
            viewModel.currentPage = 0
            viewModel.loadArtikel()
        }
    }
    private fun updateShimmerLayout(isLoaded: Boolean) {
        binding.apply {
            if (!isLoaded) {
                mCardShimmerHolder.visibility = View.GONE
                mListShimmerHolder.visibility = View.GONE
                return
            }
            if (layoutCurrent == LAYOUT_CARD) {
                mCardShimmerHolder.visibility = View.VISIBLE
                mListShimmerHolder.visibility = View.GONE
            } else {
                mCardShimmerHolder.visibility = View.GONE
                mListShimmerHolder.visibility = View.VISIBLE
            }

        }

    }
}



