import 'dart:async';

import 'package:flutter/services.dart';

class FlutterPluginKotlin {
  static const MethodChannel _channel = const MethodChannel('flutter_plugin_kotlin');

  /*static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }*/

  static Future<String> get platformVersion async{

    print('platformVersion called');
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<Null> showToast(String msg) async {
    Map<String, dynamic> args = <String, dynamic>{};
    args.putIfAbsent("msg", () => msg);
    await _channel.invokeMethod('showToast', args);
    return null;
  }

  static  get showAlertDialog async {
    await _channel.invokeMethod('showAlertDialog');
  }

}
