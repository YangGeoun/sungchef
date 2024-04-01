package com.ssafy.sungchef.data.datasource.refrigerator

import com.ssafy.sungchef.data.api.RefrigeratorService
import javax.inject.Inject

class RefrigeratorDataSourceImpl @Inject constructor(
    private val refrigeratorService: RefrigeratorService
) : RefrigeratorDataSource {

}