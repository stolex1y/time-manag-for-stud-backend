package timemanagement.service

import org.springframework.transaction.annotation.Transactional
import timemanagement.dto.DisciplineDto
import timemanagement.dto.GroupDto
import timemanagement.model.*

@Transactional(readOnly = true)
interface GroupService {
    fun getGroupById(userId: Int, groupId: Int): Group?
    fun getAllGroupDisciplines(userId: Int, groupId: Int): Set<Discipline>
    fun getAllGroupTasks(userId: Int, groupId: Int): Set<Task>
    fun getAllGroupTasksLabels(userId: Int, groupId: Int): Set<Label>

    @Transactional
    fun addUserToGroup(
        ownerId: Int,
        groupId: Int,
        addUserId: Int,
        userRight: UserRight
    )

    @Transactional
    fun updateGroup(userId: Int, groupDto: GroupDto): Group?

    @Transactional
    fun createGroupDiscipline(userId: Int, groupId: Int, disciplineDto: DisciplineDto): Discipline?

    @Transactional
    fun deleteGroupById(userId: Int, groupId: Int)


}