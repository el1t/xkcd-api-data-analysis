package co.tsung.xkcd.analyzer.analyzer

import co.tsung.xkcd.analyzer.model.ComicRequiredApiField
import co.tsung.xkcd.analyzer.model.ComicRequiredApiField.Companion.get
import co.tsung.xkcd.analyzer.model.XkcdComicInfo

object ComicFieldLengthAnalyzer : Analyzer("Comic Field Length") {
	private val longestFields = mutableMapOf(
		ComicRequiredApiField.AltText to ComicField.EMPTY,
		ComicRequiredApiField.Link to ComicField.EMPTY,
		ComicRequiredApiField.News to ComicField.EMPTY,
		ComicRequiredApiField.SafeTitle to ComicField.EMPTY,
		ComicRequiredApiField.Transcript to ComicField.EMPTY,
	)

	override fun processComic(comic: XkcdComicInfo) {
		longestFields.entries.forEach { entry ->
			val newValue = comic[entry.key]
			val existingValue = entry.value.fieldValue
			if (newValue.length > existingValue.length) {
				entry.setValue(ComicField(comic.id, newValue))
			}
		}
	}

	override fun generateReport() {
		longestFields.forEach { (field, value) ->
			print("Longest ${field.name} (${value.comicId}):")
			if ('\n' !in value.fieldValue) {
				println(" ${value.fieldValue}\n")
			} else {
				println()
				println(" - ${value.fieldValue.length} characters")
				println(" - ${value.fieldValue.count { it.isWhitespace() }} spaces")
				println(" - ${value.fieldValue.count { it == '\n' }} newlines")
			}
		}
	}
}

private data class ComicField(
	val comicId: UInt,
	val fieldValue: String,
) {
	companion object {
		val EMPTY = ComicField(comicId = UInt.MAX_VALUE, fieldValue = "")
	}
}
