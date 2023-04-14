package com.example.listafacturas.ui.basedialog

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.fragment.NavHostFragment


class BaseDialogFragment : DialogFragment() {
    companion object {
        val TITLE = "title"
        val MESSAGE = "message"
    }

    @NonNull
    override fun onCreateDialog(@Nullable savedInstanceState: Bundle?): Dialog {
        val title: String? = arguments?.getString(TITLE)
        val message: String? = arguments?.getString(MESSAGE)
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(
            "CERRAR", DialogInterface.OnClickListener { _,_ ->
                NavHostFragment.findNavController(this).navigateUp()
            })
        return builder.create()
    }
}