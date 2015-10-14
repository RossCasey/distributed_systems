/**
 *  Echo Server Lab
 *  Ross Casey - ***REMOVED***
 */

import java.io._
import scala.io._
import java.net._

class ServerThread(socket: Socket, killServerCallback: () => Unit) extends Runnable {

  var in: Iterator[String] = null
  var out: PrintStream = null

  override def run() {
    in = new BufferedSource(socket.getInputStream()).getLines()
    out = new PrintStream(socket.getOutputStream())

    val message = in.next()

    if (isKillService(message)) handleKillServer()
    else if (isHeloMessage(message)) handleHeloMessage(message)
    else handleDefault(message)

    socket.close()
  }

  def sendToClient(toSend: String): Unit = {
    out.print(toSend)
    out.flush()
  }

  def isKillService(input: String): Boolean = {
    if (input.equals("KILL_SERVICE")) {
      true
    } else {
      false
    }
  }

  def handleKillServer(): Unit = {
    killServerCallback()
  }

  def isHeloMessage(input: String): Boolean = {
    if (input.startsWith("HELO ")) {
      true
    } else {
      false
    }
  }

  def handleHeloMessage(input: String): Unit = {
    val ip = socket.getInetAddress.toString
    val port = socket.getPort.toString
    val id = "***REMOVED***"
    val output = input + "IP:" + ip + "\nPort:" + port + "\nStudentID:" + id +"\n"
    sendToClient(output)
  }

  def handleDefault(input: String): Unit = {
    //do nothing for now
  }
}