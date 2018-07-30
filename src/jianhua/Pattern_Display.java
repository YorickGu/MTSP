package jianhua;

import java.awt.*;
import javax.swing.*;

public class Pattern_Display extends JPanel{

	 Test tt1 =new Test();
	 double[][] locate = new double[tt1.N+1][tt1.N+1];
	public Pattern_Display() {
		//this.n = locate.length;
		this.locate[0][0] = 35;
		this.locate[0][1] = 39;
		for(int i =0;i<tt1.N;i++) {
			for(int j=0;j<2;j++) {
				this.locate[i+1][j] =tt1.locate[i][j];
			}
		}
//		for(int i=0;i<route.length;i++) {
//			this.route[i] = route[i];
//		}
	}
	
	public void paint(Graphics g) {
		
		super.paint(g);
		
		String[] str = new String[tt1.N]; 
		for(int i=0;i<tt1.N;i++) {
			str[i] = String.valueOf(i);
			g.drawString(str[i], (int)(this.locate[i][0])*10+2, (int)(this.locate[i][1])*10+2);
		}
		
		for(int i=0;i<70;i++) {
		  g.drawLine((int)(this.locate[tt1.route[i]][0])*10, (int)(this.locate[tt1.route[i]][1])*10,(int)(this.locate[tt1.route[i+1]][0])*10, (int)(this.locate[tt1.route[i+1]][1])*10);	
		}
		//g.drawPolyline(xPoints, yPoints, nPoints);
		
	}
	
}
