# Top 5 of codevelopments

1. GoogleCloudPlatform/microservices-demo

2. dotnet-architecture/eShopOnContainers
3. instana/robot-shop
4. microservices-demo/microservices-demo (is built as follows: each service is a different github project. Neat.)

5. sczyh30/vertx-blueprint-microservice
6. thangchung/ShoppingCartDemo



| codev 1(a)                 | codev 2                | codev 3(c)            | Codev4(d) | codev 5(b)          | codev 6                    |
| -------------------------- | ---------------------- | --------------------- | ------ | ------------------- | -------------------------- |
| Catalog - shipping         | catalog - order        | cart - User           | -      | catalog order       | checkout - order           |
| order - shipping           | cart - order           | cart - payment        | -      | catalog - user      | catalog - checkout         |
| Catalog - order            | cart - catalog         | catalog - cart        | -      | user - order        | catalog - order            |
| order - shipping - product | cart - catalog - order | catalog - user        | -      | catalog - store     | audit - checkout           |
|                            | order - user           | catalog - cart - user | -      | catalog - inventory | catalog - checkout - order |

(a) checkout = order management

(b) product = catalog

(c) user handle orders management

(d) Codev metric could not be measured as this project is split into various github repositories that share no common history.
