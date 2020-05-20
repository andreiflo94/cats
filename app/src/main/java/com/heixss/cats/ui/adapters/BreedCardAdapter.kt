package com.heixss.cats.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.heixss.cats.R
import com.heixss.cats.model.local.BreedCard
import com.heixss.cats.ui.adapters.diffutil.BreedCardDiffUtil
import io.reactivex.subjects.PublishSubject

class BreedCardAdapter(private var breedCardsList: ArrayList<BreedCard>) :
    RecyclerView.Adapter<BreedCardViewHolder>() {
    var cardClickSubject = PublishSubject.create<BreedCard>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedCardViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.breed_card_item, parent, false)
        return BreedCardViewHolder(view, cardClickSubject)
    }

    override fun getItemCount(): Int {
        return breedCardsList.size
    }

    override fun onBindViewHolder(holder: BreedCardViewHolder, position: Int) {
        holder.setup(breedCardsList[position])
    }

    fun updateCards(newBreedCardsList: List<BreedCard>) {
        val diffResult =
            DiffUtil.calculateDiff(BreedCardDiffUtil(breedCardsList, newBreedCardsList))
        breedCardsList.clear()
        breedCardsList.addAll(newBreedCardsList)
        diffResult.dispatchUpdatesTo(this)
    }
}

class BreedCardViewHolder(view: View, private val cardClickSubject: PublishSubject<BreedCard>) :
    RecyclerView.ViewHolder(view) {

    private lateinit var breedCard: BreedCard
    private val ivPic = view.findViewById<ImageView>(R.id.iv_pic)
    private val tvName = view.findViewById<TextView>(R.id.tv_name)
    private val tvDescription = view.findViewById<TextView>(R.id.tv_desc)

    init {
        view.setOnClickListener {
            if (::breedCard.isInitialized)
                cardClickSubject.onNext(breedCard)
        }
    }

    fun setup(breedCard: BreedCard) {
        this.breedCard = breedCard
        breedCard.imageUrl?.let {
            Glide.with(itemView.context).load(it)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(ivPic)
        }
        tvName.text = breedCard.name
        tvDescription.text = breedCard.description
    }
}