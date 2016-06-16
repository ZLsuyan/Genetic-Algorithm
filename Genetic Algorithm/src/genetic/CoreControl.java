package genetic;
/** 
 * @author ����
 * @date 2016/1/17
 */
import java.util.Random;

public class CoreControl {     // ���Ŀ�����
	
	/**
	 * ��Ҫ����
	 * @param A                �����½�
	 * @param B                �����Ͻ�
	 * @param interal          ���� 
	 * @param problemThings[]  ������ص� ����
	 * @param members[]        ��Ⱥ�ĳ�Ա
	 * @param LENGTH           ���볤��
	 * @param stop             ֹͣ�Ĵ���
	 * @return  
	 */
	
	public static final double A=-1;//�����½�
	public static final double B=2;//�����Ͻ�
	public static final double interal = B-A;//����
	public double[] problemThings = new double[SpeciesGroup.size];//������ص� ����
	public String[] members = new String[SpeciesGroup.size];
	
	public static final int LENGTH=22;//���볤�ȣ���ΪҪ��ȷ��С�������λ�����Ա�Ϊ22λ������һ��ʽ�ɲο�
	public static final int MJ2=4194304;//2^22 

	public static final int stop = 100;//ֹͣ�Ĵ���
	
	/**
	 * ����
	 */
	public void encode(){
		int[] decimal = new int[problemThings.length];//ʮ������ʽ ��Ա��
		for(int i = 0;i < problemThings.length;i++){
			decimal[i] = (int)((problemThings[i]-A)/interal*(MJ2-1));//���Ƚ� DOUBLE ����� ת��Ϊ ���� ������
		}
        
		for(int i = 0;i < decimal.length;i++){//ʮ���� ��������
			members[i] = Integer.toBinaryString(decimal[i]);
		}
		
		for(int i = 0;i < members.length;i++){//��Ա ���� ���� 22
			if(members[i].length()<22){
				while(members[i].length()<22){
					members[i] = '0'+members[i];//���� ����
				}
			}
			else if(members[i].length()>22){
				System.out.println("�������");
			}
			else{				
			}
		}
		
		
	}
	

	
	/**
	 * �ж��Ƿ����
	 */
	boolean isEnded(){
		if(SpeciesGroup.generation < stop)
			return false;
		else
			return true;
	}
	
	/**
	 * ��ʼ��
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
