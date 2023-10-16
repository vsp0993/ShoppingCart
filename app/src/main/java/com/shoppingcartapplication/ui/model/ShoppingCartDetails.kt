package com.shoppingcartapplication.ui.model

data class ShoppingCartDetails(
    val id: Int,
    var itemImage:Int,
    var itemName:String,
    var itemPrice:Int,
    var itemSize:String,
    var itemQuantity:Int,
    var sellerName:String,
    var isVerifyBuyOk:Boolean,
    var estimateDate:String,
    var promoCode:String,
    var promoCodeValue:Int
)
