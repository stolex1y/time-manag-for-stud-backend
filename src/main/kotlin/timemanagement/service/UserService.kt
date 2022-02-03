package timemanagement.service

import org.springframework.transaction.annotation.Transactional
import timemanagement.dto.DisciplineDto
import timemanagement.dto.GroupDto
import timemanagement.dto.UserDtoRequest
import timemanagement.model.*

@Transactional(readOnly = true)
interface UserService {
    fun getUserByLogin(login: String): User?
    fun getUserById(userId: Int): User?
    fun getUserId(login: String): Int?
    fun getAllUserTasks(userId: Int): Set<Task>
    fun getAllUserTasksLabels(userId: Int): Set<Label>
    fun getAllGroupsTasks(userId: Int): Set<Task>
    fun getAllUserDisciplines(userId: Int): Set<Discipline>
    fun getAllUserGroups(userId: Int): Set<Group>
    fun getAllUserGroupsTasksLabels(userId: Int): Set<Label>

    @Transactional
    fun createUserDiscipline(userId: Int, disciplineDto: DisciplineDto): Discipline?

    @Transactional
    fun createUserGroup(userId: Int, groupDto: GroupDto): Group?

    @Transactional
    fun deleteUserById(userId: Int)

    @Transactional
    fun updateUser(login: String, userDto: UserDtoRequest): User?
}