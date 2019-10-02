package com.market.admin.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.widget.CircularProgressDrawable
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.market.admin.R


//amination
fun AppCompatActivity.leftToRight() {
    this.overridePendingTransition(R.anim.enter, R.anim.exit)
}

fun AppCompatActivity.rightToLeft() {
    this.overridePendingTransition(R.anim.left_to_right_silder, R.anim.right_to_left__silder)
}

/*New Activity Start*/
fun AppCompatActivity.startNewActivity(cls: Class<*>) {
    this.startActivity(Intent(this, cls))
    this.finish()
}

fun AppCompatActivity.startNewActivityNoFinish(cls: Class<*>) {
    this.startActivity(Intent(this, cls))
}

fun AppCompatActivity.startNewActivityFlag(cls: Class<*>) {
    val intent = Intent(this, cls)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    this.startActivity(intent)
    finish()
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.log(tag:String,message: String) {
    Log.d("$tag=> ",message)
}

//Clear Session
fun clearAllDataAndLogOut() {

    try {
        SharedPref.instance.logOut()
    } catch (e: Exception) {
    }

    try {
       // val intent = Intent(App.appContext!!, LoginActivity::class.java)
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
       // App.appContext!!.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }

}



fun Context.hideSoftKeyboard() {
    try {
        val inputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow((this as Activity).currentFocus!!.windowToken, 0)
    } catch (e: Exception) {
    }
}

//Custom Snackbar
fun Context.showSnackBar(message: String): Snackbar {
    val sb = Snackbar.make((this as Activity).findViewById<View>(android.R.id.content), message, Snackbar.LENGTH_LONG)
    sb.view.setBackgroundColor(ContextCompat.getColor(this, R.color.sign_up_color))
    val textView = sb.view.findViewById<TextView>(android.support.design.R.id.snackbar_text)
    textView.setTextColor(Color.parseColor("#FFFFFF"))
    sb.show()
    return sb
}

// Image Load glide profile
fun ImageView.loadImageProfile(url:String){
      Glide.with(context)
        .load(url)
        .placeholder(R.drawable.avatar)
        .fitCenter()
        .into(this)
}
// Image Load glide
fun ImageView.loadImage(url:String){
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    Glide.with(context)
        .load(url)
        .placeholder(circularProgressDrawable)
        .fitCenter()
        .into(this)
}

