

# 1. Google Cloud

| From Service   | To Service     | Techno                                |
| -------------- | -------------- | ------------------------------------- |
| Checkout       | Email          | gRPC (send mail)                      |
| Checkout       | Payment        | gRPC (charge card)                    |
| Checkout       | Shipping       | gRPC (ship order, Get shipping quote) |
| Checkout       | ProductCatalog | gRPC (GetProduct)                     |
| Checkout       | Cart           | gRPC (get/empty usercart)             |
| Checkout       | Currency       | gRPC                                  |
| Recommendation | ProductCatalog | gRPC (ListProducts)                   |
| Cart           |                | CACHE REDIS                           |
| Frontend       | Ad             | gRPC                                  |
| Frontend       | Checkout       | gRPC                                  |
| Frontend       | Shipping       | gRPC                                  |
| Frontend       | Currency       | gRPC                                  |
| Frontend       | ProductCatalog | gRPC                                  |
| Frontend       | Recommendation | gRPC                                  |
| Frontend       | Cart           | gRPC                                  |

# 2. dotnet-architecture/eShopOnContainers


Most communications between microservices are decoupled using the EventBus and the "pub/sub" pattern.
However, the communications between the custom aggregators and the internal microservices is currently implemented with gRPC
as said [here](https://github.com/dotnet-architecture/eShopOnContainers/wiki/Architecture#grpc).

| Basket       | Catalog       | 
| Basket       | Order | 
| Basket       | Identity | 
| Order       | Identity | 
| Marketing       | Location | 
| Marketing       | Identity | 


Extracted from [here](https://github.com/dotnet-architecture/eShopOnContainers/tree/9ec4837abe3305563e633ee1f7d7539f0e977dc5/src/Tests/Services/Application.FunctionalTests/Services)

# 3. Instana

|          |           |                                                              |
| -------- | --------- | ------------------------------------------------------------ |
| Cart     |           | REDIS, Manage (get, delete, update, rename, create, update qty) Cart and add shipping (https://github.com/jacobkrueger/SPLC2020-Microservices-Challenge/blob/2bd5564638483967987f20b3a1a1ed945a6a6be2/cart/server.js#L71) |
| Cart     | Catalogue | HTTP  get product details (https://github.com/jacobkrueger/SPLC2020-Microservices-Challenge/blob/2bd5564638483967987f20b3a1a1ed945a6a6be2/cart/server.js#L348) |
| Catalog  |           | MongoDB                                                      |
| Payment  | User      | Check user exists https://github.com/jacobkrueger/SPLC2020-Microservices-Challenge/blob/2bd5564638483967987f20b3a1a1ed945a6a6be2/payment/payment.py#L69 |
| Payment  | Order     | RabbitMQ, queue the order https://github.com/jacobkrueger/SPLC2020-Microservices-Challenge/blob/2bd5564638483967987f20b3a1a1ed945a6a6be2/payment/payment.py#L106 |
| Payment  | Order     | HTTP, add order to history https://github.com/jacobkrueger/SPLC2020-Microservices-Challenge/blob/2bd5564638483967987f20b3a1a1ed945a6a6be2/payment/payment.py#L111 |
| Payment  | Cart      | HTTP delete cart https://github.com/jacobkrueger/SPLC2020-Microservices-Challenge/blob/2bd5564638483967987f20b3a1a1ed945a6a6be2/payment/payment.py#L121 |
| Rating   | Catalogue | HTTP (curl) check item exists https://github.com/jacobkrueger/SPLC2020-Microservices-Challenge/blob/2bd5564638483967987f20b3a1a1ed945a6a6be2/ratings/html/api.php#L144 |
| Shipping | Cart      | HTTP https://github.com/jacobkrueger/SPLC2020-Microservices-Challenge/blob/2bd5564638483967987f20b3a1a1ed945a6a6be2/shipping/src/main/java/org/steveww/spark/Main.java#L227 |
| User     |           | REDIs anonymous user                                         |
| User     | Mongo     | Manage user, user's profile, user's order history,           |




# 4. Microservices-demo/microservices-demo

-> All services communicate using `REST `
-> Shipping send orders to a `queue` for sending them out

| Cart |           | Catalogue        |
| User |           |Cart        |
| User |           |Order        |
| Order |           |Payment        |
| Order |           |Shippinh        |


# 5. sczyh30/vertx-blueprint-microservice

| Inventory |           | Redis        |
| User      |           | MySQL / Rest |
| Product   |           | MySQL/ Rest  |
| Store     |           | Mongo/ Rest  |
| Order     |           | MySQL        |
| Inventory |           | Redis / Rest |
| Product   | Inventory |              |

=> RPC on eventbus + bidirectionnal Rest



# 6. thangchung

| From     | To           | because                                          |
| -------- | ------------ | ------------------------------------------------ |
| Checkout | Catalog      | remove product in catalog                        |
| Checkout | Payment      | make payment                                     |
| Payment  | Notification | payment completed                                |
| Payment  | Email        | payment completed                                |
| Checkout | Order        | Checkout, order status ; compensate order status |


Everything is done via Saga pattern (a service registry that acts as an orchestrator here (not a choregraphy).)

