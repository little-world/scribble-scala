import java.awt.{Color, Graphics2D}
import scala.swing.*
import scala.swing.BorderPanel.Position.{Center, North}
import scala.swing.MenuBar.NoMenuBar.contents
import scala.swing.event.{MousePressed, MouseReleased}

class Rect(var x1: Int, var y1: Int, var x2:Int, var y2: Int) {
  def draw(g: Graphics2D): Unit = {
    g.drawRect(x1, y1, x2 - x1, y2 - y1)
  }
}

object Scribble4 extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "Scribble App"


    object canvas extends Panel {
      listenTo(mouse.clicks)
    
      val rect = Rect(0, 0, 0, 0)

      reactions += {
        case e: MousePressed =>
          rect.x1 = e.point.x
          rect.y1 = e.point.y

        case e: MouseReleased =>
          rect.x2 = e.point.x
          rect.y2 = e.point.y
          repaint()

      }

      override def paintComponent(g: Graphics2D): Unit = {
        super.paintComponent(g)
        rect.draw(g)
      }
    }

    contents = new BorderPanel {
      layout(canvas) = Center
    }
    size = new Dimension(500, 500)
  }
}
