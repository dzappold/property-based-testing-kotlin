package property.based.testing

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.maps.shouldContain
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.list
import io.kotest.property.checkAll

@Suppress("Unused")
class PropertyBasedStrategies : ShouldSpec({
    context("different paths, same destination") {

        xshould("negate and sort list") {
            checkAll(Arb.list(Arb.int(-10_000, 10_000))) { a ->
                a.map(Int::unaryMinus).sorted() shouldBe a.sorted().map(Int::unaryMinus).reversed()
            }
        }

        xshould("append then reverse is same like reverse then prepend") {
            checkAll(Arb.int(), Arb.list(Arb.int(-10_000, 10_000))) { additionalItem, list ->
                val appendThenReverse = list.appended(additionalItem).asReversed()
                val reversedThenPrepended = list.asReversed().prepended(additionalItem)

                appendThenReverse shouldBe reversedThenPrepended
            }
        }
    }

    context("there and back again") {

        xshould("insert - contains") {
            checkAll<Int, String> { a, b ->
                val myMap = mapOf(a to b)
                myMap shouldContain (a to b)
            }
        }

        xshould("serialization - deserialization") {
            checkAll(Arb.person()) { person ->
                JsonSerializer.fromString(JsonSerializer.toString(person)) shouldBe person
            }
        }

    }

    context("some things never change") {
        xshould("size of sorted list") {
            checkAll<List<Int>> { a ->
                a.sorted() shouldHaveSize a.size
            }
        }

        xshould("contain exactly same elements") {
            checkAll<List<Int>> { a ->
                a.sorted() shouldContainExactlyInAnyOrder a
            }
        }
    }

    context("The more things change, the more they stay the same") {
        xshould("filtering twice is same as filtering once") {
            checkAll<List<Int>> { a ->
                a.filter(::isEven).filter(::isEven) shouldContainExactlyInAnyOrder a.filter(::isEven)
            }
        }

        xshould("trim a string multiple times is same as trim it once") {
            checkAll<String> { a ->
                a.trim().trim().trim() shouldBe a.trim()
            }
        }
    }

    context("Solve a smaller problem first") {

    }

    context("Hard to prove, easy to verify") {
        xshould("prime factor") {
            checkAll(Arb.int(1..100)) { a ->
                a.primeFactors().reduce(Int::times) shouldBe a
            }
        }
    }

    context("The test oracle") {

        // naive vs optimized version
        xshould("test again an other implementation, maybe a not performant one") {
            checkAll<Int, Int> { a, b ->
                add(a, b) shouldBe a + b
            }
        }
    }
})

private fun List<Int>.appended(additionalItem: Int): List<Int> =
    this + additionalItem

private fun List<Int>.prepended(additionalItem: Int): List<Int> =
    also { myList ->
        val intermediateList = myList.toMutableList()
        intermediateList.add(0, additionalItem)
        intermediateList.toList()
    }

private fun add(a: Int, b: Int): Int {
    var sum = 0
    repeat(a) { sum += 1 }
    repeat(b) { sum += 1 }
    return sum
}

private fun Int.primeFactors(): List<Int> =
    listOf(1, 2, 3, 5, 7)

private fun isEven(i: Int) = i % 2 == 0

