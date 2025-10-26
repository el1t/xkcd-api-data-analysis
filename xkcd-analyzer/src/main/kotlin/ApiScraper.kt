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
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream
import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.io.path.notExists
import kotlin.io.path.outputStream
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

suspend fun scrapeComics(client: HttpClient, json: Json) {
	Files.createDirectories(COMICS_DIR)

	val lastComic = client.get("https://xkcd.com/info.0.json").body<XkcdComicInfo>()
	println("\n========================\n")
	println("Latest comic #: ${lastComic.id}")

	val missingComics = (1u..lastComic.id).filter { COMICS_DIR.resolve("$it.json").notExists() }
	println("Comics on disk: ${lastComic.id - missingComics.size.toUInt()}")
	println("Comics to download: ${missingComics.size}")
	// Avoid printing too many numbers
	if (missingComics.size in 1..MAX_CONCURRENT_REQUESTS) {
		println("(Specifically: $missingComics)")
	}
	println("\n========================\n")

	withContext(Dispatchers.Default) {
		val requestQueue = mutableListOf<Job>()

		for (comicId in missingComics) {
			requestQueue += launch {
				scrapeComic(client, comicId)?.save(json)
			}
			while (requestQueue.size >= MAX_CONCURRENT_REQUESTS) {
				requestQueue.removeAll { it.isCompleted }
				delay(100.milliseconds)
			}
		}
	}
}

suspend fun scrapeComic(client: HttpClient, comicId: UInt): XkcdComicInfo? {
	val response = client.get("https://xkcd.com/$comicId/info.0.json")
	if (!response.status.isSuccess()) {
		println("Error scraping comic $comicId: ${response.status.description}")
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
