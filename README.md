# Raptor query optimization

You are given two sets of documents:
* interesting ones
* noninteresting ones

Your goal is to build a fulltext search query, which discriminates between interesting and noninteresting documents. Your query should have at least 99% recall, should be at most 3000 characters long, and then you should optimize combination of precision and query size, more specifically you should optimize following score:

(3000 - \<query length\>) * \<precision\>

## How to submit a solution

Send us a link to a github repo with your code, approach description and result query to mlprague@ceai.io with subject "Raptor task".

## Technical details

Documents can be found in src/main/resources/interesting.txt and src/main/noninteresting.txt respectivelly. Both are lists of strings formatted as JSON.

Your query will be parsed using lucene parser (https://lucene.apache.org/core/6_2_1/queryparser/org/apache/lucene/queryparser/classic/QueryParser.html) and then both query and documents would be analyzed
using default lucene english analyzer
(https://lucene.apache.org/core/6_2_1/analyzers-common/org/apache/lucene/analysis/en/EnglishAnalyzer.html)
and matched against each other.

You can test your query by putting it into file query.txt and running either (if you have sbt
installed and ready):

`sbt run`

Or:

`packaged/bin/evaluator`
