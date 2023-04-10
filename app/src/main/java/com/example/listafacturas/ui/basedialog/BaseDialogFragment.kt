package com.example.listafacturas.ui.basedialog

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.fragment.NavHostFragment
import com.example.listafacturas.R


class BaseDialogFragment : DialogFragment() {
    companion object {
        val TITLE = "title"
        val MESSAGE = "message"
        val REQUEST = "requestdialog"
        val KEY_BUNDLE = "result"
    }

    @NonNull
    override fun onCreateDialog(@Nullable savedInstanceState: Bundle?): Dialog {
        val title: String? = arguments?.getString(TITLE)
        val message: String? = arguments?.getString(MESSAGE)
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(
            "CERRAR", DialogInterface.OnClickListener { dialogInterface, i ->
                NavHostFragment.findNavController(this).navigateUp()
            })
        return builder.create()
    }
}