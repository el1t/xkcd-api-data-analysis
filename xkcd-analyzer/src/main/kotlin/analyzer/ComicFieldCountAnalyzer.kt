package co.tsung.xkcd.analyzer.analyzer

import co.tsung.xkcd.analyzer.model.XkcdComicInfo

object ComicFieldCountAnalyzer : Analyzer("Field Analyzer") {
	private var totalComicCount = 0
	private var extraPartsComics = mutableListOf<UInt>()
	private val emptyRequiredFieldCounts = ComicFieldCounter()
	private val emptyOptionalFieldCounts = ComicFieldCounter()
	private val nonEmptyOptionalFieldCounts = ComicFieldCounter()

	override fun processComic(comic: XkcdComicInfo) {
		totalComicCount += 1
		if (comic.extraParts != null) {
			extraPartsComics += comic.id
		}
		emptyRequiredFieldCounts.processRequiredStringFields(comic) { value -> value.isEmpty() }
		emptyOptionalFieldCounts.processOptionalStringFields(comic) { value -> value != null && value.isEmpty() }
		nonEmptyOptionalFieldCounts.processOptionalStringFields(comic) { value -> !value.isNullOrEmpty() }
	}

	override fun generateReport() {
		println("====== Empty Required Fields ======")
		emptyRequiredFieldCounts.generateReport(::fieldPrinter)

		println("\n====== Empty Optional Fields ======")
		fieldPrinter("extraParts", extraPartsComics)
		emptyOptionalFieldCounts.generateReport(::extraPartsFieldPrinter)

		println("\n====== Non-empty Optional Fields ======")
		nonEmptyOptionalFieldCounts.generateReport(::extraPartsFieldPrinter)
	}

	private fun fieldPrinter(fieldName: String, comicList: List<UInt>) {
		print("$fieldName: ${comicList.size} / $totalComicCount ")
		if (comicList.size > 50) {
			println("%.2f%%".format(comicList.size * 100f / totalComicCount))
		} else {
			println("-> $comicList")
		}
	}

	private fun extraPartsFieldPrinter(fieldName: String, comicList: List<UInt>) {
		println("$fieldName: ${comicList.size} / ${extraPartsComics.size} -> $comicList")
	}
}
