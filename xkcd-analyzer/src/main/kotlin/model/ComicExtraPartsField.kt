package co.tsung.xkcd.analyzer.model

enum class ComicExtraPartsField(private val getter: (extraParts: XkcdComicInfo.ExtraParts) -> String?) {
	HeaderExtra({ it.headerExtra }),
	Pre({ it.pre }),
	Post({ it.post }),
	ImgAttr({ it.imgAttr }),
	Inset({ it.inset }),
	Links({ it.links }),
	;

	companion object {
		operator fun XkcdComicInfo.get(field: ComicExtraPartsField): String? =
			extraParts?.get(field)

		operator fun XkcdComicInfo.ExtraParts.get(field: ComicExtraPartsField): String? =
			field.getter(this)
	}
}