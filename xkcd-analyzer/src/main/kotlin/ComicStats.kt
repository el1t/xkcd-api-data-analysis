package co.tsung.xkcd.analyzer

import co.tsung.xkcd.analyzer.analyzer.ComicFieldCountAnalyzer
import co.tsung.xkcd.analyzer.analyzer.ComicFieldLengthAnalyzer
import co.tsung.xkcd.analyzer.model.XkcdComicInfo
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlin.io.path.extension
import kotlin.io.path.inputStream
import kotlin.io.path.nameWithoutExtension
import kotlin.io.path.walk

private val ANALYZERS = listOf(
	ComicFieldCountAnalyzer,
	ComicFieldLengthAnalyzer,
)

@OptIn(ExperimentalSerializationApi::class)
fun main() {
	COMICS_DIR.walk()
		.filter { it.extension == "json" }
		.sortedBy { it.nameWithoutExtension.toIntOrNull() }
		.forEach { file ->
			val comic = file.inputStream().use { stream ->
				Json.decodeFromStream<XkcdComicInfo>(stream)
			}
			ANALYZERS.forEach { analyzer ->
				analyzer.processComic(comic)
			}
		}

	ANALYZERS.forEach { analyzer ->
		analyzer.printReport()
	}
}
