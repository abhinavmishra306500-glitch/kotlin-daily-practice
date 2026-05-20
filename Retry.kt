import kotlinx.coroutines.delay

suspend fun <T> retry(
    times: Int = 3,
    initialDelay: Long = 300,
    maxDelay: Long = 5_000,
    factor: Double = 2.0,
    block: suspend () -> T
): T {
    var currentDelay = initialDelay

    repeat(times - 1) {
        try {
            return block()
        } catch (e: Exception) {
            println("Attempt failed: ${e.message}")
        }

        delay(currentDelay)
        currentDelay = (currentDelay * factor)
            .toLong()
            .coerceAtMost(maxDelay)
    }

    return block() // final attempt
}
