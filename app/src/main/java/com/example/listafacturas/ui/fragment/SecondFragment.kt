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

        binding.btnDateDesde.setOnClickListener(View.OnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val popup= DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener{ view, year, montOfYear, dayOfMonth ->
                val months = montOfYear+1
                binding.btnDateDesde.text = ("$dayOfMonth/$months/$year")
            }, year, month, day)

            popup.show()
        })

        binding.btnDateHasta.setOnClickListener(View.OnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val popup= DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener{ view, year, montOfYear, dayOfMonth ->
                val months = montOfYear+1
                binding.btnDateHasta.text = ("$dayOfMonth/$months/$year")
            }, year, month, day)

            popup.datePicker.minDate = SimpleDateFormat("dd/MM/yyyy").parse(binding.btnDateDesde.text.toString()).time
            popup.show()
        })


        val importeMax = viewModel.maxImporte.toInt()+1
        binding.sbImporte.max = importeMax
        binding.tvImporteMax.text = importeMax.toString().plus(" €")

        binding.sbImporte.setOnSeekBarChangeListener(
            object : OnSeekBarChangeListener {
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onProgressChanged(
                    seekBar: SeekBar, progress: Int,
                    fromUser: Boolean
                ) {
                    binding.tvImporte.text = ("0€ - $progress €")
                }
            }
        )

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity()).get(FacturaViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}