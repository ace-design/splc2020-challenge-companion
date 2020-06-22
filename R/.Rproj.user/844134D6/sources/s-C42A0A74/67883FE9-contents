source('./_common.R')
source('./multi_plot.R')

dataset1 <- read.csv('../csv/codev-GoogleCloudPlatformMicroservicesDemo.csv')
dataset2 <- read.csv('../csv/codev-DotnetArchitectureEShopOnContainers.csv')
dataset3 <- read.csv('../csv/codev-InstanaRobotShop.csv')
dataset5 <- read.csv('../csv/codev-Sczyh30VertxBlueprintMicroservice.csv')
dataset6 <- read.csv('../csv/codev-ThangchungShoppingCartDemo.csv')


# dataset1 <- read.csv('./codev-GoogleCloudPlatformMicroservicesDemo.csv')
# currencyservice,paymentservice,adservice,checkoutservice,shippingservice,productcatalogservice,recommendationservice,emailservice,
codev1 <- list(
  cart    = dataset1[ dataset1$cartservice == 'Y',]$CommitID,               # 100
  currency    = dataset1[ dataset1$currencyservice == 'Y',]$CommitID,               # 100
  checkout    = dataset1[ dataset1$checkoutservice == 'Y',]$CommitID,               # 100
  shipping    = dataset1[ dataset1$shippingservice == 'Y',]$CommitID,               # 100
  productcatalog    = dataset1[ dataset1$productcatalogservice == 'Y',]$CommitID,               # 100
  recommendation    = dataset1[ dataset1$recommendationservice == 'Y',]$CommitID,               # 100
  email    = dataset1[ dataset1$emailservice == 'Y',]$CommitID,               # 100
  payment = dataset1[ dataset1$paymentservice == 'Y',]$CommitID,      # 010
  ad   = dataset1[ dataset1$adservice == 'Y',]$CommitID   # 001
)
codev1_matrix <- make_comb_mat(codev1, mode='intersect', min_set_size=5, top_n_sets = 5)



#dataset2 <- read.csv('./codev-DotnetArchitectureEShopOnContainers.csv')
#CommitID,Payment,Ordering,Webhooks,Basket,Identity,Catalog,Marketing,
codev2 <- list(
  Payment    = dataset2[ dataset2$Payment == 'Y',]$CommitID,
  Ordering    = dataset2[ dataset2$Ordering == 'Y',]$CommitID,
  Webhooks    = dataset2[ dataset2$Webhooks == 'Y',]$CommitID,
  Basket    = dataset2[ dataset2$Basket == 'Y',]$CommitID,
  Identity    = dataset2[ dataset2$Identity == 'Y',]$CommitID,
  Catalog    = dataset2[ dataset2$Catalog == 'Y',]$CommitID,
  Marketing    = dataset2[ dataset2$Marketing == 'Y',]$CommitID
)
codev2_matrix <- make_comb_mat(codev2, mode='intersect', min_set_size=5, top_n_sets = 5)


#dataset3 <- read.csv('./codev-InstanaRobotShop.csv')
#CommitID,cart,catalogue,payment,ratings,user,shipping,
codev3 <- list(
  cart    = dataset3[ dataset3$cart == 'Y',]$CommitID,
  catalogue    = dataset3[ dataset3$catalogue == 'Y',]$CommitID,
  payment    = dataset3[ dataset3$payment == 'Y',]$CommitID,
  ratings    = dataset3[ dataset3$ratings == 'Y',]$CommitID,
  user    = dataset3[ dataset3$user == 'Y',]$CommitID,
  shipping    = dataset3[ dataset3$shipping == 'Y',]$CommitID
)
#codev2_matrix <- make_comb_mat(codev2, mode='intersect', min_set_size=5, top_n_sets = 5)
codev3_matrix <- make_comb_mat(codev3, mode='intersect')




