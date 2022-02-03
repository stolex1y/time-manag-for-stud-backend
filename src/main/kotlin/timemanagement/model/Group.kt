package timemanagement.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import timemanagement.dto.GroupDto
import javax.persistence.*

@Entity
@Table(name = "Группы")
class Group(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ИД")
    var id: Int? = null,

    @Column(name = "Название")
    var name: String,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "Дисциплины_группа",
        joinColumns = [JoinColumn(name = "Группа_ИД")],
        inverseJoinColumns = [JoinColumn(name = "Дисциплина_ИД")]
    )
    @JsonManagedReference
    var disciplines: Set<Discipline> = setOf(),

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    @JsonBackReference
    var users: Set<User> = setOf()

) {
    fun containUser(userId: Int): Boolean {
        val filtered = users.filter {
            it.id == userId
        }
        return filtered.isNotEmpty()
    }
}
