package timecalculator

import org.scalajs.dom
import scala.scalajs.js.annotation.JSExportTopLevel
import org.scalajs.dom.html
import scala.util.matching.Regex
import timecalculator.TimeCalculator.{getValueInTimeFormat, timeToMinutes}

object TimeConverter {
    val regexTime: Regex = "([0-9]+):[0-5][0-9]".r

    val inputHours: html.Input = dom.document.getElementById("timeToConvert").asInstanceOf[html.Input]
    val inputRequiredTime: html.Input = dom.document.getElementById("requiredTime").asInstanceOf[html.Input]

    val outputDays: html.Paragraph = dom.document.getElementById("timeConverted").asInstanceOf[html.Paragraph]

    def initTimeConverter(): Unit = {
        inputHours.addEventListener("input", e => calculateConversion())
        inputRequiredTime.addEventListener("input", e => calculateConversion())
    }

    def calculateConversion(): Unit = {
        if (regexTime.matches(inputHours.value)) {
            val minutesRequired: Int = timeToMinutes(getValueInTimeFormat(inputRequiredTime))
            val minutesTime: Int = timeToMinutes(getValueInTimeFormat(inputHours))

            val nbDays: Double = minutesTime.toDouble / minutesRequired.toDouble
            outputDays.innerText = f"$nbDays%.2f"
        }
    }
}
