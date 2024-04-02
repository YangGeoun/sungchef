package com.ssafy.sungchef.domain.usecase.refrigerator

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.model.refrigerator.RegisterReceiptState
import com.ssafy.sungchef.domain.repository.RefrigeratorRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class RegisterReceiptUseCase @Inject constructor(
    private val refrigeratorRepository: RefrigeratorRepository
) {
    // TODO 여기부터!!
    suspend fun registerReceipt(imageFile : File) : Flow<DataState<RegisterReceiptState>> {
        return refrigeratorRepository.registerReceipt(imageFile)
    }
}