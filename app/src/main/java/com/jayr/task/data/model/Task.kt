package com.jayr.task.data.model

data class Task(
    var title: String,
    var details: String,
    var date: String,
    var isComplete: Boolean = false
) {
    fun toggleStatus() {
        isComplete = !isComplete
    }

    fun deleteTask(): Task? {
        return null // Simulating deletion by returning null
    }
}
