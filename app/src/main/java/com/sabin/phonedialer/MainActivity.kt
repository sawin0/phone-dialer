package com.sabin.phonedialer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.sabin.phonedialer.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), PermissionListener {

    var phoneNumber: String = ""

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.ivCall.setOnClickListener {
            checkPhonePermission()
        }

        binding.ivDel.setOnClickListener {
            phoneNumber = phoneNumber.dropLast(1)
            binding.tvDisplay.text = phoneNumber
        }


        binding.ivDel.setOnLongClickListener{
            phoneNumber = ""
            binding.tvDisplay.text = phoneNumber
            true
        }

        binding.button0.setOnClickListener{
            phoneNumber = phoneNumber.plus("0")
            binding.tvDisplay.text = phoneNumber
            print(phoneNumber)
        }
        binding.button1.setOnClickListener{
            phoneNumber = phoneNumber.plus("1")
            binding.tvDisplay.text = phoneNumber
        }
        binding.button2.setOnClickListener{
            phoneNumber = phoneNumber.plus("2")
            binding.tvDisplay.text = phoneNumber
        }
        binding.button3.setOnClickListener{
            phoneNumber = phoneNumber.plus("3")
            binding.tvDisplay.text = phoneNumber
        }
        binding.button4.setOnClickListener{
            phoneNumber = phoneNumber.plus("4")
            binding.tvDisplay.text = phoneNumber
        }
        binding.button5.setOnClickListener{
            phoneNumber = phoneNumber.plus("5")
            binding.tvDisplay.text = phoneNumber
        }
        binding.button6.setOnClickListener{
            phoneNumber = phoneNumber.plus("6")
            binding.tvDisplay.text = phoneNumber
        }
        binding.button7.setOnClickListener{
            phoneNumber = phoneNumber.plus("7")
            binding.tvDisplay.text = phoneNumber
        }
        binding.button8.setOnClickListener{
            phoneNumber = phoneNumber.plus("8")
            binding.tvDisplay.text = phoneNumber
        }
        binding.button9.setOnClickListener{
            phoneNumber = phoneNumber.plus("9")
            binding.tvDisplay.text = phoneNumber
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
        if(phoneNumber.isNotEmpty() && phoneNumber.length == 10) {
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
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.parse("package:${BuildConfig.APPLICATION_ID}")
        intent.data = uri
        startActivity(intent)
    }
}
