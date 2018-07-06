package game.controller;

import java.io.*;
import java.lang.ProcessBuilder.Redirect;

import game.data.CommandData;
import game.data.Data;
import game.data.TextCommandData;

//プロセス用のController
public class CommandLineController extends Controller<Data> {
	public static final String READ_END = "end";
	private BufferedReader reader;
	private BufferedWriter writer;
	public CommandLineController() {
		super("commandLineController");
	}

	@Override
	public void open() {
		reader =new BufferedReader(new InputStreamReader(System.in));
		writer =new BufferedWriter(new OutputStreamWriter(System.out));
	}

	@Override
	public TextCommandData controll(Data data) {
		String command = "";
		try {
			//ゲームのデータを出力
			for (String s:data.toString().split("\n")) {
				writer.write(s);
				writer.newLine();
			}
			writer.flush();
			//Processの行動を入力
			String line = null;
			while(true){

				line = reader.readLine();
				if(line==null || isEndLine(line))break;
				command += line + "\n";
			}



		} catch (IOException e) {
			e.printStackTrace();
		}
		return new TextCommandData(this,command);
	}

	private boolean isEndLine(String line)  {
		return line.equals(READ_END);
	}

	@Override
	public void close() {
		/*try {
			reader.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/


	}

}
