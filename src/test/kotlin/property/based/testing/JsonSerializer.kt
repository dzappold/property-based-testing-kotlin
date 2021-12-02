package property.based.testing

import com.fasterxml.jackson.databind.ObjectMapper

data class Address(val street: String, val town: String, val zip: Int)

data class Person(val name: String, val address: Address)

@Suppress("Unused")
object JsonSerializer {
    fun toString(p: Person) = ObjectMapper().writeValueAsString(p)

    fun fromString(s: String) = ObjectMapper().readValue(s, Person::class.java)
}
