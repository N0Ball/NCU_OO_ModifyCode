package mod.instance;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.JPanel;

import Define.AreaDefine;
import Pack.DragPack;
import bgWork.handler.CanvasPanelHandler;
import mod.IFuncComponent;
import mod.ILine;
import mod.ILinePainter;
import java.lang.Math;

public class DependencyLine extends JPanel
		implements IFuncComponent, ILinePainter, ILine
{
	JPanel				from;
	int					fromSide;
	Point				fp				= new Point(0, 0);
	JPanel				to;
	int					toSide;
	Point				tp				= new Point(0, 0);
	int					arrowSize		= 6;
	boolean				isSelect		= false;
	int					selectBoxSize	= 5;
	CanvasPanelHandler	cph;

	public DependencyLine(CanvasPanelHandler cph)
	{
		this.setOpaque(false);
		this.setVisible(true);
		this.setMinimumSize(new Dimension(1, 1));
		this.cph = cph;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		Point fpPrime;
		Point tpPrime;
		renewConnect();
		fpPrime = new Point(fp.x - this.getLocation().x,
				fp.y - this.getLocation().y);
		tpPrime = new Point(tp.x - this.getLocation().x,
				tp.y - this.getLocation().y);
		paintArrow(g, tpPrime);
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
				0, new float[]{9}, 0);
		g2d.setStroke(dashed);
		g2d.drawLine(fpPrime.x, fpPrime.y, tpPrime.x, tpPrime.y);
		if (isSelect == true)
		{
			g.setColor(Color.RED);
			g.drawLine(fpPrime.x, fpPrime.y, tpPrime.x, tpPrime.y);
			// paintSelect(g);
		}
		g2d.dispose();

	}

	@Override
	public void reSize()
	{
		Dimension size = new Dimension(Math.abs(fp.x - tp.x) + 10,
				Math.abs(fp.y - tp.y) + 10);
		this.setSize(size);
		this.setLocation(Math.min(fp.x, tp.x) - 5, Math.min(fp.y, tp.y) - 5);
	}

	@Override
	public void paintArrow(Graphics g, Point point)
	{
		int x[] =
		{point.x, point.x - arrowSize, point.x, point.x + arrowSize};
		int y[] =
		{point.y + arrowSize, point.y, point.y - arrowSize, point.y};
		switch (toSide)
		{
			case 0:
				x = removeAt(x, 0);
				y = removeAt(y, 0);
				break;
			case 1:
				x = removeAt(x, 1);
				y = removeAt(y, 1);
				break;
			case 2:
				x = removeAt(x, 3);
				y = removeAt(y, 3);
				break;
			case 3:
				x = removeAt(x, 2);
				y = removeAt(y, 2);
				break;
			default:
				break;
		}
		Polygon polygon = new Polygon(x, y, x.length);
		g.setColor(Color.WHITE);
		g.fillPolygon(polygon);
		g.setColor(Color.BLACK);
		g.drawPolygon(polygon);
	}

	@Override
	public void setConnect(DragPack dPack)
	{
		Point mfp = dPack.getFrom();
		Point mtp = dPack.getTo();
		from = (JPanel) dPack.getFromObj();
		to = (JPanel) dPack.getToObj();
		fromSide = new AreaDefine().getArea(from.getLocation(), from.getSize(),
				mfp);
		toSide = new AreaDefine().getArea(to.getLocation(), to.getSize(), mtp);
		renewConnect();
		System.out.println("from side " + fromSide);
		System.out.println("to side " + toSide);
	}

	void renewConnect()
	{
		try
		{
			fp = getConnectPoint(from, fromSide);
			tp = getConnectPoint(to, toSide);
			this.reSize();
		}
		catch (NullPointerException e)
		{
			this.setVisible(false);
			cph.removeComponent(this);
		}
	}

	public Point getConnectPoint(JPanel jp, int side)
	{
		Point temp = new Point(0, 0);
		Point jpLocation = cph.getAbsLocation(jp);
		if (side == new AreaDefine().TOP)
		{
			temp.x = (int) (jpLocation.x + jp.getSize().getWidth() / 2);
			temp.y = jpLocation.y;
		}
		else if (side == new AreaDefine().RIGHT)
		{
			temp.x = (int) (jpLocation.x + jp.getSize().getWidth());
			temp.y = (int) (jpLocation.y + jp.getSize().getHeight() / 2);
		}
		else if (side == new AreaDefine().LEFT)
		{
			temp.x = jpLocation.x;
			temp.y = (int) (jpLocation.y + jp.getSize().getHeight() / 2);
		}
		else if (side == new AreaDefine().BOTTOM)
		{
			temp.x = (int) (jpLocation.x + jp.getSize().getWidth() / 2);
			temp.y = (int) (jpLocation.y + jp.getSize().getHeight());
		}
		else
		{
			temp = null;
			System.err.println("getConnectPoint fail:" + side);
		}
		return temp;
	}

	@Override
	public void paintSelect(Graphics gra)
	{
		gra.setColor(Color.BLACK);
		gra.fillRect(fp.x, fp.y, selectBoxSize, selectBoxSize);
		gra.fillRect(tp.x, tp.y, selectBoxSize, selectBoxSize);
	}

	public boolean isSelect()
	{
		return isSelect;
	}

	public void setSelect(boolean isSelect)
	{
		this.isSelect = isSelect;
	}

	int[] removeAt(int arr[], int index)
	{
		int temp[] = new int[arr.length - 1];
		for (int i = 0; i < temp.length; i ++)
		{
			if (i < index)
			{
				temp[i] = arr[i];
			}
			else if (i >= index)
			{
				temp[i] = arr[i + 1];
			}
		}
		return temp;
	}
	
	@Override
	public JPanel getFrom() {
		return from;
	}

	@Override
	public JPanel getTo() {
		return to;
	}

	@Override
	public int getFromSide() {
		return fromSide;
	}

	@Override
	public int getToSide() {
		return toSide;
	}
}