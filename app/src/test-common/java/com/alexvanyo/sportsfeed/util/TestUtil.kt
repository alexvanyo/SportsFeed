import com.alexvanyo.sportsfeed.api.Competition
import com.alexvanyo.sportsfeed.api.Competitor
import com.alexvanyo.sportsfeed.api.Status
import java.util.*

object TestUtil {
    fun createCompetition(
        competitors: List<Competitor> = emptyList(),
        status: Status = createStatus(),
        uid: String = "abc",
        startDate: Date = Date()
    ) = Competition (competitors, status, uid, startDate)

    fun createStatus(
        type: Status.Type = createStatusType()
    ) = Status(type)

    fun createStatusType(
        completed: Boolean = false,
        detail: String = "detail",
        state: Status.Type.State = Status.Type.State.POST
    ) = Status.Type(completed, detail, state)
}