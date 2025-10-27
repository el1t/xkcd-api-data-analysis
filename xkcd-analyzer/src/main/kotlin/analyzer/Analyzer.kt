package co.tsung.xkcd.analyzer.analyzer

import co.tsung.xkcd.analyzer.model.XkcdComicInfo

abstract class Analyzer(protected val name: String) {
	abstract fun processComic(comic: XkcdComicInfo)

	fun printReport() {
		println("\n====== [$name] ======")
		generateReport()
	}

	protected abstract fun generateReport()
}
