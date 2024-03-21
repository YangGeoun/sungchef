package com.ssafy.sungchef.features.screen.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.responsedto.MakeRecipeList
import com.ssafy.sungchef.data.model.responsedto.MakeRecipeListData
import com.ssafy.sungchef.data.model.responsedto.RecipeList
import com.ssafy.sungchef.data.model.responsedto.UserProfile
import com.ssafy.sungchef.data.model.responsedto.UserSimple
import com.ssafy.sungchef.domain.usecase.RecommendationUseCase
import com.ssafy.sungchef.domain.usecase.UserSimpleUseCase
import com.ssafy.sungchef.domain.viewstate.recommendation.RecommendationViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userSimpleUseCase: UserSimpleUseCase
) : ViewModel() {

    private val _userSimpleData = MutableStateFlow<UserSimple?>(UserSimple(0,"", UserProfile("","",0,0)))
    val userSimpleData: StateFlow<UserSimple?> = _userSimpleData.asStateFlow()

    private val _makeRecipeListData = MutableStateFlow<MakeRecipeList>(MakeRecipeList(0, "", MakeRecipeListData(0, listOf())))
    val makeRecipeListData: StateFlow<MakeRecipeList> = _makeRecipeListData.asStateFlow()

    var cnt = 0
//    init {
//        getUserSimple()
//    }


    fun getUserSimple() {
        viewModelScope.launch {
            val userSimple = userSimpleUseCase.userSimple()
            Log.d(TAG, "getUserSimple: $userSimple");
            _userSimpleData.value = userSimple
        }
    }

    fun getMakeRecipeList(page : Int){
        viewModelScope.launch {
            val makeRecipeListResponse = userSimpleUseCase.makeRecipeList(page)
            Log.d(TAG, "getMakeRecipeList: $makeRecipeListResponse")
            val updatedMakeRecipeList = _makeRecipeListData.value.data.makeRecipeList.toMutableList().apply {
                makeRecipeListResponse.data.makeRecipeList.forEach {
                    Log.d(TAG, "getMakeRecipeList: viewmodel : ${cnt++}")
                    add(it)
                }
            }
            _makeRecipeListData.value= MakeRecipeList(
                makeRecipeListResponse.code, makeRecipeListResponse.message,
                MakeRecipeListData(makeRecipeListResponse.data.makeRecipeCount,
                    updatedMakeRecipeList)
            )
        }
    }

}