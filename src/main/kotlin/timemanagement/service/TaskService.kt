package timemanagement.service

import org.springframework.transaction.annotation.Transactional
import timemanagement.dto.TaskDto
import timemanagement.model.*

@Transactional(readOnly = true)
interface TaskService {
    fun getById(userId: Int, taskId: Int): Task?
    fun getAllSubtasks(userId: Int, taskId: Int): Set<Task>
    fun getAllLabels(userId: Int, taskId: Int): Set<Label>
    fun getSubtaskById(userId: Int, taskId: Int, subtaskId: Int): Task?

    @Transactional
    fun createSubtask(userId: Int, taskId: Int, subtaskDto: TaskDto): Task?

    @Transactional
    fun addTaskToLabel(userId: Int, taskId: Int, labelId: Int)

    @Transactional
    fun updateTask(userId: Int, taskDto: TaskDto): Task?

    @Transactional
    fun updateSubtask(userId: Int, taskId: Int, subtaskDto: TaskDto): Task?

    @Transactional
    fun deleteTask(userId: Int, taskId: Int)

    @Transactional
    fun deleteSubtask(userId: Int, taskId: Int, subtaskId: Int)
}