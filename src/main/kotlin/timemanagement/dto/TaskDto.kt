package timemanagement.dto

import timemanagement.model.Discipline
import timemanagement.model.State
import timemanagement.model.Task
import timemanagement.model.TaskType
import java.time.ZonedDateTime
import java.util.*

data class TaskDto(
    var id: Int? = null,
    var name: String,
    var comment: String? = null,
    var deadline: Long,
    var state: State = State.WORK,
    var taskType: TaskType? = TaskType.TABLE,
    var priority: Int? = null
) {
    companion object {
        fun toTaskDto(groups: Set<Task>): Set<TaskDto> {
            val result: MutableList<TaskDto> = mutableListOf()
            groups.forEach {
                result.add(TaskDto(it))
            }
            return result.toSet()
        }
    }

    constructor(task: Task) :
            this(
                task.id,
                task.name,
                task.comment,
                task.deadline.timeInMillis,
                task.state,
                task.taskType,
                task.priority
            )

    fun toTask(): Task {
        val task = Task(
            id = this.id,
            name = this.name,
            comment = this.comment,
            deadline = Calendar.getInstance(),
            state = this.state,
            taskType = this.taskType,
            priority = this.priority
        )
        task.deadline.timeInMillis = this.deadline
        return task
    }

    fun update(task: Task) {
        task.name = this.name
        task.comment = this.comment
        task.deadline.timeInMillis = this.deadline
        task.state = this.state
        task.taskType = this.taskType
        task.priority = this.priority
    }
}