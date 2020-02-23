import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_plugin_kotlin/flutter_plugin_kotlin.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'fdjgkfjdfh';
  String _screenSize = 'fdjgkfjdfh';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    String screenSize;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {

      platformVersion = await FlutterPluginKotlin.platformVersion;
      screenSize = await FlutterPluginKotlin.getScreenSize;
      FlutterPluginKotlin.showToast("Vuggy Chuggy");
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
      _screenSize = screenSize;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example Kotlin'),
        ),
        body: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Center(
              child: Text('Running on: $_platformVersion\n'),
            ),
            RaisedButton(
              child: Text('Show Popup'), onPressed: () {

              FlutterPluginKotlin.showAlertDialog();

            },
            ),
            Text("$_screenSize")
          ],
        )
      ),
    );
  }
}
