package com.example.listafacturas.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.listafacturas.R
import com.example.listafacturas.data.adapter.FacturaAdapter
import com.example.listafacturas.data.model.Factura
import com.example.listafacturas.databinding.FragmentFirstBinding
import com.example.listafacturas.ui.basedialog.BaseDialogFragment
import com.example.listafacturas.viewmodel.FacturaViewModel
import com.example.listafacturas.viewmodel.StateDataList
import com.example.listafacturas.viewmodel.StateLiveDataList

class FirstFragment : Fragment(), FacturaAdapter.OnManageFacturaListener {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FacturaAdapter
    private lateinit var viewModel: FacturaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMenu()

        initRecyclerView()
        initViewModel()
    }

    private fun initMenu() {
        val menu: MenuHost = requireActivity()

        menu.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_filter -> {
                        NavHostFragment.findNavController(this@FirstFragment).navigate(R.id.action_FirstFragment_to_SecondFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
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
                else -> {
                    StateDataList.DataState.ERROR
                }
            }
        })

        viewModel.getDataList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onShowFactura(factura: Factura) {
        val bundle = Bundle()
        bundle.putString(BaseDialogFragment.TITLE, "Información (${factura.fecha})")
        bundle.putString(BaseDialogFragment.MESSAGE, "Esta funcionalidad aún no está disponible")

        NavHostFragment.findNavController(this).navigate(R.id.action_FirstFragment_to_baseDialogFragment, bundle)
    }
}