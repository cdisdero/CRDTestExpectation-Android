package com.chrisdisdero.crdtestexpectation;

import java.util.HashMap;

/**
 * Class to provide a test expectation which causes a test script to wait until the expectation either times out or is fulfilled with a status and additional information, if any.
 *
 * @author cdisdero.
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
public class CRDTestExpectation {

    //region Private members

    /**
     * Status of the expectation, default to {@link CRDTestExpectationStatus#TIMEOUT}
     */
    private CRDTestExpectationStatus statusCode = CRDTestExpectationStatus.TIMEOUT;

    /**
     * Additional key-value pairs, if any, that are associated with this expectation.
     */
    private HashMap<String, Object> info = new HashMap<>();

    //endregion

    //region Expectation key-value pairs

    /**
     * Gets a value by a key associated with this expectation.
     *
     * @return The value for a key associated with this expectation, if any.
     */
    public Object get(String key) {

        return info.get(key);
    }

    /**
     * Sets a key-value pair associated with this expectation.
     *
     * @param key The key for the value associated with this expectation.
     * @param value The value for the key.
     */
    public void put(String key, Object value) {

        info.put(key, value);
    }

    //endregion

    //region Waiting

    /**
     * Method to wait for up to the specified number of milliseconds or until expectation is fulfilled {@link #fulfill(CRDTestExpectationStatus)}.
     *
     * @param millisecondsTimeout the milliseconds timeout.  If the timeout is exceeded before the expectation is fulfilled, the expectation is automatically fulfilled with status {@link CRDTestExpectationStatus#TIMEOUT}.
     *
     * @return The {@link CRDTestExpectationStatus} of the expectation.
     *
     * @throws IllegalArgumentException the illegal argument exception
     * @throws InterruptedException     the interrupted exception
     */
    public CRDTestExpectationStatus waitFor(long millisecondsTimeout) throws IllegalArgumentException, InterruptedException {

        synchronized(this){

            this.wait(millisecondsTimeout);
            return statusCode;
        }
    }

    //endregion

    //region Notifying

    /**
     * Method to fulfill an expectation that is waiting with {@link #waitFor(long)} with the given status.
     *
     * @param statusCode The {@link CRDTestExpectationStatus} of the expectation.
     */
    public void fulfill(CRDTestExpectationStatus statusCode) {

        synchronized(this) {

            this.statusCode = statusCode;
            this.notify();
        }
    }

    //endregion
}
