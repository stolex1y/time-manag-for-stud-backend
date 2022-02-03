package timemanagement.rest

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import timemanagement.dto.*
import timemanagement.exception.NotFoundException
import timemanagement.service.TaskService
import timemanagement.service.UserService

@RestController
@RequestMapping("/task", produces = ["application/json"])
@ResponseStatus(HttpStatus.OK)
class TaskController(
    private val taskService: TaskService,
    private val userService: UserService
) {
    
    @GetMapping("/{taskId}")
    fun getTaskById(@PathVariable taskId: Int): TaskDto {
        val userId: Int = userService.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        return taskService.getById(userId, taskId)?.let {
            TaskDto(it)
        } ?: throw NotFoundException()
    }

    @GetMapping("/{taskId}/{subtaskId}")
    fun getSubtaskById(@PathVariable taskId: Int, @PathVariable subtaskId: Int): TaskDto {
        val userId: Int = userService.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        return taskService.getSubtaskById(userId, taskId, subtaskId)?.let {
            TaskDto(it)
        } ?: throw NotFoundException()
    }

    @GetMapping("/{taskId}/subtasks")
    fun getAllSubtasks(@PathVariable taskId: Int): Set<TaskDto> {
        val userId: Int = userService.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        return TaskDto.toTaskDto(taskService.getAllSubtasks(userId, taskId))
    }

    @PostMapping("/{taskId}/add/subtask")
    @ResponseStatus(HttpStatus.CREATED)
    fun createSubtask(@PathVariable taskId: Int, @RequestBody taskDto: TaskDto): TaskDto {
        val userId: Int = userService.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        return taskService.createSubtask(userId, taskId, taskDto)?.let { TaskDto(it) } ?: throw NotFoundException()
    }

    @PatchMapping("/{taskId}/addto/label/{labelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun addTaskToLabel(@PathVariable taskId: Int, @PathVariable labelId: Int) {
        val userId: Int = userService.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        taskService.addTaskToLabel(userId, taskId, labelId)
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateTask(@RequestBody taskDto: TaskDto) {
        val userId: Int = userService.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        taskService.updateTask(userId, taskDto) ?: throw NotFoundException()
    }

    @PutMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateSubtask(@PathVariable taskId: Int, @RequestBody subtaskDto: TaskDto) {
        val userId: Int = userService.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        taskService.updateSubtask(userId, taskId, subtaskDto) ?: throw NotFoundException()
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTask(@PathVariable taskId: Int) {
        val userId: Int = userService.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        taskService.deleteTask(userId, taskId)
    }

    @DeleteMapping("/{taskId}/{subtaskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSubtask(@PathVariable taskId: Int, @PathVariable subtaskId: Int) {
        val userId: Int = userService.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        taskService.deleteSubtask(userId, taskId, subtaskId)
    }
}