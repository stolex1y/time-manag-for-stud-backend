package timemanagement.rest

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import timemanagement.dto.DisciplineDto
import timemanagement.dto.GroupDto
import timemanagement.dto.LabelDto
import timemanagement.dto.TaskDto
import timemanagement.exception.NotFoundException
import timemanagement.model.UserRight
import timemanagement.service.GroupService
import timemanagement.service.UserService

@RestController
@RequestMapping("/group", produces = ["application/json"])
@ResponseStatus(HttpStatus.OK)
class GroupController(
    private val userService: UserService,
    private val groupService: GroupService) {

    @GetMapping("/{groupId}")
    fun getGroupById(@PathVariable groupId: Int): GroupDto {
        val userId: Int = userService.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        return groupService.getGroupById(userId, groupId)?.let {
            GroupDto(it)
        } ?: throw NotFoundException()
    }

    @GetMapping("/{groupId}/disciplines")
    fun getAllGroupDisciplines(@PathVariable groupId: Int): Set<DisciplineDto> {
        val userId: Int = userService.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        return DisciplineDto.toDisciplineDto(groupService.getAllGroupDisciplines(userId, groupId))
    }

    @GetMapping("/{groupId}/tasks")
    fun getAllGroupTasks(@PathVariable groupId: Int): Set<TaskDto> {
        val userId: Int = userService.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        return TaskDto.toTaskDto(groupService.getAllGroupTasks(userId, groupId))
    }

    @GetMapping("/{groupId}/tasks/labels")
    fun getAllGroupTasksLabels(@PathVariable groupId: Int): Set<LabelDto> {
        val userId: Int = userService.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        return LabelDto.toLabelDto(groupService.getAllGroupTasksLabels(userId, groupId))
    }

    @PatchMapping("/{groupId}/add/user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun addUserToGroup(@PathVariable groupId: Int, @RequestBody addUsername: String, @RequestBody right: UserRight) {
        val ownerId: Int = userService.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        val addUserId: Int = userService.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        groupService.addUserToGroup(ownerId, groupId, addUserId, right)
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateGroup(@RequestBody groupDto: GroupDto) {
        val userId: Int = userService.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        groupService.updateGroup(userId, groupDto) ?: throw NotFoundException()
    }

    @PostMapping("/{groupId}/add/discipline")
    @ResponseStatus(HttpStatus.CREATED)
    fun createGroupDiscipline(@PathVariable groupId: Int, @RequestBody disciplineDto: DisciplineDto): DisciplineDto {
        val userId: Int = userService.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        return groupService.createGroupDiscipline(userId, groupId, disciplineDto)?.let { DisciplineDto(it) } ?: throw NotFoundException()
    }

    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteGroupById(@PathVariable groupId: Int) {
        val userId: Int = userService.getUserId(AuthController.getUsernameLoggedIn()) ?: throw NotFoundException()
        groupService.deleteGroupById(userId, groupId)
    }
}
