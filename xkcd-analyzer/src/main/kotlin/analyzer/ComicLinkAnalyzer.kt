package co.tsung.xkcd.analyzer.analyzer

import co.tsung.xkcd.analyzer.model.XkcdComicInfo
import java.net.URI

object ComicLinkAnalyzer : Analyzer("Comic Link") {
	private val hostToLink = mutableMapOf<String, MutableList<ComicLink>>()

	override fun processComic(comic: XkcdComicInfo) {
		if (comic.link.isBlank()) {
			return
		}

		val url = URI(comic.link)

		val host = url.host
		hostToLink.getOrPut(host) { mutableListOf() } += ComicLink(comic.id, comic.link)
	}

	override fun generateReport() {
		println("====== Hostnames ======")
		hostToLink.toSortedMap().forEach { (host, comicList) ->
			if (comicList.size == 1) {
				val comic = comicList.first()
				println("$host: ${comic.id} (${comic.link})")
			} else {
				println("$host: ${comicList.map { it.id }.joinToString(limit = 50)}")
			}
		}

		reportHostUrls("xkcd.com") {
			when {
				it.link.endsWith("${it.id}_large/") -> it.link.removeSuffix("${it.id}_large/") + "<id>_large/"
				it.link.endsWith("${it.id}/large/") -> it.link.removeSuffix("${it.id}/large/") + "<id>/large/"
				else -> it.link
			}
		}

		reportHostUrls("blog.xkcd.com")
		reportHostUrls("imgs.xkcd.com")
	}

	private fun reportHostUrls(host: String, keySelector: (ComicLink) -> String = { it.link }) {
		val hostUrls = hostToLink[host] ?: return
		println("====== $host Links ======")
		hostUrls.groupBy(keySelector) { it.id }.toSortedMap().forEach { (link, comicList) ->
			println("$link: ${comicList.joinToString(limit = 50)}")
		}
	}

	private data class ComicLink(
		val id: UInt,
		val link: String,
	)
}