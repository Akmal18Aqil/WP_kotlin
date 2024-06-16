package com.akmal.wordpress.ViewModel

import androidx.lifecycle.ViewModel
import com.akmal.wordpress.repositories.MyRepository

class MyViewModel(val myRepository: MyRepository): ViewModel() {
    var currentPage = 0
    val artikelLiveData get() = myRepository.liveData
    fun loadArtikel() {
        currentPage++
        myRepository.GetArtikel(currentPage.toString())
    }

}