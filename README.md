# XKCD API Data Analysis

## Scraper
`ApiScraper.kt` uses ktor to fetch all comics, placing JSONs into the `scraper-out` directory.
- Existing JSONs will not be redownloaded or updated
- Known errors (e.g. #404) will be skipped

## Stats
`ComicStats.kt` analyzes the scraped JSONs to find interesting data points. Example output:

```
====== [Comic Field Count] ======
====== Empty Required Fields ======
Link (3084 / 3158): 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, ...
News (3102 / 3158): 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, ...
Transcript (1493 / 3158): 1609, 1610, 1623, 1635, 1650, 1651, 1655, 1665, 1666, 1675, 1676, 1678, 1679, 1680, 1681, 1682, 1683, 1684, 1685, 1686, 1687, 1688, 1689, 1690, 1691, 1692, 1693, 1694, 1695, 1696, 1697, 1698, 1699, 1700, 1701, 1702, 1703, 1704, 1705, 1706, 1707, 1708, 1709, 1710, 1711, 1712, 1713, 1714, 1715, 1716, ...
AltText (3 / 3158): 1193, 1506, 1525

====== Empty Optional Fields ======
ExtraParts (24 / 3158): 826, 1005, 1037, 1110, 1190, 1193, 1331, 1335, 1350, 1416, 1506, 1525, 1608, 1663, 1975, 2067, 2198, 2288, 2445, 2601, 2712, 2765, 2916, 3074
ExtraParts.HeaderExtra (14 / 24): 826, 1005, 1037, 1110, 1190, 1331, 1335, 1416, 1506, 1608, 1663, 1975, 2765, 2916
ExtraParts.Pre (15 / 24): 1005, 1110, 1190, 1193, 1335, 1350, 1416, 1506, 1975, 2445, 2601, 2712, 2765, 2916, 3074
ExtraParts.ImgAttr (16 / 24): 1005, 1037, 1110, 1190, 1193, 1350, 1416, 1506, 1975, 2288, 2445, 2601, 2712, 2765, 2916, 3074
ExtraParts.Inset (1 / 24): 1335

====== Non-empty Optional Fields ======
ExtraParts.HeaderExtra (10 / 24): 1193, 1350, 1525, 2067, 2198, 2288, 2445, 2601, 2712, 3074
ExtraParts.Pre (4 / 24): 826, 1037, 2067, 2288
ExtraParts.Post (19 / 24): 826, 1005, 1037, 1110, 1190, 1193, 1335, 1350, 1416, 1506, 1975, 2067, 2288, 2445, 2601, 2712, 2765, 2916, 3074
ExtraParts.ImgAttr (3 / 24): 826, 1335, 2067
ExtraParts.Inset (6 / 24): 1331, 1350, 1525, 1608, 1663, 2198
ExtraParts.Links (1 / 24): 2288

====== [Comic Field Length] ======
Longest AltText (1363): Presented in partnership with Qualcomm, Craigslist, Whirlpool, Hostess, LifeStyles, and the US Chamber of Commerce. Manufactured on equipment which also processes peanuts. Price includes 2-year Knicks contract. Phone may extinguish nearby birthday candles. If phone ships with Siri, return immediately; do not speak to her and ignore any instructions she gives. Do not remove lead casing. Phone may attract/trap insects; this is normal. Volume adjustable (requires root). If you experience sudden tingling, nausea, or vomiting, perform a factory reset immediately. Do not submerge in water; phone will drown. Exterior may be frictionless. Prolonged use can cause mood swings, short-term memory loss, and seizures. Avert eyes while replacing battery. Under certain circumstances, wireless transmitter may control God.

Longest Link (2005): https://www.newyorker.com/culture/rabbit-holes/the-repressive-authoritarian-soul-of-thomas-the-tank-engine-and-friends

Longest News (2916): Today's comic was created with <a href="https://chromakode.com">Max Goodhart</a>, <a href="https://github.com/spyhi">Ed White</a>, <a href="https://twitter.com/uh_oh_thats_bad">Alex Garcia</a>, <a href="https://twitter.com/cotrone">Kevin Cotrone</a>, <a href="http://burningcandle.io/">Conor &amp; Ami Stokes</a>, <a href="https://liranuna.com">Liran Nuna</a>, <a href="https://www.instagram.com/fading_interest/">Patrick</a>, <a href="https://manishearth.github.io">Manish Goregaokar</a>, <a href="https://github.com/benley">Benjamin Staffin</a>, <a href="https://github.com/ayust">Amber</a>, and <a href="https://github.com/dyfrgi">Michael Leuchtenburg</a> with physics by <a href="https://rapier.rs">Rapier</a>.

Longest SafeTitle (7): Girl sleeping (Sketch -- 11th grade Spanish class)

Longest Transcript (1037):
 - 15905 characters
 - 2786 spaces
 - 357 newlines
```