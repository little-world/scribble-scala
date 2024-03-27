import java.awt.{Color, Graphics2D}
import scala.swing.BorderPanel.Position.{Center, North}
import scala.swing.MenuBar.NoMenuBar.contents
import scala.swing.{BorderPanel, Dimension, MainFrame, Panel, SimpleSwingApplication}

object Scribble1 extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "Scribble App"


    object canvas extends Panel {
    //  background = Color.white
    //  preferredSize = new Dimension(400, 400)
    //  focusable = true

      override def paintComponent(g: Graphics2D): Unit = {
        super.paintComponent(g)
        g.setColor(Color.black)
        g.drawRect(20, 20, 20, 20)
      }
    }

    contents = new BorderPanel {
      layout(canvas) = Center
    }
    size = new Dimension(500, 500)
  }
}
