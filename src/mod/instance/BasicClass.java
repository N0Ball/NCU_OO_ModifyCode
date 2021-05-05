package mod.instance;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;

import javax.swing.JPanel;

import bgWork.handler.CanvasPanelHandler;
import mod.IClassPainter;
import mod.IFuncComponent;

public class BasicClass extends JPanel implements IFuncComponent, IClassPainter
{
	Vector <String>		texts			= new Vector <>();
	Dimension			defSize			= new Dimension(150, 25);
	int					maxLength		= 20;
	int					textShiftX		= 5;
	boolean				isSelect		= false;
	int					selectBoxSize	= 5;
	CanvasPanelHandler	cph;

	public BasicClass(CanvasPanelHandler cph)
	{
		texts.add("New Class");
		texts.add("<empty>");
		reSize();
		this.setVisible(true);
		this.setLocation(0, 0);
		this.setOpaque(true);
		this.cph = cph;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		reSize();
		for (int i = 0; i < texts.size(); i ++)
		{
			g.setColor(Color.WHITE);
			g.fillRect(0, (int) (0 + i * defSize.getHeight()),
					(int) defSize.getWidth() - 1, (int) defSize.height - 1);
			g.setColor(Color.BLACK);
			g.drawRect(0, (int) (0 + i * defSize.getHeight()),
					(int) defSize.getWidth() - 1, (int) defSize.height - 1);
			if (texts.elementAt(i).length() > maxLength)
			{
				g.drawString(texts.elementAt(i).substring(0, maxLength) + "...",
						textShiftX,
						(int) (0 + (i + 0.8) * defSize.getHeight()));
			}
			else
			{
				g.drawString(texts.elementAt(i), textShiftX,
						(int) (0 + (i + 0.8) * defSize.getHeight()));
			}
		}
		if (isSelect == true)
		{
			paintSelect(g);
		}
	}

	@Override
	public void reSize()
	{
		switch (texts.size())
		{
			case 0:
				this.setSize(defSize);
				break;
			default:
				this.setSize(defSize.width, defSize.height * texts.size());
				break;
		}
	}

	@Override
	public void setText(String text)
	{
		texts.clear();
		texts.add(text);
		texts.add("<empty>");
		this.repaint();
	}

	public void addText(String text)
	{
		texts.add(text);
		this.repaint();
	}

	public void removeText(int index)
	{
		if (index < texts.size() && index >= 0)
		{
			texts.remove(index);
			this.repaint();
		}
	}

	public boolean isSelect()
	{
		return isSelect;
	}

	public void setSelect(boolean isSelect)
	{
		System.out.println(isSelect);
		this.isSelect = isSelect;
	}

	public int getPort(Point pt)
	{
		System.out.println(pt.x);
		System.out.println(pt.y);
		System.out.println(this.getLocation().x + this.getWidth() / 2 - selectBoxSize);
		System.out.println(this.getLocation().y);

		// Top
		if (this.getLocation().x + this.getWidth() / 2 - selectBoxSize < pt.x && this.getLocation().x + this.getWidth() / 2 + selectBoxSize > pt.x)
		{
			if (this.getLocation().y < pt.y && this.getLocation().y + selectBoxSize > pt.y)
			{
				System.out.println("Select Top");
				return 3;
			}
		}

		// Left
		if (this.getLocation().x < pt.x && this.getLocation().x + selectBoxSize > pt.x)
		{
			if (this.getLocation().y + this.getHeight() / 2 - selectBoxSize < pt.y && this.getLocation().y + this.getHeight() / 2 + selectBoxSize  > pt.y)
			{
				System.out.println("Select Left");
				return 1;
			}
		}

		// Right
		if (this.getLocation().x + this.getWidth() - selectBoxSize < pt.x && this.getLocation().x + this.getWidth() > pt.x)
		{
			if (this.getLocation().y + this.getHeight() / 2 - selectBoxSize < pt.y && this.getLocation().y + this.getHeight() / 2 + selectBoxSize  > pt.y)
			{
				System.out.println("Select Right");
				return 2;
			}
		}

		// Bottom
		if (this.getLocation().x + this.getWidth() / 2 - selectBoxSize < pt.x && this.getLocation().x + this.getWidth() / 2 + selectBoxSize > pt.x)
		{
			if (this.getLocation().y + this.getHeight() - selectBoxSize < pt.y && this.getLocation().y + this.getHeight() > pt.y)
			{
				System.out.println("Select Bottom");
				return 0;
			}
		}

		return -1;
	}

	@Override
	public void paintSelect(Graphics gra)
	{
		gra.setColor(Color.BLACK);
		gra.fillRect(this.getWidth() / 2 - selectBoxSize, 0, selectBoxSize * 2,
				selectBoxSize);
		gra.fillRect(this.getWidth() / 2 - selectBoxSize,
				this.getHeight() - selectBoxSize, selectBoxSize * 2,
				selectBoxSize);
		gra.fillRect(0, this.getHeight() / 2 - selectBoxSize, selectBoxSize,
				selectBoxSize * 2);
		gra.fillRect(this.getWidth() - selectBoxSize,
				this.getHeight() / 2 - selectBoxSize, selectBoxSize,
				selectBoxSize * 2);
	}
}
