1. GoogleCloudPlatform/microservices-demo
2. dotnet-architecture/eShopOnContainers
3. instana/robot-shop
4. microservices-demo/microservices-demo (is built as follows: each service is a different github project. Neat.)
5. sczyh30/vertx-blueprint-microservice
6. thangchung/ShoppingCartDemo

# Ad service

|      | Get Ads | Get Random Ads | Get Ads by category | Cre. Campaign | Get Camp | Upd. Camp. | Del. Camp | Get. Camp. By User |      |
| ---- | ------- | -------------- | ------------------- | ------------- | -------- | ---------- | --------- | ------------------ | ---- |
| 1    | X       | X              | X                   |               |          |            |           |                    |      |
| 2    |         |                |                     | X             | X        | X          | X         | X                  |      |
|      |         |                |                     |               |          |            |           |                    |      |

2. event bus RABBITMQ. Does the localization counts as a feature?



# Audit service

|      | Get an audit | Add an audit to a method of a service |
| ---- | ------------ | ------------------------------------- |
| 6    | X            | X                                     |
|      |              |                                       |
|      |              |                                       |



# Cart Management

|      | Add Item in cart | Empty Cart | Get Cart | Ping | Create basket | Checkout  basket | Update Basket | Delete Basket | Rename basket (e.g. at login) | Remove item | Update item                          | Add shipping by id |
| ---- | ---------------- | ---------- | -------- | ---- | ------------- | ---------------- | ------------- | ------------- | ----------------------------- | ----------- | ------------------------------------ | ------------------ |
| 1    | X                | X          | X        | X    |               |                  |               |               |                               |             |                                      |                    |
| 2    |                  |            | X        |      | X             | X                | X             |               |                               |             |                                      |                    |
| 3    | X (with qty)     |            | X        |      | X             |                  | X             | X             | X                             | X           | X (update qty only. remove when = 0) | X                  |
| 4    | X                |            | X        |      |               |                  |               | X             |                               | X           | X                                    |                    |
| 5    | X (a)            |            | X        |      | X             |                  |               |               |                               | X (a)       |                                      |                    |

3. Use `get` as `http` verb to `update` or `rename` basket. 

4. (a) https://github.com/jacobkrueger/SPLC2020-Microservices-Challenge/blob/5949c6a720cd2c324d2bf74056ab846383e43021/shopping-cart-microservice/src/main/java/io/vertx/blueprint/microservice/cart/ShoppingCart.java#L57

# Checkout service

|      | Sum  | Checkout |      |
| ---- | ---- | -------- | ---- |
| 1    | X    |          |      |
| 5    |      | X        |      |
| 6    |      | X        |      |

# Currency Service

|      | Get supported currencies | Convert between currencies |
| ---- | ------------------------ | -------------------------- |
| 1    | X                        | X                          |
|      |                          |                            |
|      |                          |                            |

# Email Service

|      | Send Order Confirmation |      |
| ---- | ----------------------- | ---- |
| 1    | X                       |      |
|      |                         |      |
|      |                         |      |

# Inventory Service

|      | Get nb in stock | Inc. nb in stock | Dec. nb in stock |      |      |      |      |      |      |
| ---- | --------------- | ---------------- | ---------------- | ---- | ---- | ---- | ---- | ---- | ---- |
| 5    | X               | X                | X                |      |      |      |      |      |      |

5. is an inventory of vehicules.



# Order service

|      | Create order    | Get order by id | Cancel order | Get orders of a user | Get all orders | Modify order status | Modify saga (transaction) status |
| ---- | --------------- | --------------- | ------------ | -------------------- | -------------- | ------------------- | -------------------------------- |
| 2    | X (from basket) | X               | X            | X                    |                |                     |                                  |
| 4    | X               |                 |              | X                    |                |                     |                                  |
| 5    | X               | X               |              | X                    |                |                     |                                  |
| 6    | X               | X               |              |                      | X              | X                   | X                                |
|      |                 |                 |              |                      |                |                     |                                  |
|      |                 |                 |              |                      |                |                     |                                  |
|      |                 |                 |              |                      |                |                     |                                  |

# Payment Service

|      | Charge | Mastercard | Visa | Pay  | Authorize | Add payment | Get payment record | Compensate money to customer |
| ---- | ------ | ---------- | ---- | ---- | --------- | ----------- | ------------------ | ---------------------------- |
| 1    | X      | X          | X    |      |           |             |                    |                              |
| 2    |        |            |      |      |           |             |                    |                              |
| 3    |        |            |      | X    |           |             |                    |                              |
| 4    |        |            |      |      | X         |             |                    |                              |
| 5    |        |            |      |      |           | X           | X                  |                              |
| 6    |        |            |      |      |           | X           | X                  | X                            |

