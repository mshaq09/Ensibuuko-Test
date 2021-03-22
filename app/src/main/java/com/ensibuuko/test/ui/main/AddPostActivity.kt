package com.ensibuuko.test.ui.main

import android.content.Context
import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.setActionButtonEnabled
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.ensibuuko.test.R
import com.ensibuuko.test.databinding.ActivityAddPostBinding
import com.ensibuuko.test.ui.models.Posts
import com.ensibuuko.test.ui.models.User
import io.realm.Case
import io.realm.Realm

class AddPostActivity(context: Context) : AppCompatActivity() {

    private lateinit var binding: ActivityAddPostBinding
    private var user: User? = null
    private  val type = InputType.TYPE_CLASS_TEXT or
            InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityAddPostBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        showDialog()

        binding.save.setOnClickListener {
            if(validate()){
                savePost(binding.title.text.toString(), binding.desc.text.toString())
            }

        }

    }

    private fun showDialog(){
        MaterialDialog(this).show {
            input(hintRes = R.string.enter_username, inputType = type) { dialog, text ->
                // Text submitted with the action button
                val inputField = dialog.getInputField()

                if(verifyUser(username = text.toString())){
                    binding.username.text = (user?.username)
                    Utils.save(Utils.NAME_KEY, user?.name!!, this@AddPostActivity)
                    Utils.save(Utils.USERNAME_KEY, user?.username!!, this@AddPostActivity)
                    dialog.setActionButtonEnabled(WhichButton.POSITIVE, true)
                    dialog.dismiss()
                }else{
                    inputField.error = "Enter a valid username"
                    dialog.setActionButtonEnabled(WhichButton.POSITIVE, false)
                }

            }
            positiveButton(R.string.submit)
            negativeButton(R.string.cancel){ dialog ->
                // Do something
                dialog.dismiss()
                finish()
            }
            noAutoDismiss()
            cancelOnTouchOutside(false)
            title(R.string.login)
            message(R.string.enter_username_msg)
        }
    }

    private fun savePost(title: String?, body: String?) {
        val realm = Realm.getDefaultInstance()
        val post = Posts()
        post.body = body
        post.title = title
        post.userId = user?.id!!

        // Get the current max id in the users table
        // Get the current max id in the users table
        val maxId: Number? = realm.where(Posts::class.java).max("id")
        // If there are no rows, currentId is null, so the next id must be 1
        // If currentId is not null, increment it by 1
        // If there are no rows, currentId is null, so the next id must be 1
        // If currentId is not null, increment it by 1
        val nextId = if (maxId == null) 1 else maxId.toInt() + 1
        // User object created with the new Primary key
        post.id = nextId

        realm.executeTransactionAsync({ bgrealm -> bgrealm.insert(post) }, {
            MaterialDialog(this).show {
                title(R.string.success)
                message(R.string.success_msg)
                cancelOnTouchOutside(false)
                positiveButton(R.string.ok) {
                    finish()
                }
            }

        }) {
            it.printStackTrace()
        }
    }

    fun verifyUser(username: String) :Boolean{
        val realm = Realm.getDefaultInstance()
        user = realm.where(User::class.java).equalTo("username", username, Case.INSENSITIVE).findFirst()
       return user != null
    }

     fun validate(): Boolean{
        if(binding.title.text.isEmpty()){
            binding.title.error = getString(R.string.title_empty)
            return false
        }else if(binding.desc.text.isEmpty()){

            binding.desc.error = getString(R.string.desc_empty)
            return false
        }
        return true
    }

}