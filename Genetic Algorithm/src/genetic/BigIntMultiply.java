package genetic;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * �������˷�
 * @author Maxy
 *
 */
public class BigIntMultiply
{

    //��ģֻҪ�������Χ�ھͿ���ֱ�Ӽ�����
    private final static int SIZE = 4;

    // �˷���Ҫ��֤���lenΪX��Y�ĳ������ֵ
    private static String bigIntMultiply(String X, String Y, int len)
    {
        // ���շ��ؽ��
        String str = "";
        // ����X��Y��ʹ֮������ͬ
        X = formatNumber(X, len);
        Y = formatNumber(Y, len);

        // ����4λ������ֱ�Ӽ���
        if (len <= SIZE)
        {
            return "" + (Integer.parseInt(X) * Integer.parseInt(Y));
        }

        // ��X��Y�ֱ�԰�ֳ�������
        int len1 = len / 2;
        int len2 = len - len1;
        String A = X.substring(0, len1);
        String B = X.substring(len1);
        String C = Y.substring(0, len1);
        String D = Y.substring(len1);

        // �˷����򣬷ֿ鴦��
        int lenM = Math.max(len1, len2);
        String AC = bigIntMultiply(A, C, len1);
        String AD = bigIntMultiply(A, D, lenM);
        String BC = bigIntMultiply(B, C, lenM);
        String BD = bigIntMultiply(B, D, len2);

        // ����BD���õ�ԭλ����λ
        String[] sBD = dealString(BD, len2);

        // ����AD+BC�ĺ�
        String ADBC = addition(AD, BC);
        // ����BD�Ľ�λ
        if (!"0".equals(sBD[1]))
        {
            ADBC = addition(ADBC, sBD[1]);
        }

        // �õ�ADBC�Ľ�λ
        String[] sADBC = dealString(ADBC, lenM);

        // AC����ADBC�Ľ�λ
        AC = addition(AC, sADBC[1]);

        // ���ս��
        str = AC + sADBC[0] + sBD[0];

        return str;
    }

    // �������ִ���λ��
    private static String addition(String ad, String bc)
    {
        // ���صĽ��
        String str = "";

        // ���ַ�������Ҫ��ͬ
        int lenM = Math.max(ad.length(), bc.length());
        ad = formatNumber(ad, lenM);
        bc = formatNumber(bc, lenM);

        // ��λ�ӣ���λ�洢��temp��
        int flag = 0;

        // �Ӻ���ǰ��λ���
        for (int i = lenM - 1; i >= 0; i--)
        {
            int t =
                flag + Integer.parseInt(ad.substring(i, i + 1))
                    + Integer.parseInt(bc.substring(i, i + 1));

            // ����������9�����λ��ǰλֻ������λ��
            if (t > 9)
            {
                flag = 1;
                t = t - 10;
            }
            else
            {
                flag = 0;
            }

            // ƴ�ӽ���ַ���
            str = "" + t + str;
        }
        if (flag != 0)
        {
            str = "" + flag + str;
        }
        return str;
    }

    // �������ִ����������λ��
    // String�����һ��Ϊԭλ���֣��ڶ���Ϊ��λ
    private static String[] dealString(String ac, int len1)
    {
        String[] str = {ac, "0"};
        if (len1 < ac.length())
        {
            int t = ac.length() - len1;
            str[0] = ac.substring(t);
            str[1] = ac.substring(0, t);
        }
        else
        {
            // Ҫ��֤�����length����ε�lenһ�£��������λ��0
            String result = str[0];
            for (int i = result.length(); i < len1; i++)
            {
                result = "0" + result;
            }
            str[0] = result;
        }
        return str;
    }

    // ������������λ������
    private static String formatNumber(String x, int len)
    {
        while (len > x.length())
        {
            x = "0" + x;
        }
        return x;
    }

    //����׮
    public static void main(String[] args)
    {
        // ������ʽ������0��ͷ�����ִ�
        String pat = "^[1-9]\\d*$";
        Pattern p = Pattern.compile(pat);

        // ��ó���A
        System.out.println("���������A������0��ͷ������������");
        Scanner sc = new Scanner(System.in);
        String A = sc.nextLine();
        Matcher m = p.matcher(A);
        if (!m.matches())
        {
            System.out.println("���ֲ��Ϸ���");
            return;
        }

        // ��ó���B
        System.out.println("���������B������0��ͷ������������");
        sc = new Scanner(System.in);
        String B = sc.nextLine();
        m = p.matcher(B);
        if (!m.matches())
        {
            System.out.println("���ֲ��Ϸ���");
            return;
        }
        System.out.println(A + " * " + B + " = "
            + bigIntMultiply(A, B, Math.max(A.length(), B.length())));
    }
}