1. What about 'handling visa' ? This is a feature too !

2. RabbitMQ communication. Hard to see the feature here https://github.com/jacobkrueger/SPLC2020-Microservices-Challenge/tree/dotnet-architecture/eShopOnContainers/src/Services/Payment/Payment.API
3. Use RabbitMQ. Internally, create an order, add it to user's history, delete the cart

# Product Catalog Service

|      | List Products | Get Product | Search Products | Get products by categories | Get all categories | Get size of an item | Get tags | Add product | Get product price | Get products by page | Delete product | Delete all products | Inc. / Dec. Qty |      |
| ---- | ------------- | ----------- | --------------- | -------------------------- | ------------------ | ------------------- | -------- | ----------- | ----------------- | -------------------- | -------------- | ------------------- | --------------- | ---- |
| 1    | x             | X           | X               |                            |                    |                     |          |             |                   |                      |                |                     |                 |      |
| 2    | X (get items) | X           |                 |                            |                    |                     |          |             |                   |                      |                |                     |                 |      |
| 3    | X             | X           | X               | X                          | X                  |                     |          |             |                   |                      |                |                     |                 |      |
| 4    | X             | X           |                 |                            |                    | X                   | X        |             |                   |                      |                |                     |                 |      |
| 5    | X             | X           |                 |                            |                    |                     |          | X           | X                 | X                    | X              | X                   |                 |      |
| 6    | X             | X           |                 |                            |                    |                     |          |             | X                 |                      |                |                     | X               |      |

# Rating service

|      | Get rating | Rate a product |      |      |      |
| ---- | ---------- | -------------- | ---- | ---- | ---- |
| 3    | X          | X              |      |      |      |
|      |            |                |      |      |      |
|      |            |                |      |      |      |

# Recommendation Service

|      | List Recommendations                                         |      |
| ---- | ------------------------------------------------------------ | ---- |
| 1    | X                                                            |      |
| 5    | NOT IMPLEMENTED (https://github.com/jacobkrueger/SPLC2020-Microservices-Challenge/tree/5949c6a720cd2c324d2bf74056ab846383e43021/recommendation-microservice) |      |
|      |                                                              |      |

# Shipping Service



|      | Get Quote | Ship Order | Get shipment | Get shipment by id | Create shipment |      |      |
| ---- | --------- | ---------- | ------------ | ------------------ | --------------- | ---- | ---- |
| 1    | X         | X          |              |                    |                 |      |      |
| 4    |           |            | X            | X                  | X               |      |      |
|      |           |            |              |                    |                 |      |      |



# Store service

|      | Add online store | Delete online store | Get online store |
| ---- | ---------------- | ------------------- | ---------------- |
| 5    | X                | X                   | X                |
|      |                  |                     |                  |
|      |                  |                     |                  |



# User service

|        | Get anonymous user | Get a user | Check user exists | Update user | Get all users | Login | Register a user | Create an order | Get order history | Delete a customer | Get customer's cards | Get customer's address | Get Put card | Get Del. a card | Get Put address | Get Del. an address |
| ------ | ------------------ | ---------- | ----------------- | ----------- | ------------- | ----- | --------------- | --------------- | ----------------- | ----------------- | -------------------- | ---------------------- | ------------ | --------------- | --------------- | ------------------- |
| 1  |                    |            |                   |             |               |       |                 |                 |                   |                   |                      |                        |              |                 |                 |                     |
| 2      |                    | X          |                   |             |               |       |                 |                 |                   |                   |                      |                        |              |                 |                 |                     |
| 3      | X                  |            | X                 |             | X             | X     | X               | X               | X                 |                   |                      |                        |              |                 |                 |                     |
| 4      |                    | X          |                   |             | X             | X     | X               |                 |                   | X                 | X                    | X                      | X            | X               | X               | X                   |
| 5      |                    | X          |                   | X           | X             |       | X               |                 |                   | X                 |                      |                        |              |                 |                 |                     |
| 6      |                    | X          |                   |             | X             |       | X               |                 |                   |                   |                      |                        |              |                 |                 |                     |
|        |                    |            |                   |             |               |       |                 |                 |                   |                   |                      |                        |              |                 |                 |                     |

