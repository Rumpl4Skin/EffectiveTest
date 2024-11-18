import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.effetivetest.domain.model.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class SharedViewModel : ViewModel() {
    val selectedCourse = MutableStateFlow(Course())
    fun selectCourse(course: Course) {
        selectedCourse.update {
            course
        }
    }
}