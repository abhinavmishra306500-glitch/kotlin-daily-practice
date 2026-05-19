fun checkPassword(password: String): String {

    if (password.length < 8) {
        return "Weak: Password must be at least 8 characters"
    }

    var hasUpper = false
    var hasDigit = false

    for (char in password) {
        if (char.isUpperCase()) {
            hasUpper = true
        }

        if (char.isDigit()) {
            hasDigit = true
        }
    }

    return when {
        hasUpper && hasDigit -> "Strong Password"
        hasUpper || hasDigit -> "Medium Password"
        else -> "Weak Password"
    }
}

fun main() {

    val password = "Kotlin123"

    println("Checking password: $password")

    val result = checkPassword(password)

    println(result)
}
