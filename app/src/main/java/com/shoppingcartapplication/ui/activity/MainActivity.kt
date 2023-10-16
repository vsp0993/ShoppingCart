package com.shoppingcartapplication.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.shoppingcartapplication.R
import com.shoppingcartapplication.databinding.ActivityMainBinding
import com.shoppingcartapplication.ui.adapter.ClickType
import com.shoppingcartapplication.ui.adapter.ShoppingCardAdapter
import com.shoppingcartapplication.ui.model.ShoppingCartDetails

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ShoppingCardAdapter
    private var shoppingCartList: List<ShoppingCartDetails>? = listOf()
    private var promoValue1: Int = 0
    private var promoValue2: Int = 0
    private var promoValue3: Int = 0
    private val taxAmount = 192
    private var cartPrice: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        shoppingCartList = setUpItemList()
        initView()
        setUpAdapterClickEvent()
    }

    private fun initView() = with(binding) {
        cartPrice = setUpItemList().sumOf { it.itemPrice }
        tvPrice.text = "₹ $cartPrice"

        tvTotal.text = "₹ " + (cartPrice + taxAmount).toString()
    }

    private fun setUpAdapterClickEvent() = with(binding) {
        adapter = ShoppingCardAdapter()
        binding.cartRecyclerView.adapter = adapter
        adapter.setItem(shoppingCartList!!)


        adapter.listener = { position, shoppingDetails, promoCode, finalPrice, clickEvent ->
            when (clickEvent) {
                ClickType.DELETE -> {
                    showDeleteDialog(position, shoppingDetails, finalPrice)
                }

                ClickType.PROMO_APPLY -> {
                    cartPrice = adapter.getItemList().sumOf { it.itemPrice }
                    tvPrice.text = "₹ $cartPrice"


                    when (shoppingDetails.promoCode) {
                        "Promo1" -> {
                            promoValue1 = finalPrice
                        }

                        "Promo2" -> {
                            promoValue2 = finalPrice
                        }

                        "Promo3" -> {
                            promoValue3 = finalPrice
                        }
                    }

                    tvCouponDiscount.text = "₹ ${(promoValue1 + promoValue2 + promoValue3)}"

                    tvTotal.text =
                        "₹ ${(cartPrice + taxAmount - (promoValue1 + promoValue2 + promoValue3))}"
                }

                ClickType.PROMO_REMOVE -> {
                    when (shoppingDetails.promoCode) {
                        "Promo1" -> {
                            promoValue1 = finalPrice
                        }

                        "Promo2" -> {
                            promoValue2 = finalPrice
                        }

                        "Promo3" -> {
                            promoValue3 = finalPrice
                        }
                    }

                    tvCouponDiscount.text = "₹ ${(promoValue1 + promoValue2 + promoValue3)}"
                    tvTotal.text =
                        "₹ ${(cartPrice + taxAmount - (promoValue1 + promoValue2 + promoValue3))}"
                }

                ClickType.PROMO_CODE -> {
                }

                ClickType.VALID_PROMO -> {
                    showErrorDialog()
                }

                else -> {}
            }

        }
    }

    private fun setUpItemList(): List<ShoppingCartDetails> {

        // Adding data into the model list
        return listOf(
            ShoppingCartDetails(
                id = 1,
                itemName = "Burberry T-shirt",
                itemImage = R.drawable.ic_dummy_item_image,
                itemPrice = 2500,
                itemSize = "Medium",
                itemQuantity = 2,
                sellerName = "Blueberry store",
                isVerifyBuyOk = true,
                estimateDate = getString(R.string.dummy_estimated_date),
                promoCode = "Promo1",
                promoCodeValue = 0

            ),

            ShoppingCartDetails(
                id = 2,
                itemName = "Burberry T-shirt",
                itemImage = R.drawable.ic_dummy_item_image,
                itemPrice = 500,
                itemSize = "Medium",
                itemQuantity = 4,
                sellerName = "Seller 2 name 2",
                isVerifyBuyOk = true,
                estimateDate = getString(R.string.dummy_estimated_date),
                promoCode = "Promo2",
                promoCodeValue = 0
            ),

            ShoppingCartDetails(
                id = 3,
                itemName = "Burberry T-shirt",
                itemImage = R.drawable.ic_dummy_item_image,
                itemPrice = 2000,
                itemSize = "Medium",
                itemQuantity = 2,
                sellerName = "Seller 2 name 2",
                isVerifyBuyOk = true,
                estimateDate = getString(R.string.dummy_estimated_date),
                promoCode = "Promo3",
                promoCodeValue = 0
            )

        )
    }

    // dialog to show user confirmation for deleting item from the list
    private fun showDeleteDialog(
        position: Int, shoppingDetails: ShoppingCartDetails, finalPrice: Int
    ) = with(binding) {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle(getString(R.string.alert))
        builder.setMessage(getString(R.string.are_you_sure_you_want_to_remove_this_item))

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            adapter.removeItem(position)

            if (adapter.getItemList().isEmpty()) {
                tvEmptyMessage.isVisible = true
                buttonCheckOut.isGone = true
                scrollView.isGone = true
            }

            cartPrice = adapter.getItemList().sumOf { it.itemPrice }
            tvPrice.text = "₹ $cartPrice"


            when (shoppingDetails.promoCode) {
                "Promo1" -> {
                    promoValue1 = finalPrice
                }

                "Promo2" -> {
                    promoValue2 = finalPrice
                }

                "Promo3" -> {
                    promoValue3 = finalPrice
                }
            }

            tvCouponDiscount.text = "₹ ${(promoValue1 + promoValue2 + promoValue3)}"

            tvTotal.text =
                "₹ ${(cartPrice + taxAmount - (promoValue1 + promoValue2 + promoValue3))}"

        }
        builder.setNegativeButton(android.R.string.no) { dialog, which ->
        }
        builder.show()
    }

    private fun showErrorDialog() = with(binding) {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle(getString(R.string.invalid_promo_code))
        builder.setMessage(getString(R.string.the_entered_promo_code_is_not_valid_please_try_again))

        builder.setPositiveButton(android.R.string.ok) { dialog, which ->

        }
        builder.show()
    }
}