# CRDTestExpectation-Android

[![Release](https://jitpack.io/v/cdisdero/CRDTestExpectation-Android.svg)](https://jitpack.io/#cdisdero/CRDTestExpectation-Android)

Android library for unit testing asynchronous tasks with a test expectation object that causes a test to wait until the expectation either times out or is fulfilled with a status and additional information, if any.

- [Overview](#overview)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Conclusion](#conclusion)
- [License](#license)

## Overview
When you are testing asynchronous tasks, such as loading a url in a WebView, you need to make the test script wait until some event has occurred and then be able to signal the script so it will continue execution.  CRDTestExpectation is a simple Android library that helps you accomplish this.

## Requirements
- Android API 16 or higher
- Android Studio 2.3+
- Java 1.7+

## Installation
You can simply copy the following files from the GitHub tree into your app project:

  * `CRDTestExpectation.java`
    - Class to provide a test expectation which causes a test script to wait until the expectation either times out or is fulfilled with a status and additional information, if any.

  * `CRDTestExpectationStatus.java`
    - Enumeration representing the various status codes that a {@link CRDTestExpectation} can have when notified.

### JitPack
Alternatively, you can install it via [JitPack.io](https://jitpack.io/#cdisdero/CRDTestExpectation-Android)

To integrate CRDTestExpectation into your Android Studio app project, add the following to your root build.gradle at the end of repositories:

```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Then, add this dependency to your app build.gradle file:

```
	dependencies {
		compile 'com.github.cdisdero:CRDTestExpectation-Android:1.0.0'
	}
```

## Usage
The library is easy to use.  Create an instance of CRDTestExpectation where ever you need to wait for a task in a test script:

```
CRDTestExpectation expectation = new CRDTestExpectation();
```

Below your asynchronous task with callback, wait for the expectation to be fulfilled:

```
CRDTestExpectationStatus status = expectation.waitFor(5000);
```

Here we wait for up to 5 seconds (5000 ms) before continuing.  If we fulfill the expectation before the timeout specified, we get the status we set when we fulfill the expectation.

In the callback method for the asynchronous task, fulfill the expectation, for example:

```
...
@Override
public void onPageFinished(WebView view, String url) {

    super.onPageFinished(view, url);

    // Finished loading the page, fulfill with success status.
    expectation.fulfill(CRDTestExpectationStatus.SUCCESS);
}
...
```

In this example, the status returned will be `SUCCESS`:

```
CRDTestExpectationStatus status = expectation.waitFor(5000);
assertEquals(CRDTestExpecationStatus.SUCCESS, status);
```

If the `onPageFinished` callback method is never called for some reason, then the expectation will time out in 5 seconds and the status returned will be `TIMEOUT`.

You can also pass additional information back from the expectation by adding key-value pairs to the expectation.  As an example, when testing loading a web page in a WebView, you might get an error:

```
...
@Override
public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

    super.onReceivedError(view, request, error);

    // Error occurred, fulfill with failure status and the error message.
    expectation.put("error", error);
    expectation.fulfill(CRDTestExpectationStatus.FAILURE);
}
...
```

Here we pass back the `error` object from the callback so that when `waitFor` is signalled, we can get the value of the error:

```
CRDTestExpectationStatus status = expectation.waitFor(5000);
assertEquals(expectation.get("error") == null ? "unexpected status" : "error " + expectation.get("error"), CRDTestExpectationStatus.SUCCESS, status);
```

## Conclusion
I hope this small library is helpful to you in your next Android project.  I'll be updating as time and inclination permits and of course I welcome all your feedback.

## License
CRDTestExpectation is released under an Apache 2.0 license. See LICENSE for details.
