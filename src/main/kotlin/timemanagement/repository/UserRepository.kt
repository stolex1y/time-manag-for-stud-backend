package timemanagement.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import timemanagement.model.User

@Repository
@Transactional(readOnly = true)
interface UserRepository : CrudRepository<User, Int> {
    fun existsUserByLoginAndPass(login: String, pass: String): Boolean
    fun existsUserByLogin(login: String): Boolean
    fun findUserByLogin(login: String): User?
    fun findUserByLoginAndPass(login: String, pass: String): User?
    fun findUserById(id: Int): User?

    @Transactional
    fun save(user: User): User

    @Query(value = "select 1 from add_user_discipline(?1, ?2)", nativeQuery = true)
    @Transactional
    fun addUserDiscipline(userId: Int, disciplineId: Int)

    @Query(value = "select 1 from add_user_group(?1, ?2)", nativeQuery = true)
    @Transactional
    fun addUserGroup(userId: Int, disciplineId: Int)
}