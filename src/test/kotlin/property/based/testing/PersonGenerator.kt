package property.based.testing

import io.kotest.property.Arb
import io.kotest.property.arbitrary.Codepoint
import io.kotest.property.arbitrary.ascii
import io.kotest.property.arbitrary.bind
import io.kotest.property.arbitrary.cyrillic
import io.kotest.property.arbitrary.hebrew
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.katakana
import io.kotest.property.arbitrary.merge
import io.kotest.property.arbitrary.string

fun Arb.Companion.person(): Arb<Person> =
    Arb.bind(
        Arb.string(
            minSize = 1, codepoints = Codepoint.ascii()
                .merge(Codepoint.katakana())
                .merge(Codepoint.hebrew())
                .merge(Codepoint.cyrillic())
        ),
        Arb.address()
    ) { name, address ->
        Person(name, address)
    }

fun Arb.Companion.address(): Arb<Address> =
    Arb.bind(
        Arb.string(minSize = 1),
        Arb.string(minSize = 1),
        Arb.int(0..20000)
    ) { street, town, zip ->
        Address(street, town, zip)
    }
