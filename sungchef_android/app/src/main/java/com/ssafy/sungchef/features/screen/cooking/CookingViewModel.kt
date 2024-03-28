package com.ssafy.sungchef.features.screen.cooking

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.core.os.bundleOf
import androidx.lifecycle.viewModelScope
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.usecase.cooking.GetRecipeStepUseCase
import com.ssafy.sungchef.domain.usecase.cooking.GetUsedIngredientUseCase
import com.ssafy.sungchef.domain.viewstate.cooking.CookingViewState
import com.ssafy.sungchef.features.screen.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class CookingViewModel @Inject constructor(
    private val getUsedIngredientUseCase: GetUsedIngredientUseCase,
    private val getRecipeStepUseCase: GetRecipeStepUseCase,
) : BaseViewModel<CookingViewState, CookingEvent>() {
    var textToSpeech: TextToSpeech? = null
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
                        setState {
                            currentState.copy(isLoading = false, usedIngredient = it.data)
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