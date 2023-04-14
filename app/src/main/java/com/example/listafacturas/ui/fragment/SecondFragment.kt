package com.example.listafacturas.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.listafacturas.R
import com.example.listafacturas.data.FacturaRepository
import com.example.listafacturas.databinding.FragmentSecondBinding
import com.example.listafacturas.viewmodel.FacturaViewModel
import java.text.SimpleDateFormat
import java.util.*


class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FacturaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_second_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_close -> {
                NavHostFragment.findNavController(this).navigateUp()
                true
            }
            else -> false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        initDataPicker()

        initSeekBar()

        binding.btnAplicarFiltros.setOnClickListener(View.OnClickListener {

            viewModel.isFiltered = true

            // Fechas
            val fechaDesde = binding.btnDateDesde.text.toString()
            if (!fechaDesde.equals(viewModel.dateInit))
                viewModel.fechaDesde = fechaDesde

            val fechaHasta = binding.btnDateHasta.text.toString()
            if (!fechaHasta.equals(viewModel.dateInit))
                viewModel.fechaHasta = fechaHasta

            // Importe
            viewModel.importeMaxSelected = binding.sbImporte.progress

            viewModel.isChecked = viewModel.bPagadas.value == true || viewModel.bAnuladas.value == true || viewModel.bCuotaFija.value == true || viewModel.bPendientePagos.value == true || viewModel.bPlanPago.value == true

            viewModel.filter()

            NavHostFragment.findNavController(this).navigateUp()
        })

        binding.btnEliminarFiltros.setOnClickListener(View.OnClickListener {
            viewModel.fechaDesde = viewModel.dateInit
            viewModel.fechaHasta = viewModel.dateInit
            binding.btnDateDesde.text = viewModel.fechaDesde
            binding.btnDateHasta.text = viewModel.fechaHasta
            binding.sbImporte.progress = FacturaRepository.importeMaximo.toInt()
        })
    }

    private fun initSeekBar() {
        val importeMax = FacturaRepository.importeMaximo.toInt()
        binding.sbImporte.max = importeMax
        binding.tvImporteMax.text = importeMax.toString().plus(" €")

        val importeProgress = viewModel.importeMaxSelected
        binding.sbImporte.progress = importeProgress
        binding.tvImporte.text = ("0€ - $importeProgress €")

        binding.sbImporte.setOnSeekBarChangeListener(
            object : OnSeekBarChangeListener {
                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    // Cuando se deja de mover el SeekBar
                }
                override fun onStartTrackingTouch(seekBar: SeekBar) {
                    // Cuando se comienza a mover el SeekBar
                }
                override fun onProgressChanged(
                    seekBar: SeekBar, progress: Int,
                    fromUser: Boolean
                ) {
                    binding.tvImporte.text = ("0€ - $progress €")
                }
            }
        )
    }

    private fun initDataPicker() {
        binding.btnDateDesde.setOnClickListener(View.OnClickListener {
            val c = Calendar.getInstance()

            val popup= DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener{ _, year, monthOfYear, dayOfMonth ->
                val days = if (dayOfMonth < 10) "0${dayOfMonth}" else "${dayOfMonth}"

                val month = monthOfYear+1
                val months = if (month < 10) "0${month}" else "${month}"

                binding.btnDateDesde.text = ("$days/$months/$year")
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))

            popup.datePicker.maxDate = Calendar.getInstance().timeInMillis

            popup.show()
        })

        binding.btnDateHasta.setOnClickListener(View.OnClickListener {
            val c = Calendar.getInstance()

            val popup= DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener{ _, year, monthOfYear, dayOfMonth ->
                val days = if (dayOfMonth < 10) "0${dayOfMonth}" else "${dayOfMonth}"

                val month = monthOfYear+1
                val months = if (month < 10) "0${month}" else "${month}"

                binding.btnDateHasta.text = ("$days/$months/$year")
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))

            if (!binding.btnDateDesde.text.toString().equals("día/mes/año"))
                popup.datePicker.minDate = SimpleDateFormat("dd/MM/yyyy").parse(binding.btnDateDesde.text.toString() ).time
            popup.show()
        })
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity()).get(FacturaViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // DatePicker
        binding.btnDateDesde.text = viewModel.fechaDesde
        binding.btnDateHasta.text = viewModel.fechaHasta
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}