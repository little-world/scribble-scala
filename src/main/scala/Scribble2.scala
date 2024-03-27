import java.awt.{Color, Graphics2D}
import scala.swing.*
import scala.swing.BorderPanel.Position.{Center, North}
import scala.swing.MenuBar.NoMenuBar.contents
import scala.swing.event.{MousePressed, MouseReleased}

object Scribble2 extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "Scribble App"


    object canvas extends Panel {
      listenTo(mouse.clicks)
      var x1 = 0
      var y1 = 0 
      var x2 = 0
      var y2 = 0


      reactions += {
        case e: MousePressed =>
          x1 = e.point.x
          y1 = e.point.y

        case e: MouseReleased =>
          x2 = e.point.x
          y2 = e.point.y
          repaint()

      }

      override def paintComponent(g: Graphics2D): Unit = {
        super.paintComponent(g)
        g.drawRect(x1, y1, x2 - x1, y2 - y1)
      }
    }

    contents = new BorderPanel {
      layout(canvas) = Center
    }
    size = new Dimension(500, 500)
  }
}
