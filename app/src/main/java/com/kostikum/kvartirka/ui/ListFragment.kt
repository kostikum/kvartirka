package com.kostikum.kvartirka.ui

import android.Manifest.permission
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kostikum.kvartirka.R
import com.kostikum.kvartirka.adapters.RecyclerViewAdapter
import com.kostikum.kvartirka.databinding.FragmentListBinding
import com.kostikum.kvartirka.util.Failure
import com.kostikum.kvartirka.util.checkSelfPermissionCompat
import com.kostikum.kvartirka.viewmodels.ListViewModel
import com.kostikum.kvartirka.viewmodels.ListViewModelFactory
import kotlinx.android.synthetic.main.fragment_list.view.*
import java.util.*

const val PRMSSN_REQUEST = 0

class ListFragment : Fragment(), ActivityCompat.OnRequestPermissionsResultCallback {
    private val viewModel: ListViewModel by viewModels {
        ListViewModelFactory(requireActivity())
    }

    private var binding: FragmentListBinding? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentListBinding.inflate(inflater, container, false).let {
            binding = it
            it.root
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        checkAndRequestPermissions()
        val recyclerViewAdapter = RecyclerViewAdapter()
        recyclerViewAdapter.clickListener = { flat, view ->
            val direction = ListFragmentDirections.actionListFragmentToDetailsFragment(flat)
            view.findNavController().navigate(direction)
        }
        binding?.apply {
            root.flat_list.adapter = recyclerViewAdapter
            lifecycleOwner = viewLifecycleOwner
        }
        viewModel.flatList.observe(viewLifecycleOwner, Observer { list ->
            list?.apply { recyclerViewAdapter.flats = list }
        })
        viewModel.failure.observe(viewLifecycleOwner, Observer { handleFailure(it) })
    }

    private fun checkAndRequestPermissions() {
        when (checkSelfPermissionCompat(permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            true -> requestData()
            false -> requestPermissions(arrayOf(permission.ACCESS_FINE_LOCATION), PRMSSN_REQUEST)
        }
    }

    private fun requestData() {
        try {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    try {
                        if (location == null) throw Exception("Location not found")
                        val gcd = Geocoder(requireContext(), Locale("ru", "RU"))
                        val addresses: List<Address> =
                            gcd.getFromLocation(location.latitude, location.longitude, 1)
                        viewModel.getDataUsingCityName(addresses[0].countryName, addresses[0].locality)
                    } catch (e: Exception) {
                        Log.e(this::class.java.simpleName, e.toString())
                        viewModel.getDataUsingCityName()
                    }
                }
        } catch (securityException: SecurityException) {
            Log.e(this::class.java.simpleName, securityException.toString())
            viewModel.getDataUsingCityName()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PRMSSN_REQUEST) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), R.string.permission_granted, Toast.LENGTH_LONG)
                    .show()
                requestData()
            } else {
                Toast.makeText(requireContext(), R.string.permission_denied, Toast.LENGTH_LONG)
                    .show()
                viewModel.getDataUsingCityName()
            }
        }
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection ->
                Toast.makeText(
                    requireContext(),
                    R.string.failure_network_connection,
                    Toast.LENGTH_LONG
                ).show()
            is Failure.ServerError ->
                Toast.makeText(requireContext(), R.string.failure_server_error, Toast.LENGTH_LONG)
                    .show()
        }
    }
}
