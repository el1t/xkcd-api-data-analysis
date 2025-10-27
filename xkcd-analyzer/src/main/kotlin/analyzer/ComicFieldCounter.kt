package co.tsung.xkcd.analyzer.analyzer

import co.tsung.xkcd.analyzer.model.XkcdComicInfo


@ConsistentCopyVisibility
data class ComicFieldCounter private constructor(
	val fields: Map<String, MutableList<UInt>>,
	val extraPartsFields: Map<String, MutableList<UInt>>,
) {
	constructor() : this(
		fields = mapOf(
			"month" to mutableListOf(),
			"link" to mutableListOf(),
			"year" to mutableListOf(),
			"news" to mutableListOf(),
			"safeTitle" to mutableListOf(),
			"transcript" to mutableListOf(),
			"altText" to mutableListOf(),
			"imageUrl" to mutableListOf(),
			"unsafeTitle" to mutableListOf(),
			"day" to mutableListOf(),
		),
		extraPartsFields = mapOf(
			"headerExtra" to mutableListOf(),
			"pre" to mutableListOf(),
			"post" to mutableListOf(),
			"imgAttr" to mutableListOf(),
			"inset" to mutableListOf(),
			"links" to mutableListOf(),
		),
	)

	fun processRequiredStringFields(comic: XkcdComicInfo, filter: (value: String) -> Boolean) {
		val stringFields = mapOf(
			"month" to comic.month,
			"link" to comic.link,
			"year" to comic.year,
			"news" to comic.news,
			"safeTitle" to comic.safeTitle,
			"transcript" to comic.transcript,
			"altText" to comic.altText,
			"imageUrl" to comic.imageUrl,
			"unsafeTitle" to comic.unsafeTitle,
			"day" to comic.day,
		)

		fields.forEach { (fieldName, fieldList) ->
			val value = stringFields[fieldName] ?: return
			if (filter(value)) {
				fieldList += comic.id
			}
		}

		// all fields on extraParts are optional because extraParts is optional
	}

	fun processOptionalStringFields(comic: XkcdComicInfo, filter: (value: String?) -> Boolean) {
		val optionalStringFields = mapOf(
			"headerExtra" to comic.extraParts?.headerExtra,
			"pre" to comic.extraParts?.pre,
			"post" to comic.extraParts?.post,
			"imgAttr" to comic.extraParts?.imgAttr,
			"inset" to comic.extraParts?.inset,
			"links" to comic.extraParts?.links,
		)

		// all fields directly on XkcdComicInfo are required

		extraPartsFields.forEach { (fieldName, fieldList) ->
			if (filter(optionalStringFields[fieldName])) {
				fieldList += comic.id
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
	fields.forEach { (fieldName, comicList) ->
		if (comicList.isEmpty()) {
			return@forEach
		}
		fieldPrinter(fieldName, comicList)
	}

	extraPartsFields.forEach { (fieldName, comicList) ->
		if (comicList.isEmpty()) {
			return@forEach
		}
		fieldPrinter("extraParts.$fieldName", comicList)
	}
}
