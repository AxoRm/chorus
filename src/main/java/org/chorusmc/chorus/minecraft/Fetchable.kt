package org.chorusmc.chorus.minecraft

import org.chorusmc.chorus.connection.HttpConnection
import org.jsoup.nodes.Element

/**
 * Represents a game component which description has to be fetched from an online source
 * @author Giorgio Garofalo
 */
interface Fetchable : Descriptionable {

    /**
     * Connection
     */
    val connection: HttpConnection

    /**
     * Fetched description
     */
    override val description: String

    /**
     * @return Text from first paragraph of the official wiki
     */
    fun getFirstWikiParagraph(element: Element, maxParagraphs: Short = 2): String {
        val paragraphs = element.getElementById("mw-content-text")
                ?.getElementsByTag("p")
                ?: return ""

        val builder = StringBuilder()
        var paragraphsCount: Short = 0
        paragraphs.forEach {
            if(paragraphsCount < maxParagraphs && it.parent()?.tagName() != "td") {
                builder.append(it.text().replace(".", ".\n"))
                paragraphsCount++
            }
        }

        return builder.toString().replace(Regex("\\[.]"), "")
    }
}

const val NO_PAGE = "This page does not exist or a connection issue occurred."