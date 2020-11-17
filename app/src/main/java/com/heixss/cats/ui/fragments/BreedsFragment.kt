package com.heixss.cats.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.heixss.cats.R
import com.heixss.cats.ui.adapters.BreedCardAdapter
import com.heixss.cats.ui.adapters.ItemVerticalSpacingDecorator
import com.heixss.cats.viewmodels.BreedsViewModel
import com.heixss.cats.viewmodels.Progress
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.breeds_fragment.*
import java.util.*

@AndroidEntryPoint
class BreedsFragment : BaseFragment(R.layout.breeds_fragment) {

    private lateinit var breedCardAdapter: BreedCardAdapter
    private val viewModel: BreedsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configUi()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.breedCardsLiveData().observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                viewModel.unfilteredBreedCards = it
                breedCardAdapter.updateCards(it)
            } else viewModel.loadBreedsAndStore()
        })
    }

    override fun onResume() {
        super.onResume()
        observeCardClickSubject()
        observeErrorSubject()
        observeRefreshSubject()
    }

    private fun configUi() {
        breedCardAdapter = BreedCardAdapter(arrayListOf())
        breedCardAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        rv_breeds.addItemDecoration(
            ItemVerticalSpacingDecorator(
                resources.getDimensionPixelSize(
                    R.dimen.cards_vertical_margin
                )
            )
        )
        rv_breeds.adapter = breedCardAdapter

        srf_layout.setOnRefreshListener {
            spnr_country_code.setSelection(0)
            viewModel.clearCachedBreeds()
            viewModel.loadBreedsAndStore()
        }
        spnr_country_code.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                spnr_country_code.selectedView?.let { v ->
                    val result = (v as TextView).text.toString()
                    getCountryCode(result)?.let { countryCode ->
                        if (viewModel.isUnfilteredBreedCardsInitialized()) {
                            val filteredBreedCards =
                                viewModel.unfilteredBreedCards.filter { it.countryCode == countryCode }
                            if (filteredBreedCards.isEmpty())
                                tv_no_breeds.visibility = View.VISIBLE
                            else
                                tv_no_breeds.visibility = View.GONE
                            breedCardAdapter.updateCards(filteredBreedCards)
                        }
                    } ?: run {
                        if (viewModel.isUnfilteredBreedCardsInitialized()) {
                            tv_no_breeds.visibility = View.GONE
                            breedCardAdapter.updateCards(viewModel.unfilteredBreedCards)
                        }
                    }
                }
            }

        }
    }

    fun getCountryCode(countryName: String) =
        Locale.getISOCountries().find { Locale("", it).displayCountry == countryName }

    private fun observeRefreshSubject() {
        disposables.add(viewModel.refreshSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                srf_layout.isRefreshing = it == Progress.SHOW
            })
    }

    private fun observeErrorSubject() {
        disposables.add(viewModel.errorSubjectSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            })
    }

    private fun observeCardClickSubject() {
        disposables.add(breedCardAdapter.cardClickSubject.subscribe {
            findNavController().navigate(
                BreedsFragmentDirections.actionBreedsFragmentToBreedDetailsFragment(
                    it
                )
            )
        })
    }
}
