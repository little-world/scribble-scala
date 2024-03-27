import scala.swing.*
import scala.swing.event.*
import java.awt.{Color, Graphics2D, Point, Rectangle, Shape}
import scala.collection.mutable.ListBuffer
import scala.swing.BorderPanel.Position.{Center, North}

object ScribbleApp extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "Rectangle and Oval Scribble App"

    // Modes for drawing shapes
    sealed trait DrawMode
    case object Rectangle extends DrawMode
    case object Oval extends DrawMode

    var currentMode: DrawMode = Rectangle // Default mode

    // Canvas is a Panel where shapes are drawn
    object canvas extends Panel {
      background = Color.white
      preferredSize = new Dimension(400, 400)
      focusable = true
      listenTo(mouse.clicks, mouse.moves)

      private var startPoint: Option[Point] = None
      private val shapes: ListBuffer[Shape] = ListBuffer()

      // React to mouse events to draw shapes
      reactions += {
        case e: MousePressed =>
          startPoint = Some(e.point)

        case e: MouseReleased =>
          startPoint match {
            case Some(start) =>
              val shape = currentMode match {
                case Rectangle => new Rectangle(start.x, start.y, e.point.x - start.x, e.point.y - start.y)
                case Oval => new java.awt.geom.Ellipse2D.Double(start.x, start.y, e.point.x - start.x, e.point.y - start.y)
              }
              shapes += shape
              repaint()
            case None =>
          }
          startPoint = None
      }

      override def paintComponent(g: Graphics2D): Unit = {
        super.paintComponent(g)
        g.setColor(Color.black)
        shapes.foreach {
          case rect: Rectangle => g.draw(rect)
          case oval: java.awt.geom.Ellipse2D.Double => g.draw(oval)
          case _ =>
        }
      }
    }

    // Mode selection buttons
    val modePanel = new FlowPanel {
      val rectangleButton = new Button("Rectangle")
      val ovalButton = new Button("Oval")

      contents += rectangleButton
      contents += ovalButton

      listenTo(rectangleButton, ovalButton)

      reactions += {
        case ButtonClicked(`rectangleButton`) => currentMode = Rectangle
        case ButtonClicked(`ovalButton`) => currentMode = Oval
      }
    }

    contents = new BorderPanel {
      layout(modePanel) = North
      layout(canvas) = Center
    }
    size = new Dimension(500, 500)
  }
}
