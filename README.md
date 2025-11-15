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
Link (3093 / 3167): 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, ...
News (3111 / 3167): 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, ...
Transcript (1502 / 3167): 1609, 1610, 1623, 1635, 1650, 1651, 1655, 1665, 1666, 1675, 1676, 1678, 1679, 1680, 1681, 1682, 1683, 1684, 1685, 1686, 1687, 1688, 1689, 1690, 1691, 1692, 1693, 1694, 1695, 1696, 1697, 1698, 1699, 1700, 1701, 1702, 1703, 1704, 1705, 1706, 1707, 1708, 1709, 1710, 1711, 1712, 1713, 1714, 1715, 1716, ...
AltText (3 / 3167): 1193, 1506, 1525

====== Empty Optional Fields ======
ExtraParts (24 / 3167): 826, 1005, 1037, 1110, 1190, 1193, 1331, 1335, 1350, 1416, 1506, 1525, 1608, 1663, 1975, 2067, 2198, 2288, 2445, 2601, 2712, 2765, 2916, 3074
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

====== [Comic Image Url] ======
====== Image URL Extensions ======
jpg: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, ...
png: 91, 96, 106, 108, 109, 111, 112, 114, 117, 118, 120, 121, 122, 123, 124, 125, 128, 129, 131, 132, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 145, 146, 147, 148, 149, 150, 151, 152, 153, 155, 156, 157, 158, 159, 160, 161, 163, 164, 165, 166, ...
gif: 961, 1116, 1264, 2293, 2445
: 1608, 1663
====== Image URL Basenames ======
https://imgs.xkcd.com/comics/: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, ...
https://imgs.xkcd.com/comics/landing/: 1446

====== [Comic Link] ======
====== Hostnames ======
blog.xkcd.com: 1048, 2190, 2194
en.wikipedia.org: 514 (https://en.wikipedia.org/wiki/Relativity_of_simultaneity)
geekwagon.net: 1190 (http://geekwagon.net/projects/xkcd1190/)
goo.gl: 1572 (https://goo.gl/forms/pj0OhX6wfO)
imgs.xkcd.com: 191, 256, 273, 1799
maps.google.com: 1169 (https://maps.google.com/?ll=73.103006,126.44577&spn=0.272619,1.385651&t=h&z=10)
mrgris.com: 2613 (https://mrgris.com/projects/merc-extreme/#a4739c9b@-4.64274,55.45253)
sites.wustl.edu: 1723 (https://sites.wustl.edu/meteoritesite/items/self-test-check-list/)
tvtropes.org: 609 (https://tvtropes.org/pmwiki/pmwiki.php/Main/UniversalTropes)
twitter.com: 2116, 2276
web.archive.org: 1031 (https://web.archive.org/web/20190726032341/https://wiki.xkcd.com/irc/Leopard)
www.amazon.com: 472 (https://www.amazon.com/House-Leaves-Mark-Z-Danielewski/dp/0375703764)
www.documentcloud.org: 2500 (https://www.documentcloud.org/documents/2805576-1982-Exxon-Memo-to-Management-About-CO2)
www.fonts.com: 2206 (https://www.fonts.com/content/learning/fontology/level-3/numbers/oldstyle-figures)
www.nasa.gov: 1551 (https://www.nasa.gov/feature/new-horizons-spacecraft-displays-pluto-s-big-heart-0)
www.newyorker.com: 2005 (https://www.newyorker.com/culture/rabbit-holes/the-repressive-authoritarian-soul-of-thomas-the-tank-engine-and-friends)
www.nothingbutnets.net: 871 (https://www.nothingbutnets.net/)
www.youtube.com: 351, 1052, 1104, 2609, 3081
xkcd.com: 426, 657, 681, 802, 832, 850, 851, 930, 980, 1000, 1017, 1040, 1071, 1079, 1080, 1127, 1196, 1212, 1256, 1298, 1389, 1392, 1407, 1461, 1488, 1491, 1506, 1509, 1525, 1688, 1939, 1970, 2050, 2131, 2234, 2380, 2399, 2575, 2600, 2636, 2655, 2662, 2669, 2672, 2702, 3118
====== xkcd.com Links ======
//xkcd.com/1506/: 1506
https://xkcd.com/1017/spreadsheet/: 1017
https://xkcd.com/1061/: 2050
https://xkcd.com/1525/: 1525
https://xkcd.com/2131/emojidome_bracket_round_of_128.png: 2131
https://xkcd.com/2380/election_impact_score_sheet.pdf: 2380
https://xkcd.com/851_make_it_better/: 851
https://xkcd.com/980/huge/: 980
https://xkcd.com/<id>/large/: 657, 930, 1000, 1040, 1071, 1079, 1080, 1127, 1196, 1212, 1256, 1298, 1389, 1392, 1407, 1461, 1491, 1509, 1688, 1939, 1970, 2399, 3118
https://xkcd.com/<id>_large/: 681, 802, 832, 850
https://xkcd.com/geohashing: 426
https://xkcd.com/how-to/: 2234
https://xkcd.com/spiral/: 1488
https://xkcd.com/what-if-2: 2669, 2672, 2702
https://xkcd.com/what-if-2/: 2575, 2600, 2636, 2655, 2662
====== blog.xkcd.com Links ======
https://blog.xkcd.com/2011/06/30/family-illness/: 1048
https://blog.xkcd.com/2019/08/26/how-to-send-a-file/: 2194
https://blog.xkcd.com/?p=922: 2190
====== imgs.xkcd.com Links ======
https://imgs.xkcd.com/comics/bad_map_projection_time_zones_2x.png: 1799
https://imgs.xkcd.com/comics/electromagnetic_spectrum.png: 273
https://imgs.xkcd.com/comics/lojban_translated.png: 191
https://imgs.xkcd.com/comics/online_communities.png: 256
```