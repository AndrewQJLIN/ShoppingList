package com.bignerdranch.android.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import com.bignerdranch.android.shoppinglist.data.ShopListRepositoryImpl
import com.bignerdranch.android.shoppinglist.domain.AddShopItemUseCase
import com.bignerdranch.android.shoppinglist.domain.EditShopItemUseCase
import com.bignerdranch.android.shoppinglist.domain.GetShopItemUseCase
import com.bignerdranch.android.shoppinglist.domain.ShopItem

class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl
    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    fun getShopItem(shopItemId: Int) {
        val item = getShopItemUseCase.getShopItem(shopItemId)
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parsName(inputName)
        val count = parsCount(inputCount)
        val fielsValid = validateInput(name, count)
        if (fielsValid) {
            val shopItem = ShopItem(name = name, count = count, enabled = true)
            addShopItemUseCase.addShopItem(shopItem)
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parsName(inputName)
        val count = parsCount(inputCount)
        val fielsValid = validateInput(name, count)
        if (fielsValid) {
            val shopItem = ShopItem(name = name, count = count, enabled = true)
            editShopItemUseCase.editShopItem(shopItem)
        }


    }

    private fun parsName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parsCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch
            (e: java.lang.Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            // TODO show input name
            result = false
        }

        if (count <= 0) {
            // TODO show input count
            result = false
        }
        return result
    }
}