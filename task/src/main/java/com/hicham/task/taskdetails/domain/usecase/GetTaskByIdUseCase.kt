package com.hicham.task.taskdetails.domain.usecase

import com.hicham.core.domain.usecase.UseCase
import com.hicham.data.persistence.model.Task
import com.hicham.data.repository.TaskRepository
import javax.inject.Inject

class GetTaskByIdUseCase @Inject constructor(private val repository: TaskRepository) : UseCase<String, Task>() {
    override suspend fun run(param: String): Task {
        return repository.getTaskById(param.toInt())
    }
}