class Controller {
    var isCompleted = false
    suspend fun suspendHere(): String = suspendWithCurrentContinuation { x ->
        x.resume("OK")
        Suspend
    }

    operator fun handleResult(x: Unit, y: Continuation<Nothing>) {
        isCompleted = true
    }

    // INTERCEPT_RESUME_PLACEHOLDER
}

fun builder(coroutine c: Controller.() -> Continuation<Unit>) {
    val controller = Controller()
    c(controller).resume(Unit)
    if (!controller.isCompleted) throw RuntimeException("fail")
}

fun box(): String {
    builder {
        "OK"
    }

    builder {
        suspendHere()
    }

    return "OK"
}