package jianhua;

import static java.lang.Math.pow;
//import static java.lang.Math.random;
import static java.lang.Math.sqrt;

import javax.swing.JFrame;

public class Test extends JFrame{
	public static double[][] locate = {{8375,4700},{8775,4700},{8375,4900},{8175,4900},{8775,4900},{8575,4900},{8775,5400},{8375,5450},
			{8775,5600},{8575,5600},{8375,5650},{8175,5650},{8375,6200},{8775,6200},{8375,6400},{8175,6400},
			{8775,6400},{8575,6400},{8375,7000},{8775,7000},{8375,7200},{8175,7200},{8775,7200},{8575,7200},
			{8375,7800},{8775,7800},{8375,8000},{8175,8000},{8775,8000},{8575,8000},{8375,8700},{8775,8700},
			{8375,8900},{8175,8900},{8775,8900},{8575,8900},{8375,9600},{8775,9600},{8375,9800},{8175,9800},
			{8775,9800},{8575,9800},{8375,10500},{8775,10450},{8375,10700},{8175,10700},{8775,10650},{8575,10650},
			{8375,11300},{8775,11300},{8375,11500},{8175,11500},{8775,11500},{8575,11500},{15825,11500},{15825,10700},
			{15825,9800},{15825,8900},{15825,8000},{15825,7200},{15825,6400},{15825,5650},{15825,4900},{16025,4700},
			{16425,4700},{16025,4900},{16225,4900},{16425,4900},{16425,5400},{16025,5450},{16225,5600},{16425,5600},
			{16025,5650},{16025,6200},{16425,6200},{16025,6400},{16225,6400},{16425,6400},{16025,7000},{16425,7000},
			{16025,7200},{16225,7200},{16425,7200},{16025,7800},{16425,7800},{16025,8000},{16225,8000},{16425,8000},
			{16025,8700},{16425,8700},{16025,8900},{16225,8900},{16425,8900},{16025,9600},{16425,9600},{16025,9800},
			{16225,9800},{16425,9800},{16025,10500},{16425,10450},{16025,10700},{16225,10650},{16425,10650},{16025,11300},
			{16425,11300},{16025,11500},{16225,11500}};
	final static int N=locate.length+1;
	static double[] disself=new double[N];
	static double[][] dis = new double[N][N];  //距离矩阵
	static double[][] S = new double[N][N];	//节省值矩阵
	static double[] e = new double[N*N];    //节省值降序排列
	static int[] route =new int [N*2+1];    
	static int[] x =new int[N];
	static int[] y =new int[N];
	
	Pattern_Display pd = null;
	
	public static void main(String[] args) {
		
		//算每个点到原点的大小
		for(int i=0;i<N-1;i++) {
			disself[i+1]=sqrt(pow((locate[i][0]),2)+pow((locate[i][1]),2));
		}
		
		//算每个点之间的距离
		for(int i=0;i<N-1;i++) {
			for(int j=0;j<N-1;j++) {
				if(i != j) {
					dis[i+1][j+1]= sqrt(pow((locate[i][0]-locate[j][0]),2)+pow((locate[i][1]-locate[j][1]),2));
				}
			}
		}
		
		//计算节约值的大小
		for(int i=1;i<N;i++) {
			for(int j=1;j<N;j++) {
				if(i!=j) {
				S[i][j] = disself[i]+disself[j]-dis[i][j];  //计算节约值的大小，并且由于节约值的大小是呈现一个对称的矩阵形式的
				//S[j][i] = S[i][j];
				}
			}
		}
		
		while(CheckS(S)) {
		int[] max =findmax(S);
		
		//如果两个点都不在构成的线路上
		if((!InTheQueue(max[0],route))&&(!InTheQueue(max[1],route))) {
			GenerQueue(max[0],max[1],route);
			ClearTheQueue(max[0],max[1],S);
			continue;
		}
		
		//如果任意有一个点在构成的线路上
		if(InTheQueue(max[0],route)||InTheQueue(max[1],route)) {
			int contx =0; 
			int conty =0;
			//如果两个点中横坐标的点在线路上
			if((InTheQueue(max[0],route))&&(!InTheQueue(max[1],route))) {
				contx = findindex(max[0],route);
				if(route[contx+1]==0) {
					InsertXQueue(contx,max[0],max[1],route);
					ClearTheQueue(max[0],max[1],S);
					continue;
				}
			}
			//如果纵坐标的点在线路上
			if((!InTheQueue(max[0],route))&&(InTheQueue(max[1],route))) {
				conty = findindex(max[1],route);
				if(route[conty-1]==0) {
					InsertYQueue(conty,max[0],max[1],route);
					ClearTheQueue(max[0],max[1],S);
					continue;
				}
			}
			//如果两个点都在线路上
			if((InTheQueue(max[0],route))&&(InTheQueue(max[1],route))) {
				contx = findindex(max[0],route);
				conty = findindex(max[1],route);
				if(exitzero(contx,conty,route)) {

				if((route[contx+1]==0)&&(route[conty-1]==0)) {
					if(contx<conty) {
						linkqueue(contx,conty,route);
						ClearTheQueue(max[0],max[1],S);
						continue;
					}
					if(conty<contx) {
						linkqueue1(contx,conty,route);
						ClearTheQueue(max[0],max[1],S);
						continue;
					}
				}
			}
				ClearTheQueue(max[0],max[1],S);
				continue;

			}
			ClearTheQueue(max[0],max[1],S);
			continue;
		}
			
		}
		
		for(int i=0;i<2*N+1;i++) {
			//if(!((route[i]==0)&&(route[i+1]==0)))
			System.out.print(route[i]+"→");
			if((route[i]==0)&&(route[i+1]==0)) {
				break;
			}
		}
		
		
		//绘制图形界面
		System.out.println();
		Test tt =new Test();
	}
	
