package timemanagement.service

import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import timemanagement.dto.DisciplineDto
import timemanagement.dto.GroupDto
import timemanagement.model.*
import timemanagement.repository.DisciplineRepository
import timemanagement.repository.GroupRepository

@Service
class GroupServiceImpl(
    private val userService: UserService,
    private val disciplineRepository: DisciplineRepository,
    private val groupRepository: GroupRepository
) : GroupService {

    override fun getGroupById(userId: Int, groupId: Int): Group? {
        return userService.getUserById(userId)?.run {
            groups.filter {
                it.id == groupId
            }.getOrNull(0)
        }
    }

    override fun getAllGroupDisciplines(userId: Int, groupId: Int): Set<Discipline> {
        return getGroupById(userId, groupId)?.disciplines ?: setOf()
    }

    override fun getAllGroupTasks(userId: Int, groupId: Int): Set<Task> {
        val tasks: MutableList<Task> = mutableListOf()
        getGroupById(userId, groupId)?.disciplines?.forEach {
            tasks.addAll(it.tasks)
        }
        return tasks.toSet()
    }

    override fun getAllGroupTasksLabels(userId: Int, groupId: Int): Set<Label> {
        val tasks = getAllGroupTasks(userId, groupId)
        val labels: MutableList<Label> = mutableListOf()
        tasks.forEach {
            labels.addAll(it.labels)
        }
        return labels.toSet()
    }

    override fun addUserToGroup(ownerId: Int, groupId: Int, addUserId:Int, userRight: UserRight) {
        groupRepository.addUserToGroup(ownerId, groupId, addUserId, userRight)
    }

    override fun updateGroup(userId: Int, groupDto: GroupDto): Group? {
        return groupDto.id?.let {
            val group = getGroupById(userId, it) ?: return null
            if (!groupRepository.userCanEditGroup(userId, it)) return null
            groupDto.updateGroup(group)
            groupRepository.save(group)
            groupRepository.save(group)
        } ?: userService.createUserGroup(userId, groupDto)
    }

    override fun createGroupDiscipline(userId: Int, groupId: Int, disciplineDto: DisciplineDto): Discipline? {
        var discipline = disciplineDto.toDiscipline()
        discipline.id = null
        discipline = disciplineRepository.save(discipline)
        return discipline.id?.let {
            groupRepository.addGroupDiscipline(userId, groupId, it)
            disciplineRepository.findDisciplineById(it)
        }
    }

    override fun deleteGroupById(userId: Int, groupId: Int) = groupRepository.deleteById(userId, groupId)
}