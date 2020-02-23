package com.bdjobs.flutter_plugin_kotlin


import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.WindowManager
import android.widget.Toast


import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
import java.lang.Byte.MAX_VALUE
import java.lang.Byte.MIN_VALUE
import java.util.*


public class FlutterPluginKotlinPlugin : FlutterPlugin, MethodCallHandler,ActivityAware {



    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        val channel = MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "flutter_plugin_kotlin")
        channel.setMethodCallHandler(FlutterPluginKotlinPlugin())
       context = flutterPluginBinding.applicationContext
    }


    companion object {
        private var context: Context? = null
        var activity: Activity? = null
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val channel = MethodChannel(registrar.messenger(), "flutter_plugin_kotlin")
            channel.setMethodCallHandler(FlutterPluginKotlinPlugin())
            activity = registrar.activity()
            context = registrar.context()

        }
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        if (call.method == "getPlatformVersion") {
            result.success("Android ${Build.VERSION.RELEASE}")
        } else if (call.method == "showToast") {
            val msg = call.argument<Any>("msg").toString()
            Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
        } else if (call.method == "showAlertDialog") {
            val dialog = Dialog(activity!!)
            dialog.setTitle("Hi, My Name is Flutter")
            dialog.show()
        } else if(call.method == "screenSize"){

            Log.d("TTTT", "Screen Size "+ getScreenSizeInches())
            result.success("Screen Size ${getScreenSizeInches()}")

        }

        else {

            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    }

    override fun onDetachedFromActivity() {

    }

    override fun onReattachedToActivityForConfigChanges(p0: ActivityPluginBinding) {

    }

    override fun onAttachedToActivity(p0: ActivityPluginBinding) {

        activity = p0.activity
    }

    override fun onDetachedFromActivityForConfigChanges() {

    }

    private fun getScreenSizeInches(): String? {
        var x = 0.0
        var y = 0.0
        val mWidthPixels: Int
        val mHeightPixels: Int
        try {
            val windowManager: WindowManager = activity!!.getWindowManager()
            val display = windowManager.defaultDisplay
            val displayMetrics = DisplayMetrics()
            display.getMetrics(displayMetrics)
            val realSize = Point()
            Display::class.java.getMethod("getRealSize", Point::class.java).invoke(display, realSize)
            mWidthPixels = realSize.x
            mHeightPixels = realSize.y
            val dm = DisplayMetrics()
            activity!!.getWindowManager().getDefaultDisplay().getMetrics(dm)
            x = Math.pow(mWidthPixels / dm.xdpi.toDouble(), 2.0)
            y = Math.pow(mHeightPixels / dm.ydpi.toDouble(), 2.0)
        } catch (ignored: Exception) {
        }
        return String.format(Locale.US, "%.1f", Math.sqrt(x + y))
    }

    private fun getMobileNetwork(): String? {
        val telephonyManager = context!!.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager?.networkOperatorName
    }


  /*  private fun getInternalMemoryInfo(infoType: String): String? {
        val statFs = StatFs(Environment.getRootDirectory().absolutePath)
        var memoryInfo = ""
        val blockSize: Long
        val availableBlocks: Long
        val totalBlocks: Long
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = statFs.blockSizeLong
            availableBlocks = statFs.availableBlocksLong
            totalBlocks = statFs.blockCountLong
        } else {
            blockSize = statFs.blockSize.toLong()
            availableBlocks = statFs.availableBlocks.toLong()
            totalBlocks = statFs.blockCount.toLong()
        }
        when (infoType) {
            "totalInternal" -> memoryInfo = humanReadableByteCountSI(totalBlocks * blockSize)
            "freeInternal" -> memoryInfo = humanReadableByteCountSI(availableBlocks * blockSize)
        }
        return memoryInfo
    }

    @SuppressLint("DefaultLocale")
    private fun humanReadableByteCountSI(bytes: Byte): String {
        val s = if (bytes < 0) "-" else ""
        var b = if (bytes == MIN_VALUE) MAX_VALUE else Math.abs(bytes.toLong())
        return if (b < 1000L) "$bytes B" else if (b < 999950L) String.format("%s%.1f kB", s, b / 1e3) else if (1000.let { b /= it; b } < 999950L) String.format("%s%.1f MB", s, b / 1e3) else if (1000.let { b /= it; b } < 999950L) String.format("%s%.1f GB", s, b / 1e3) else if (1000.let { b /= it; b } < 999950L) String.format("%s%.1f TB", s, b / 1e3) else if (1000.let { b /= it; b } < 999950L) String.format("%s%.1f PB", s, b / 1e3) else String.format("%s%.1f EB", s, b / 1e6)
    }*/



}

