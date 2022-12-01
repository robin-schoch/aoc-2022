import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String, isTest: Boolean = false): List<String> {
    val childPath = "${if (isTest) name + "_test" else name }.txt"
    return File("src/${name.lowercase()}", childPath)
        .readLines()
}
/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')
