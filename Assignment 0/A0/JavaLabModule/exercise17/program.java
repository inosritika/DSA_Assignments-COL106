public class program
{
	public String[] test(String fileNames[])
	{

		int size = 0;
		for(int i = 0;i<fileNames.length;i++){
			String s = fileNames[i];
			if(s.length()<5){
				continue;
			}
			s = s.substring(s.length()-5,s.length());
			if(s.equals(".java")){
				size++;
			}
		}
		String javaFiles[] = new String[size];
		int idx = 0;
		for(String j : fileNames){
			String s = j;
			if(s.length()<5){
				continue;
			}
			s = s.substring(s.length()-5,s.length());
			if(s.equals(".java")){
				javaFiles[idx] = j;
				idx++;
			}
		}
		return javaFiles;
	}
}
