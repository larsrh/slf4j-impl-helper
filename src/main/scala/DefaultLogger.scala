package info.hupel.slf4j

import org.slf4j.Logger
import org.slf4j.helpers.{MessageFormatter, MarkerIgnoringBase}

// Code based on https://github.com/vast-engineering/sbt-slf4j
// See CREDITS for details

object DefaultLogger {

  sealed trait LogLevel
  case object TRACE extends LogLevel
  case object DEBUG extends LogLevel
  case object INFO extends LogLevel
  case object WARN extends LogLevel
  case object ERROR extends LogLevel

}

abstract class DefaultLogger extends MarkerIgnoringBase {

  import DefaultLogger._

  val isErrorEnabled: Boolean = true
  val isWarnEnabled: Boolean = true
  val isInfoEnabled: Boolean = true
  val isDebugEnabled: Boolean = true
  val isTraceEnabled: Boolean = true

  def warn(msg: String): Unit = writeLogMessage(WARN, Some(msg))
  def warn(format: String, arg: scala.Any): Unit = formatAndLog(WARN, format, arg)
  def warn(format: String, arg1: scala.Any, arg2: scala.Any): Unit = formatAndLog(WARN, format, arg1, arg2)
  def warn(format: String, arguments: AnyRef*): Unit = formatAndLog(WARN, format, arguments)
  def warn(msg: String, t: Throwable): Unit = writeLogMessage(WARN, Some(msg), Some(t))

  def error(msg: String): Unit = writeLogMessage(ERROR, Some(msg))
  def error(format: String, arg: scala.Any): Unit = formatAndLog(ERROR, format, arg)
  def error(format: String, arg1: scala.Any, arg2: scala.Any): Unit = formatAndLog(ERROR, format, arg1, arg2)
  def error(format: String, arguments: AnyRef*): Unit = formatAndLog(ERROR, format, arguments)
  def error(msg: String, t: Throwable): Unit = writeLogMessage(ERROR, Some(msg), Some(t))

  def debug(msg: String): Unit = writeLogMessage(DEBUG, Some(msg))
  def debug(format: String, arg: scala.Any): Unit = formatAndLog(DEBUG, format, arg)
  def debug(format: String, arg1: scala.Any, arg2: scala.Any): Unit = formatAndLog(DEBUG, format, arg1, arg2)
  def debug(format: String, arguments: AnyRef*): Unit = formatAndLog(DEBUG, format, arguments)
  def debug(msg: String, t: Throwable): Unit = writeLogMessage(DEBUG, Some(msg), Some(t))

  def trace(msg: String): Unit = writeLogMessage(TRACE, Some(msg))
  def trace(format: String, arg: scala.Any): Unit = formatAndLog(TRACE, format, arg)
  def trace(format: String, arg1: scala.Any, arg2: scala.Any): Unit = formatAndLog(TRACE, format, arg1, arg2)
  def trace(format: String, arguments: AnyRef*): Unit = formatAndLog(TRACE, format, arguments)
  def trace(msg: String, t: Throwable): Unit = writeLogMessage(TRACE, Some(msg), Some(t))

  def info(msg: String): Unit = writeLogMessage(INFO, Some(msg))
  def info(format: String, arg: scala.Any): Unit = formatAndLog(INFO, format, arg)
  def info(format: String, arg1: scala.Any, arg2: scala.Any): Unit = formatAndLog(INFO, format, arg1, arg2)
  def info(format: String, arguments: AnyRef*): Unit = formatAndLog(INFO, format, arguments)
  def info(msg: String, t: Throwable): Unit = writeLogMessage(INFO, Some(msg), Some(t))

  private def formatAndLog(level: LogLevel, format: String, arg: Any) {
    val tuple = MessageFormatter.format(format, arg)
    writeLogMessage(level, Option(tuple.getMessage), Option(tuple.getThrowable))
  }

  private def formatAndLog(level: LogLevel, format: String, arg1: Any, arg2: Any) {
    val tuple = MessageFormatter.format(format, arg1, arg2)
    writeLogMessage(level, Option(tuple.getMessage), Option(tuple.getThrowable))
  }

  private def formatAndLog(level: LogLevel, format: String, arguments: AnyRef*) {
    val tuple = MessageFormatter.arrayFormat(format, arguments.toArray)
    writeLogMessage(level, Option(tuple.getMessage), Option(tuple.getThrowable))
  }

  protected def writeLogMessage(level: LogLevel, message: Option[String], t: Option[Throwable] = None): Unit

}
