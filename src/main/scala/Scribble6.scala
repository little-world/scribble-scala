import java.awt.{Color, Graphics2D}
import scala.collection.mutable.ListBuffer
import scala.swing.*
import scala.swing.BorderPanel.Position.{Center, North}
import scala.swing.event.{MousePressed, MouseReleased}

object Scribble6 extends SimpleSwingApplication {

  trait Shape {
    var x1: Int
    var y1: Int
    var x2: Int
    var y2: Int
    def draw(g: Graphics2D): Unit
  }

  class Rect(var x1: Int, var y1: Int, var x2: Int, var y2: Int) extends Shape {
    def draw(g: Graphics2D): Unit = {
      g.drawRect(x1, y1, x2 - x1, y2 - y1)
    }
  }

  class Oval(var x1: Int, var y1: Int, var x2: Int, var y2: Int) extends Shape {
    def draw(g: Graphics2D): Unit = {
      g.drawOval(x1, y1, x2 - x1, y2 - y1)
    }
  }

  private val shapes: ListBuffer[Shape] = ListBuffer()
  
  def top = new MainFrame {
    title = "Scribble App"


    object canvas extends Panel {
      listenTo(mouse.clicks)

      var shp: Shape = Oval(0, 0, 0, 0)

      reactions += {
        case e: MousePressed =>
          if (e.triggersPopup) 
            shp = Oval(e.point.x,e.point.y, 0, 0 )
          else
            shp = Rect(e.point.x, e.point.y, 0, 0)
        
         shapes.append(shp)
          
        case e: MouseReleased =>
          shp.x2 = e.point.x
          shp.y2 = e.point.y
          repaint()

      }

      override def paintComponent(g: Graphics2D): Unit = {
        super.paintComponent(g)
        shapes.foreach ( shp =>shp.draw(g) )
      }
    }

    contents = new BorderPanel {
      layout(canvas) = Center
    }
    size = new Dimension(500, 500)
  }
}
