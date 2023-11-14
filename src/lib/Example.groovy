package lib

import com.cloudbees.groovy.cps.NonCPS

class Example {
    @NonCPS
    static def classicLoopNonCPS() {
        def messages = []
        messages += "Begin NonCPS"
        for (; ;) {
            messages += "Inside classic loop"
            break
        }
        messages += "End NonCPS"
        return messages
    }

    static def classicLoopCPS() {
        def messages = []
        messages += "Begin CPS"
        for (; ;) {
            messages += "Inside classic loop"
            break
        }
        messages += "End CPS"
        return messages
    }
}
