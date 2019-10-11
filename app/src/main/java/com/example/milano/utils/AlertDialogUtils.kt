package com.example.milano.utils

import android.app.AlertDialog
import android.content.Context
import android.view.View
import com.example.milano.R
import com.example.milano.async.DeletarProdutoAsyncTasks

class AlertDialogUtils {

    companion object{
        fun dialog(
            context: Context,
            title: String?,
            mensagem: String?,
            clicked:Boolean,
            view: View?
        ):AlertDialog.Builder {

            return AlertDialog.Builder(context)
                .setTitle(title)
                .setCancelable(clicked)
                .setMessage(mensagem)
                .setView(view)
        }

        fun alertDeletarProduto(context: Context) {

            var title = ResouscesUtils.get(context, R.string.title_alert)
            var msg = ResouscesUtils.get(context, R.string.mensagem_deletar)

            var builder = dialog(
                context, title.toUpperCase(), msg.toUpperCase(),
                false, null
            )

            var button_sim = ResouscesUtils.get(context,R.string.button_sim)
            var button_nao = ResouscesUtils.get(context, R.string.button_nao)

            builder.setPositiveButton(button_sim) { dialogInterface, i ->
                DeletarProdutoAsyncTasks(context).execute()
            }

            builder.setNegativeButton(button_nao) { dialogInterface, i ->
                dialogInterface.dismiss()
            }

            var alertDialog = builder.create()
            alertDialog.show()
        }

    }
}
