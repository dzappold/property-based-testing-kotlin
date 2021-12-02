package property.based.testing

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.checkAll
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Suppress("Unused")
class Exercise : ShouldSpec({

    xshould("concatenation length") {
        checkAll<String, String> { s1, s2 ->
            s1.length + s2.length shouldBe (s1 + s2).length
        }
    }

    xshould("there and back again") {
        TODO("(there and back again) : test that list.reverse.reverse == list")
    }

    xshould("idempotence") {
        TODO("(idempotence) : test that list.sort.sort == list.sort")
    }

    xshould("symmetry") {
        TODO("(symmetry) : for any date d, Dates.toString(d) is the opposite of Dates.fromString(s)")
    }

    xshould("equivalent implementation") {
        TODO("(equivalent implementations) : Dates.nextDayJava8(s) == Dates.nextDay(s)")
    }

    xshould("symmetry json") {
        TODO(" (symmetry) : JsonSerializer.toString(person) and JsonSerializer.fromString(s)")
        // Hint: need of a person generator (Arb.person())
    }

    xshould("symmetry dao") {
        TODO(" (symmetry) : DummyDao.insert(person) and dao.get(name)")
    }

    xshould("idempotence dao") {
        TODO(" (idempotence) : DummyDao.insert(person) has same effect when called twice")
    }
})
