package timemanagement.rest

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import timemanagement.dto.*
import timemanagement.exception.NotFoundException
import timemanagement.model.*
import timemanagement.service.UserService
import timemanagement.service.AuthService
import kotlin.streams.toList

@RestController
@RequestMapping("/user", produces = ["application/json"])
@ResponseStatus(HttpStatus.OK)
class UserController(
    private val service: UserService,
    private val authService: AuthService
) {

    @GetMapping("")
    fun getUser(): UserDtoResponse {
        val login: String = AuthController.getUsernameLoggedIn()
        val user: User = service.getUserByLogin(login) ?: throw NotFoundException()
        return UserDtoResponse(user)
    }

    @PatchMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateUser(@RequestBody userDto: UserDtoRequest) {
        val login: String = AuthController.getUsernameLoggedIn()
        service.updateUser(login, userDto)?.let { UserDtoResponse(it) } ?: throw NotFoundException()
    }

    @PatchMapping("/pass")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateUser(@RequestBody userDetails: UserDetailsDto) {
        authService.updateUserPass(userDetails)?.let { UserDtoResponse(it) } ?: throw NotFoundException()
    }

    @GetMapping("/groups")
    fun getUserGroups(): Set<GroupDto> {
        val id: Int = service.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        return GroupDto.toGroupDto(service.getAllUserGroups(id))
    }
    
    @GetMapping("/tasks")
    fun getAllUserTasks(): Set<TaskDto> {
        val id: Int = service.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        return TaskDto.toTaskDto(service.getAllUserTasks(id))
    }

    @GetMapping("/tasks/labels")
    fun getAllUserTasksLabels(): Set<LabelDto> {
        val id: Int = service.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        return LabelDto.toLabelDto(service.getAllUserTasksLabels(id))
    }

    @GetMapping("/groups/tasks")
    fun getAllGroupTasks(): Set<TaskDto> {
        val id: Int = service.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        return TaskDto.toTaskDto(service.getAllGroupsTasks(id))
    }

    @GetMapping("/groups/tasks/labels")
    fun getAllGroupTasksLabels(): Set<LabelDto> {
        val id: Int = service.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        return LabelDto.toLabelDto(service.getAllUserGroupsTasksLabels(id))
    }

    @GetMapping("/disciplines")
    fun getAllUserDisciplines(): Set<DisciplineDto> {
        val id: Int = service.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        return DisciplineDto.toDisciplineDto(service.getAllUserDisciplines(id))
    }

    @DeleteMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser() {
        val userId: Int = service.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        service.deleteUserById(userId)
    }

    @PostMapping("/add/discipline")
    @ResponseStatus(HttpStatus.CREATED)
    fun createTask(@RequestBody disciplineDto: DisciplineDto): DisciplineDto {
        val userId: Int = service.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        return service.createUserDiscipline(userId, disciplineDto)?.let { DisciplineDto(it) } ?: throw NotFoundException()
    }

    @PostMapping("/add/group")
    @ResponseStatus(HttpStatus.CREATED)
    fun createTask(@RequestBody groupDto: GroupDto): GroupDto {
        val userId: Int = service.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        return service.createUserGroup(userId, groupDto)?.let { GroupDto(it) } ?: throw NotFoundException()
    }
}