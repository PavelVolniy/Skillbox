package com.example.retrofitapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val retrofitRepo: RetrofitRepo
) : ViewModel() {
    private val _viewCard: MutableStateFlow<ViewCardUser?> = MutableStateFlow(null)
    val viewCard = _viewCard.asStateFlow()

    fun getUserData() {
        viewModelScope.launch {
            val user = retrofitRepo.getData("some text").results[0]

            val userCard = ViewCardUser(
                user.name.first,
                user.name.last,
                user.dob.age.toString(),
                user.email,
                user.name.title,
                user.picture.large,
                user.phone,
                user.registered.date,
            )
            _viewCard.value = userCard
        }
    }
}