package com.bm.bankmasrtask.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bm.bankmasrtask.data.model.response.HistoricalDataResponse
import com.bm.bankmasrtask.databinding.FragmentDetailsBinding
import com.bm.bankmasrtask.domain.entity.Resource
import com.bm.bankmasrtask.presentation.basefragment.BaseFragment
import com.bm.bankmasrtask.presentation.details.adapter.HistoricalDataAdapter
import com.bm.bankmasrtask.presentation.details.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : BaseFragment() {
    private var binding: FragmentDetailsBinding? = null

    @Inject
    lateinit var historicalAdapter: HistoricalDataAdapter

    private lateinit var viewModel: DetailsViewModel
    private var from: String? = null
    private var to: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        from = DetailsFragmentArgs.fromBundle(requireArguments()).from
        to = DetailsFragmentArgs.fromBundle(requireArguments()).to
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            initialization()
    }
    private fun initialization() {
        initViewModel()
        initRV()
    }
    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        viewModel.getHistoricalData(accessKey = "e30974634ea8ab00fd58c75b27e4385d",from!!,to!!)
        viewModel.getHistoricalLiveData().observe(viewLifecycleOwner, historicalObserver)
    }
    private fun initRV() {
        binding!!.detailsRv.adapter = historicalAdapter
    }
    private var historicalObserver: Observer<Resource<HistoricalDataResponse?>> = Observer {
        when (it.status) {
            Resource.Status.LOADING -> {
            }
            Resource.Status.SUCCESS -> {
                val historicalData = it.data?.rates
                if (historicalData != null) {
//                    historicalAdapter.setData(historicalData)
                } else {
                    handleError("No historical data available")
                }
            }
            Resource.Status.API_ERROR -> {
                handleError(it.error_msg.toString())
            }
            Resource.Status.DOMAIN_ERROR -> {
                handleError(it.throwable.toString())
            }
        }
    }
}