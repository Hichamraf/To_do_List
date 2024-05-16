package com.hicham.inbox.domain

import com.hicham.core.domain.usecase.UseCase
import com.hicham.data.persistence.model.Task
import com.hicham.data.repository.TaskRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetAllTasksUseCas @Inject constructor(private val taskRepository: TaskRepository) : UseCase<Unit, Flow<List<Task>>>() {
    override suspend fun run(param: Unit): Flow<List<Task>> {
        return taskRepository.getAllTasks()
    }
}