package AddRestrictions;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import javax.swing.JFrame;

public class CWTSP extends JFrame {
	static double[] disself=new double[init.N];
	static double[][] dis = new double[init.N][init.N];  //距离矩阵
	static double[][] S = new double[init.N][init.N];	 //节省值矩阵
	static double[] e = new double[init.N*init.N];    	 //节省值降序排列
	static int[][] route =new int [init.N][init.N+50];
	static int[] x =new int[init.N*init.N];
	static int[] y =new int[init.N*init.N];
	static int[] ans =new int[init.N+50];
	static double avex = 0;
	static double avey = 0;
	static int n = 0;
	static int flag =0;
	static double[] Capacity = new double[init.N];
	Pattern_Display pd = null;

	public static void main(String[] args) {

		function fun =new function();

		init();

//		System.out.println("节约值排序");
//		for(int i =1;i<=n;i++) {
//			if(e[i]!=0) {
//				System.out.println(e[i]+"...("+locate[x[i]][0]+","+locate[x[i]][1]+")--("+locate[y[i]][0]+","+locate[y[i]][1]+")");
//			}
//		}

		for(int i=1; i<=n; i++) {
			int[] a = fun.findindex(x[i],route);
			int[] b = fun.findindex(y[i],route);
			if((Capacity[a[0]]+Capacity[b[0]])<=5) {
			if(a[0]!=b[0]) {
				int k = fun.Type(a,b,route);
				switch(k) {
				case 1:
					fun.inthequeue(a,b,route,Capacity);
					break;
				case 2:
					fun.inthequeue2(a,b,route,Capacity);
					break;
				case 3:
					fun.inthequeue3(a,b,route,Capacity);
					break;
				case 4:
					fun.inthequeue4(a,b,route,Capacity);
					break;
				default:
					break;
				}

			}
		}

		}


		int cont =0;
		for(int i=0; i<init.N; i++) {
			if((route[i][0]==0)&&(route[i][1]==0)) {
				continue;
			}
			for(int j=0; j<init.N+20; j++) {
				ans[cont] = route[i][j];
				cont++;
				if((route[i][j]==0)&&(route[i][j+1]==0)) {
					break;
				}

			}
		}

		for(int i=0; i<init.N+20; i++) {
			System.out.print(ans[i]+"→");
		}
		System.out.println();
		
        double distance =0;
		for(int i=1;i<init.N-1;i++) {
			distance =distance + dis[ans[i]][ans[i+1]];
		}
		distance = distance +dis[init.N-1][1];
		System.out.println(distance);

		CWTSP ct =new CWTSP();

	}


	public CWTSP() {
		pd = new Pattern_Display(init.locate,ans,avex,avey);
		this.add(pd);
		this.setSize(1366, 730);
		this.setLocationRelativeTo(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);  	  //最大化
		this.setResizable(false);       				  //不能改变大小
//		this.setUndecorated(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public static void init() {
		for(int i=1; i<init.N; i++) {
//			disself[i] = sqrt(pow((init.locate[i][0])-(init.locate[1][0]),2)
//						+pow((init.locate[i][1])-(init.locate[1][1]),2)); //每个点到原点的距离
			disself[i] = sqrt(pow((init.locate[i][0]-avex),2)
					+pow((init.locate[i][1]-avey),2)); //每个点到原点的距离
		}

		for(int i=1; i<init.N; i++) {
			for(int j=i+1; j<init.N; j++) {
				n++;
				dis[i][j] = sqrt(pow((init.locate[i][0]-init.locate[j][0]),2)
							+pow((init.locate[i][1]-init.locate[j][1]),2));
				dis[j][i] = dis[i][j];
				S[i][j] = disself[i]+disself[j]-dis[i][j];
				x[n] = i;
				y[n] = j;
				e[n] = S[i][j];
			}
		}

		for(int i =1; i<=n; i++) {
			flag = 0;
			for(int j = 1; j<n+1-i; j++) {
				if(e[j]<e[j+1]) {
					double k=e[j];
					e[j] =e[j+1];
					e[j+1] = k;
					int a = x[j];
					x[j] = x[j+1];
					x[j+1] = a;
					int b = y[j];
					y[j] = y[j+1];
					y[j+1] = b;
					flag = 1;
				}
			}
			if(flag==0) {
				break;
			}
		}

		for(int i=0; i<init.N-1; i++) {
			route[i][1] =i+1;
		}
		
		for(int i=0;i<init.N;i++) {
			avex = avex + init.locate[i][0];
			avey = avey + init.locate[i][0];
		}
		avex = avex/init.N;
		avey = avey/init.N;
		
		for(int i=0;i<init.Demand.length;i++) {
			Capacity[i] = init.Demand[i];
		}

	}
}
