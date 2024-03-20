package com.ssafy.sungchef.features.screen.signup


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(

) : ViewModel(){

    val topBarNumber = mutableIntStateOf(1)
    val nickname: MutableState<String> = mutableStateOf("")
    val birth = mutableStateOf("")
    val gender = mutableStateOf("")

    fun checkNickname(): Boolean {
        val length = nickname.value.length

        return length !in 2..10 && length != 0
    }
}