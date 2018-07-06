package game.controller;

import java.io.*;
import java.lang.ProcessBuilder.Redirect;

import game.data.Data;
import game.data.TextCommandData;

//プロセス用のController
public class ProcessController extends Controller<Data> {
	public static final String READ_END = "end";
	private ProcessBuilder processBuilder;
	private Process process;
	private BufferedReader reader;
	private BufferedWriter writer ;
	public ProcessController(String command) {
		super(command);
		processBuilder = new ProcessBuilder(command.split(" "));//コマンドを空白で分割
		processBuilder.redirectError(Redirect.INHERIT);//TODO エラー出力を標準出力にとりあえずリダイレクト
	}

	@Override
	public void open() {
		try {
			process = processBuilder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		reader =new BufferedReader(new InputStreamReader(process.getInputStream()));
		writer =new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));


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
		try {
			reader.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}
