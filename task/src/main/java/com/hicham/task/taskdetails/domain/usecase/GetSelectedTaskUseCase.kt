package com.hicham.task.taskdetails.domain.usecase

import com.hicham.core.domain.usecase.UseCase
import com.hicham.data.persistence.model.Task
import com.hicham.data.repository.TaskModRepo
import javax.inject.Inject

class GetSelectedTaskUseCase @Inject constructor(private val taskModRepo: TaskModRepo) : UseCase<Unit, Task>() {
    override suspend fun run(param: Unit): Task {
        return taskModRepo.getSelectedTask()
    }
}