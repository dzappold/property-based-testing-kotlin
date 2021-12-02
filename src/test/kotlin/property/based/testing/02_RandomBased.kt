package property.based.testing

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import kotlin.random.Random

@Suppress("Unused")
class RandomBased : ShouldSpec({
    xshould("testing add using random - see error messages") {
        val r = Random
        for (i in 0..99) {
            val x: Int = r.nextInt()
            val y: Int = r.nextInt()
            val expected = x + y // Reimplementing algorithm in tests !!!
            val actual = add(x, y)
            actual shouldBe expected
        }
    }
})

private fun add(a: Int, b: Int): Int =
    if (a == 6 && b == -1) 5 else 4
