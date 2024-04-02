package com.ssafy.sungchef.data.mapper.refrigerator

import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.ingredient.search.SearchIngredientResponse
import com.ssafy.sungchef.data.model.responsedto.ocr.ConvertInfo
import com.ssafy.sungchef.data.model.responsedto.ocr.OcrResponse
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

fun OcrResponse.toMapIngredient() : Map<String, List<ConvertInfo>> {

    // NOT_CONVERTED 항목을 먼저 찾습니다.
    val notConverted = this.convertProductList.find { it.ingredientType == "NOT_CONVERTED" }
    // NOT_CONVERTED를 제외한 나머지 항목들을 처리합니다.
    val rest = this.convertProductList.filterNot { it.ingredientType == "NOT_CONVERTED" }

    val ingredientMap = mutableMapOf<String, List<ConvertInfo>>()

    // NOT_CONVERTED가 있으면 맨 앞에 추가합니다.
//    notConverted?.let {
//        ingredientMap[it.ingredientType] = it.convertProductList
//    }

    notConverted?.let {
//        ingredientMap[it.ingredientType] = listOf()

        val notConvertList = it.convertProductList
        val tempList = mutableListOf<ConvertInfo>()
        var tempId = -1

        // 고유한 id를 갖도록 음수 id를 바꿔줌
        for (notConvert in notConvertList) {
            val tempConvertInfo = ConvertInfo(
                notConvert.convertedName,
                tempId,
                notConvert.converted
            )
            tempList.add(tempConvertInfo)
            tempId--
        }

        ingredientMap[it.ingredientType] = tempList
    }




    // 나머지 항목들을 추가합니다.
    rest.forEach { convertIngredient ->

        // id가 -1이 아니면 중복 제거
        val uniqueItems = convertIngredient.convertProductList
            .filter { it.ingredientId != -1 }
            .distinctBy { it.ingredientId }

        ingredientMap[convertIngredient.ingredientType] = uniqueItems
    }

    return ingredientMap
}

//"FRUIT" to "과일",
//"VEGETABLE" to "채소",
//"RICE_GRAIN" to "쌀/잡곡",
//"MEAT_EGG" to "육류",
//"FISH" to "수산",
//"MILK" to "유제품",
//"SAUCE" to "소스/양념",
//"ETC" to "기타"