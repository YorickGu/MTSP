package MTSP;

import java.awt.*;
import javax.swing.*;

public class Pattern_Display extends JPanel{
	 int n;
	// Test tt1 = new Test();
	 final static int N =109;
	 double[][] locate =new double[N][N];
	 int[] route = new int[2*N+1];
	 
	public Pattern_Display(double[][] locate,int[] route) {
		this.n = locate.length;
		this.locate[0][0] = 35;
		this.locate[0][1] = 39;
		for(int i =0;i<n;i++) {
			for(int j=0;j<2;j++) {
				this.locate[i+1][j] =locate[i][j];
			}
		}
		for(int i=0;i<route.length;i++) {
			this.route[i] = route[i];
		}
	}
	
	public void paint(Graphics g) {
		
		super.paint(g);
		
		String[] str = new String[N]; 
		for(int i=0;i<N;i++) {
			str[i] = String.valueOf(i);
			g.drawString(str[i], (int)(this.locate[i][0]*10)+2, (int)(this.locate[i][1]*10)+2);
		}
		
		for(int i=0;i<N;i++) {
		  g.drawLine((int)(this.locate[this.route[i]][0]*10), 
				  (int)(this.locate[this.route[i]][1]*10),
				  (int)(this.locate[this.route[i+1]][0]*10), 
				  (int)(this.locate[this.route[i+1]][1]*10));	
		}
		//g.drawPolyline(xPoints, yPoints, nPoints);
		
	}
	
}
