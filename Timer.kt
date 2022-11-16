object Timer {
    fun measure(body: () -> Unit) {
        val before = System.currentTimeMillis()
        body()
        println("Took " + (System.currentTimeMillis() - before) + " ms")
    }
}
