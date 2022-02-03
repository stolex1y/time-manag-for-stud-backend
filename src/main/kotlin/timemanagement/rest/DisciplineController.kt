package timemanagement.rest

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import timemanagement.dto.*
import timemanagement.exception.NotFoundException
import timemanagement.service.DisciplineService
import timemanagement.service.UserService

@RestController
@RequestMapping("/discipline", produces = ["application/json"])
@ResponseStatus(HttpStatus.OK)
class DisciplineController(
    private val disciplineService: DisciplineService,
    private val userService: UserService
) {

    @GetMapping("/{disciplineId}")
    fun getDisciplineById(@PathVariable disciplineId: Int): DisciplineDto {
        val userId: Int = userService.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        return disciplineService.getById(userId, disciplineId)?.let {
            DisciplineDto(it)
        } ?: throw NotFoundException()
    }

    @GetMapping("/{disciplineId}/tasks")
    fun getAllTasks(@PathVariable disciplineId: Int): Set<TaskDto> {
        val userId: Int = userService.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        return TaskDto.toTaskDto(disciplineService.getAllTasks(userId, disciplineId))
    }

    @PostMapping("/{disciplineId}/add/task")
    @ResponseStatus(HttpStatus.CREATED)
    fun createTask(@PathVariable disciplineId: Int, @RequestBody taskDto: TaskDto): TaskDto {
        val userId: Int = userService.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        return disciplineService.createDisciplineTask(userId, disciplineId, taskDto)?.let { TaskDto(it) } ?: throw NotFoundException()
    }

    @PatchMapping("/{disciplineId}/copyto/user")
    fun copyDisciplineToUser(@PathVariable disciplineId: Int): DisciplineDto {
        val userId: Int = userService.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        return disciplineService.copyDisciplineToUser(userId, disciplineId)?.let { DisciplineDto(it) } ?: throw NotFoundException()
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateDiscipline(@RequestBody disciplineDto: DisciplineDto) {
        val userId: Int = userService.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        disciplineService.updateDiscipline(userId, disciplineDto) ?: throw NotFoundException()
    }

    @DeleteMapping("/{disciplineId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteDiscipline(@PathVariable disciplineId: Int) {
        val userId: Int = userService.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        disciplineService.deleteDiscipline(userId, disciplineId)
    }
}
