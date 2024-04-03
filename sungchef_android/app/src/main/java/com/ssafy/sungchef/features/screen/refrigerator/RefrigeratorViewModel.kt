package com.ssafy.sungchef.features.screen.refrigerator

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.sungchef.R
import com.ssafy.sungchef.commons.ALREADY_NICKNAME
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.commons.SERVER_INSTABILITY
import com.ssafy.sungchef.commons.WRONG_NICKNAME_FORMAT
import com.ssafy.sungchef.data.model.requestdto.ContactRequestDTO
import com.ssafy.sungchef.data.model.requestdto.UserUpdateRequestDTO
import com.ssafy.sungchef.data.model.responsedto.BookmarkRecipeList
import com.ssafy.sungchef.data.model.responsedto.BookmarkRecipeListData
import com.ssafy.sungchef.data.model.responsedto.IngredientItem
import com.ssafy.sungchef.data.model.responsedto.IngredientListData
import com.ssafy.sungchef.data.model.responsedto.MakeRecipeList
import com.ssafy.sungchef.data.model.responsedto.MakeRecipeListData
import com.ssafy.sungchef.data.model.responsedto.UserProfile
import com.ssafy.sungchef.data.model.responsedto.UserSimple
import com.ssafy.sungchef.domain.model.base.BaseModel
import com.ssafy.sungchef.domain.usecase.refrigerator.RefrigeratorUseCase
import com.ssafy.sungchef.domain.usecase.signup.DuplicateNicknameUseCase
import com.ssafy.sungchef.domain.usecase.user.SettingUseCase
import com.ssafy.sungchef.domain.usecase.user.UserSimpleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RefrigeratorViewModel @Inject constructor(
    private val refrigeratorUseCase: RefrigeratorUseCase
) : ViewModel() {


    var items = MutableStateFlow(mutableListOf<IngredientItem>())
    var items_detail = MutableStateFlow(mutableListOf(mutableListOf<IngredientListData>()))
    var isRefrigeratorEmpty : MutableStateFlow<Boolean> = MutableStateFlow(false)


    init {
        Log.d(TAG, "init 시작 ")
        getFridgeInfo()
    }

    fun getFridgeInfo() {
        items.value.clear()
        items_detail.value.clear()
        var itms = listOf(
            IngredientItem((R.drawable.fruit), 0, "과일"),
            IngredientItem((R.drawable.vegetable), 0, "채소"),
            IngredientItem((R.drawable.rice_grain), 0, "쌀/잡곡"),
            IngredientItem((R.drawable.meat_egg), 0, "육류"),
            IngredientItem((R.drawable.fish), 0, "수산"),
            IngredientItem((R.drawable.milk), 0, "유제품"),
            IngredientItem((R.drawable.sauce), 0, "조미료"),
            IngredientItem((R.drawable.etc), 0, "기타"),

            )
        items.value.addAll(itms)
        var detailitms = mutableListOf(
            mutableListOf<IngredientListData>(),
            mutableListOf<IngredientListData>(),
            mutableListOf<IngredientListData>(),
            mutableListOf<IngredientListData>(),
            mutableListOf<IngredientListData>(),
            mutableListOf<IngredientListData>(),
            mutableListOf<IngredientListData>(),
            mutableListOf<IngredientListData>(),
            )
        items_detail.value.addAll(detailitms)

        //API통신으로 데이터 가져오기
        viewModelScope.launch {
            refrigeratorUseCase.getFridgeIngredientList().collect(){
                Log.d(TAG, "viewModelScope.launch 시작 ")

                when(it){
                    is DataState.Success -> {
                        if(it.data.code == 200){
                            Log.d(TAG, "getFridgeInfo: Success")
                            var datalst = it.data.data.ingredientInfoList
                            for (idx in 0 until 8){
                                Log.d(TAG, "getFridgeInfo: $idx 번째 : ${datalst[idx].ingredientResList.size}")
                                var addlst = datalst[idx].ingredientResList.toSet().toList()

                                items.value[idx].num = addlst.size
                                addlst.forEach {detail ->
                                    items_detail.value[idx].add(IngredientListData( detail.ingredientName, detail.ingredientId,))
                                }
                            }
    //                        Log.d(TAG, "getFridgeInfo: ${ datalst}")
                        } else if(it.data.code == 204){
                            isRefrigeratorEmpty.value = true
                        }
                    }

                    is DataState.Error -> {
                        Log.d(TAG, "getFridgeInfo: Error")
                    }

                    is DataState.Loading -> {
                        Log.d(TAG, "getFridgeInfo: Loading")

                    }
                }
            }

        }
    }

    fun setIsRefrigeratorEmptyFalse(){
        isRefrigeratorEmpty.value = false
    }

}