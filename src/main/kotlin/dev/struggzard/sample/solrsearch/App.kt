@file:JvmName("App")
package dev.struggzard.sample.solrsearch

import dev.struggzard.sample.solrsearch.model.Component
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.post

fun main() {
    val app = Javalin.create().start(7000)

    app.routes {
        get("/component") {ctx ->
            val name = ctx.queryParam("name") as String
            ctx.queryParam("q")
            ctx.json(SolrConnector.getDocuments(name))
        }

        post("/component") { context ->
            val data = context.body<Component>()
            SolrConnector.addDocument(data)
            context.status(201)
        }
    }
}
