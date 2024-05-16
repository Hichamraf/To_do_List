package com.hicham.shared.domain.usecase

import com.hicham.core.domain.usecase.UseCase
import com.hicham.data.persistence.model.Task
import com.hicham.data.repository.TaskModRepo
import javax.inject.Inject

class SetSelectedTaskUseCase @Inject constructor(private val taskModRepo: TaskModRepo) : UseCase<Task, Unit>() {
    override suspend fun run(param: Task) {
        taskModRepo.setSelectTask(param)
    }
}