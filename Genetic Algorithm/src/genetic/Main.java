package genetic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/** 
 * @author ����
 * @date 2016/1/17
 */

public class Main {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		PrintWriter pw1 = new PrintWriter(new File("F:"+File.separator+"averg.txt")) ;
		PrintWriter pw2 = new PrintWriter(new File("F:"+File.separator+"maxfit.txt")) ;
		
		CoreControl control = new CoreControl();
		control.initialize();        //���г�ʼ��
		
		SpeciesGroup speciesGroup = new SpeciesGroup(control.getMembers());
		System.out.println("��ʼ����Ⱥ��ԱΪ��");
		speciesGroup.print();       //�����Ŵ��㷨֮ǰ��Ⱥ��Ա���
		double theBest;
		
		speciesGroup.decode();
		System.out.println("��0����");
		theBest = speciesGroup.sufficiency();
		System.out.println("f="+theBest); //���ÿ�ε�����Ӧ������Ӧ�Ľ��ֵ
		System.out.println();
		
		double[] values = speciesGroup.getValues() ;
//		pw1.println("0 "+ values[0]) ;
//		pw2.println("0 "+ values[1]) ;
		pw1.println(values[0]) ;
		pw2.println(values[1]) ;
		pw1.flush() ;
		pw2.flush() ;
		
		
		int n = 1;
		do{
			speciesGroup.gambleWheel();        //���ö��̷�������ѡ��			          
			speciesGroup.intersect();          //����
			speciesGroup.variation();          //����

			values = speciesGroup.getValues() ;
//			pw1.println(n+" "+ values[0]) ;
//			pw2.println(n+" "+ values[1]) ;
			pw1.println(values[0]) ;
			pw2.println(values[1]) ;
			pw1.flush() ;
			pw2.flush() ;
			
//			double avergfitness = 0;
//			double[] fitness = new double[50];
//			for()
			speciesGroup.decode();
			System.out.println("��"+n+"����");
			theBest = speciesGroup.sufficiency();
			System.out.println("f="+theBest); //���ÿ�ε�����Ӧ������Ӧ�Ľ��ֵ
			System.out.println();

			
			SpeciesGroup.generation++;
			n++;

		}while(!control.isEnded());           //����������û�ﵽʱ��������
			
		System.out.println("����Ⱥ��ԱΪ��");
		speciesGroup.print();      //�����Ŵ��㷨֮����Ⱥ��Ա���
	}

}
