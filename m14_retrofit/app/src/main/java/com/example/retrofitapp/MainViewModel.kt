package com.example.retrofitapp

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val retrofitRepo: RetrofitRepo
) : ViewModel() {
    private val _viewCard: MutableStateFlow<UserCardModel?> = MutableStateFlow(null)
    val viewCard = _viewCard.asStateFlow()
    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Created)
    val state = _state.asStateFlow()

    fun getUserData(imageView: ImageView) {
        viewModelScope.launch {
            _state.value = State.Loading
            val user = retrofitRepo.getData().results[0]

            val userCard = UserCardModel(
                name = "Name: ${user.name.first}",
                secondName = "secondName: ${user.name.last}",
                age = "Age: ${user.dob.age}",
                email = "Email: ${user.email}",
                nickName = user.login.username,
                pictureUri = user.picture.large,
                phone = "phone number: ${user.phone}",
                dateOfRegistration = "date of registration: ${user.registered.date}",
            )
            Glide.with(imageView.context)
                .load(userCard.pictureUri)
                .into(imageView)
            Log.e("image", user.picture.large)
            _viewCard.value = userCard
            _state.value = State.Success
        }
    }
}