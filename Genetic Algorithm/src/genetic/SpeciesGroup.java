package genetic;
/** 
 * @author ����
 * @date 2016/1/17
 */

import java.util.Random;


public class SpeciesGroup {
	
	/**
	 * @param size             ��Ⱥ��С 
	 * @param member[]         ��Ⱥ��Ա
	 * @param fitness[]        ��Ա��Ӧ��
	 * @param generation       ����
	 * @param VProbability     ��������
	 * @param IProbability     ��������
	 * @param lengthOfCode     Ⱦɫ��������  || �볤
	 * @param problemThings[]  ������ص� ����
	 * @return  
	 */
	
	
	/**
	 * ������	
	 */
	static int size = 50;//��Ⱥ��С��
	String[] member = new  String[SpeciesGroup.size];//��Ⱥ��Ա��
	double[] fitness = new  double[SpeciesGroup.size];//��Ա��Ӧ��
	
	static int generation = 0;//����
	double VProbability = 0.01;//�������
	double IProbability = 0.25;//�������
	static int lengthOfCode = 22;//Ⱦɫ��������  || �볤
	
		
	/**
	 * ��������	
	 */
	Random randomNumber=new Random();
	public double[] problemThings = new double[SpeciesGroup.size];//������ص� ����
	
	/**
	 * ����ʵ��
	 * @param firstGeneration
	 */
	SpeciesGroup(String[] firstGeneration){
		for(int i = 0;i<size;i++){
			member[i] = firstGeneration[i];
		}
		//���н���
		decode();
	}
	
	/**
	 * ����
	 */
	public void encode(){
		int[] decimal = new int[problemThings.length];//ʮ������ʽ ��Ա��
		for(int i = 0;i < problemThings.length;i++){
			decimal[i] = (int)((problemThings[i]-CoreControl.A)/CoreControl.interal*(CoreControl.MJ2-1));//���Ƚ� DOUBLE ����� ת��Ϊ ���� ������
		}
        
		for(int i = 0;i < decimal.length;i++){//ʮ���� ��������
			member[i] = Integer.toBinaryString(decimal[i]);
		}
		
		for(int i = 0;i < member.length;i++){//��Ա ���� ���� 22
			if(member[i].length()<lengthOfCode){
				while(member[i].length()<lengthOfCode){
					member[i] = '0'+member[i];//���� ����
				}
			}
			else if(member[i].length()>22){
				System.out.println("������� !");
			}
			else{			
			}
		}	
	}
	
	/**
	 * ��һ�����ƽ����Ӧ�ȣ��ڶ�����������Ӧ��
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
	 * ����
	 */
	public void decode(){
		int k;//ʮ������ʽ ��Ա��
		for(int i = 0; i<member.length;i++){
			k = Integer.parseInt(member[i], 2);
			problemThings[i] = CoreControl.A + CoreControl.interal/(CoreControl.MJ2-1)*k;
		}		
	}
	
	/**
	 * ���� 
	 */
	public void variation(){
		for(int i=0;i<size;i++){
			if(randomNumber.nextDouble()<=VProbability){

				int changePlace=randomNumber.nextInt(22);  //���ѡ��һλȡ��
				StringBuffer sb=new StringBuffer(member[i]);
				
				if(sb.charAt(changePlace)=='0'){     
					sb.setCharAt(changePlace, '1');			//ȡ��	 						
				}
				else if(sb.charAt(changePlace)=='1'){		
					sb.setCharAt(changePlace, '0');         //ȡ��
				}
				else{
					System.out.println("��������");
				}
				member[i]=sb.toString();
				
			//	System.out.println(member[i]);
			}
			else{				
			}
		}
	}
	
	/**
	 * ����
	 */
	public void intersect(){
		for(int i = 0;i <= size/2;i++){
			if(Math.random() <= IProbability){
				int one = randomNumber.nextInt(size);//���ѡ�� һ�� ����
				int theOther = randomNumber.nextInt(size);//���ѡ����һ������
				int intersection = randomNumber.nextInt(lengthOfCode);//������� �����
							
				String str_one = member[one].substring(intersection);
				String str_theOther = member[theOther].substring(intersection);
				
	//			System.out.println(str_one);
	//			System.out.println(str_theOther);
				
				StringBuffer sb = new StringBuffer(member[one]);//������һ�� ����
				sb.replace(intersection, lengthOfCode,str_theOther);
				member[one] = sb.toString();
				
			    sb = new StringBuffer(member[theOther]);//�����ڶ��� ����
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
	 * ���̶ķ���
	 */
	public void gambleWheel_1()
	{ 
	   double sum=0;
	   for (int i = 0; i <size; i++) {
		   sum=fitness[i]+sum;
	   }

	   double[] p = new double[SpeciesGroup.size]; //��Ӧ�ȵĸ���
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
	 * ����ѡ�� 
	 */
	public void gambleWheel(){	
		double sum=0;//��Ӧ��֮��
		for(int i=0;i<SpeciesGroup.size;i++){
			sum+=fitness[i];
		}
		
		double[] fProbability = new double[SpeciesGroup.size]; //��Ӧ�ȵĸ���
		for(int i=0;i<SpeciesGroup.size;i++){
			fProbability[i] = fitness[i]/sum;
		}

		double[] wheelBorder = new double[SpeciesGroup.size+1];//���� �߽�
		wheelBorder[0] = 0;
		for(int i = 0;i<SpeciesGroup.size;i++){
			for(int j = 0;j<=i;j++){
				wheelBorder[i+1] +=   fProbability[j];
			}
		}
		
		double pointer = 0;
		String[]tempMember = new  String[SpeciesGroup.size];//��һ�� ��ʱ��Ⱥ��Ա��
		for(int i = 0;i<SpeciesGroup.size;i++){
		// pointer = Math.random();                     //  ģ�¶���ָ��  			
			pointer = randomNumber.nextDouble();			 
			for(int j=0;j<wheelBorder.length-1;j++){		//���� ת�� �� ָ�� ��� ָ�� ���� �е�һ������				 
				if(pointer>=wheelBorder[j]&&pointer<wheelBorder[j+1]){//ȷ�����ǿ�����					 
					tempMember[i] = member[j];           //     �����ʱ����  ����Ӱ�� ��Ⱥ����					 
					break;				 
				}				 
				else {				 
				}			 			
			}		
		}
		for(int i = 0;i<SpeciesGroup.size;i++){//��ʱ ��Ա  ת�� Ϊ ��ʵ ��Ա			
			member[i] = tempMember[i];
		}	
	}
	
	/**
	 * ����������Ӧ��ֵ
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
		System.out.println("x=��"+problemThings[max]);        //���ÿ�ε�����Ӧ������Ӧ��xֵ
		return fitness[max];
	}
	
	
	
	/**
	 * ��һ��
	 */
	public void nextGeneration(){	
		//gambleWheel_1();  //ѡ��
		gambleWheel();      //ѡ��		
		
		intersect();   		//����
		variation();   		//����
		SpeciesGroup.generation++;         //��ͣ��������һ��
	}
	
	/**
	 * �����Ⱥ��Ա
	 */
	public void print(){
		for(int i=0;i< SpeciesGroup.size;i++){
			System.out.println(member[i]);
		}
	}
	
	/**
	 * ������
	 * @param args
	 */
	public static void main(String[] args) {
    }

}
