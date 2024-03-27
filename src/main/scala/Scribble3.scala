import java.awt.{Color, Graphics2D}
import scala.swing.BorderPanel.Position.{Center, North}
import scala.swing.MenuBar.NoMenuBar.contents
import scala.swing.*
import scala.swing.event.{MousePressed, MouseReleased}

object Scribble3 extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "Scribble App"


    object canvas extends Panel {
      listenTo(mouse.clicks)
      private var startPoint: Option[Point] = None
      private var endPoint: Option[Point] = None

      reactions += {
        case e: MousePressed =>
          startPoint = Some(e.point)

        case e: MouseReleased =>
          endPoint = Some(e.point)
          repaint()

      }

      override def paintComponent(g: Graphics2D): Unit = {
        super.paintComponent(g)
        g.setColor(Color.black)
        for {
          start <- startPoint
          end <- endPoint
        } yield {
          g.drawRect(start.x, start.y,  end.x-start.x, end.y-start.y)
        }
      }
    }

    contents = new BorderPanel {
      layout(canvas) = Center
    }
    size = new Dimension(500, 500)
  }
}
