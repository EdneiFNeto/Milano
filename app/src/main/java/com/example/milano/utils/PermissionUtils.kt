package com.example.milano.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class PermissionUtils {
 companion object{
     private const val REQUEST_CODE_PERMISSSION_CAMERA: Int = 201

     fun checkPermissionCamera(context: Context, appCompatActivity: AppCompatActivity) {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             if (ContextCompat.checkSelfPermission(
                     context,
                     Manifest.permission.CAMERA
                 ) == PackageManager.PERMISSION_DENIED) {

                 //ask for permission
                 appCompatActivity.requestPermissions(
                     arrayOf(Manifest.permission.CAMERA),
                     REQUEST_CODE_PERMISSSION_CAMERA
                 )
             }
         }
     }
 }
}
