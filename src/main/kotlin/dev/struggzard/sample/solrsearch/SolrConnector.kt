package dev.struggzard.sample.solrsearch

import dev.struggzard.sample.solrsearch.model.Component
import org.apache.solr.client.solrj.SolrQuery
import org.apache.solr.client.solrj.impl.HttpSolrClient
import org.apache.solr.client.solrj.impl.XMLResponseParser
import org.apache.solr.client.solrj.response.QueryResponse
import org.apache.solr.common.SolrInputDocument


object SolrConnector {

    private val client = initClient()

    private fun initClient(): HttpSolrClient {
        val urlString = "http://localhost:8983/solr/gettingstarted"
        val solr = HttpSolrClient.Builder(urlString).build()
        solr.parser = XMLResponseParser()
        return solr
    }

    fun getDocuments(name: String): List<Component> {
        val query = SolrQuery()
        query.set("q", "compName_s:${name}")
        val response: QueryResponse = client.query(query)

        val components: MutableList<Component> = arrayListOf()
        for (result in response.results) {
            components.add(
                Component(
                    result.getFirstValue("id") as String,
                    result.getFirstValue("compName_s") as String,
                    result.getFirstValue("address_s") as String
                )
            )
        }
        return components
    }

    fun addDocument(component: Component) {
        val document = SolrInputDocument()
        document.addField("id", component.id)
        document.addField("compName_s", component.name)
        document.addField("address_s", component.address)
        client.add(document)
        client.commit()
    }
}