package com.heixss.cats.ui.adapters.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.heixss.cats.model.local.BreedCard

class BreedCardDiffUtil(
    private var oldCards: List<BreedCard>,
    private var newCards: List<BreedCard>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldCards[oldItemPosition].id == newCards[newItemPosition].id

    override fun getOldListSize(): Int = oldCards.size


    override fun getNewListSize(): Int = newCards.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return (oldCards[oldItemPosition].name == newCards[newItemPosition].name &&
               oldCards[oldItemPosition].countryCode == newCards[newItemPosition].countryCode &&
               oldCards[oldItemPosition].description == newCards[newItemPosition].description &&
               oldCards[oldItemPosition].imageUrl == newCards[newItemPosition].imageUrl &&
               oldCards[oldItemPosition].temperament == newCards[newItemPosition].temperament &&
               oldCards[oldItemPosition].wikiLink == newCards[newItemPosition].wikiLink)
    }
}