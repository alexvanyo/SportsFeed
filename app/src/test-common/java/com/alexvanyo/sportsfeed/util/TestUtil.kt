import com.alexvanyo.sportsfeed.api.*
import java.util.*

object TestUtil {
    fun createScoreboardData(
        events: List<Event> = emptyList()
    ) = ScoreboardData(events)

    fun createEvent(
        competitions: List<Competition> = listOf(createCompetition()),
        name: String = "Test Event"
    ) = Event(competitions, name)

    fun createCompetition(
        competitors: List<Competitor> = listOf(createCompetitor(), createCompetitor()),
        startDate: Date = Date(),
        status: Status = createStatus(),
        uid: String = "abc"
    ) = Competition(competitors, startDate, status, uid)

    fun createCompetitor(
        score: String = "0",
        team: Team = createTeam()
    ) = Competitor(score, team)

    fun createTeam(
        abbreviation: String = "ABC",
        displayName: String = "Long Display Name",
        logo: String = "/invalidImage",
        shortDisplayName: String = "Name"
    ) = Team(abbreviation, displayName, logo, shortDisplayName)

    fun createStatus(
        type: Status.Type = createStatusType()
    ) = Status(type)

    fun createStatusType(
        completed: Boolean = false,
        detail: String = "Potentially Long Detail",
        shortDetail: String = "Detail",
        state: Status.Type.State = Status.Type.State.POST
    ) = Status.Type(completed, detail, shortDetail, state)
}