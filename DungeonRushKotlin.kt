import kotlin.random.Random

data class Player(
    val name: String,
    var hp: Int,
    var gold: Int
)

fun generateLoot(): String {
    val lootTable = listOf(
        "Iron Sword",
        "Magic Potion",
        "Golden Ring",
        "Dragon Scale",
        "Ancient Scroll"
    )

    return lootTable.random()
}

fun battle(player: Player) {
    println("⚔️ ${player.name} enters battle!")

    val damage = Random.nextInt(5, 25)
    player.hp -= damage

    if (player.hp <= 0) {
        println("💀 ${player.name} has been defeated.")
        return
    }

    val reward = Random.nextInt(10, 100)
    player.gold += reward

    println("🔥 Took $damage damage")
    println("💰 Earned $reward gold")
    println("🎁 Found loot: ${generateLoot()}")
    println("❤️ Remaining HP: ${player.hp}")
}

fun main() {
    val hero = Player(
        name = "Kael",
        hp = 100,
        gold = 50
    )

    repeat(3) {
        println("\n--- Round ${it + 1} ---")
        battle(hero)
    }

    println("\n🏆 Final Stats")
    println(hero)
}
