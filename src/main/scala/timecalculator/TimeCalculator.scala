package timecalculator

import scala.scalajs.js
import org.scalajs.dom
import scala.scalajs.js.annotation.JSExportTopLevel
import org.scalajs.dom.html

object TimeCalculator {
  // Retrieve the inputs elements
  val inputRequiredTime: html.Input = dom.document.getElementById("requiredTime").asInstanceOf[html.Input]
  val inputStartTime: html.Input = dom.document.getElementById("startTime").asInstanceOf[html.Input]
  val inputBreakStart: html.Input = dom.document.getElementById("breakStart").asInstanceOf[html.Input]
  val inputBreakEnd: html.Input = dom.document.getElementById("breakEnd").asInstanceOf[html.Input]
  val outputEndTime: html.Input = dom.document.getElementById("endTime").asInstanceOf[html.Input]
  val inputs: Array[html.Input] = Array(inputRequiredTime, inputStartTime, inputBreakStart, inputBreakEnd)

  // Variables of time constants
  val minutesInHour: Int = 60
  val hoursInDay: Int = 24
  val minutesInDay: Int = hoursInDay * minutesInHour

  /**
    * Initiate the time calculator
    */
  def initTimeCalculator(): Unit = {
    dom.window.onload = { (e: dom.Event) =>
      // Add the input event on inputs
      inputs.foreach(_.addEventListener("input", e => calculateEndTime()))
    }
  }

  /**
    * Convert a time ([HH:mm]) in minutes (Int)
    *
    * @param time The array of hours and minutes
    * @return The conversion of the time in minutes 
    */
  def timeToMinutes(time: Array[Int]): Int = {
    time(0).toInt * minutesInHour + time(1).toInt
  }

  /**
    * Retrieve the value of the input 
    * and format it to the app time format ([HH:mm])
    *
    * @param input The input containing the value
    * @return The input value in the app time format
    */
  def getValueInTimeFormat(input: html.Input): Array[Int] = {
    var stringTime: Array[String] = input.value.split(':')
    Array(stringTime(0).toInt, stringTime(1).toInt)
  }

  /**
    * Checks if the value of the given input is set
    *
    * @param input The input to check its value
    * @return If it's set or not
    */
  def isValueSet(input: html.Input): Boolean = {
    input.value.equals("")
  }

  /**
    * Calculate the end time, but only if all the inputs are set
    */
  def calculateEndTime(): Unit = {
    // If all the inputs have a value, proceed to calculations
    if (!isValueSet(inputRequiredTime)
      && !isValueSet(inputStartTime)
      && !isValueSet(inputBreakStart)
      && !isValueSet(inputBreakEnd)) {
        // Get the inputs values and separated hours from minutes
        val timeRequired: Array[Int] = getValueInTimeFormat(inputRequiredTime)
        val timeStart: Array[Int] = getValueInTimeFormat(inputStartTime)
        val timeBreakStart: Array[Int] = getValueInTimeFormat(inputBreakStart)
        val timeBreakEnd: Array[Int] = getValueInTimeFormat(inputBreakEnd)

        // Convert values in minutes
        val minutesRequired: Int = timeToMinutes(timeRequired)
        val minutesStart: Int = timeToMinutes(timeStart)
        var minutesBreakStart: Int = timeToMinutes(timeBreakStart)
        val minutesBreakEnd: Int = timeToMinutes(timeBreakEnd)

        // If the break start time is the next day
        // Example: 23h30 -> 02h30
        if (minutesStart > minutesBreakStart) {
          minutesBreakStart += minutesInDay
        }

        val minutesWorked: Int = minutesBreakStart - minutesStart

        var minutesEnd: Int = 0

        // If the time done is equal or over, 
        // there is no need to calculate using BreakEnd
        if (minutesWorked >= minutesRequired) {
          minutesEnd = minutesStart + minutesRequired
        } else {
          var minutesRemaining: Int = minutesRequired - minutesWorked
          minutesEnd = minutesBreakEnd + minutesRemaining
        }

        // Convert to clock hours
        while (minutesEnd > minutesInDay) {
          minutesEnd -= minutesInDay
        }

        // Display the end time in the dedicated element
        val timeEnd: String = f"${minutesEnd / minutesInHour}%02d : ${minutesEnd % minutesInHour}%02d"
        outputEndTime.innerText = timeEnd
    }
  }
}
