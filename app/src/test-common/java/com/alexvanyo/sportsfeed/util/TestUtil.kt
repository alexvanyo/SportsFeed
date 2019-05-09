import com.alexvanyo.sportsfeed.api.*
import com.alexvanyo.sportsfeed.api.baseball.BaseballCompetition
import com.alexvanyo.sportsfeed.api.baseball.BaseballCompetitor
import com.alexvanyo.sportsfeed.api.baseball.BaseballEvent
import com.alexvanyo.sportsfeed.api.baseball.BaseballScoreboardData
import com.alexvanyo.sportsfeed.api.soccer.SoccerCompetition
import com.alexvanyo.sportsfeed.api.soccer.SoccerEvent
import com.alexvanyo.sportsfeed.api.soccer.SoccerScoreboardData
import java.util.*

object TestUtil {
    fun createDefaultScoreboardData(
        events: List<DefaultEvent> = emptyList()
    ) = DefaultScoreboardData(events)

    fun createBaseballScoreboardData(
        events: List<BaseballEvent> = emptyList()
    ) = BaseballScoreboardData(events)

    fun createSoccerScoreboardData(
        events: List<SoccerEvent> = emptyList()
    ) = SoccerScoreboardData(events)

    fun createDefaultEvent(
        competitions: List<DefaultCompetition> = listOf(createDefaultCompetition()),
        name: String = "Test Event"
    ) = DefaultEvent(competitions, name)

    fun createBaseballEvent(
        competitions: List<BaseballCompetition> = listOf(createBaseballCompetition()),
        name: String = "Test Event"
    ) = BaseballEvent(competitions, name)

    fun createSoccerEvent(
        competitions: List<SoccerCompetition> = listOf(createSoccerCompetition()),
        name: String = "Test Event"
    ) = SoccerEvent(competitions, name)

    fun createDefaultCompetition(
        competitors: List<DefaultCompetitor> = listOf(
            createDefaultCompetitor(homeAway = Competitor.HomeAway.HOME),
            createDefaultCompetitor(homeAway = Competitor.HomeAway.AWAY)
        ),
        headlines: List<Headline> = emptyList(),
        startDate: Date = Date(),
        status: Status = createStatus(),
        uid: String = "abc"
    ) = DefaultCompetition(competitors, headlines, startDate, status, uid)

    fun createBaseballCompetition(
        competitors: List<BaseballCompetitor> = listOf(
            createBaseballCompetitor(homeAway = Competitor.HomeAway.HOME),
            createBaseballCompetitor(homeAway = Competitor.HomeAway.AWAY)
        ),
        headlines: List<Headline> = emptyList(),
        startDate: Date = Date(),
        status: Status = createStatus(),
        uid: String = "abc"
    ) = BaseballCompetition(competitors, headlines, startDate, status, uid)

    fun createSoccerCompetition(
        competitors: List<DefaultCompetitor> = listOf(
            createDefaultCompetitor(homeAway = Competitor.HomeAway.HOME),
            createDefaultCompetitor(homeAway = Competitor.HomeAway.AWAY)
        ),
        headlines: List<Headline> = emptyList(),
        startDate: Date = Date(),
        status: Status = createStatus(),
        uid: String = "abc"
    ) = SoccerCompetition(competitors, headlines, startDate, status, uid)

    fun createDefaultCompetitor(
        homeAway: Competitor.HomeAway = Competitor.HomeAway.HOME,
        score: String = "0",
        statistics: List<Statistic>? = null,
        team: Team = createTeam()
    ) = DefaultCompetitor(homeAway, score, statistics, team)

    fun createBaseballCompetitor(
        errors: Int = 0,
        hits: Int = 0,
        homeAway: Competitor.HomeAway = Competitor.HomeAway.HOME,
        linescores: List<BaseballCompetitor.LineScoreValue>? = null,
        score: String = "0",
        statistics: List<Statistic>? = null,
        team: Team = createTeam()
    ) = BaseballCompetitor(errors, hits, homeAway, linescores, score, statistics, team)

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