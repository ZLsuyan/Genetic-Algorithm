package genetic;
/** 
 * @author 曾丽
 * @date 2016/1/17
 */

import java.util.Random;


public class SpeciesGroup {
	
	/**
	 * @param size             种群大小 
	 * @param member[]         种群成员
	 * @param fitness[]        成员适应度
	 * @param generation       代数
	 * @param VProbability     变异属性
	 * @param IProbability     交叉属性
	 * @param lengthOfCode     染色体基因个数  || 码长
	 * @param problemThings[]  问题相关的 数字
	 * @return  
	 */
	
	
	/**
	 * 主属性	
	 */
	static int size = 50;//种群大小；
	String[] member = new  String[SpeciesGroup.size];//种群成员；
	double[] fitness = new  double[SpeciesGroup.size];//成员适应度
	
	static int generation = 0;//代数
	double VProbability = 0.01;//变异概率
	double IProbability = 0.25;//交叉概率
	static int lengthOfCode = 22;//染色体基因个数  || 码长
	
		
	/**
	 * 工具属性	
	 */
	Random randomNumber=new Random();
	public double[] problemThings = new double[SpeciesGroup.size];//问题相关的 数字
	
	/**
	 * 方法实现
	 * @param firstGeneration
	 */
	SpeciesGroup(String[] firstGeneration){
		for(int i = 0;i<size;i++){
			member[i] = firstGeneration[i];
		}
		//进行解码
		decode();
	}
	
	/**
	 * 编码
	 */
	public void encode(){
		int[] decimal = new int[problemThings.length];//十进制形式 成员的
		for(int i = 0;i < problemThings.length;i++){
			decimal[i] = (int)((problemThings[i]-CoreControl.A)/CoreControl.interal*(CoreControl.MJ2-1));//首先将 DOUBLE 随机数 转化为 编码 的整数
		}
        
		for(int i = 0;i < decimal.length;i++){//十进制 到二进制
			member[i] = Integer.toBinaryString(decimal[i]);
		}
		
		for(int i = 0;i < member.length;i++){//成员 基因 不满 22
			if(member[i].length()<lengthOfCode){
				while(member[i].length()<lengthOfCode){
					member[i] = '0'+member[i];//不满 补零
				}
			}
			else if(member[i].length()>22){
				System.out.println("编码出错 !");
			}
			else{			
			}
		}	
	}
	
	/**
	 * 第一个输出平均适应度，第二个输出最大适应度
	 * @return
	 */
	public double[] getValues(){
		double[] values = new double[2] ;
		
		double sum = 0 ;
		for(int i = 0 ; i < fitness.length ; i ++){
			sum += fitness[i] ;
		}
		
		values[0] = sum / fitness.length ;
		values[1] = sufficiency() ;
		
		return values ;
	}
	
	
	
	
	
	
	
	
	/**
	 * 解码
	 */
	public void decode(){
		int k;//十进制形式 成员的
		for(int i = 0; i<member.length;i++){
			k = Integer.parseInt(member[i], 2);
			problemThings[i] = CoreControl.A + CoreControl.interal/(CoreControl.MJ2-1)*k;
		}		
	}
	
	/**
	 * 变异 
	 */
	public void variation(){
		for(int i=0;i<size;i++){
			if(randomNumber.nextDouble()<=VProbability){

				int changePlace=randomNumber.nextInt(22);  //随机选择一位取反
				StringBuffer sb=new StringBuffer(member[i]);
				
				if(sb.charAt(changePlace)=='0'){     
					sb.setCharAt(changePlace, '1');			//取反	 						
				}
				else if(sb.charAt(changePlace)=='1'){		
					sb.setCharAt(changePlace, '0');         //取反
				}
				else{
					System.out.println("出错！！！");
				}
				member[i]=sb.toString();
				
			//	System.out.println(member[i]);
			}
			else{				
			}
		}
	}
	
	/**
	 * 交叉
	 */
	public void intersect(){
		for(int i = 0;i <= size/2;i++){
			if(Math.random() <= IProbability){
				int one = randomNumber.nextInt(size);//随机选出 一个 个体
				int theOther = randomNumber.nextInt(size);//随机选出另一个个体
				int intersection = randomNumber.nextInt(lengthOfCode);//随机产生 交叉点
							
				String str_one = member[one].substring(intersection);
				String str_theOther = member[theOther].substring(intersection);
				
	//			System.out.println(str_one);
	//			System.out.println(str_theOther);
				
				StringBuffer sb = new StringBuffer(member[one]);//交换第一个 个体
				sb.replace(intersection, lengthOfCode,str_theOther);
				member[one] = sb.toString();
				
			    sb = new StringBuffer(member[theOther]);//交换第二个 个体
				sb.replace(intersection, lengthOfCode,str_one);
				member[theOther] = sb.toString();
				
		//		System.out.println(member[one]);
		//		System.out.println(member[theOther]);
 			}
			else{				
			}
		}
	}
	
