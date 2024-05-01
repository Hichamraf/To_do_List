package com.hicham.task.utils

import com.hicham.data.persistence.model.Task

fun Task.isTaskValid() = name.isNotEmpty()