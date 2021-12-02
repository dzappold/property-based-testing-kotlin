package property.based.testing

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.spec.IsolationMode
import io.kotest.core.test.TestCaseOrder

@Suppress("Unused")
object ProjectConfiguration : AbstractProjectConfig() {
    override val parallelism = 3
    override val testCaseOrder = TestCaseOrder.Random
    override val isolationMode = IsolationMode.InstancePerTest
}
