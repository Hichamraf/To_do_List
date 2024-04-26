package com.hicham.home.domain.usecase

import com.hicham.core.domain.usecase.UseCase
import com.hicham.data.persistence.model.Task
import com.hicham.data.repository.TaskRepository
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val taskRepository: TaskRepository) : UseCase<Unit, List<Task>>() {
    override suspend fun run(param: Unit): List<Task> {
        return taskRepository.getAllTasks()
    }
}