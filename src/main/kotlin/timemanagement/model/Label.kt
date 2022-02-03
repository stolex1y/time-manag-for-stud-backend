package timemanagement.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*

@Entity
@Table(name = "Метки")
class Label(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ИД")
    var id: Int? = null,

    @Column(name = "Название")
    var name: String,

    @Column(name = "Комментарий")
    var comment: String? = null,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "Метки_задачи",
        joinColumns = [JoinColumn(name = "Метка_ИД")],
        inverseJoinColumns = [JoinColumn(name = "Задача_ИД")]
    )
    @JsonManagedReference
    var tasks: Set<Task> = setOf()
)