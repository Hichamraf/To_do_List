package com.hicham.shared.domain.usecase

import com.hicham.core.domain.usecase.UseCase
import com.hicham.core.utils.SharedPrefsUtils
import com.hicham.shared.domain.model.DARK_MODE
import javax.inject.Inject

class SaveDarkModeUseCase @Inject constructor(private val sharedPrefsUtils: SharedPrefsUtils) : UseCase<Boolean, Unit>() {
    override suspend fun run(param: Boolean): Unit {
        sharedPrefsUtils.save(DARK_MODE, param)
    }
}