	/**
	 * 轮盘赌方法
	 */
	public void gambleWheel_1()
	{ 
	   double sum=0;
	   for (int i = 0; i <size; i++) {
		   sum=fitness[i]+sum;
	   }

	   double[] p = new double[SpeciesGroup.size]; //适应度的概率
	   for (int i = 0; i < size; i++) {
		   p[i]=fitness[i]/sum;
	   }
	   double[] q = new double[SpeciesGroup.size];
	   for (int i = 0; i < size; i++) {
		   for (int j = 0; j < i+1; j++) {	     
			  q[i]+=p[j];
	       }
	   }
	   double[] ran=new double[50];
	   String[] tempPop=new String[50];
	   for (int i = 0; i < ran.length; i++) {	    
		   ran[i]=randomNumber.nextDouble();
	   }
	   for (int i = 0; i < ran.length; i++) {	    
		   int k = 0;	  
		   for (int j = 0; j < q.length; j++) {	    
			   if(ran[i]<q[j]){	     
				   k=j;	      
				   break;	    
			   }	     
			   else continue;	    
		   }	    
		   tempPop[i]=member[k];	  
	   }
	   for (int i = 0; i < tempPop.length; i++) {	    
		   member[i]=tempPop[i];
	   }
	}
	
	/**
	 * 赌轮选择 
	 */
	public void gambleWheel(){	
		double sum=0;//适应度之和
		for(int i=0;i<SpeciesGroup.size;i++){
			sum+=fitness[i];
		}
		
		double[] fProbability = new double[SpeciesGroup.size]; //适应度的概率
		for(int i=0;i<SpeciesGroup.size;i++){
			fProbability[i] = fitness[i]/sum;
		}

		double[] wheelBorder = new double[SpeciesGroup.size+1];//赌轮 边界
		wheelBorder[0] = 0;
		for(int i = 0;i<SpeciesGroup.size;i++){
			for(int j = 0;j<=i;j++){
				wheelBorder[i+1] +=   fProbability[j];
			}
		}
		
		double pointer = 0;
		String[]tempMember = new  String[SpeciesGroup.size];//下一代 临时种群成员；
		for(int i = 0;i<SpeciesGroup.size;i++){
		// pointer = Math.random();                     //  模仿赌轮指针  			
			pointer = randomNumber.nextDouble();			 
			for(int j=0;j<wheelBorder.length-1;j++){		//赌轮 转动 后 指针 随机 指向 赌轮 中的一块区域				 
				if(pointer>=wheelBorder[j]&&pointer<wheelBorder[j+1]){//确定是那块区域					 
					tempMember[i] = member[j];           //     结果临时保存  以免影响 种群属性					 
					break;				 
				}				 
				else {				 
				}			 			
			}		
		}
		for(int i = 0;i<SpeciesGroup.size;i++){//临时 成员  转化 为 真实 成员			
			member[i] = tempMember[i];
		}	
	}
	
	/**
	 * 返回最大的适应度值
	 * f==xsin(10*pi*x)+1
	 */
	public double sufficiency(){
	//	String theBest=null;
		int max = 0;
		for(int i = 0;i<SpeciesGroup.size;i++){
			fitness[i] = problemThings[i]*Math.sin(Math.PI*10*problemThings[i])+1;
			if(fitness[max]<fitness[i]){
				max = i;
			}
		}
		System.out.println("x=："+problemThings[max]);        //输出每次迭代适应度最大对应的x值
		return fitness[max];
	}
	
	
	
	/**
	 * 下一代
	 */
	public void nextGeneration(){	
		//gambleWheel_1();  //选择
		gambleWheel();      //选择		
		
		intersect();   		//交叉
		variation();   		//变异
		SpeciesGroup.generation++;         //不停迭代到下一代
	}
	
	/**
	 * 输出种群成员
	 */
	public void print(){
		for(int i=0;i< SpeciesGroup.size;i++){
			System.out.println(member[i]);
		}
	}
	
	/**
	 * 主函数
	 * @param args
	 */
	public static void main(String[] args) {
    }

}
