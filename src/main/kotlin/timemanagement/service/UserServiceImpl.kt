package timemanagement.service

import org.springframework.stereotype.Service
import timemanagement.dto.*
import timemanagement.model.*
import timemanagement.repository.DisciplineRepository
import timemanagement.repository.GroupRepository
import timemanagement.repository.UserRepository

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val disciplineRepository: DisciplineRepository,
    private val groupRepository: GroupRepository
) : UserService {
    override fun getUserByLogin(login: String) = userRepository.findUserByLogin(login)
    override fun getUserById(userId: Int) = userRepository.findUserById(userId)
    override fun getUserId(login: String) = userRepository.findUserByLogin(login)?.id
    override fun getAllUserGroups(userId: Int) = getUserById(userId)?.groups ?: setOf()

    override fun getAllUserTasks(userId: Int): Set<Task> {
        val tasks: MutableList<Task> = mutableListOf()
        getUserById(userId)?.disciplines?.forEach {
            tasks.addAll(it.tasks)
        }
        return tasks.toSet()
    }

    override fun getAllUserTasksLabels(userId: Int): Set<Label> {
        val tasks = getAllUserTasks(userId)
        val labels: MutableList<Label> = mutableListOf()
        tasks.forEach {
            labels.addAll(it.labels)
        }
        return labels.toSet()
    }

    override fun getAllGroupsTasks(userId: Int): Set<Task> {
        val tasks: MutableList<Task> = mutableListOf()
        getAllUserGroups(userId).forEach {
            it.disciplines.forEach { discipline ->
                tasks.addAll(discipline.tasks)
            }
        }
        return tasks.toSet()
    }

    override fun getAllUserGroupsTasksLabels(userId: Int): Set<Label> {
        val tasks = getAllGroupsTasks(userId)
        val labels: MutableList<Label> = mutableListOf()
        tasks.forEach {
            labels.addAll(it.labels)
        }
        return labels.toSet()
    }

    override fun getAllUserDisciplines(userId: Int) = getUserById(userId)?.disciplines ?: setOf()

    override fun createUserDiscipline(userId: Int, disciplineDto: DisciplineDto): Discipline? {
        var discipline = disciplineDto.toDiscipline()
        discipline.id = null
        discipline = disciplineRepository.save(discipline)
        discipline.id?.let {
            userRepository.addUserDiscipline(userId, it)
        } ?: return null
        return discipline
    }

    override fun createUserGroup(userId: Int, groupDto: GroupDto): Group? {
        var group = groupDto.toGroup()
        group.id = null
        group = groupRepository.save(group)
        group.id?.let {
            userRepository.addUserGroup(userId, it)
        } ?: return null
        return group
    }

    override fun deleteUserById(userId: Int) {
        userRepository.deleteById(userId)
    }

    override fun updateUser(login: String, userDto: UserDtoRequest): User? {
        val user: User = getUserByLogin(login) ?: return null
        userDto.update(user)
        return userRepository.save(user)
    }
}