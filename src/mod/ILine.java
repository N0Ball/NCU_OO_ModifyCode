package mod;

import java.awt.Point;

import javax.swing.JPanel;

public interface ILine 
{
    Point getConnectPoint(JPanel jp, int side);
    JPanel getFrom();
    JPanel getTo();
    int getFromSide();
    int getToSide();
    void setSelect(boolean isSelect);
}
