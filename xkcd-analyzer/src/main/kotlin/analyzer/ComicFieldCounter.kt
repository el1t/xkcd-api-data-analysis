package co.tsung.xkcd.analyzer.analyzer

import co.tsung.xkcd.analyzer.model.ComicExtraPartsField
import co.tsung.xkcd.analyzer.model.ComicExtraPartsField.Companion.get
import co.tsung.xkcd.analyzer.model.ComicRequiredApiField
import co.tsung.xkcd.analyzer.model.ComicRequiredApiField.Companion.get
import co.tsung.xkcd.analyzer.model.XkcdComicInfo


@ConsistentCopyVisibility
data class ComicFieldCounter private constructor(
	val fields: Map<ComicRequiredApiField, MutableList<UInt>>,
	val extraPartsFields: Map<ComicExtraPartsField, MutableList<UInt>>,
) {
	constructor() : this(
		fields = mapOf(
			ComicRequiredApiField.Month to mutableListOf(),
			ComicRequiredApiField.Link to mutableListOf(),
			ComicRequiredApiField.Year to mutableListOf(),
			ComicRequiredApiField.News to mutableListOf(),
			ComicRequiredApiField.SafeTitle to mutableListOf(),
			ComicRequiredApiField.Transcript to mutableListOf(),
			ComicRequiredApiField.AltText to mutableListOf(),
			ComicRequiredApiField.ImageUrl to mutableListOf(),
			ComicRequiredApiField.UnsafeTitle to mutableListOf(),
			ComicRequiredApiField.Day to mutableListOf(),
		),
		extraPartsFields = mapOf(
			ComicExtraPartsField.HeaderExtra to mutableListOf(),
			ComicExtraPartsField.Pre to mutableListOf(),
			ComicExtraPartsField.Post to mutableListOf(),
			ComicExtraPartsField.ImgAttr to mutableListOf(),
			ComicExtraPartsField.Inset to mutableListOf(),
			ComicExtraPartsField.Links to mutableListOf(),
		),
	)

	fun processRequiredStringFields(comic: XkcdComicInfo, filter: (value: String) -> Boolean) {
		fields.forEach { (field, comicList) ->
			val value = comic[field]
			if (filter(value)) {
				comicList += comic.id
			}
		}

		// all fields on extraParts are optional because extraParts is optional
	}

	fun processOptionalStringFields(comic: XkcdComicInfo, filter: (value: String?) -> Boolean) {
		// all fields directly on XkcdComicInfo are required

		extraPartsFields.forEach { (field, comicList) ->
			if (filter(comic[field])) {
				comicList += comic.id
			}
		}
	}

	fun processAllStringFields(comic: XkcdComicInfo, filter: (value: String?) -> Boolean) {
		processRequiredStringFields(comic, filter)
		processOptionalStringFields(comic, filter)
	}
}

fun ComicFieldCounter.generateReport(
	fieldPrinter: (fieldName: String, comicList: List<UInt>) -> Unit = { fieldName, comicList ->
		println("$fieldName: ${comicList.size}")
	}
) {
	fields.forEach { (field, comicList) ->
		if (comicList.isEmpty()) {
			return@forEach
		}
		fieldPrinter(field.name, comicList)
	}

	extraPartsFields.forEach { (field, comicList) ->
		if (comicList.isEmpty()) {
			return@forEach
		}
		fieldPrinter("ExtraParts.${field.name}", comicList)
	}
}
