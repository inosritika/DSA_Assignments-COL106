public class program
{
	public String test(String hex)
	{
		/*
		Exercise 16: Hex to binary- Given a string representing a number in hexadecimal
		format, convert it into its equivalent binary string. For e.g. if the input if ”1F1”
		then its binary equivalent is ”111110001”. If the input is ”13AFFFF”, the output
		should be ”1001110101111111111111111”.
		*/
		String binary="";
		int num = (Integer.parseInt(hex, 16));
		binary = Integer.toBinaryString(num);
		return binary;
	}
}
