package com.bm.bankmasrtask.presentation.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bm.bankmasrtask.R
import com.bm.bankmasrtask.data.model.response.LatestResponse
import com.bm.bankmasrtask.databinding.FragmentHomeBinding
import com.bm.bankmasrtask.domain.entity.Resource
import com.bm.bankmasrtask.presentation.basefragment.BaseFragment
import com.bm.bankmasrtask.presentation.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.stream.Collectors.toList

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private var binding: FragmentHomeBinding? = null
    private lateinit var viewModel: HomeViewModel
    private var isAmountUpdating = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            initialization()
        }
    }

    private fun initialization() {
        initViewModel()
        initListeners()
    }
    private fun initListeners() {
        binding!!.detailsButton.setOnClickListener {
           val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(from = binding!!.fromInput.text.toString(), to = binding!!.toInput.text.toString())
            findNavController().navigate(action)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.getLatestData(accessKey = "e30974634ea8ab00fd58c75b27e4385d")
        viewModel.getLatestLiveData().observe(viewLifecycleOwner, latestObserver)

        binding!!.fromInput.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateConversionRate()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding!!.toInput.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateConversionRate()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding!!.fromAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!isAmountUpdating && !s.isNullOrEmpty() && viewModel != null) {
                    try {
                        val amount = s.toString().toDouble()
                        val rate = viewModel.getConversionRate(binding!!.fromInput.text.toString(), binding!!.toInput.text.toString())
                        isAmountUpdating = true // Set the flag to true to prevent infinite loop
                        binding!!.toAmount.setText((amount * rate).toString())
                        isAmountUpdating = false // Reset the flag
                    } catch (e:NumberFormatException){
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding!!.toAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!isAmountUpdating && !s.isNullOrEmpty() && viewModel != null) {
                    try {
                        val amount = s.toString().toDouble()
                        val rate = viewModel.getConversionRate(binding!!.toInput.text.toString(), binding!!.fromInput.text.toString())
                        isAmountUpdating = true // Set the flag to true to prevent infinite loop
                        binding!!.fromAmount.setText((amount * rate).toString())
                        isAmountUpdating = false // Reset the flag
                    } catch (e:NumberFormatException){
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        updateConversionRate()
    }
    private fun updateConversionRate() {
        val fromCurrency = binding!!.fromInput.text.toString()
        val toCurrency = binding!!.toInput.text.toString()
        val rate = viewModel.getConversionRate(fromCurrency, toCurrency)
        val amount = binding!!.fromAmount.text.toString().toDoubleOrNull() ?: 0.0
        binding!!.toAmount.setText((amount * rate).toString())
    }
    private var latestObserver: Observer<Resource<LatestResponse?>> = Observer {
        when (it.status) {
            Resource.Status.LOADING -> {
            }
            Resource.Status.SUCCESS -> {
                val latestResponse = it.data
                val toInput: AutoCompleteTextView = binding!!.toInput
                val fromInput: AutoCompleteTextView = binding!!.fromInput

                val rates = latestResponse?.rates
                val currencies = rates?.let {
                    it.javaClass.declaredFields.map { field ->
                        field.name
                    }
                } ?: emptyList()
                val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, currencies)
                toInput.setAdapter(adapter)
                toInput.onItemClickListener =
                    AdapterView.OnItemClickListener { adapterView, _, i, _ ->
                        val itemSelected = adapterView.getItemAtPosition(i)
                        Toast.makeText(requireContext(), "$itemSelected", Toast.LENGTH_SHORT).show()
                    }
                val adapter2 = ArrayAdapter(requireContext(), R.layout.dropdown_item, currencies)
                fromInput.setAdapter(adapter2)
                fromInput.onItemClickListener =
                    AdapterView.OnItemClickListener { adapterView, _, i, _ ->
                        val itemSelected = adapterView.getItemAtPosition(i)
                        Toast.makeText(requireContext(), "$itemSelected", Toast.LENGTH_SHORT).show()
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