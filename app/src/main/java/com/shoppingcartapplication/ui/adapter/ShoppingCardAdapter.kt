package com.shoppingcartapplication.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shoppingcartapplication.R
import com.shoppingcartapplication.databinding.ItemShoppingCartBinding
import com.shoppingcartapplication.ui.model.ShoppingCartDetails

enum class ClickType() {
    DELETE,
    PROMO_APPLY,
    PROMO_REMOVE,
    PROMO_CODE,
    VALID_PROMO
}

class ShoppingCardAdapter :
    ListAdapter<ShoppingCartDetails, ShoppingCardAdapter.ShoppingCartVH>(callback) {
    private val items = ArrayList<ShoppingCartDetails>()
    internal var listener: ((position: Int, shoppingDetails: ShoppingCartDetails, promoCode: String, finalPrice: Int, clickEvent: ClickType?) -> Unit)? =
        null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemShoppingCartBinding.inflate(inflater, parent, false)
        return ShoppingCartVH(binding, listener)
    }

    override fun onBindViewHolder(holder: ShoppingCartVH, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    companion object {
        val callback = object : DiffUtil.ItemCallback<ShoppingCartDetails>() {
            override fun areItemsTheSame(
                oldItem: ShoppingCartDetails,
                newItem: ShoppingCartDetails
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ShoppingCartDetails,
                newItem: ShoppingCartDetails
            ) = oldItem == newItem

        }
    }

    inner class ShoppingCartVH(
        private val binding: ItemShoppingCartBinding,
        private val listener: ((position: Int, shoppingDetails: ShoppingCartDetails, promoCode: String, finalPrice: Int, clickEvent: ClickType) -> Unit)? = null
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ShoppingCartDetails) = with(binding) {
            tvItemName.text = model.itemName
            tvItemPrice.text = "₹ " + model.itemPrice.toString()
            tvItemSize.text = model.itemSize
            tvItemQty.text = model.itemQuantity.toString()
            tvSellerName.text = model.sellerName

            if (model.isVerifyBuyOk) {
                tvBuyokVerified.isVisible = true
            } else {
                tvBuyokVerified.isGone = true
            }

            tvPromoCode.setOnClickListener {
                clPromoCode.isVisible = true
                tvClPromoCode.isVisible = true
                tvPromoCode.isGone = true
                listener?.invoke(adapterPosition, model, "", 0, ClickType.PROMO_CODE)
            }


            tvApply.setOnClickListener {
                if (edtPromoCode.text!!.isNotEmpty()) {
                    var value = 0
                    when (edtPromoCode.text.toString()) {
                        "Promo1" -> {
                            value = 50
                            edtPromoCode.setText("Promo1 ($value% discount)")
                            tvDiscountPercent.text = "($value% off)"
                            tvOfferedPrice.isVisible = true
                            tvDiscountPercent.isVisible = true
                            tvRemove.isVisible = true
                            tvApply.isGone = true
                            tvOfferedPrice.text = model.itemPrice.toString()
                            tvOfferedPrice.paintFlags =
                                tvOfferedPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                            clPromoCode.setBackgroundResource(R.drawable.rounded_focused)
                            tvClPromoCode.setTextColor(
                                ContextCompat.getColor(
                                    binding.root.context, R.color.focused_box_color
                                )
                            )

                            val percentage = (value.toDouble() / 100.0)

                            var finalPrice = model.itemPrice * percentage

                            tvItemPrice.text = "₹ ${model.itemPrice - finalPrice.toInt()}"

                            listener?.invoke(
                                adapterPosition,
                                model,
                                edtPromoCode.text.toString(),
                                finalPrice.toInt(),
                                ClickType.PROMO_APPLY
                            )
                        }

                        "Promo2" -> {
                            value = 10
                            edtPromoCode.setText("Promo2 ($value% discount)")
                            tvDiscountPercent.text = "($value% off)"
                            tvOfferedPrice.isVisible = true
                            tvDiscountPercent.isVisible = true
                            tvRemove.isVisible = true
                            tvApply.isGone = true
                            tvOfferedPrice.text = model.itemPrice.toString()
                            tvOfferedPrice.paintFlags =
                                tvOfferedPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                            clPromoCode.setBackgroundResource(R.drawable.rounded_focused)
                            tvClPromoCode.setTextColor(
                                ContextCompat.getColor(
                                    binding.root.context, R.color.focused_box_color
                                )
                            )

                            val percentage = (value.toDouble() / 100.0)

                            var finalPrice = model.itemPrice * percentage

                            tvItemPrice.text = "₹ ${model.itemPrice - finalPrice.toInt()}"

                            listener?.invoke(
                                adapterPosition,
                                model,
                                edtPromoCode.text.toString(),
                                finalPrice.toInt(),
                                ClickType.PROMO_APPLY
                            )
                        }

                        "Promo3" -> {
                            value = 30
                            edtPromoCode.setText("Promo3 ($value% discount)")
                            tvDiscountPercent.text = "($value% off)"
                            tvOfferedPrice.isVisible = true
                            tvDiscountPercent.isVisible = true
                            tvRemove.isVisible = true
                            tvApply.isGone = true
                            tvOfferedPrice.text = model.itemPrice.toString()
                            tvOfferedPrice.paintFlags =
                                tvOfferedPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                            clPromoCode.setBackgroundResource(R.drawable.rounded_focused)
                            tvClPromoCode.setTextColor(
                                ContextCompat.getColor(
                                    binding.root.context, R.color.focused_box_color
                                )
                            )

                            val percentage = (value.toDouble() / 100.0)

                            var finalPrice = model.itemPrice * percentage

                            tvItemPrice.text = "₹ ${model.itemPrice - finalPrice.toInt()}"

                            listener?.invoke(
                                adapterPosition,
                                model,
                                edtPromoCode.text.toString(),
                                finalPrice.toInt(),
                                ClickType.PROMO_APPLY
                            )
                        }

                        else -> {
                            listener?.invoke(adapterPosition, model, "", 0, ClickType.VALID_PROMO)
                            edtPromoCode.setText("")
                        }
                    }

                }


            }

            tvRemove.setOnClickListener {
                listener?.invoke(adapterPosition, model, "", 0, ClickType.PROMO_REMOVE)
                tvOfferedPrice.isGone = true
                tvDiscountPercent.isGone = true
                tvRemove.isGone = true
                tvApply.isVisible = true
                edtPromoCode.setText("")
                tvItemPrice.text = "₹ ${model.itemPrice}"
            }

            ivDelete.setOnClickListener {
                listener?.invoke(adapterPosition, model, "", 0, ClickType.DELETE)
            }

        }

    }

    fun setItem(list: List<ShoppingCartDetails>) {
        this.items.clear()
        this.items.addAll(list)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getItemList(): List<ShoppingCartDetails> {
        return items
    }
}