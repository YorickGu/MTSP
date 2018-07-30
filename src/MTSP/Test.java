package MTSP;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import javax.swing.JFrame;

public class Test extends JFrame{
	public static double[][] locate = {{37,52},{49,49},{52,64},{20,26},{40,30},{21,47},{17,63},{31,62},
			{52,33},{51,21},{42,41},{31,32},{5,25},{12,42},{36,16},{52,41},
			{27,23},{17,33},{13,13},{57,58},{62,42},{42,57},{16,57},{8,52},
			{7,38},{27,68},{30,48},{43,67},{58,48},{58,27},{37,69},{38,46},
			{46,10},{61,33},{62,63},{63,69},{32,22},{45,35},{59,15},{5,6},
			{10,17},{21,10},{5,64},{30,15},{39,10},{32,39},{25,32},{25,55},
			{48,28},{56,37},{30,40}};
	public final static int N=locate.length+1;
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
			disself[i+1]=sqrt(pow((locate[i][0]-35),2)+pow((locate[i][1]-39),2));
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
		
		show(route);
		
		//绘制图形界面
		Test tt =new Test();
		
	}
	
	//构造函数
	public Test() {
		pd = new Pattern_Display(locate,route);
		this.add(pd);
		this.setSize(1366, 730);
		//this.setLocationRelativeTo(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);   //最大化 
		this.setResizable(false);         //不能改变大小 
//		this.setUndecorated(true); 
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
		System.arraycopy(route1, 0, route, contx-x+1+y-conty, conty-1);
		System.arraycopy(route1, y, route, contx-x+y, x-y);
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
	
	public static void show(int[] route){
		for(int i=0;i<2*N+1;i++) {
			System.out.print(route[i]+"→");
			if((route[i]==0)&&(route[i+1]==0)) {
				break;
			}
		}
		System.out.println();
		
		
//	       for(int i = 0; i < route.length; i++){
//	            for(int j = 0; j < route.length-1-i; j++){
//	                if(route[j] > route[j+1]){
//	                    int temp = route[j];
//	                    route[j] = route[j+1];
//	                    route[j+1] = temp;
//	                }
//	            }
//	        }
//	       
//			for(int i=0;i<2*N+1;i++) {
//				System.out.print(route[i]+"→");
//			}
	       
	       
	}

}
