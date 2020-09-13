# sample-solr-search
A basic sample of the Apache Solr search integration.

## What you need
* Docker installed
* Java 11+ 

## Solr server setup
Docker approach will be used to set up a Solr server. Steps below are
for Windows 10 yet can be easily adjusted for other OS as well.

1. Download image using command: 

    ```
    docker pull solr
    ```

2. Create a directory for solr data (could be any directory):

    ```
    mkdir C:\solr_data
    ```

3. Start solr image with defined name and pre-creation sample core commands:

    ```
    docker run -v "C:\solr_data:/var/solr" -p 8983:8983 --name my_solr -t solr solr-precreate gettingstarted
    ```
4. Populate some test data for `gettingstarted` core:

    ```
    docker exec -it my_solr post -c gettingstarted example/exampledocs/manufacturers.xml
    ```
5. Check Solr admin: http://localhost:8983/

If everything goes well on Solr admin there should be `gettingstarted` sample collection with some test data.

## Start Solr

Once setup is complete subsequent Solr starts can be done with command:

```
docker container start my_solr
```

## Sample application setup

The sample application can be run either via gradle task or directly from IDE.

```
gradlew runApp
```

## Test endpoints

Two endpoints exposed for testing search integration:

### Add Document
Document can be added to search index via POST endpoint:

POST http://localhost:7000/component

```
{
    "id": "appletest",
    "name": "Apple test",
    "address": "1 Infinite Way, Cupertino CA"
}
```

### Search Document
Document search can be performed via GET endpoint:

GET http://localhost:7000/component?name=Apple*

Here query parameter `name` was used with value `Apple*` where star at the end works as wildcard.