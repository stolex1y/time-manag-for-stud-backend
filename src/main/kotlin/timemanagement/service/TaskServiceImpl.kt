package timemanagement.service

import org.springframework.stereotype.Service
import timemanagement.dto.TaskDto
import timemanagement.exception.IllegalAccessException
import timemanagement.exception.NotFoundException
import timemanagement.model.Label
import timemanagement.model.Task
import timemanagement.repository.GroupRepository
import timemanagement.repository.TaskRepository

@Service
class TaskServiceImpl(
    private var taskRepository: TaskRepository,
    private var groupRepository: GroupRepository
) : TaskService {

    override fun getById(userId: Int, taskId: Int): Task? {
        val result = taskRepository.findTaskById(taskId) ?: return null
        return if (taskRepository.userContainTask(userId, taskId))
            result
        else
            null
    }

    override fun getAllSubtasks(userId: Int, taskId: Int): Set<Task> {
        val task = getById(userId, taskId) ?: return setOf()
        return task.subtasks
    }

    override fun getSubtaskById(userId: Int, taskId: Int, subtaskId: Int): Task? {
        val task = getById(userId, taskId) ?: return null
        return task.subtasks.filter {
            it.id == subtaskId
        }.getOrNull(0)
    }

    override fun createSubtask(userId: Int, taskId: Int, subtaskDto: TaskDto): Task? {
        var subtask = subtaskDto.toTask()
        subtask.id = null
        subtask = taskRepository.save(subtask)
        val subtaskId = subtask.id ?: return null
        taskRepository.addSubtask(userId, taskId, subtaskId)
        return subtask
    }

    override fun addTaskToLabel(userId: Int, taskId: Int, labelId: Int) {
        taskRepository.addTaskToLabel(userId, taskId, labelId)
    }

    override fun updateTask(userId: Int, taskDto: TaskDto): Task? {
        val taskId = taskDto.id ?: return null
        var task = getById(userId, taskId) ?: return null
        if (task.isGroupTask()) {
            val groupId = task.getRootDiscipline().group?.id ?: return null
            if (!groupRepository.userCanEditGroup(userId, groupId)) throw IllegalAccessException()
        }
        taskDto.update(task)
        task = taskRepository.save(task)
        return task
    }

    override fun getAllLabels(userId: Int, taskId: Int): Set<Label> {
        val task = getById(userId, taskId) ?: return setOf()
        return task.labels
    }

    override fun updateSubtask(userId: Int, taskId: Int, subtaskDto: TaskDto): Task? {
        val subtaskId = subtaskDto.id ?: return null
        var task = getById(userId, taskId) ?: return null
        val subtask = getSubtaskById(userId, taskId, subtaskId) ?: return null
        task = task.parentTask ?: task
        if (task.isGroupTask()) {
            val groupId = task.getRootDiscipline().group!!.id!!
            if (!groupRepository.userCanEditGroup(userId, groupId))
                throw IllegalAccessException()
        }
        subtaskDto.update(subtask)
        return taskRepository.save(subtask)
    }

    override fun deleteTask(userId: Int, taskId: Int) {
        val task = getById(userId, taskId) ?: throw NotFoundException()
        if (task.isGroupTask()) {
            val groupId = task.getRootDiscipline().group!!.id!!
            if (!groupRepository.userCanEditGroup(userId, groupId))
                throw IllegalAccessException()
        }
        taskRepository.deleteById(taskId)
    }

    override fun deleteSubtask(userId: Int, taskId: Int, subtaskId: Int) {
        var task = getById(userId, taskId) ?: throw NotFoundException()
        task = task.parentTask ?: task
        if (task.isGroupTask()) {
            val groupId = task.getRootDiscipline().group!!.id!!
            if (!groupRepository.userCanEditGroup(userId, groupId))
                throw IllegalAccessException()
        }
        getSubtaskById(userId, taskId, subtaskId) ?: throw NotFoundException()
        taskRepository.deleteById(subtaskId)
    }
}