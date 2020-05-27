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


class MainActivity : AppCompatActivity(), PermissionListener {

    lateinit var tvDisplay: TextView
    lateinit var ibCall: ImageView
    lateinit var ivDel: ImageView
    lateinit var btn1: Button
    lateinit var btn2: Button
    lateinit var btn3: Button
    lateinit var btn4: Button
    lateinit var btn5: Button
    lateinit var btn6: Button
    lateinit var btn7: Button
    lateinit var btn8: Button
    lateinit var btn9: Button
    lateinit var btn0: Button

    var phoneNumber: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDisplay = findViewById(R.id.tvDisplay)
        ibCall = findViewById(R.id.ivCall)
        ivDel = findViewById(R.id.ivDel)

        btn0 = findViewById(R.id.button0)
        btn1 = findViewById(R.id.button1)
        btn2 = findViewById(R.id.button2)
        btn3 = findViewById(R.id.button3)
        btn4 = findViewById(R.id.button4)
        btn5 = findViewById(R.id.button5)
        btn6 = findViewById(R.id.button6)
        btn7 = findViewById(R.id.button7)
        btn8 = findViewById(R.id.button8)
        btn9 = findViewById(R.id.button9)

        ibCall.setOnClickListener {
            checkPhonePermission()
        }

        ivDel.setOnClickListener {
            phoneNumber = phoneNumber.dropLast(1)
            tvDisplay.text = phoneNumber
        }

        btn0.setOnClickListener{
            phoneNumber = phoneNumber.plus("0")
            tvDisplay.text = phoneNumber
            print(phoneNumber)
        }
        btn1.setOnClickListener{
            phoneNumber = phoneNumber.plus("1")
            tvDisplay.text = phoneNumber
        }
        btn2.setOnClickListener{
            phoneNumber = phoneNumber.plus("2")
            tvDisplay.text = phoneNumber
        }
        btn3.setOnClickListener{
            phoneNumber = phoneNumber.plus("3")
            tvDisplay.text = phoneNumber
        }
        btn4.setOnClickListener{
            phoneNumber = phoneNumber.plus("4")
            tvDisplay.text = phoneNumber
        }
        btn5.setOnClickListener{
            phoneNumber = phoneNumber.plus("5")
            tvDisplay.text = phoneNumber
        }
        btn6.setOnClickListener{
            phoneNumber = phoneNumber.plus("6")
            tvDisplay.text = phoneNumber
        }
        btn7.setOnClickListener{
            phoneNumber = phoneNumber.plus("7")
            tvDisplay.text = phoneNumber
        }
        btn8.setOnClickListener{
            phoneNumber = phoneNumber.plus("8")
            tvDisplay.text = phoneNumber
        }
        btn9.setOnClickListener{
            phoneNumber = phoneNumber.plus("9")
            tvDisplay.text = phoneNumber
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
