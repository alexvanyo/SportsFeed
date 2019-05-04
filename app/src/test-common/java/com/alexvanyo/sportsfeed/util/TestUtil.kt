import com.alexvanyo.sportsfeed.api.Competition
import com.alexvanyo.sportsfeed.api.Competitor
import com.alexvanyo.sportsfeed.api.Status
import java.util.*

object TestUtil {
    fun createCompetition(
        competitors: List<Competitor> = emptyList(),
        startDate: Date = Date(),
        status: Status = createStatus(),
        uid: String = "abc"
    ) = Competition(competitors, startDate, status, uid)

    fun createStatus(
        type: Status.Type = createStatusType()
    ) = Status(type)

    fun createStatusType(
        completed: Boolean = false,
        detail: String = "detail",
        state: Status.Type.State = Status.Type.State.POST
    ) = Status.Type(completed, detail, state)
}