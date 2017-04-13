package com.chrisdisdero.crdtestexpectation;

/**
 * Enumeration representing the various status codes that a {@link CRDTestExpectation} can have when notified.
 *
 * Created by cdisdero on 4/11/17.
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
public enum CRDTestExpectationStatus {

    /**
     * The expectation timed out.
     */
    TIMEOUT,

    /**
     * The expectation succeeded.
     */
    SUCCESS,

    /**
     * The expectation failed.
     */
    FAILURE
}