#dataset5 <- read.csv('./codev-Sczyh30VertxBlueprintMicroservice.csv')
#CommitID,shoppingCart,users,consul,order,product,store,inventory,recommendation,payment,account,
codev5 <- list(
  shoppingCart    = dataset5[ dataset5$shoppingCart == 'Y',]$CommitID,
  users    = dataset5[ dataset5$users == 'Y',]$CommitID,
  consul    = dataset5[ dataset5$consul == 'Y',]$CommitID,
  order    = dataset5[ dataset5$order == 'Y',]$CommitID,
  product    = dataset5[ dataset5$product == 'Y',]$CommitID,
  store    = dataset5[ dataset5$store == 'Y',]$CommitID,
  inventory    = dataset5[ dataset5$inventory == 'Y',]$CommitID,
  recommendation    = dataset5[ dataset5$recommendation == 'Y',]$CommitID,
  payment    = dataset5[ dataset5$payment == 'Y',]$CommitID,
  account    = dataset5[ dataset5$account == 'Y',]$CommitID
)
codev5_matrix <- make_comb_mat(codev5, mode='intersect', min_set_size=5, top_n_sets = 5)


#dataset6 <- read.csv('./codev-ThangchungShoppingCartDemo.csv')
#CommitID,IdentityServer,CustomerService,PaymentService,CheckoutProcess,CatalogService,OrderService,AuditService,
codev6 <- list(
  IdentityServer    = dataset6[ dataset6$IdentityServer == 'Y',]$CommitID,
  CustomerService    = dataset6[ dataset6$CustomerService == 'Y',]$CommitID,
  PaymentService    = dataset6[ dataset6$PaymentService == 'Y',]$CommitID,
  CheckoutProcess    = dataset6[ dataset6$CheckoutProcess == 'Y',]$CommitID,
  CatalogService    = dataset6[ dataset6$CatalogService == 'Y',]$CommitID,
  OrderService    = dataset6[ dataset6$OrderService == 'Y',]$CommitID,
  AuditService    = dataset6[ dataset6$AuditService == 'Y',]$CommitID
)
codev6_matrix <- make_comb_mat(codev6, mode='intersect', min_set_size=5, top_n_sets = 5)

pdf("codev1.pdf", width=6,height = 3)
UpSet(codev1_matrix, 
      set_order = order(set_size(codev1_matrix)),
      row_title = "|codev|",
      right_annotation = upset_right_annotation(codev1_matrix, ylim = c(0, 35)),
      top_annotation = upset_top_annotation(codev1_matrix, ylim=c(0,35)))
dev.off()


pdf("codev2.pdf", width=8,height = 4)
UpSet(codev2_matrix, 
      set_order = order(set_size(codev2_matrix)),
      row_title = "|codev|",
      right_annotation = upset_right_annotation(codev2_matrix, ylim = c(0, 350)),
      top_annotation = upset_top_annotation(codev2_matrix, ylim=c(0,400)))
dev.off()

pdf("codev3.pdf", width=6,height = 3)
UpSet(codev3_matrix, 
      set_order = order(set_size(codev3_matrix)),
      row_title = "|codev|",
      right_annotation = upset_right_annotation(codev3_matrix, ylim = c(0, 15)),
      top_annotation = upset_top_annotation(codev3_matrix, ylim=c(0,13)))
dev.off()

pdf("codev5.pdf", width=6,height = 3)
UpSet(codev5_matrix, 
      set_order = order(set_size(codev5_matrix)),
      row_title = "|codev|",
      right_annotation = upset_right_annotation(codev5_matrix, ylim = c(0, 20)),
      top_annotation = upset_top_annotation(codev5_matrix, ylim=c(0,20)))
dev.off()

pdf("codev6.pdf", width=6,height = 3)
UpSet(codev6_matrix, 
      set_order = order(set_size(codev6_matrix)),
      row_title = "|codev|",
      right_annotation = upset_right_annotation(codev6_matrix, ylim = c(0, 13)),
      top_annotation = upset_top_annotation(codev6_matrix, ylim=c(0,13)))
dev.off()