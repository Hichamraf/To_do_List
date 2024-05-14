package com.hicham.home.domain.usecase

import com.hicham.core.domain.usecase.UseCase
import com.hicham.data.persistence.model.Task
import com.hicham.data.repository.TaskRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetTaskByDateUseCase @Inject constructor(private val taskRepository: TaskRepository) : UseCase<Long, Flow<List<Task>>>() {
    override suspend fun run(param: Long): Flow<List<Task>> {
        return taskRepository.getTasksByDate(param)
    }
}