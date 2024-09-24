package timecalculator

import scala.scalajs.js
import org.scalajs.dom

@main
def TimeCalculator(): Unit = {
    dom.document.querySelector("#app").innerHTML = """
        <h1>Hello ScalaJS!</h1>
        <a href="https://vitejs.dev/guide/features.html" target="_blank">Documentation</a>
    """
}