	public Test() {
		pd = new Pattern_Display();
		this.add(pd);
		this.setSize(1366, 730);
		//this.setLocationRelativeTo(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);   //最大化 
		this.setResizable(false);         //不能改变大小 
		this.setUndecorated(true); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static int[] findmax(double[][] S) {
		int[] max =new int[2];
		int k=0;
		double temp = S[k+1][k+1];
		for(int i =1;i<N;i++) {
			for(int j =1;j<N;j++) {
				if(temp<S[i][j]) {
					temp = S[i][j];
					max[k] = i;
					max[k+1] = j;
				}
			}
		}
		return max;
	}
	
	public static boolean InTheQueue(int x,int[] route) {
		int cont=1;
		while((route[cont]>0)||route[cont+1]>0) {
			if(route[cont]==x) {
				return true;
			}
			cont++;
		}
		
		return false;
	}
	
	public  static void GenerQueue(int x,int y,int[] route) {
		int cont = 0;
		while((route[cont]>0)||(route[cont+1]>0)) {
			cont++;
		}
		
		route[cont+1] = x;
		route[cont+2] = y;
		
	}
	
	public static void ClearTheQueue(int x,int y,double[][] S) {
		
		for(int j=0;j<N;j++) {
			S[x][j] = 0;
		}
		for(int i=0;i<N;i++) {
			S[i][y] = 0;
		}
		S[y][x] = 0;
	}
	
	public static boolean CheckS(double[][] S) {
	
		for(int i=1;i<N;i++) {
			for(int j=1;j<N;j++) {
				if(S[i][j]!=0) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static void InsertXQueue(int a,int x,int y,int[] route) {
		for(int i=route.length-2;i>a;i--) {
			route[i+1] = route[i];
		}
		route[a+1] = y;
	}
	
	public static void InsertYQueue(int b,int x,int y,int[]route) {
		for(int i=route.length-2;i>b-1;i--) {
			route[i+1] = route[i];
		}
		route[b] = x;
	}
	
	public static int findindex(int x,int[] route) {
		int cont = 0;
		while(route[cont]!=x) {
			cont++;
		}
		return cont;
	}
	
	public static void linkqueue(int contx,int conty,int[] route){
		int[] route1 = new int[2*N+1];
		System.arraycopy(route, 0, route1, 0, route.length);
		int x=contx;
		int y=conty;
		while(route1[x]>0) {
			x--;
		}
		while(route1[y]>0) {
			y++;
		}
		System.arraycopy(route1, x, route, 0, contx-x+1);
		System.arraycopy(route1, conty, route, contx-x+1, y-conty);
		System.arraycopy(route1, 0, route, contx-x+1+y-conty, x);
		System.arraycopy(route1, contx+1, route, contx+y-conty, conty-contx-1);
		System.arraycopy(route1, y+1, route, y, route.length-1-y);
	}
	
	public static void linkqueue1(int contx,int conty,int[] route) {
		int[] route1 = new int[2*N+1];
		System.arraycopy(route, 0, route1, 0, route.length);
		int x=contx;
		int y=conty;
		while(route1[x]>0) {
			x--;
		}
		while(route1[y]>0) {
			y++;
		}
	
		System.arraycopy(route1, x, route, 0, contx-x+1);
		System.arraycopy(route1, conty, route, contx-x+1, y-conty);
		System.arraycopy(route1, 0, route, contx-x+1+y-conty, conty);
		System.arraycopy(route1, y, route, contx-x+y, y-x);
		System.arraycopy(route1, contx+1, route, contx, route.length-1-contx);
		
	}
	
	public static boolean exitzero(int x,int y,int[] route) {
		int k=0;
		if(x>y) {
			k = x;
			x = y;
			y = k;
		}
		for(int i = x+1;i<y;i++) {
			if(route[i]==0) {
				return true;
			}
		}
		return false;
	}

}
