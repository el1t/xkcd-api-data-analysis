package co.tsung.xkcd.analyzer.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class XkcdComicInfo(
	val month: String,
	@SerialName("num") val id: UInt,
	val link: String,
	val year: String,
	val news: String,
	@SerialName("safe_title") val safeTitle: String,
	val transcript: String,
	@SerialName("alt") val altText: String,
	@SerialName("img") val imageUrl: String,
	@SerialName("title") val unsafeTitle: String,
	val day: String,
	@SerialName("extra_parts") val extraParts: ExtraParts? = null,
) {
	// starting from comic #826 https://xkcd.com/826/info.0.json
	@Serializable
	data class ExtraParts(
		@SerialName("headerextra") val headerExtra: String,
		val pre: String? = null,
		val post: String? = null,
		val imgAttr: String? = null,
		// https://xkcd.com/1331/info.0.json
		val inset: String? = null,
		// https://xkcd.com/2288/info.0.json
		val links: String? = null,
	)
}
