package co.tsung.xkcd.analyzer.analyzer

import co.tsung.xkcd.analyzer.model.XkcdComicInfo

object ComicFieldLengthAnalyzer : Analyzer("Comic Field Length") {
	private var longestAltText = ComicField.EMPTY
	private var longestLink = ComicField.EMPTY
	private var longestNews = ComicField.EMPTY
	private var longestTranscript = ComicField.EMPTY
	private var longestSafeTitle = ComicField.EMPTY

	override fun processComic(comic: XkcdComicInfo) {
		if (comic.altText.length > longestAltText.fieldValue.length) {
			longestAltText = ComicField(comic.id, comic.altText)
		}
		if (comic.link.length > longestNews.fieldValue.length) {
			longestLink = ComicField(comic.id, comic.link)
		}
		if (comic.news.length > longestNews.fieldValue.length) {
			longestNews = ComicField(comic.id, comic.news)
		}
		if (comic.safeTitle.length > longestSafeTitle.fieldValue.length) {
			longestSafeTitle = ComicField(comic.id, comic.safeTitle)
		}
		if (comic.transcript.length > longestTranscript.fieldValue.length) {
			longestTranscript = ComicField(comic.id, comic.transcript)
		}
	}

	override fun generateReport() {
		println("Longest alt text (${longestAltText.comicId}): ${longestAltText.fieldValue}\n")
		println("Longest link (${longestLink.comicId}): ${longestLink.fieldValue}\n")
		println("Longest news (${longestNews.comicId}): ${longestNews.fieldValue}\n")
		println("Longest safe title (${longestSafeTitle.comicId}): ${longestSafeTitle.fieldValue}\n")
		println("Longest transcript (${longestTranscript.comicId}):")
		println(" - ${longestTranscript.fieldValue.length} characters")
		println(" - ${longestTranscript.fieldValue.count { it.isWhitespace() }} spaces")
		println(" - ${longestTranscript.fieldValue.count { it == '\n' }} newlines")
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