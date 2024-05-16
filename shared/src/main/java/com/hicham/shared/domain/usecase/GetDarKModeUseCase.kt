package com.hicham.shared.domain.usecase

import com.hicham.core.domain.usecase.UseCase
import com.hicham.core.utils.SharedPrefsUtils
import com.hicham.shared.domain.model.DARK_MODE
import javax.inject.Inject


class GetDarKModeUseCase @Inject constructor(private val sharedPrefsUtils: SharedPrefsUtils) : UseCase<Unit, Boolean>() {
    override suspend fun run(param: Unit): Boolean {
        return sharedPrefsUtils.getBoolean(DARK_MODE)
    }
}