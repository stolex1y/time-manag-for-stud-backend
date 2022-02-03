package timemanagement.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import timemanagement.dto.DisciplineDto
import timemanagement.dto.TaskDto
import timemanagement.model.Discipline
import timemanagement.model.Task
import timemanagement.repository.DisciplineRepository
import timemanagement.repository.GroupRepository
import timemanagement.repository.TaskRepository

@Service
class DisciplineServiceImpl(
    private val disciplineRepository: DisciplineRepository,
    private val taskRepository: TaskRepository,
    private val groupRepository: GroupRepository
) : DisciplineService {

    override fun getById(userId: Int, disciplineId: Int): Discipline? {
        val discipline = disciplineRepository.findDisciplineById(disciplineId) ?: return null
        if (discipline.user?.id == userId) return discipline
        if (discipline.group?.containUser(userId) == true) return discipline
        return null
    }

    override fun getAllTasks(userId: Int, disciplineId: Int): Set<Task> {
        return getById(userId, disciplineId)?.tasks ?: setOf()
    }

    override fun createDisciplineTask(userId: Int, disciplineId: Int, taskDto: TaskDto): Task? {
        val discipline = getById(userId, disciplineId) ?: return null
        var task = taskDto.toTask()
        task.id = null
        task = taskRepository.save(task)
        val taskId: Int = task.id ?: return null
        if (discipline.isUserDiscipline()) {
            disciplineRepository.addTaskToUserDiscipline(userId, disciplineId, taskId)
        } else if (discipline.isGroupDiscipline()) {
            val groupId: Int = discipline.group?.id ?: return null
            disciplineRepository.addTaskToGroupDiscipline(userId, groupId, disciplineId, taskId)
        } else
            return null
        return task
    }

    override fun copyDisciplineToUser(userId: Int, disciplineId: Int): Discipline? {
        val discipline = getById(userId, disciplineId) ?: return null
        val groupId = discipline.group?.id ?: return null
        val newDisciplineId: Int = disciplineRepository.copyDisciplineToUser(userId, groupId, disciplineId) ?: return null
        return getById(userId, newDisciplineId)
    }

    override fun updateDiscipline(userId: Int, disciplineDto: DisciplineDto): Discipline? {
        val disciplineId: Int = disciplineDto.id ?: return null
        val discipline: Discipline = getById(userId, disciplineId) ?: return null
        if (discipline.isGroupDiscipline()) {
            val groupId = discipline.group?.id ?: return null
            if (!groupRepository.userCanEditGroup(userId, groupId)) return null
        }
        disciplineDto.updateDiscipline(discipline)
        return disciplineRepository.save(discipline)
    }

    override fun deleteDiscipline(userId: Int, disciplineId: Int) {
        val discipline = getById(userId, disciplineId) ?: return
        if (discipline.isUserDiscipline()) {
            disciplineRepository.deleteByIdAndUserId(disciplineId, userId)
        } else if (discipline.isGroupDiscipline()) {
            val groupId: Int = discipline.group?.id ?: return
            disciplineRepository.deleteGroupDiscipline(userId, groupId, disciplineId)
        } else
            return
    }
}