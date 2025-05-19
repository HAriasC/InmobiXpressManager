package com.inmobixpress.inmobixpressmanager.ui.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.inmobixpress.inmobixpressmanager.ui.model.UIState
import com.inmobixpress.inmobixpressmanager.ui.viewmodel.MainViewModel

fun Context.callProprietor(viewModel: MainViewModel, id: Int) {
    try {
        val call = Uri.parse(
            "tel:${
                viewModel.deviceItems.value!!.first {
                    it.user.id == id
                }.phone
            }"
        )
        this.startActivity(Intent(Intent.ACTION_DIAL, call))
    } catch (_: SecurityException) {
    }
}

fun Context.sendWhatsAppsProprietor(
    viewModel: MainViewModel,
    id: Int,
    message: String
) {
    try {
        val send = Uri.parse(
            String.format(
                format = "https://api.whatsapp.com/send?phone=%s&text=%s",
                viewModel.deviceItems.value!!.first {
                    it.user.id == id
                }.phone,
                message
            )
        )
        this.startActivity(Intent(Intent.ACTION_VIEW, send))
    } catch (_: SecurityException) {
    }
}

fun Context.launchEmailChooser(email: String, title: String, text: String) {
    Log.e("EM", email)
    val to = arrayOf(email) //Direcciones email  a enviar.
    val cc = arrayOf("") //Direcciones email con copia.
    val emailIntent = Intent(Intent.ACTION_SENDTO)
    emailIntent.setData(Uri.parse("mailto:"))
    emailIntent.putExtra(Intent.EXTRA_EMAIL, to)
    emailIntent.putExtra(Intent.EXTRA_CC, cc)
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, title)
    emailIntent.putExtra(Intent.EXTRA_TEXT, text)
    try {
        this.startActivity(Intent.createChooser(emailIntent, "Enviar email"))
        Log.e("EMAIL", "Enviando email...")
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(
            this,
            "NO existe ningún cliente de email instalado!.",
            Toast.LENGTH_SHORT
        ).show()
    }
}

fun Context.launchPDFChooser(url: String) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(url)
    )
    this.startActivity(Intent.createChooser(intent, "View PDF"))
}