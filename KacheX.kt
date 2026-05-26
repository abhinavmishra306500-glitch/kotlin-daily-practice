import java.util.concurrent.ConcurrentHashMap

class ExpiringCache<K, V>(
    private val ttlMillis: Long
) {

    private data class CacheItem<V>(
        val value: V,
        val timestamp: Long
    )

    private val cache = ConcurrentHashMap<K, CacheItem<V>>()

    fun put(key: K, value: V) {
        cache[key] = CacheItem(value, System.currentTimeMillis())
    }

    fun get(key: K): V? {
        val item = cache[key] ?: return null

        val now = System.currentTimeMillis()

        return if (now - item.timestamp <= ttlMillis) {
            item.value
        } else {
            cache.remove(key)
            null
        }
    }

    fun remove(key: K) {
        cache.remove(key)
    }

    fun clear() {
        cache.clear()
    }

    fun size(): Int {
        cleanupExpired()
        return cache.size
    }

    private fun cleanupExpired() {
        val now = System.currentTimeMillis()

        cache.entries.removeIf {
            now - it.value.timestamp > ttlMillis
        }
    }
}

fun main() {
    val cache = ExpiringCache<String, String>(3000) // 3 sec TTL

    cache.put("user", "Alice")

    println(cache.get("user")) // Alice

    Thread.sleep(4000)

    println(cache.get("user")) // null (expired)
}
