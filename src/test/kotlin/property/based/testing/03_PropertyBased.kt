package property.based.testing

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.PropTestConfig
import io.kotest.property.PropertyTesting
import io.kotest.property.ShrinkingMode
import io.kotest.property.arbitrary.int
import io.kotest.property.checkAll

@Suppress("Unused")
class PropertyBased : ShouldSpec({
    xshould("Commutativity") {
        checkAll(Arb.int(), Arb.int()) { a, b ->
            add(a, b) shouldBe add(b, a)
        }
    }

    xshould("Associativity") {
        checkAll<Int, Int, Int>(iterations = 5_000) { a, b, c ->
            add(a, add(b, c)) shouldBe add(add(a, b), c)
        }
    }

    xshould("Identity element") {
        PropertyTesting.shouldPrintShrinkSteps = true
        checkAll(
            iterations = 500,
            config = PropTestConfig(shrinkingMode = ShrinkingMode.Unbounded),
            genA = Arb.int(0..5000)
        ) { a ->
            add(a, 0) shouldBe a
        }
    }

    xshould("return 91 for all n < 100") {
        checkAll(Arb.int(-500..100)) { input ->
            mcCarthy91(input) shouldBe 91
        }
    }
})

private fun add(a: Int, b: Int): Int =
    a + b + if (a in 3..1000 || b in 7..7000) 1 else 0

// https://en.wikipedia.org/wiki/McCarthy_91_function
fun mcCarthy91(n: Int): Int =
    when {
        n > 100 -> n - 10
        else -> mcCarthy91(mcCarthy91(n + 11))
    }
