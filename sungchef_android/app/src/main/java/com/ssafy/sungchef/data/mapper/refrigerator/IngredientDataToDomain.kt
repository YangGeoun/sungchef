package com.ssafy.sungchef.data.mapper.refrigerator

import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.ingredient.search.SearchIngredientResponse
import com.ssafy.sungchef.domain.model.refrigerator.RegisterReceiptState
import com.ssafy.sungchef.domain.model.refrigerator.SearchIngredient
import com.ssafy.sungchef.domain.model.user.LoginState

fun List<SearchIngredientResponse>.toSearchIngredient() : List<SearchIngredient> {
    return this.map{ response ->
        SearchIngredient(
            response.ingredientId,
            transformIngredientType(response.ingredientType),
            response.ingredientName
        )
    }
}

// TODO Enum 클래스로 리팩토링 필요
fun transformIngredientType(type : String) : String {
    return when (type) {
        "FRUIT" -> "과일"
        "VEGETABLE" -> "채소"
        "RICE_GRAIN" -> "쌀/잡곡"
        "MEAT_EGG" -> "육류"
        "FISH" -> "수산"
        "MILK" -> "유제품"
        "SAUCE" -> "소스/양념"
        "ETC" -> "기타"
        else -> ""
    }
}

fun<T> ResponseDto<T>.toRegisterReceiptState() : RegisterReceiptState {
    return RegisterReceiptState(
        code = this.code,
        dialogTitle = this.message,
        imageUrl = this.data as String
    )
}

//"FRUIT" to "과일",
//"VEGETABLE" to "채소",
//"RICE_GRAIN" to "쌀/잡곡",
//"MEAT_EGG" to "육류",
//"FISH" to "수산",
//"MILK" to "유제품",
//"SAUCE" to "소스/양념",
//"ETC" to "기타"