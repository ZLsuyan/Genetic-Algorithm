package genetic;
/** 
 * @author 曾丽
 * @date 2016/1/17
 */
import java.util.Random;

public class CoreControl {     // 核心控制类
	
	/**
	 * 主要参数
	 * @param A                区间下界
	 * @param B                区间上界
	 * @param interal          区间 
	 * @param problemThings[]  问题相关的 数字
	 * @param members[]        种群的成员
	 * @param LENGTH           编码长度
	 * @param stop             停止的代数
	 * @return  
	 */
	
	public static final double A=-1;//区间下界
	public static final double B=2;//区间上界
	public static final double interal = B-A;//区间
	public double[] problemThings = new double[SpeciesGroup.size];//问题相关的 数字
	public String[] members = new String[SpeciesGroup.size];
	
	public static final int LENGTH=22;//编码长度，因为要精确到小数点后六位，所以编为22位长，有一公式可参考
	public static final int MJ2=4194304;//2^22 

	public static final int stop = 100;//停止的代数
	
	/**
	 * 编码
	 */
	public void encode(){
		int[] decimal = new int[problemThings.length];//十进制形式 成员的
		for(int i = 0;i < problemThings.length;i++){
			decimal[i] = (int)((problemThings[i]-A)/interal*(MJ2-1));//首先将 DOUBLE 随机数 转化为 编码 的整数
		}
        
		for(int i = 0;i < decimal.length;i++){//十进制 到二进制
			members[i] = Integer.toBinaryString(decimal[i]);
		}
		
		for(int i = 0;i < members.length;i++){//成员 基因 不满 22
			if(members[i].length()<22){
				while(members[i].length()<22){
					members[i] = '0'+members[i];//不满 补零
				}
			}
			else if(members[i].length()>22){
				System.out.println("编码错处啦");
			}
			else{				
			}
		}
		
		
	}
	

	
	/**
	 * 判断是否结束
	 */
	boolean isEnded(){
		if(SpeciesGroup.generation < stop)
			return false;
		else
			return true;
	}
	
	/**
	 * 初始化
	 * @param args
	 */
	Random randomNumber=new Random();
	public void initialize(){
		for(int i = 0;i<SpeciesGroup.size;i++){
			problemThings[i] = A+randomNumber.nextDouble()*interal;
		//	problemThings[i] = Math.random();
		}
		
		encode();
		
	}
	
	public String[] getMembers(){
		return members;
	}
}
