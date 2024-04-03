package com.ssafy.sungchef.data.repository

import android.util.Log
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.datasource.refrigerator.RefrigeratorDataSource
import com.ssafy.sungchef.data.mapper.ingredient.toIngredientRequestDTO
import com.ssafy.sungchef.data.mapper.refrigerator.toIngredientRequestDTO
import com.ssafy.sungchef.data.mapper.refrigerator.toMapIngredient
import com.ssafy.sungchef.data.mapper.refrigerator.toRegisterReceiptState
import com.ssafy.sungchef.data.mapper.refrigerator.toSearchIngredient
import com.ssafy.sungchef.data.mapper.user.toBaseModel
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.requestdto.IngredientId
import com.ssafy.sungchef.data.model.requestdto.IngredientRequestDTO
import com.ssafy.sungchef.data.model.requestdto.RecipeRequest
import com.ssafy.sungchef.data.model.responsedto.FridgeData
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.ingredient.search.SearchIngredientResponse

import com.ssafy.sungchef.data.model.responsedto.ocr.ConvertInfo
import com.ssafy.sungchef.domain.model.base.BaseModel
import com.ssafy.sungchef.domain.model.ingredient.IngredientList
import com.ssafy.sungchef.domain.model.refrigerator.RegisterReceiptState
import com.ssafy.sungchef.domain.model.refrigerator.SearchIngredient
import com.ssafy.sungchef.domain.repository.RefrigeratorRepository
import com.ssafy.sungchef.util.MultipartHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.onStart
import java.io.File
import javax.inject.Inject

class RefrigeratorRepositoryImpl @Inject constructor(
    private val refrigeratorDataSource: RefrigeratorDataSource
) : RefrigeratorRepository {
    override suspend fun searchIngredient(ingredientName: String): Flow<DataState<List<SearchIngredient>>> {
        return flow {
            val ingredientResponse = refrigeratorDataSource.searchIngredient(ingredientName)

            if (ingredientResponse is DataState.Success) {
                emit(DataState.Success(ingredientResponse.data.data.toSearchIngredient()))
            } else if (ingredientResponse is DataState.Error) {
                emit(DataState.Error(ingredientResponse.apiError))
            }
        }
    }

    override suspend fun registerIngredient(ingredientIdList: List<Int>): Flow<DataState<APIError>> {
        return flow {
            val ingredientRequestDTO = ingredientIdList.toIngredientRequestDTO()

            val registerResponse = refrigeratorDataSource.registerIngredient(ingredientRequestDTO)

            if (registerResponse is DataState.Success) {
                emit(DataState.Success(registerResponse.data))
            } else if (registerResponse is DataState.Error) {
                emit(DataState.Error(registerResponse.apiError))
            }
        }
    }

    override suspend fun registerReceipt(imageFile: File): Flow<DataState<RegisterReceiptState>> {
        return flow {

            val multipartHandler = MultipartHandler()

            val multiPartImage = multipartHandler.convertMultiPart(imageFile, "convertImage")

            val ocrImageResponse = refrigeratorDataSource.registerReceipt(multiPartImage)

            when (ocrImageResponse) {
                is DataState.Success -> {
                    emit(DataState.Success(ocrImageResponse.data.toRegisterReceiptState()))
                }

                is DataState.Loading -> {
                    emit(DataState.Loading())
                }

                is DataState.Error -> {
                    emit(DataState.Error(ocrImageResponse.apiError))
                }
            }

        }.onStart {
            emit(DataState.Loading())
        }

    }

    override suspend fun getFridgeIngredientList(): Flow<DataState<ResponseDto<FridgeData>>> {
        Log.d("TAG", "Repo flow전 getFridgeIngredientList: ")

        return flow{
            Log.d("TAG", "Repo getFridgeIngredientList: ")
            emit(refrigeratorDataSource.getFridgeIngredientList())
        }
    }

    override suspend fun deleteIngredient(ingredientList: IngredientList): Flow<DataState<BaseModel>> =
        flow {
            when (val result =
                refrigeratorDataSource.deleteIngredient(ingredientList.toIngredientRequestDTO())) {
                is DataState.Success -> {
                    Log.d("TAG", "Repo deleteIngredient: success")
                    if (result.data.code == 200.toLong()) {
                        emit(DataState.Success(result.data.toBaseModel()))
                    } else {
                        emit(DataState.Error(APIError(result.data.code, result.data.message)))
                    }
                }

                is DataState.Error -> {
                    Log.d("TAG", "Repo deleteIngredient: error")

                    emit(DataState.Error(result.apiError))
                }

                is DataState.Loading -> {
                    Log.d("TAG", "Repo deleteIngredient: loading")

                    emit(DataState.Loading())
                }
            }
        }.onStart { emit(DataState.Loading()) }

    override suspend fun registerNeedIngredient(id: Int): Flow<DataState<BaseModel>> =
        flow {
            when (val result = refrigeratorDataSource.registerNeedIngredient(RecipeRequest(id))) {
                is DataState.Success -> {
                    emit(DataState.Success(result.data.toBaseModel()))
                }

                is DataState.Error -> {

                }

                is DataState.Loading -> {
                    emit(DataState.Loading())
                }
            }
        }.onStart { emit(DataState.Loading()) }


    override suspend fun getOcrConvert(convertOCRKey: String): Flow<DataState<Map<String, List<ConvertInfo>>>> {
        return flow {
            val ocrResponse = refrigeratorDataSource.getOcrConvert(convertOCRKey)

            when (ocrResponse) {
                is DataState.Success -> {
                    emit(DataState.Success(ocrResponse.data.data.toMapIngredient()))
                }

                is DataState.Loading -> {

                }

                is DataState.Error -> {
                    emit(DataState.Error(ocrResponse.apiError))
                }
            }
        }
    }

}