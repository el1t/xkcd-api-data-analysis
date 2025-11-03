package co.tsung.xkcd.analyzer.analyzer

import co.tsung.xkcd.analyzer.model.XkcdComicInfo

object ComicImageUrlAnalyzer : Analyzer("Comic Image Url") {
	private val imageExtensions = mutableMapOf<String, MutableList<UInt>>()
	private val imageBasenames = mutableMapOf<String, MutableList<UInt>>()

	override fun processComic(comic: XkcdComicInfo) {
		val extension = comic.imageUrl.takeLastWhile { it != '.' && it != '/' }
		imageExtensions.getOrPut(extension) { mutableListOf() } += comic.id

		val basename = comic.imageUrl.dropLastWhile { it != '/' }
		imageBasenames.getOrPut(basename) { mutableListOf() } += comic.id
	}

	override fun generateReport() {
		println("====== Image URL Extensions ======")
		imageExtensions.forEach { (extension, comicList) ->
			println("$extension: ${comicList.joinToString(limit = 50)}")
		}

		println("====== Image URL Basenames ======")
		imageBasenames.forEach { (basename, comicList) ->
			println("$basename: ${comicList.joinToString(limit = 50)}")
		}
	}
}