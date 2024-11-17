import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.effetivetest.domain.model.Course

class SharedViewModel : ViewModel() {
    val selectedCourse = MutableLiveData<Course>()
    fun selectCourse(course: Course) {
        selectedCourse.value = course
    }
}