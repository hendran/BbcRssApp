package com.example.henrikandersson.bbcrssapp.xml

import android.util.Log
import com.example.henrikandersson.bbcrssapp.datamodel.NewsItemDataModel
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.StringReader

class XmlParser {
    companion object {
        private const val TAG = "XmlParser"
    }

    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(xml: String): ArrayList<NewsItemDataModel> {
        val models = ArrayList<NewsItemDataModel>()
        val parser: XmlPullParser = XmlPullParserFactory.newInstance().newPullParser()
        parser.setInput(StringReader(xml))
        while (parser.eventType != XmlPullParser.END_DOCUMENT) {
            if (parser.eventType == XmlPullParser.START_TAG) {
                if (parser.name == "item") {
                    models.add(parseItem(parser))
                }
            }
            parser.next()
        }

        Log.d(TAG, "Finished parsing, returning ${models.size} news items")
        return models
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun parseItem(parser: XmlPullParser): NewsItemDataModel {
        val item = NewsItemDataModel()
        while (parser.next() != XmlPullParser.END_TAG) {
            Log.d(TAG, " " + parser.name + " ")
            Log.d(TAG, " " + (parser.eventType != XmlPullParser.START_TAG))
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "title" -> item.title = parseTextFromField(parser, "title")
                "pubDate" -> item.date = parseTextFromField(parser, "pubDate")
                else -> skipTag(parser)
            }
        }
        return item
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun skipTag(parser: XmlPullParser) {
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun parseTextFromField(parser: XmlPullParser, name: String): String {
        parser.require(XmlPullParser.START_TAG, null, name)
        val text = readText(parser)
        parser.require(XmlPullParser.END_TAG, null, name)
        return text
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readText(parser: XmlPullParser): String {
        var text = ""
        if (parser.next() == XmlPullParser.TEXT) {
            text = parser.text
            parser.nextTag()
        }
        return text
    }
}