package com.ssafy.sungchef.features.screen.cooking

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.os.bundleOf
import androidx.lifecycle.viewModelScope
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.requestdto.RegisterCookingDTO
import com.ssafy.sungchef.domain.model.ingredient.Ingredient
import com.ssafy.sungchef.domain.model.ingredient.IngredientId
import com.ssafy.sungchef.domain.model.ingredient.IngredientInfo
import com.ssafy.sungchef.domain.model.ingredient.IngredientList
import com.ssafy.sungchef.domain.model.ingredient.LackIngredient
import com.ssafy.sungchef.domain.usecase.cooking.GetRecipeStepUseCase
import com.ssafy.sungchef.domain.usecase.cooking.GetUsedIngredientUseCase
import com.ssafy.sungchef.domain.usecase.cooking.RegisterCookingUseCase
import com.ssafy.sungchef.domain.usecase.refrigerator.DeleteIngredientUseCase
import com.ssafy.sungchef.domain.usecase.refrigerator.RegisterNeedIngredientUseCase
import com.ssafy.sungchef.domain.viewstate.cooking.CookingViewState
import com.ssafy.sungchef.features.screen.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import java.util.Collections
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class CookingViewModel @Inject constructor(
    private val getUsedIngredientUseCase: GetUsedIngredientUseCase,
    private val getRecipeStepUseCase: GetRecipeStepUseCase,
    private val registerCookingUseCase: RegisterCookingUseCase,
    private val deleteIngredientUseCase: DeleteIngredientUseCase,
    private val registerNeedIngredientUseCase: RegisterNeedIngredientUseCase
) : BaseViewModel<CookingViewState, CookingEvent>() {
    var textToSpeech: TextToSpeech? = null

    private val file = mutableStateOf<File?>(null)
    override fun createInitialState(): CookingViewState = CookingViewState()
    override fun onTriggerEvent(event: CookingEvent) {
        TODO("Not yet implemented")
    }

    fun getRecipeStep(id: Int) {
        viewModelScope.launch {
            getRecipeStepUseCase(id).collect {
                when (it) {
                    is DataState.Success -> {
                        setState {
                            currentState.copy(
                                isLoading = false,
                                recipeName = it.data.recipeName,
                                recipeDetailList = it.data.recipeDetailInfoList,
                                count = it.data.recipeDetailInfoList.size
                            )
                        }
                    }

                    is DataState.Error -> {
                        setState { currentState.copy(isLoading = false) }
                    }

                    is DataState.Loading -> {
                        setState { currentState.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    fun textToSpeech(context: Context, string: String) {
        textToSpeech = TextToSpeech(
            context
        ) {
            if (it == TextToSpeech.SUCCESS) {
                textToSpeech?.let { txtToSpeech ->
                    txtToSpeech.language = Locale.KOREAN
                    txtToSpeech.setSpeechRate(1.0f)
                    val params: Bundle = bundleOf(
                        TextToSpeech.Engine.KEY_PARAM_VOLUME to "30.0"
                    )
                    txtToSpeech.speak(
                        string,
                        TextToSpeech.QUEUE_ADD,
                        params,
                        null
                    )
                }
            }
        }
    }

    fun getUsedIngredient(id: Int) {
        viewModelScope.launch {
            getUsedIngredientUseCase(id).collect {
                when (it) {
                    is DataState.Success -> {
                        val result: MutableList<Ingredient> = mutableListOf()
                        it.data.ingredientInfo.map { ingredientInfo ->
                            ingredientInfo.recipeIngredientList.map { ingredient ->
                                result.add(ingredient)
                            }
                        }
                        setState { currentState.copy(isLoading = false, usingIngredient = result) }
                        setState { currentState.copy(isLoading = false, usedIngredient = it.data) }
                    }

                    is DataState.Error -> {
                        setState { currentState.copy(isLoading = false) }
                    }

                    is DataState.Loading -> {
                        setState { currentState.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    fun registerNeedIngredient(id: Int) {
        viewModelScope.launch {
            registerNeedIngredientUseCase(id).collect {
                when (it) {
                    is DataState.Success -> {
                        setState { currentState.copy(isLoading = false, isNavigateToDelete = true) }
                    }

                    is DataState.Loading -> {
                        setState { currentState.copy(isLoading = true) }
                    }

                    is DataState.Error -> {
                        setState { currentState.copy(isLoading = false, isError = true) }
                    }
                }
            }
        }
    }

    fun deleteIngredient(ingredient: Ingredient) {
        viewModelScope.launch {
            deleteIngredientUseCase(IngredientList(mutableListOf(IngredientId(ingredient.recipeIngredientId)))).collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        val result = uiState.value.usingIngredient.toMutableList().apply {
                            remove(ingredient)
                        }.toList()
                        setState { currentState.copy(isLoading = false, usingIngredient = result) }
                    }

                    is DataState.Loading -> {
                        setState { currentState.copy(isLoading = true) }
                    }

                    is DataState.Error -> {
                        setState { currentState.copy(isLoading = false) }
                    }
                }
            }
        }
    }

    fun setFile(file: File) {
        this.file.value = file
    }

    fun registerCooking(id: Int, description: String) {
        if (file.value != null && description != "") {
            viewModelScope.launch {
                registerCookingUseCase(file.value!!, id, description).collect {
                    when (it) {
                        is DataState.Success -> {
                            setState {
                                currentState.copy(
                                    isLoading = false,
                                    isNavigateToHome = true
                                )
                            }
                        }

                        is DataState.Error -> {
                            setState { currentState.copy(isLoading = false) }
                        }

                        is DataState.Loading -> {
                            setState { currentState.copy(isLoading = true) }
                        }
                    }
                }
            }
        }
    }

    fun setError() {
        setState { currentState.copy(isError = false) }
    }
}