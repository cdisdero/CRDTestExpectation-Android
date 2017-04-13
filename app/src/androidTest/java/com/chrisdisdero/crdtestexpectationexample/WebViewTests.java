package com.chrisdisdero.crdtestexpectationexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.test.runner.AndroidJUnit4;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chrisdisdero.crdtestexpectation.CRDTestExpectation;
import com.chrisdisdero.crdtestexpectation.CRDTestExpectationStatus;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

/**
 * Example instrumentation test for a {@link WebView} that is instantiated within a test.
 *
 Copyright © 2017 Christopher Disdero.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
@RunWith(AndroidJUnit4.class)
public class WebViewTests {

    //region Private members

    /**
     * Private reference to the instance of a {@link WebView} object to test.
     */
    private WebView webView = null;

    //endregion

    //region Tests

    /**
     * Example test of loading a web page in a {@link WebView}.
     *
     * @throws Exception
     */
    @Test
    public void testLoadWebPage() throws Exception {

        // We create a test expectation which causes the test to wait until the expectation is fulfilled with success or failure.
        final CRDTestExpectation expectation = new CRDTestExpectation();

        // Get the application context.
        final Context applicationContext = MyApplication.shared().getApplicationContext();

        // It's important to run the code to create the WebView, WebViewClient, and load the web page in the main UI thread.
        Handler mainHandler = new Handler(applicationContext.getMainLooper());
        Runnable myRunnable = new Runnable() {

            @Override
            public void run() {

                // Create an instance of a WebView based on the application context.
                webView = new WebView(applicationContext);

                // Create a WebViewClient that we can use to test the progress of loading a web page.
                WebViewClient webViewClient = new WebViewClient() {

                    @Override
                    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

                        super.onReceivedError(view, request, error);

                        // Error occurred, fulfill with failure status and the error message.
                        expectation.put("error", error);
                        expectation.fulfill(CRDTestExpectationStatus.FAILURE);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {

                        super.onPageFinished(view, url);

                        // Finished loading the page, fulfill with success status.
                        expectation.fulfill(CRDTestExpectationStatus.SUCCESS);
                    }

                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {

                        super.onPageStarted(view, url, favicon);
                    }
                };

                // Set the WebView to use the WebViewClient we just created.
                webView.setWebViewClient(webViewClient);

                // Load a web page to test.
                webView.loadUrl("http://code.chrisdisdero.com");
            }
        };
        mainHandler.post(myRunnable);

        // Wait up to 5 seconds for the test expectation to be fulfilled before continuing with the test script.
        CRDTestExpectationStatus status = expectation.waitFor(5000);

        // Check to make sure we were fulfilled with success status, otherwise report the error.
        assertEquals(expectation.get("error") == null ? "unexpected status" : "error " + expectation.get("error"), CRDTestExpectationStatus.SUCCESS, status);
    }

    //endregion
}
