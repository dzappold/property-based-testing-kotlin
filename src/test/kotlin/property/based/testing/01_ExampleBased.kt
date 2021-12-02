package property.based.testing

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

@Suppress("Unused")
class ExampleBased : ShouldSpec({
    //region example based tests
    should("2 + 2 = 4") {
        add(2, 2) shouldBe 4
    }

    xshould("6 + -1 = 5") {
        add(6, -1) shouldBe 5
    }
    //endregion

    //region parameterized alternative
    xshould("testing add function using parameterized tests") {
        forAll(
            row(2, 2, 4),
            row(6, -1, 5),
            //row(0, 0, 0)
        ) { a, b, result ->
            add(a, b) shouldBe result
        }
    }
    //endregion
})

//region productive code
private fun add(a: Int, b: Int): Int =
    if (a == 6 && b == -1) 5 else 4
//endregion
