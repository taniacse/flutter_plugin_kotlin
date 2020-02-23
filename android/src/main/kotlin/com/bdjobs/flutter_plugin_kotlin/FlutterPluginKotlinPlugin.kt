package com.bdjobs.flutter_plugin_kotlin


import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.widget.Toast


import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar


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
            Log.d("TTTT", "getPlatformVersion called ")
            result.success("Android ${android.os.Build.VERSION.RELEASE}")
        } else if (call.method == "showToast") {
            val msg = call.argument<Any>("msg").toString()
            Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
        } else if (call.method == "showAlertDialog") {

            Log.d("TTTT", "showAlertDialog called ")

            val dialog = Dialog(activity!!)
            dialog.setTitle("Hi, My Name is Flutter")
            dialog.show()
        } else {
            Log.d("TTTT", "result.notImplemented() called ")
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

}
