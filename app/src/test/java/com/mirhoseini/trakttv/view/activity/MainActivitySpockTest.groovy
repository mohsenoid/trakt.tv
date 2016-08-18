package com.mirhoseini.trakttv.view.activity

import android.widget.TextView
import org.robolectric.RuntimeEnvironment
import org.robospock.GradleRoboSpecification

/**
 * Created by Mohsen on 05/08/16.
 */
class MainActivitySpockTest extends GradleRoboSpecification{

    def "should display hello text"() {
        given:
        def textView = new TextView(RuntimeEnvironment.application)

        expect:
        textView.text == "Hello"
    }
}
