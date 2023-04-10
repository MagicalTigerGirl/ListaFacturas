package com.example.listafacturas.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.listafacturas.R
import com.example.listafacturas.data.adapter.FacturaAdapter
import com.example.listafacturas.databinding.FragmentFirstBinding
import com.example.listafacturas.data.model.Factura
import com.example.listafacturas.ui.basedialog.BaseDialogFragment
import com.example.listafacturas.viewmodel.FacturaViewModel
import com.example.listafacturas.viewmodel.StateDataList

class FirstFragment : Fragment(), FacturaAdapter.OnManageFacturaListener {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FacturaAdapter
    private lateinit var viewModel: FacturaViewModel
    val actividad: FragmentActivity? = activity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                NavHostFragment.findNavController(this).navigate(R.id.action_FirstFragment_to_SecondFragment)
                true
            }
            else -> false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initViewModel()
    }

    private fun initRecyclerView() {
        adapter = FacturaAdapter(this)
        binding.rvFacturas.adapter = adapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity()).get(FacturaViewModel::class.java)

        viewModel.liveDataList.observe(viewLifecycleOwner, Observer {
            when (it.state) {
                StateDataList.DataState.SUCCESS -> adapter.update(it.data)
                StateDataList.DataState.NODATA -> binding.tvNoData.visibility = View.VISIBLE
                else -> {}
            }
        })

        viewModel.getDataList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onShowFactura(factura: Factura) {
        var bundle: Bundle = Bundle()
        bundle.putString(BaseDialogFragment.TITLE, "Información")
        bundle.putString(BaseDialogFragment.MESSAGE, "Esta funcionalidad aún no está disponible")

        NavHostFragment.findNavController(this).navigate(R.id.action_FirstFragment_to_baseDialogFragment, bundle)
    }
}