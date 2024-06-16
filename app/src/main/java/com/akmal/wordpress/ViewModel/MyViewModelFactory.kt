package com.akmal.wordpress.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.akmal.wordpress.repositories.MyRepository

class MyViewModelFactory(val myRepository: MyRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyViewModel(myRepository) as T
    }
}