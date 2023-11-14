def call() {
    def messages = []
    messages += "Begin"
    for (; ;) {
        messages += "Inside classic loop"
        break
    }
    messages += "End"
    return messages
}
