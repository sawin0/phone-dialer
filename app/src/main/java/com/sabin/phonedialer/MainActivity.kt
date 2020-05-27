package com.sabin.phonedialer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener


class MainActivity : AppCompatActivity(), PermissionListener {

    lateinit var etPhone: EditText
    lateinit var ibCall: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etPhone = findViewById(R.id.etPhone)
        ibCall = findViewById(R.id.ibCall)

        ibCall.setOnClickListener {
            checkPhonePermission()
        }


    }

    /*
    * Dexter checking Phone permission
     */
    private fun checkPhonePermission() {

        Dexter.withContext(this)
            .withPermission(Manifest.permission.CALL_PHONE)
            .withListener(this)
            .check()
    }

    /*
    * makes phone call to number user has entered also checks for permission
     */
    private fun makePhoneCall() {
        // store user entered phone number and trims whitespaces
        val phoneNumber = etPhone.text.toString().trim()
        if(phoneNumber.length > 0) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                checkPhonePermission()
            } else {
                startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber")))

            }
        } else{
            Toast.makeText(applicationContext, "Please enter a valid number", Toast.LENGTH_SHORT).show()
        }

    }

    /*
    * Calling number when user grants permission
     */
    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
        makePhoneCall()
    }

    /*
    * Repeatedly ask for permission
     */
    override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {
        p1?.continuePermissionRequest()
    }

    /*
    * used when user permanently denied permission
     */
    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
        if (p0!!.isPermanentlyDenied) {
            openSettings()
        }
        Toast.makeText(applicationContext, "Please grant phone permission", Toast.LENGTH_SHORT)
            .show()
    }

    /**
     * Opens app details screen from the settings
     */
    private fun openSettings() {
        var intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        var uri = Uri.parse("package:${BuildConfig.APPLICATION_ID}")
        intent.setData(uri)
        startActivity(intent)
    }
}
