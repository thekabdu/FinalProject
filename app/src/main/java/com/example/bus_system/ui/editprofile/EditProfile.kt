package com.example.bus_system.ui.editprofile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.bus_system.R
import com.example.bus_system.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import java.io.ByteArrayOutputStream


class EditProfile : Fragment() {

    private val DEFAULT_IMAGE_URL = "https://picsum.photos/200"
    private lateinit var imageUri :Uri
    private val REQUEST_IMAGE_CAPTURE = 100

    private val currentUser =FirebaseAuth.getInstance().currentUser

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentUser?.let { user->

            Glide.with(this)
                    .load(user.photoUrl)
                    .into(img_profile)

            edit_text_name.setText(user.displayName)
           // edit_text_surname.text = user.surname
            //edit_text_email.text = user.email

            //edit_text_phone.text = if(user.phoneNumber.isNullOrEmpty()) "Add Number" else user.phoneNumber
        }

        img_profile.setOnClickListener {
            takePictureIntent()
        }

        btn_save_prf_edit.setOnClickListener{

            val photo = when {
                ::imageUri.isInitialized -> imageUri
                currentUser?.photoUrl == null ->Uri.parse(DEFAULT_IMAGE_URL)
                else -> currentUser.photoUrl
            }

            val name = edit_text_name.text.toString().trim()

            if (name.isEmpty()){
                edit_text_name.error = "name required"
                edit_text_name.requestFocus()
                return@setOnClickListener
            }

            val updates = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .setPhotoUri(photo)
                    .build()

            currentUser?.updateProfile(updates)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            context?.toast("Profile Update")
                        }else{
                            context?.toast(task.exception?.message!!)
                        }

                    }
        }
    }

    private fun takePictureIntent(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also{pictureIntent->
            pictureIntent.resolveActivity(activity?.packageManager!!)?.also{
                startActivityForResult(pictureIntent,REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){

            val imageBitmap = data?.extras?.get("data") as Bitmap
            //upload image
            uploadImageAndSaveUri(imageBitmap)
        }
    }

    private fun uploadImageAndSaveUri(bitmap: Bitmap){
        val baos = ByteArrayOutputStream()
        val storageRef = FirebaseStorage.getInstance()
                .reference
                .child("pics/${FirebaseAuth.getInstance().currentUser?.uid}")
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image = baos.toByteArray()

        val upload = storageRef.putBytes(image)

        progressbar_img.visibility = View.VISIBLE
        upload.addOnCompleteListener{uploadTask ->
            progressbar_img.visibility = View.INVISIBLE

            if (uploadTask.isSuccessful){
                storageRef.downloadUrl.addOnCompleteListener { urlTask ->
                        urlTask.result?.let{
                            imageUri = it
                            img_profile.setImageBitmap(bitmap)
                        }
                }
            }
        }
    }
}