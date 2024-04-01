package com.ssafy.sungchef.data.repository

import com.ssafy.sungchef.data.datasource.refrigerator.RefrigeratorDataSource
import com.ssafy.sungchef.domain.repository.RefrigeratorRepository
import javax.inject.Inject

class RefrigeratorRepositoryImpl @Inject constructor(
    private val refrigeratorDataSource: RefrigeratorDataSource
) : RefrigeratorRepository {

}