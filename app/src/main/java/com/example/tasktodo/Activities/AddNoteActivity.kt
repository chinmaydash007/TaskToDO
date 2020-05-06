package com.example.tasktodo.Activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.example.tasktodo.BuildConfig
import com.example.tasktodo.R
import com.example.tasktodo.Utils.AppConstant
import com.example.tasktodo.Utils.logger
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity() {
    lateinit var addNote: MaterialButton
    lateinit var addImage: MaterialButton
    lateinit var titleTextLayout: TextInputLayout
    lateinit var descriptionTextLayout: TextInputLayout
    private val REQUEST_CODE_GALLERY = 1
    private val REQUEST_CODE_CAMERA = 2
    private val PERMISSIONS_CODE = 3
    private var picturePath = ""
    lateinit var alertDialog: AlertDialog
    lateinit var image: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        initViews()
        addImage.setOnClickListener {
            logger(
                "${checkAndRequestPermission()}"
            )
            if (checkAndRequestPermission()) {
                showChooserDialog()
            }
        }
        addNote.setOnClickListener {
            var title = titleTextLayout.editText?.text.toString()
            var description = descriptionTextLayout.editText?.text.toString()
            val intent = Intent()
            intent.putExtra(AppConstant.TITLE, title)
            intent.putExtra(AppConstant.DESCRIPTION, description)
            intent.putExtra(AppConstant.IMAGE_PATH, picturePath)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

    fun initViews() {
        addNote = findViewById(R.id.add_note_btn)
        addImage = findViewById(R.id.add_image_btn)
        titleTextLayout = findViewById(R.id.title)
        descriptionTextLayout = findViewById(R.id.description)
        image = findViewById(R.id.image)
        image.visibility = View.INVISIBLE
    }

    private fun showChooserDialog() {
        var view: View = LayoutInflater.from(this).inflate(R.layout.chooser_dialog_layout, null)
        var camera_textView: Button = view.findViewById(R.id.camera)
        var gallery_textView: Button = view.findViewById(R.id.gallery)
        alertDialog = AlertDialog.Builder(this).setView(view).setCancelable(true).create()

        camera_textView.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            var photofile: File? = null
            photofile = createImage()
            if (photofile != null) {
                val photoURI = FileProvider.getUriForFile(
                    this@AddNoteActivity,
                    BuildConfig.APPLICATION_ID + ".provider",
                    photofile
                )
                picturePath = photofile.absolutePath
                logger(picturePath)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA)
                alertDialog.hide()
            }
        }
        gallery_textView.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(intent, REQUEST_CODE_GALLERY)
            alertDialog.hide()
        }

        alertDialog.show()
    }


    private fun createImage(): File? {
        val timestamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val fileName = "JPEG_" + timestamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDir)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_GALLERY -> {
                    image.visibility = View.VISIBLE
                    val selectedImage = data?.data
                    val filePath = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor = contentResolver.query(selectedImage!!, filePath, null, null, null)
                    cursor?.moveToFirst()
                    val columnIndex = cursor?.getColumnIndex(filePath[0])
                    picturePath = cursor?.getString(columnIndex!!)!!
                    cursor.close()
                    logger(picturePath)
                    Glide.with(this).load(picturePath).into(image)
                    addImage.visibility = View.GONE
                }
                REQUEST_CODE_CAMERA -> {
                    logger(picturePath)
                    Glide.with(this).load(picturePath).into(image)
                    addImage.visibility = View.GONE
                }
            }
        }
    }

    private fun checkAndRequestPermission(): Boolean {
        val cameraPermission =
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        val storagePermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val listPermissionNeeded = ArrayList<String>()
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionNeeded.add(android.Manifest.permission.CAMERA)
        }
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (listPermissionNeeded.isNotEmpty()) {
            requestPermissions(
                this,
                listPermissionNeeded.toTypedArray(),
                PERMISSIONS_CODE
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {

            PERMISSIONS_CODE -> {
                logger("hello")
                if (grantResults.isNotEmpty() and (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    showChooserDialog()
                }
            }
            else -> {

            }
        }
    }

    override fun onPause() {
        super.onPause()
        alertDialog.dismiss()
    }
}

