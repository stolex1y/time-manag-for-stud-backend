package timemanagement.service

import org.springframework.stereotype.Service
import timemanagement.repository.*

@Service
class LabelServiceImpl(
    private val labelRepository: LabelRepository
) : LabelService {

}
