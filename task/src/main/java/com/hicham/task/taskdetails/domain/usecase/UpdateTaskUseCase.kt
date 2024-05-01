package com.hicham.task.taskdetails.domain.usecase

import com.hicham.core.domain.usecase.UseCase
import com.hicham.data.persistence.model.Task
import com.hicham.data.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) : UseCase<Task, Unit>() {
    override suspend fun run(param: Task) {
        taskRepository.updateTask(param)
    }
}