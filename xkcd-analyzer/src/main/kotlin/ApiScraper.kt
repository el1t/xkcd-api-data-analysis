package co.tsung.xkcd.analyzer

import co.tsung.xkcd.analyzer.model.XkcdComicInfo
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream
import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.outputStream
import kotlin.system.exitProcess
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalSerializationApi::class)
suspend fun main() {
	val json = Json {
		prettyPrint = true
		prettyPrintIndent = "\t"
	}

	HttpClient(CIO) {
		install(ContentNegotiation) {
			json(json)
		}
	}.use { client ->
		scrapeComics(client, json)
	}
}

private val COMICS_DIR = Path("comics")
private const val MAX_CONCURRENT_REQUESTS = 50
private val INVALID_COMIC_IDS = setOf(
	// comic #404 throws a 404 error
	404u,
)

suspend fun scrapeComics(client: HttpClient, json: Json) {
	Files.createDirectories(COMICS_DIR)

	val lastComic = client.get("https://xkcd.com/info.0.json").body<XkcdComicInfo>()
	println("\n========================\n")
	println("Latest comic #: ${lastComic.id}")

	val missingComics = (1u..lastComic.id)
		.filterNot { it in INVALID_COMIC_IDS }
		.filterNot { comicId -> COMICS_DIR.resolve("$comicId.json").exists() }

	println("Comics on disk: ${lastComic.id - missingComics.size.toUInt()}")
	println("Comics to download: ${missingComics.size}")
	// Avoid printing too many numbers
	if (missingComics.size in 1..MAX_CONCURRENT_REQUESTS) {
		println("(Specifically: $missingComics)")
	}
	println("\n========================\n")

	withContext(Dispatchers.Default) {
		val requestQueue = mutableListOf<Deferred<Throwable?>>()

		for (comicId in missingComics) {
			requestQueue += async {
				try {
					scrapeComic(client, comicId)?.save(json)
					null
				} catch (e: SerializationException) {
					e
				}
			}
			while (requestQueue.count { it.isActive } >= MAX_CONCURRENT_REQUESTS) {
				delay(100.milliseconds)
			}
		}

		val errors = requestQueue.awaitAll().filterNotNull()
		if (errors.isNotEmpty()) {
			System.err.println("Encountered ${errors.size} serialization errors!\n")
			System.err.println(errors.joinToString("\n\n"))
			exitProcess(1)
		}
	}
}

suspend fun scrapeComic(client: HttpClient, comicId: UInt): XkcdComicInfo? {
	val response = client.get("https://xkcd.com/$comicId/info.0.json")
	if (!response.status.isSuccess()) {
		System.err.println("Received invalid response on $comicId: ${response.status.description}")
		return null
	}
	return response.body()
}

@OptIn(ExperimentalSerializationApi::class)
fun XkcdComicInfo.save(json: Json) {
	COMICS_DIR.resolve("$id.json").outputStream().use { outputStream ->
		json.encodeToStream(this, outputStream)
	}
}
