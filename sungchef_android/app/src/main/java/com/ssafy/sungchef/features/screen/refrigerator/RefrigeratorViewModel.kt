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
import com.ssafy.sungchef.data.model.responsedto.MakeRecipeList
import com.ssafy.sungchef.data.model.responsedto.MakeRecipeListData
import com.ssafy.sungchef.data.model.responsedto.UserProfile
import com.ssafy.sungchef.data.model.responsedto.UserSimple
import com.ssafy.sungchef.domain.model.base.BaseModel
import com.ssafy.sungchef.domain.usecase.signup.DuplicateNicknameUseCase
import com.ssafy.sungchef.domain.usecase.user.SettingUseCase
import com.ssafy.sungchef.domain.usecase.user.UserSimpleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RefrigeratorViewModel @Inject constructor(

) : ViewModel() {


    var items = MutableStateFlow(mutableListOf<IngredientItem>())


    init {
        getFridgeInfo()
    }

    fun getFridgeInfo() {
        val itms = listOf(
            IngredientItem((R.drawable.fruit), -1, "과일"),
            IngredientItem((R.drawable.vegetable), -1, "채소"),
            IngredientItem((R.drawable.rice_grain), -1, "쌀/잡곡"),
            IngredientItem((R.drawable.meat_egg), -1, "육류"),
            IngredientItem((R.drawable.fish), -1, "수산"),
            IngredientItem((R.drawable.milk), -1, "유제품"),
            IngredientItem((R.drawable.sauce), -1, "조미료"),
            IngredientItem((R.drawable.etc), -1, "기타"),

            )
        items.value.addAll(itms)

        //API통신으로 데이터 가져오기
    }




}