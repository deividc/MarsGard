package com.deividsilva.marsgard

import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ScanMode


private const val CAMERA_REQUEST_CODE = 101

class QR_Camera : AppCompatActivity() {
    private var scanned_value: String = ""
    private lateinit var db: DatabaseAccess
    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q_r__camera)

        this.db = DatabaseAccess(this)
        codeScanner()
    }

    private fun codeScanner(){
        codeScanner = CodeScanner(this, findViewById(R.id.qrcode_scanner_view))
        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS

            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                runOnUiThread {
                    scanned_value = it.text
                    var temp = db.get_garden_key_by_serial_number(scanned_value)
                    findViewById<TextView>(R.id.qrcode_textview).text = temp

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()

    }

    override fun onPause() {
        super.onPause()
        codeScanner.releaseResources()
    }

    private fun requestCamera(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Camera permission required", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}