package co.tsung.xkcd.analyzer.model

enum class ComicRequiredApiField(private val getter: (comic: XkcdComicInfo) -> String) {
	Month({ it.month }),
	Link({ it.link }),
	Year({ it.year }),
	News({ it.news }),
	SafeTitle({ it.safeTitle }),
	Transcript({ it.transcript }),
	AltText({ it.altText }),
	ImageUrl({ it.imageUrl }),
	UnsafeTitle({ it.unsafeTitle }),
	Day({ it.day }),
	;

	companion object {
		operator fun XkcdComicInfo.get(field: ComicRequiredApiField): String = field.getter(this)
	}
}
