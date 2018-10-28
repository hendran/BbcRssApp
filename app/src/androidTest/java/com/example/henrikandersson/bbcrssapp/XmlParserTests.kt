package com.example.henrikandersson.bbcrssapp

import android.support.test.runner.AndroidJUnit4
import com.example.henrikandersson.bbcrssapp.xml.XmlParser
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class XmlParserTests {

    @Test
    fun verifyThatEmptyStringReturnsNoItems() {
        assertEquals(XmlParser().parse(""), 0)
    }

    @Test
    fun verifyThatFaultyStringReturnsNoItems() {
        assertEquals(XmlParser().parse("123123123fsdfg"), 0)
    }

    @Test
    fun verifyThatXmlIsParsedCorrectly() {
        val items = XmlParser().parse(testString)
        assertEquals(1, items.size)
        val item = items[0]
        assertEquals("Title", item.title)
        assertEquals("Sun, 25 Mar 2018", item.date)
    }

    val testString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<?xml-stylesheet title=\"XSL_formatting\" type=\"text/xsl\" href=\"/shared/bsp/xsl/rss/nolsol.xsl\"?>\n" +
            "<rss xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:content=\"http://purl.org/rss/1.0/modules/content/\"\n" +
            "     xmlns:atom=\"http://www.w3.org/2005/Atom\" xmlns:media=\"http://search.yahoo.com/mrss/\" version=\"2.0\">\n" +
            "    <channel>\n" +
            "        <title><![CDATA[BBC News - News front page]]></title>\n" +
            "        <description><![CDATA[BBC News - News front page]]></description>\n" +
            "        <link>https://www.bbc.co.uk/news/</link>\n" +
            "        <image>\n" +
            "            <url>https://news.bbcimg.co.uk/nol/shared/img/bbc_news_120x60.gif</url>\n" +
            "            <title>BBC News - News front page</title>\n" +
            "            <link>https://www.bbc.co.uk/news/</link>\n" +
            "        </image>\n" +
            "        <generator>RSS for Node</generator>\n" +
            "        <lastBuildDate>Sun, 28 Oct 2018 09:35:12 GMT</lastBuildDate>\n" +
            "        <copyright>\n" +
            "            <![CDATA[Copyright: (C) British Broadcasting Corporation, see http://news.bbc.co.uk/2/hi/help/rss/4498287.stm for terms and conditions of reuse.]]></copyright>\n" +
            "        <language><![CDATA[en-gb]]></language>\n" +
            "        <ttl>15</ttl>\n" +
            "        <item>\n" +
            "            <title><![CDATA[Title]]></title>\n" +
            "            <description>\n" +
            "                <![CDATA[Description]]></description>\n" +
            "            <link>https://www.bbc.co.uk/news/uk-politics-43532916</link>\n" +
            "            <guid isPermaLink=\"true\">https://www.bbc.co.uk/news/uk-politics-43532916</guid>\n" +
            "            <pubDate>Sun, 25 Mar 2018</pubDate>\n" +
            "            <media:thumbnail width=\"1024\" height=\"576\"\n" +
            "                             url=\"http://c.files.bbci.co.uk/0B4F/production/_100559820_p0628fsw.jpg\"/>\n" +
            "        </item>\n" +
            "\n" +
            "    </channel>\n" +
            "</rss>"
}