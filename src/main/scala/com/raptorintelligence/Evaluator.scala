package com.raptorintelligence

import org.apache.lucene.analysis.en.EnglishAnalyzer
import org.apache.lucene.index.memory.MemoryIndex
import org.apache.lucene.queryparser.classic.QueryParser
import org.apache.lucene.search.Query
import spray.json._
import DefaultJsonProtocol._

import scala.io.Source

object Evaluator {
  val analyzer = new EnglishAnalyzer()

  def matchText(text: String, query: Query): Boolean = {
    val index = new MemoryIndex()
    index.addField("text", text, analyzer)
    val score = index.search(query)
    score > 0.0f
  }

  def parseQuery(queryStr: String): Query = {
    val parser = new QueryParser("text", analyzer)
    parser.parse(queryStr)
  }

  def main(args: Array[String]): Unit = {
    val queryStr = Source.fromFile("query.txt").mkString
    val query = parseQuery(queryStr)
    println(s"query length: ${queryStr.size}")

    var trueMatches = 0
    var falsePositives = 0
    var falseNegatives = 0

    val interesting = Source.fromInputStream(getClass.getResourceAsStream("/interesting.txt")).mkString.parseJson.convertTo[List[String]]
    interesting.foreach(document => {
      if (matchText(document, query)) {
        trueMatches += 1
      } else {
        falseNegatives += 1
      }
    })

    val nonInteresting = Source.fromInputStream(getClass.getResourceAsStream("/noninteresting.txt")).mkString.parseJson.convertTo[List[String]]
    nonInteresting.foreach(document => {
      if (matchText(document, query)) {
        falsePositives += 1
      } else {
      }
    })

    val recall = 1.0 * trueMatches / (trueMatches + falseNegatives)
    val precision = 1.0 * trueMatches / (trueMatches + falsePositives)

    println(s"recall ${recall}")
    println(s"precistion ${precision}")

    val score = if (recall < 0.99 || (queryStr.length > 3000)) {
      0
    } else {
      (3000 - queryStr.length) * precision
    }

    println(s"score $score")
  }
}
