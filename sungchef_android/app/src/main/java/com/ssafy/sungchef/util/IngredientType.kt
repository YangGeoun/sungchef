package com.ssafy.sungchef.util

import com.ssafy.sungchef.R

object IngredientType {
    val ingredientType = mapOf<String, Int>(
        "채소" to R.drawable.vegetable,
        "과일" to R.drawable.fruit,
        "쌀/잡곡" to R.drawable.rice_grain,
        "육류" to R.drawable.meat_egg,
        "수산" to R.drawable.fish,
        "유제품" to R.drawable.milk,
        "소스/양념" to R.drawable.sauce,
        "기타" to R.drawable.etc,
    )

    val ingredientEnglishType = mapOf<String, Int>(
        "VEGETABLE" to R.drawable.vegetable,
        "FRUIT" to R.drawable.fruit,
        "RICE_GRAIN" to R.drawable.rice_grain,
        "MEAT_EGG" to R.drawable.meat_egg,
        "FISH" to R.drawable.fish,
        "MILK" to R.drawable.milk,
        "SAUCE" to R.drawable.sauce,
        "ETC" to R.drawable.etc,
        "NOT_CONVERTED" to R.drawable.not_converted
    )

    fun transformToKorean(type : String) : String {
        return when (type) {
            "VEGETABLE" -> "채소"
            "FRUIT" -> "과일"
            "RICE_GRAIN" -> "쌀/잡곡"
            "MEAT_EGG" -> "육류"
            "FISH" -> "수산"
            "MILK" -> "유제품"
            "SAUCE" -> "소스/양념"
            "ETC" -> "기타"
            "NOT_CONVERTED" -> "수정이 필요해요!"
            else -> ""
        }
    }

    fun transformToEnglish(type : String) : String {
        return when (type) {
            "채소" -> "VEGETABLE"
            "과일" -> "FRUIT"
            "쌀/잡곡" -> "RICE_GRAIN"
            "육류" -> "MEAT_EGG"
            "수산" -> "FISH"
            "유제품" -> "MILK"
            "소스/양념" -> "SAUCE"
            "기타" -> "ETC"
            "수정이 필요해요!" -> "NOT_CONVERTED"
            else -> ""
        }
    }
}