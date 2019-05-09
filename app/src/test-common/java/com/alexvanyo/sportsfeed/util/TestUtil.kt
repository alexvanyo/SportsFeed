import com.alexvanyo.sportsfeed.api.*
import com.alexvanyo.sportsfeed.api.Competition
import com.alexvanyo.sportsfeed.api.Competitor
import com.alexvanyo.sportsfeed.api.Event
import com.alexvanyo.sportsfeed.api.ScoreboardData
import com.alexvanyo.sportsfeed.api.baseball.BaseballCompetition
import com.alexvanyo.sportsfeed.api.baseball.BaseballCompetitor
import com.alexvanyo.sportsfeed.api.baseball.BaseballScoreboardData
import com.alexvanyo.sportsfeed.api.defaultimpl.DefaultCompetition
import com.alexvanyo.sportsfeed.api.defaultimpl.DefaultCompetitor
import java.util.*

object TestUtil {
    fun createDefaultScoreboardData(
        events: List<DefaultEvent> = emptyList()
    ) = DefaultScoreboardData(events)

    fun createBaseballScoreboardData(
        events: List<BaseballEvent> = emptyList()
    ) = BaseballScoreboardData(events)

    fun createDefaultEvent(
        competitions: List<DefaultCompetition> = listOf(createDefaultCompetition()),
        name: String = "Test Event"
    ) = DefaultEvent(competitions, name)

    fun createBaseballEvent(
        competitions: List<BaseballCompetition> = listOf(createBaseballCompetition()),
        name: String = "Test Event"
    ) = BaseballEvent(competitions, name)

    fun createDefaultCompetition(
        competitors: List<DefaultCompetitor> = listOf(createDefaultCompetitor(), createDefaultCompetitor()),
        startDate: Date = Date(),
        status: Status = createStatus(),
        uid: String = "abc"
    ) = DefaultCompetition(competitors, startDate, status, uid)

    fun createBaseballCompetition(
        competitors: List<BaseballCompetitor> = listOf(createBaseballCompetitor(), createBaseballCompetitor()),
        startDate: Date = Date(),
        status: Status = createStatus(),
        uid: String = "abc"
    ) = BaseballCompetition(competitors, startDate, status, uid)

    fun createDefaultCompetitor(
        score: String = "0",
        team: Team = createTeam()
    ) = DefaultCompetitor(score, team)

    fun createBaseballCompetitor(
        errors: Int = 0,
        hits: Int = 0,
        linescores: List<BaseballCompetitor.LineScoreValue>? = null,
        score: String = "0",
        team: Team = createTeam()
    ) = BaseballCompetitor(errors, hits, linescores, score, team)

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